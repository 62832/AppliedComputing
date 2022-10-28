package gripe._90.appcom.part;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import dan200.computercraft.api.network.IPacketNetwork;
import dan200.computercraft.api.network.IPacketReceiver;
import dan200.computercraft.api.network.IPacketSender;
import dan200.computercraft.api.network.Packet;
import dan200.computercraft.api.peripheral.IComputerAccess;
import dan200.computercraft.api.peripheral.IPeripheral;
import dan200.computercraft.shared.Capabilities;
import dan200.computercraft.shared.peripheral.modem.ModemState;
import dan200.computercraft.shared.peripheral.modem.wireless.WirelessNetwork;

import appeng.api.parts.IPartItem;
import appeng.api.parts.IPartModel;
import appeng.items.parts.PartModels;
import appeng.parts.p2p.CapabilityP2PTunnelPart;
import appeng.parts.p2p.P2PModels;

import gripe._90.appcom.AppliedComputing;

public class ModemP2PTunnelPart extends CapabilityP2PTunnelPart<ModemP2PTunnelPart, IPeripheral> {

    private static final P2PModels MODELS = new P2PModels(AppliedComputing.makeId("part/modem_p2p_tunnel"));
    private static final NullPacketHandler NULL_MODEM = new NullPacketHandler();

    // TODO
    private final IPacketNetwork network = new WirelessNetwork();
    private final Set<IComputerAccess> computers = new HashSet<>(1);
    private final ModemState state = new ModemState();

    public ModemP2PTunnelPart(IPartItem<?> partItem) {
        super(partItem, Capabilities.CAPABILITY_PERIPHERAL);
        this.inputHandler = new InputPacketHandler();
        this.outputHandler = new OutputPacketHandler();
        this.emptyHandler = NULL_MODEM;
    }

    @PartModels
    public static List<IPartModel> getModels() {
        return MODELS.getModels();
    }

    @Override
    public IPartModel getStaticModels() {
        return MODELS.getModel(this.isPowered(), this.isActive());
    }

    private class InputPacketHandler implements IPeripheral, IPacketSender {
        @NotNull
        @Override
        public Level getLevel() {
            return ModemP2PTunnelPart.this.getLevel();
        }

        @NotNull
        @Override
        public Vec3 getPosition() {
            var pos = getHost().getLocation().getPos();
            return new Vec3(pos.getX(), pos.getY(), pos.getZ());
        }

        @NotNull
        @Override
        public String getSenderID() {
            synchronized (computers) {
                if (computers.size() != 1) {
                    return "unknown";
                } else {
                    var computer = computers.iterator().next();
                    return computer.getID() + "_" + computer.getAttachmentName();
                }
            }
        }

        @NotNull
        @Override
        public String getType() {
            return "modem";
        }

        @Override
        public boolean equals(@Nullable IPeripheral other) {
            for (var tunnel : ModemP2PTunnelPart.this.getOutputs()) {
                try (CapabilityGuard guard = tunnel.getAdjacentCapability()) {
                    return guard.get().equals(other);
                }
            }
            return this == other;
        }
    }

    private class OutputPacketHandler implements IPeripheral, IPacketReceiver {
        @NotNull
        @Override
        public Level getLevel() {
            return ModemP2PTunnelPart.this.getLevel();
        }

        @NotNull
        @Override
        public Vec3 getPosition() {
            var pos = getHost().getLocation().getPos();
            return new Vec3(pos.getX(), pos.getY(), pos.getZ());
        }

        @Override
        public double getRange() {
            return Double.MAX_VALUE;
        }

        @Override
        public boolean isInterdimensional() {
            return true;
        }

        @Override
        public void receiveSameDimension(@NotNull Packet packet, double distance) {
            if (!state.isOpen(packet.channel())) {
                return;
            }

            synchronized (computers) {
                for (var computer : computers) {
                    computer.queueEvent("modem_message", computer.getAttachmentName(), packet.channel(),
                            packet.replyChannel(), packet.payload(), distance);
                }
            }
        }

        @Override
        public void receiveDifferentDimension(@NotNull Packet packet) {
            if (!state.isOpen(packet.channel())) {
                return;
            }

            synchronized (computers) {
                for (var computer : computers) {
                    computer.queueEvent("modem_message", computer.getAttachmentName(), packet.channel(),
                            packet.replyChannel(), packet.payload());
                }
            }
        }

        @NotNull
        @Override
        public String getType() {
            return "modem";
        }

        @Override
        public boolean equals(@Nullable IPeripheral other) {
            try (CapabilityGuard input = ModemP2PTunnelPart.this.getInputCapability()) {
                return input.get().equals(other) || this == other;
            }
        }
    }

    private static class NullPacketHandler implements IPeripheral {
        @NotNull
        @Override
        public String getType() {
            return "modem";
        }

        @Override
        public boolean equals(@Nullable IPeripheral other) {
            return false;
        }
    }
}

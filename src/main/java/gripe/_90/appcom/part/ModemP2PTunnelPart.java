package gripe._90.appcom.part;

import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import dan200.computercraft.api.network.IPacketNetwork;
import dan200.computercraft.api.peripheral.IPeripheral;
import dan200.computercraft.shared.Capabilities;
import dan200.computercraft.shared.peripheral.modem.ModemPeripheral;
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

    private final IPacketNetwork network = new WirelessNetwork();
    private final ModemState state = new ModemState();

    public ModemP2PTunnelPart(IPartItem<?> partItem) {
        super(partItem, Capabilities.CAPABILITY_PERIPHERAL);
        this.inputHandler = this.outputHandler = new ModemTunnelPeripheral();
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

    private class ModemTunnelPeripheral extends ModemPeripheral {
        protected ModemTunnelPeripheral() {
            super(state);
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
        protected IPacketNetwork getNetwork() {
            return network;
        }

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
        public boolean equals(@Nullable IPeripheral other) {
            for (var outputTunnel : ModemP2PTunnelPart.this.getOutputs()) {
                try (CapabilityGuard output = outputTunnel.getAdjacentCapability()) {
                    return output.get().equals(other);
                }
            }
            try (CapabilityGuard input = ModemP2PTunnelPart.this.getInputCapability()) {
                return input.get().equals(other);
            }
        }
    }

    private static class NullPacketHandler implements IPeripheral {
        @NotNull
        @Override
        public String getType() {
            return "";
        }

        @Override
        public boolean equals(@Nullable IPeripheral other) {
            return false;
        }
    }
}

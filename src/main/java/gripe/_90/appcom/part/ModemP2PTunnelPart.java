package gripe._90.appcom.part;

import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import dan200.computercraft.api.network.IPacketReceiver;
import dan200.computercraft.api.network.IPacketSender;
import dan200.computercraft.api.network.Packet;
import dan200.computercraft.api.peripheral.IPeripheral;
import dan200.computercraft.shared.Capabilities;

import appeng.api.parts.IPartItem;
import appeng.api.parts.IPartModel;
import appeng.items.parts.PartModels;
import appeng.parts.p2p.CapabilityP2PTunnelPart;
import appeng.parts.p2p.P2PModels;

import gripe._90.appcom.AppliedComputing;

public class ModemP2PTunnelPart extends CapabilityP2PTunnelPart<ModemP2PTunnelPart, IPeripheral> {

    private static final P2PModels MODELS = new P2PModels(AppliedComputing.makeId("part/modem_p2p_tunnel"));
    private static final NullPacketHandler NULL_MODEM = new NullPacketHandler();

    public ModemP2PTunnelPart(IPartItem<?> partItem) {
        super(partItem, Capabilities.CAPABILITY_PERIPHERAL);
        inputHandler = new InputPacketHandler();
        outputHandler = new OutputPacketHandler();
        emptyHandler = NULL_MODEM;
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
            return null; // TODO
        }

        @NotNull
        @Override
        public String getType() {
            return "modem";
        }

        @Override
        public boolean equals(@Nullable IPeripheral other) {
            return false; // TODO
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
            // TODO
        }

        @Override
        public void receiveDifferentDimension(@NotNull Packet packet) {
            // TODO
        }

        @NotNull
        @Override
        public String getType() {
            return "modem";
        }

        @Override
        public boolean equals(@Nullable IPeripheral other) {
            return false; // TODO
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

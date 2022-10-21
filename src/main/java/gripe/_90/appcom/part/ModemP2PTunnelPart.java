package gripe._90.appcom.part;

import java.util.List;

import org.jetbrains.annotations.NotNull;

import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import dan200.computercraft.api.network.IPacketReceiver;
import dan200.computercraft.api.network.Packet;
import dan200.computercraft.api.network.wired.IWiredElement;
import dan200.computercraft.api.network.wired.IWiredNode;
import dan200.computercraft.shared.Capabilities;
import dan200.computercraft.shared.wired.WiredNode;

import appeng.api.parts.IPartItem;
import appeng.api.parts.IPartModel;
import appeng.items.parts.PartModels;
import appeng.parts.p2p.CapabilityP2PTunnelPart;
import appeng.parts.p2p.P2PModels;

import gripe._90.appcom.AppliedComputing;

public class ModemP2PTunnelPart extends CapabilityP2PTunnelPart<ModemP2PTunnelPart, IWiredElement> {

    private static final P2PModels MODELS = new P2PModels(AppliedComputing.makeId("part/modem_p2p_tunnel"));

    public ModemP2PTunnelPart(IPartItem<?> partItem) {
        super(partItem, Capabilities.CAPABILITY_WIRED_ELEMENT);
        inputHandler = new InputPacketHandler();
        outputHandler = new OutputPacketHandler();
    }

    @PartModels
    public static List<IPartModel> getModels() {
        return MODELS.getModels();
    }

    @Override
    public IPartModel getStaticModels() {
        return MODELS.getModel(this.isPowered(), this.isActive());
    }

    private class InputPacketHandler implements IWiredElement, IPacketReceiver {
        @Override
        public double getRange() {
            return Integer.MAX_VALUE;
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
        public IWiredNode getNode() {
            return new WiredNode(this);
        }

        @NotNull
        @Override
        public Level getLevel() {
            return getHost().getLocation().getLevel();
        }

        @NotNull
        @Override
        public Vec3 getPosition() {
            var blockPos = getHost().getLocation().getPos();
            return new Vec3(blockPos.getX(), blockPos.getY(), blockPos.getZ());
        }

        @NotNull
        @Override
        public String getSenderID() {
            return "modem";
        }
    }

    private class OutputPacketHandler implements IWiredElement {
        @NotNull
        @Override
        public IWiredNode getNode() {
            return new WiredNode(this);
        }

        @NotNull
        @Override
        public Level getLevel() {
            return getHost().getLocation().getLevel();
        }

        @NotNull
        @Override
        public Vec3 getPosition() {
            var blockPos = getHost().getLocation().getPos();
            return new Vec3(blockPos.getX(), blockPos.getY(), blockPos.getZ());
        }

        @NotNull
        @Override
        public String getSenderID() {
            return "modem";
        }
    }
}

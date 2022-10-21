package gripe._90.appcom.data;

import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.data.ExistingFileHelper;

import appeng.core.AppEng;

import gripe._90.appcom.AppliedComputing;

public class ItemModelProvider extends net.minecraftforge.client.model.generators.ItemModelProvider {

    private static final ResourceLocation P2P_TUNNEL_BASE_ITEM = AppEng.makeId("item/p2p_tunnel_base");
    private static final ResourceLocation P2P_TUNNEL_BASE_PART = AppEng.makeId("part/p2p/p2p_tunnel_base");

    private static final ResourceLocation MODEM_FACE = new ResourceLocation("computercraft",
            "block/wireless_modem_normal_face");

    public ItemModelProvider(DataGenerator gen, ExistingFileHelper efh) {
        super(gen, AppliedComputing.MODID, efh);
        efh.trackGenerated(P2P_TUNNEL_BASE_ITEM, MODEL);
        efh.trackGenerated(P2P_TUNNEL_BASE_PART, MODEL);
        efh.trackGenerated(MODEM_FACE, TEXTURE);
    }

    @Override
    protected void registerModels() {
        withExistingParent("item/modem_p2p_tunnel", P2P_TUNNEL_BASE_ITEM).texture("type", MODEM_FACE);
        withExistingParent("part/modem_p2p_tunnel", P2P_TUNNEL_BASE_PART).texture("type", MODEM_FACE);
    }
}

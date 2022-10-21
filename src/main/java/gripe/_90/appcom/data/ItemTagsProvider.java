package gripe._90.appcom.data;

import org.jetbrains.annotations.Nullable;

import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.data.ExistingFileHelper;

import appeng.api.features.P2PTunnelAttunement;

import gripe._90.appcom.AppliedComputing;

public class ItemTagsProvider extends net.minecraft.data.tags.ItemTagsProvider {

    public ItemTagsProvider(DataGenerator gen, @Nullable ExistingFileHelper efh) {
        super(gen, new BlockTagsProvider(gen, efh), AppliedComputing.MODID, efh);
    }

    @Override
    protected void addTags() {
        var cc = P2PTunnelAttunement.getAttunementTag(AppliedComputing.MODEM_P2P_TUNNEL::get);
        tag(cc).addOptional(new ResourceLocation("computercraft", "computer_normal"));
        tag(cc).addOptional(new ResourceLocation("computercraft", "computer_advanced"));
        tag(cc).addOptional(new ResourceLocation("computercraft", "wired_modem"));
        tag(cc).addOptional(new ResourceLocation("computercraft", "wireless_modem_normal"));
        tag(cc).addOptional(new ResourceLocation("computercraft", "wireless_modem_advanced"));
        tag(cc).addOptional(new ResourceLocation("computercraft", "cable"));
        tag(cc).addOptional(new ResourceLocation("computercraft", "pocket_computer_normal"));
        tag(cc).addOptional(new ResourceLocation("computercraft", "pocket_computer_advanced"));
        tag(cc).addOptional(new ResourceLocation("computercraft", "turtle_normal"));
        tag(cc).addOptional(new ResourceLocation("computercraft", "turtle_advanced"));
    }

    private static class BlockTagsProvider extends net.minecraft.data.tags.BlockTagsProvider {
        public BlockTagsProvider(DataGenerator gen, @Nullable ExistingFileHelper efh) {
            super(gen, AppliedComputing.MODID, efh);
        }
    }
}

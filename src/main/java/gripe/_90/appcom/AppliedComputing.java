package gripe._90.appcom;

import org.jetbrains.annotations.NotNull;

import net.minecraft.Util;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import appeng.api.features.P2PTunnelAttunement;
import appeng.api.parts.PartModels;
import appeng.items.parts.PartItem;
import appeng.items.parts.PartModelsHelper;

import gripe._90.appcom.data.AppComDataGenerator;
import gripe._90.appcom.part.ModemP2PTunnelPart;

@Mod(AppliedComputing.MODID)
public class AppliedComputing {

    public static final String MODID = "appcom";

    public static ResourceLocation makeId(String id) {
        return new ResourceLocation(MODID, id);
    }

    public static final CreativeModeTab CREATIVE_TAB = new CreativeModeTab(MODID) {
        @Override
        public @NotNull ItemStack makeIcon() {
            return new ItemStack(MODEM_P2P_TUNNEL::get);
        }
    };

    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);

    public static final RegistryObject<PartItem<ModemP2PTunnelPart>> MODEM_P2P_TUNNEL = Util.make(() -> {
        PartModels.registerModels(PartModelsHelper.createModels(ModemP2PTunnelPart.class));
        return ITEMS.register("modem_p2p_tunnel", () -> new PartItem<>(new Item.Properties().tab(CREATIVE_TAB),
                ModemP2PTunnelPart.class, ModemP2PTunnelPart::new));
    });

    public AppliedComputing() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        ITEMS.register(bus);

        bus.addListener(AppComDataGenerator::onGatherData);
        bus.addListener((FMLCommonSetupEvent event) -> {
            P2PTunnelAttunement.registerAttunementTag(MODEM_P2P_TUNNEL::get);
        });
    }
}

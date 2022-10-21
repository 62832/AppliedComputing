package gripe._90.appcom.data;

import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

public class AppComDataGenerator {
    public static void onGatherData(GatherDataEvent event) {
        var gen = event.getGenerator();
        var efh = event.getExistingFileHelper();

        gen.addProvider(new ItemModelProvider(gen, efh));
        gen.addProvider(new ItemTagsProvider(gen, efh));
    }
}

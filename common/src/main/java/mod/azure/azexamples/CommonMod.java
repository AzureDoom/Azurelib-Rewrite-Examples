package mod.azure.azexamples;

import net.minecraft.resources.ResourceLocation;

public class CommonMod {

    private CommonMod() {}

    public static ResourceLocation modResource(String name) {
        return new ResourceLocation(CommonStrings.MOD_ID, name);
    }
}

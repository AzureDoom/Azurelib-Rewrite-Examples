package mod.azure.azexamples;

import net.minecraft.resources.ResourceLocation;

import mod.azure.azexamples.registry.*;

public class CommonMod {

    private CommonMod() {}

    public static ResourceLocation modResource(String name) {
        return ResourceLocation.fromNamespaceAndPath(CommonStrings.MOD_ID, name);
    }

    /**
     * Initializes the registries for the mod, including blocks, entities, and creative tabs. This method should be
     * called early in the mod's initialization process to ensure that all components are properly registered before
     * being accessed.
     */
    public static void initRegistries() {
        BlockRegistry.initialize();
        EntityRegistry.initialize();
        CreativeTabRegistry.initialize();
        SoundRegistry.initialize();
        ItemRegistry.initialize();
    }
}

package mod.azure.azexamples;

import net.minecraft.resources.ResourceLocation;

import mod.azure.azexamples.registry.BlockRegistry;
import mod.azure.azexamples.registry.CreativeTabRegistry;
import mod.azure.azexamples.registry.EntityRegistry;

public class CommonMod {

    public static final String MOD_ID = "azexamples";

    public static final String IDLE_ANIMATION_NAME = "idle";

    public static final String WALK_ANIMATION_NAME = "walk";

    public static final String SPAWN_ANIMATION_NAME = "spawn";

    public static final String DEATH_ANIMATION_NAME = "death";

    public static final String RUN_ANIMATION_NAME = "run";

    public static final String MELEE_ANIMATION_NAME = "axe_attack";

    private CommonMod() {}

    public static ResourceLocation modResource(String name) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, name);
    }

    public static void initRegistries() {
        BlockRegistry.initialize();
        EntityRegistry.initialize();
        CreativeTabRegistry.initialize();
    }
}

package mod.azure.azexamples;

import mod.azure.azurelib.animation.cache.AzIdentityRegistry;
import mod.azure.azurelib.util.ResourceLocation;

import mod.azure.azexamples.entities.manul.ManulAnimationDispatcher;
import mod.azure.azexamples.platform.ExMc;
import mod.azure.azexamples.registry.BlockRegistry;
import mod.azure.azexamples.registry.ItemRegistry;

public final class CommonMod {

    private CommonMod() {}

    public static ResourceLocation modResource(String name) {
        return new ResourceLocation(CommonStrings.MOD_ID, name);
    }

    public static void init() {
        ManulAnimationDispatcher.init();
        AzIdentityRegistry.register(
            ItemRegistry.PEACEMAKER,
            ItemRegistry.PISTOL,
            ItemRegistry.DOOMICORN_HELMET,
            ItemRegistry.DOOMICORN_CHESTPLATE,
            ItemRegistry.DOOMICORN_LEGGINGS,
            ItemRegistry.DOOMICORN_BOOTS,
            BlockRegistry.stargateItem(),
            ExMc.diamondSword(),
            ExMc.diamondHelmet(),
            ExMc.diamondChestplate(),
            ExMc.diamondLeggings(),
            ExMc.diamondBoots()
        );
    }
}

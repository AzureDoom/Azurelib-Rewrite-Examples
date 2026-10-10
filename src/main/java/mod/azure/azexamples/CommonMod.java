package mod.azure.azexamples;

import net.minecraft.init.Items;
import net.minecraft.util.ResourceLocation;

import mod.azure.azurelib.animation.cache.AzIdentityRegistry;

import mod.azure.azexamples.entities.manul.ManulAnimationDispatcher;
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
            BlockRegistry.STARGATE_ITEM,
            Items.DIAMOND_SWORD,
            Items.DIAMOND_HELMET,
            Items.DIAMOND_CHESTPLATE,
            Items.DIAMOND_LEGGINGS,
            Items.DIAMOND_BOOTS
        );
    }
}

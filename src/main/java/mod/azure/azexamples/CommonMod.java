package mod.azure.azexamples;

import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraft.init.Items;
import net.minecraft.util.ResourceLocation;

import mod.azure.azurelib.animation.cache.AzIdentityRegistry;

import mod.azure.azexamples.entities.manul.ManulAnimationDispatcher;
import mod.azure.azexamples.events.DiamondArmorEvents;
import mod.azure.azexamples.registry.BlockRegistry;
import mod.azure.azexamples.registry.EntityRegistry;
import mod.azure.azexamples.registry.ItemRegistry;

public final class CommonMod {

    private CommonMod() {}

    public static ResourceLocation modResource(String name) {
        return new ResourceLocation(CommonStrings.MOD_ID, name);
    }

    /**
     * 1.7.10 has no registry events; blocks, items, tile entities and entities are registered during pre-init.
     */
    public static void preInit() {
        BlockRegistry.register();
        ItemRegistry.register();
        EntityRegistry.register();
    }

    /**
     * Common setup. Item identities are registered on both sides so server-created stacks of animated items get an
     * AzureLib ID too.
     */
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
            Items.diamond_sword,
            Items.diamond_helmet,
            Items.diamond_chestplate,
            Items.diamond_leggings,
            Items.diamond_boots
        );
        // 1.7.10 fires tick events on FML's bus.
        FMLCommonHandler.instance().bus().register(new DiamondArmorEvents());
    }
}

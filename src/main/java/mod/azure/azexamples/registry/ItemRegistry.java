package mod.azure.azexamples.registry;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;

import mod.azure.azexamples.CommonStrings;
import mod.azure.azexamples.items.PistolItem;
import mod.azure.azexamples.items.armors.DoomicornArmor;
import mod.azure.azexamples.items.gunwitharm.GunWithArmItem;
import mod.azure.azexamples.items.spawnegg.ExampleSpawnEggItem;

public final class ItemRegistry {

    public static final Item PISTOL = new PistolItem();

    public static final Item PEACEMAKER = new GunWithArmItem();

    public static final Item DOOMICORN_HELMET = new DoomicornArmor(0);

    public static final Item DOOMICORN_CHESTPLATE = new DoomicornArmor(1);

    public static final Item DOOMICORN_LEGGINGS = new DoomicornArmor(2);

    public static final Item DOOMICORN_BOOTS = new DoomicornArmor(3);

    public static final Item SPAWN_EGG = new ExampleSpawnEggItem();

    private ItemRegistry() {}

    public static void register() {
        register(PISTOL, "pistol", null);
        register(PEACEMAKER, "peacemaker", null);
        register(DOOMICORN_HELMET, "doomicorn_helmet", "doomicorn_helmet");
        register(DOOMICORN_CHESTPLATE, "doomicorn_chestplate", "doomicorn_chestplate");
        register(DOOMICORN_LEGGINGS, "doomicorn_leggings", "doomicorn_leggings");
        register(DOOMICORN_BOOTS, "doomicorn_boots", "doomicorn_boots");
        register(SPAWN_EGG, "spawn_egg", null);
    }

    /**
     * @param icon icon texture under {@code textures/items}, or {@code null} for items AzureLib renders everywhere
     */
    private static void register(Item item, String name, String icon) {
        item.setUnlocalizedName(CommonStrings.MOD_ID + "." + name);
        item.setCreativeTab(AzExamplesTab.TAB);

        if (icon != null) {
            item.setTextureName(CommonStrings.MOD_ID + ":" + icon);
        }

        GameRegistry.registerItem(item, name);
    }
}

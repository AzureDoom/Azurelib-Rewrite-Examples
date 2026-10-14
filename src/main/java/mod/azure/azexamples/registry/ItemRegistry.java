package mod.azure.azexamples.registry;

import net.minecraft.item.Item;

import java.util.LinkedHashMap;
import java.util.Map;

import mod.azure.azexamples.blocks.StargateItem;
import mod.azure.azexamples.items.PistolItem;
import mod.azure.azexamples.items.armors.DoomicornArmor;
import mod.azure.azexamples.items.gunwitharm.GunWithArmItem;
import mod.azure.azexamples.items.spawnegg.ExampleSpawnEggItem;

/**
 * The examples' items. Created in StationAPI's {@code ItemRegistryEvent} (see {@link ExamplesEvents}), which hands out
 * numeric item IDs, so the fields are only set from then on.
 */
public final class ItemRegistry {

    public static Item PISTOL;

    public static Item PEACEMAKER;

    public static Item STARGATE;

    public static Item DOOMICORN_HELMET;

    public static Item DOOMICORN_CHESTPLATE;

    public static Item DOOMICORN_LEGGINGS;

    public static Item DOOMICORN_BOOTS;

    public static final Map<String, Item> SPAWN_EGGS = new LinkedHashMap<>();

    private ItemRegistry() {}

    public static void register() {
        PISTOL = new PistolItem(ExamplesEvents.id("pistol"));
        PEACEMAKER = new GunWithArmItem(ExamplesEvents.id("peacemaker"));
        STARGATE = new StargateItem(ExamplesEvents.id("stargate_item"));
        // Beta armor slots: 0 helmet, 1 chestplate, 2 leggings, 3 boots.
        DOOMICORN_HELMET = new DoomicornArmor(ExamplesEvents.id("doomicorn_helmet"), 0);
        DOOMICORN_CHESTPLATE = new DoomicornArmor(ExamplesEvents.id("doomicorn_chestplate"), 1);
        DOOMICORN_LEGGINGS = new DoomicornArmor(ExamplesEvents.id("doomicorn_leggings"), 2);
        DOOMICORN_BOOTS = new DoomicornArmor(ExamplesEvents.id("doomicorn_boots"), 3);
        for (EntityRegistry.Entry entry : EntityRegistry.entries()) {
            String name = entry.name + "_spawn_egg";
            Item egg = new ExampleSpawnEggItem(ExamplesEvents.id(name), entry.type);
            ExamplesEvents.name(egg, name);
            SPAWN_EGGS.put(entry.name, egg);
        }

        ExamplesEvents.name(PISTOL, "pistol");
        ExamplesEvents.name(PEACEMAKER, "peacemaker");
        ExamplesEvents.name(STARGATE, "stargate");
        ExamplesEvents.name(DOOMICORN_HELMET, "doomicorn_helmet");
        ExamplesEvents.name(DOOMICORN_CHESTPLATE, "doomicorn_chestplate");
        ExamplesEvents.name(DOOMICORN_LEGGINGS, "doomicorn_leggings");
        ExamplesEvents.name(DOOMICORN_BOOTS, "doomicorn_boots");
    }
}

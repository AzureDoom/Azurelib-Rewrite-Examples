package mod.azure.azexamples.registry;

import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;

import java.util.function.Supplier;

import mod.azure.azexamples.items.PistolItem;
import mod.azure.azexamples.items.armors.DoomicornArmor;
import mod.azure.azexamples.services.AzExampleServices;

public class ItemRegistry {

    private ItemRegistry() {}

    public static final Supplier<Item> PISTOL = registerItem(
        "pistol",
        PistolItem::new
    );

    public static final Supplier<Item> DOOMICORN_HELMET = registerItem(
        "doomicorn_helmet",
        () -> new DoomicornArmor(ArmorItem.Type.HELMET)
    );

    public static final Supplier<Item> DOOMICORN_CHESTPLATE = registerItem(
        "doomicorn_chestplate",
        () -> new DoomicornArmor(ArmorItem.Type.CHESTPLATE)
    );

    public static final Supplier<Item> DOOMICORN_LEGGINGS = registerItem(
        "doomicorn_leggings",
        () -> new DoomicornArmor(ArmorItem.Type.LEGGINGS)
    );

    public static final Supplier<Item> DOOMICORN_BOOTS = registerItem(
        "doomicorn_boots",
        () -> new DoomicornArmor(ArmorItem.Type.BOOTS)
    );

    public static final Supplier<SpawnEggItem> MARAUDER_SPAWN_EGG = registerItem(
        "marauder_spawn_egg",
        AzExampleServices.COMMON_REGISTRY.makeSpawnEggFor(
            EntityRegistry.MARAUDER,
            0xe9e2ed,
            0x574f44,
            new Item.Properties()
        )
    );

    public static final Supplier<SpawnEggItem> DOOMHUNTER_SPAWN_EGG = registerItem(
        "doomhunter_spawn_egg",
        AzExampleServices.COMMON_REGISTRY.makeSpawnEggFor(
            EntityRegistry.DOOMHUNTER,
            0x5a575a,
            0x86472e,
            new Item.Properties()
        )
    );

    /**
     * Registers a new Item.
     *
     * @param itemName The name of the item.
     * @param item     A supplier for the item.
     * @param <T>      The type of the item.
     * @return A supplier for the registered item.
     */
    static <T extends Item> Supplier<T> registerItem(String itemName, Supplier<T> item) {
        return AzExampleServices.COMMON_REGISTRY.registerItem(itemName, item);
    }
}

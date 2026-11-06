package mod.azure.azexamples.registry;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.equipment.ArmorMaterials;
import net.minecraft.world.item.equipment.ArmorType;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import java.util.stream.IntStream;

import mod.azure.azexamples.entities.meshtest.MeshTestVariant;
import mod.azure.azexamples.items.PistolItem;
import mod.azure.azexamples.items.armors.DoomicornArmor;
import mod.azure.azexamples.items.gunwitharm.GunWithArmItem;
import mod.azure.azexamples.services.AzExampleServices;

/**
 * The ItemRegistry class is responsible for managing the registration of items within the mod. It contains predefined
 * static fields for various item types and provides a utility method to register custom items to the game's registry.
 * This class is intended to centralize and simplify item registration.
 */
@SuppressWarnings("unused")
public class ItemRegistry {

    private ItemRegistry() {}

    public static final Supplier<Item> PISTOL = registerItem(
        "pistol",
        PistolItem::new,
        properties -> properties.stacksTo(1)
    );

    public static final Supplier<Item> PEACEMAKER = registerItem(
        "peacemaker",
        GunWithArmItem::new,
        properties -> properties.stacksTo(1)
    );

    public static final Supplier<Item> DOOMICORN_HELMET = registerItem(
        "doomicorn_helmet",
        DoomicornArmor::new,
        properties -> properties
            .humanoidArmor(ArmorMaterials.NETHERITE, ArmorType.HELMET)
            .stacksTo(1)
    );

    public static final Supplier<Item> DOOMICORN_CHESTPLATE = registerItem(
        "doomicorn_chestplate",
        DoomicornArmor::new,
        properties -> properties
            .humanoidArmor(ArmorMaterials.NETHERITE, ArmorType.CHESTPLATE)
            .stacksTo(1)
    );

    public static final Supplier<Item> DOOMICORN_LEGGINGS = registerItem(
        "doomicorn_leggings",
        DoomicornArmor::new,
        properties -> properties
            .humanoidArmor(ArmorMaterials.NETHERITE, ArmorType.LEGGINGS)
            .stacksTo(1)
    );

    public static final Supplier<Item> DOOMICORN_BOOTS = registerItem(
        "doomicorn_boots",
        DoomicornArmor::new,
        properties -> properties
            .humanoidArmor(ArmorMaterials.NETHERITE, ArmorType.BOOTS)
            .stacksTo(1)
    );

    public static final Supplier<SpawnEggItem> MARAUDER_SPAWN_EGG =
        AzExampleServices.COMMON_REGISTRY.registerSpawnEgg(
            "marauder_spawn_egg",
            EntityRegistry.MARAUDER
        );

    public static final Supplier<SpawnEggItem> DOOMHUNTER_SPAWN_EGG =
        AzExampleServices.COMMON_REGISTRY.registerSpawnEgg(
            "doomhunter_spawn_egg",
            EntityRegistry.DOOMHUNTER
        );

    public static final Supplier<SpawnEggItem> MANUL_SPAWN_EGG =
        AzExampleServices.COMMON_REGISTRY.registerSpawnEgg(
            "manul_spawn_egg",
            EntityRegistry.MANUL
        );

    public static final Supplier<SpawnEggItem> JURAVENATOR_SPAWN_EGG =
        AzExampleServices.COMMON_REGISTRY.registerSpawnEgg(
            "juravenator_spawn_egg",
            EntityRegistry.JURAVENATOR
        );

    public static final Supplier<SpawnEggItem> MARINE_SPAWN_EGG =
        AzExampleServices.COMMON_REGISTRY.registerSpawnEgg(
            "marine_spawn_egg",
            EntityRegistry.MARINE
        );

    public static final Supplier<SpawnEggItem> GREMLIN_SPAWN_EGG =
        AzExampleServices.COMMON_REGISTRY.registerSpawnEgg(
            "gremlin_spawn_egg",
            EntityRegistry.GREMLIN
        );

    public static final Supplier<SpawnEggItem> OPTIMUSPRIME_SPAWN_EGG =
        AzExampleServices.COMMON_REGISTRY.registerSpawnEgg(
            "optimusprime_spawn_egg",
            EntityRegistry.OPTIMUSPRIME
        );

    /**
     * Spawn eggs for {@link EntityRegistry#MESH_TESTS}, in the same order, named {@code mesh_test_<variant>_spawn_egg}.
     */
    public static final List<Supplier<SpawnEggItem>> MESH_TEST_SPAWN_EGGS = IntStream
        .range(0, EntityRegistry.MESH_TESTS.size())
        .mapToObj(
            index -> AzExampleServices.COMMON_REGISTRY.registerSpawnEgg(
                "mesh_test_" + MeshTestVariant.values()[index].name().toLowerCase() + "_spawn_egg",
                EntityRegistry.MESH_TESTS.get(index)
            )
        )
        .toList();

    /**
     * Registers a new Item.
     *
     * @param itemName   The name of the item.
     * @param factory    The properties factory of the item.
     * @param properties The main properties of the item.
     * @return A supplier for the registered item.
     */
    public static <T extends Item> Supplier<T> registerItem(
        String itemName,
        Function<Item.Properties, T> factory,
        UnaryOperator<Item.Properties> properties
    ) {
        return AzExampleServices.COMMON_REGISTRY.registerItem(
            itemName,
            factory,
            properties
        );
    }

    public static void initialize() {}
}

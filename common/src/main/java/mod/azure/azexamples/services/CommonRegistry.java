package mod.azure.azexamples.services;

import net.minecraft.core.Registry;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;

import java.util.function.Supplier;

/**
 * The CommonRegistry interface provides a set of methods for registering various types of objects and creating specific
 * tools used in mod development, such as blocks, entities, items, sounds, and creative mode tabs. This interface is
 * designed to be implemented differently based on the platform to handle registration in a unified way.
 */
public interface CommonRegistry {

    <T> Supplier<T> register(
        Registry<? super T> registry,
        String registryName,
        Supplier<? extends T> supplier
    );

    <E extends Mob> Supplier<SpawnEggItem> makeSpawnEggFor(
        Supplier<EntityType<E>> entityType,
        int primaryEggColour,
        int secondaryEggColour,
        Item.Properties itemProperties
    );

    CreativeModeTab.Builder newCreativeTabBuilder();
}

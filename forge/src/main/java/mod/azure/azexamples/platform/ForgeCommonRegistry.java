package mod.azure.azexamples.platform;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.common.ForgeSpawnEggItem;

import java.util.function.Supplier;

import mod.azure.azexamples.ForgeMod;
import mod.azure.azexamples.services.CommonRegistry;

/**
 * The NeoForgeCommonRegistry class provides a concrete implementation of the CommonRegistry interface for use with
 * NeoForge. It facilitates centralized registration of blocks, items, entities, block entities, and creative mode tabs.
 * This implementation leverages the ForgeMod's deferred registers to manage the creation and registration of these
 * objects.
 */
public class ForgeCommonRegistry implements CommonRegistry {

    @Override
    public <T> Supplier<T> register(Registry<? super T> registry, String registryName, Supplier<? extends T> supplier) {
        if (registry == BuiltInRegistries.BLOCK) {
            return (Supplier<T>) ForgeMod.blockDeferredRegister.register(registryName, (Supplier<Block>) supplier);
        } else if (registry == BuiltInRegistries.CREATIVE_MODE_TAB) {
            return (Supplier<T>) ForgeMod.creativeModeTabDeferredRegister.register(
                registryName,
                (Supplier<CreativeModeTab>) supplier
            );
        } else if (registry == BuiltInRegistries.ITEM) {
            return (Supplier<T>) ForgeMod.itemDeferredRegister.register(registryName, (Supplier<Item>) supplier);
        } else if (registry == BuiltInRegistries.BLOCK_ENTITY_TYPE) {
            return (Supplier<T>) ForgeMod.blockEntityTypeDeferredRegister.register(
                registryName,
                (Supplier<BlockEntityType<?>>) supplier
            );
        } else if (registry == BuiltInRegistries.ENTITY_TYPE) {
            return (Supplier<T>) ForgeMod.entityTypeDeferredRegister.register(
                registryName,
                (Supplier<EntityType<?>>) supplier
            );
        } else if (registry == BuiltInRegistries.SOUND_EVENT) {
            return (Supplier<T>) ForgeMod.soundEventDeferredRegister.register(
                registryName,
                (Supplier<SoundEvent>) supplier
            );
        }

        throw new IllegalArgumentException(
            "Received registration attempt for an unhandled registry. Registry: " + registry
        );
    }

    @Override
    public <E extends Mob> Supplier<SpawnEggItem> makeSpawnEggFor(
        Supplier<EntityType<E>> entityType,
        int primaryEggColour,
        int secondaryEggColour,
        Item.Properties itemProperties
    ) {
        return () -> new ForgeSpawnEggItem(entityType, primaryEggColour, secondaryEggColour, itemProperties);
    }

    @Override
    public CreativeModeTab.Builder newCreativeTabBuilder() {
        return CreativeModeTab.builder();
    }
}

package mod.azure.azexamples.platform;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;

import java.util.function.Supplier;

import mod.azure.azexamples.NeoForgeMod;
import mod.azure.azexamples.services.CommonRegistry;

/**
 * The NeoForgeCommonRegistry class provides a concrete implementation of the CommonRegistry interface
 * for use with NeoForge. It facilitates centralized registration of blocks, items, entities, block entities,
 * and creative mode tabs. This implementation leverages the NeoForgeMod's deferred registers
 * to manage the creation and registration of these objects.
 */
public class NeoForgeCommonRegistry implements CommonRegistry {

    @Override
    public <T extends BlockEntity> Supplier<BlockEntityType<T>> registerBlockEntity(
        String blockEntityName,
        Supplier<BlockEntityType<T>> blockEntityType
    ) {
        return NeoForgeMod.blockEntityTypeDeferredRegister.register(blockEntityName, blockEntityType);
    }

    @Override
    public <T extends Block> Supplier<T> registerBlock(String blockName, Supplier<T> block) {
        return NeoForgeMod.blockDeferredRegister.register(blockName, block);
    }

    @Override
    public <T extends Entity> Supplier<EntityType<T>> registerEntity(
        String entityName,
        Supplier<EntityType<T>> entity
    ) {
        return NeoForgeMod.entityTypeDeferredRegister.register(entityName, entity);
    }

    @Override
    public <T extends Item> Supplier<T> registerItem(String itemName, Supplier<T> item) {
        return NeoForgeMod.itemDeferredRegister.register(itemName, item);
    }

    @Override
    public <T extends CreativeModeTab> Supplier<T> registerCreativeModeTab(String tabName, Supplier<T> tab) {
        return NeoForgeMod.creativeModeTabDeferredRegister.register(tabName, tab);
    }

    @Override
    public <E extends Mob> Supplier<SpawnEggItem> makeSpawnEggFor(
        Supplier<EntityType<E>> entityType,
        int primaryEggColour,
        int secondaryEggColour,
        Item.Properties itemProperties
    ) {
        return () -> new DeferredSpawnEggItem(entityType, primaryEggColour, secondaryEggColour, itemProperties);
    }

    @Override
    public CreativeModeTab.Builder newCreativeTabBuilder() {
        return CreativeModeTab.builder();
    }
}

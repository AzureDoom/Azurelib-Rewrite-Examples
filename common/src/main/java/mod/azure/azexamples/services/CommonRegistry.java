package mod.azure.azexamples.services;

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

import java.util.function.Supplier;

public interface CommonRegistry {

    <T extends BlockEntity> Supplier<BlockEntityType<T>> registerBlockEntity(
        String blockEntityName,
        Supplier<BlockEntityType<T>> blockEntityType
    );

    <T extends Block> Supplier<T> registerBlock(String blockName, Supplier<T> block);

    <T extends Entity> Supplier<EntityType<T>> registerEntity(
        String entityName,
        Supplier<EntityType<T>> entity
    );

    <T extends Item> Supplier<T> registerItem(String itemName, Supplier<T> item);

    <T extends SoundEvent> Supplier<T> registerSound(String soundName, Supplier<T> sound);

    <T extends CreativeModeTab> Supplier<T> registerCreativeModeTab(String tabName, Supplier<T> tab);

    <E extends Mob> Supplier<SpawnEggItem> makeSpawnEggFor(
        Supplier<EntityType<E>> entityType,
        int primaryEggColour,
        int secondaryEggColour,
        Item.Properties itemProperties
    );

    CreativeModeTab.Builder newCreativeTabBuilder();
}

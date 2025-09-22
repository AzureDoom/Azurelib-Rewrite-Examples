package mod.azure.azexamples.services;

import net.minecraft.world.level.block.entity.BlockEntityType;

import mod.azure.azexamples.blocks.blockentity.StargateBlockEntity;

/**
 * The CommonRegistry interface provides a set of methods for registering various types of objects and creating specific
 * tools used in mod development, such as blocks, entities, items, sounds, and creative mode tabs. This interface is
 * designed to be implemented differently based on the platform to handle registration in a unified way.
 */
public interface CommonRegistry {

    BlockEntityType<StargateBlockEntity> stargateBlockEntity();
}

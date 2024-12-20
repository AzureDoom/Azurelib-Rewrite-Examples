package mod.azure.azexamples.registry;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.function.Supplier;

import mod.azure.azexamples.blocks.blockentity.StargateBlockEntity;
import mod.azure.azexamples.entities.doomhunter.DoomHunterEntity;
import mod.azure.azexamples.entities.marauder.MarauderEntity;
import mod.azure.azexamples.services.AzExampleServices;

public class EntityRegistry {

    public static final Supplier<BlockEntityType<StargateBlockEntity>> STARGATE_BLOCK_ENTITY = registerBlockEntity(
        "stargate_block_entity",
        () -> BlockEntityType.Builder.of(
            StargateBlockEntity::new,
            BlockRegistry.STARGATE.get()
        ).build(null)
    );

    public static final Supplier<EntityType<MarauderEntity>> MARAUDER = registerEntity(
        "marauder",
        MarauderEntity::new,
        MobCategory.MONSTER,
        1.5f,
        2.6f
    );

    public static final Supplier<EntityType<DoomHunterEntity>> DOOMHUNTER = registerEntity(
        "doomhunter",
        DoomHunterEntity::new,
        MobCategory.MONSTER,
        3.0f,
        7.0f
    );

    /**
     * Registers a new Block Entity.
     *
     * @param blockEntityName The name of the Block Entity.
     * @param blockEntity     A supplier for the block entity type.
     * @param <T>             The type of the block entity.
     * @return A supplier for the registered block entity type.
     */
    static <T extends BlockEntity> Supplier<BlockEntityType<T>> registerBlockEntity(
        String blockEntityName,
        Supplier<BlockEntityType<T>> blockEntity
    ) {
        return AzExampleServices.COMMON_REGISTRY.registerBlockEntity(blockEntityName, blockEntity);
    }

    /**
     * Registers a new entity type.
     *
     * @param entityName The name of the entity.
     * @param entity     The factory method for creating instances of the entity.
     * @param width      The width of the entity.
     * @param height     The height of the entity.
     * @param <T>        The type of the entity.
     * @return A supplier for the registered entity type.
     */
    static <T extends Entity> Supplier<EntityType<T>> registerEntity(
        String entityName,
        EntityType.EntityFactory<T> entity,
        MobCategory mobCategory,
        float width,
        float height
    ) {
        return AzExampleServices.COMMON_REGISTRY.registerEntity(
            entityName,
            () -> EntityType.Builder.of(entity, mobCategory).sized(width, height).build(entityName)
        );
    }

    public static void initialize() {}
}

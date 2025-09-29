package mod.azure.azexamples;

import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;

import mod.azure.azexamples.blocks.StargateBlock;
import mod.azure.azexamples.blocks.StargateBlockItem;
import mod.azure.azexamples.blocks.blockentity.StargateBlockEntity;
import mod.azure.azexamples.entities.SilencedEntityTypeBuilder;
import mod.azure.azexamples.entities.doomhunter.DoomHunterEntity;
import mod.azure.azexamples.entities.juravenator.JuravenatorEntity;
import mod.azure.azexamples.entities.manul.ManulEntity;
import mod.azure.azexamples.entities.marauder.MarauderEntity;
import mod.azure.azexamples.entities.marine.MarineEntity;
import mod.azure.azexamples.items.PistolItem;
import mod.azure.azexamples.items.armors.DoomicornArmor;

public class ExampleRegistry {

    private ExampleRegistry() {}

    public static final Item PISTOL = registerItem(
        "pistol",
        new PistolItem(new Item.Properties().tab(ExampleRegistry.EXAMPLEMOD_TAB).stacksTo(1))
    );

    public static final Item DOOMICORN_HELMET = registerItem(
        "doomicorn_helmet",
        new DoomicornArmor(EquipmentSlot.HEAD, ExampleRegistry.EXAMPLEMOD_TAB)
    );

    public static final Item DOOMICORN_CHESTPLATE = registerItem(
        "doomicorn_chestplate",
        new DoomicornArmor(EquipmentSlot.CHEST, ExampleRegistry.EXAMPLEMOD_TAB)
    );

    public static final Item DOOMICORN_LEGGINGS = registerItem(
        "doomicorn_leggings",
        new DoomicornArmor(EquipmentSlot.LEGS, ExampleRegistry.EXAMPLEMOD_TAB)
    );

    public static final Item DOOMICORN_BOOTS = registerItem(
        "doomicorn_boots",
        new DoomicornArmor(EquipmentSlot.FEET, ExampleRegistry.EXAMPLEMOD_TAB)
    );

    public static final StargateBlock STARGATE = registerBlock(
        "stargate",
        new StargateBlock(
            BlockBehaviour.Properties.of(Material.HEAVY_METAL)
                .sound(SoundType.DRIPSTONE_BLOCK)
                .strength(5.0f, 8.0f)
                .noOcclusion()
        )
    );

    public static final BlockItem STARGATE_ITEM = registerItem(
        "stargate",
        new StargateBlockItem(STARGATE, ExampleRegistry.EXAMPLEMOD_TAB)
    );

    public static final BlockEntityType<StargateBlockEntity> STARGATE_BLOCK_ENTITY = registerBlockEntity(
        "stargate_block_entity",
        BlockEntityType.Builder.of(
            StargateBlockEntity::new,
            ExampleRegistry.STARGATE
        ).build(null)
    );

    public static final EntityType<MarauderEntity> MARAUDER = registerEntity(
        "marauder",
        MarauderEntity::new,
        MobCategory.MONSTER,
        1.5f,
        2.6f
    );

    public static final EntityType<ManulEntity> MANUL = registerEntity(
        "manul",
        ManulEntity::new,
        MobCategory.CREATURE,
        1.2f,
        1.1f
    );

    public static final EntityType<DoomHunterEntity> DOOMHUNTER = registerEntity(
        "doomhunter",
        DoomHunterEntity::new,
        MobCategory.MONSTER,
        3.0f,
        7.0f
    );

    public static final EntityType<JuravenatorEntity> JURAVENATOR = registerEntity(
        "juravenator",
        JuravenatorEntity::new,
        MobCategory.MONSTER,
        3.0f,
        7.0f
    );

    public static final EntityType<MarineEntity> MARINE = registerEntity(
        "marine",
        MarineEntity::new,
        MobCategory.WATER_CREATURE,
        1.5f,
        2.6f
    );

    public static final SpawnEggItem MARAUDER_SPAWN_EGG = registerItem(
        "marauder_spawn_egg",
        new SpawnEggItem(
            ExampleRegistry.MARAUDER,
            0xe9e2ed,
            0x574f44,
            new Item.Properties().tab(ExampleRegistry.EXAMPLEMOD_TAB)
        )
    );

    public static final SpawnEggItem DOOMHUNTER_SPAWN_EGG = registerItem(
        "doomhunter_spawn_egg",
        new SpawnEggItem(
            ExampleRegistry.DOOMHUNTER,
            0x5a575a,
            0x86472e,
            new Item.Properties().tab(ExampleRegistry.EXAMPLEMOD_TAB)
        )
    );

    public static final SpawnEggItem MANUL_SPAWN_EGG = registerItem(
        "manul_spawn_egg",
        new SpawnEggItem(
            ExampleRegistry.MANUL,
            0xc38160,
            0x3d362e,
            new Item.Properties().tab(ExampleRegistry.EXAMPLEMOD_TAB)
        )
    );

    public static final SpawnEggItem JURAVENATOR_SPAWN_EGG = registerItem(
        "juravenator_spawn_egg",
        new SpawnEggItem(
            ExampleRegistry.JURAVENATOR,
            0xc09e58,
            0x574028,
            new Item.Properties().tab(ExampleRegistry.EXAMPLEMOD_TAB)
        )
    );

    public static final SpawnEggItem MARINE_SPAWN_EGG = registerItem(
        "marine_spawn_egg",
        new SpawnEggItem(
            ExampleRegistry.MARINE,
            0xc09e58,
            0x574028,
            new Item.Properties().tab(ExampleRegistry.EXAMPLEMOD_TAB)
        )
    );

    static <T extends Entity> SilencedEntityTypeBuilder create(
        EntityType.EntityFactory<T> entity,
        MobCategory mobCategory,
        float width,
        float height
    ) {
        return (SilencedEntityTypeBuilder) EntityType.Builder.of(entity, mobCategory).sized(width, height);
    }

    public static <T extends Item> T registerItem(String name, T item) {
        return Registry.register(Registry.ITEM, CommonMod.modResource(name), item);
    }

    public static <T extends Block> T registerBlock(String blockName, T block) {
        return Registry.register(Registry.BLOCK, blockName, block);
    }

    public static <T extends Entity> EntityType<T> registerEntity(
        String entityName,
        EntityType.EntityFactory<T> entity,
        MobCategory mobCategory,
        float width,
        float height
    ) {
        return Registry.register(
            Registry.ENTITY_TYPE,
            CommonMod.modResource(entityName),
            create(entity, mobCategory, width, height).buildWithoutDataFixerCheck()
        );
    }

    public static <T extends BlockEntity> BlockEntityType<T> registerBlockEntity(
        String blockEntityName,
        BlockEntityType<T> blockEntity
    ) {
        return Registry.register(
            Registry.BLOCK_ENTITY_TYPE,
            CommonMod.modResource(blockEntityName),
            blockEntity
        );
    }

    public static final CreativeModeTab EXAMPLEMOD_TAB = FabricItemGroupBuilder.build(
        new ResourceLocation(CommonStrings.MOD_ID, "examplemod_items"),
        () -> new ItemStack(ExampleRegistry.STARGATE_ITEM)
    );

    public static void initialize() {}
}

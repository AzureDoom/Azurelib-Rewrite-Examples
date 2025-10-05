package mod.azure.azexamples;

import mod.azure.azexamples.items.gunwitharm.GunWithArmItem;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import mod.azure.azexamples.blocks.StargateBlock;
import mod.azure.azexamples.blocks.StargateBlockItem;
import mod.azure.azexamples.blocks.blockentity.StargateBlockEntity;
import mod.azure.azexamples.entities.SilencedEntityTypeBuilder;
import mod.azure.azexamples.entities.doomhunter.DoomHunterEntity;
import mod.azure.azexamples.entities.gremlin.GremlinEntity;
import mod.azure.azexamples.entities.juravenator.JuravenatorEntity;
import mod.azure.azexamples.entities.manul.ManulEntity;
import mod.azure.azexamples.entities.marauder.MarauderEntity;
import mod.azure.azexamples.entities.marine.MarineEntity;
import mod.azure.azexamples.items.PistolItem;
import mod.azure.azexamples.items.armors.DoomicornArmor;

public class ExampleRegistry {

    public static DeferredRegister<BlockEntityType<?>> blockEntityTypeDeferredRegister = DeferredRegister.create(
        ForgeRegistries.BLOCK_ENTITIES,
        CommonStrings.MOD_ID
    );

    public static DeferredRegister<Block> blockDeferredRegister = DeferredRegister.create(
        ForgeRegistries.BLOCKS,
        CommonStrings.MOD_ID
    );

    public static DeferredRegister<EntityType<?>> entityTypeDeferredRegister = DeferredRegister.create(
        ForgeRegistries.ENTITIES,
        CommonStrings.MOD_ID
    );

    public static DeferredRegister<Item> itemDeferredRegister = DeferredRegister.create(
        ForgeRegistries.ITEMS,
        CommonStrings.MOD_ID
    );

	public static DeferredRegister<SoundEvent> soundDefferredRegister = DeferredRegister.create(
		ForgeRegistries.SOUND_EVENTS,
		CommonStrings.MOD_ID
	);

	public static final RegistryObject<SoundEvent> SHOOT_GUN = soundDefferredRegister.register(
		"vigilance_fire",
		() -> new SoundEvent(CommonMod.modResource("vigilance_fire"))
	);

    public static final RegistryObject<Item> PISTOL = itemDeferredRegister.register(
        "pistol",
	    PistolItem::new
    );

	public static final RegistryObject<Item> PEACEMAKER = itemDeferredRegister.register(
		"peacemaker",
		GunWithArmItem::new
	);

    public static final RegistryObject<Item> DOOMICORN_HELMET = itemDeferredRegister.register(
        "doomicorn_helmet",
        () -> new DoomicornArmor(EquipmentSlot.HEAD)
    );

    public static final RegistryObject<Item> DOOMICORN_CHESTPLATE = itemDeferredRegister.register(
        "doomicorn_chestplate",
        () -> new DoomicornArmor(EquipmentSlot.CHEST)
    );

    public static final RegistryObject<Item> DOOMICORN_LEGGINGS = itemDeferredRegister.register(
        "doomicorn_leggings",
        () -> new DoomicornArmor(EquipmentSlot.LEGS)
    );

    public static final RegistryObject<Item> DOOMICORN_BOOTS = itemDeferredRegister.register(
        "doomicorn_boots",
        () -> new DoomicornArmor(EquipmentSlot.FEET)
    );

    public static final RegistryObject<SpawnEggItem> MARAUDER_SPAWN_EGG = itemDeferredRegister.register(
        "marauder_spawn_egg",
        () -> new ForgeSpawnEggItem(
            ExampleRegistry.MARAUDER,
            0xe9e2ed,
            0x574f44,
            new Item.Properties().tab(ForgeMod.EXAMPLEMOD_TAB)
        )
    );

    public static final RegistryObject<SpawnEggItem> DOOMHUNTER_SPAWN_EGG = itemDeferredRegister.register(
        "doomhunter_spawn_egg",
        () -> new ForgeSpawnEggItem(
            ExampleRegistry.DOOMHUNTER,
            0x5a575a,
            0x86472e,
            new Item.Properties().tab(ForgeMod.EXAMPLEMOD_TAB)
        )
    );

    public static final RegistryObject<SpawnEggItem> MANUL_SPAWN_EGG = itemDeferredRegister.register(
        "manul_spawn_egg",
        () -> new ForgeSpawnEggItem(
            ExampleRegistry.MANUL,
            0xc38160,
            0x3d362e,
            new Item.Properties().tab(ForgeMod.EXAMPLEMOD_TAB)
        )
    );

    public static final RegistryObject<SpawnEggItem> JURAVENATOR_SPAWN_EGG = itemDeferredRegister.register(
        "juravenator_spawn_egg",
        () -> new ForgeSpawnEggItem(
            ExampleRegistry.JURAVENATOR,
            0xc09e58,
            0x574028,
            new Item.Properties().tab(ForgeMod.EXAMPLEMOD_TAB)
        )
    );

    public static final RegistryObject<SpawnEggItem> MARINE_SPAWN_EGG = itemDeferredRegister.register(
        "marine_spawn_egg",
        () -> new ForgeSpawnEggItem(
            ExampleRegistry.MARINE,
            0xc09e58,
            0x574028,
            new Item.Properties().tab(ForgeMod.EXAMPLEMOD_TAB)
        )
    );

    public static final RegistryObject<SpawnEggItem> GREMLIN_SPAWN_EGG = itemDeferredRegister.register(
        "gremlin_spawn_egg",
        () -> new ForgeSpawnEggItem(
            ExampleRegistry.GREMLIN,
            0x424242,
            0x606060,
            new Item.Properties().tab(ForgeMod.EXAMPLEMOD_TAB)
        )
    );

    public static final RegistryObject<StargateBlock> STARGATE = blockDeferredRegister.register(
        "stargate",
        () -> new StargateBlock(
            BlockBehaviour.Properties.of(Material.HEAVY_METAL)
                .sound(SoundType.DRIPSTONE_BLOCK)
                .strength(5.0f, 8.0f)
                .noOcclusion()
        )
    );

    public static final RegistryObject<BlockItem> STARGATE_ITEM = itemDeferredRegister.register(
        "stargate",
        () -> new StargateBlockItem(STARGATE.get())
    );

    public static final RegistryObject<BlockEntityType<StargateBlockEntity>> STARGATE_BLOCK_ENTITY =
        blockEntityTypeDeferredRegister.register(
            "stargate_block_entity",
            () -> BlockEntityType.Builder.of(
                StargateBlockEntity::new,
                ExampleRegistry.STARGATE.get()
            ).build(null)
        );

    public static final RegistryObject<EntityType<MarauderEntity>> MARAUDER = entityTypeDeferredRegister.register(
        "marauder",
        () -> create(
            MarauderEntity::new,
            MobCategory.MONSTER,
            1.5f,
            2.6f
        ).buildWithoutDataFixerCheck()
    );

    public static final RegistryObject<EntityType<ManulEntity>> MANUL = entityTypeDeferredRegister.register(
        "manul",
        () -> create(
            ManulEntity::new,
            MobCategory.CREATURE,
            1.2f,
            1.1f
        ).buildWithoutDataFixerCheck()
    );

    public static final RegistryObject<EntityType<DoomHunterEntity>> DOOMHUNTER = entityTypeDeferredRegister.register(
        "doomhunter",
        () -> create(DoomHunterEntity::new, MobCategory.MONSTER, 3.0f, 7.0f).buildWithoutDataFixerCheck()
    );

    public static final RegistryObject<EntityType<JuravenatorEntity>> JURAVENATOR = entityTypeDeferredRegister.register(
        "juravenator",
        () -> create(JuravenatorEntity::new, MobCategory.MONSTER, 3.0f, 7.0f).buildWithoutDataFixerCheck()
    );

    public static final RegistryObject<EntityType<MarineEntity>> MARINE = entityTypeDeferredRegister.register(
        "marine",
        () -> create(MarineEntity::new, MobCategory.WATER_CREATURE, 1.5f, 2.6f).buildWithoutDataFixerCheck()
    );

    public static final RegistryObject<EntityType<GremlinEntity>> GREMLIN = entityTypeDeferredRegister.register(
        "gremlin",
        () -> create(GremlinEntity::new, MobCategory.MISC, 0.6f, 1.8f).buildWithoutDataFixerCheck()
    );

    static <T extends Entity> SilencedEntityTypeBuilder create(
        EntityType.EntityFactory<T> entity,
        MobCategory mobCategory,
        float width,
        float height
    ) {
        return (SilencedEntityTypeBuilder) EntityType.Builder.of(entity, mobCategory).sized(width, height);
    }
}

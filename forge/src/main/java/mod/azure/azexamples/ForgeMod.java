package mod.azure.azexamples;

import mod.azure.azurelib.animation.cache.AzIdentityRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import mod.azure.azexamples.registry.EntityRegistry;
import mod.azure.azexamples.registry.ItemRegistry;

@Mod.EventBusSubscriber
@Mod(CommonStrings.MOD_ID)
public final class ForgeMod {

    public static DeferredRegister<BlockEntityType<?>> blockEntityTypeDeferredRegister = DeferredRegister.create(
        ForgeRegistries.BLOCK_ENTITY_TYPES,
        CommonStrings.MOD_ID
    );

    public static DeferredRegister<Block> blockDeferredRegister = DeferredRegister.create(
        ForgeRegistries.BLOCKS,
        CommonStrings.MOD_ID
    );

    public static DeferredRegister<EntityType<?>> entityTypeDeferredRegister = DeferredRegister.create(
        ForgeRegistries.ENTITY_TYPES,
        CommonStrings.MOD_ID
    );

    public static DeferredRegister<Item> itemDeferredRegister = DeferredRegister.create(
        ForgeRegistries.ITEMS,
        CommonStrings.MOD_ID
    );

    public static DeferredRegister<CreativeModeTab> creativeModeTabDeferredRegister = DeferredRegister.create(
        Registries.CREATIVE_MODE_TAB,
        CommonStrings.MOD_ID
    );

    public static DeferredRegister<SoundEvent> soundEventDeferredRegister = DeferredRegister.create(
        Registries.SOUND_EVENT,
        CommonStrings.MOD_ID
    );

    public ForgeMod(FMLJavaModLoadingContext loadingContext) {
        IEventBus modEventBus = loadingContext.getModEventBus();
        CommonMod.initRegistries();
        blockEntityTypeDeferredRegister.register(modEventBus);
        blockDeferredRegister.register(modEventBus);
        entityTypeDeferredRegister.register(modEventBus);
        itemDeferredRegister.register(modEventBus);
        creativeModeTabDeferredRegister.register(modEventBus);
        soundEventDeferredRegister.register(modEventBus);
        modEventBus.addListener(this::createEntityAttributes);
        modEventBus.addListener(this::init);
    }

    public void init(final FMLCommonSetupEvent event) {
        event.enqueueWork(
            () -> AzIdentityRegistry.register(
                ItemRegistry.PEACEMAKER.get(),
                ItemRegistry.PISTOL.get(),
                ItemRegistry.DOOMICORN_HELMET.get(),
                ItemRegistry.DOOMICORN_CHESTPLATE.get(),
                ItemRegistry.DOOMICORN_LEGGINGS.get(),
                ItemRegistry.DOOMICORN_BOOTS.get(),
                Items.NETHERITE_SWORD,
                Items.NETHERITE_HELMET,
                Items.NETHERITE_CHESTPLATE,
                Items.NETHERITE_LEGGINGS,
                Items.NETHERITE_BOOTS
            )
        );
    }

    public void createEntityAttributes(final EntityAttributeCreationEvent event) {
        event.put(EntityRegistry.DOOMHUNTER.get(), Monster.createMonsterAttributes().build());
        event.put(EntityRegistry.MARAUDER.get(), Monster.createMonsterAttributes().build());
        event.put(EntityRegistry.MANUL.get(), Mob.createMobAttributes().build());
        event.put(EntityRegistry.JURAVENATOR.get(), Mob.createMobAttributes().build());
        event.put(EntityRegistry.MARINE.get(), Mob.createMobAttributes().build());
        event.put(EntityRegistry.GREMLIN.get(), Mob.createMobAttributes().build());
    }
}

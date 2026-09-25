package mod.azure.azexamples;

import mod.azure.azurelib.animation.cache.AzIdentityRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.registries.DeferredRegister;

import mod.azure.azexamples.registry.EntityRegistry;
import mod.azure.azexamples.registry.ItemRegistry;

@Mod(CommonStrings.MOD_ID)
public final class NeoForgeMod {

    public static DeferredRegister<BlockEntityType<?>> blockEntityTypeDeferredRegister = DeferredRegister.create(
        BuiltInRegistries.BLOCK_ENTITY_TYPE,
        CommonStrings.MOD_ID
    );

    public static DeferredRegister<Block> blockDeferredRegister = DeferredRegister.createBlocks(
        CommonStrings.MOD_ID
    );

    public static DeferredRegister<EntityType<?>> entityTypeDeferredRegister = DeferredRegister.createEntities(
        CommonStrings.MOD_ID
    );

    public static final DeferredRegister.Items itemDeferredRegister =
        DeferredRegister.createItems(CommonStrings.MOD_ID);

    public static DeferredRegister<CreativeModeTab> creativeModeTabDeferredRegister = DeferredRegister.create(
        BuiltInRegistries.CREATIVE_MODE_TAB,
        CommonStrings.MOD_ID
    );

    public static DeferredRegister<SoundEvent> soundEventDeferredRegister = DeferredRegister.create(
        BuiltInRegistries.SOUND_EVENT,
        CommonStrings.MOD_ID
    );

    public NeoForgeMod(IEventBus modEventBus) {
        CommonMod.initRegistries();
        if (blockEntityTypeDeferredRegister != null)
            blockEntityTypeDeferredRegister.register(modEventBus);
        if (blockDeferredRegister != null)
            blockDeferredRegister.register(modEventBus);
        if (entityTypeDeferredRegister != null)
            entityTypeDeferredRegister.register(modEventBus);
        itemDeferredRegister.register(modEventBus);
        if (creativeModeTabDeferredRegister != null)
            creativeModeTabDeferredRegister.register(modEventBus);
        if (soundEventDeferredRegister != null)
            soundEventDeferredRegister.register(modEventBus);
        modEventBus.addListener(this::createEntityAttributes);
        modEventBus.addListener(this::registerAzIdentity);
    }

    public void registerAzIdentity(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> AzIdentityRegistry.register(
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
        ));
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

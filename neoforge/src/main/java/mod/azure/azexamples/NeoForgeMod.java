package mod.azure.azexamples;

import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.registries.DeferredRegister;

import mod.azure.azexamples.registry.EntityRegistry;

@Mod(CommonMod.MOD_ID)
public final class NeoForgeMod {

    public static DeferredRegister<BlockEntityType<?>> blockEntityTypeDeferredRegister = DeferredRegister.create(
        Registries.BLOCK_ENTITY_TYPE,
        CommonMod.MOD_ID
    );

    public static DeferredRegister<Block> blockDeferredRegister = DeferredRegister.create(
        Registries.BLOCK,
        CommonMod.MOD_ID
    );

    public static DeferredRegister<EntityType<?>> entityTypeDeferredRegister = DeferredRegister.create(
        Registries.ENTITY_TYPE,
        CommonMod.MOD_ID
    );

    public static DeferredRegister<Item> itemDeferredRegister = DeferredRegister.create(
        Registries.ITEM,
        CommonMod.MOD_ID
    );

    public static DeferredRegister<SoundEvent> soundEventDeferredRegister = DeferredRegister.create(
        Registries.SOUND_EVENT,
        CommonMod.MOD_ID
    );

    public static DeferredRegister<CreativeModeTab> creativeModeTabDeferredRegister = DeferredRegister.create(
        Registries.CREATIVE_MODE_TAB,
        CommonMod.MOD_ID
    );

    /**
     * TODO: Remove null checks once things are setup
     */
    public NeoForgeMod(IEventBus modEventBus) {
        CommonMod.initRegistries();
        blockEntityTypeDeferredRegister.register(modEventBus);
        blockDeferredRegister.register(modEventBus);
        entityTypeDeferredRegister.register(modEventBus);
        itemDeferredRegister.register(modEventBus);
        if (soundEventDeferredRegister != null)
            soundEventDeferredRegister.register(modEventBus);
        creativeModeTabDeferredRegister.register(modEventBus);
        modEventBus.addListener(this::createEntityAttributes);
    }

    public void createEntityAttributes(final EntityAttributeCreationEvent event) {
        event.put(EntityRegistry.DOOMHUNTER.get(), Monster.createMonsterAttributes().build());
        event.put(EntityRegistry.MARAUDER.get(), Monster.createMonsterAttributes().build());
    }
}

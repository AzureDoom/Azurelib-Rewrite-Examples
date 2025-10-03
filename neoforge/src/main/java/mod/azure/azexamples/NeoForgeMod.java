package mod.azure.azexamples;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
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

@Mod(CommonStrings.MOD_ID)
public final class NeoForgeMod {

    public static DeferredRegister<BlockEntityType<?>> blockEntityTypeDeferredRegister = DeferredRegister.create(
        BuiltInRegistries.BLOCK_ENTITY_TYPE,
        CommonStrings.MOD_ID
    );

    public static DeferredRegister<Block> blockDeferredRegister = DeferredRegister.create(
        BuiltInRegistries.BLOCK,
        CommonStrings.MOD_ID
    );

    public static DeferredRegister<EntityType<?>> entityTypeDeferredRegister = DeferredRegister.create(
        BuiltInRegistries.ENTITY_TYPE,
        CommonStrings.MOD_ID
    );

    public static DeferredRegister<Item> itemDeferredRegister = DeferredRegister.create(
        BuiltInRegistries.ITEM,
        CommonStrings.MOD_ID
    );

    public static DeferredRegister<CreativeModeTab> creativeModeTabDeferredRegister = DeferredRegister.create(
        BuiltInRegistries.CREATIVE_MODE_TAB,
        CommonStrings.MOD_ID
    );

    public NeoForgeMod(IEventBus modEventBus) {
        CommonMod.initRegistries();
        blockEntityTypeDeferredRegister.register(modEventBus);
        blockDeferredRegister.register(modEventBus);
        entityTypeDeferredRegister.register(modEventBus);
        itemDeferredRegister.register(modEventBus);
        creativeModeTabDeferredRegister.register(modEventBus);
        modEventBus.addListener(this::createEntityAttributes);
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

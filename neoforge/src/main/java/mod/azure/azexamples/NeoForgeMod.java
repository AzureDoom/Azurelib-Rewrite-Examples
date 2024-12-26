package mod.azure.azexamples;

import mod.azure.azexamples.registry.ItemRegistry;
import mod.azure.azurelib.core2.animation.cache.AzIdentityRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.registries.DeferredRegister;

import mod.azure.azexamples.registry.EntityRegistry;

@Mod(CommonStrings.MOD_ID)
public final class NeoForgeMod {

    public static DeferredRegister<BlockEntityType<?>> blockEntityTypeDeferredRegister = DeferredRegister.create(
        Registries.BLOCK_ENTITY_TYPE,
        CommonStrings.MOD_ID
    );

    public static DeferredRegister<Block> blockDeferredRegister = DeferredRegister.create(
        Registries.BLOCK,
        CommonStrings.MOD_ID
    );

    public static DeferredRegister<EntityType<?>> entityTypeDeferredRegister = DeferredRegister.create(
        Registries.ENTITY_TYPE,
        CommonStrings.MOD_ID
    );

    public static DeferredRegister<Item> itemDeferredRegister = DeferredRegister.create(
        Registries.ITEM,
        CommonStrings.MOD_ID
    );

    public static DeferredRegister<CreativeModeTab> creativeModeTabDeferredRegister = DeferredRegister.create(
        Registries.CREATIVE_MODE_TAB,
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
        modEventBus.addListener(this::commonSetup);
    }

    public void commonSetup(final FMLCommonSetupEvent event) {
        AzIdentityRegistry.register(
                ItemRegistry.PISTOL.get(),
                ItemRegistry.DOOMICORN_HELMET.get(),
                ItemRegistry.DOOMICORN_CHESTPLATE.get(),
                ItemRegistry.DOOMICORN_LEGGINGS.get(),
                ItemRegistry.DOOMICORN_BOOTS.get(),
                Items.NETHERITE_SWORD,
                Items.NETHERITE_HELMET,
                Items.NETHERITE_CHESTPLATE,
                Items.NETHERITE_LEGGINGS,
                Items.NETHERITE_BOOTS);
    }

    public void createEntityAttributes(final EntityAttributeCreationEvent event) {
        event.put(EntityRegistry.DOOMHUNTER.get(), Monster.createMonsterAttributes().build());
        event.put(EntityRegistry.MARAUDER.get(), Monster.createMonsterAttributes().build());
    }
}

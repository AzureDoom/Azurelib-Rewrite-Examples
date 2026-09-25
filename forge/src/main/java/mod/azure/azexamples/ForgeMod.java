package mod.azure.azexamples;

import mod.azure.azurelib.animation.cache.AzIdentityRegistry;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
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
import org.jetbrains.annotations.NotNull;

import mod.azure.azexamples.registry.BlockRegistry;
import mod.azure.azexamples.registry.EntityRegistry;
import mod.azure.azexamples.registry.ItemRegistry;

@Mod.EventBusSubscriber
@Mod(CommonStrings.MOD_ID)
public final class ForgeMod {

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

    public static DeferredRegister<SoundEvent> soundEventDeferredRegister = DeferredRegister.create(
        ForgeRegistries.SOUND_EVENTS,
        CommonStrings.MOD_ID
    );

    public static final CreativeModeTab EXAMPLEMOD_TAB = (new CreativeModeTab("examplemod_items") {

        @Override
        public @NotNull ItemStack makeIcon() {
            return new ItemStack(BlockRegistry.STARGATE_ITEM.get());
        }
    });

    public ForgeMod() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        CommonMod.initRegistries();
        blockEntityTypeDeferredRegister.register(modEventBus);
        blockDeferredRegister.register(modEventBus);
        entityTypeDeferredRegister.register(modEventBus);
        itemDeferredRegister.register(modEventBus);
        soundEventDeferredRegister.register(modEventBus);
        modEventBus.addListener(this::init);
        modEventBus.addListener(this::createEntityAttributes);
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

package mod.azure.azexamples;

import mod.azure.azurelib.rewrite.animation.cache.AzIdentityRegistry;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.Items;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod.EventBusSubscriber
@Mod(CommonStrings.MOD_ID)
public final class ForgeMod {

    public ForgeMod(FMLJavaModLoadingContext loadingContext) {
        IEventBus modEventBus = loadingContext.getModEventBus();
        ExampleRegistry.blockEntityTypeDeferredRegister.register(modEventBus);
        ExampleRegistry.blockDeferredRegister.register(modEventBus);
        ExampleRegistry.entityTypeDeferredRegister.register(modEventBus);
        ExampleRegistry.itemDeferredRegister.register(modEventBus);
        ExampleRegistry.creativeModeTabDeferredRegister.register(modEventBus);
        modEventBus.addListener(this::createEntityAttributes);
        modEventBus.addListener(this::commonSetup);
    }

    public void commonSetup(final FMLCommonSetupEvent event) {
        AzIdentityRegistry.register(
            ExampleRegistry.PISTOL.get(),
            ExampleRegistry.STARGATE_ITEM.get(),
            ExampleRegistry.DOOMICORN_HELMET.get(),
            ExampleRegistry.DOOMICORN_CHESTPLATE.get(),
            ExampleRegistry.DOOMICORN_LEGGINGS.get(),
            ExampleRegistry.DOOMICORN_BOOTS.get(),
            Items.NETHERITE_SWORD,
            Items.NETHERITE_HELMET,
            Items.NETHERITE_CHESTPLATE,
            Items.NETHERITE_LEGGINGS,
            Items.NETHERITE_BOOTS
        );
    }

    public void createEntityAttributes(final EntityAttributeCreationEvent event) {
        event.put(ExampleRegistry.DOOMHUNTER.get(), Monster.createMonsterAttributes().build());
        event.put(ExampleRegistry.MARAUDER.get(), Monster.createMonsterAttributes().build());
        event.put(ExampleRegistry.MANUL.get(), Mob.createMobAttributes().build());
        event.put(ExampleRegistry.JURAVENATOR.get(), Mob.createMobAttributes().build());
        event.put(ExampleRegistry.MARINE.get(), Mob.createMobAttributes().build());
    }
}

package mod.azure.azexamples;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.jetbrains.annotations.NotNull;

@Mod.EventBusSubscriber
@Mod(CommonStrings.MOD_ID)
public final class ForgeMod {

	public static final CreativeModeTab EXAMPLEMOD_TAB = (new CreativeModeTab("examplemod_items") {

		@Override
		public @NotNull ItemStack makeIcon() {
			return new ItemStack(ExampleRegistry.STARGATE_ITEM.get());
		}
	});

    public ForgeMod() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ExampleRegistry.blockEntityTypeDeferredRegister.register(modEventBus);
        ExampleRegistry.blockDeferredRegister.register(modEventBus);
        ExampleRegistry.entityTypeDeferredRegister.register(modEventBus);
        ExampleRegistry.itemDeferredRegister.register(modEventBus);
		ExampleRegistry.soundDefferredRegister.register(modEventBus);
        modEventBus.addListener(this::createEntityAttributes);
    }

    public void createEntityAttributes(final EntityAttributeCreationEvent event) {
        event.put(ExampleRegistry.DOOMHUNTER.get(), Monster.createMonsterAttributes().build());
        event.put(ExampleRegistry.MARAUDER.get(), Monster.createMonsterAttributes().build());
        event.put(ExampleRegistry.MANUL.get(), Mob.createMobAttributes().build());
        event.put(ExampleRegistry.JURAVENATOR.get(), Mob.createMobAttributes().build());
        event.put(ExampleRegistry.MARINE.get(), Mob.createMobAttributes().build());
        event.put(ExampleRegistry.GREMLIN.get(), Mob.createMobAttributes().build());
    }
}

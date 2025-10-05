package mod.azure.azexamples;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public final class FabricLibMod implements ModInitializer {

	public static final CreativeModeTab EXAMPLEMOD_TAB = FabricItemGroupBuilder.build(
		new ResourceLocation(CommonStrings.MOD_ID, "examplemod_items"),
		() -> new ItemStack(ExampleRegistry.STARGATE_ITEM)
	);

    @Override
    public void onInitialize() {
        ExampleRegistry.initialize();
        FabricDefaultAttributeRegistry.register(
            ExampleRegistry.DOOMHUNTER,
            Monster.createMonsterAttributes()
        );
        FabricDefaultAttributeRegistry.register(
            ExampleRegistry.MARAUDER,
            Monster.createMonsterAttributes()
        );
        FabricDefaultAttributeRegistry.register(
            ExampleRegistry.MANUL,
            Mob.createMobAttributes()
        );
        FabricDefaultAttributeRegistry.register(
            ExampleRegistry.JURAVENATOR,
            Mob.createMobAttributes()
        );
        FabricDefaultAttributeRegistry.register(
            ExampleRegistry.MARINE,
            Mob.createMobAttributes()
        );
        FabricDefaultAttributeRegistry.register(
            ExampleRegistry.GREMLIN,
            Mob.createMobAttributes()
        );
    }
}

package mod.azure.azexamples;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

import mod.azure.azexamples.registry.BlockRegistry;
import mod.azure.azexamples.registry.EntityRegistry;

public final class FabricLibMod implements ModInitializer {

    public static CreativeModeTab EXAMPLEMOD_TAB = FabricItemGroupBuilder.build(
        new ResourceLocation(CommonStrings.MOD_ID, "examplemod_items"),
        () -> new ItemStack(BlockRegistry.STARGATE_ITEM.get())
    );

    @Override
    public void onInitialize() {
        CommonMod.initRegistries();
        FabricDefaultAttributeRegistry.register(
            EntityRegistry.DOOMHUNTER.get(),
            Monster.createMonsterAttributes()
        );
        FabricDefaultAttributeRegistry.register(
            EntityRegistry.MARAUDER.get(),
            Monster.createMonsterAttributes()
        );
        FabricDefaultAttributeRegistry.register(
            EntityRegistry.MANUL.get(),
            Mob.createMobAttributes()
        );
        FabricDefaultAttributeRegistry.register(
            EntityRegistry.JURAVENATOR.get(),
            Mob.createMobAttributes()
        );
        FabricDefaultAttributeRegistry.register(
            EntityRegistry.MARINE.get(),
            Mob.createMobAttributes()
        );
        FabricDefaultAttributeRegistry.register(
            EntityRegistry.GREMLIN.get(),
            Mob.createMobAttributes()
        );
    }
}

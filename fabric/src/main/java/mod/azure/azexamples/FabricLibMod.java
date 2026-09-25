package mod.azure.azexamples;

import mod.azure.azurelib.animation.cache.AzIdentityRegistry;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import mod.azure.azexamples.registry.BlockRegistry;
import mod.azure.azexamples.registry.EntityRegistry;
import mod.azure.azexamples.registry.ItemRegistry;

public final class FabricLibMod implements ModInitializer {

    public static final CreativeModeTab EXAMPLEMOD_TAB = FabricItemGroupBuilder.build(
        new ResourceLocation(CommonStrings.MOD_ID, "examplemod_items"),
        () -> new ItemStack(BlockRegistry.STARGATE_ITEM.get())
    );

    @Override
    public void onInitialize() {
        CommonMod.initRegistries();
        AzIdentityRegistry.register(
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
        );
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

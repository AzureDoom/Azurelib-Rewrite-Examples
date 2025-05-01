package mod.azure.azexamples;

import mod.azure.azurelib.rewrite.animation.cache.AzIdentityRegistry;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.Items;

import mod.azure.azexamples.registry.EntityRegistry;
import mod.azure.azexamples.registry.ItemRegistry;

public final class FabricLibMod implements ModInitializer {

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
        );
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
            Items.NETHERITE_BOOTS
        );
    }
}

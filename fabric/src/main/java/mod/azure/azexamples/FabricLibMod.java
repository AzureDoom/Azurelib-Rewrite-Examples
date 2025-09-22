package mod.azure.azexamples;

import mod.azure.azurelib.rewrite.animation.cache.AzIdentityRegistry;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.Items;

public final class FabricLibMod implements ModInitializer {

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
        AzIdentityRegistry.register(
            ExampleRegistry.PISTOL,
            ExampleRegistry.STARGATE_ITEM,
            ExampleRegistry.DOOMICORN_HELMET,
            ExampleRegistry.DOOMICORN_CHESTPLATE,
            ExampleRegistry.DOOMICORN_LEGGINGS,
            ExampleRegistry.DOOMICORN_BOOTS,
            Items.NETHERITE_SWORD,
            Items.NETHERITE_HELMET,
            Items.NETHERITE_CHESTPLATE,
            Items.NETHERITE_LEGGINGS,
            Items.NETHERITE_BOOTS
        );
    }
}

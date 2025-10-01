package mod.azure.azexamples;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Monster;

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
        FabricDefaultAttributeRegistry.register(
            ExampleRegistry.MARINE,
            Mob.createMobAttributes()
        );
    }
}

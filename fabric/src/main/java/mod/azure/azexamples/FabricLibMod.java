package mod.azure.azexamples;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.world.entity.monster.Monster;

import mod.azure.azexamples.registry.EntityRegistry;

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
    }
}

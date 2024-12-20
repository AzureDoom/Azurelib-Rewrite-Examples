package mod.azure.azexamples.entities.marauder;

import mod.azure.azurelib.core2.animation.dispatch.AzDispatcher;
import mod.azure.azurelib.core2.animation.dispatch.command.AzDispatchCommand;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

import mod.azure.azexamples.CommonMod;

public class MarauderAnimationDispatcher {

    private static final AzDispatchCommand IDLE_COMMAND = AzDispatchCommand.builder()
        .playAnimation("base_controller", CommonMod.IDLE_ANIMATION_NAME)
        .build();

    private static final AzDispatchCommand WALK_COMMAND = AzDispatchCommand.builder()
        .playAnimation("base_controller", CommonMod.WALK_ANIMATION_NAME)
        .build();

    private static final AzDispatchCommand RUN_COMMAND = AzDispatchCommand.builder()
        .playAnimation("base_controller", CommonMod.RUN_ANIMATION_NAME)
        .build();

    private static final AzDispatchCommand MELEE_COMMAND = AzDispatchCommand.builder()
        .playAnimation("base_controller", CommonMod.MELEE_ANIMATION_NAME)
        .build();

    private static final AzDispatchCommand DEATH_COMMAND = AzDispatchCommand.builder()
        .playAnimation("base_controller", CommonMod.DEATH_ANIMATION_NAME)
        .build();

    private static final AzDispatchCommand SPAWN_COMMAND = AzDispatchCommand.builder()
        .playAnimation("base_controller", CommonMod.SPAWN_ANIMATION_NAME)
        .build();

    public void clientIdle(@NotNull Entity entity) {
        AzDispatcher.fromClient(IDLE_COMMAND).sendForEntity(entity);
    }

    public void clientWalk(@NotNull Entity entity) {
        AzDispatcher.fromClient(WALK_COMMAND).sendForEntity(entity);
    }

    public void clientRun(@NotNull Entity entity) {
        AzDispatcher.fromClient(RUN_COMMAND).sendForEntity(entity);
    }

    public void serverMelee(@NotNull Entity entity) {
        AzDispatcher.fromServer(MELEE_COMMAND).sendForEntity(entity);
    }

    public void clientDeath(@NotNull Entity entity) {
        AzDispatcher.fromClient(DEATH_COMMAND).sendForEntity(entity);
    }

    public void clientSpawn(@NotNull Entity entity) {
        AzDispatcher.fromClient(SPAWN_COMMAND).sendForEntity(entity);
    }
}

package mod.azure.azexamples.entities.marauder;

import mod.azure.azurelib.core2.animation.dispatch.AzDispatcher;
import mod.azure.azurelib.core2.animation.dispatch.command.AzDispatchCommand;

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

    public void clientIdle() {
        AzDispatcher.fromClient(IDLE_COMMAND);
    }

    public void clientWalk() {
        AzDispatcher.fromClient(WALK_COMMAND);
    }

    public void clientRun() {
        AzDispatcher.fromClient(RUN_COMMAND);
    }

    public void serverMelee() {
        AzDispatcher.fromServer(MELEE_COMMAND);
    }

    public void clientDeath() {
        AzDispatcher.fromClient(DEATH_COMMAND);
    }

    public void clientSpawn() {
        AzDispatcher.fromClient(SPAWN_COMMAND);
    }
}

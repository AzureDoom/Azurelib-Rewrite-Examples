package mod.azure.azexamples.entities.doomhunter;

import mod.azure.azurelib.core2.animation.dispatch.AzDispatcher;
import mod.azure.azurelib.core2.animation.dispatch.command.AzDispatchCommand;

import mod.azure.azexamples.CommonMod;

public class DoomHunterAnimationDispatcher {

    private static final AzDispatchCommand IDLE_COMMAND = AzDispatchCommand.builder()
        .playAnimation("base_controller", CommonMod.IDLE_ANIMATION_NAME)
        .build();

    public void clientIdle() {
        AzDispatcher.fromClient(IDLE_COMMAND);
    }
}

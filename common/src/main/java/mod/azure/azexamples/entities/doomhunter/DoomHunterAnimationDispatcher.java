package mod.azure.azexamples.entities.doomhunter;

import mod.azure.azurelib.animation.dispatch.command.AzCommand;
import mod.azure.azurelib.animation.play_behavior.AzPlayBehaviors;

import mod.azure.azexamples.CommonStrings;

/**
 * The DoomHunterAnimationDispatcher class is responsible for managing and sending animation commands for the Doom
 * Hunter entity. It acts as a utility to dispatch specific animation actions, such as playing the idle animation, to
 * the entity's client-side. This class integrates with the AzureLib animation dispatch system to construct and send
 * predefined commands, ensuring that animations are triggered consistently and efficiently in the game environment.
 * Usage of this class is tied to the entity lifecycle and is typically triggered during specific game events or entity
 * states, as demonstrated in the {@code DoomHunterEntity} class.
 */
public class DoomHunterAnimationDispatcher {

    private final AzCommand IDLE_COMMAND = AzCommand
        .create(CommonStrings.BASE_CONTROLLER, CommonStrings.IDLE_ANIMATION_NAME, AzPlayBehaviors.LOOP);

    private final DoomHunterEntity doomHunter;

    public DoomHunterAnimationDispatcher(DoomHunterEntity doomHunter) {
        this.doomHunter = doomHunter;
    }

    public void clientIdle() {
        IDLE_COMMAND.sendForEntity(doomHunter);
    }
}

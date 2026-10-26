package mod.azure.azexamples.entities.optimusprime;

import mod.azure.azurelib.common.animation.dispatch.command.AzCommand;
import mod.azure.azurelib.common.animation.play_behavior.AzPlayBehaviors;

import mod.azure.azexamples.CommonStrings;

/**
 * Credit to <a href="https://github.com/MARCTR0N">Marctron</a> for the model/textures/animations of this example.
 */
public class OptimusPrimeAnimationDispatcher {

    private final AzCommand idleCommand = AzCommand
        .create(CommonStrings.BASE_CONTROLLER, "walkcycle", AzPlayBehaviors.LOOP);

    private final OptimusPrimeEntity optimusPrimeEntity;

    public OptimusPrimeAnimationDispatcher(OptimusPrimeEntity optimusPrimeEntity) {
        this.optimusPrimeEntity = optimusPrimeEntity;
    }

    public void clientIdle() {
        idleCommand.sendForEntity(optimusPrimeEntity);
    }
}

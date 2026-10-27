package mod.azure.azexamples.entities.optimusprime;

import mod.azure.azexamples.CommonMod;
import mod.azure.azexamples.CommonStrings;
import mod.azure.azurelib.animation.AzAnimatorConfig;
import mod.azure.azurelib.animation.controller.AzAnimationController;
import mod.azure.azurelib.animation.controller.AzAnimationControllerContainer;
import mod.azure.azurelib.animation.controller.AzBlendMode;
import mod.azure.azurelib.animation.impl.AzEntityAnimator;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.NotNull;

/**
 * Credit to <a href="https://github.com/MARCTR0N">Marctron</a> for the model/textures/animations of this example.
 */
public class OptimusPrimeAnimator extends AzEntityAnimator<OptimusPrimeEntity> {

    /**
     * Blend length of the base controller. {@link OptimusPrimeAnimationDispatcher#STRIKE_TIMING} uses the same value so
     * the server knows how long each stage's blend takes.
     */
    public static final int BASE_TRANSITION_TICKS = 10;

    private static final Identifier ANIMATIONS = CommonMod.modResource(
        "animations/entity/optimus_prime.animation.json"
    );

    public OptimusPrimeAnimator() {
        super(AzAnimatorConfig.defaultConfig());
    }

    @Override
    public void registerControllers(AzAnimationControllerContainer<OptimusPrimeEntity> animationControllerContainer) {
        animationControllerContainer.add(
            AzAnimationController.builder(this, CommonStrings.BASE_CONTROLLER)
                .setTransitionLength(BASE_TRANSITION_TICKS)
                .build(),
            AzAnimationController.builder(this, CommonStrings.ATTACK_CONTROLLER)
                .setBlendMode(AzBlendMode.ADDITIVE)
                .setWeight(0.6)
                .build()
        );
    }

    @Override
    public @NotNull Identifier getAnimationLocation(OptimusPrimeEntity optimusPrimeEntity) {
        return ANIMATIONS;
    }
}

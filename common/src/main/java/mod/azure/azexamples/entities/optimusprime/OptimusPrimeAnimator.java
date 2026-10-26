package mod.azure.azexamples.entities.optimusprime;

import mod.azure.azurelib.animation.AzAnimatorConfig;
import mod.azure.azurelib.animation.controller.AzAnimationController;
import mod.azure.azurelib.animation.controller.AzAnimationControllerContainer;
import mod.azure.azurelib.animation.controller.AzBlendMode;
import mod.azure.azurelib.animation.impl.AzEntityAnimator;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import mod.azure.azexamples.CommonMod;
import mod.azure.azexamples.CommonStrings;

/**
 * Credit to <a href="https://github.com/MARCTR0N">Marctron</a> for the model/textures/animations of this example.
 */
public class OptimusPrimeAnimator extends AzEntityAnimator<OptimusPrimeEntity> {

    private static final ResourceLocation ANIMATIONS = CommonMod.modResource(
        "animations/entity/optimus_prime.animation.json"
    );

    public OptimusPrimeAnimator() {
        super(AzAnimatorConfig.defaultConfig());
    }

    @Override
    public void registerControllers(AzAnimationControllerContainer<OptimusPrimeEntity> animationControllerContainer) {
        animationControllerContainer.add(
            AzAnimationController.builder(this, CommonStrings.BASE_CONTROLLER).setTransitionLength(10).build(),
            AzAnimationController.builder(this, CommonStrings.ATTACK_CONTROLLER)
                .setBlendMode(AzBlendMode.ADDITIVE)
                .setWeight(0.6)
                .build()
        );
    }

    @Override
    public @NotNull ResourceLocation getAnimationLocation(OptimusPrimeEntity optimusPrimeEntity) {
        return ANIMATIONS;
    }
}

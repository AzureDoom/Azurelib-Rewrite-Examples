package mod.azure.azexamples.entities.manul;

import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

import mod.azure.azurelib.animation.AzAnimatorConfig;
import mod.azure.azurelib.animation.controller.AzAnimationController;
import mod.azure.azurelib.animation.controller.AzAnimationControllerContainer;
import mod.azure.azurelib.animation.impl.AzEntityAnimator;

import mod.azure.azexamples.CommonMod;
import mod.azure.azexamples.CommonStrings;

/**
 * Credit to Immersed for the animations of this example.
 */
public class ManulAnimator extends AzEntityAnimator<ManulEntity> {

    private static final ResourceLocation ANIMATIONS = CommonMod.modResource(
        "animations/entity/manul.animation.json"
    );

    public ManulAnimator() {
        super(AzAnimatorConfig.defaultConfig());
    }

    @Override
    public void registerControllers(AzAnimationControllerContainer<ManulEntity> animationControllerContainer) {
        animationControllerContainer.add(
            AzAnimationController.builder(this, CommonStrings.BASE_CONTROLLER).setTransitionLength(5).build()
        );
    }

    @Override
    public @Nonnull ResourceLocation getAnimationLocation(ManulEntity drone) {
        return ANIMATIONS;
    }
}

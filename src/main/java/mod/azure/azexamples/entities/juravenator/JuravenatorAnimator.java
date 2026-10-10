package mod.azure.azexamples.entities.juravenator;

import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

import mod.azure.azurelib.animation.AzAnimatorConfig;
import mod.azure.azurelib.animation.controller.AzAnimationController;
import mod.azure.azurelib.animation.controller.AzAnimationControllerContainer;
import mod.azure.azurelib.animation.impl.AzEntityAnimator;

import mod.azure.azexamples.CommonMod;
import mod.azure.azexamples.CommonStrings;

/**
 * Credit to Collinvht of <a href="https://modrinth.com/mod/new-world-mod">New World</a> for the animations of this
 * example, as it is using pure Bedrock Animations!
 */
public class JuravenatorAnimator extends AzEntityAnimator<JuravenatorEntity> {

    private static final ResourceLocation ANIMATIONS = CommonMod.modResource(
        "animations/entity/juravenator.animation.json"
    );

    public JuravenatorAnimator() {
        super(AzAnimatorConfig.defaultConfig());
    }

    @Override
    public void registerControllers(AzAnimationControllerContainer<JuravenatorEntity> animationControllerContainer) {
        animationControllerContainer.add(
            AzAnimationController.builder(this, CommonStrings.BASE_CONTROLLER).build()
        );
    }

    @Override
    public @Nonnull ResourceLocation getAnimationLocation(JuravenatorEntity juravenator) {
        return ANIMATIONS;
    }
}

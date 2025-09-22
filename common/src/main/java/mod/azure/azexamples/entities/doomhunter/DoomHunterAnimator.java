package mod.azure.azexamples.entities.doomhunter;

import mod.azure.azurelib.rewrite.animation.AzAnimatorConfig;
import mod.azure.azurelib.rewrite.animation.controller.AzAnimationController;
import mod.azure.azurelib.rewrite.animation.controller.AzAnimationControllerContainer;
import mod.azure.azurelib.rewrite.animation.impl.AzEntityAnimator;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import mod.azure.azexamples.CommonMod;
import mod.azure.azexamples.CommonStrings;

/**
 * The DoomHunterAnimator class is responsible for controlling the animations of the Doom Hunter entity in the game. It
 * inherits from the {@code AzEntityAnimator} and is used specifically for the {@code DoomHunterEntity}.
 */
public class DoomHunterAnimator extends AzEntityAnimator<DoomHunterEntity> {

    private static final ResourceLocation ANIMATIONS = CommonMod.modResource(
        "animations/entity/doomhunter.animation.json"
    );

    public DoomHunterAnimator() {
        super(AzAnimatorConfig.defaultConfig());
    }

    @Override
    public void registerControllers(AzAnimationControllerContainer<DoomHunterEntity> animationControllerContainer) {
        animationControllerContainer.add(
            AzAnimationController.builder(this, CommonStrings.BASE_CONTROLLER).setTransitionLength(10).build()
        );
    }

    @Override
    public @NotNull ResourceLocation getAnimationLocation(DoomHunterEntity drone) {
        return ANIMATIONS;
    }
}

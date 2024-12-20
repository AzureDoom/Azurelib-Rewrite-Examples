package mod.azure.azexamples.entities.doomhunter;

import mod.azure.azurelib.common.internal.common.AzureLib;
import mod.azure.azurelib.core2.animation.AzAnimatorConfig;
import mod.azure.azurelib.core2.animation.controller.AzAnimationController;
import mod.azure.azurelib.core2.animation.controller.AzAnimationControllerContainer;
import mod.azure.azurelib.core2.animation.impl.AzEntityAnimator;
import mod.azure.azurelib.core2.animation.primitive.AzRawAnimation;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import mod.azure.azexamples.CommonMod;

public class DoomHunterAnimator extends AzEntityAnimator<DoomHunterEntity> {

    private static final ResourceLocation ANIMATIONS = AzureLib.modResource(
        "animations/entity/doomhunter.animation.json"
    );

    private static final AzRawAnimation IDLE_ANIMATION = AzRawAnimation.begin().thenLoop(CommonMod.IDLE_ANIMATION_NAME);

    public DoomHunterAnimator() {
        super(AzAnimatorConfig.defaultConfig());
    }

    @Override
    public void registerControllers(AzAnimationControllerContainer<DoomHunterEntity> animationControllerContainer) {
        animationControllerContainer.add(
            AzAnimationController.builder(this, "base_controller")
                .triggerableAnim(CommonMod.IDLE_ANIMATION_NAME, IDLE_ANIMATION)
                .build()
        );
    }

    @Override
    public @NotNull ResourceLocation getAnimationLocation(DoomHunterEntity drone) {
        return ANIMATIONS;
    }
}

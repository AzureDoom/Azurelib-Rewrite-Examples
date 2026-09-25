package mod.azure.azexamples.entities.marine;

import mod.azure.azurelib.common.animation.AzAnimatorConfig;
import mod.azure.azurelib.common.animation.controller.AzAnimationController;
import mod.azure.azurelib.common.animation.controller.AzAnimationControllerContainer;
import mod.azure.azurelib.common.animation.impl.AzEntityAnimator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

import mod.azure.azexamples.CommonMod;
import mod.azure.azexamples.CommonStrings;

public class MarineAnimator extends AzEntityAnimator<MarineEntity> {

    private static final ResourceLocation ANIMATIONS = CommonMod.modResource(
        "animations/entity/possessed_engineer.animation.json"
    );

    public MarineAnimator() {
        super(AzAnimatorConfig.defaultConfig());
    }

    @Override
    public void registerControllers(AzAnimationControllerContainer<MarineEntity> animationControllerContainer) {
        animationControllerContainer.add(
            AzAnimationController.builder(this, CommonStrings.BASE_CONTROLLER).build()
        );
    }

    @Override
    public @NotNull ResourceLocation getAnimationLocation(MarineEntity drone) {
        return ANIMATIONS;
    }

    @Override
    public void setCustomAnimations(MarineEntity animatable, float partialTicks) {
        super.setCustomAnimations(animatable, partialTicks);
        var boneCache = this.context().boneCache();
        var leftArm = boneCache.getBakedModel().getBone("gLeftArm");
        var rightArm = boneCache.getBakedModel().getBone("gRightArm");
        var leftLeg = boneCache.getBakedModel().getBone("gLeftLeg");
        var rightLeg = boneCache.getBakedModel().getBone("gRightLeg");

        leftArm.ifPresent(
            azBone -> azBone
                .setRotX(
                    Mth.cos(animatable.walkAnimation.position(partialTicks) * 0.6662F + 3.1415927F) * 2.0F
                        * animatable.walkAnimation.speed() * 0.5F
                )
        );
        rightArm.ifPresent(
            azBone -> azBone
                .setRotX(
                    Mth.cos(animatable.walkAnimation.position(partialTicks) * 0.6662F + 3.1415927F) * -2.0F
                        * animatable.walkAnimation.speed() * 0.5F
                )
        );
        leftLeg.ifPresent(
            azBone -> azBone
                .setRotX(
                    Mth.cos(animatable.walkAnimation.position(partialTicks) * 0.6662F + 3.1415927F) * 2.0F
                        * animatable.walkAnimation.speed() * 0.5F
                )
        );
        rightLeg.ifPresent(
            azBone -> azBone
                .setRotX(
                    Mth.cos(animatable.walkAnimation.position(partialTicks) * 0.6662F) * 1.4F * animatable.walkAnimation
                        .speed() * 0.5F
                )
        );
    }
}

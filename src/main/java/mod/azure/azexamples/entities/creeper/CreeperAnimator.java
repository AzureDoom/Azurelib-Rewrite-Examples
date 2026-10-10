package mod.azure.azexamples.entities.creeper;

import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

import mod.azure.azurelib.animation.AzAnimatorConfig;
import mod.azure.azurelib.animation.controller.AzAnimationController;
import mod.azure.azurelib.animation.controller.AzAnimationControllerContainer;
import mod.azure.azurelib.animation.impl.AzEntityAnimator;
import mod.azure.azurelib.model.AzBakedModel;
import mod.azure.azurelib.model.AzBone;
import mod.azure.azurelib.util.math.Mth;

import mod.azure.azexamples.CommonMod;
import mod.azure.azexamples.CommonStrings;

public class CreeperAnimator extends AzEntityAnimator<EntityCreeper> {

    private static final ResourceLocation ANIMATIONS = CommonMod.modResource(
        "animations/entity/possessed_engineer.animation.json"
    );

    public CreeperAnimator() {
        super(AzAnimatorConfig.defaultConfig());
    }

    @Override
    public void registerControllers(AzAnimationControllerContainer<EntityCreeper> animationControllerContainer) {
        animationControllerContainer.add(
            AzAnimationController.builder(this, CommonStrings.BASE_CONTROLLER).build()
        );
    }

    @Override
    public @Nonnull ResourceLocation getAnimationLocation(EntityCreeper creeper) {
        return ANIMATIONS;
    }

    @Override
    public void setCustomAnimations(EntityCreeper creeper, float partialTicks) {
        super.setCustomAnimations(creeper, partialTicks);
        AzBakedModel model = this.context().boneCache().getBakedModel();
        float walkPosition = (creeper.limbSwing - creeper.limbSwingAmount * (1.0F - partialTicks)) * 0.6662F;
        float walkSpeed = Mth.lerp(partialTicks, creeper.prevLimbSwingAmount, creeper.limbSwingAmount);
        float armSwing = Mth.cos(walkPosition) * walkSpeed;
        float legSwing = Mth.cos(walkPosition) * 1.4F * walkSpeed;
        AzBone rightArm = model.getBoneOrNull("field_191224_h");
        AzBone leftArm = model.getBoneOrNull("field_191223_g");
        AzBone rightLeg = model.getBoneOrNull("field_217144_h");
        AzBone leftLeg = model.getBoneOrNull("field_217143_g");

        if (rightArm != null)
            rightArm.setRotX(rightArm.getInitialAzSnapshot().getRotX() - armSwing);
        if (leftArm != null)
            leftArm.setRotX(leftArm.getInitialAzSnapshot().getRotX() + armSwing);
        if (rightLeg != null)
            rightLeg.setRotX(rightLeg.getInitialAzSnapshot().getRotX() + legSwing);
        if (leftLeg != null)
            leftLeg.setRotX(leftLeg.getInitialAzSnapshot().getRotX() - legSwing);
    }
}

package mod.azure.azexamples.entities.creeper;

import mod.azure.azurelib.animation.AzAnimatorConfig;
import mod.azure.azurelib.animation.controller.AzAnimationController;
import mod.azure.azurelib.animation.controller.AzAnimationControllerContainer;
import mod.azure.azurelib.animation.impl.AzEntityAnimator;
import mod.azure.azurelib.model.AzBakedModel;
import mod.azure.azurelib.model.AzBone;
import mod.azure.azurelib.platform.mc.Mc;
import mod.azure.azurelib.util.ResourceLocation;
import mod.azure.azurelib.util.math.Mth;
import net.minecraft.entity.mob.CreeperEntity;

import javax.annotation.Nonnull;

import mod.azure.azexamples.CommonMod;
import mod.azure.azexamples.CommonStrings;

public class CreeperAnimator extends AzEntityAnimator<CreeperEntity> {

    private static final ResourceLocation ANIMATIONS = CommonMod.modResource(
        "animations/entity/possessed_engineer.animation.json"
    );

    public CreeperAnimator() {
        super(AzAnimatorConfig.defaultConfig());
    }

    @Override
    public void registerControllers(AzAnimationControllerContainer<CreeperEntity> animationControllerContainer) {
        animationControllerContainer.add(
            AzAnimationController.builder(this, CommonStrings.BASE_CONTROLLER).build()
        );
    }

    @Override
    public @Nonnull ResourceLocation getAnimationLocation(CreeperEntity creeper) {
        return ANIMATIONS;
    }

    @Override
    public void setCustomAnimations(CreeperEntity creeper, float partialTicks) {
        super.setCustomAnimations(creeper, partialTicks);
        AzBakedModel model = this.context().boneCache().getBakedModel();
        float walkPosition = (Mc.limbSwing(creeper) - Mc.limbSwingAmount(creeper) * (1.0F - partialTicks)) * 0.6662F;
        float walkSpeed = Mth.lerp(partialTicks, Mc.prevLimbSwingAmount(creeper), Mc.limbSwingAmount(creeper));
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

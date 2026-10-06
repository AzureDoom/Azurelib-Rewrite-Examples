package mod.azure.azexamples.entities.creeper;

import mod.azure.azurelib.common.animation.AzAnimatorConfig;
import mod.azure.azurelib.common.animation.controller.AzAnimationController;
import mod.azure.azurelib.common.animation.controller.AzAnimationControllerContainer;
import mod.azure.azurelib.common.animation.impl.AzEntityAnimator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.monster.Creeper;
import org.jetbrains.annotations.NotNull;

import mod.azure.azexamples.CommonMod;
import mod.azure.azexamples.CommonStrings;

public class CreeperAnimator extends AzEntityAnimator<Creeper> {

    private static final ResourceLocation ANIMATIONS = CommonMod.modResource(
        "animations/entity/possessed_engineer.animation.json"
    );

    public CreeperAnimator() {
        super(AzAnimatorConfig.defaultConfig());
    }

    @Override
    public void registerControllers(AzAnimationControllerContainer<Creeper> animationControllerContainer) {
        animationControllerContainer.add(
            AzAnimationController.builder(this, CommonStrings.BASE_CONTROLLER).build()
        );
    }

    @Override
    public @NotNull ResourceLocation getAnimationLocation(Creeper creeper) {
        return ANIMATIONS;
    }

    /**
     * Swings the arms and legs like a player's while walking. Each limb is set from its default pose rather than added
     * to its current rotation, so it works whether the animation also moves the limbs, and never drifts.
     */
    @Override
    public void setCustomAnimations(Creeper creeper, float partialTicks) {
        super.setCustomAnimations(creeper, partialTicks);

        var model = this.context().boneCache().getBakedModel();

        float walkPosition = creeper.walkAnimation.position(partialTicks) * 0.6662F;
        float walkSpeed = creeper.walkAnimation.speed(partialTicks);

        float armSwing = Mth.cos(walkPosition) * walkSpeed;
        float legSwing = Mth.cos(walkPosition) * 1.4F * walkSpeed;

        var rightArm = model.getBoneOrNull("field_191224_h");
        var leftArm = model.getBoneOrNull("field_191223_g");
        var rightLeg = model.getBoneOrNull("field_217144_h");
        var leftLeg = model.getBoneOrNull("field_217143_g");

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

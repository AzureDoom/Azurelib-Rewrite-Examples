package mod.azure.azexamples.entities.creeper;

import net.minecraft.resources.Identifier;

import mod.azure.azexamples.CommonMod;

public class CreeperAnimator { // extends AzEntityAnimator<Creeper> {

    private static final Identifier ANIMATIONS = CommonMod.modResource(
        "animations/entity/possessed_engineer.animation.json"
    );

    public CreeperAnimator() {
        // super(AzAnimatorConfig.defaultConfig());
    }

    // @Override
    // public void registerControllers(AzAnimationControllerContainer<Creeper> animationControllerContainer) {
    // animationControllerContainer.add(
    // AzAnimationController.builder(this, CommonStrings.BASE_CONTROLLER).build()
    // );
    // }
    //
    // @Override
    // public @NotNull Identifier getAnimationLocation(Creeper drone) {
    // return ANIMATIONS;
    // }
    //
    // @Override
    // public void setCustomAnimations(Creeper animatable, float partialTicks) {
    // super.setCustomAnimations(animatable, partialTicks);
    // var boneCache = this.context().boneCache();
    // var leftArm = boneCache.getBakedModel().getBone("field_191223_g");
    // var rightArm = boneCache.getBakedModel().getBone("field_191224_h");
    // var leftLeg = boneCache.getBakedModel().getBone("field_217143_g");
    // var rightLeg = boneCache.getBakedModel().getBone("field_217144_h");
    //
    // if (leftArm.isPresent())
    // leftArm.get()
    // .setRotX(
    // Mth.cos(animatable.walkAnimation.position(partialTicks) * 0.6662F + 3.1415927F) * 2.0F
    // * animatable.walkAnimation.speed() * 0.5F
    // );
    // if (rightArm.isPresent())
    // rightArm.get()
    // .setRotX(
    // Mth.cos(animatable.walkAnimation.position(partialTicks) * 0.6662F + 3.1415927F) * -2.0F
    // * animatable.walkAnimation.speed() * 0.5F
    // );
    // if (leftLeg.isPresent())
    // leftLeg.get()
    // .setRotX(
    // Mth.cos(animatable.walkAnimation.position(partialTicks) * 0.6662F + 3.1415927F) * 2.0F
    // * animatable.walkAnimation.speed() * 0.5F
    // );
    // if (rightLeg.isPresent())
    // rightLeg.get()
    // .setRotX(
    // Mth.cos(animatable.walkAnimation.position(partialTicks) * 0.6662F) * 1.4F * animatable.walkAnimation
    // .speed() * 0.5F
    // );
    // }
}

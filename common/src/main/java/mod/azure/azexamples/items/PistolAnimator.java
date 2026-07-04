package mod.azure.azexamples.items;

import net.minecraft.resources.Identifier;

import mod.azure.azexamples.CommonMod;

public class PistolAnimator { // extends AzItemAnimator {

    private static final Identifier ANIMATIONS = CommonMod.modResource("animations/item/pistol.animation.json");

    public PistolAnimator() {
        // super(AzAnimatorConfig.defaultConfig());
    }

    // @Override
    // public void registerControllers(AzAnimationControllerContainer<ItemStack> animationControllerContainer) {
    // animationControllerContainer.add(
    // AzAnimationController.builder(this, CommonStrings.BASE_CONTROLLER)
    // .build()
    // );
    // }
    //
    // @Override
    // public @NotNull Identifier getAnimationLocation(ItemStack animatable) {
    // return ANIMATIONS;
    // }

}

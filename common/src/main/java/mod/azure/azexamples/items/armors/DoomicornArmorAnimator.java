package mod.azure.azexamples.items.armors;

import net.minecraft.resources.Identifier;

import mod.azure.azexamples.CommonMod;

public class DoomicornArmorAnimator { // extends AzItemAnimator {

    private static final Identifier ANIMATIONS = CommonMod.modResource(
        "animations/item/doomicorn.animation.json"
    );

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

package mod.azure.azexamples.entities.manul;

import net.minecraft.resources.Identifier;

import mod.azure.azexamples.CommonMod;

/**
 * Credit to Immersed for the animations of this example.
 */
public class ManulAnimator { // extends AzEntityAnimator<ManulEntity> {

    private static final Identifier ANIMATIONS = CommonMod.modResource(
        "animations/entity/manul.animation.json"
    );

    public ManulAnimator() {
        // super(AzAnimatorConfig.defaultConfig());
    }

    // @Override
    // public void registerControllers(AzAnimationControllerContainer<ManulEntity> animationControllerContainer) {
    // animationControllerContainer.add(
    // AzAnimationController.builder(this, CommonStrings.BASE_CONTROLLER).build()
    // );
    // }
    //
    // @Override
    // public @NotNull Identifier getAnimationLocation(ManulEntity drone) {
    // return ANIMATIONS;
    // }
}

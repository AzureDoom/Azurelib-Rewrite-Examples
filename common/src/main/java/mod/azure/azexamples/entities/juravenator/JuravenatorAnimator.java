package mod.azure.azexamples.entities.juravenator;

import net.minecraft.resources.Identifier;

import mod.azure.azexamples.CommonMod;

/**
 * Credit to Collinvht of <a href="https://modrinth.com/mod/new-world-mod">New World</a> for the animations of this
 * example, as it is using pure Bedrock Animations!
 */
public class JuravenatorAnimator { // extends AzEntityAnimator<JuravenatorEntity> {

    private static final Identifier ANIMATIONS = CommonMod.modResource(
        "animations/entity/juravenator.animation.json"
    );

    public JuravenatorAnimator() {
        // super(AzAnimatorConfig.defaultConfig());
    }

    // @Override
    // public void registerControllers(AzAnimationControllerContainer<JuravenatorEntity> animationControllerContainer) {
    // animationControllerContainer.add(
    // AzAnimationController.builder(this, CommonStrings.BASE_CONTROLLER).build()
    // );
    // }
    //
    // @Override
    // public @NotNull Identifier getAnimationLocation(JuravenatorEntity juravenator) {
    // return ANIMATIONS;
    // }
}

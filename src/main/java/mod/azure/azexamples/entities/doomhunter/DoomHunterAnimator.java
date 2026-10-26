package mod.azure.azexamples.entities.doomhunter;

import mod.azure.azurelib.animation.AzAnimatorConfig;
import mod.azure.azurelib.animation.controller.AzAnimationController;
import mod.azure.azurelib.animation.controller.AzAnimationControllerContainer;
import mod.azure.azurelib.animation.controller.AzBlendMode;
import mod.azure.azurelib.animation.impl.AzEntityAnimator;
import mod.azure.azurelib.util.ResourceLocation;

import mod.azure.azexamples.CommonMod;
import mod.azure.azexamples.CommonStrings;

/**
 * This animator demonstrates both kinds of controller blending:
 * <ul>
 * <li><b>Static</b>: configured once in {@link #registerControllers}. The base controller is a full-weight
 * {@link AzBlendMode#OVERRIDE} layer carrying the idle loop, and the attack controller is an
 * {@link AzBlendMode#ADDITIVE} layer, so the flamethrower motion is added on top of the idle instead of replacing
 * it.</li>
 * <li><b>Dynamic</b>: the attack controller starts at weight {@code 0} and is faded at runtime between {@code 0} and
 * {@link #FLAMETHROWER_WEIGHT} by {@link DoomHunterAnimationDispatcher}. The base controller is never touched, so the
 * idle loop keeps playing without restarting while the attack layer fades in and out over it.</li>
 * </ul>
 */
public class DoomHunterAnimator extends AzEntityAnimator<DoomHunterEntity> {

    /**
     * Peak strength of the additive flamethrower layer while firing. Fades target this value instead of {@code 1} so
     * the idle underneath stays visible at full fire.
     */
    public static final float FLAMETHROWER_WEIGHT = 0.6F;

    private static final ResourceLocation ANIMATIONS = CommonMod.modResource(
        "animations/entity/doomhunter.animation.json"
    );

    public DoomHunterAnimator() {
        super(AzAnimatorConfig.defaultConfig());
    }

    @Override
    public void registerControllers(AzAnimationControllerContainer<DoomHunterEntity> animationControllerContainer) {
        animationControllerContainer.add(
            // Base layer: OVERRIDE at the default weight of 1. Holds the idle loop for the entity's whole life.
            AzAnimationController.builder(this, CommonStrings.BASE_CONTROLLER).setTransitionLength(10).build(),
            // Attack layer: ADDITIVE, starting silent. Its weight is faded at runtime up to FLAMETHROWER_WEIGHT.
            AzAnimationController.builder(this, CommonStrings.ATTACK_CONTROLLER)
                .setBlendMode(AzBlendMode.ADDITIVE)
                .setWeight(0)
                .build()
        );
    }

    @Override
    public ResourceLocation getAnimationLocation(DoomHunterEntity doomHunter) {
        return ANIMATIONS;
    }
}

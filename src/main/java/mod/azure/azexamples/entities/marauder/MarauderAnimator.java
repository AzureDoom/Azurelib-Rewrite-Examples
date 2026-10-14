package mod.azure.azexamples.entities.marauder;

import mod.azure.azurelib.animation.AzAnimatorConfig;
import mod.azure.azurelib.animation.controller.AzAnimationController;
import mod.azure.azurelib.animation.controller.AzAnimationControllerContainer;
import mod.azure.azurelib.animation.controller.keyframe.AzKeyframeCallbacks;
import mod.azure.azurelib.animation.impl.AzEntityAnimator;
import mod.azure.azurelib.util.ResourceLocation;

import javax.annotation.Nonnull;

import mod.azure.azexamples.CommonMod;
import mod.azure.azexamples.CommonStrings;
import mod.azure.azexamples.platform.ExMc;

public class MarauderAnimator extends AzEntityAnimator<MarauderEntity> {

    private static final ResourceLocation ANIMATIONS = CommonMod.modResource(
        "animations/entity/marauder.animation.json"
    );

    public MarauderAnimator() {
        super(AzAnimatorConfig.defaultConfig());
    }

    @Override
    public void registerControllers(AzAnimationControllerContainer<MarauderEntity> animationControllerContainer) {
        animationControllerContainer.add(
            AzAnimationController.builder(this, CommonStrings.BASE_CONTROLLER)
                .setTransitionLength(5)
                .setKeyframeCallbacks(
                    AzKeyframeCallbacks.<MarauderEntity>builder()
                        .setSoundKeyframeHandler(
                            event -> {
                                String sound = event.getKeyframeData().getSound();
                                MarauderEntity marauder = event.getAnimatable();

                                if (sound.equals("walk")) {
                                    playLocal(marauder, "step.stone", 1.0F);
                                }

                                if (sound.equals("run")) {
                                    playLocal(marauder, "step.stone" /* Beta has no skeleton step sound */, 1.0F);
                                }

                                if (sound.equals("portal")) {
                                    playLocal(marauder, "portal.portal", 0.2F);
                                }

                                if (sound.equals("axe")) {
                                    playLocal(marauder, "random.bow", 1.0F);
                                }
                            }
                        )
                        .build()
                )
                .build()
        );
    }

    private static void playLocal(MarauderEntity marauder, String sound, float volume) {
        ExMc.playSound(marauder, sound, volume, 1.0F);
    }

    @Override
    public @Nonnull ResourceLocation getAnimationLocation(MarauderEntity marauder) {
        return ANIMATIONS;
    }
}

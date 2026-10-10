package mod.azure.azexamples.entities.marauder;

import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

import mod.azure.azurelib.animation.AzAnimatorConfig;
import mod.azure.azurelib.animation.controller.AzAnimationController;
import mod.azure.azurelib.animation.controller.AzAnimationControllerContainer;
import mod.azure.azurelib.animation.controller.keyframe.AzKeyframeCallbacks;
import mod.azure.azurelib.animation.impl.AzEntityAnimator;

import mod.azure.azexamples.CommonMod;
import mod.azure.azexamples.CommonStrings;

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
                                    playLocal(marauder, "mob.skeleton.step", 1.0F);
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
        marauder.worldObj.playSound(
            marauder.posX,
            marauder.posY,
            marauder.posZ,
            sound,
            volume,
            1.0F,
            true
        );
    }

    @Override
    public @Nonnull ResourceLocation getAnimationLocation(MarauderEntity marauder) {
        return ANIMATIONS;
    }
}

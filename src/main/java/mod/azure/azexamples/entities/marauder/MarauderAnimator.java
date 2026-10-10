package mod.azure.azexamples.entities.marauder;

import net.minecraft.init.SoundEvents;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;

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
                                    playLocal(marauder, SoundEvents.BLOCK_METAL_STEP, 1.0F);
                                }

                                if (sound.equals("run")) {
                                    playLocal(marauder, SoundEvents.ENTITY_SKELETON_STEP, 1.0F);
                                }

                                if (sound.equals("portal")) {
                                    playLocal(marauder, SoundEvents.BLOCK_PORTAL_AMBIENT, 0.2F);
                                }

                                if (sound.equals("axe")) {
                                    playLocal(marauder, SoundEvents.ENTITY_ENDEREYE_LAUNCH, 1.0F);
                                }
                            }
                        )
                        .build()
                )
                .build()
        );
    }

    private static void playLocal(MarauderEntity marauder, SoundEvent sound, float volume) {
        marauder.world.playSound(
            marauder.posX,
            marauder.posY,
            marauder.posZ,
            sound,
            SoundCategory.HOSTILE,
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

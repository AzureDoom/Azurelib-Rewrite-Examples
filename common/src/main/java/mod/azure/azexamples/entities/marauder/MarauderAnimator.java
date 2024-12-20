package mod.azure.azexamples.entities.marauder;

import mod.azure.azurelib.core2.animation.AzAnimatorConfig;
import mod.azure.azurelib.core2.animation.controller.AzAnimationController;
import mod.azure.azurelib.core2.animation.controller.AzAnimationControllerContainer;
import mod.azure.azurelib.core2.animation.controller.keyframe.AzKeyFrameCallbacks;
import mod.azure.azurelib.core2.animation.impl.AzEntityAnimator;
import mod.azure.azurelib.core2.animation.primitive.AzLoopType;
import mod.azure.azurelib.core2.animation.primitive.AzRawAnimation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import org.jetbrains.annotations.NotNull;

import mod.azure.azexamples.CommonMod;

public class MarauderAnimator extends AzEntityAnimator<MarauderEntity> {

    private static final ResourceLocation ANIMATIONS = CommonMod.modResource(
        "animations/entity/marauder.animation.json"
    );

    private static final AzRawAnimation IDLE_ANIMATION = AzRawAnimation.begin().thenLoop(CommonMod.IDLE_ANIMATION_NAME);

    private static final AzRawAnimation WALK_ANIMATION = AzRawAnimation.begin().thenLoop(CommonMod.WALK_ANIMATION_NAME);

    private static final AzRawAnimation SPAWN_ANIMATION = AzRawAnimation.begin()
        .then(CommonMod.SPAWN_ANIMATION_NAME, AzLoopType.PLAY_ONCE);

    private static final AzRawAnimation DEATH_ANIMATION = AzRawAnimation.begin()
        .then(CommonMod.DEATH_ANIMATION_NAME, AzLoopType.HOLD_ON_LAST_FRAME);

    private static final AzRawAnimation RUN_ANIMATION = AzRawAnimation.begin().thenLoop(CommonMod.RUN_ANIMATION_NAME);

    private static final AzRawAnimation MELEE_ANIMATION = AzRawAnimation.begin()
        .then(CommonMod.MELEE_ANIMATION_NAME, AzLoopType.PLAY_ONCE);

    public MarauderAnimator() {
        super(AzAnimatorConfig.defaultConfig());
    }

    @Override
    public void registerControllers(AzAnimationControllerContainer<MarauderEntity> animationControllerContainer) {
        animationControllerContainer.add(
            AzAnimationController.builder(this, "base_controller")
                .setTransitionLength(5)
                .setKeyFrameCallbacks(
                    AzKeyFrameCallbacks.<MarauderEntity>builder()
                        .setSoundKeyframeHandler(
                            event -> {
                                if (event.getKeyframeData().getSound().equals("walk")) {
                                    event.getAnimatable()
                                        .level()
                                        .playLocalSound(
                                            event.getAnimatable().getX(),
                                            event.getAnimatable().getY(),
                                            event.getAnimatable().getZ(),
                                            SoundEvents.METAL_STEP,
                                            SoundSource.HOSTILE,
                                            1.00F,
                                            1.0F,
                                            true
                                        );
                                }
                                if (event.getKeyframeData().getSound().equals("run")) {
                                    event.getAnimatable()
                                        .level()
                                        .playLocalSound(
                                            event.getAnimatable().getX(),
                                            event.getAnimatable().getY(),
                                            event.getAnimatable().getZ(),
                                            SoundEvents.SKELETON_STEP,
                                            SoundSource.HOSTILE,
                                            1.00F,
                                            1.0F,
                                            true
                                        );
                                }
                                if (event.getKeyframeData().getSound().equals("portal")) {
                                    event.getAnimatable()
                                        .level()
                                        .playLocalSound(
                                            event.getAnimatable().getX(),
                                            event.getAnimatable().getY(),
                                            event.getAnimatable().getZ(),
                                            SoundEvents.PORTAL_AMBIENT,
                                            SoundSource.HOSTILE,
                                            0.20F,
                                            1.0F,
                                            true
                                        );
                                }
                                if (event.getKeyframeData().getSound().equals("axe")) {
                                    event.getAnimatable()
                                        .level()
                                        .playLocalSound(
                                            event.getAnimatable().getX(),
                                            event.getAnimatable().getY(),
                                            event.getAnimatable().getZ(),
                                            SoundEvents.ENDER_EYE_LAUNCH,
                                            SoundSource.HOSTILE,
                                            1.00F,
                                            1.0F,
                                            true
                                        );
                                }
                            }
                        )
                        .build()
                )
                .triggerableAnim(CommonMod.IDLE_ANIMATION_NAME, IDLE_ANIMATION)
                .triggerableAnim(CommonMod.WALK_ANIMATION_NAME, WALK_ANIMATION)
                .triggerableAnim(CommonMod.RUN_ANIMATION_NAME, RUN_ANIMATION)
                .triggerableAnim(CommonMod.MELEE_ANIMATION_NAME, MELEE_ANIMATION)
                .triggerableAnim(CommonMod.DEATH_ANIMATION_NAME, DEATH_ANIMATION)
                .triggerableAnim(CommonMod.SPAWN_ANIMATION_NAME, SPAWN_ANIMATION)
                .build()
        );
    }

    @Override
    public @NotNull ResourceLocation getAnimationLocation(MarauderEntity drone) {
        return ANIMATIONS;
    }

    @Override
    public void setCustomAnimations(MarauderEntity animatable) {
        super.setCustomAnimations(animatable);
    }
}

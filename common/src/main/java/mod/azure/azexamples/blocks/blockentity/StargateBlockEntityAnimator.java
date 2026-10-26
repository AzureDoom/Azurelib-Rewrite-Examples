package mod.azure.azexamples.blocks.blockentity;

import mod.azure.azexamples.CommonMod;
import mod.azure.azexamples.CommonStrings;
import mod.azure.azurelib.animation.AzAnimatorConfig;
import mod.azure.azurelib.animation.controller.AzAnimationController;
import mod.azure.azurelib.animation.controller.AzAnimationControllerContainer;
import mod.azure.azurelib.animation.impl.AzBlockAnimator;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

/**
 * StargateBlockEntityAnimator is responsible for managing and configuring animations for the StargateBlockEntity. It
 * registers a single controller; everything else (sequences, ping-pong, freezing, reversing) is chosen per command by
 * {@link StargateBlockAnimationDispatcher}.
 */
public class StargateBlockEntityAnimator extends AzBlockAnimator<StargateBlockEntity> {

    /** Blend between stages. Short, so each mechanical step stays easy to tell apart. */
    public static final int TRANSITION_TICKS = 4;

    private static final ResourceLocation ANIMATIONS = CommonMod.modResource(
        "animations/block/stargate.animation.json"
    );

    protected StargateBlockEntityAnimator() {
        super(AzAnimatorConfig.defaultConfig());
    }

    @Override
    public void registerControllers(AzAnimationControllerContainer<StargateBlockEntity> animationControllerContainer) {
        animationControllerContainer.add(
            AzAnimationController.builder(this, CommonStrings.BASE_CONTROLLER)
                .setTransitionLength(TRANSITION_TICKS)
                .build()
        );
    }

    @Override
    public @NotNull ResourceLocation getAnimationLocation(StargateBlockEntity animatable) {
        return ANIMATIONS;
    }
}

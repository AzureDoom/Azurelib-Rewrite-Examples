package mod.azure.azexamples.blocks.blockentity;

import mod.azure.azexamples.CommonMod;
import mod.azure.azurelib.common.internal.common.AzureLib;
import mod.azure.azurelib.core2.animation.AzAnimatorConfig;
import mod.azure.azurelib.core2.animation.controller.AzAnimationController;
import mod.azure.azurelib.core2.animation.controller.AzAnimationControllerContainer;
import mod.azure.azurelib.core2.animation.impl.AzBlockAnimator;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

/**
 * StargateBlockEntityAnimator is responsible for managing and configuring
 * animations for the StargateBlockEntity. It defines specific animations
 * and registers them with the animation controller system, enabling dynamic
 * and interactive visual effects based on the block entity's state.
 */
public class StargateBlockEntityAnimator extends AzBlockAnimator<StargateBlockEntity> {
    private static final ResourceLocation ANIMATIONS = CommonMod.modResource(
            "animations/block/stargate.animation.json"
    );

    protected StargateBlockEntityAnimator() {
        super(AzAnimatorConfig.defaultConfig());
    }

    @Override
    public void registerControllers(AzAnimationControllerContainer<StargateBlockEntity> animationControllerContainer) {
        animationControllerContainer.add(
                AzAnimationController.builder(this, "base_controller")
                        .build()
        );
    }

    @Override
    public @NotNull ResourceLocation getAnimationLocation(StargateBlockEntity animatable) {
        return ANIMATIONS;
    }
}

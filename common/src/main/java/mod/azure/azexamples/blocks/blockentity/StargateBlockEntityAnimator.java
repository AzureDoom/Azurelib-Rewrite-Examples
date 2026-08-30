package mod.azure.azexamples.blocks.blockentity;

import mod.azure.azurelib.animation.AzAnimatorConfig;
import mod.azure.azurelib.animation.controller.AzAnimationController;
import mod.azure.azurelib.animation.controller.AzAnimationControllerContainer;
import mod.azure.azurelib.animation.impl.AzBlockAnimator;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.NotNull;

import mod.azure.azexamples.CommonMod;

/**
 * StargateBlockEntityAnimator is responsible for managing and configuring animations for the StargateBlockEntity. It
 * defines specific animations and registers them with the animation controller system, enabling dynamic and interactive
 * visual effects based on the block entity's state.
 */
public class StargateBlockEntityAnimator extends AzBlockAnimator<StargateBlockEntity> {

    private static final Identifier ANIMATIONS = CommonMod.modResource(
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
    public @NotNull Identifier getAnimationLocation(StargateBlockEntity animatable) {
        return ANIMATIONS;
    }
}

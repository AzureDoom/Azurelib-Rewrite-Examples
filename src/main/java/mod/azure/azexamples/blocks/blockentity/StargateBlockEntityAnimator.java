package mod.azure.azexamples.blocks.blockentity;

import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

import mod.azure.azurelib.animation.AzAnimatorConfig;
import mod.azure.azurelib.animation.controller.AzAnimationController;
import mod.azure.azurelib.animation.controller.AzAnimationControllerContainer;
import mod.azure.azurelib.animation.impl.AzBlockAnimator;

import mod.azure.azexamples.CommonMod;
import mod.azure.azexamples.CommonStrings;

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
            AzAnimationController.builder(this, CommonStrings.BASE_CONTROLLER)
                .build()
        );
    }

    @Override
    public @Nonnull ResourceLocation getAnimationLocation(StargateBlockEntity animatable) {
        return ANIMATIONS;
    }
}

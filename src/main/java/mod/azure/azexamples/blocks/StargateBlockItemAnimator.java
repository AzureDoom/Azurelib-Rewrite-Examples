package mod.azure.azexamples.blocks;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

import mod.azure.azurelib.animation.AzAnimatorConfig;
import mod.azure.azurelib.animation.controller.AzAnimationController;
import mod.azure.azurelib.animation.controller.AzAnimationControllerContainer;
import mod.azure.azurelib.animation.impl.AzItemAnimator;

import mod.azure.azexamples.CommonMod;
import mod.azure.azexamples.CommonStrings;

public class StargateBlockItemAnimator extends AzItemAnimator {

    private static final ResourceLocation ANIMATIONS = CommonMod.modResource(
        "animations/block/stargate.animation.json"
    );

    public StargateBlockItemAnimator() {
        super(AzAnimatorConfig.defaultConfig());
    }

    @Override
    public void registerControllers(AzAnimationControllerContainer<ItemStack> animationControllerContainer) {
        animationControllerContainer.add(
            AzAnimationController.builder(this, CommonStrings.BASE_CONTROLLER).build()
        );
    }

    @Override
    public @Nonnull ResourceLocation getAnimationLocation(ItemStack animatable) {
        return ANIMATIONS;
    }
}

package mod.azure.azexamples.items.diamondreplace.armor;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

import mod.azure.azurelib.animation.controller.AzAnimationController;
import mod.azure.azurelib.animation.controller.AzAnimationControllerContainer;
import mod.azure.azurelib.animation.impl.AzItemAnimator;

import mod.azure.azexamples.CommonMod;
import mod.azure.azexamples.CommonStrings;

public class DiamondArmorAnimator extends AzItemAnimator {

    private static final ResourceLocation ANIMATIONS = CommonMod.modResource(
        "animations/item/cultist_armor.animation.json"
    );

    @Override
    public void registerControllers(AzAnimationControllerContainer<ItemStack> animationControllerContainer) {
        animationControllerContainer.add(
            AzAnimationController.builder(this, CommonStrings.BASE_CONTROLLER)
                .build()
        );
    }

    @Override
    public @Nonnull ResourceLocation getAnimationLocation(ItemStack animatable) {
        return ANIMATIONS;
    }
}

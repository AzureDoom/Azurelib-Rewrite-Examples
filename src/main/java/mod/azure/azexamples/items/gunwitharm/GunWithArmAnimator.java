package mod.azure.azexamples.items.gunwitharm;

import mod.azure.azurelib.animation.AzAnimatorConfig;
import mod.azure.azurelib.animation.controller.AzAnimationController;
import mod.azure.azurelib.animation.controller.AzAnimationControllerContainer;
import mod.azure.azurelib.animation.controller.keyframe.AzKeyframeCallbacks;
import mod.azure.azurelib.animation.impl.AzItemAnimator;
import mod.azure.azurelib.util.ResourceLocation;
import mod.azure.azurelib.util.client.ClientUtils;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

import mod.azure.azexamples.CommonMod;
import mod.azure.azexamples.CommonStrings;
import mod.azure.azexamples.platform.ExMc;

public class GunWithArmAnimator extends AzItemAnimator {

    private static final ResourceLocation ANIMATIONS = CommonMod.modResource(
        "animations/item/peacemaker.animation.json"
    );

    public GunWithArmAnimator() {
        super(AzAnimatorConfig.defaultConfig());
    }

    @Override
    public void registerControllers(AzAnimationControllerContainer<ItemStack> animationControllerContainer) {
        animationControllerContainer.add(
            AzAnimationController.builder(this, CommonStrings.BASE_CONTROLLER)
                .setTransitionLength(1)
                .setKeyframeCallbacks(
                    AzKeyframeCallbacks.<ItemStack>builder()
                        .setSoundKeyframeHandler(
                            event -> {
                                PlayerEntity player = ClientUtils.getClientPlayer();

                                if (player != null && event.getKeyframeData().getSound().matches("firing"))
                                    ExMc.playSound(player, CommonStrings.MOD_ID + ":vigilance_fire", 1.0F, 1.0F);
                            }
                        )
                        .build()
                )
                .build()
        );
    }

    @Override
    public @Nonnull ResourceLocation getAnimationLocation(ItemStack animatable) {
        return ANIMATIONS;
    }
}

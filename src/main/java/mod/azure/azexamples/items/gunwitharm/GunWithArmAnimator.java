package mod.azure.azexamples.items.gunwitharm;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

import mod.azure.azurelib.animation.AzAnimatorConfig;
import mod.azure.azurelib.animation.controller.AzAnimationController;
import mod.azure.azurelib.animation.controller.AzAnimationControllerContainer;
import mod.azure.azurelib.animation.controller.keyframe.AzKeyframeCallbacks;
import mod.azure.azurelib.animation.impl.AzItemAnimator;
import mod.azure.azurelib.util.client.ClientUtils;

import mod.azure.azexamples.CommonMod;
import mod.azure.azexamples.CommonStrings;

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
                                EntityPlayer player = ClientUtils.getClientPlayer();

                                if (player != null && event.getKeyframeData().getSound().matches("firing"))
                                    player.worldObj.playSound(
                                        player.posX,
                                        player.posY,
                                        player.posZ,
                                        CommonStrings.MOD_ID + ":vigilance_fire",
                                        1.0F,
                                        1.0F,
                                        false
                                    );
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

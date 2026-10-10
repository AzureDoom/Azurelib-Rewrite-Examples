package mod.azure.azexamples.items.gunwitharm;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;

import javax.annotation.Nonnull;

import mod.azure.azurelib.animation.AzAnimatorConfig;
import mod.azure.azurelib.animation.controller.AzAnimationController;
import mod.azure.azurelib.animation.controller.AzAnimationControllerContainer;
import mod.azure.azurelib.animation.controller.keyframe.AzKeyframeCallbacks;
import mod.azure.azurelib.animation.impl.AzItemAnimator;
import mod.azure.azurelib.util.client.ClientUtils;

import mod.azure.azexamples.CommonMod;
import mod.azure.azexamples.CommonStrings;
import mod.azure.azexamples.registry.SoundRegistry;

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
                                    player.world.playSound(
                                        player,
                                        player.getPosition(),
                                        SoundRegistry.SHOOT_GUN,
                                        SoundCategory.PLAYERS,
                                        1.0F,
                                        1.0F
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

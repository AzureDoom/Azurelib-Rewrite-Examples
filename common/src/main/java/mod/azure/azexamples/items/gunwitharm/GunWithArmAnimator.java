package mod.azure.azexamples.items.gunwitharm;

import mod.azure.azexamples.services.AzExampleServices;
import mod.azure.azurelib.rewrite.animation.AzAnimatorConfig;
import mod.azure.azurelib.rewrite.animation.controller.AzAnimationController;
import mod.azure.azurelib.rewrite.animation.controller.AzAnimationControllerContainer;
import mod.azure.azurelib.rewrite.animation.controller.keyframe.AzKeyframeCallbacks;
import mod.azure.azurelib.rewrite.animation.impl.AzItemAnimator;
import mod.azure.azurelib.util.ClientUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

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
                                var player = ClientUtils.getClientPlayer();
                                if (event.getKeyframeData().getSound().matches("firing"))
                                    player.level
                                        .playSound(
                                            player,
                                            player.blockPosition(),
	                                        AzExampleServices.COMMON_REGISTRY.firingSound(),
                                            SoundSource.PLAYERS,
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
    public @NotNull ResourceLocation getAnimationLocation(ItemStack animatable) {
        return ANIMATIONS;
    }
}

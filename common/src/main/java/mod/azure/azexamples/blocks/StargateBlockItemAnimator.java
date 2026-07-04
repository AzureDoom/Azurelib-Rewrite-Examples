package mod.azure.azexamples.blocks;

import net.minecraft.resources.Identifier;

import mod.azure.azexamples.CommonMod;

/**
 * The StargateBlockItemAnimator class is responsible for handling animations for the Stargate block item. It extends
 * the AzItemAnimator class and integrates animation capabilities defined by AzureLib.
 */
public class StargateBlockItemAnimator {// extends AzItemAnimator {

    private static final Identifier ANIMATIONS = CommonMod.modResource(
        "animations/block/stargate.animation.json"
    );

    // public StargateBlockItemAnimator() {
    // //super(AzAnimatorConfig.defaultConfig());
    // }
    //
    // @Override
    // public void registerControllers(AzAnimationControllerContainer<ItemStack> animationControllerContainer) {
    // animationControllerContainer.add(
    // AzAnimationController.builder(this, "base_controller").build()
    // );
    // }
    //
    // @Override
    // public @NotNull Identifier getAnimationLocation(ItemStack animatable) {
    // return ANIMATIONS;
    // }
}

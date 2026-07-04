package mod.azure.azexamples.items.gunwitharm;

import net.minecraft.resources.Identifier;

import mod.azure.azexamples.CommonMod;

public class GunWithArmAnimator { // extends AzItemAnimator {

    private static final Identifier ANIMATIONS = CommonMod.modResource(
        "animations/item/peacemaker.animation.json"
    );

    public GunWithArmAnimator() {
        // super(AzAnimatorConfig.defaultConfig());
    }

    // @Override
    // public void registerControllers(AzAnimationControllerContainer<ItemStack> animationControllerContainer) {
    // animationControllerContainer.add(
    // AzAnimationController.builder(this, CommonStrings.BASE_CONTROLLER)
    // .setTransitionLength(1)
    // .setKeyframeCallbacks(
    // AzKeyframeCallbacks.<ItemStack>builder()
    // .setSoundKeyframeHandler(
    // event -> {
    // var player = ClientUtils.getClientPlayer();
    // if (event.getKeyframeData().getSound().matches("firing"))
    // player.level()
    // .playSound(
    // player,
    // player.blockPosition(),
    // SoundRegistry.SHOOT_GUN.get(),
    // SoundSource.PLAYERS,
    // 1.0F,
    // 1.0F
    // );
    // }
    // )
    // .build()
    // )
    // .build()
    // );
    // }
    //
    // @Override
    // public @NotNull Identifier getAnimationLocation(ItemStack animatable) {
    // return ANIMATIONS;
    // }
}

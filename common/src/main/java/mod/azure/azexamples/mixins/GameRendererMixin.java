package mod.azure.azexamples.mixins;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/**
 * Disables the bobView when holding the gun
 */
@Mixin(GameRenderer.class)
public class GameRendererMixin {

    @Shadow
    @Final
    Minecraft minecraft;

    // TODO: Find 26.2 replacement
    // @Inject(
    // method = "renderItemInHand", at = @At(
    // value = "INVOKE",
    // target = "Lnet/minecraft/client/renderer/GameRenderer;bobView(Lcom/mojang/blaze3d/vertex/PoseStack;F)V",
    // shift = At.Shift.BEFORE
    // )
    // )
    // private void azexamples$bobViewGun(
    // CameraRenderState cameraState,
    // float deltaPartialTick,
    // Matrix4fc modelViewMatrix,
    // CallbackInfo ci
    // ) {
    // if (
    // this.minecraft.player.getUseItem().getItem() instanceof GunWithArmItem && !this.minecraft.player
    // .isSprinting()
    // ) {
    // azexamples$gunBobView(projectionMatrix, partialTick);
    // }
    // }

    // TODO: Find 26.2 replacement
    // @WrapWithCondition(
    // method = "renderItemInHand", at = @At(
    // value = "INVOKE",
    // target = "Lnet/minecraft/client/renderer/GameRenderer;bobView(Lcom/mojang/blaze3d/vertex/PoseStack;F)V"
    // )
    // )
    // private boolean azexamples$vanillaBobView(GameRenderer instance, PoseStack matrices, float tickDelta) {
    // return !(this.minecraft.player.getUseItem().getItem() instanceof GunWithArmItem);
    // }

    // TODO: Find 26.2 replacement
    // @Unique
    // private void azexamples$gunBobView(Matrix4f projectionMatrix, float tickDelta) {
    // if (!(this.minecraft.gameRenderer.getMainCamera().getEntity() instanceof Player playerEntity)) {
    // return;
    // }
    // var f = (playerEntity.walkDist - playerEntity.walkDistO);
    // var g = -(playerEntity.walkDist + f * tickDelta);
    // var h = Mth.lerp(tickDelta, playerEntity.oBob, playerEntity.bob) * 0.25f;
    //
    // projectionMatrix.translate(
    // Mth.sin(g * (float) Math.PI) * h * 0.5f,
    // -Math.abs(Mth.cos(g * (float) Math.PI) * h),
    // 0
    // );
    //
    // projectionMatrix.rotate(Axis.ZP.rotationDegrees(Mth.sin(g * (float) Math.PI) * h * 3.0f));
    // projectionMatrix.rotate(Axis.XP.rotationDegrees(Math.abs(Mth.cos(g * (float) Math.PI - 0.2f) * h) * 5.0f));
    // }
}

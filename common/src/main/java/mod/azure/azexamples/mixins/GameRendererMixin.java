package mod.azure.azexamples.mixins;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import mod.azure.azexamples.items.gunwitharm.GunWithArmItem;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Disables the bobView when holding the gun
 */
@Mixin(GameRenderer.class)
public class GameRendererMixin {

    @Shadow
    @Final
    Minecraft minecraft;

    @Inject(
        method = "renderItemInHand", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/renderer/GameRenderer;bobView(Lcom/mojang/blaze3d/vertex/PoseStack;F)V",
            shift = At.Shift.BEFORE
        )
    )
    private void azexamples$bobViewGun(PoseStack poseStack, Camera activeRenderInfo, float partialTicks, CallbackInfo ci) {
        if (
            this.minecraft.player.getUseItem().getItem() instanceof GunWithArmItem && !this.minecraft.player
                .isSprinting()
        ) {
            azexamples$gunBobView(poseStack, partialTicks);
        }
    }

    @WrapWithCondition(
        method = "renderItemInHand", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/renderer/GameRenderer;bobView(Lcom/mojang/blaze3d/vertex/PoseStack;F)V"
        )
    )
    private boolean azexamples$vanillaBobView(GameRenderer instance, PoseStack matrices, float tickDelta) {
        return !(this.minecraft.player.getUseItem().getItem() instanceof GunWithArmItem);
    }

    @Unique
    private void azexamples$gunBobView(PoseStack poseStack, float tickDelta) {
        if (!(this.minecraft.gameRenderer.getMainCamera().getEntity() instanceof Player playerEntity)) {
            return;
        }
        var f = (playerEntity.walkDist - playerEntity.walkDistO);
        var g = -(playerEntity.walkDist + f * tickDelta);
        var h = Mth.lerp(tickDelta, playerEntity.oBob, playerEntity.bob) * 0.25f;

	    poseStack.translate(
            Mth.sin(g * (float) Math.PI) * h * 0.5f,
            -Math.abs(Mth.cos(g * (float) Math.PI) * h),
            0
        );

	    poseStack.mulPose(Axis.ZP.rotationDegrees(Mth.sin(g * (float) Math.PI) * h * 3.0f));
	    poseStack.mulPose(Axis.XP.rotationDegrees(Math.abs(Mth.cos(g * (float) Math.PI - 0.2f) * h) * 5.0f));
    }
}

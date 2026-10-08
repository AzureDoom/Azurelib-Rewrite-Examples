package mod.azure.azexamples.mixins;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.util.Mth;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

import mod.azure.azexamples.items.gunwitharm.GunWithArmItem;

/**
 * Disables/replaces vanilla view bobbing when holding the gun.
 */
@Mixin(GameRenderer.class)
public class GameRendererMixin {

    @Shadow
    @Final
    private Minecraft minecraft;

    @WrapOperation(
        method = "renderItemInHand",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/renderer/GameRenderer;bobView(Lnet/minecraft/client/renderer/state/level/CameraRenderState;Lcom/mojang/blaze3d/vertex/PoseStack;)V"
        )
    )
    private void azexamples$bobViewGun(
        GameRenderer instance,
        CameraRenderState cameraState,
        PoseStack poseStack,
        Operation<Void> original
    ) {
        var player = this.minecraft.player;

        if (
            player != null
                && player.getUseItem().getItem() instanceof GunWithArmItem
        ) {
            if (!player.isSprinting()) {
                azexamples$gunBobView(cameraState, poseStack);
            }

            return;
        }

        original.call(instance, cameraState, poseStack);
    }

    @Unique
    private void azexamples$gunBobView(
        CameraRenderState cameraState,
        PoseStack poseStack
    ) {
        if (!cameraState.entityRenderState.isPlayer) {
            return;
        }

        float walkDistance =
            cameraState.entityRenderState.backwardsInterpolatedWalkDistance;

        float bob = cameraState.entityRenderState.bob * 0.25F;

        poseStack.translate(
            Mth.sin(walkDistance * (float) Math.PI) * bob * 0.5F,
            -Math.abs(Mth.cos(walkDistance * (float) Math.PI) * bob),
            0.0F
        );

        poseStack.mulPose(
            Axis.ZP.rotationDegrees(
                Mth.sin(walkDistance * (float) Math.PI) * bob * 3.0F
            )
        );

        poseStack.mulPose(
            Axis.XP.rotationDegrees(
                Math.abs(
                    Mth.cos(walkDistance * (float) Math.PI - 0.2F) * bob
                ) * 5.0F
            )
        );
    }
}

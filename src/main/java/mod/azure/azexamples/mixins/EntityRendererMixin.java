
package mod.azure.azexamples.mixins;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import mod.azure.azurelib.util.math.Mth;

import mod.azure.azexamples.items.gunwitharm.GunWithArmItem;

@Mixin(EntityRenderer.class)
public abstract class EntityRendererMixin {

    @Shadow
    @Final
    private Minecraft mc;

    @Inject(
        method = "applyBobbing",
        at = @At("HEAD"),
        cancellable = true
    )
    private void azexamples$gunBobbing(float partialTicks, CallbackInfo ci) {
        if (
            this.mc.player == null ||
                !GunWithArmItem.isUsingGun(this.mc.player)
        ) {
            return;
        }

        // Replace vanilla bobbing while using the gun.
        ci.cancel();

        if (!this.mc.player.isSprinting()) {
            azexamples$gunBobView(partialTicks);
        }
    }

    @Unique
    private void azexamples$gunBobView(float partialTicks) {
        Entity viewEntity = this.mc.getRenderViewEntity();

        if (!(viewEntity instanceof EntityPlayer)) {
            return;
        }

        EntityPlayer player = (EntityPlayer) viewEntity;

        float walked = player.distanceWalkedModified
            - player.prevDistanceWalkedModified;

        float walkPos = -(player.distanceWalkedModified
            + walked * partialTicks);

        float bob = Mth.lerp(
            partialTicks,
            player.prevCameraYaw,
            player.cameraYaw
        ) * 0.25F;

        GlStateManager.translate(
            Mth.sin(walkPos * (float) Math.PI) * bob * 0.5F,
            -Math.abs(Mth.cos(walkPos * (float) Math.PI) * bob),
            0.0F
        );

        GlStateManager.rotate(
            Mth.sin(walkPos * (float) Math.PI) * bob * 3.0F,
            0.0F,
            0.0F,
            1.0F
        );

        GlStateManager.rotate(
            Math.abs(Mth.cos(walkPos * (float) Math.PI - 0.2F) * bob) * 5.0F,
            1.0F,
            0.0F,
            0.0F
        );
    }
}

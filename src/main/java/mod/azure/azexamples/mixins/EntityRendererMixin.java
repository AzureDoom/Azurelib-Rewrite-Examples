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
import org.spongepowered.asm.mixin.injection.Redirect;

import mod.azure.azurelib.util.math.Mth;

import mod.azure.azexamples.items.gunwitharm.GunWithArmItem;

@Mixin(EntityRenderer.class)
public abstract class EntityRendererMixin {

    @Shadow
    @Final
    private Minecraft mc;

    @Shadow
    protected abstract void applyBobbing(float partialTicks);

    @Redirect(
        method = "renderHand",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/EntityRenderer;applyBobbing(F)V")
    )
    private void azexamples$gunBobbing(EntityRenderer renderer, float partialTicks) {
        if (!GunWithArmItem.isUsingGun(this.mc.player)) {
            this.applyBobbing(partialTicks);
            return;
        }

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
        float walked = player.distanceWalkedModified - player.prevDistanceWalkedModified;
        float walkPos = -(player.distanceWalkedModified + walked * partialTicks);
        float bob = Mth.lerp(partialTicks, player.prevCameraYaw, player.cameraYaw) * 0.25F;

        GlStateManager.translate(
            Mth.sin(walkPos * (float) Math.PI) * bob * 0.5F,
            -Math.abs(Mth.cos(walkPos * (float) Math.PI) * bob),
            0.0F
        );
        GlStateManager.rotate(Mth.sin(walkPos * (float) Math.PI) * bob * 3.0F, 0.0F, 0.0F, 1.0F);
        GlStateManager.rotate(Math.abs(Mth.cos(walkPos * (float) Math.PI - 0.2F) * bob) * 5.0F, 1.0F, 0.0F, 0.0F);
    }
}

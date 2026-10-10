
package mod.azure.azexamples.mixins;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import mod.azure.azurelib.util.math.Mth;

import mod.azure.azexamples.items.gunwitharm.GunWithArmItem;

/**
 * Replaces the view bobbing of the first-person hand with a softer gun bob while the peacemaker is in use.
 */
@Mixin(EntityRenderer.class)
public abstract class EntityRendererMixin {

    @Shadow
    private Minecraft mc;

    @Inject(
        method = "setupViewBobbing",
        at = @At("HEAD"),
        cancellable = true
    )
    private void azexamples$gunBobbing(float partialTicks, CallbackInfo ci) {
        if (
            this.mc.thePlayer == null ||
                !GunWithArmItem.isUsingGun(this.mc.thePlayer)
        ) {
            return;
        }

        // Skip vanilla bobbing while using the gun.
        ci.cancel();

        if (!this.mc.thePlayer.isSprinting()) {
            azexamples$gunBobView(partialTicks);
        }
    }

    @Unique
    private void azexamples$gunBobView(float partialTicks) {
        if (!(this.mc.renderViewEntity instanceof EntityPlayer)) {
            return;
        }

        EntityPlayer player = (EntityPlayer) this.mc.renderViewEntity;

        float walked = player.distanceWalkedModified
            - player.prevDistanceWalkedModified;

        float walkPos = -(player.distanceWalkedModified
            + walked * partialTicks);

        float bob = Mth.lerp(
            partialTicks,
            player.prevCameraYaw,
            player.cameraYaw
        ) * 0.25F;

        GL11.glTranslatef(
            Mth.sin(walkPos * (float) Math.PI) * bob * 0.5F,
            -Math.abs(Mth.cos(walkPos * (float) Math.PI) * bob),
            0.0F
        );

        GL11.glRotatef(
            Mth.sin(walkPos * (float) Math.PI) * bob * 3.0F,
            0.0F,
            0.0F,
            1.0F
        );

        GL11.glRotatef(
            Math.abs(Mth.cos(walkPos * (float) Math.PI - 0.2F) * bob) * 5.0F,
            1.0F,
            0.0F,
            0.0F
        );
    }
}

package mod.azure.azexamples.mixins;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import mod.azure.azexamples.items.gunwitharm.GunWithArmItem;

@Mixin(Minecraft.class)
public abstract class MinecraftMixin {

    @Shadow
    public EntityPlayerSP player;

    @Inject(method = "sendClickBlockToController", at = @At("HEAD"), cancellable = true)
    private void azexamples$handleBlockBreaking(boolean leftClick, CallbackInfo ci) {
        if (GunWithArmItem.isUsingGun(this.player)) {
            ci.cancel();
        }
    }

    @Inject(method = "clickMouse", at = @At("HEAD"), cancellable = true)
    private void azexamples$onClickMouse(CallbackInfo ci) {
        if (this.player != null && this.player.getHeldItemMainhand().getItem() instanceof GunWithArmItem) {
            ci.cancel();
        }
    }
}

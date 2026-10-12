package mod.azure.azexamples.mixins;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.phys.HitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import mod.azure.azexamples.items.gunwitharm.GunWithArmItem;

@Mixin(Minecraft.class)
public class MinecraftMixin {

    @Shadow
    public HitResult hitResult;

    @Shadow
    public LocalPlayer player;

    @Inject(
        method = "continueAttack",
        at = @At("HEAD"),
        cancellable = true
    )
    private void azexamples$handleBlockBreaking(boolean down, CallbackInfo ci) {
        if (player.getMainHandItem().getItem() instanceof GunWithArmItem) {
            ci.cancel();
        }
    }

    @Inject(method = "startAttack", at = @At("HEAD"), cancellable = true)
    private void azexamples$onStartAttack(CallbackInfoReturnable<Boolean> cir) {
        if (player != null && player.getMainHandItem().getItem() instanceof GunWithArmItem) {
            cir.setReturnValue(false);
        }
    }
}

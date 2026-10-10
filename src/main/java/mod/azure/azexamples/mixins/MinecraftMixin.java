package mod.azure.azexamples.mixins;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import mod.azure.azexamples.items.gunwitharm.GunWithArmItem;

/**
 * Left click does nothing (no block breaking, no attack) while holding or using the peacemaker. Both methods are
 * private and unnamed in some 1.7.10 mappings, so they are targeted by MCP and SRG name.
 */
@Mixin(Minecraft.class)
public abstract class MinecraftMixin {

    @Shadow
    public EntityClientPlayerMP thePlayer;

    /** sendClickBlockToController(boolean) */
    @Inject(method = { "func_147115_a", "sendClickBlockToController" }, at = @At("HEAD"), cancellable = true)
    private void azexamples$handleBlockBreaking(boolean leftClick, CallbackInfo ci) {
        if (GunWithArmItem.isUsingGun(this.thePlayer)) {
            ci.cancel();
        }
    }

    /** clickMouse() */
    @Inject(method = { "func_147116_af", "clickMouse" }, at = @At("HEAD"), cancellable = true)
    private void azexamples$onClickMouse(CallbackInfo ci) {
        if (
            this.thePlayer != null && this.thePlayer.getCurrentEquippedItem() != null && this.thePlayer
                .getCurrentEquippedItem()
                .getItem() instanceof GunWithArmItem
        ) {
            ci.cancel();
        }
    }
}

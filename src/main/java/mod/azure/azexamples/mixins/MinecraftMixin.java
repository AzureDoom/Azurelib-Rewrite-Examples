
package mod.azure.azexamples.mixins;

import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import mod.azure.azexamples.items.gunwitharm.GunWithArmItem;

@Mixin(Minecraft.class)
public abstract class MinecraftMixin {

    @Inject(
        method = { "func_147115_a", "sendClickBlockToController" },
        at = @At("HEAD"),
        cancellable = true
    )
    private void azexamples$handleBlockBreaking(
        boolean leftClick,
        CallbackInfo ci
    ) {
        if (GunWithArmItem.isUsingGun(Minecraft.getMinecraft().thePlayer)) {
            ci.cancel();
        }
    }

    @Inject(
        method = { "func_147116_af", "clickMouse" },
        at = @At("HEAD"),
        cancellable = true
    )
    private void azexamples$onClickMouse(CallbackInfo ci) {
        Minecraft mc = Minecraft.getMinecraft();

        if (
            mc.thePlayer != null &&
                mc.thePlayer.getCurrentEquippedItem() != null &&
                mc.thePlayer.getCurrentEquippedItem().getItem() instanceof GunWithArmItem
        ) {
            ci.cancel();
        }
    }
}

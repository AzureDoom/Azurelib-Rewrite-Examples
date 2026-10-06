package mod.azure.azexamples.mixins;

import net.minecraft.client.player.FirstPersonHandsAndItems;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import mod.azure.azexamples.items.gunwitharm.GunWithArmItem;

/**
 * Stops the vanilla drop-down animation when firing.
 */
@Mixin(FirstPersonHandsAndItems.class)
public class HeldItemRendererMixin {

    @Shadow
    private float mainHandHeight;

    @Shadow
    private float oMainHandHeight;

    @Shadow
    private float offHandHeight;

    @Shadow
    private float oOffHandHeight;

    @Shadow
    private ItemStack mainHandItem;

    @Shadow
    private ItemStack offHandItem;

    @Inject(method = "tick", at = @At("TAIL"))
    private void azexamples$cancelAnimation(LocalPlayer player, CallbackInfo ci) {
        var mainHandStack = player.getMainHandItem();
        var offHandStack = player.getOffhandItem();

        if (
            mainHandItem.getItem() instanceof GunWithArmItem
                && ItemStack.isSameItem(mainHandItem, mainHandStack)
        ) {
            mainHandHeight = 1.0F;
            oMainHandHeight = 1.0F;
            mainHandItem = mainHandStack;
        }

        if (
            offHandItem.getItem() instanceof GunWithArmItem
                && ItemStack.isSameItem(offHandItem, offHandStack)
        ) {
            offHandHeight = 1.0F;
            oOffHandHeight = 1.0F;
            offHandItem = offHandStack;
        }
    }
}

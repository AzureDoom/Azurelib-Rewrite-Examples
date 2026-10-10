package mod.azure.azexamples.mixins;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import mod.azure.azexamples.items.gunwitharm.GunWithArmItem;

/**
 * Stops the "re-equip" dip of the held gun whenever its NBT changes (AzureLib's item IDs and animation commands touch
 * the stack).
 */
@Mixin(ItemRenderer.class)
public abstract class ItemRendererMixin {

    @Shadow
    private Minecraft mc;

    @Shadow
    private ItemStack itemToRender;

    @Shadow
    private float equippedProgress;

    @Shadow
    private float prevEquippedProgress;

    @Inject(method = "updateEquippedItem", at = @At("TAIL"))
    private void azexamples$cancelAnimation(CallbackInfo ci) {
        if (this.mc.thePlayer == null) {
            return;
        }

        ItemStack held = this.mc.thePlayer.inventory.getCurrentItem();

        if (
            held != null && this.itemToRender != null && held.getItem() instanceof GunWithArmItem && held
                .getItem() == this.itemToRender.getItem()
        ) {
            this.equippedProgress = 1.0F;
            this.prevEquippedProgress = 1.0F;
            this.itemToRender = held;
        }
    }
}

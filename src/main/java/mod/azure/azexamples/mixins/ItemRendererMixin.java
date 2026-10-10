package mod.azure.azexamples.mixins;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import mod.azure.azexamples.items.gunwitharm.GunWithArmItem;

@Mixin(ItemRenderer.class)
public abstract class ItemRendererMixin {

    @Shadow
    @Final
    private Minecraft mc;

    @Shadow
    private ItemStack itemStackMainHand;

    @Shadow
    private ItemStack itemStackOffHand;

    @Shadow
    private float equippedProgressMainHand;

    @Shadow
    private float prevEquippedProgressMainHand;

    @Shadow
    private float equippedProgressOffHand;

    @Shadow
    private float prevEquippedProgressOffHand;

    @Inject(method = "updateEquippedItem", at = @At("TAIL"))
    private void azexamples$cancelAnimation(CallbackInfo ci) {
        EntityPlayerSP player = this.mc.player;

        if (player == null) {
            return;
        }

        ItemStack mainHand = player.getHeldItemMainhand();
        ItemStack offHand = player.getHeldItemOffhand();

        if (
            this.itemStackMainHand.getItem() instanceof GunWithArmItem && ItemStack.areItemsEqual(
                this.itemStackMainHand,
                mainHand
            )
        ) {
            this.equippedProgressMainHand = 1.0F;
            this.prevEquippedProgressMainHand = 1.0F;
            this.itemStackMainHand = mainHand;
        }

        if (
            this.itemStackOffHand.getItem() instanceof GunWithArmItem && ItemStack.areItemsEqual(
                this.itemStackOffHand,
                offHand
            )
        ) {
            this.equippedProgressOffHand = 1.0F;
            this.prevEquippedProgressOffHand = 1.0F;
            this.itemStackOffHand = offHand;
        }
    }
}

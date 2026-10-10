package mod.azure.azexamples.mixins;

import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import mod.azure.azexamples.items.gunwitharm.GunWithArmItem;

@Mixin(EntityVillager.class)
public abstract class EntityVillagerMixin {

    @Inject(method = "processInteract", at = @At("HEAD"), cancellable = true)
    private void azexamples$noTradeWithGun(EntityPlayer player, EnumHand hand, CallbackInfoReturnable<Boolean> cir) {
        ItemStack itemStack = player.getHeldItem(hand);

        if (itemStack.getItem() instanceof GunWithArmItem) {
            cir.setReturnValue(false);
        }
    }
}

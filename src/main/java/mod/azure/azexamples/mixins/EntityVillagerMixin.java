package mod.azure.azexamples.mixins;

import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import mod.azure.azexamples.items.gunwitharm.GunWithArmItem;

/**
 * Right clicking a villager with the peacemaker doesn't open trading.
 */
@Mixin(EntityVillager.class)
public abstract class EntityVillagerMixin {

    @Inject(method = "interact", at = @At("HEAD"), cancellable = true)
    private void azexamples$noTradeWithGun(EntityPlayer player, CallbackInfoReturnable<Boolean> cir) {
        ItemStack itemStack = player.getCurrentEquippedItem();

        if (itemStack != null && itemStack.getItem() instanceof GunWithArmItem) {
            cir.setReturnValue(false);
        }
    }
}

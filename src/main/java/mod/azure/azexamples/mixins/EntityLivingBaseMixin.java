package mod.azure.azexamples.mixins;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import mod.azure.azexamples.items.gunwitharm.GunWithArmItem;

@Mixin(EntityLivingBase.class)
public abstract class EntityLivingBaseMixin {

    @Inject(method = "swingArm", at = @At("HEAD"), cancellable = true)
    private void azexamples$noGunSwing(EnumHand hand, CallbackInfo ci) {
        Object self = this;

        if (self instanceof EntityPlayer && GunWithArmItem.isUsingGun((EntityPlayer) self)) {
            ci.cancel();
        }
    }
}

package mod.azure.azexamples.mixins;

import net.minecraft.entity.EntityLivingBase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import mod.azure.azexamples.items.gunwitharm.GunWithArmItem;

/**
 * Players don't swing their arm while using the peacemaker.
 */
@Mixin(EntityLivingBase.class)
public abstract class EntityLivingBaseMixin {

    @Inject(method = "swingItem", at = @At("HEAD"), cancellable = true)
    private void azexamples$noGunSwing(CallbackInfo ci) {
        if (GunWithArmItem.isUsingGun((EntityLivingBase) (Object) this)) {
            ci.cancel();
        }
    }
}

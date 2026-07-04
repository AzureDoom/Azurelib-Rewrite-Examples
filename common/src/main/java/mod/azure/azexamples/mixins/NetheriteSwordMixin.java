package mod.azure.azexamples.mixins;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import mod.azure.azexamples.items.netheritereplace.NetheriteSwordAnimationDespatcher;

@Mixin(Item.class)
public abstract class NetheriteSwordMixin {

    private NetheriteSwordAnimationDespatcher dispatcher;

    private boolean isPlayingAnimation = false;

    private long lastAnimationTime = 0;

    @Inject(method = "<init>", at = @At("TAIL"))
    public void azexamples$init(CallbackInfo info) {
        this.dispatcher = new NetheriteSwordAnimationDespatcher();
    }

    @Inject(method = "postHurtEnemy", at = @At("TAIL"))
    public void azexamples$postHurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker, CallbackInfo ci) {
        if (
            !attacker.level().isClientSide() && attacker.getItemInHand(attacker.getUsedItemHand())
                .is(Items.NETHERITE_SWORD)
        ) {
            dispatcher.serverOpening(attacker, attacker.getItemInHand(attacker.getUsedItemHand()));
            isPlayingAnimation = true;
        }
    }
}

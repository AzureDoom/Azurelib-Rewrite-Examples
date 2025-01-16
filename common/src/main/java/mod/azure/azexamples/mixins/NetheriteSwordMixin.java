package mod.azure.azexamples.mixins;

import mod.azure.azurelib.common.internal.common.AzureLib;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import mod.azure.azexamples.items.netheritereplace.NetheriteSwordAnimationDespatcher;

@Mixin(SwordItem.class)
public abstract class NetheriteSwordMixin extends Item {

    private NetheriteSwordAnimationDespatcher dispatcher;

    private boolean isPlayingAnimation = false;

    private long lastAnimationTime = 0;

    public NetheriteSwordMixin(Properties properties) {
        super(properties);
        this.dispatcher = new NetheriteSwordAnimationDespatcher();
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    public void azexample_Init(CallbackInfo info) {
        this.dispatcher = new NetheriteSwordAnimationDespatcher();
    }

    @Inject(method = "postHurtEnemy", at = @At("TAIL"))
    public void azexample_postHurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker, CallbackInfo ci) {
        if (
            !attacker.level().isClientSide() && attacker.getItemInHand(attacker.getUsedItemHand())
                .is(Items.NETHERITE_SWORD)
        ) {
            dispatcher.serverOpening(attacker, attacker.getItemInHand(attacker.getUsedItemHand()));
            isPlayingAnimation = true;
            AzureLib.LOGGER.info("Hey I'm called!");
        }
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        // if (!level.isClientSide && entity instanceof Player player &&
        // player.getItemInHand(player.getUsedItemHand()).is(Items.NETHERITE_SWORD) && !isPlayingAnimation) {
        // lastAnimationTime++;
        // if (lastAnimationTime > 40) {
        // dispatcher.serverClosed(player, player.getItemInHand(player.getUsedItemHand()));
        // isPlayingAnimation = false;
        // }
        // }
        super.inventoryTick(stack, level, entity, slotId, isSelected);
    }
}

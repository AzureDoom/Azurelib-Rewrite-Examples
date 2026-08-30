package mod.azure.azexamples.mixins;

import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import mod.azure.azexamples.items.netheritereplace.armor.NetheriteArmorAnimationDispatcher;

@Mixin(Item.class)
public abstract class NetheriteArmorMixin {

    @Unique
    private NetheriteArmorAnimationDispatcher dispatcher;

    @Inject(method = "<init>", at = @At("TAIL"))
    public void azexamples$init(CallbackInfo info) {
        this.dispatcher = new NetheriteArmorAnimationDispatcher();
    }

    // TODO: Fix for 26.2
    // @Override
    // public void inventoryTick(ItemStack stack, ServerLevel level, Entity entity, Slot slot) {
    // if (!level.isClientSide() && entity instanceof Player player) {
    // player.getArmorSlots().forEach(wornArmor -> {
    // if (wornArmor != null && wornArmor.is(Items.NETHERITE_CHESTPLATE)) {
    // dispatcher.serverIdleArmor(player, wornArmor);
    // }
    // });
    // }
    // }
}

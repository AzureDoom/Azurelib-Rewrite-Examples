package mod.azure.azexamples.mixins;

import mod.azure.azurelib.platform.mc.Mc;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import mod.azure.azexamples.items.armors.DoomicornArmor;
import mod.azure.azexamples.items.diamondreplace.armor.DiamondArmorAnimationDispatcher;
import mod.azure.azexamples.platform.ExMc;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin_ArmorTick {

    @org.spongepowered.asm.mixin.Unique
    private static final DiamondArmorAnimationDispatcher AZEXAMPLES$DIAMOND = new DiamondArmorAnimationDispatcher();

    @Inject(method = "tick", at = @At("TAIL"))
    private void azexamples$armorTick(CallbackInfo ci) {
        PlayerEntity player = (PlayerEntity) (Object) this;
        if (Mc.isClientSide(Mc.world(player))) {
            return;
        }

        // Beta's PlayerInventory#armor: 0 boots, 1 leggings, 2 chestplate, 3 helmet.
        for (int i = 0; i < 4; i++) {
            ItemStack stack = Mc.armor(player, i);
            if (stack != null && Mc.item(stack) instanceof DoomicornArmor) {
                ((DoomicornArmor) Mc.item(stack)).armorTick(player, stack);
            }
        }

        ItemStack chest = Mc.armor(player, 2);
        if (chest != null && Mc.item(chest) == ExMc.diamondChestplate()) {
            AZEXAMPLES$DIAMOND.serverIdleArmor(player, chest);
        }
    }
}

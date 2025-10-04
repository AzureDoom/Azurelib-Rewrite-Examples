package mod.azure.azexamples.mixins;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import mod.azure.azexamples.items.netheritereplace.armor.NetheriteArmorAnimationDispatcher;

@Mixin(ArmorItem.class)
public abstract class NetheriteArmorMixin extends Item {

    private NetheriteArmorAnimationDispatcher dispatcher;

    public NetheriteArmorMixin(Properties properties) {
        super(properties);
        this.dispatcher = new NetheriteArmorAnimationDispatcher();
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    public void azexamples$init(CallbackInfo info) {
        this.dispatcher = new NetheriteArmorAnimationDispatcher();
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        if (!level.isClientSide && entity instanceof Player player) {
            player.getArmorSlots().forEach(wornArmor -> {
                if (wornArmor != null && wornArmor.is(Items.NETHERITE_CHESTPLATE)) {
                    dispatcher.serverIdleArmor(player, wornArmor);
                }
            });
        }
    }
}

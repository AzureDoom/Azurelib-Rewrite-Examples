package mod.azure.azexamples.mixins;

import mod.azure.azexamples.items.netheritereplace.NetheriteSwordAnimationDespatcher;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SwordItem.class)
public abstract class NetheriteSwordMixin extends Item {

    private NetheriteSwordAnimationDespatcher dispatcher;
    private boolean isPlayingAnimation = false;

    public NetheriteSwordMixin(Properties properties) {
        super(properties);
        this.dispatcher = new NetheriteSwordAnimationDespatcher();
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    public void azexample_Init(CallbackInfo info) {
        this.dispatcher = new NetheriteSwordAnimationDespatcher();
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        if (isSelected && !level.isClientSide && stack.is(Items.NETHERITE_SWORD) && !isPlayingAnimation) {
            dispatcher.serverOpening(entity, stack);
            isPlayingAnimation = true;
        }
        if (isPlayingAnimation && !isSelected) {
            isPlayingAnimation = false;
        }
    }
}

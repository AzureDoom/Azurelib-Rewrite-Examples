package mod.azure.azexamples.items;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class PistolItem extends Item {

    private final PistolAnimationDispatcher dispatcher;

    public PistolItem() {
        this.setMaxStackSize(1);
        this.dispatcher = new PistolAnimationDispatcher();
    }

    @Override
    public void onUsingTick(ItemStack stack, EntityLivingBase livingEntity, int remainingUseDuration) {
        super.onUsingTick(stack, livingEntity, remainingUseDuration);

        if (livingEntity instanceof EntityPlayer && !livingEntity.world.isRemote) {
            dispatcher.serverFire(livingEntity, stack);
        }
    }

    @Override
    @Nonnull
    public ActionResult<ItemStack> onItemRightClick(@Nonnull World world, EntityPlayer user, @Nonnull EnumHand hand) {
        ItemStack itemStack = user.getHeldItem(hand);
        user.setActiveHand(hand);
        return new ActionResult<>(EnumActionResult.SUCCESS, itemStack);
    }

    @Override
    public int getMaxItemUseDuration(@Nonnull ItemStack stack) {
        return 72000;
    }
}

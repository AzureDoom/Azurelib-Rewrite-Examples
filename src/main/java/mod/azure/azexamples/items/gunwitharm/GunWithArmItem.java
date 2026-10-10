package mod.azure.azexamples.items.gunwitharm;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

import mod.azure.azurelib.util.client.ClientUtils;

public class GunWithArmItem extends Item {

    final GunWithArmDispatcher dispatcher;

    public GunWithArmItem() {
        this.setMaxStackSize(1);
        this.dispatcher = new GunWithArmDispatcher();
    }

    @Override
    public void onUsingTick(ItemStack stack, EntityLivingBase livingEntity, int remainingUseDuration) {
        if (livingEntity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) livingEntity;

            if (!player.getCooldownTracker().hasCooldown(stack.getItem())) {
                fire(player.world, player, stack);
            }
        }

        super.onUsingTick(stack, livingEntity, remainingUseDuration);
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

    private void fire(@Nonnull World world, @Nonnull EntityPlayer player, ItemStack itemStack) {
        dispatcher.serverFire(player, itemStack);
        player.getCooldownTracker().setCooldown(itemStack.getItem(), 5);

        if (world.isRemote) {
            ClientUtils.getClientPlayer().turn(getRecoilX(player) * 5, -getRecoilY() * 5);
        }
    }

    public static float getRecoilX(EntityPlayer player) {
        float baseRecoilX = player.world.rand.nextBoolean() ? 1f : -1f;
        return baseRecoilX / 2;
    }

    public static float getRecoilY() {
        return 2.5f / 2;
    }

    /**
     * True if the entity is currently using (holding right click with) a gun. Used by the client mixins.
     */
    public static boolean isUsingGun(EntityLivingBase entity) {
        return entity != null && entity.getActiveItemStack().getItem() instanceof GunWithArmItem;
    }
}

package mod.azure.azexamples.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class PistolItem extends Item {

    private final PistolAnimationDispatcher dispatcher;

    public PistolItem() {
        this.setMaxStackSize(1);
        this.dispatcher = new PistolAnimationDispatcher();
    }

    @Override
    public void onUsingTick(ItemStack stack, EntityPlayer player, int count) {
        super.onUsingTick(stack, player, count);

        if (!player.worldObj.isRemote) {
            dispatcher.serverFire(player, stack);
        }
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        player.setItemInUse(stack, this.getMaxItemUseDuration(stack));
        return stack;
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack) {
        return 72000;
    }
}

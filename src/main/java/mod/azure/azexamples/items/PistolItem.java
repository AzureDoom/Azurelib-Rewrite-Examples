package mod.azure.azexamples.items;

import mod.azure.azurelib.platform.mc.Mc;
import net.minecraft.entity.mob.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class PistolItem extends Item {

    private final PistolAnimationDispatcher dispatcher;

    public PistolItem(int id) {
        super(id);
        this.setMaxStackSize(1);
        this.dispatcher = new PistolAnimationDispatcher();
    }

    @Override
    public ItemStack startUsing(ItemStack stack, World world, PlayerEntity player) {
        if (!Mc.isClientSide(world)) {
            dispatcher.serverFire(player, stack);
        }
        return stack;
    }
}

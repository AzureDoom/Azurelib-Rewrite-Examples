package mod.azure.azexamples.items.armors;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

/**
 * Diamond stats. {@link #onArmorTick} is Forge's per-tick hook for worn armor.
 */
public class DoomicornArmor extends ItemArmor {

    private final DoomicornArmorAnimationDispatcher dispatcher;

    /**
     * @param armorType 0 helmet, 1 chestplate, 2 leggings, 3 boots
     */
    public DoomicornArmor(int armorType) {
        super(ArmorMaterial.DIAMOND, 3, armorType);
        this.setMaxStackSize(1);
        this.dispatcher = new DoomicornArmorAnimationDispatcher();
    }

    @Override
    public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {
        if (!world.isRemote) {
            dispatcher.serverIdle(player, itemStack);
        }
    }
}

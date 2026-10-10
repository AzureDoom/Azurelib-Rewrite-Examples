package mod.azure.azexamples.items.armors;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

/**
 * Diamond stats (1.12.2 has no netherite). {@link #onArmorTick} is Forge's per-tick hook for worn armor, replacing the
 * 1.18 inventory-tick loop over the armor slots.
 */
public class DoomicornArmor extends ItemArmor {

    private final DoomicornArmorAnimationDispatcher dispatcher;

    public DoomicornArmor(EntityEquipmentSlot equipmentSlot) {
        super(ArmorMaterial.DIAMOND, 3, equipmentSlot);
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

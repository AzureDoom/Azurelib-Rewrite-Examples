package mod.azure.azexamples.entities.marine;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

/**
 * Right-click a marine with any item to equip a copy of it in the matching slot.
 */
public class MarineEntity extends EntityMob {

    public MarineEntity(World world) {
        super(world);
        this.setSize(0.6F, 1.8F);
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(5, new EntityAIWander(this, 0.3));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(7, new EntityAILookIdle(this));
    }

    /**
     * No AI at all. Without {@code isAIEnabled()} a 1.7.10 mob runs the old built-in AI from this method (random
     * wandering, hunting players), so leaving it empty keeps the marine standing still.
     */
    @Override
    protected void updateEntityActionState() {}

    @Override
    public boolean interact(EntityPlayer player) {
        ItemStack stack = player.getCurrentEquippedItem();

        if (this.worldObj.isRemote || stack == null)
            return super.interact(player);

        // 0 held item, 1-4 boots..helmet
        int slot = EntityLiving.getArmorPosition(stack);
        this.setCurrentItemOrArmor(slot, new ItemStack(stack.getItem(), 1, stack.getItem().getDamage(stack)));
        return true;
    }

    /** 1.7.10 has no off hand or handedness; the marine is always right-handed. */
    public boolean isLeftHanded() {
        return false;
    }
}

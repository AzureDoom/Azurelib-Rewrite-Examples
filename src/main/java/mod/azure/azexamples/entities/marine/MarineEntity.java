package mod.azure.azexamples.entities.marine;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class MarineEntity extends EntityMob {

    public MarineEntity(World world) {
        super(world);
        this.setSize(0.6F, 1.8F);
    }

    /**
     * Wandering only, so the walk cycle has something to show. Aiming and firing are driven entirely by
     * {@link MarineAnimator} from the held item, independent of movement.
     */
    @Override
    protected void initEntityAI() {
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(7, new EntityAIWander(this, 0.3D));
        this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(7, new EntityAILookIdle(this));
    }

    @Override
    protected boolean processInteract(@Nonnull EntityPlayer player, @Nonnull EnumHand hand) {
        ItemStack stack = player.getHeldItem(hand);

        if (this.world.isRemote || stack.isEmpty())
            return super.processInteract(player, hand);

        EntityEquipmentSlot slot = EntityLiving.getSlotForItemStack(stack);
        this.setItemStackToSlot(slot, new ItemStack(stack.getItem(), 1, stack.getMetadata()));
        return true;
    }
}

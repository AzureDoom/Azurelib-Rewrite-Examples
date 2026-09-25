package mod.azure.azexamples.entities.marine;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class MarineEntity extends Monster {

    public MarineEntity(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
    }

    /**
     * Handles the interaction between a player and this entity when the player interacts with it using a specific hand.
     * This method allows the entity to equip an item based on the item the player is holding.
     *
     * @param player the player interacting with the entity
     * @param hand   the hand the player is using to interact (e.g., main hand or off hand)
     * @return {@link InteractionResult} indicating the result of the interaction, e.g., SUCCESS if the entity
     *         successfully equips the item
     */
    @Override
    protected @NotNull InteractionResult mobInteract(Player player, @NotNull InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (this.level.isClientSide() || stack.isEmpty())
            return super.mobInteract(player, hand);

        EquipmentSlot slot = getEquipmentSlotForItem(stack);

        setItemSlot(slot, new ItemStack(stack.getItem(), 1));

        return InteractionResult.SUCCESS;
    }
}

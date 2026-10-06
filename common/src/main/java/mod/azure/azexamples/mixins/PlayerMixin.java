package mod.azure.azexamples.mixins;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.component.SwingAnimation;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.NonNull;
import org.spongepowered.asm.mixin.Mixin;

import mod.azure.azexamples.items.gunwitharm.GunWithArmItem;

@Mixin(Player.class)
public abstract class PlayerMixin extends LivingEntity {

    protected PlayerMixin(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public boolean swing(
        @NonNull InteractionHand hand,
        @NonNull SwingAnimation animation,
        boolean sendToSwingingEntity
    ) {
        if (this.getItemInHand(hand).getItem() instanceof GunWithArmItem) {
            return false;
        }

        return super.swing(hand, animation, sendToSwingingEntity);
    }
}

package mod.azure.azexamples.mixins;

import mod.azure.azexamples.items.gunwitharm.GunWithArmItem;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Player.class)
public abstract class PlayerMixin extends LivingEntity {

    protected PlayerMixin(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public void swing(InteractionHand hand) {
        if (!(this.getUseItem().getItem() instanceof GunWithArmItem))
            super.swing(hand);
    }
}

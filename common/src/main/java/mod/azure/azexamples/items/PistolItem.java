package mod.azure.azexamples.items;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

public class PistolItem extends Item {

    private final PistolAnimationDispatcher dispatcher;

    public PistolItem(Properties properties) {
        super(properties);
        this.dispatcher = new PistolAnimationDispatcher();
    }

    @Override
    public void onUseTick(
        @NotNull Level level,
        @NotNull LivingEntity livingEntity,
        @NotNull ItemStack stack,
        int remainingUseDuration
    ) {
        super.onUseTick(level, livingEntity, stack, remainingUseDuration);
        if (livingEntity instanceof Player player && !level.isClientSide()) {
            dispatcher.serverFire(player, stack);
        }
    }

    @Override
    public @NonNull InteractionResult useOn(@NonNull UseOnContext context) {
        return super.useOn(context);
    }

    @Override
    public int getUseDuration(@NotNull ItemStack stack, @NotNull LivingEntity entity) {
        return 72000;
    }

    @Override
    public @NonNull InteractionResult use(
        @NonNull Level level,
        Player player,
        @NonNull InteractionHand hand
    ) {
        player.startUsingItem(hand);

        return InteractionResult.CONSUME;
    }
}

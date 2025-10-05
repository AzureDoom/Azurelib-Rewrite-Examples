package mod.azure.azexamples.items.gunwitharm;

import mod.azure.azurelib.util.ClientUtils;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import mod.azure.azexamples.services.AzExampleServices;

public class GunWithArmItem extends Item {

    GunWithArmDispatcher dispatcher;

    public GunWithArmItem() {
        super(new Properties().stacksTo(1).tab(AzExampleServices.COMMON_REGISTRY.getCreativeTab()));
        this.dispatcher = new GunWithArmDispatcher();
    }

    @Override
    public void inventoryTick(
        @NotNull ItemStack stack,
        Level level,
        @NotNull Entity entity,
        int slotId,
        boolean isSelected
    ) {
        if (
            !level.isClientSide() && stack.is(this) && entity instanceof LivingEntity livingEntity &&
                !livingEntity.isUsingItem() && livingEntity instanceof Player player &&
                !player.getCooldowns().isOnCooldown(stack.getItem())
        ) {
            dispatcher.sendIdle(entity, stack);
        }
        super.inventoryTick(stack, level, entity, slotId, isSelected);
    }

    @Override
    public void onUseTick(
        @NotNull Level level,
        @NotNull LivingEntity livingEntity,
        @NotNull ItemStack stack,
        int remainingUseDuration
    ) {
        if (livingEntity instanceof Player player && (!player.getCooldowns().isOnCooldown(stack.getItem()))) {
            fire(level, player, stack);
        }
        super.onUseTick(level, livingEntity, stack, remainingUseDuration);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(
        @NotNull Level world,
        Player user,
        @NotNull InteractionHand hand
    ) {
        final var itemStack = user.getItemInHand(hand);
        user.startUsingItem(hand);
        return InteractionResultHolder.consume(itemStack);
    }

    @Override
    public int getUseDuration(@NotNull ItemStack stack) {
        return 72000;
    }

    /**
     * Handles the operation of firing a weapon, including applying damage to an entity, triggering server-side fire
     * logic, managing item cooldowns, and applying client-side recoil effects.
     *
     * @param level     The level in which the operation takes place. Cannot be null.
     * @param player    The player triggering the firing action. Cannot be null.
     * @param itemStack The item stack representing the weapon being fired.
     */
    private void fire(@NotNull Level level, @NotNull Player player, ItemStack itemStack) {
        dispatcher.serverFire(player, itemStack);
        player.getCooldowns().addCooldown(itemStack.getItem(), 5);
        if (level.isClientSide()) {
            ClientUtils.getClientPlayer().turn(getRecoilX(player) * 5, -getRecoilY() * 5);
        }
    }

    /**
     * Determines and retrieves the horizontal recoil value for a weapon. The recoil direction is randomized and
     * adjusted before being returned.
     *
     * @param player The player using the weapon, whose context is required to calculate randomness.
     * @return The horizontal recoil value as a float, representing the left or right deviation.
     */
    public static float getRecoilX(Player player) {
        var baseRecoilX = player.level.getRandom().nextBoolean() ? 1f : -1f;
        return baseRecoilX / 2;
    }

    /**
     * Retrieves the vertical recoil value for the weapon.
     *
     * @return The vertical recoil as a float value.
     */
    public static float getRecoilY() {
        return 2.5f / 2;
    }
}

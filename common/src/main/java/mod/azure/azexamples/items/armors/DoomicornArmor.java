package mod.azure.azexamples.items.armors;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.equipment.ArmorMaterials;
import net.minecraft.world.item.equipment.ArmorType;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.Nullable;

public class DoomicornArmor extends Item {

    private final DoomicornArmorAnimationDispatcher dispatcher;

    public DoomicornArmor(ArmorType type) {
        super(new Properties().humanoidArmor(ArmorMaterials.NETHERITE, type).stacksTo(1));
        this.dispatcher = new DoomicornArmorAnimationDispatcher();
    }

    @Override
    public void inventoryTick(
        @NotNull ItemStack stack,
        @NotNull ServerLevel level,
        @NotNull Entity entity,
        @Nullable EquipmentSlot slot
    ) {
        if (!level.isClientSide() && entity instanceof LivingEntity livingEntity) {
            // TODO: Port for 26.2
            // livingEntity.getArmorSlots().forEach(wornArmor -> {
            // if (wornArmor != null && wornArmor.is(this)) {
            // dispatcher.serverIdle(livingEntity, wornArmor);
            // }
            // });
        }
    }

    // TODO: Find 26.2 replacement
    // @Override
    // public @NotNull InteractionResultHolder<ItemStack> swapWithEquipmentSlot(
    // @NotNull Item item,
    // @NotNull Level level,
    // @NotNull Player player,
    // @NotNull InteractionHand hand
    // ) {
    // InteractionResultHolder<ItemStack> result = super.swapWithEquipmentSlot(item, level, player, hand);
    //
    // if (!level.isClientSide) {
    // EquipmentSlot slot = getEquipmentSlot();
    // ItemStack itemStack = player.getItemBySlot(slot);
    // dispatcher.serverEquipHelmet(player, itemStack);
    // }
    //
    // return result;
    // }
}

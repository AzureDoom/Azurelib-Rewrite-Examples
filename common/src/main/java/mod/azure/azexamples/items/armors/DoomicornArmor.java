package mod.azure.azexamples.items.armors;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import mod.azure.azexamples.services.AzExampleServices;

public class DoomicornArmor extends ArmorItem {

    private final DoomicornArmorAnimationDispatcher dispatcher;

    public DoomicornArmor(EquipmentSlot equipmentSlot) {
        super(
            ArmorMaterials.NETHERITE,
            equipmentSlot,
            new Properties().stacksTo(1).tab(AzExampleServices.COMMON_REGISTRY.getCreativeTab())
        );
        this.dispatcher = new DoomicornArmorAnimationDispatcher();
    }

    @Override
    public void inventoryTick(
        @NotNull ItemStack stack,
        Level level,
        @NotNull Entity entity,
        int slotId,
        boolean isSelected
    ) {
        if (!level.isClientSide && entity instanceof Player player) {
            player.getArmorSlots().forEach(wornArmor -> {
                if (wornArmor != null && wornArmor.is(this)) {
                    dispatcher.serverIdle(player, wornArmor);
                }
            });
        }
    }
}

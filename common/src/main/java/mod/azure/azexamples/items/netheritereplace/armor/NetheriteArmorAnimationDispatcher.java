package mod.azure.azexamples.items.netheritereplace.armor;

import mod.azure.azexamples.CommonStrings;
import mod.azure.azurelib.core2.animation.dispatch.command.AzCommand;
import mod.azure.azurelib.core2.animation.primitive.AzLoopType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;

public class NetheriteArmorAnimationDispatcher {

    private static final AzCommand EQUIP = AzCommand.create(CommonStrings.BASE_CONTROLLER, CommonStrings.IDLE_ANIMATION_NAME, AzLoopType.HOLD_ON_LAST_FRAME);

    public void serverIdleArmor(Entity entity, ItemStack itemStack) {
        EQUIP.sendForItem(entity, itemStack);
    }
}

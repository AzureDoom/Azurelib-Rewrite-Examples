package mod.azure.azexamples.items.armors;

import mod.azure.azexamples.CommonStrings;
import mod.azure.azurelib.core2.animation.dispatch.command.AzCommand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;

public class DoomicornArmorAnimationDispatcher {

    private static final AzCommand EQUIP = AzCommand.create(CommonStrings.BASE_CONTROLLER, CommonStrings.EQUIP_ANIMATION_NAME);


    public void serverEquipHelmet(Entity entity, ItemStack itemStack) {
        EQUIP.sendForItem(entity, itemStack);
    }
}

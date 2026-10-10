package mod.azure.azexamples.events;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import mod.azure.azexamples.items.diamondreplace.armor.DiamondArmorAnimationDispatcher;

/**
 * Plays the replacement chestplate's animation while a diamond chestplate is worn.
 */
public final class DiamondArmorEvents {

    private static final DiamondArmorAnimationDispatcher DISPATCHER = new DiamondArmorAnimationDispatcher();

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END || event.player.worldObj.isRemote) {
            return;
        }

        // getCurrentArmor: 0 boots, 1 leggings, 2 chestplate, 3 helmet
        ItemStack chest = event.player.getCurrentArmor(2);

        if (chest != null && chest.getItem() == Items.diamond_chestplate) {
            DISPATCHER.serverIdleArmor(event.player, chest);
        }
    }
}

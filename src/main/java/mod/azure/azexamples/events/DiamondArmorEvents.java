package mod.azure.azexamples.events;

import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import mod.azure.azexamples.CommonStrings;
import mod.azure.azexamples.items.diamondreplace.armor.DiamondArmorAnimationDispatcher;

@Mod.EventBusSubscriber(modid = CommonStrings.MOD_ID)
public final class DiamondArmorEvents {

    private static final DiamondArmorAnimationDispatcher DISPATCHER = new DiamondArmorAnimationDispatcher();

    private DiamondArmorEvents() {}

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END || event.player.world.isRemote) {
            return;
        }

        ItemStack chest = event.player.getItemStackFromSlot(EntityEquipmentSlot.CHEST);

        if (!chest.isEmpty() && chest.getItem() == Items.DIAMOND_CHESTPLATE) {
            DISPATCHER.serverIdleArmor(event.player, chest);
        }
    }
}

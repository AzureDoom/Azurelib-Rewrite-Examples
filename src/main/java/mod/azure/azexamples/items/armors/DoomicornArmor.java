package mod.azure.azexamples.items.armors;

import mod.azure.azurelib.platform.mc.Mc;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.modificationstation.stationapi.api.template.item.ItemTemplate;
import net.modificationstation.stationapi.api.util.Identifier;

/**
 * Beta armor: {@code ArmorItem(id, material level, texture index, slot)} with diamond stats. The texture index only
 * matters to vanilla's armor renderer, which AzureLib replaces for these items.
 * <p>
 * Beta has no armor tick hook, so {@code PlayerEntityMixin_ArmorTick} calls {@link #armorTick} for worn pieces.
 */
public class DoomicornArmor extends ArmorItem {

    private static final int DIAMOND = 3;

    private final DoomicornArmorAnimationDispatcher dispatcher;

    public DoomicornArmor(Identifier identifier, int slot) {
        super(ItemTemplate.getNextId(), DIAMOND, DIAMOND, slot);
        ItemTemplate.onConstructor(this, identifier);
        this.dispatcher = new DoomicornArmorAnimationDispatcher();
    }

    public void armorTick(PlayerEntity player, ItemStack itemStack) {
        if (!Mc.isClientSide(Mc.world(player))) {
            dispatcher.serverIdle(player, itemStack);
        }
    }
}

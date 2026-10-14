package mod.azure.azexamples.items;

import mod.azure.azurelib.platform.mc.Mc;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.template.item.ItemTemplate;
import net.modificationstation.stationapi.api.util.Identifier;

public class PistolItem extends Item {

    private final PistolAnimationDispatcher dispatcher;

    public PistolItem(Identifier identifier) {
        super(ItemTemplate.getNextId());
        ItemTemplate.onConstructor(this, identifier);
        this.setMaxCount(1);
        this.dispatcher = new PistolAnimationDispatcher();
    }

    @Override
    public ItemStack use(ItemStack stack, World world, PlayerEntity player) {
        if (!Mc.isClientSide(world)) {
            dispatcher.serverFire(player, stack);
        }
        return stack;
    }
}

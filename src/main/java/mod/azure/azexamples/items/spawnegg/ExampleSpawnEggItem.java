package mod.azure.azexamples.items.spawnegg;

import mod.azure.azurelib.platform.mc.Mc;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.template.item.ItemTemplate;
import net.modificationstation.stationapi.api.util.Identifier;

import mod.azure.azexamples.platform.ExMc;

/**
 * A spawn egg for one example entity, spawning it on the clicked face. Beta has no spawn eggs, so every egg reuses the
 * vanilla egg's icon.
 */
public class ExampleSpawnEggItem extends Item {

    private static final int[][] OFFSETS = {
        { 0, -1, 0 },
        { 0, 1, 0 },
        { 0, 0, -1 },
        { 0, 0, 1 },
        { -1, 0, 0 },
        { 1, 0, 0 } };

    private final Class<? extends Entity> entityType;

    public ExampleSpawnEggItem(Identifier identifier, Class<? extends Entity> entityType) {
        super(ItemTemplate.getNextId());
        ItemTemplate.onConstructor(this, identifier);
        this.entityType = entityType;
    }

    @Override
    public int getTextureId(int damage) {
        return ExMc.eggTextureId();
    }

    @Override
    public boolean useOnBlock(ItemStack stack, PlayerEntity player, World world, int x, int y, int z, int side) {
        if (Mc.isClientSide(world)) {
            return true;
        }

        int[] offset = OFFSETS[Math.max(0, Math.min(5, side))];
        try {
            Entity entity = this.entityType.getConstructor(World.class).newInstance(world);
            ExMc.spawn(
                world,
                entity,
                x + offset[0] + 0.5D,
                y + offset[1],
                z + offset[2] + 0.5D,
                ExMc.random(world).nextFloat() * 360.0F
            );
            ExMc.decrementStack(stack);
        } catch (ReflectiveOperationException e) {
            throw new IllegalStateException("Cannot create " + this.entityType, e);
        }
        return true;
    }
}

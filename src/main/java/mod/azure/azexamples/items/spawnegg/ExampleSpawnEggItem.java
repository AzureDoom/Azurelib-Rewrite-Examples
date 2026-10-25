package mod.azure.azexamples.items.spawnegg;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import java.util.List;

import mod.azure.azexamples.CommonStrings;
import mod.azure.azexamples.registry.EntityRegistry;

/**
 * Spawn eggs for the example entities, one damage value per {@link EntityRegistry} entry, drawn with the vanilla egg
 * textures like {@code ItemMonsterPlacer}.
 */
public class ExampleSpawnEggItem extends Item {

    @SideOnly(Side.CLIENT)
    private IIcon overlay;

    public ExampleSpawnEggItem() {
        this.setHasSubtypes(true);
    }

    private static EntityRegistry.Entry entry(ItemStack stack) {
        List<EntityRegistry.Entry> entries = EntityRegistry.entries();
        int index = stack.getItemDamage();
        return index >= 0 && index < entries.size() ? entries.get(index) : null;
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        EntityRegistry.Entry entry = entry(stack);
        String entityName = entry == null
            ? "?"
            : StatCollector.translateToLocal("entity." + CommonStrings.MOD_ID + "." + entry.name + ".name");
        return StatCollector.translateToLocalFormatted(this.getUnlocalizedName() + ".name", entityName);
    }

    @Override
    public boolean onItemUse(
        ItemStack stack,
        EntityPlayer player,
        World world,
        int x,
        int y,
        int z,
        int side,
        float hitX,
        float hitY,
        float hitZ
    ) {
        if (world.isRemote) {
            return true;
        }

        EntityRegistry.Entry entry = entry(stack);

        if (entry == null) {
            return false;
        }

        // Spawn on the clicked face, like vanilla eggs.
        int[][] offsets = { { 0, -1, 0 }, { 0, 1, 0 }, { 0, 0, -1 }, { 0, 0, 1 }, { -1, 0, 0 }, { 1, 0, 0 } };
        int[] offset = offsets[side];

        try {
            Entity entity = entry.type.getConstructor(World.class).newInstance(world);
            entity.setLocationAndAngles(
                x + offset[0] + 0.5D,
                y + offset[1],
                z + offset[2] + 0.5D,
                world.rand.nextFloat() * 360.0F,
                0.0F
            );

            if (entity instanceof EntityLiving) {
                ((EntityLiving) entity).onSpawnWithEgg(null);
            }

            world.spawnEntityInWorld(entity);

            if (!player.capabilities.isCreativeMode) {
                --stack.stackSize;
            }
        } catch (ReflectiveOperationException e) {
            throw new IllegalStateException("Cannot create " + entry.type, e);
        }

        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void getSubItems(Item item, CreativeTabs tab, List list) {
        for (int i = 0; i < EntityRegistry.entries().size(); i++) {
            list.add(new ItemStack(item, 1, i));
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getColorFromItemStack(ItemStack stack, int renderPass) {
        EntityRegistry.Entry entry = entry(stack);
        return entry == null ? 0xFFFFFF : renderPass == 0 ? entry.primaryColor : entry.secondaryColor;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean requiresMultipleRenderPasses() {
        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamageForRenderPass(int damage, int renderPass) {
        return renderPass > 0 ? this.overlay : this.itemIcon;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister register) {
        this.itemIcon = register.registerIcon("spawn_egg");
        this.overlay = register.registerIcon("spawn_egg_overlay");
    }
}

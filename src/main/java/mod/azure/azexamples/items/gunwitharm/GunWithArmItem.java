package mod.azure.azexamples.items.gunwitharm;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.Map;
import java.util.UUID;
import java.util.WeakHashMap;

public class GunWithArmItem extends Item {

    private static final int FIRE_COOLDOWN_TICKS = 5;

    final GunWithArmDispatcher dispatcher;

    /**
     * 1.7.10 has no item cooldown tracker, so the next allowed shot is kept per player (and per side, since the client
     * and server each run this item's logic).
     */
    private final Map<World, Map<UUID, Long>> nextShot = new WeakHashMap<>();

    public GunWithArmItem() {
        this.setMaxStackSize(1);
        this.dispatcher = new GunWithArmDispatcher();
    }

    @Override
    public void onUsingTick(ItemStack stack, EntityPlayer player, int count) {
        Map<UUID, Long> shots = nextShot.computeIfAbsent(player.worldObj, world -> new java.util.HashMap<>());
        long now = player.worldObj.getTotalWorldTime();
        Long allowedAt = shots.get(player.getUniqueID());

        if (allowedAt == null || now >= allowedAt) {
            shots.put(player.getUniqueID(), now + FIRE_COOLDOWN_TICKS);
            fire(player.worldObj, player, stack);
        }

        super.onUsingTick(stack, player, count);
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        player.setItemInUse(stack, this.getMaxItemUseDuration(stack));
        return stack;
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack) {
        return 72000;
    }

    private void fire(World world, EntityPlayer player, ItemStack itemStack) {
        dispatcher.serverFire(player, itemStack);

        if (world.isRemote) {
            // Entity#setAngles is the client's mouse-look turn (yaw, pitch), used here for recoil.
            player.setAngles(getRecoilX(player) * 5, -getRecoilY() * 5);
        }
    }

    public static float getRecoilX(EntityPlayer player) {
        float baseRecoilX = player.worldObj.rand.nextBoolean() ? 1f : -1f;
        return baseRecoilX / 2;
    }

    public static float getRecoilY() {
        return 2.5f / 2;
    }

    /**
     * True if the entity is a player currently using (holding right click with) a gun. Used by the client mixins.
     */
    public static boolean isUsingGun(EntityLivingBase entity) {
        if (!(entity instanceof EntityPlayer)) {
            return false;
        }

        ItemStack inUse = ((EntityPlayer) entity).getItemInUse();
        return inUse != null && inUse.getItem() instanceof GunWithArmItem;
    }
}

package mod.azure.azexamples.items.gunwitharm;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
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
        Map<UUID, Long> shots = nextShot.computeIfAbsent(
            player.worldObj,
            world -> new java.util.HashMap<>()
        );

        long now = player.worldObj.getTotalWorldTime();
        Long allowedAt = shots.get(player.getUniqueID());

        if (allowedAt == null || now >= allowedAt) {
            shots.put(player.getUniqueID(), now + FIRE_COOLDOWN_TICKS);

            MovingObjectPosition hitResult = getGunHitResult(
                player.worldObj,
                player,
                35.0D
            );

            fire(player.worldObj, player, stack, hitResult);
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

    private static MovingObjectPosition getGunHitResult(
        World world,
        EntityPlayer player,
        double range
    ) {
        // Preserve the original eye position as primitive values.
        double startX = player.posX;
        double startY = player.boundingBox.minY + player.getEyeHeight();
        double startZ = player.posZ;

        Vec3 direction = player.getLook(1.0F);

        double dirX = direction.xCoord;
        double dirY = direction.yCoord;
        double dirZ = direction.zCoord;

        double endX = startX + dirX * range;
        double endY = startY + dirY * range;
        double endZ = startZ + dirZ * range;

        // Give the block raycast its own vectors.
        MovingObjectPosition blockHit = world.rayTraceBlocks(
            Vec3.createVectorHelper(startX, startY, startZ),
            Vec3.createVectorHelper(endX, endY, endZ),
            false,
            true,
            false
        );

        // Create fresh vectors for entity intersection checks.
        Vec3 start = Vec3.createVectorHelper(startX, startY, startZ);
        Vec3 end = Vec3.createVectorHelper(endX, endY, endZ);

        double maxDistance = range * range;

        if (blockHit != null && blockHit.hitVec != null) {
            maxDistance = start.squareDistanceTo(blockHit.hitVec);
        }

        AxisAlignedBB searchBox = player.boundingBox
            .addCoord(
                dirX * range,
                dirY * range,
                dirZ * range
            )
            .expand(1.0D, 1.0D, 1.0D);

        Entity closestEntity = null;
        Vec3 closestHitVec = null;
        double closestDistance = maxDistance;

        for (
            Object obj : world.getEntitiesWithinAABBExcludingEntity(
                player,
                searchBox
            )
        ) {
            Entity entity = (Entity) obj;

            if (
                !(entity instanceof EntityLivingBase)
                    || !entity.isEntityAlive()
            ) {
                continue;
            }

            float border = entity.getCollisionBorderSize();

            AxisAlignedBB entityBox = entity.boundingBox.expand(
                border,
                border,
                border
            );

            MovingObjectPosition entityHit = entityBox.calculateIntercept(
                start,
                end
            );

            Vec3 hitVec = null;

            if (entityBox.isVecInside(start)) {
                hitVec = start;
            } else if (entityHit != null) {
                hitVec = entityHit.hitVec;
            }

            if (hitVec != null) {
                double distance = start.squareDistanceTo(hitVec);

                if (distance <= closestDistance) {
                    closestDistance = distance;
                    closestEntity = entity;

                    // Preserve the intersection coordinates.
                    closestHitVec = Vec3.createVectorHelper(
                        hitVec.xCoord,
                        hitVec.yCoord,
                        hitVec.zCoord
                    );
                }
            }
        }

        if (closestEntity != null) {
            return new MovingObjectPosition(
                closestEntity,
                closestHitVec
            );
        }

        return blockHit;
    }

    private void fire(World world, EntityPlayer player, ItemStack itemStack, MovingObjectPosition hitResult) {
        if (!world.isRemote) {
            damageEntity(world, player, hitResult);
        }

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

    public static void damageEntity(
        World world,
        EntityPlayer player,
        MovingObjectPosition hitResult
    ) {
        if (world.isRemote) {
            return;
        }

        if (hitResult == null) {
            return;
        }

        if (
            hitResult.typeOfHit == MovingObjectPosition.MovingObjectType.ENTITY
                && hitResult.entityHit instanceof EntityLivingBase
        ) {

            EntityLivingBase livingEntity =
                (EntityLivingBase) hitResult.entityHit;

            livingEntity.hurtResistantTime = 0;

            livingEntity.attackEntityFrom(
                DamageSource.causePlayerDamage(player),
                5.0F
            );
        }
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

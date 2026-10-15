package mod.azure.azexamples.entities.marauder;

import mod.azure.azurelib.platform.mc.Mc;
import mod.azure.azurelib.util.MoveAnalysis;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;

import mod.azure.azexamples.platform.ExMc;

public class MarauderEntity extends MobEntity {

    private static final int SPAWN_TICKS = 20;

    private static final int AGGRESSIVE = 21;

    private static final int ATTACK_DELAY_TICKS = 5;

    public int MAX_SPAWN_ANIMATION_TICKS = 290;

    public final MarauderAnimationDispatcher animationDispatcher;

    private final MoveAnalysis moveAnalysis;

    private int attackCooldown;

    private int delayBeforeAttack = ATTACK_DELAY_TICKS;

    private boolean triggeredAttackAnimation;

    public MarauderEntity(World world) {
        super(world);
        this.setSize(1.5F, 2.6F);
        this.animationDispatcher = new MarauderAnimationDispatcher(this);
        this.moveAnalysis = new MoveAnalysis(this);
    }

    @Override
    protected void registerSyncedData() {
        super.registerSyncedData();
        this.syncedData.register(SPAWN_TICKS, 0);
        this.syncedData.register(AGGRESSIVE, (byte) 0);
    }

    @Override
    public void tick() {
        super.tick();
        moveAnalysis.update();

        if (Mc.isClientSide(this.world)) {
            return;
        }

        if (this.attackCooldown > 0) {
            this.attackCooldown--;
        }

        // this.syncedData.update(AGGRESSIVE, (byte) (this.walkTarget != null ? 1 : 0));

        if (this.getSpawnTicks() < MAX_SPAWN_ANIMATION_TICKS && Mc.isAlive(this)) {
            this.setSpawnTicks(this.getSpawnTicks() + 1);
            // this.walkTarget = null;
            this.velocityX = 0;
            this.velocityZ = 0;
            this.bodyYaw = 0;
            this.pitch = 0;
            this.yaw = 0;
        }
    }

    // @Override
    protected void targetInSight(Entity target, float distance) {
        if (this.getSpawnTicks() < MAX_SPAWN_ANIMATION_TICKS) {
            return;
        }

        double reach = Mc.width(this) * 2.0F + Mc.width(target);
        if (distance > reach) {
            this.delayBeforeAttack = ATTACK_DELAY_TICKS;
            this.triggeredAttackAnimation = false;
            return;
        }

        if (this.attackCooldown > 0) {
            return;
        }

        if (!this.triggeredAttackAnimation) {
            runAttackAnimations();
            this.triggeredAttackAnimation = true;
        }

        if (this.delayBeforeAttack > 0) {
            this.delayBeforeAttack--;
            return;
        }

        this.attackCooldown = 20;
        ExMc.damage(target, this, 6);
        this.triggeredAttackAnimation = false;
        this.delayBeforeAttack = ATTACK_DELAY_TICKS;
    }

    public boolean isAggressive() {
        return this.syncedData.getByte(AGGRESSIVE) != 0;
    }

    public void updateAnimations() {
        boolean isMovingOnGround = moveAnalysis.isMovingHorizontally() && Mc.onGround(this);

        if (Mc.health(this) <= 0) {
            animationDispatcher.clientDeath();
            return;
        }

        if (this.getSpawnTicks() < MAX_SPAWN_ANIMATION_TICKS) {
            animationDispatcher.clientSpawn();
            return;
        }

        if (isMovingOnGround) {
            if (this.isAggressive()) {
                animationDispatcher.clientRun();
            } else {
                animationDispatcher.clientWalk();
            }
            return;
        }

        if (!this.isAggressive()) {
            animationDispatcher.clientIdle();
        }
    }

    public void runAttackAnimations() {
        animationDispatcher.serverMelee();
    }

    protected void setSpawnTicks(int spawnTicks) {
        this.syncedData.update(SPAWN_TICKS, spawnTicks);
    }

    public int getSpawnTicks() {
        return this.syncedData.getInt(SPAWN_TICKS);
    }

    @Override
    public boolean writeNbt(NbtCompound compound) {
        super.writeNbt(compound);
        compound.putInt("SpawnTicks", this.getSpawnTicks());
        return true;
    }

    @Override
    public void readNbt(NbtCompound compound) {
        super.readNbt(compound);
        this.setSpawnTicks(compound.getInt("SpawnTicks"));
    }
}

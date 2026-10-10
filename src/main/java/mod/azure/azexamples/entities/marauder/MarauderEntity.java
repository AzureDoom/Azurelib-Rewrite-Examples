package mod.azure.azexamples.entities.marauder;

import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import mod.azure.azurelib.util.MoveAnalysis;

import mod.azure.azexamples.entities.marauder.ai.DelayedAttackAI;

public class MarauderEntity extends EntityMob {

    /** DataWatcher slots. */
    private static final int SPAWN_TICKS = 20;

    private static final int AGGRESSIVE = 21;

    public int MAX_SPAWN_ANIMATION_TICKS = 290;

    public final MarauderAnimationDispatcher animationDispatcher;

    private final MoveAnalysis moveAnalysis;

    public MarauderEntity(World world) {
        super(world);
        this.setSize(1.5F, 2.6F);
        this.animationDispatcher = new MarauderAnimationDispatcher(this);
        this.moveAnalysis = new MoveAnalysis(this);
        this.stepHeight = 2.0F;
        this.tasks.addTask(7, new EntityAIWander(this, 0.3D));
        this.tasks.addTask(2, new DelayedAttackAI(this, 0.6D, 5, this::runAttackAnimations));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityVillager.class, 0, true));
    }

    /** 1.7.10 mobs only run their task list when this returns true. */
    @Override
    protected boolean isAIEnabled() {
        return true;
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(SPAWN_TICKS, 0.0F);
        this.dataWatcher.addObject(AGGRESSIVE, (byte) 0);
    }

    /**
     * Keeps the body around for the 80-tick death animation before removing it.
     */
    @Override
    protected void onDeathUpdate() {
        ++this.deathTime;

        if (this.deathTime >= 80 && !this.worldObj.isRemote && !this.isDead) {
            this.worldObj.setEntityState(this, (byte) 20);
            this.setDead();
        }
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        moveAnalysis.update();

        if (this.worldObj.isRemote) {
            return;
        }

        this.dataWatcher.updateObject(AGGRESSIVE, (byte) (this.getAttackTarget() != null ? 1 : 0));

        if (this.getSpawnTicks() < MAX_SPAWN_ANIMATION_TICKS && this.isEntityAlive()) {
            this.setSpawnTicks(this.getSpawnTicks() + 1.0F);
            this.getNavigator().clearPathEntity();
            this.renderYawOffset = 0;
            this.rotationYawHead = 0;
            this.rotationPitch = 0;
            this.rotationYaw = 0;
            this.setAttackTarget(null);
        }
    }

    /**
     * The attack target only exists on the server in 1.7.10, so it is synced to the client through the data watcher.
     */
    public boolean isAggressive() {
        return this.dataWatcher.getWatchableObjectByte(AGGRESSIVE) != 0;
    }

    public void updateAnimations() {
        boolean isMovingOnGround = moveAnalysis.isMovingHorizontally() && this.onGround;

        if (this.getHealth() <= 0) {
            animationDispatcher.clientDeath();
            return;
        }

        if (this.getSpawnTicks() < MAX_SPAWN_ANIMATION_TICKS) {
            animationDispatcher.clientSpawn();
            return;
        }

        if (isMovingOnGround) {
            if (this.isAggressive() && !this.isSwingInProgress) {
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

    protected void setSpawnTicks(float spawnTicks) {
        this.dataWatcher.updateObject(SPAWN_TICKS, spawnTicks);
    }

    public float getSpawnTicks() {
        return this.dataWatcher.getWatchableObjectFloat(SPAWN_TICKS);
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        compound.setFloat("SpawnTicks", this.getSpawnTicks());
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        this.setSpawnTicks(compound.getFloat("SpawnTicks"));
    }
}

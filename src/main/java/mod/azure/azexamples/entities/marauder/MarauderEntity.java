package mod.azure.azexamples.entities.marauder;

import net.minecraft.block.Block;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

import mod.azure.azurelib.util.MoveAnalysis;

import mod.azure.azexamples.entities.marauder.ai.DelayedAttackAI;

public class MarauderEntity extends EntityMob {

    protected static final DataParameter<Float> SPAWN_TICKS = EntityDataManager.createKey(
        MarauderEntity.class,
        DataSerializers.FLOAT
    );

    public int MAX_SPAWN_ANIMATION_TICKS = 290;

    public final MarauderAnimationDispatcher animationDispatcher;

    private final MoveAnalysis moveAnalysis;

    public MarauderEntity(World world) {
        super(world);
        this.setSize(1.5F, 2.6F);
        this.animationDispatcher = new MarauderAnimationDispatcher(this);
        this.moveAnalysis = new MoveAnalysis(this);
        this.stepHeight = 2.0F;
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(SPAWN_TICKS, 0.0F);
    }

    /**
     * Keeps the body around for the 80-tick death animation before removing it.
     */
    @Override
    protected void onDeathUpdate() {
        ++this.deathTime;

        if (this.deathTime >= 80 && !this.world.isRemote && !this.isDead) {
            this.world.setEntityState(this, (byte) 20);
            this.setDead();
        }
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        moveAnalysis.update();

        if (!this.world.isRemote && this.getSpawnTicks() < MAX_SPAWN_ANIMATION_TICKS && this.isEntityAlive()) {
            this.setSpawnTicks(this.getSpawnTicks() + 1.0F);
            this.getNavigator().clearPath();
            this.renderYawOffset = 0;
            this.rotationYawHead = 0;
            this.rotationPitch = 0;
            this.rotationYaw = 0;
            this.setAttackTarget(null);
        }
    }

    /**
     * 1.12.2 has no {@code Mob#isAggressive}; having a target is the closest equivalent.
     */
    public boolean isAggressive() {
        return this.getAttackTarget() != null;
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
        this.dataManager.set(SPAWN_TICKS, spawnTicks);
    }

    public float getSpawnTicks() {
        return this.dataManager.get(SPAWN_TICKS);
    }

    @Override
    public void writeEntityToNBT(@Nonnull NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        compound.setFloat("SpawnTicks", this.getSpawnTicks());
    }

    @Override
    public void readEntityFromNBT(@Nonnull NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        this.setSpawnTicks(compound.getFloat("SpawnTicks"));
    }

    @Override
    protected void initEntityAI() {
        this.tasks.addTask(7, new EntityAIWander(this, 0.3D));
        this.tasks.addTask(2, new DelayedAttackAI(this, 0.6D, true, 5, this::runAttackAnimations));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget<>(this, EntityVillager.class, true));
    }

    @Override
    protected void playStepSound(@Nonnull BlockPos pos, @Nonnull Block block) { /* DISABLES VANILLA WALK SOUND */}
}

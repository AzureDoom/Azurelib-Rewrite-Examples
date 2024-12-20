package mod.azure.azexamples.entities.marauder;

import mod.azure.azurelib.common.api.common.ai.pathing.AzureNavigation;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class MarauderEntity extends Monster {

    private final MarauderAnimationDispatcher animationDispatcher;

    private final MoveAnalysis moveAnalysis;

    public MarauderEntity(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
        this.animationDispatcher = new MarauderAnimationDispatcher();
        this.moveAnalysis = new MoveAnalysis(this);
    }

    @Override
    protected @NotNull PathNavigation createNavigation(@NotNull Level level) {
        return new AzureNavigation(this, level);
    }

    @Override
    public float maxUpStep() {
        return 2.0F;
    }

    @Override
    protected void tickDeath() {
        ++this.deathTime;
        if (this.deathTime >= 80 && !this.level().isClientSide() && !this.isRemoved()) {
            this.level().broadcastEntityEvent(this, (byte) 60);
            this.remove(RemovalReason.KILLED);
        }
    }

    @Override
    public void tick() {
        super.tick();
        moveAnalysis.update();

        if (this.level().isClientSide) {
            var isMovingOnGround = moveAnalysis.isMovingHorizontally() && onGround();
            if (!this.isAlive()) {
                animationDispatcher.clientDeath(this);
            } else if (this.tickCount < 270) {
                animationDispatcher.clientSpawn(this);
            } else if (isMovingOnGround) {
                if (this.isAggressive()) {
                    animationDispatcher.clientRun(this);
                } else {
                    animationDispatcher.clientWalk(this);
                }
            } else {
                animationDispatcher.clientIdle(this);
            }
        } else {
            if (this.tickCount < 280 && this.isAlive()) {
                if (this.getNavigation() instanceof AzureNavigation azureNavigation) {
                    azureNavigation.hardStop();
                    azureNavigation.stop();
                }
                this.setYBodyRot(0);
                this.setYHeadRot(0);
                this.getEyePosition(90);
                this.setXRot(0);
                this.setYRot(0);
            }
        }
    }

    /**
     * TODO: Get longer Melee animations working
     */
    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(7, new RandomStrollGoal(this, 0.3F));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 0.6F, true));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, true));
    }

    @Override
    protected void playStepSound(@NotNull BlockPos pos, @NotNull BlockState state) {}
}

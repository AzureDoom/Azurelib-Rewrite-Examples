package mod.azure.azexamples.entities.marauder.ai;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.util.EnumHand;

import mod.azure.azexamples.entities.marauder.MarauderEntity;

public class DelayedAttackAI extends EntityAIAttackMelee {

    private final int delayTicksBeforeAttack;

    private final Runnable attackAnimationRunnable;

    private int delayBeforeAttack;

    private boolean triggeredAttackAnimation;

    public DelayedAttackAI(
        EntityCreature mob,
        double speedModifier,
        boolean useLongMemory,
        int delayTicksBeforeAttack,
        Runnable attackAnimationRunnable
    ) {
        super(mob, speedModifier, useLongMemory);
        this.delayTicksBeforeAttack = delayTicksBeforeAttack;
        this.attackAnimationRunnable = attackAnimationRunnable;
        this.setMutexBits(3);
    }

    private boolean spawnFinished() {
        if (this.attacker instanceof MarauderEntity) {
            MarauderEntity marauder = (MarauderEntity) this.attacker;
            return marauder.getSpawnTicks() >= marauder.MAX_SPAWN_ANIMATION_TICKS;
        }
        return true;
    }

    @Override
    public boolean shouldExecute() {
        return spawnFinished() && super.shouldExecute();
    }

    @Override
    public void startExecuting() {
        super.startExecuting();
        this.delayBeforeAttack = delayTicksBeforeAttack;
        this.triggeredAttackAnimation = false;
    }

    protected void checkAndAttack(EntityLivingBase target, double distToEnemySqr) {
        if (this.attacker.world.isRemote || !spawnFinished()) {
            return;
        }

        if (canPerformAttack(target, distToEnemySqr)) {
            if (!triggeredAttackAnimation) {
                attackAnimationRunnable.run();
                this.triggeredAttackAnimation = true;
            }

            if (delayBeforeAttack > 0) {
                delayBeforeAttack--;
                this.attacker.getNavigator().clearPath();
            } else {
                this.attackTick = 20;
                this.attacker.swingArm(EnumHand.MAIN_HAND);
                this.attacker.attackEntityAsMob(target);
                this.triggeredAttackAnimation = false;
                this.delayBeforeAttack = delayTicksBeforeAttack;
            }
        } else {
            this.delayBeforeAttack = delayTicksBeforeAttack;
            this.triggeredAttackAnimation = false;
        }
    }

    protected boolean canPerformAttack(EntityLivingBase entity, double distToEnemySqr) {
        return this.attackTick <= 0 && distToEnemySqr <= getAttackReachSqr(entity) && this.attacker.getEntitySenses()
            .canSee(entity);
    }
}

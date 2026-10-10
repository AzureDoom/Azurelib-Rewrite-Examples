package mod.azure.azexamples.entities.marauder.ai;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;

import mod.azure.azexamples.entities.marauder.MarauderEntity;

/**
 * Melee attack that waits {@code delayTicksBeforeAttack} ticks once in range, starting the attack animation as the wait
 * begins so the hit lands with the swing. 1.7.10's melee task ({@code EntityAIAttackOnCollide}) keeps its state
 * private, so this is a small self-contained version.
 */
public class DelayedAttackAI extends EntityAIBase {

    private final EntityCreature attacker;

    private final double speed;

    private final int delayTicksBeforeAttack;

    private final Runnable attackAnimationRunnable;

    private int attackCooldown;

    private int delayBeforeAttack;

    private int repathTicks;

    private boolean triggeredAttackAnimation;

    public DelayedAttackAI(
        EntityCreature mob,
        double speed,
        int delayTicksBeforeAttack,
        Runnable attackAnimationRunnable
    ) {
        this.attacker = mob;
        this.speed = speed;
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
        EntityLivingBase target = this.attacker.getAttackTarget();
        return spawnFinished() && target != null && target.isEntityAlive();
    }

    @Override
    public boolean continueExecuting() {
        return shouldExecute();
    }

    @Override
    public void startExecuting() {
        this.delayBeforeAttack = delayTicksBeforeAttack;
        this.triggeredAttackAnimation = false;
        this.repathTicks = 0;
    }

    @Override
    public void resetTask() {
        this.attacker.getNavigator().clearPathEntity();
    }

    @Override
    public void updateTask() {
        EntityLivingBase target = this.attacker.getAttackTarget();

        if (target == null) {
            return;
        }

        this.attacker.getLookHelper().setLookPositionWithEntity(target, 30.0F, 30.0F);
        double distSq = this.attacker.getDistanceSq(target.posX, target.boundingBox.minY, target.posZ);
        double reach = this.attacker.width * 2.0F * this.attacker.width * 2.0F + target.width;

        if (this.attackCooldown > 0) {
            this.attackCooldown--;
        }

        boolean inRange = distSq <= reach && this.attacker.getEntitySenses().canSee(target);

        if (!inRange) {
            this.delayBeforeAttack = delayTicksBeforeAttack;
            this.triggeredAttackAnimation = false;

            if (--this.repathTicks <= 0) {
                this.repathTicks = 4 + this.attacker.getRNG().nextInt(7);
                this.attacker.getNavigator().tryMoveToEntityLiving(target, this.speed);
            }
            return;
        }

        if (this.attackCooldown > 0) {
            return;
        }

        if (!this.triggeredAttackAnimation) {
            this.attackAnimationRunnable.run();
            this.triggeredAttackAnimation = true;
        }

        if (this.delayBeforeAttack > 0) {
            this.delayBeforeAttack--;
            this.attacker.getNavigator().clearPathEntity();
            return;
        }

        this.attackCooldown = 20;
        this.attacker.swingItem();
        this.attacker.attackEntityAsMob(target);
        this.triggeredAttackAnimation = false;
        this.delayBeforeAttack = delayTicksBeforeAttack;
    }
}

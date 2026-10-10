package mod.azure.azexamples.entities.doomhunter;

import net.minecraft.entity.monster.EntityMob;
import net.minecraft.world.World;

public class DoomHunterEntity extends EntityMob {

    protected final DoomHunterAnimationDispatcher animationDispatcher;

    public DoomHunterEntity(World world) {
        super(world);
        this.setSize(3.0F, 7.0F);
        this.animationDispatcher = new DoomHunterAnimationDispatcher(this);
    }

    /**
     * 1.12.2 has no {@code Mob#isAggressive}; having a target is the closest equivalent.
     */
    public boolean isAggressive() {
        return this.getAttackTarget() != null;
    }

    public void updateAnimations() {
        animationDispatcher.clientIdle();
        animationDispatcher.clientFlamethrower();
    }
}

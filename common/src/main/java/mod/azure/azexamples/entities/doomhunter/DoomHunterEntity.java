package mod.azure.azexamples.entities.doomhunter;

import mod.azure.azurelib.hitbox.AzHitboxHolder;
import mod.azure.azurelib.hitbox.AzHitboxSet;
import mod.azure.azurelib.hitbox.AzHitboxes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;

public class DoomHunterEntity extends Monster implements AzHitboxHolder {

    private static final AzHitboxSet HITBOXES = AzHitboxSet.builder()

        .add("eye", h -> h.damageMultiplier(3f).tag("weak_point").tag("critical"))
        .add("head", h -> h.inflate(1).damageMultiplier(2f).tag("weak_point"))
        .add("neck", h -> h.damageMultiplier(1.5f))

        .add("chest")
        .add("body")
        .add(
            "back_thruster",
            "propulsion6",
            h -> h.size(11, 12, 11)
                .anchorAtPivot()
                .offset(-0.5f, -5, 4.5f)
                .damageMultiplier(1.25f)
                .tag("thruster")
        )
        .add(
            "belly_thruster",
            "propulsion5",
            h -> h.size(11, 12, 11)
                .anchorAtPivot()
                .offset(-3.5f, -5, 4.5f)
                .damageMultiplier(1.25f)
                .tag("thruster")
        )

        .add("rShoulder")
        .add("rArm1")
        .add("rArm2", h -> h.size(8, 28, 12).anchorAtPivot().offset(1, -11.5f, -9))
        .add("bigGun", h -> h.damageMultiplier(0.5f).tag("weapon"))

        .add("lShoulder")
        .add("lArm1")
        .add("lArm2")
        .add("lArm3", h -> h.size(12, 23, 10).anchorAtPivot().offset(-2, -8, -2))
        .add("sawBase", h -> h.damageMultiplier(0.5f).tag("weapon"))
        .add("sawBlades", h -> h.damageMultiplier(0.5f).tag("weapon"))

        .add(
            "sled_deck",
            "sled",
            h -> h.size(45, 7, 38)
                .anchorAtPivot()
                .offset(0.5f, -15.5f, 2.5f)
                .damageMultiplier(0.75f)
                .tag("sled")
                .tag("armored")
        )
        .add(
            "sled_hull",
            "sled",
            h -> h.size(30, 21, 40)
                .anchorAtPivot()
                .offset(0, -8.5f, 2)
                .damageMultiplier(0.75f)
                .tag("sled")
                .tag("armored")
        )
        .add(
            "sled_nose",
            "sled",
            h -> h.size(23, 15, 23)
                .anchorAtPivot()
                .offset(0.5f, -8.5f, -13.5f)
                .damageMultiplier(0.75f)
                .tag("sled")
                .tag("armored")
        )
        .add(
            "sled_left_pod",
            "sled",
            h -> h.size(15, 14, 41)
                .anchorAtPivot()
                .offset(17, -11, 1.5f)
                .damageMultiplier(0.75f)
                .tag("sled")
                .tag("armored")
        )
        .add(
            "sled_right_pod",
            "sled",
            h -> h.size(15, 14, 41)
                .anchorAtPivot()
                .offset(-16, -11, 1.5f)
                .damageMultiplier(0.75f)
                .tag("sled")
                .tag("armored")
        )
        .add(
            "sled_left_wing",
            "sled",
            h -> h.size(14, 12, 29)
                .anchorAtPivot()
                .offset(27.5f, -15.5f, 1.5f)
                .damageMultiplier(0.75f)
                .tag("sled")
                .tag("armored")
        )
        .add(
            "sled_right_wing",
            "sled",
            h -> h.size(13, 12, 29)
                .anchorAtPivot()
                .offset(-26.5f, -15.5f, 1.5f)
                .damageMultiplier(0.75f)
                .tag("sled")
                .tag("armored")
        )

        .add(
            "thruster_front_right",
            "propulsion1",
            h -> h.size(11, 12, 11)
                .anchorAtPivot()
                .offset(3.5f, -5, -4.5f)
                .damageMultiplier(1.25f)
                .tag("sled")
                .tag("thruster")
        )
        .add(
            "thruster_front_left",
            "propulsion2",
            h -> h.size(11, 12, 11)
                .anchorAtPivot()
                .offset(3.5f, -5, -4.5f)
                .damageMultiplier(1.25f)
                .tag("sled")
                .tag("thruster")
        )
        .add(
            "thruster_back_left",
            "propulsion3",
            h -> h.size(11, 12, 11)
                .anchorAtPivot()
                .offset(-3.5f, -5, 4.5f)
                .damageMultiplier(1.25f)
                .tag("sled")
                .tag("thruster")
        )
        .add(
            "thruster_back_right",
            "propulsion4",
            h -> h.size(11, 12, 11)
                .anchorAtPivot()
                .offset(-3.5f, -5, 4.5f)
                .damageMultiplier(1.25f)
                .tag("sled")
                .tag("thruster")
        )

        .add("lGunBarrel", h -> h.tag("sled").tag("weapon"))
        .add("rGunBarrel2", h -> h.tag("sled").tag("weapon"))
        .add("lMissleLaunch", h -> h.tag("sled").tag("weapon"))
        .add("rMissleLaunch2", h -> h.tag("sled").tag("weapon"))

        .maxReach(8f)
        .build();

    protected final DoomHunterAnimationDispatcher animationDispatcher;

    private boolean animationStarted;

    public DoomHunterEntity(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
        this.animationDispatcher = new DoomHunterAnimationDispatcher(this);
    }

    @Override
    public AzHitboxSet getHitboxSet() {
        return HITBOXES;
    }

    @Override
    public @NotNull AABB getBoundingBoxForCulling() {
        return AzHitboxes.getCullingBox(this, super.getBoundingBoxForCulling());
    }

    @Override
    public float getScale() {
        return 0.5F;
    }

    @Override
    public void tick() {
        super.tick();

        if (level().isClientSide()) {
            updateAnimations();
        }
    }

    @Override
    public boolean requiresCustomPersistence() {
        return true;
    }

    public void updateAnimations() {
        if (animationStarted) {
            return;
        }
        animationStarted = true;

        animationDispatcher.clientIdle();
        animationDispatcher.clientFlamethrower();
    }
}

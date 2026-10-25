package mod.azure.azexamples.entities.doomhunter;

import mod.azure.azurelib.hitbox.AzHitboxHolder;
import mod.azure.azurelib.hitbox.AzHitboxSet;
import mod.azure.azurelib.hitbox.AzHitboxes;
import mod.azure.azurelib.platform.mc.Mc;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

public class DoomHunterEntity extends MobEntity implements AzHitboxHolder {

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

    private static final int AGGRESSIVE = 20;

    protected final DoomHunterAnimationDispatcher animationDispatcher;

    public DoomHunterEntity(World world) {
        super(world);
        this.setSize(3.0F, 7.0F);
        this.animationDispatcher = new DoomHunterAnimationDispatcher(this);
        this.ignoreCameraFrustum = true;
    }

    @Override
    protected void registerSyncedData() {
        super.registerSyncedData();
        this.syncedData.register(AGGRESSIVE, (byte) 0);
    }

    @Override
    public void tick() {
        super.tick();
        if (!Mc.isClientSide(this.world)) {
            // this.syncedData.update(AGGRESSIVE, (byte) (this.walkTarget != null ? 1 : 0));
        }

        if (!world.isMultiplayer) {
            updateAnimations();
        }
    }

    public boolean isAggressive() {
        return this.syncedData.getByte(AGGRESSIVE) != 0;
    }

    public void updateAnimations() {
        animationDispatcher.clientIdle();
        animationDispatcher.clientFlamethrower();
    }

    @Override
    public AzHitboxSet getHitboxSet() {
        return HITBOXES;
    }

    @Override
    @Environment(EnvType.CLIENT)
    public boolean shouldRender(double distanceSq) {
        Box box = AzHitboxes.getCullingBox(this, Mc.boundingBox(this));
        double size = (box.maxX - box.minX + box.maxY - box.minY + box.maxZ - box.minZ) / 3.0;
        double range = size * 64.0 * this.viewDistanceScaling;
        return distanceSq < range * range;
    }
}

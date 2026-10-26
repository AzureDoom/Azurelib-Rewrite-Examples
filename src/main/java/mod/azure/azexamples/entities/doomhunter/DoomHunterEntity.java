package mod.azure.azexamples.entities.doomhunter;

import net.minecraft.entity.monster.EntityMob;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import mod.azure.azurelib.hitbox.AzHitboxHolder;
import mod.azure.azurelib.hitbox.AzHitboxSet;
import mod.azure.azurelib.hitbox.AzHitboxes;

public class DoomHunterEntity extends EntityMob implements AzHitboxHolder {

    /** Ticks spent idling between bursts. */
    private static final int IDLE_TICKS = 60;

    /** Ticks spent firing per burst, counted from the start of the fade-in. */
    private static final int FIRING_TICKS = 80;

    /**
     * Whether the Doom Hunter is firing. Decided on the server and synced, so every client fades on the same edges and
     * a client that starts tracking mid-burst snaps to the right state instead of waiting for the next one.
     */
    private static final DataParameter<Boolean> FIRING = EntityDataManager.createKey(
        DoomHunterEntity.class,
        DataSerializers.BOOLEAN
    );

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

    /** Client-side: the firing state seen last tick, or {@code null} before the first client tick. */
    private Boolean lastFiring;

    public DoomHunterEntity(World world) {
        super(world);
        this.setSize(3.0F, 7.0F);
        this.ignoreFrustumCheck = true;
        this.animationDispatcher = new DoomHunterAnimationDispatcher(this);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(FIRING, false);
    }

    public boolean isFiring() {
        return dataManager.get(FIRING);
    }

    public void setFiring(boolean firing) {
        dataManager.set(FIRING, firing);
    }

    @Override
    public AzHitboxSet getHitboxSet() {
        return HITBOXES;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getRenderBoundingBox() {
        return AzHitboxes.getCullingBox(this, super.getRenderBoundingBox());
    }

    @Override
    public void onUpdate() {
        super.onUpdate();

        if (world.isRemote) {
            updateAnimations();
        } else {
            updateFiringCycle();
        }
    }

    /**
     * Server-side showcase loop: idle for {@link #IDLE_TICKS}, then fire for {@link #FIRING_TICKS}, forever. Only the
     * edges change the synced value, so this costs nothing on ticks where the state holds.
     */
    private void updateFiringCycle() {
        setFiring(ticksExisted % (IDLE_TICKS + FIRING_TICKS) >= IDLE_TICKS);
    }

    @Override
    public boolean isNoDespawnRequired() {
        return true;
    }

    /**
     * Drives idle -> fade-in -> active firing -> fade-out on the client. The idle is sent once and never again, so the
     * base layer loops uninterrupted; every later change only moves the weight of the additive attack layer.
     */
    public void updateAnimations() {
        boolean firing = isFiring();

        if (lastFiring == null) {
            // First client tick: start the base loop and snap the attack layer to the current state, no fade.
            animationDispatcher.clientIdle();

            if (firing) {
                animationDispatcher.clientFlamethrowerActive();
            } else {
                animationDispatcher.clientFlamethrowerOff();
            }
        } else if (firing != lastFiring) {
            // Firing edge: fade rather than pop. Active firing is just the weight holding at its peak.
            if (firing) {
                animationDispatcher.clientFlamethrowerFadeIn();
            } else {
                animationDispatcher.clientFlamethrowerFadeOut();
            }
        }

        lastFiring = firing;
    }
}

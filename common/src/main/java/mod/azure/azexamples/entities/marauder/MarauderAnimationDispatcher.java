package mod.azure.azexamples.entities.marauder;

import mod.azure.azurelib.common.animation.dispatch.command.AzCommand;
import mod.azure.azurelib.common.animation.dispatch.command.sequence.AzSequence;
import mod.azure.azurelib.common.animation.play_behavior.AzPlayBehaviors;

import mod.azure.azexamples.CommonStrings;

/**
 * Dispatches the Marauder's animation commands (idle, walk, run, melee, energy slash, spawn, death) on the client,
 * driven from the synced entity state in {@link MarauderEntity#updateAnimations()}.
 */
public class MarauderAnimationDispatcher {

    private static final String ENERGY_SLASH_ANIMATION_NAME = "energy_slash";

    private final AzCommand deathCommand = AzCommand.create(
        CommonStrings.BASE_CONTROLLER,
        CommonStrings.DEATH_ANIMATION_NAME,
        AzPlayBehaviors.HOLD_ON_LAST_FRAME
    );

    private final AzCommand idleCommand = AzCommand.create(
        CommonStrings.BASE_CONTROLLER,
        CommonStrings.IDLE_ANIMATION_NAME,
        AzPlayBehaviors.LOOP
    );

    private final AzCommand walkCommand = AzCommand.create(
        CommonStrings.BASE_CONTROLLER,
        CommonStrings.WALK_ANIMATION_NAME,
        AzPlayBehaviors.LOOP
    );

    private final AzCommand runCommand = AzCommand.create(
        CommonStrings.BASE_CONTROLLER,
        CommonStrings.RUN_ANIMATION_NAME,
        AzPlayBehaviors.LOOP
    );

    private static final AzSequence meleeSequence = AzSequence.builder()
        .play(CommonStrings.MELEE_ANIMATION_NAME)
        .then(CommonStrings.IDLE_ANIMATION_NAME, AzPlayBehaviors.LOOP)
        .build();

    private final AzCommand meleeCommand = AzCommand.create(
        CommonStrings.BASE_CONTROLLER,
        meleeSequence
    );

    private static final AzSequence energySlashSequence = AzSequence.builder()
        .play(ENERGY_SLASH_ANIMATION_NAME)
        .then(CommonStrings.IDLE_ANIMATION_NAME, AzPlayBehaviors.LOOP)
        .build();

    private final AzCommand energySlashCommand = AzCommand.create(
        CommonStrings.BASE_CONTROLLER,
        energySlashSequence
    );

    private static final AzSequence spawnSequence = AzSequence.builder()
        .play(CommonStrings.SPAWN_ANIMATION_NAME)
        .then(CommonStrings.IDLE_ANIMATION_NAME, AzPlayBehaviors.LOOP)
        .build();

    private final AzCommand spawnCommand = AzCommand.create(
        CommonStrings.BASE_CONTROLLER,
        spawnSequence
    );

    private final MarauderEntity marauder;

    public MarauderAnimationDispatcher(MarauderEntity marauder) {
        this.marauder = marauder;
    }

    public void clientIdle() {
        idleCommand.sendForEntity(marauder);
    }

    public void clientWalk() {
        walkCommand.sendForEntity(marauder);
    }

    public void clientRun() {
        runCommand.sendForEntity(marauder);
    }

    public void clientMelee() {
        meleeCommand.sendForEntity(marauder);
    }

    public void clientEnergySlash() {
        energySlashCommand.sendForEntity(marauder);
    }

    public void clientDeath() {
        deathCommand.sendForEntity(marauder);
    }

    public void clientSpawn() {
        spawnCommand.sendForEntity(marauder);
    }
}

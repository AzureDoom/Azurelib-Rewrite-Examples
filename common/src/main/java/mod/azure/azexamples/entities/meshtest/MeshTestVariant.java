package mod.azure.azexamples.entities.meshtest;

import mod.azure.azurelib.animation.dispatch.command.AzCommand;
import mod.azure.azurelib.animation.play_behavior.AzPlayBehaviors;
import net.minecraft.resources.Identifier;

import java.util.Arrays;
import java.util.List;

import mod.azure.azexamples.CommonMod;
import mod.azure.azexamples.CommonStrings;

/**
 * The polygon mesh test cases. Each spawn egg in the creative tab spawns one; they stand still, and right-clicking one
 * switches to its next animation (only for that entity).
 * <p>
 * All of them use {@code textures/entity/mesh_test.png}. Its test card cell has a red corner at the top-left, green
 * top-right, blue bottom-left, yellow bottom-right and a black "F": a correctly mapped face reads like the texture
 * file, seen from the side the face points to. Orange pegs are small cubes placed on mesh vertices; cubes use
 * AzureLib's existing cube path, so they show where the mesh is supposed to be.
 * </p>
 * <p>
 * The assets are generated; the geometry files can be opened in Blockbench with the AzureLib plugin's Import AzureLib
 * .geo Model.
 * </p>
 */
public enum MeshTestVariant {

    /**
     * <b>Rigid mesh.</b> Quads facing south and up, a triangle facing east, a quad tilted 45 degrees up-and-west and a
     * triangle facing down, all on one static bone. Check: each face sits on its pegs, the card reads correctly with
     * the red corner top-left from the front, and brightness follows Minecraft's directional shading (up brightest,
     * down darkest, the tilted quad between up and side).
     */
    RIGID("mesh_rigid", "mesh_rigid", false, "idle"),

    /**
     * <b>Animated mesh.</b> A two-bone pendulum: both bones have non-zero rest rotations and animate rotation and
     * position, and the lower bone also scales on Y. Each bone carries a double-sided mesh with pegs on its corners.
     * Check: the pegs stay on the mesh corners on every frame, with no offset, lag or extra rotation.
     */
    ANIMATED("mesh_animated", "mesh_animated", false, "swing"),

    /**
     * <b>Mixed geometry.</b> A house: cube walls and a cube chimney with a mesh gable roof, all on one bone that spins,
     * tilts and bobs. Check: the roof's eaves stay on the wall tops and the gable triangles close the wall ends, with
     * no gaps or overlaps at any angle.
     */
    MIXED("mesh_mixed", "mesh_mixed", false, "spin"),

    /**
     * <b>Two-joint skinning.</b> A square tube weighted to two joints, blending over 5 pixels either side of the joint
     * at y = 14; only the upper joint rotates. The armature sits under a static bone, so all motion comes from the
     * deformation. Check: the tube curves smoothly through the blend zone without kinks, gaps or flipped faces, the
     * gradient texture stretches evenly, and lighting changes gradually around the bend. Right-click for the rest pose:
     * the tube must be perfectly straight.
     */
    SKINNED("mesh_skinned", "mesh_skinned", false, "bend", "rest"),

    /**
     * <b>Non-uniform scaling.</b> The same tube while the upper joint bends and scales (wide on X, thin on Z) and the
     * lower joint stretches on Y. A grey reference cube sits on the upper joint with its faces parallel to the tube's.
     * Check: the tube's upper faces are lit the same as the cube's matching faces on every frame (cubes use Minecraft's
     * own normal handling), and nothing collapses or inverts.
     */
    SCALED("mesh_scaled", "mesh_scaled", false, "scale", "rest"),

    /**
     * <b>Model copies.</b> Uses the skinned test's geometry. Copies with an even entity ID start on "bend", odd ones on
     * "wave". Spawn two or more side by side. Check: each copy deforms with its own animation and timing, and
     * right-clicking one changes only that one.
     */
    COPIES("mesh_skinned", "mesh_skinned", true, "bend", "wave"),

    /**
     * <b>Skinned mesh on an animated bone.</b> Three identical tube rigs side by side, all bending their upper joint
     * the same way. They differ only in what tilts the whole tube, by the same angle:
     * <ul>
     * <li><b>Grey plate</b> (left): the lower joint tilts it, under a static bone. This is the recommended setup.</li>
     * <li><b>Orange plate</b> (middle): the bone that holds the armature tilts it; the joints only bend.</li>
     * <li><b>Red plate</b> (right): both the armature's bone and the lower joint tilt it.</li>
     * </ul>
     * Check: the orange tube matches the grey one exactly on every frame (an animated owner bone moves the whole
     * deformed mesh, nothing more), and the red tube leans twice as far, because the two animations stack. The red tube
     * is the "looks odd" case: animating the armature's bone and the joints for the same motion doubles it. Right-click
     * for the rest pose: all three must be straight.
     */
    ANIMATED_OWNER("mesh_animated_owner", "mesh_animated_owner", false, "stack", "rest");

    private final Identifier model;

    private final Identifier animations;

    private final boolean alternateStart;

    private final List<AzCommand> commands;

    MeshTestVariant(String model, String animations, boolean alternateStart, String... animationNames) {
        this.model = CommonMod.modResource("geo/entity/" + model + ".geo.json");
        this.animations = CommonMod.modResource("animations/entity/" + animations + ".animation.json");
        this.alternateStart = alternateStart;
        this.commands = Arrays.stream(animationNames)
            .map(name -> AzCommand.create(CommonStrings.BASE_CONTROLLER, name, AzPlayBehaviors.LOOP))
            .toList();
    }

    public Identifier model() {
        return model;
    }

    public Identifier animations() {
        return animations;
    }

    public int animationCount() {
        return commands.size();
    }

    public AzCommand command(int index) {
        return commands.get(Math.floorMod(index, commands.size()));
    }

    /** The animation a new entity starts on: alternating by entity ID for {@link #COPIES}, otherwise the first. */
    public int startAnimation(int entityId) {
        return alternateStart ? Math.floorMod(entityId, commands.size()) : 0;
    }
}

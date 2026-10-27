package mod.azure.azexamples.blocks.jukebox;

import mod.azure.azurelib.animation.AzAnimatorConfig;
import mod.azure.azurelib.animation.controller.AzAnimationController;
import mod.azure.azurelib.animation.controller.AzAnimationControllerContainer;
import mod.azure.azurelib.animation.dispatch.command.AzCommand;
import mod.azure.azurelib.animation.dispatch.command.sequence.AzSequence;
import mod.azure.azurelib.animation.impl.AzBlockAnimator;
import mod.azure.azurelib.util.ResourceLocation;
import net.minecraft.block.Block;
import net.minecraft.block.JukeboxBlock;
import net.minecraft.block.entity.JukeboxBlockEntity;
import org.jetbrains.annotations.NotNull;

import mod.azure.azexamples.CommonMod;
import mod.azure.azexamples.CommonStrings;

/**
 * Animates vanilla's jukebox as the resonator. Nothing is added to the jukebox itself: AzureLib caches an animator on
 * every tile entity, vanilla's included, and the state comes from the jukebox's own block state.
 * <p>
 * Everything happens on the client, in {@link #setCustomAnimations}, which runs every frame the jukebox is rendered:
 * <ul>
 * <li>Disc inserted: the speakers fold out and the spinner rises ({@code activate}), then it spins
 * ({@code spinning}).</li>
 * <li>Disc removed: everything folds back down ({@code deactivate}) and holds there.</li>
 * </ul>
 * One animator exists per jukebox, so it can remember the state it saw last frame and only react to changes.
 */
public class JukeboxResonatorAnimator extends AzBlockAnimator<JukeboxBlockEntity> {

    private static final ResourceLocation ANIMATIONS = CommonMod.modResource(
        "animations/block/resonator.animation.json"
    );

    /** Unfold, then spin until something else plays. */
    private static final AzCommand START = AzSequence.builder()
        .play("animation.activate")
        .loop("animation.spinning")
        .build()
        .toCommand(CommonStrings.BASE_CONTROLLER);

    /** Already playing when the jukebox comes into view: skip the unfold and go straight to spinning. */
    private static final AzCommand ALREADY_PLAYING = AzSequence.builder()
        .loop("animation.spinning")
        .build()
        .toCommand(CommonStrings.BASE_CONTROLLER);

    /** Fold back up and stay folded. Its last frame is the model's rest pose. */
    private static final AzCommand STOP = AzSequence.builder()
        .hold("animation.deactivate")
        .build()
        .toCommand(CommonStrings.BASE_CONTROLLER);

    /** Whether the jukebox held a disc last frame, or {@code null} before the first frame. */
    private Boolean lastHasRecord;

    public JukeboxResonatorAnimator() {
        super(AzAnimatorConfig.defaultConfig());
    }

    @Override
    public void registerControllers(
        AzAnimationControllerContainer<JukeboxBlockEntity> animationControllerContainer
    ) {
        animationControllerContainer.add(
            AzAnimationController.builder(this, CommonStrings.BASE_CONTROLLER).setTransitionLength(2).build()
        );
    }

    @Override
    public @NotNull ResourceLocation getAnimationLocation(JukeboxBlockEntity jukebox) {
        return ANIMATIONS;
    }

    @Override
    public void setCustomAnimations(JukeboxBlockEntity jukebox, float partialTicks) {
        super.setCustomAnimations(jukebox, partialTicks);

        if (jukebox.world == null) {
            return;
        }

        // 1.12 tile entities have no getBlockState(), so read it from the world. HAS_RECORD is part of the block
        // state, which every client receives; the record item itself is never synced to clients in 1.12, so
        // getRecord() would always be empty here.
        Block block = jukebox.world.getBlockState(jukebox.x, jukebox.y, jukebox.z).getBlock();

        if (!(block instanceof JukeboxBlock)) {
            return;
        }

        boolean hasRecord = jukebox.world.getBlockMeta(jukebox.x, jukebox.y, jukebox.z) > 0;

        if (lastHasRecord == null) {
            // First frame for this jukebox. With no disc the model's rest pose is already the folded state, so nothing
            // needs to play; sending STOP here would show a fold every time the chunk loads.
            if (hasRecord) {
                ALREADY_PLAYING.sendForBlockEntity(jukebox);
            }
        } else if (hasRecord != lastHasRecord) {
            (hasRecord ? START : STOP).sendForBlockEntity(jukebox);
        }

        lastHasRecord = hasRecord;
    }
}

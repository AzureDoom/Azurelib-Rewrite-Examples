package mod.azure.azexamples.entities.doomhunter;

import mod.azure.azurelib.core2.animation.impl.AzEntityAnimator;
import mod.azure.azurelib.core2.render.entity.AzEntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import mod.azure.azexamples.CommonMod;

public class DoomHunterRenderer extends AzEntityRenderer<DoomHunterEntity> {

    private static final ResourceLocation MODEL = CommonMod.modResource("geo/entity/doomhunter.geo.json");

    private static final ResourceLocation TEXTURE = CommonMod.modResource("textures/entity/doomhunter.png");

    public DoomHunterRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    protected @Nullable AzEntityAnimator<DoomHunterEntity> createAnimator() {
        return new DoomHunterAnimator();
    }

    @Override
    protected @NotNull ResourceLocation getModelLocation(DoomHunterEntity doomHunter) {
        return MODEL;
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull DoomHunterEntity doomHunter) {
        return TEXTURE;
    }
}

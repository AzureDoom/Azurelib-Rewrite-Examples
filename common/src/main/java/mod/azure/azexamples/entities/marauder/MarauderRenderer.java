package mod.azure.azexamples.entities.marauder;

import mod.azure.azurelib.core2.animation.impl.AzEntityAnimator;
import mod.azure.azurelib.core2.render.entity.AzEntityRenderer;
import mod.azure.azurelib.core2.render.layer.AzAutoGlowingLayer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import mod.azure.azexamples.CommonMod;

public class MarauderRenderer extends AzEntityRenderer<MarauderEntity> {

    private static final ResourceLocation MODEL = CommonMod.modResource("geo/entity/marauder.geo.json");

    private static final ResourceLocation TEXTURE = CommonMod.modResource("textures/entity/marauder.png");

    public MarauderRenderer(EntityRendererProvider.Context context) {
        super(context);
        addRenderLayer(new AzAutoGlowingLayer<>());
    }

    @Override
    protected @Nullable AzEntityAnimator<MarauderEntity> createAnimator() {
        return new MarauderAnimator();
    }

    @Override
    protected @NotNull ResourceLocation getModelLocation(MarauderEntity drone) {
        return MODEL;
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull MarauderEntity drone) {
        return TEXTURE;
    }
}

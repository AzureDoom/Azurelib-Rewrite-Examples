package mod.azure.azexamples.entities.optimusprime;

import mod.azure.azurelib.render.entity.AzEntityRenderer;
import mod.azure.azurelib.render.entity.AzEntityRendererConfig;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

import mod.azure.azexamples.CommonMod;

/**
 * Credit to <a href="https://github.com/MARCTR0N">Marctron</a> for the model/textures/animations of this example.
 */
public class OptimusPrimeRenderer extends AzEntityRenderer<OptimusPrimeEntity> {

    private static final ResourceLocation MODEL = CommonMod.modResource("geo/entity/optimus_prime.geo.json");

    private static final ResourceLocation TEXTURE = CommonMod.modResource("textures/entity/optimus_prime.png");

    public OptimusPrimeRenderer(EntityRendererProvider.Context context) {
        super(
            AzEntityRendererConfig.<OptimusPrimeEntity>builder(MODEL, TEXTURE)
                .setAnimatorProvider(OptimusPrimeAnimator::new)
                .setShadowRadius(3.0F)
                .build(),
            context
        );
    }
}

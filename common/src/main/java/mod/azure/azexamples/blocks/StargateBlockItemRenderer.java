package mod.azure.azexamples.blocks;

import mod.azure.azurelib.core2.animation.impl.AzItemAnimator;
import mod.azure.azurelib.core2.render.item.AzItemRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import mod.azure.azexamples.CommonMod;

public class StargateBlockItemRenderer extends AzItemRenderer {

    private static final ResourceLocation MODEL = CommonMod.modResource("geo/block/stargate.geo.json");

    private static final ResourceLocation TEXTURE = CommonMod.modResource("textures/block/stargate.png");

    @Override
    protected @Nullable AzItemAnimator createAnimator() {
        return new StargateBlockItemAnimator();
    }

    @Override
    protected @NotNull ResourceLocation getModelLocation(ItemStack item) {
        return MODEL;
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(ItemStack item) {
        return TEXTURE;
    }
}

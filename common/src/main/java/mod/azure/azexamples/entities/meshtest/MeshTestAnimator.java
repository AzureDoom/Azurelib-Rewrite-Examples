package mod.azure.azexamples.entities.meshtest;

import mod.azure.azurelib.common.animation.AzAnimatorConfig;
import mod.azure.azurelib.common.animation.controller.AzAnimationController;
import mod.azure.azurelib.common.animation.controller.AzAnimationControllerContainer;
import mod.azure.azurelib.common.animation.impl.AzEntityAnimator;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import mod.azure.azexamples.CommonStrings;

public class MeshTestAnimator extends AzEntityAnimator<MeshTestEntity> {

    public MeshTestAnimator() {
        super(AzAnimatorConfig.defaultConfig());
    }

    @Override
    public void registerControllers(AzAnimationControllerContainer<MeshTestEntity> animationControllerContainer) {
        animationControllerContainer.add(
            AzAnimationController.builder(this, CommonStrings.BASE_CONTROLLER).setTransitionLength(5).build()
        );
    }

    @Override
    public @NotNull ResourceLocation getAnimationLocation(MeshTestEntity entity) {
        return entity.variant().animations();
    }
}

package mod.azure.azexamples.entities.meshtest;

import mod.azure.azurelib.animation.AzAnimatorConfig;
import mod.azure.azurelib.animation.controller.AzAnimationController;
import mod.azure.azurelib.animation.controller.AzAnimationControllerContainer;
import mod.azure.azurelib.animation.impl.AzEntityAnimator;
import mod.azure.azurelib.util.ResourceLocation;

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
    public ResourceLocation getAnimationLocation(MeshTestEntity entity) {
        return entity.variant().animations();
    }
}

package mod.azure.azexamples.items;

import mod.azure.azurelib.core2.model.AzBakedModel;
import mod.azure.azurelib.core2.model.AzBone;
import mod.azure.azurelib.core2.render.armor.bone.AzDefaultArmorBoneProvider;

public class DoomArmorBoneProvider extends AzDefaultArmorBoneProvider {

    @Override
    public AzBone getLeftBootBone(AzBakedModel model) {
        return model.getBone("armorRightBoot").orElse(null);
    }

    @Override
    public AzBone getLeftLegBone(AzBakedModel model) {
        return model.getBone("armorRightLeg").orElse(null);
    }

    @Override
    public AzBone getRightBootBone(AzBakedModel model) {
        return model.getBone("armorLeftBoot").orElse(null);
    }

    @Override
    public AzBone getRightLegBone(AzBakedModel model) {
        return model.getBone("armorLeftLeg").orElse(null);
    }
}

package mod.azure.azexamples.blocks.blockentity;

import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.entity.BlockEntity;

public class StargateBlockEntity extends BlockEntity {

    private static final boolean CLIENT = FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT;

    public final StargateBlockAnimationDispatcher animationDispatcher;

    public StargateBlockEntity() {
        this.animationDispatcher = new StargateBlockAnimationDispatcher(this);
    }

    @Override
    public void tick() {
        if (CLIENT && this.world != null) {
            this.animationDispatcher.serverSpin();
        }
    }
}

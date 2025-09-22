package mod.azure.azexamples.mixins;

import mod.azure.azurelib.util.AzureLibUtil;
import net.minecraft.world.entity.monster.Creeper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import mod.azure.azexamples.entities.creeper.CreeperAnimationDispatcher;

/**
 * A Mixin class for the Creeper entity that integrates additional animation functionalities by dispatching animation
 * commands during specific game events. This class hooks into the Creeper entity lifecycle and injects
 * animation-related behavior. <br>
 * The animation logic is handled through the {@link CreeperAnimationDispatcher}, which facilitates client-side
 * animations such as idle animations.
 */
@Mixin(Creeper.class)
public abstract class CreeperMixin {

    @Unique
    private CreeperAnimationDispatcher animationDispatcher;

    protected CreeperMixin(CreeperAnimationDispatcher animationDispatcher) {
        this.animationDispatcher = animationDispatcher;
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    public void azexample_Init(CallbackInfo info) {
        var self = AzureLibUtil.<Creeper>self(this);
        this.animationDispatcher = new CreeperAnimationDispatcher(self);
    }

    @Inject(method = "tick", at = @At("TAIL"), remap = false)
    public void azexample_Tick(CallbackInfo info) {
        var self = AzureLibUtil.<Creeper>self(this);
        if (self.level.isClientSide) {
            Runnable animationRunner = animationDispatcher::clientIdle;
            animationRunner.run();
        }
    }
}

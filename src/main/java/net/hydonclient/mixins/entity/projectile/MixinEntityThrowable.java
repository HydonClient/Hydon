package net.hydonclient.mixins.entity.projectile;

import net.hydonclient.Hydon;
import net.minecraft.entity.projectile.EntityThrowable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityThrowable.class)
public abstract class MixinEntityThrowable {

    @Inject(method = "isInRangeToRenderDist", at = @At("HEAD"), cancellable = true)
    private void isInRangeToRenderDist(double distance, CallbackInfoReturnable<Boolean> cir) {
        if (Hydon.SETTINGS.disableThrownProjectiles) {
            cir.setReturnValue(false);
        }
    }
}

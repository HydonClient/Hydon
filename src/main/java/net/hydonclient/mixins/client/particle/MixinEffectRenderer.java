package net.hydonclient.mixins.client.particle;

import net.hydonclient.Hydon;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EffectRenderer.class)
public class MixinEffectRenderer {

    @Inject(method = "renderParticles", at = @At("HEAD"), cancellable = true)
    private void renderParticles(Entity entityIn, float partialTicks, CallbackInfo ci) {
        if (Hydon.SETTINGS.disableAllParticles) {
            ci.cancel();
        }
    }

    @Inject(method = "renderLitParticles", at = @At("HEAD"), cancellable = true)
    private void renderLitParticles(Entity entityIn, float partialTicks, CallbackInfo ci) {
        if (Hydon.SETTINGS.disableAllParticles) {
            ci.cancel();
        }
    }
}

package net.hydonclient.mixins.client.renderer.entity;

import net.hydonclient.Hydon;
import net.minecraft.client.renderer.entity.RenderXPOrb;
import net.minecraft.entity.item.EntityXPOrb;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RenderXPOrb.class)
public class MixinRenderXPOrb {

    @Inject(method = "doRender", at = @At("HEAD"), cancellable = true)
    private void doRender(EntityXPOrb entity, double x, double y, double z, float entityYaw, float partialTicks, CallbackInfo ci) {
        if (Hydon.SETTINGS.disableXPOrbs) {
            ci.cancel();
        }
    }
}

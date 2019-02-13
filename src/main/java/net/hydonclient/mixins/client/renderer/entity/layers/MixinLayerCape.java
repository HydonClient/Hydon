package net.hydonclient.mixins.client.renderer.entity.layers;

import net.hydonclient.Hydon;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerCape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LayerCape.class)
public class MixinLayerCape {

    @Inject(method = "doRenderLayer", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/ModelPlayer;renderCape(F)V", ordinal = 0))
    private void doRenderLayer(AbstractClientPlayer entitylivingbaseIn, float p_177141_2_, float p_177141_3_, float partialTicks, float p_177141_5_, float p_177141_6_, float p_177141_7_, float scale, CallbackInfo ci) {
        if (entitylivingbaseIn.isSneaking() && !Hydon.INSTANCE.wasOptifineFound()) {
            GlStateManager.translate(0, 0, .1);
        }
    }
}

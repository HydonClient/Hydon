package net.hydonclient.mixinsimp.client.renderer.entity.layers;

import net.hydonclient.Hydon;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.EntityLivingBase;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public class HydonLayerArmorBase<T extends ModelBase> implements LayerRenderer<EntityLivingBase> {

    public void doRenderLayer(EntityLivingBase entitylivingbaseIn, float p_177141_2_, float p_177141_3_, float partialTicks, float p_177141_5_, float p_177141_6_, float p_177141_7_, float scale) {
    }

    public boolean shouldCombineTextures() {
        return false;
    }

    public void renderGlint(EntityLivingBase entitylivingbaseIn, T modelbaseIn,
                             float p_177183_3_, float p_177183_4_, float partialTicks,
                             float p_177183_6_, float p_177183_7_, float p_177183_8_, float scale,
                             CallbackInfo ci) {
        if (Hydon.SETTINGS.disableEnchantments) {
            ci.cancel();
        }
    }
}

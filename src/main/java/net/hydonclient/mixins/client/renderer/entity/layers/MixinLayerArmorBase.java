package net.hydonclient.mixins.client.renderer.entity.layers;

import net.hydonclient.mixinsimp.client.renderer.entity.layers.HydonLayerArmorBase;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.layers.LayerArmorBase;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.EntityLivingBase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LayerArmorBase.class)
public abstract class MixinLayerArmorBase<T extends ModelBase> implements LayerRenderer<EntityLivingBase> {

    private HydonLayerArmorBase impl = new HydonLayerArmorBase();

    /**
     * Disable enchant glint
     *
     * @param entitylivingbaseIn entity wearing armor
     * @param modelbaseIn        entity model
     * @param p_177183_3_        limb swing amount
     * @param p_177183_4_        age in ticks
     * @param partialTicks       world tick
     * @param p_177183_6_        rotation angle
     * @param p_177183_7_        rotation angle
     * @param p_177183_8_        rotation angle
     * @param scale              model scale
     * @param ci                 callback
     */
    @Inject(method = "renderGlint", at = @At("HEAD"), cancellable = true)
    public void renderGlint(EntityLivingBase entitylivingbaseIn, T modelbaseIn,
                            float p_177183_3_, float p_177183_4_, float partialTicks,
                            float p_177183_6_, float p_177183_7_, float p_177183_8_, float scale,
                            CallbackInfo ci) {
        impl.renderGlint(entitylivingbaseIn, modelbaseIn, p_177183_3_, p_177183_4_, partialTicks, p_177183_6_, p_177183_7_, p_177183_8_, scale, ci);
    }
}

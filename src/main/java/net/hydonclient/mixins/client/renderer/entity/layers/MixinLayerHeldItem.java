package net.hydonclient.mixins.client.renderer.entity.layers;

import net.hydonclient.mixinsimp.client.renderer.entity.layers.HydonLayerHeldItem;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
import net.minecraft.entity.EntityLivingBase;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(LayerHeldItem.class)
public class MixinLayerHeldItem {

    @Shadow
    @Final
    private RendererLivingEntity<?> livingEntityRenderer;

    private HydonLayerHeldItem impl = new HydonLayerHeldItem();

    /**
     * @author Mojang
     * @reason 1.7 Item holding & blocking
     */
    @Overwrite
    public void doRenderLayer(EntityLivingBase entity, float f, float f2, float f3, float partialTicks, float f4, float f5, float scale) {
        impl.doRenderLayer(entity, livingEntityRenderer);
    }
}

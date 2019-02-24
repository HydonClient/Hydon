package net.hydonclient.mixinsimp.client.renderer.entity;

import net.hydonclient.Hydon;
import net.hydonclient.mixins.client.renderer.entity.IMixinRenderLivingEntity;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

public class HydonRendererLivingEntity<T extends EntityLivingBase> extends Render<T> {

    private RendererLivingEntity<T> parent;

    public HydonRendererLivingEntity(RenderManager renderManager, RendererLivingEntity<T> parent) {
        super(renderManager);
        this.parent = parent;
    }

    @Override
    protected ResourceLocation getEntityTexture(T entity) {
        return null;
    }

    public void doRender(T entity, double x, double y, double z, float entityYaw, float partialTicks, CallbackInfo ci) {
        if (Hydon.SETTINGS.DISABLE_ARMORSTANDS && entity instanceof EntityArmorStand) {
            ci.cancel();
        }
    }

    public void renderLayers(T entitylivingbaseIn, float p_177093_2_, float p_177093_3_, float partialTicks, float p_177093_5_, float p_177093_6_, float p_177093_7_, float p_177093_8_, List<LayerRenderer<T>> layerRenderers) {
        for (LayerRenderer<T> layerRenderer : layerRenderers) {
            boolean combine = layerRenderer.shouldCombineTextures();

            if (Hydon.SETTINGS.OLD_ARMOR) {
                combine = true;
            }

            boolean setBrightness = ((IMixinRenderLivingEntity<T>) parent).callSetBrightness(entitylivingbaseIn, partialTicks, combine);

            layerRenderer.doRenderLayer(entitylivingbaseIn, p_177093_2_, p_177093_3_, partialTicks, p_177093_5_, p_177093_6_, p_177093_7_, p_177093_8_);

            if (setBrightness) {
                ((IMixinRenderLivingEntity<T>) parent).callUnsetBrightness();
            }
        }
    }
}

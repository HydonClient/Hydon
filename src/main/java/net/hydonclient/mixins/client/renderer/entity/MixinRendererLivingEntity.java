package net.hydonclient.mixins.client.renderer.entity;

import net.hydonclient.mixinsimp.client.renderer.entity.HydonRendererLivingEntity;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.EntityLivingBase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(RendererLivingEntity.class)
public abstract class MixinRendererLivingEntity<T extends EntityLivingBase> extends Render<T> {

    @Shadow
    protected List<LayerRenderer<T>> layerRenderers;

    private HydonRendererLivingEntity<T> impl = new HydonRendererLivingEntity<T>(renderManager, (RendererLivingEntity) (Object) this);

    protected MixinRendererLivingEntity(RenderManager renderManager) {
        super(renderManager);
    }

    @Inject(method = "doRender", at = @At("HEAD"), cancellable = true)
    private void doRender(T entity, double x, double y, double z, float entityYaw, float partialTicks, CallbackInfo ci) {
        impl.doRender(entity, x, y, z, entityYaw, partialTicks, ci);
    }

    /**
     * @author Mojang
     * @reason 1.7 Armor
     */
    @Overwrite
    protected void renderLayers(T entitylivingbaseIn, float p_177093_2_, float p_177093_3_, float partialTicks, float p_177093_5_, float p_177093_6_, float p_177093_7_, float p_177093_8_) {
        impl.renderLayers(entitylivingbaseIn, p_177093_2_, p_177093_3_, partialTicks, p_177093_5_, p_177093_6_, p_177093_7_, p_177093_8_, layerRenderers);
    }
}

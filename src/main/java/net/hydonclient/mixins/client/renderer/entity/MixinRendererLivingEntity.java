package net.hydonclient.mixins.client.renderer.entity;

import net.hydonclient.mixinsimp.client.renderer.entity.HydonRendererLivingEntity;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.entity.EntityLivingBase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RendererLivingEntity.class)
public abstract class MixinRendererLivingEntity<T extends EntityLivingBase> extends Render<T> {

    private HydonRendererLivingEntity<T> impl = new HydonRendererLivingEntity<>(renderManager);

    protected MixinRendererLivingEntity(RenderManager renderManager) {
        super(renderManager);
    }

    @Inject(method = "doRender", at = @At("HEAD"), cancellable = true)
    public void doRender(T entity, double x, double y, double z, float entityYaw, float partialTicks, CallbackInfo ci) {
        impl.doRender(entity, x, y, z, entityYaw, partialTicks, ci);
    }
}

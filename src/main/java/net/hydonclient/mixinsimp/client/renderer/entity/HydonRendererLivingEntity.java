package net.hydonclient.mixinsimp.client.renderer.entity;

import net.hydonclient.Hydon;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public class HydonRendererLivingEntity<T extends EntityLivingBase> extends Render<T> {
    public HydonRendererLivingEntity(RenderManager renderManager) {
        super(renderManager);
    }

    @Override
    protected ResourceLocation getEntityTexture(T entity) {
        return null;
    }

    public void doRender(T entity, double x, double y, double z, float entityYaw, float partialTicks, CallbackInfo ci) {
        if (Hydon.SETTINGS.disableArmorstands && entity instanceof EntityArmorStand) {
            ci.cancel();
        }
    }
}

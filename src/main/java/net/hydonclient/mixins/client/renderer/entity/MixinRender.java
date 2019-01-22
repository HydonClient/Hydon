package net.hydonclient.mixins.client.renderer.entity;

import net.hydonclient.event.EventBus;
import net.hydonclient.event.events.render.EntityRenderEvent;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Render.class)
public abstract class MixinRender<T extends Entity> {

    @Inject(method = "doRender", at = @At("HEAD"))
    private void doRender(T entity, double x, double y, double z, float entityYaw, float partialTicks, CallbackInfo ci) {
        EventBus.call(new EntityRenderEvent(entity, (float) x, (float) y, (float) z, entity.rotationPitch, entityYaw, 1.0F));
    }
}

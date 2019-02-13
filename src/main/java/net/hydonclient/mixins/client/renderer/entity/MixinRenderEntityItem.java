package net.hydonclient.mixins.client.renderer.entity;

import net.hydonclient.event.EventBus;
import net.hydonclient.event.events.render.RenderItemEvent;
import net.minecraft.client.renderer.entity.RenderEntityItem;
import net.minecraft.entity.item.EntityItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RenderEntityItem.class)
public class MixinRenderEntityItem {

    @Inject(method = "doRender", at = @At("HEAD"), cancellable = true)
    private void doRender(EntityItem entity, double x, double y, double z, float entityYaw,
        float partialTicks, CallbackInfo ci) {
        RenderItemEvent renderItemEvent = new RenderItemEvent(entity, x, y, z, entityYaw,
            partialTicks);
        EventBus.call(renderItemEvent);
        if (renderItemEvent.isCancelled()) {
            ci.cancel();
        }
    }
}

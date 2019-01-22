package net.hydonclient.mixins.client.renderer.entity;

import net.hydonclient.Hydon;
import net.hydonclient.mods.itemphysics.physics.ClientPhysics;
import net.minecraft.client.renderer.entity.RenderEntityItem;
import net.minecraft.entity.item.EntityItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RenderEntityItem.class)
public class MixinRenderEntityItem {

    @Inject(method = "doRender", at = @At("HEAD"), cancellable = true)
    private void doRender(EntityItem entity, double x, double y, double z, float entityYaw, float partialTicks, CallbackInfo ci) {
        if (Hydon.SETTINGS.itemPhysics) {
            ClientPhysics.doRender(entity, x, y, z, entityYaw, partialTicks);
            ci.cancel();
        }
    }
}

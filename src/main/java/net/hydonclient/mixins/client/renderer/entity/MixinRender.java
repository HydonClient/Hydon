package net.hydonclient.mixins.client.renderer.entity;

import net.hydonclient.event.EventBus;
import net.hydonclient.event.events.render.EntityRenderEvent;
import net.hydonclient.util.patches.IPatchedTextureAtlasSprite;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Render.class)
public abstract class MixinRender {

    @Inject(method = "doRender", at = @At("HEAD"))
    private void doRender(Entity entity, double x, double y, double z, float entityYaw, float partialTicks, CallbackInfo ci) {
        EventBus.call(new EntityRenderEvent(entity, (float) x, (float) y, (float) z, entity.rotationPitch, entityYaw, 1.0F));
    }

    @Inject(method = "renderEntityOnFire", at = @At("HEAD"))
    private void addFireTextureOnRenderEntityOnFire(Entity entity, double x, double y, double z, float partialTicks, CallbackInfo ci) {
        TextureMap textureMapBlocks = Minecraft.getMinecraft().getTextureMapBlocks();
        ((IPatchedTextureAtlasSprite) textureMapBlocks.getAtlasSprite("minecraft:blocks/fire_layer_0")).markNeedsAnimationUpdate();
        ((IPatchedTextureAtlasSprite) textureMapBlocks.getAtlasSprite("minecraft:blocks/fire_layer_1")).markNeedsAnimationUpdate();
    }
}
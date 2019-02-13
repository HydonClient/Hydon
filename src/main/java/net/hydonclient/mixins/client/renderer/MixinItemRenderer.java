package net.hydonclient.mixins.client.renderer;

import net.hydonclient.util.patches.IPatchedTextureAtlasSprite;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemRenderer.class)
public class MixinItemRenderer {

    @Shadow @Final private Minecraft mc;

    @Inject(method = "renderFireInFirstPerson", at = @At("HEAD"))
    private void beforeRenderFireInFirstPerson(CallbackInfo ci) {
        ((IPatchedTextureAtlasSprite) mc.getTextureMapBlocks().getAtlasSprite("minecraft:blocks/fire_layer_1")).markNeedsAnimationUpdate();
    }
}

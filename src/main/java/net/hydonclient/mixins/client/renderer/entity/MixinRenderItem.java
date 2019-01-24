package net.hydonclient.mixins.client.renderer.entity;

import net.hydonclient.mixinsimp.client.renderer.entity.HydonRenderItem;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.model.IBakedModel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RenderItem.class)
public class MixinRenderItem {

    private HydonRenderItem impl = new HydonRenderItem();

    /**
     * Disable enchant glint
     *
     * @param model item model
     * @param ci    callback
     */
    @Inject(method = "renderEffect", at = @At("HEAD"), cancellable = true)
    private void renderEffect(IBakedModel model, CallbackInfo ci) {
        impl.renderEffect(model, ci);
    }
}

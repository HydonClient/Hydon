package net.hydonclient.mixinsimp.client.renderer.entity;

import net.hydonclient.Hydon;
import net.minecraft.client.resources.model.IBakedModel;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public class HydonRenderItem {

    public void renderEffect(IBakedModel model, CallbackInfo ci) {
        if (Hydon.SETTINGS.disableEnchantments) {
            ci.cancel();
        }
    }
}

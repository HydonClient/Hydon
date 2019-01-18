package net.hydonclient.mixinsimp.client.renderer.tileentity;

import net.hydonclient.Hydon;
import net.minecraft.entity.item.EntityItemFrame;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public class HydonRenderItemFrame {

    public void doRender(EntityItemFrame entity, double x, double y, double z, float entityYaw, float partialTicks, CallbackInfo ci) {
        if (Hydon.SETTINGS.disableItemFrames) {
            ci.cancel();
        }
    }
}

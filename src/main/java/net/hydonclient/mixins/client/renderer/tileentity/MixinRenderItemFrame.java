package net.hydonclient.mixins.client.renderer.tileentity;

import net.hydonclient.mixinsimp.client.renderer.tileentity.HydonRenderItemFrame;
import net.minecraft.client.renderer.tileentity.RenderItemFrame;
import net.minecraft.entity.item.EntityItemFrame;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RenderItemFrame.class)
public class MixinRenderItemFrame {

    private HydonRenderItemFrame impl = new HydonRenderItemFrame();

    @Inject(method = "doRender", at = @At("HEAD"), cancellable = true)
    public void doRender(EntityItemFrame entity, double x, double y, double z, float entityYaw, float partialTicks, CallbackInfo ci) {
        impl.doRender(entity, x, y, z, entityYaw, partialTicks, ci);
    }
}

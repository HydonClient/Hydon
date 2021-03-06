package net.hydonclient.mixins.client.renderer;

import net.hydonclient.event.EventBus;
import net.hydonclient.event.events.render.RenderWorldLastEvent;
import net.hydonclient.mixinsimp.client.renderer.HydonEntityRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = EntityRenderer.class, priority = 1500)
public class MixinEntityRenderer {

    @Shadow private float thirdPersonDistance;
    @Shadow private float thirdPersonDistanceTemp;
    @Shadow private boolean cloudFog;
    @Shadow private Minecraft mc;

    private HydonEntityRenderer impl = new HydonEntityRenderer((EntityRenderer) (Object) this);

    @Inject(method = "updateCameraAndRender", at = @At(value = "INVOKE", target = "Lnet/minecraft/profiler/Profiler;endStartSection(Ljava/lang/String;)V", shift = At.Shift.AFTER))
    private void updateCameraAndRender(float partialTicks, long nano, CallbackInfo ci) {
        impl.updateCameraAndRender();
    }

    /**
     * @author canelex
     * @reason 360 PerspectiveKeyBind Degree mod
     */
    @Overwrite
    private void orientCamera(float partialTicks) {
        impl.orientCamera(partialTicks, this.thirdPersonDistanceTemp, this.thirdPersonDistance, this.cloudFog, this.mc);
    }

    @Inject(method = "updateCameraAndRender", at = @At(value = "INVOKE_STRING", target = "Lnet/minecraft/profiler/Profiler;startSection(Ljava/lang/String;)V", args = "ldc=mouse"))
    private void updateCameraAndRender2(float partialTicks, long nanoTime, CallbackInfo ci) {
        impl.updateCameraAndRender();
    }

    @Inject(method = "renderWorldPass", at = @At(value = "INVOKE_STRING", target = "Lnet/minecraft/profiler/Profiler;endStartSection(Ljava/lang/String;)V", args = "ldc=outline"), cancellable = true)
    private void renderWorldPass(int pass, float part, long nano, CallbackInfo ci) {
        impl.renderWorldPass(part, mc);
    }

    @Inject(method = "renderWorldPass", at = @At(value = "INVOKE_STRING", target = "Lnet/minecraft/profiler/Profiler;endStartSection(Ljava/lang/String;)V", args = "ldc=hand", shift = Shift.BEFORE))
    private void renderWorldPass2(int pass, float part, long nano, CallbackInfo ci) {
        EventBus.call(new RenderWorldLastEvent(mc.renderGlobal, part));
    }
}

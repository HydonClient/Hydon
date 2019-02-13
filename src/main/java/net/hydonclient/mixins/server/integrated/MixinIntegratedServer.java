package net.hydonclient.mixins.server.integrated;

import net.hydonclient.util.patches.IPatchedMinecraft;
import net.minecraft.client.Minecraft;
import net.minecraft.crash.CrashReport;
import net.minecraft.server.integrated.IntegratedServer;
import net.minecraft.util.ReportedException;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.concurrent.Future;

@Mixin(IntegratedServer.class)
public abstract class MixinIntegratedServer {

    @Shadow
    @Final
    private Minecraft mc;

    @Redirect(method = "initiateShutdown", at = @At(value = "INVOKE", target = "Lcom/google/common/util/concurrent/Futures;getUnchecked(Ljava/util/concurrent/Future;)Ljava/lang/Object;", ordinal = 0))
    private <V> V initiateShutdown(Future<V> future) {
        return null;
    }

    /* Check if the integrated server crash was activated by Alt + F3 + C */
    @Inject(method = "tick", at = @At("HEAD"))
    private void tick(CallbackInfo ci) {
        if (((IPatchedMinecraft) mc).shouldCrashIntegratedServerNextTick()) {
            throw new ReportedException(new CrashReport("Manually triggered server-side debug crash", new Throwable()));
        }
    }
}

package net.hydonclient.mixins.client.renderer;

import net.hydonclient.util.patches.StateManager;
import net.minecraft.client.renderer.WorldRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldRenderer.class)
public abstract class MixinWorldRenderer implements StateManager.IResettable {

    @Shadow private boolean isDrawing;

    @Shadow public abstract void finishDrawing();

    /* Reset the WorldRenderer dispatcher after crashing */
    @Inject(method = "<init>", at = @At("RETURN"))
    private void init(int bufferSizeIn, CallbackInfo ci) {
        register();
    }

    @Override
    public void resetState() {
        if (isDrawing) finishDrawing();
    }
}

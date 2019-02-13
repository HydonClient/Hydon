package net.hydonclient.mixins.client.renderer.chunk;

import net.hydonclient.util.patches.TemporaryStorage;
import net.minecraft.client.renderer.chunk.ChunkCompileTaskGenerator;
import net.minecraft.client.renderer.chunk.CompiledChunk;
import net.minecraft.client.renderer.chunk.RenderChunk;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(RenderChunk.class)
public class MixinRenderChunk {

    @Inject(method = "rebuildChunk", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/chunk/CompiledChunk;<init>()V", ordinal = 0, shift = At.Shift.BY, by = 2), locals = LocalCapture.CAPTURE_FAILHARD)
    private void onRebuildChunk(float x, float y, float z, ChunkCompileTaskGenerator generator, CallbackInfo ci, CompiledChunk compiledChunk) {
        TemporaryStorage.currentCompiledChunk.set(compiledChunk);
    }
}

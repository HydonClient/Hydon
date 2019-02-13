package net.hydonclient.mixins.client.renderer;

import net.hydonclient.util.patches.IPatchedCompiledChunk;
import net.hydonclient.util.patches.IPatchedTextureAtlasSprite;
import net.hydonclient.util.patches.TemporaryStorage;
import net.minecraft.client.renderer.BlockFluidRenderer;
import net.minecraft.client.renderer.chunk.CompiledChunk;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(BlockFluidRenderer.class)
public class MixinBlockFluidRenderer {

    @ModifyVariable(method = "renderFluid", at = @At(value = "CONSTANT", args = "floatValue=0.001", ordinal = 1), ordinal = 0)
    private TextureAtlasSprite afterTextureDetermined(TextureAtlasSprite texture) {
        CompiledChunk compiledChunk = TemporaryStorage.currentCompiledChunk.get();
        if (compiledChunk != null) {
            ((IPatchedCompiledChunk) compiledChunk).getVisibleTextures().add(texture);
        } else {
            ((IPatchedTextureAtlasSprite) texture).markNeedsAnimationUpdate();
        }

        return texture;
    }
}

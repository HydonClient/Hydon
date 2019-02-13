package net.hydonclient.mixins.client.renderer.chunk;

import net.hydonclient.util.patches.IPatchedCompiledChunk;
import net.minecraft.client.renderer.chunk.CompiledChunk;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import org.spongepowered.asm.mixin.Mixin;

import java.util.HashSet;
import java.util.Set;

@Mixin(CompiledChunk.class)
public class MixinCompiledChunk implements IPatchedCompiledChunk {
    public Set<TextureAtlasSprite> visibleTextures = new HashSet<>();

    @Override
    public Set<TextureAtlasSprite> getVisibleTextures() {
        return visibleTextures;
    }
}

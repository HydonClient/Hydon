package net.hydonclient.mixins.client.renderer.texture;

import net.hydonclient.util.patches.IPatchedTextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(TextureAtlasSprite.class)
public class MixinTextureAtlasSprite implements IPatchedTextureAtlasSprite {

    private boolean needsAnimationUpdate = false;

    @Override
    public void markNeedsAnimationUpdate() {
        needsAnimationUpdate = true;
    }

    @Override
    public boolean needsAnimationUpdate() {
        return needsAnimationUpdate;
    }

    @Override
    public void unmarkNeedsAnimationUpdate() {
        needsAnimationUpdate = false;
    }
}

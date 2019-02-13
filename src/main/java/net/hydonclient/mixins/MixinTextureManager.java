package net.hydonclient.mixins;

import net.hydonclient.mixinsimp.HydonTextureManager;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.renderer.texture.ITickableTextureObject;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(TextureManager.class)
public abstract class MixinTextureManager {

    private HydonTextureManager hydonTextureManager = new HydonTextureManager(
        (TextureManager) (Object) this);

    /**
     * @author Koding
     * @reason Fix purple screen
     */
    @Overwrite
    public boolean loadTickableTexture(ResourceLocation textureLocation,
        ITickableTextureObject textureObj) {
        return hydonTextureManager.loadTickableTexture(textureLocation, textureObj);
    }

    /**
     * @author Koding
     * @reason Fix purple screen
     */
    @Overwrite
    public boolean loadTexture(ResourceLocation textureLocation, ITextureObject textureObj) {
        return hydonTextureManager.loadTexture(textureLocation, textureObj);
    }
}

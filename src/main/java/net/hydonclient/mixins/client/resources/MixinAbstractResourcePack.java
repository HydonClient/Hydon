package net.hydonclient.mixins.client.resources;

import net.hydonclient.mixinsimp.client.resources.HydonAbstractResourcePack;
import net.minecraft.client.resources.AbstractResourcePack;
import net.minecraft.client.resources.IResourcePack;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

@Mixin(AbstractResourcePack.class)
public abstract class MixinAbstractResourcePack implements IResourcePack {

    // TODO: Make pack image size adjustable (16 x 16 -> 256 x 256)
    private static int packImageSize = 32;

    @Shadow
    public abstract InputStream getInputStream(ResourceLocation texture) throws IOException;

    private HydonAbstractResourcePack impl = new HydonAbstractResourcePack((AbstractResourcePack) (Object) this);

    /**
     * @author prplz
     * @reason Memoryfix - Resize any resource pack image to use 32 x 32 to avoid high memory usage.
     */
    @Overwrite
    public BufferedImage getPackImage() throws IOException {
        return impl.getPackImage(packImageSize);
    }
}

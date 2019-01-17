package net.hydonclient.mixinsimp.client.resources;

import net.hydonclient.mixins.client.resources.IMixinAbstractResourcePack;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.resources.AbstractResourcePack;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class HydonAbstractResourcePack {

    private AbstractResourcePack abstractResourcePack;

    public HydonAbstractResourcePack(AbstractResourcePack abstractResourcePack) {
        this.abstractResourcePack = abstractResourcePack;
    }

    public BufferedImage getPackImage(int iconSize) throws IOException {
        BufferedImage originalImage = TextureUtil.readBufferedImage(((IMixinAbstractResourcePack) abstractResourcePack).callGetInputStreamByName("pack.png"));

        if (originalImage == null) {
            return null;
        }

        BufferedImage resizedImage = new BufferedImage(iconSize, iconSize, BufferedImage.TYPE_INT_ARGB);

        Graphics graphics = resizedImage.getGraphics();
        graphics.drawImage(originalImage, 0, 0, iconSize, iconSize, null);
        graphics.dispose();

        return resizedImage;
    }
}

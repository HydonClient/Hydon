package net.hydonclient.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;

public enum Images {

    ALT_BG_1(new ResourceLocation("textures/alt-bg-1.png"), 1920, 1080),
    ALT_BG_2(new ResourceLocation("textures/alt-bg-2.png"), 2560, 1600),
    LOGO(new ResourceLocation("textures/logo.png"), 1920, 1080);

    private ResourceLocation location;
    private int width, height;

    Images(ResourceLocation location, int width, int height) {
        this.location = location;
        this.width = width;
        this.height = height;
    }

    public void bind() {
        bind(Minecraft.getMinecraft().getTextureManager());
    }

    public void bind(TextureManager textureManager) {
        textureManager.bindTexture(location);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public ResourceLocation getLocation() {
        return location;
    }

}

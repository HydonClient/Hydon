package net.hydonclient.util.maps;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;

public enum Images {

    LOGO(new ResourceLocation("textures/logo.png"), 1920, 1080),
    LOGO_V2(new ResourceLocation("textures/logo_v2.png"), 1920, 1080),
    LOGO_V2_DOWNSCALED(new ResourceLocation("textures/logo_v2_downscaled.png"), 288, 162);

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

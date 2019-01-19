package net.hydonclient.gui.enums;

import net.hydonclient.Hydon;
import net.hydonclient.gui.main.HydonMainGui;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;

public enum EnumBackground {

    BACKGROUND_1(new ResourceLocation("textures/bg-1.png"), 1920, 1080),
    BACKGROUND_2(new ResourceLocation("textures/bg-2.png"), 2560, 1600),
    BACKGROUND_3(new ResourceLocation("textures/bg-3.png"), 1920, 1080),
    BACKGROUND_4(new ResourceLocation("textures/bg-4.png"), 1920, 1080);

    private ResourceLocation location;
    private int width, height;

    EnumBackground(ResourceLocation location, int width, int height) {
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

    public static void cycleBackground() {
        EnumBackground currentBg = Hydon.SETTINGS.getCurrentBackground();
        Hydon.SETTINGS.currentBackground = (currentBg.ordinal() + 1) % values().length;
        HydonMainGui.currentBackgroundButton.setLabel(
            "Current Background: " + (Hydon.SETTINGS.getCurrentBackground().ordinal() + 1));
    }

}

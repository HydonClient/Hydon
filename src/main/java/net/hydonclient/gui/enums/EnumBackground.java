package net.hydonclient.gui.enums;

import net.hydonclient.Hydon;
import net.hydonclient.gui.main.HydonMainGui;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;

public enum EnumBackground {

    /**
     * The currently added custom backgrounds
     * <p>
     * resourceName is the location of the image
     * width is the width of the image
     * height is the height of the image
     */
    BACKGROUND_1(new ResourceLocation("textures/bg-1.png"), 1920, 1080),
    BACKGROUND_2(new ResourceLocation("textures/bg-2.png"), 2560, 1600),
    BACKGROUND_3(new ResourceLocation("textures/bg-3.png"), 1920, 1080),
    BACKGROUND_4(new ResourceLocation("textures/bg-4.png"), 1920, 1080);

    /**
     * The location for the background texture
     */
    private ResourceLocation location;

    /**
     * The width and height of the texture
     */
    private int width, height;

    /**
     * Register the images
     *
     * @param location the texture location
     * @param width    the texture width
     * @param height   the texture height
     */
    EnumBackground(ResourceLocation location, int width, int height) {
        this.location = location;
        this.width = width;
        this.height = height;
    }

    /**
     * Send the image texture to the TextureManager
     */
    public void bind() {
        bind(Minecraft.getMinecraft().getTextureManager());
    }

    /**
     * Send the image texture to the TextureManager
     *
     * @param textureManager the texture manager
     */
    public void bind(TextureManager textureManager) {
        textureManager.bindTexture(location);
    }

    /**
     * Image width
     *
     * @return the image width
     */
    public int getWidth() {
        return width;
    }

    /**
     * Image height
     *
     * @return the image height
     */
    public int getHeight() {
        return height;
    }

    /**
     * Image texture location
     *
     * @return the texture location
     */
    public ResourceLocation getLocation() {
        return location;
    }

    /**
     * Cycle through the current selection of backgrounds
     * Allows for a various amount of backgrounds to choose from
     */
    public static void cycleBackground() {
        EnumBackground currentBg = Hydon.SETTINGS.getCurrentBackground();
        Hydon.SETTINGS.currentBackground = (currentBg.ordinal() + 1) % values().length;
        HydonMainGui.currentBackgroundButton.setLabel(
                "Current Background: " + (Hydon.SETTINGS.getCurrentBackground().ordinal() + 1));
    }

}

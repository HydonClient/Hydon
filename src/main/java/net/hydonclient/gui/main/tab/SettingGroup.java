package net.hydonclient.gui.main.tab;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import net.hydonclient.gui.main.HydonMainGui;
import net.hydonclient.gui.main.element.SettingsElement;
import net.hydonclient.gui.main.element.impl.SettingsButton;
import net.hydonclient.ttf.HydonFonts;
import net.hydonclient.ttf.MinecraftFontRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;

public class SettingGroup {

    /**
     * The name of the group
     */
    private String name;

    /**
     * The options inside of the current group
     */
    private List<SettingsElement> settingsElements = new ArrayList<>();

    /**
     * The button that holds all of the options
     */
    private SettingsButton settingsButton;

    /**
     * Regular font for the text
     */
    private final MinecraftFontRenderer regularFont = HydonFonts.PRODUCT_SANS_REGULAR;

    /**
     * Bold font for the text
     */
    private final MinecraftFontRenderer boldFont = HydonFonts.PRODUCT_SANS_BOLD;

    /**
     * width is how wide it'll be
     * height is how long it'll be
     * <p>
     * x is the horizontal position
     * y is the vertical position
     */
    protected int width, height, x, y;

    /**
     * The constructor for groups
     *
     * @param name the name of the group
     */
    public SettingGroup(String name) {
        this.name = name;
        this.settingsButton = new SettingsButton(name, () -> {
        });
    }

    /**
     * Draw the group
     */
    public void draw() {
        ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());

        this.x = scaledResolution.getScaledWidth() - width;
        this.y = 0;
        this.width = 150;
        this.height = 20;
        settingsElements.forEach(settingsElement -> this.height += settingsElement.getHeight());

        Gui.drawRect(scaledResolution.getScaledWidth() - width, 0,
                scaledResolution.getScaledWidth(), scaledResolution.getScaledHeight(),
                new Color(0, 0, 0, 20).getRGB());

        int rightSideOffset = HydonMainGui.INSTANCE.rightSideOffset;

        boldFont.drawCenteredString(name, x + width / 2f, 5 + rightSideOffset, 0xffffff);

        AtomicInteger yMod = new AtomicInteger();
        settingsElements.forEach(settingsElement -> {
            settingsElement.draw(x, y + 25 + yMod.get() + rightSideOffset);
            yMod.addAndGet(settingsElement.getHeight() + 5);
        });
    }

    /**
     * When the group is clicked, it'll reveal the configuration inside of it
     *
     * @param mouseButton the mouse button being clicked
     * @param mouseX      the x position of the mouse
     * @param mouseY      the y position of the mouse
     */
    public void mouseClicked(int mouseButton, int mouseX, int mouseY) {
        settingsElements
                .forEach(settingsElement -> settingsElement.mouseClicked(mouseButton, mouseX, mouseY));
    }

    /**
     * When the mouse is released, it'll reveal the configuration inside of it
     *
     * @param mouseX the x position of the mouse
     * @param mouseY the y position of the mouse
     * @param state  the current state of the mouse button being clicked (down or up)
     */
    public void mouseReleased(int mouseX, int mouseY, int state) {
        settingsElements.forEach(settingsElement -> settingsElement.mouseReleased(mouseX, mouseY));
    }

    /**
     * When the mouse is dragged, it'll reveal the configuration inside of it
     *
     * @param mouseX             the x position of the mouse
     * @param mouseY             the y position of the mouse
     * @param clickedMouseButton the mouse button being clicked
     * @param timeSinceLastClick the time since the button was last clicked
     */
    public void mouseDragged(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {
        settingsElements.forEach(settingsElement -> settingsElement.mouseDragged(mouseX, mouseY));
    }

    /**
     * Add all of the configuration that are initialized with SettingGroup
     *
     * @param elements all of the configuration that is initialized to this
     */
    public void addElements(SettingsElement... elements) {
        settingsElements.addAll(Arrays.asList(elements));
    }

    /**
     * Get all of the configuration that are initialized with SettingGroup
     *
     * @return all of the configuration that is initialized to this
     */
    public List<SettingsElement> getSettingsElements() {
        return settingsElements;
    }

    /**
     * How long it'll be
     *
     * @return the height
     */
    public int getHeight() {
        return height;
    }

    /**
     * How wide it'll be
     *
     * @return the width
     */
    public int getWidth() {
        return width;
    }

    /**
     * Where it'll be horizontally
     *
     * @return the horizontal position
     */
    public int getX() {
        return x;
    }

    /**
     * Where it'll be vertically
     *
     * @return the vertical position
     */
    public int getY() {
        return y;
    }

    /**
     * Get the button that's set
     *
     * @return the set button
     */
    public SettingsButton getSettingsButton() {
        return settingsButton;
    }

    /**
     * What'll happen when the button is clicked
     *
     * @param runnable the action
     */
    public void setSettingsButton(Runnable runnable) {
        this.settingsButton = new SettingsButton(name, runnable);
    }
}

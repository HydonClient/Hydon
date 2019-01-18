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

    private String name;
    private List<SettingsElement> settingsElements = new ArrayList<>();
    private SettingsButton settingsButton;

    private final MinecraftFontRenderer regularFont = HydonFonts.PRODUCT_SANS_REGULAR;
    private final MinecraftFontRenderer boldFont = HydonFonts.PRODUCT_SANS_BOLD;

    protected int width, height, x, y;

    public SettingGroup(String name) {
        this.name = name;
        this.settingsButton = new SettingsButton(name, () -> {});
    }

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

    public void mouseClicked(int mouseButton, int mouseX, int mouseY) {
        settingsElements
            .forEach(settingsElement -> settingsElement.mouseClicked(mouseButton, mouseX, mouseY));
    }

    public void addElements(SettingsElement... elements) {
        settingsElements.addAll(Arrays.asList(elements));
    }

    public List<SettingsElement> getSettingsElements() {
        return settingsElements;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public SettingsButton getSettingsButton() {
        return settingsButton;
    }

    public void setSettingsButton(Runnable runnable) {
        this.settingsButton = new SettingsButton(name, runnable);
    }
}

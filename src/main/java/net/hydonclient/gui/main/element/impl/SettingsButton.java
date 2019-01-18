package net.hydonclient.gui.main.element.impl;

import java.awt.Color;
import net.hydonclient.gui.main.element.SettingsElement;
import net.hydonclient.util.GuiUtils;
import net.minecraft.client.gui.Gui;

public class SettingsButton extends SettingsElement {

    private String label;
    private Runnable onClick;

    private boolean overrideHovered;

    public SettingsButton(String label, Runnable onClick) {
        this.label = label;
        this.onClick = onClick;
        this.width = 150;
        this.height = 15;
    }

    @Override
    public void draw(int x, int y) {
        this.x = x;
        this.y = y;

        Gui.drawRect(x + 5, y, x + width - 5, y + height, new Color(0, 0, 0, GuiUtils.isHovered(x, y, width, height) || overrideHovered ? 50 : 70).getRGB());
        regularFont.drawCenteredString(label, x + width / 2f, y + 3, 0xffffff);
    }

    @Override
    public void mouseClicked(int button, int mouseX, int mouseY) {
        if (button != 0) {
            return;
        }

        if (GuiUtils.isHovered(getX(), getY(), width, height)) {
            onClick.run();
        }
    }

    public String getLabel() {
        return label;
    }

    public boolean isOverrideHovered() {
        return overrideHovered;
    }

    public void setOverrideHovered(boolean overrideHovered) {
        this.overrideHovered = overrideHovered;
    }
}

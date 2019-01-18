package net.hydonclient.gui.main.element.impl;

import java.awt.Color;
import net.hydonclient.gui.main.element.SettingsElement;
import net.hydonclient.util.GuiUtils;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.MathHelper;

public class SettingsToggle extends SettingsElement {

    private String label;
    private boolean toggled;
    private int progress;
    private Result resultCallback;

    public SettingsToggle(String label, boolean toggled, Result resultCallback) {
        this.label = label;
        this.toggled = toggled;
        this.resultCallback = resultCallback;
        this.width = 150;
        this.height = 15;
    }

    @Override
    public void draw(int x, int y) {
        this.x = x;
        this.y = y;
        progress = MathHelper.clamp_int(progress + (toggled ? 5 : -5), 0, 100);
        Gui.drawRect(x + 5, y, x + width - 5, y + height,
            GuiUtils.blendColors(new Color(0x8bc34a), new Color(0xF44336), progress / 100d)
                .getRGB());
        regularFont
            .drawCenteredString(label + ": " + (toggled ? "ON" : "OFF"), x + width / 2f,
                y + 3, 0xffffff);
    }

    @Override
    public void mouseClicked(int button, int mouseX, int mouseY) {
        if (button != 0) {
            return;
        }

        if (GuiUtils.isHovered(getX(), getY(), width, height)) {
            toggled = !toggled;
            resultCallback.onResult(toggled);
        }
    }

    public String getLabel() {
        return label;
    }

    public boolean isToggled() {
        return toggled;
    }

    public interface Result {

        void onResult(boolean result);
    }
}

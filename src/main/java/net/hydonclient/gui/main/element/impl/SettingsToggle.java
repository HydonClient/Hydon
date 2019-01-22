package net.hydonclient.gui.main.element.impl;

import java.awt.Color;

import net.hydonclient.gui.main.element.SettingsElement;
import net.hydonclient.util.GuiUtils;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.MathHelper;

public class SettingsToggle extends SettingsElement {

    /**
     * The button text
     */
    private String label;

    /**
     * Decide if it's on or off
     */
    private boolean toggled;

    /**
     * Fade animation for when state is toggled
     */
    private int progress = 50;

    /**
     * The new configuration state (on/off)
     */
    private Result resultCallback;

    /**
     * The constructor for toggles
     *
     * @param label          the buttons text
     * @param toggled        the current state of the option
     * @param resultCallback the new state of the option
     */
    public SettingsToggle(String label, boolean toggled, Result resultCallback) {
        this.label = label;
        this.toggled = toggled;
        this.resultCallback = resultCallback;
        this.width = 150;
        this.height = 15;
    }

    /**
     * Draw the toggle
     *
     * @param x location of the note on the x axis
     * @param y location of the note on the y axis
     */
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

    /**
     * If the button is clicked, toggle the state
     *
     * @param button the mouse button being clicked
     * @param mouseX the current mouse x location
     * @param mouseY the current mouse y location
     */
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

    /**
     * Get the button text
     *
     * @return the button text
     */
    public String getLabel() {
        return label;
    }

    /**
     * Get the current state of the option
     *
     * @return the state
     */
    public boolean isToggled() {
        return toggled;
    }

    /**
     * The class for the end-result of the toggle
     */
    public interface Result {

        /**
         * When the button is clicked, what will be the new state for the configuration
         *
         * @param result the new configuration state
         */
        void onResult(boolean result);
    }
}

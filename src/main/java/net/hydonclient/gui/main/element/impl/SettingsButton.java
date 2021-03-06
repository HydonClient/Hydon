package net.hydonclient.gui.main.element.impl;

import java.awt.Color;

import net.hydonclient.gui.main.element.SettingsElement;
import net.hydonclient.util.GraphicsUtil;
import net.hydonclient.util.GuiUtils;
import net.minecraft.client.gui.Gui;
import org.lwjgl.input.Mouse;

public class SettingsButton extends SettingsElement {

    /**
     * The button text
     */
    private String label;

    /**
     * The buttons action
     */
    private Runnable onClick;

    /**
     * If the button is hovered over, override the current alpha
     * to change the color of the button
     */
    private boolean overrideHovered;

    /**
     * The constructor for Buttons
     *
     * @param label   the text as to what the button will say
     * @param onClick when clicked, what will it do (open a gui? print a message in chat?)
     */
    public SettingsButton(String label, Runnable onClick) {
        this.label = label;
        this.onClick = onClick;
        this.width = 150;
        this.height = 15;
    }

    /**
     * Draw the button to the screen
     *
     * @param x location of the button on the x axis
     * @param y location of the button on the y axis
     */
    @Override
    public void draw(int x, int y) {
        this.x = x;
        this.y = y;

        Gui.drawRect(x + width - 30, y, x + width - 10, y + height - 1, new Color(255, 255, 255, 255).getRGB());
        GraphicsUtil.drawRegularPolygon(x + width - 30, y + 7, 7, 0xffffff);
        GraphicsUtil.drawRegularPolygon(x + width - 10, y + 7, 7, 0xffffff);
        regularFont.drawCenteredString("Click", x + width - 20, y + 4, 0x000000);
        regularFont.drawString(label, x + 5, y + 3, 0xffffff);
    }

    /**
     * Invoked when the left click button is clicked
     *
     * @param button the mouse button being clicked
     * @param mouseX the current mouse x location
     * @param mouseY the current mouse y location
     */
    @Override
    public void mouseClicked(int button, int mouseX, int mouseY) {

        /* If the mouse button isn't left click, return */
        if (button != 0) {
            return;
        }

        /*
         * If the button is hovered over and it's clicked
         * Do what the button is told to do
         */
        if (GuiUtils.isHovered(getX(), getY(), width, height)) {
            onClick.run();
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
     * If the button is hovered over
     *
     * @return the button color
     */
    public boolean isOverrideHovered() {
        return overrideHovered;
    }

    /**
     * When the button is hovered over, set the hover boolean to be true
     *
     * @param overrideHovered true when hovered over
     */
    public void setOverrideHovered(boolean overrideHovered) {
        this.overrideHovered = overrideHovered;
    }

    /**
     * Set the button text
     *
     * @param label the button text
     */
    public void setLabel(String label) {
        this.label = label;
    }
}

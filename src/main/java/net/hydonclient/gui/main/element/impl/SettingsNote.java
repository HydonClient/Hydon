package net.hydonclient.gui.main.element.impl;

import net.hydonclient.gui.main.element.SettingsElement;
import net.hydonclient.util.GuiUtils;
import net.minecraft.client.gui.Gui;

import java.awt.*;

public class SettingsNote extends SettingsElement {

    /**
     * The button text
     */
    private String label;

    /**
     * The constructor for notes
     *
     * @param label what the note will say
     */
    public SettingsNote(String label) {
        this.label = label;
        this.width = 150;
        this.height = 15;
    }

    /**
     * Draw the note
     *
     * @param x location of the note on the x axis
     * @param y location of the note on the y axis
     */
    @Override
    public void draw(int x, int y) {
        this.x = x;
        this.y = y;

        Gui.drawRect(x + 5, y, x + width - 5, y + height, new Color(0, 0, 0, 50).getRGB());
        regularFont.drawCenteredString(label, x + width / 2f, y + 3, 0xffffff);
    }

    /**
     * If the note is clicked, ignore it, not meant to do anything
     *
     * @param button the mouse button being clicked
     * @param mouseX the current mouse x location
     * @param mouseY the current mouse y location
     */
    @Override
    public void mouseClicked(int button, int mouseX, int mouseY) {
    }
}

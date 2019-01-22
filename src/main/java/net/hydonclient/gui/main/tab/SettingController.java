package net.hydonclient.gui.main.tab;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import net.hydonclient.gui.main.HydonMainGui;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;

/**
 * The controller for all of the settings
 */
public class SettingController {

    /**
     * The list of elements that can be clicked on to dropdown other options
     */
    private List<SettingsDropdownElement> dropdownElements = new ArrayList<>();

    /**
     * Draw the dropdown element
     */
    public void draw() {
        ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());

        int width = 150;
        int height = scaledResolution.getScaledHeight();

        Gui.drawRect(0, 0, width, height, new Color(0, 0, 0, 40).getRGB());

        int leftSideOffset = HydonMainGui.INSTANCE.leftSideOffset;

        AtomicInteger yMod = new AtomicInteger(leftSideOffset);
        dropdownElements.forEach(settingsDropdownElement -> {
            settingsDropdownElement.draw(0, yMod.get());
            yMod.addAndGet(settingsDropdownElement.getHeight() + 5);
        });
    }

    /**
     * When the dropdown menu is clicked, it'll reveal the other group elements inside of it
     *
     * @param button the mouse button being clicked
     * @param mouseX the x position of the mouse
     * @param mouseY the y position of the mouse
     */
    public void mouseClicked(int button, int mouseX, int mouseY) {
        dropdownElements.forEach(settingsDropdownElement -> settingsDropdownElement
                .mouseClicked(button, mouseX, mouseY));
    }

    /**
     * Add all of the group elements that are initialized with SettingsDropdownElement
     *
     * @param elements the group elements that are initialized to this
     */
    public void addElements(SettingsDropdownElement... elements) {
        dropdownElements.addAll(Arrays.asList(elements));
    }

    /**
     * Get the group elements that are initialized with SettingsDropdownElement
     *
     * @return the group elements that are initialized to this
     */
    public List<SettingsDropdownElement> getDropdownElements() {
        return dropdownElements;
    }
}

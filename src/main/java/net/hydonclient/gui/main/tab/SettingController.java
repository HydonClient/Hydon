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

public class SettingController {

    private List<SettingsDropdownElement> dropdownElements = new ArrayList<>();

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

    public void mouseClicked(int button, int mouseX, int mouseY) {
        dropdownElements.forEach(settingsDropdownElement -> settingsDropdownElement
            .mouseClicked(button, mouseX, mouseY));
    }

    public void addElements(SettingsDropdownElement... elements) {
        dropdownElements.addAll(Arrays.asList(elements));
    }

    public List<SettingsDropdownElement> getDropdownElements() {
        return dropdownElements;
    }
}

package net.hydonclient.gui.main.tab;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import net.hydonclient.gui.main.HydonMainGui;
import net.hydonclient.ttf.HydonFonts;
import net.hydonclient.ttf.MinecraftFontRenderer;
import net.hydonclient.util.GuiUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

public class SettingsDropdownElement {

    private String name;
    private boolean expanded;
    private List<SettingGroup> settingGroups = new ArrayList<>();

    private final MinecraftFontRenderer regularFont = HydonFonts.PRODUCT_SANS_REGULAR;
    private final MinecraftFontRenderer boldFont = HydonFonts.PRODUCT_SANS_BOLD;

    protected int width, height, x, y;

    public SettingsDropdownElement(String name) {
        this.name = name;
    }

    public void draw(int x, int y) {
        this.x = x;
        this.y = y;
        this.width = 150;
        this.height = 20;
        if (this.expanded) {
            this.height += 5;
            settingGroups
                .forEach(settingsElement -> this.height += settingsElement.getSettingsButton().getHeight() + 5);
        }

        Gui.drawRect(x + 5, y + 5, x + width - 5, y + 20,
            new Color(0, 0, 0, GuiUtils.isHovered(x, y, width, height) || expanded ? 50 : 70).getRGB());
        regularFont
            .drawCenteredString(name, x + width / 2f, y + 8,
                0xffffff);

        if (this.expanded) {
            Gui.drawRect(x + 10, y + 20, x + width - 10, y + height, new Color(0, 0, 0, 30).getRGB());
            AtomicInteger yMod = new AtomicInteger();
            settingGroups.forEach(settingGroup -> {
                settingGroup.getSettingsButton().setOverrideHovered(((HydonMainGui) Minecraft
                    .getMinecraft().currentScreen).currentGroup == settingGroup);
                settingGroup.setSettingsButton(() -> ((HydonMainGui) Minecraft
                    .getMinecraft().currentScreen).currentGroup = settingGroup);
                settingGroup.getSettingsButton().setWidth(130);
                settingGroup.getSettingsButton().draw(x + 10, y + 25 + yMod.get());
                yMod.addAndGet(settingGroup.getSettingsButton().getHeight() + 5);
            });
        }
    }

    public void mouseClicked(int button, int mouseX, int mouseY) {
        if (this.expanded) {
            settingGroups
                .forEach(settingGroup -> {
                    if (settingGroup == HydonMainGui.INSTANCE.currentGroup) {
                        settingGroup.mouseClicked(button, mouseX, mouseY);
                    }
                });
        }
        settingGroups.forEach(
            settingGroup -> settingGroup.getSettingsButton().mouseClicked(button, mouseX, mouseY));
        if (GuiUtils.isHovered(getX(), getY(), this.width, 20) && button == 0) {
            expanded = !expanded;
        }
    }

    public void addElements(SettingGroup... elements) {
        settingGroups.addAll(Arrays.asList(elements));
    }

    public List<SettingGroup> getSettingsElements() {
        return settingGroups;
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
}

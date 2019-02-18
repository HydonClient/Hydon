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
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class SettingsDropdownElement {

    /**
     * The name of the dropdown element
     */
    private String name;

    /**
     * If the element is clicked, return true
     */
    private boolean expanded;

    /**
     * The list of groups inside of the dropdown element
     */
    private List<SettingGroup> settingGroups = new ArrayList<>();

    /**
     * Regular font for the text
     */
    private final MinecraftFontRenderer regularFont = HydonFonts.FONT_REGULAR;

    /**
     * Bold font for the text
     */
    private final MinecraftFontRenderer boldFont = HydonFonts.FONT_BOLD;

    /**
     * width is how wide it'll be
     * height is how long it'll be
     * <p>
     * x is the horizontal position
     * y is the vertical position
     */
    protected int width, height, x, y;

    /**
     * The constructor for dropdown elements
     *
     * @param name the name of the menu
     */
    public SettingsDropdownElement(String name) {
        this.name = name;
    }

    /**
     * Draw the dropdown to the screen
     *
     * @param x location of the button on the x axis
     * @param y location of the button on the y axis
     */
    public void draw(int x, int y) {
        this.x = x;
        this.y = y;
        this.width = 150;
        this.height = 20;

        ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
        int padding = scaledResolution.getScaledHeight() / 8;
        int sidebarWidth = scaledResolution.getScaledWidth() / 4;

        if (this.expanded) {
            this.height += 5;
            settingGroups
                    .forEach(settingsElement -> this.height += settingsElement.getDropdownButton().getHeight() + 5);
        }

        if (GuiUtils.isHovered(x, y, width, height) || expanded) {
            GlStateManager.pushMatrix();
            Color color = new Color(0x42a5f5);
            GlStateManager.enableBlend();
            Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("textures/shapes/" + (expanded ? "down" : "right") + "-triangle.png"));
            GlStateManager.color(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f, color.getAlpha() / 255f);
            Gui.drawModalRectWithCustomSizedTexture(x + 10, y + 5, 0, 0, 10, 10, 10, 10);
            GlStateManager.popMatrix();
        }

        regularFont.drawString(name, x + 25, y + 7, 0xffffff);

        if (this.expanded) {
//            Gui.drawRect(x + 10, y + 20, x + width - 10, y + height, new Color(0, 0, 0, 30).getRGB());
            AtomicInteger yMod = new AtomicInteger();

            AtomicInteger index = new AtomicInteger();
            settingGroups.forEach(settingGroup -> {
                if (y + 35 + yMod.get() >= scaledResolution.getScaledHeight() - padding) {
                    return;
                }

                settingGroup.getDropdownButton().setOverrideHovered(((HydonMainGui) Minecraft
                        .getMinecraft().currentScreen).currentGroup == settingGroup);
                settingGroup.setDropdownButton(() -> ((HydonMainGui) Minecraft
                        .getMinecraft().currentScreen).currentGroup = settingGroup);
                settingGroup.getDropdownButton().setWidth(130);
                settingGroup.getDropdownButton().draw(x + 10, y + 25 + yMod.get());
                yMod.addAndGet(settingGroup.getDropdownButton().getHeight() + 5);
                index.getAndIncrement();
            });
        }
    }

    /**
     * Show the groups inside of the menu when clicked
     *
     * @param button the mouse button being clicked
     * @param mouseX the current mouse x location
     * @param mouseY the current mouse y location
     */
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
                settingGroup -> settingGroup.getDropdownButton().mouseClicked(button, mouseX, mouseY));
        if (GuiUtils.isHovered(getX(), getY(), this.width, 20) && button == 0) {
            expanded = !expanded;
        }
    }

    /**
     * Add all of the group elements that are initialized with SettingsDropdownElement
     *
     * @param elements the groups that are initialized to SettingGroup
     */
    public void addElements(SettingGroup... elements) {
        settingGroups.addAll(Arrays.asList(elements));
    }

    /**
     * Get the group elements that are initialized with SettingsDropdownElement
     *
     * @return the groups that are initialized to SettingGroup
     */
    public List<SettingGroup> getSettingsElements() {
        return settingGroups;
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
     * If it's expanded
     * @return expanded
     */
    public boolean isExpanded() {
        return expanded;
    }
}

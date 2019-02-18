package net.hydonclient.gui.main.tab;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import net.hydonclient.Hydon;
import net.hydonclient.gui.enums.EnumBackground;
import net.hydonclient.gui.main.HydonMainGui;
import net.hydonclient.util.GraphicsUtil;
import net.hydonclient.util.GuiUtils;
import net.hydonclient.util.maps.Images;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;

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

        int padding = scaledResolution.getScaledHeight() / 8;
        int width = scaledResolution.getScaledWidth() - padding;
        int height = scaledResolution.getScaledHeight() - padding;

        Gui.drawRect(padding - 1, padding - 1, width + 1, height + 1, new Color(10, 10, 10).getRGB());
        Gui.drawRect(padding, padding, width, height, new Color(5, 5, 5).getRGB());

        GlStateManager.enableBlend();
        float logoFactor = 2.5f;
        Images.LOGO_V2_DOWNSCALED.bind();
        GlStateManager.color(1, 1, 1, 1);
        Gui.drawModalRectWithCustomSizedTexture(padding, padding, 0, 0,
            (int) (Images.LOGO_V2_DOWNSCALED.getWidth() / logoFactor), (int) (Images.LOGO_V2_DOWNSCALED.getHeight() / logoFactor),
            Images.LOGO_V2_DOWNSCALED.getWidth() / logoFactor, Images.LOGO_V2_DOWNSCALED.getHeight() / logoFactor);

        int startHeight = (int) (padding + Images.LOGO_V2_DOWNSCALED.getHeight() / logoFactor);
        int sidebarWidth = scaledResolution.getScaledWidth() / 4;

        Gui.drawRect(padding, startHeight, sidebarWidth, height, new Color(7, 7, 7).getRGB());
        Gui.drawRect(sidebarWidth, startHeight, width, height, new Color(6, 6, 6).getRGB());
        Gui.drawRect(padding, startHeight - 1, width, startHeight, new Color(10, 10, 10).getRGB());
        Gui.drawRect(sidebarWidth, startHeight - 1, sidebarWidth + 1, height, new Color(10, 10, 10).getRGB());

        AtomicInteger yMod = new AtomicInteger(startHeight + HydonMainGui.INSTANCE.leftSideOffset);
        dropdownElements.forEach(settingsDropdownElement -> {
            if (yMod.get() + 20 >= scaledResolution.getScaledHeight() - padding) {
                return;
            }

            settingsDropdownElement.draw(padding, yMod.get());
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

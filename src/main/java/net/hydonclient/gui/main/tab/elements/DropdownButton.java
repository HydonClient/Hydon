package net.hydonclient.gui.main.tab.elements;

import java.awt.Color;
import net.hydonclient.gui.main.element.SettingsElement;
import net.hydonclient.util.GuiUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;

public class DropdownButton extends SettingsElement {

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
    public DropdownButton(String label, Runnable onClick) {
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

        if (GuiUtils.isHovered(x, y, width, height)) {
            Color color = new Color(0x42a5f5);
            GlStateManager.enableBlend();
            Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("textures/shapes/right-triangle.png"));
            GlStateManager.color(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f, color.getAlpha() / 255f);
            Gui.drawModalRectWithCustomSizedTexture(x + 8, y + 2, 0, 0, height / 4 * 3, height / 4 * 3, (height >> 2) * 3, (height >> 2) * 3);
        }

        regularFont.drawString(label, x + 25, y + 3, 0xffffff);
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

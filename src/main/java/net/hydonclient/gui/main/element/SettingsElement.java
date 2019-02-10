package net.hydonclient.gui.main.element;

import net.hydonclient.ttf.HydonFonts;
import net.hydonclient.ttf.MinecraftFontRenderer;

/**
 * The base class for elements
 * (buttons, notes, sliders, toggles, etc)
 */
public abstract class SettingsElement {

    /**
     * Regular font for the text
     */
    protected final MinecraftFontRenderer regularFont = HydonFonts.FONT_REGULAR;

    /**
     * Bold font for the text
     */
    protected final MinecraftFontRenderer boldFont = HydonFonts.FONT_BOLD;

    /**
     * width is how wide it'll be
     * height is how long it'll be
     * <p>
     * x is the horizontal position
     * y is the vertical position
     */
    protected int width, height, x, y;

    /**
     * Draw the element
     *
     * @param x the horizontal position of the element
     * @param y the vertical position of the element
     */
    public abstract void draw(int x, int y);

    /**
     * Return if the button was clicked
     *
     * @param button the mouse button being clicked
     * @param mouseX the x position of the mouse
     * @param mouseY the y position of the mouse
     */
    public abstract void mouseClicked(int button, int mouseX, int mouseY);

    /**
     * Return if the mouse is currently dragging
     *
     * @param mouseX the x position of the mouse
     * @param mouseY the y position of the mouse
     */
    public void mouseDragged(int mouseX, int mouseY) {

    }

    /**
     * Return if the mouse was released after dragging
     *
     * @param mouseX the x position of the mouse
     * @param mouseY the y position of the mouse
     */
    public void mouseReleased(int mouseX, int mouseY) {

    }

    /**
     * Get the height of the element
     *
     * @return the element's height
     */
    public int getHeight() {
        return height;
    }

    /**
     * Get the width of the element
     *
     * @return the element's width
     */
    public int getWidth() {
        return width;
    }

    /**
     * Get the horizontal position of the element
     *
     * @return the element's horizontal position
     */
    public int getX() {
        return x;
    }

    /**
     * Get the vertical position of the element
     *
     * @return the element's vertical position
     */
    public int getY() {
        return y;
    }

    /**
     * Set the width of the element
     *
     * @param width the width of the element
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * Set the height of the element
     *
     * @param height the height of the element
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * Set the horizontal position of the element
     *
     * @param x the horizontal position of the element
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Set the vertical position of the element
     *
     * @param y the vertical position of the element
     */
    public void setY(int y) {
        this.y = y;
    }
}

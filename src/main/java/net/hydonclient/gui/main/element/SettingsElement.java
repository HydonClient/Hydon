package net.hydonclient.gui.main.element;

import net.hydonclient.ttf.HydonFonts;
import net.hydonclient.ttf.MinecraftFontRenderer;

public abstract class SettingsElement {

    protected final MinecraftFontRenderer regularFont = HydonFonts.PRODUCT_SANS_REGULAR;
    protected final MinecraftFontRenderer boldFont = HydonFonts.PRODUCT_SANS_BOLD;

    protected int width, height, x, y;

    public abstract void draw(int x, int y);

    public abstract void mouseClicked(int button, int mouseX, int mouseY);

    public void mouseDragged(int mouseX, int mouseY) {

    }

    public void mouseReleased(int mouseX, int mouseY) {

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

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}

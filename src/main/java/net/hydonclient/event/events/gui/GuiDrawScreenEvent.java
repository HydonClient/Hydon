package net.hydonclient.event.events.gui;

import net.hydonclient.event.Event;
import net.minecraft.client.gui.GuiScreen;

public class GuiDrawScreenEvent extends Event {

    private GuiScreen screen;

    private int mouseX;
    private int mouseY;
    private float partialTicks;

    /**
     * Fires when a Gui is drawn
     *
     * @param screen       the players screen
     * @param mouseX       the players current mouse pos x
     * @param mouseY       the players current mouse pos y
     * @param partialTicks the world tick
     */
    public GuiDrawScreenEvent(GuiScreen screen, int mouseX, int mouseY, float partialTicks) {
        this.screen = screen;

        this.mouseX = mouseX;
        this.mouseY = mouseY;
        this.partialTicks = partialTicks;
    }

    public GuiScreen getScreen() {
        return screen;
    }

    public int getMouseX() {
        return mouseX;
    }

    public int getMouseY() {
        return mouseY;
    }

    public float getPartialTicks() {
        return partialTicks;
    }
}

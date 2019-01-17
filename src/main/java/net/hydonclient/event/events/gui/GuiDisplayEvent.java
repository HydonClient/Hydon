package net.hydonclient.event.events.gui;

import net.hydonclient.event.Event;
import net.minecraft.client.gui.GuiScreen;

public class GuiDisplayEvent extends Event {

    private GuiScreen guiScreen;

    /**
     * Fires when a GUI has been opened
     * @param guiScreen the Gui class
     */
    public GuiDisplayEvent(GuiScreen guiScreen) {
        this.guiScreen = guiScreen;
    }

    public GuiScreen getGuiScreen() {
        return guiScreen;
    }
}

package net.hydonclient.event.events.gui;

import net.hydonclient.event.Event;
import net.minecraft.client.gui.GuiScreen;

public class GuiDisplayEvent extends Event {

    private GuiScreen guiScreen;

    public GuiDisplayEvent(GuiScreen guiScreen) {
        this.guiScreen = guiScreen;
    }

    public GuiScreen getGuiScreen() {
        return guiScreen;
    }
}

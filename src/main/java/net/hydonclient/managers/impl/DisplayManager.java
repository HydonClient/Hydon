package net.hydonclient.managers.impl;

import net.hydonclient.event.EventListener;
import net.hydonclient.event.events.game.UpdateEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

public class DisplayManager {

    private GuiScreen screen;

    public void displayScreenNextTick(GuiScreen screen) {
        this.screen = screen;
    }

    @EventListener
    public void tick(UpdateEvent event) {
        if (screen != null) {
            Minecraft.getMinecraft().displayGuiScreen(screen);
            screen = null;
        }
    }
}

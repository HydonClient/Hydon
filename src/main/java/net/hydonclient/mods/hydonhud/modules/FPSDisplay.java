package net.hydonclient.mods.hydonhud.modules;

import net.hydonclient.event.EventListener;
import net.hydonclient.event.events.render.RenderGameOverlayEvent;
import net.hydonclient.mods.hydonhud.HydonHUD;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

public class FPSDisplay extends Gui {

    private HydonHUD core;
    private String fps;

    public FPSDisplay(HydonHUD core) {
        this.core = core;
    }

    @EventListener
    public void display(RenderGameOverlayEvent event) {
        if (core.getConfig().SURROUNDING_CHARS == 0) {
            fps = ("fps: " + Minecraft.getDebugFPS());
        } else {
            fps = ("(fps: " + Minecraft.getDebugFPS() + ")");
        }

        if (core.getConfig().SHADOW) {
            core.drawStringWithShadow(fps);
        } else {
            core.drawString(fps);
        }
    }

}

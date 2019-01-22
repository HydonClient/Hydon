package net.hydonclient.mods.hydonhud.modules;

import net.hydonclient.event.EventListener;
import net.hydonclient.event.events.render.RenderGameOverlayEvent;
import net.hydonclient.mods.hydonhud.HydonHUD;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiChat;

import java.awt.*;

public class FPSDisplay extends Gui {

    private HydonHUD core;
    private String fps;

    public FPSDisplay(HydonHUD core) {
        this.core = core;
    }

    @EventListener
    public void display(RenderGameOverlayEvent event) {
        if (!core.getConfig().SHOW_FPS_IN_CHAT && Minecraft.getMinecraft().currentScreen instanceof GuiChat) {
            return;
        }

        if (core.getConfig().FPS && !core.getMinecraft().gameSettings.showDebugInfo) {
            if (!core.getConfig().FPS_PARENTHESES) {
                fps = ("fps: " + Minecraft.getDebugFPS());
            } else {
                fps = ("(fps: " + Minecraft.getDebugFPS() + ")");
            }

            if (core.getConfig().FPS_SHADOW) {
                core.drawStringWithShadow(fps, core.getConfig().fpsX, core.getConfig().fpsY,
                        new Color(core.getConfig().FPS_RED, core.getConfig().FPS_GREEN, core.getConfig().FPS_BLUE).getRGB());
            } else {
                core.drawString(fps, core.getConfig().fpsX, core.getConfig().fpsY,
                        new Color(core.getConfig().FPS_RED, core.getConfig().FPS_GREEN, core.getConfig().FPS_BLUE).getRGB());
            }
        }
    }
}

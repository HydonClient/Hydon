package net.hydonclient.mods.hydonhud.modules;

import net.hydonclient.event.EventListener;
import net.hydonclient.event.events.render.RenderGameOverlayEvent;
import net.hydonclient.mods.hydonhud.HydonHUD;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiChat;

import java.awt.*;

public class SprintDisplay extends Gui {

    private HydonHUD core;
    private String status;

    public SprintDisplay(HydonHUD core) {
        this.core = core;
    }

    @EventListener
    public void display(RenderGameOverlayEvent event) {
        if (!core.getConfig().SHOW_STATUS_IN_CHAT && Minecraft.getMinecraft().currentScreen instanceof GuiChat) {
            return;
        }

        if (!Minecraft.getMinecraft().thePlayer.isSprinting()) {
            return;
        }

        if (core.getConfig().SPRINT && !core.getMinecraft().gameSettings.showDebugInfo) {
            if (!core.getConfig().SPRINT_PARENTHESES) {
                status = "sprinting";
            } else {
                status = "(sprinting)";
            }

            if (core.getConfig().SPRINT_SHADOW) {
                core.drawStringWithShadow(status, core.getConfig().statusX, core.getConfig().statusY,
                        new Color(core.getConfig().FPS_RED, core.getConfig().FPS_GREEN, core.getConfig().FPS_BLUE).getRGB());
            } else {
                core.drawString(status, core.getConfig().statusX, core.getConfig().statusY,
                        new Color(core.getConfig().FPS_RED, core.getConfig().FPS_GREEN, core.getConfig().FPS_BLUE).getRGB());
            }
        }
    }
}

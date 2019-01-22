package net.hydonclient.mods.hydonhud.modules;

import net.hydonclient.event.EventListener;
import net.hydonclient.event.events.render.RenderGameOverlayEvent;
import net.hydonclient.mods.hydonhud.HydonHUD;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.entity.player.EntityPlayer;

import java.awt.*;
import java.text.DecimalFormat;

public class CoordinateDisplay extends Gui {


    private HydonHUD core;

    public CoordinateDisplay(HydonHUD core) {
        this.core = core;
    }

    @EventListener
    public void display(RenderGameOverlayEvent event) {
        if (!core.getConfig().SHOW_COORDS_IN_CHAT && Minecraft.getMinecraft().currentScreen instanceof GuiChat) {
            return;
        }

        EntityPlayer player = Minecraft.getMinecraft().thePlayer;
        String coords;
        if (player != null) {
            StringBuilder expandedCoordinates = new StringBuilder("0");

            if (core.getConfig().PRECISION > 0) {
                expandedCoordinates.append(".");

                for (int i = 0; i < core.getConfig().PRECISION; i++) {
                    expandedCoordinates.append("0");
                }

                DecimalFormat format = new DecimalFormat(expandedCoordinates.toString());

                if (!core.getConfig().COORDS_PARENTHESES) {
                    coords = ("x: " + format.format(player.posX) +
                            ", y: " + format.format(player.posY) +
                            ", z: " + format.format(player.posZ));
                } else {
                    coords = ("(x: " + format.format(player.posX) +
                            ", y: " + format.format(player.posY) +
                            ", z: " + format.format(player.posZ) + ")");
                }

                if (core.getConfig().COORDINATES && !Minecraft.getMinecraft().gameSettings.showDebugInfo) {
                    if (core.getConfig().COORDS_SHADOW) {
                        core.drawStringWithShadow(coords, core.getConfig().coordsX, core.getConfig().coordsY,
                                new Color(core.getConfig().COORDS_RED, core.getConfig().COORDS_GREEN, core.getConfig().COORDS_BLUE).getRGB());
                    } else {
                        core.drawString(coords, core.getConfig().coordsX, core.getConfig().coordsY,
                                new Color(core.getConfig().COORDS_RED, core.getConfig().COORDS_GREEN, core.getConfig().COORDS_BLUE).getRGB());
                    }
                }
            }
        }
    }
}

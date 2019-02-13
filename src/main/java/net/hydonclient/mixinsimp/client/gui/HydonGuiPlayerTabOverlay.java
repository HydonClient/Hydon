package net.hydonclient.mixinsimp.client.gui;

import net.hydonclient.Hydon;
import net.hydonclient.mixins.client.gui.IMixinGui;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiPlayerTabOverlay;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.GlStateManager;

import java.awt.*;

public class HydonGuiPlayerTabOverlay {
    private GuiPlayerTabOverlay parent;

    public HydonGuiPlayerTabOverlay(GuiPlayerTabOverlay parent) {
        this.parent = parent;
    }

    public void drawPing(int textureX, int textureY, int yIn, NetworkPlayerInfo networkPlayerInfoIn, float zLevel, Minecraft mc) {
        if (Hydon.SETTINGS.numberPing) {
            int ping = networkPlayerInfoIn.getResponseTime();
            int x = textureY + textureX - (mc.fontRendererObj.getStringWidth(ping + "") >> 1) - 2;
            int y = yIn + (mc.fontRendererObj.FONT_HEIGHT >> 2);

            int color;

            if (ping > 500) {
                color = new Color(255, 50, 50).getRGB();
            } else if (ping > 300) {
                color = new Color(255, 100, 0).getRGB();
            } else if (ping > 200) {
                color = new Color(255, 200, 0).getRGB();
            } else if (ping > 70) {
                color = new Color(0, 200, 0).getRGB();
            } else if (ping >= 0) {
                color = new Color(25, 240, 25).getRGB();
            } else {
                color = new Color(255, 0, 0).getRGB();
            }

            if (ping >= 0 && ping < 10000) {
                GlStateManager.pushMatrix();
                GlStateManager.scale(0.5f, 0.5f, 0.5f);
                mc.fontRendererObj.drawString(String.valueOf(ping), (2 * x), (2 * y), color);
                GlStateManager.scale(2.0f, 2.0f, 2.0f);
                GlStateManager.popMatrix();
            }
        } else {
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            mc.getTextureManager().bindTexture(Gui.icons);
            int i = 0;
            int j = 0;

            if (networkPlayerInfoIn.getResponseTime() < 0) {
                j = 5;
            } else if (networkPlayerInfoIn.getResponseTime() < 150) {
                j = 0;
            } else if (networkPlayerInfoIn.getResponseTime() < 300) {
                j = 1;
            } else if (networkPlayerInfoIn.getResponseTime() < 600) {
                j = 2;
            } else if (networkPlayerInfoIn.getResponseTime() < 1000) {
                j = 3;
            } else {
                j = 4;
            }

            ((IMixinGui) parent).setZLevel(zLevel += 100.0F);
            parent.drawTexturedModalRect(textureY + textureX - 11, yIn, i * 10, 176 + j * 8, 10, 8);
            ((IMixinGui) parent).setZLevel(zLevel -= 100.0F);
        }
    }
}

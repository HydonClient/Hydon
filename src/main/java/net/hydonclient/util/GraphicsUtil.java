package net.hydonclient.util;

import java.awt.Color;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import org.lwjgl.opengl.GL11;

public class GraphicsUtil {

    private static final double TWICE_PI = Math.PI * 2;
    private static Tessellator tessellator = Tessellator.getInstance();
    private static WorldRenderer worldRenderer = tessellator.getWorldRenderer();

    private static void drawRegularPolygon(double x, double y, int radius, int color) {
        int sides = 50;

        GlStateManager.pushAttrib();
        GlStateManager.pushMatrix();

        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        Color colorObj = new Color(color);
        GlStateManager.color(colorObj.getRed() / 255f, colorObj.getGreen() / 255f, colorObj.getBlue() / 255f, colorObj.getAlpha() / 255f);

        worldRenderer.begin(GL11.GL_TRIANGLE_FAN, DefaultVertexFormats.POSITION);
        worldRenderer.pos(x, y, 0).endVertex();

        for (int i = 0; i <= sides; i++) {
            double angle = (TWICE_PI * i / sides) + Math.toRadians(180);
            worldRenderer.pos(x + Math.sin(angle) * radius, y + Math.cos(angle) * radius, 0)
                    .endVertex();
        }
        tessellator.draw();

        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_BLEND);

        GlStateManager.popMatrix();
        GlStateManager.popAttrib();
    }

}

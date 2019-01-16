package net.hydonclient;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import net.hydonclient.ttf.HydonFonts;
import net.hydonclient.ttf.MinecraftFontRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class SplashScreen {

    private static ResourceLocation backgroundLocation = new ResourceLocation(
        "textures/alt-bg-1.png");

    public static class Progress {

        private int current;
        private int max = 8;
        private String text = "Starting...";

        void inc(String text) {
            this.text = text;
            current++;
        }

        int getCurrent() {
            return current;
        }

        int getMax() {
            return max;
        }

        String getText() {
            return text;
        }
    }

    private static Progress progress = new Progress();

    public static void advanceProgress(String text) {
        progress.inc(text);
        render(Minecraft.getMinecraft().getTextureManager());
    }

    public static void render(TextureManager tm) {
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        int i = sr.getScaleFactor();
        Framebuffer framebuffer = new Framebuffer(sr.getScaledWidth() * i,
            sr.getScaledHeight() * i,
            true);

        framebuffer.bindFramebuffer(false);
        GlStateManager.matrixMode(5889);
        GlStateManager.loadIdentity();
        GlStateManager
            .ortho(0.0D, (double) sr.getScaledWidth(), (double) sr.getScaledHeight(), 0.0D, 1000.0D,
                3000.0D);
        GlStateManager.matrixMode(5888);
        GlStateManager.loadIdentity();
        GlStateManager.translate(0.0F, 0.0F, -2000.0F);
        GlStateManager.disableLighting();
        GlStateManager.disableFog();
        GlStateManager.disableDepth();
        GlStateManager.enableTexture2D();

        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        tm.bindTexture(backgroundLocation);
        Gui.drawModalRectWithCustomSizedTexture(0, 0, 0, 0, sr.getScaledWidth(),
            sr.getScaledHeight(), sr.getScaledWidth(), sr.getScaledHeight());

        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(false);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glDisable(GL11.GL_ALPHA_TEST);
//
//        tm.bindTexture(logoLocation);
//
//        double reduceBy = 5;
//
//        double w = Constants.LOGO_WIDTH / Math.max(1, reduceBy);
//        double h = Constants.LOGO_HEIGHT / Math.max(1, reduceBy);
//
        int spacing = 10;
//
//        Gui.drawModalRectWithCustomSizedTexture((int) ((sr.getScaledWidth() - w) / 2), (int) ((sr.getScaledHeight() / 2 + spacing) - h), 0, 0, (int) w, (int) h, (int) w,(int) h);

        try {
            MinecraftFontRenderer fontRenderer = new MinecraftFontRenderer(
                Font.createFont(Font.TRUETYPE_FONT,
                    Hydon.class
                        .getResourceAsStream("/assets/minecraft/fonts/Product-Sans-Bold.ttf"))
                    .deriveFont(40f), true, true);
            fontRenderer.drawCenteredStringWithShadow("HYDON", sr.getScaledWidth() / 2f,
                sr.getScaledHeight() / 3f - fontRenderer.getHeight(),
                0xffffff);

            HydonFonts.PRODUCT_SANS_REGULAR.drawCenteredStringWithShadow(progress.getText(), sr.getScaledWidth() / 2f, sr.getScaledHeight() / 2f - HydonFonts.PRODUCT_SANS_REGULAR.getHeight(), 0xffffff);

            int barHeight = 20;
            double newCurrentProgress = progress.getCurrent();
            double progressDouble = (newCurrentProgress / progress.getMax());
            progressDouble *= sr.getScaledWidth() / 2f;
            Gui.drawRect(sr.getScaledWidth() / 4, sr.getScaledHeight() / 2 + spacing,
                sr.getScaledWidth() / 4 * 3, sr.getScaledHeight() / 2 + spacing + barHeight,
                new Color(0, 0, 0, 100).getRGB());
            Gui.drawRect(sr.getScaledWidth() / 4, sr.getScaledHeight() / 2 + spacing,
                (int) (sr.getScaledWidth() / 4 + progressDouble),
                sr.getScaledHeight() / 2 + spacing + barHeight,
                new Color(201, 57, 53, 200).getRGB());

        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }

            framebuffer.unbindFramebuffer();
            framebuffer.framebufferRender(sr.getScaledWidth() * i, sr.getScaledHeight() * i);
            GlStateManager.enableAlpha();
            GlStateManager.alphaFunc(516, 0.1F);
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);

        Minecraft.getMinecraft().updateDisplay();
    }

}

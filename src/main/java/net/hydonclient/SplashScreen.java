package net.hydonclient;

import java.awt.Color;

import net.hydonclient.gui.enums.EnumBackground;
import net.hydonclient.ttf.HydonFonts;
import net.hydonclient.util.maps.Images;
import net.hydonclient.util.ResolutionUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class SplashScreen {

    private static ResourceLocation backgroundLocation = EnumBackground.BACKGROUND_1.getLocation();

    public static class Progress {

        private int current;
        private int max = 9;
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

    /**
     * Renders the SplashScreen
     *
     * @param tm the texture manager for rendering with
     */
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

        GlStateManager.enableAlpha();
        tm.bindTexture(Images.LOGO.getLocation());
        double logoScaleFactor = ResolutionUtil.getImageScaleFactor();
        int logoWidth = (int) (Images.LOGO.getWidth() * logoScaleFactor);
        int logoHeight = (int) (Images.LOGO.getHeight() * logoScaleFactor);
        int logoX = (ResolutionUtil.getCurrent().getScaledWidth() - logoWidth) / 2;
        int logoY = (sr.getScaledHeight() + logoHeight) / 4;

        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);

        GlStateManager.color(0.0f, 0.0f, 0.0f, 0.2f);
        Gui.drawModalRectWithCustomSizedTexture(logoX + 2, logoY + 2, 0, 0, logoWidth, logoHeight,
                logoWidth, logoHeight);

        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        Gui.drawModalRectWithCustomSizedTexture(logoX, logoY, 0, 0, logoWidth, logoHeight,
                logoWidth, logoHeight);

        int barHeight = 20;
        double newCurrentProgress = progress.getCurrent();
        double progressDouble = (newCurrentProgress / progress.getMax());
        progressDouble *= sr.getScaledWidth() / 2f;
        Gui.drawRect(sr.getScaledWidth() / 4, sr.getScaledHeight() - barHeight,
                sr.getScaledWidth() / 4 * 3, sr.getScaledHeight(),
                new Color(0, 0, 0, 100).getRGB());
        Gui.drawRect(sr.getScaledWidth() / 4, sr.getScaledHeight() - barHeight,
                (int) (sr.getScaledWidth() / 4 + progressDouble),
                sr.getScaledHeight(),
                new Color(201, 57, 53, 200).getRGB());

        HydonFonts.PRODUCT_SANS_REGULAR
                .drawCenteredStringWithShadow(progress.getText(), sr.getScaledWidth() / 2f,
                        sr.getScaledHeight()
                                - (barHeight + HydonFonts.PRODUCT_SANS_REGULAR.getHeight()) / 2f, 0xffffff);

        framebuffer.unbindFramebuffer();
        framebuffer.framebufferRender(sr.getScaledWidth() * i, sr.getScaledHeight() * i);
        GlStateManager.enableAlpha();
        GlStateManager.alphaFunc(516, 0.1F);
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);

        Minecraft.getMinecraft().updateDisplay();
    }

}

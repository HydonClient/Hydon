package net.hydonclient.util;

import java.awt.Color;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class GuiUtils {

    public static void drawBG() {
        drawBG(false);
    }

    public static void drawBG(boolean overrideWorldCheck) {
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());

        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(false);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glDisable(GL11.GL_ALPHA_TEST);

        if (!(Minecraft.getMinecraft().theWorld != null && Minecraft
            .getMinecraft().theWorld.playerEntities.contains(Minecraft.getMinecraft().thePlayer)) || overrideWorldCheck) {
            Minecraft.getMinecraft().getTextureManager()
                .bindTexture(new ResourceLocation("textures/alt-bg-1.png"));
            Gui.drawModalRectWithCustomSizedTexture(0, 0, 0, 0, sr.getScaledWidth(),
                sr.getScaledHeight(),
                sr.getScaledWidth(), sr.getScaledHeight());
        }
    }

    public static void drawIngameGuiGradient() {
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        Gui.drawRect(0, 0, sr.getScaledWidth(), sr.getScaledHeight(),
            new Color(0, 0, 0, 120).getRGB());
    }

    public static void applyShader(ResourceLocation path) {
        Method method = ReflectionUtils
            .getMethod(EntityRenderer.class, new String[]{"loadShader", "a"},
                new Class[]{ResourceLocation.class});
        assert method != null;
        method.setAccessible(true);
        try {
            method.invoke(Minecraft.getMinecraft().entityRenderer,
                path);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public static void unloadShader() {
        Minecraft.getMinecraft().entityRenderer.stopUseShader();
    }

    public static void overlayBackground(int startY, int endY, Minecraft mc) {
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());

        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(false);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glDisable(GL11.GL_ALPHA_TEST);

        mc.getTextureManager().bindTexture(new ResourceLocation("textures/alt-bg-1.png"));
        Gui.drawModalRectWithCustomSizedTexture(0, startY, 0, startY, sr.getScaledWidth(), endY, sr.getScaledWidth(), sr.getScaledHeight());
    }

}

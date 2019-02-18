package net.hydonclient.util;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.NumberFormat;

import net.hydonclient.Hydon;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Mouse;
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
                .getMinecraft().theWorld.playerEntities.contains(Minecraft.getMinecraft().thePlayer))
                || overrideWorldCheck) {
            Minecraft.getMinecraft().getTextureManager()
                    .bindTexture(Hydon.SETTINGS.getCurrentBackground().getLocation());
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

        mc.getTextureManager().bindTexture(Hydon.SETTINGS.getCurrentBackground().getLocation());
        Gui.drawModalRectWithCustomSizedTexture(0, startY, 0, startY, sr.getScaledWidth(), endY,
                sr.getScaledWidth(), sr.getScaledHeight());
    }

    public static Point getMouse() {
        ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
        int i = Mouse.getEventX() * scaledResolution.getScaledWidth() / Minecraft
                .getMinecraft().displayWidth;
        int j = scaledResolution.getScaledHeight()
                - Mouse.getEventY() * scaledResolution.getScaledHeight() / Minecraft
                .getMinecraft().displayHeight - 1;
        return new Point(i, j);
    }

    public static boolean isHovered(int x, int y, int width, int height) {
        Rectangle area = new Rectangle(x, y, width, height);
        Point point = GuiUtils.getMouse();
        return area.contains(point);
    }

    public static Color blendColors(Color color1, Color color2, double ratio) {
        float r = (float) ratio;
        float ir = (float) 1.0 - r;

        float[] rgb1 = new float[4];
        float[] rgb2 = new float[4];

        color1.getColorComponents(rgb1);
        color2.getColorComponents(rgb2);

        float red = rgb1[0] * r + rgb2[0] * ir;
        float green = rgb1[1] * r + rgb2[1] * ir;
        float blue = rgb1[2] * r + rgb2[2] * ir;
        float alpha = rgb1[3] * r + rgb2[3] * ir;

        if (red < 0) {
            red = 0;
        } else if (red > 255) {
            red = 255;
        }
        if (green < 0) {
            green = 0;
        } else if (green > 255) {
            green = 255;
        }
        if (blue < 0) {
            blue = 0;
        } else if (blue > 255) {
            blue = 255;
        }
        if (alpha < 0) {
            alpha = 0;
        } else if (alpha > 255) {
            alpha = 255;
        }

        Color color = null;
        try {
            color = new Color(red, green, blue, alpha);
        } catch (IllegalArgumentException exp) {
            NumberFormat nf = NumberFormat.getNumberInstance();
            System.out.println(nf.format(red) + "; " + nf.format(green) + "; " + nf.format(blue));
            exp.printStackTrace();
        }
        return color;
    }

    public static Color rainbowColor(int delay) {
        double rainbowState = Math.ceil((System.currentTimeMillis() + delay) / 20.0);
        rainbowState %= 360;
        return Color.getHSBColor((float) (rainbowState / 360.0f), 0.8f, 0.7f);
    }

}

package net.hydonclient.mixinsimp.util;

import net.hydonclient.integrations.asyncscreenshotsaver.AsyncScreenshotSaver;
import net.hydonclient.util.ChatColor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ScreenShotHelper;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

import java.io.File;
import java.nio.IntBuffer;

public class HydonScreenshotHelper {

    private ScreenShotHelper screenShotHelper;

    public HydonScreenshotHelper(ScreenShotHelper screenShotHelper) {
        this.screenShotHelper = screenShotHelper;
    }


    public static IChatComponent saveScreenshot(File gameDirectory, String screenshotName, int width, int height, Framebuffer buffer, IntBuffer pixelBuffer, int[] pixelValues) {
        final File screenshotDirectory = new File(Minecraft.getMinecraft().mcDataDir, "screenshots");
        screenshotDirectory.mkdir();
        if (OpenGlHelper.isFramebufferEnabled()) {
            width = buffer.framebufferTextureWidth;
            height = buffer.framebufferTextureHeight;
        }
        final int i = width * height;
        if (pixelBuffer == null || pixelBuffer.capacity() < i) {
            pixelBuffer = BufferUtils.createIntBuffer(i);
            pixelValues = new int[i];
        }
        GL11.glPixelStorei(3333, 1);
        GL11.glPixelStorei(3317, 1);
        pixelBuffer.clear();
        if (OpenGlHelper.isFramebufferEnabled()) {
            GlStateManager.bindTexture(buffer.framebufferTexture);
            GL11.glGetTexImage(3553, 0, 32993, 33639, pixelBuffer);
        } else {
            GL11.glReadPixels(0, 0, width, height, 32993, 33639, pixelBuffer);
        }
        pixelBuffer.get(pixelValues);
        new Thread(new AsyncScreenshotSaver(width, height, pixelValues, Minecraft.getMinecraft().getFramebuffer(), screenshotDirectory)).start();
        return new ChatComponentText(ChatColor.DARK_AQUA + "[" + ChatColor.AQUA + ChatColor.BOLD + "Hydon" + ChatColor.DARK_AQUA + "] " + ChatColor.GRAY + "Capturing screenshot.");
    }
}

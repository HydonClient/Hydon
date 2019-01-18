package net.hydonclient.integrations.asyncscreenshotsaver;

import net.hydonclient.Hydon;
import net.hydonclient.util.ChatColor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.event.ClickEvent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AsyncScreenshotSaver implements Runnable {

    private final int width;
    private final int height;
    private final int[] pixelValues;
    private final Framebuffer frameBuffer;
    private final File screenshotDir;

    public AsyncScreenshotSaver(final int width, final int height, final int[] pixelValues, final Framebuffer frameBuffer, final File screenshotDir) {
        this.width = width;
        this.height = height;
        this.pixelValues = pixelValues;
        this.frameBuffer = frameBuffer;
        this.screenshotDir = screenshotDir;
    }

    private static File getTimestampedPNGFileForDirectory(final File gameDirectory) {
        final String s = new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss").format(new Date());
        int i = 1;
        File screenshot;
        while (true) {
            screenshot = new File(gameDirectory, s + ((i == 1) ? "" : ("_" + i)) + ".png");
            if (!screenshot.exists()) {
                break;
            }
            ++i;
        }
        return screenshot;
    }

    private static void processPixelValues(final int[] values, final int displayWidth, final int displayHeight) {
        final int[] aint = new int[displayWidth];
        for (int i = displayHeight / 2, j = 0; j < i; ++j) {
            System.arraycopy(values, j * displayWidth, aint, 0, displayWidth);
            System.arraycopy(values, (displayHeight - 1 - j) * displayWidth, values, j * displayWidth, displayWidth);
            System.arraycopy(aint, 0, values, (displayHeight - 1 - j) * displayWidth, displayWidth);
        }
    }

    @Override
    public void run() {
        processPixelValues(this.pixelValues, this.width, this.height);
        BufferedImage bufferedimage;
        final File file2 = getTimestampedPNGFileForDirectory(this.screenshotDir);

        try {
            if (OpenGlHelper.isFramebufferEnabled()) {
                bufferedimage = new BufferedImage(this.frameBuffer.framebufferWidth, this.frameBuffer.framebufferHeight, 1);
                int k;
                for (int j = k = this.frameBuffer.framebufferTextureHeight - this.frameBuffer.framebufferHeight; k < this.frameBuffer.framebufferTextureHeight; ++k) {
                    for (int l = 0; l < this.frameBuffer.framebufferWidth; ++l) {
                        bufferedimage.setRGB(l, k - j, this.pixelValues[k * this.frameBuffer.framebufferTextureWidth + l]);
                    }
                }
            } else {
                bufferedimage = new BufferedImage(this.width, this.height, 1);
                bufferedimage.setRGB(0, 0, this.width, this.height, this.pixelValues, 0, this.width);
            }
            ImageIO.write(bufferedimage, "png", file2);
            IChatComponent ichatcomponent = new ChatComponentText(ChatColor.DARK_AQUA + "[" + ChatColor.AQUA + ChatColor.BOLD + "Hydon" + ChatColor.DARK_AQUA + "] "
                    + ChatColor.GRAY + "Screenshot saved to " + ChatColor.BOLD + file2.getName());
            ichatcomponent.getChatStyle().setChatClickEvent(new ClickEvent(ClickEvent.Action.OPEN_FILE, file2.getCanonicalPath()));
            Minecraft.getMinecraft().thePlayer.addChatMessage(ichatcomponent);
        } catch (Exception e) {
            Hydon.LOGGER.warn("Screenshot not saved", e);
        }
    }
}

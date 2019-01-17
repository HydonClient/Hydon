package net.hydonclient.mixins.util;

import net.hydonclient.mixinsimp.util.HydonScreenshotHelper;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ScreenShotHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.io.File;
import java.nio.IntBuffer;

@Mixin(ScreenShotHelper.class)
public class MixinScreenshotHelper {

    @Shadow
    private static IntBuffer pixelBuffer;

    @Shadow
    private static int[] pixelValues;

    /**
     * @author OrangeMarshall
     * @reason Screenshot anytime with zero delay
     */
    @Overwrite
    public static IChatComponent saveScreenshot(File gameDirectory, String screenshotName, int width, int height, Framebuffer buffer) {
        return HydonScreenshotHelper.saveScreenshot(gameDirectory, screenshotName, width, height, buffer, pixelBuffer, pixelValues);
    }
}

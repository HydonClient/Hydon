package net.hydonclient.mixinsimp;

import net.hydonclient.mixins.IMixinMinecraft;
import net.minecraft.client.Minecraft;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public class HydonMinecraft {

    private Minecraft minecraft;

    public HydonMinecraft(Minecraft minecraft) {
        this.minecraft = minecraft;
    }

    public void setInitialDisplayMode(boolean fullscreen, int displayWidth, int displayHeight, CallbackInfo ci) throws LWJGLException {
        Display.setFullscreen(false);

        if (fullscreen) {
            Display.setFullscreen(true);
            DisplayMode mode = Display.getDisplayMode();

            ((IMixinMinecraft) minecraft).setDisplayWidth(Math.max(1, mode.getWidth()));
            ((IMixinMinecraft) minecraft).setDisplayHeight(Math.max(1, mode.getHeight()));
        } else {
            Display.setDisplayMode(new DisplayMode(displayWidth, displayHeight));
        }

        Display.setResizable(false);
        Display.setResizable(true);

        ci.cancel();
    }

    public void toggleFullscreen(boolean fullscreen, int displayWidth, int displayHeight, CallbackInfo ci) throws LWJGLException {
        if (!fullscreen) {
            Display.setDisplayMode(new DisplayMode(displayWidth, displayHeight));
            Display.setResizable(false);
            Display.setResizable(true);
        } else {
            Display.setFullscreen(true);
        }
    }
}

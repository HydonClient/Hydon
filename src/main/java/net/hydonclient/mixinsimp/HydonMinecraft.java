package net.hydonclient.mixinsimp;

import net.hydonclient.Hydon;
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
            if (Hydon.SETTINGS.WINDOWED_FULLSCREEN) {
                System.setProperty("org.lwjgl.opengl.Window.undecorated", "true");
            } else {
                Display.setFullscreen(true);
                DisplayMode mode = Display.getDisplayMode();

                ((IMixinMinecraft) minecraft).setDisplayWidth(Math.max(1, mode.getWidth()));
                ((IMixinMinecraft) minecraft).setDisplayHeight(Math.max(1, mode.getHeight()));
            }
        } else {
            if (Hydon.SETTINGS.WINDOWED_FULLSCREEN) {
                System.setProperty("org.lwjgl.opengl.Window.undecorated", "false");
            } else {
                Display.setDisplayMode(new DisplayMode(displayWidth, displayHeight));
            }
        }

        Display.setResizable(false);
        Display.setResizable(true);

        ci.cancel();
    }

    public void toggleFullscreen(boolean fullscreen, int displayWidth, int displayHeight, CallbackInfo ci) throws LWJGLException {
        if (Hydon.SETTINGS.WINDOWED_FULLSCREEN) {
            if (fullscreen) {
                System.setProperty("org.lwjgl.opengl.Window.undecorated", "true");

                Display.setDisplayMode(Display.getDesktopDisplayMode());
                Display.setLocation(0, 0);

                Display.setFullscreen(false);
            } else {
                System.setProperty("org.lwjgl.opengl.Window.undecorated", "false");
                Display.setDisplayMode(new DisplayMode(displayWidth, displayHeight));
            }
        } else {
            Display.setFullscreen(fullscreen);
            System.setProperty("org.lwjgl.opengl.Window.undecorated", "false");
        }

        Display.setResizable(false);
        Display.setResizable(true);
    }
}

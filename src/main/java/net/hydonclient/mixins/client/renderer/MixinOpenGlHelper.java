package net.hydonclient.mixins.client.renderer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(OpenGlHelper.class)
public class MixinOpenGlHelper {

    @Shadow
    public static boolean framebufferSupported;

    /**
     * @author Koding
     * @reason Fast Render Issues
     */
    @Overwrite
    public static boolean isFramebufferEnabled() {
        return framebufferSupported && Minecraft.getMinecraft().gameSettings.fboEnable;
    }
}

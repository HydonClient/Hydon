package net.hydonclient.mods.blur;

import com.google.common.base.Throwables;
import java.lang.reflect.Field;
import java.util.List;

import net.hydonclient.Hydon;
import net.hydonclient.event.EventBus;
import net.hydonclient.event.EventListener;
import net.hydonclient.event.events.gui.GuiDisplayEvent;
import net.hydonclient.event.events.render.RenderTickEvent;
import net.hydonclient.managers.HydonManagers;
import net.hydonclient.mods.Mod;
import net.hydonclient.mods.Mod.Info;
import net.hydonclient.util.GuiUtils;
import net.hydonclient.util.ReflectionUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.shader.Shader;
import net.minecraft.client.shader.ShaderGroup;
import net.minecraft.client.shader.ShaderUniform;
import net.minecraft.util.ResourceLocation;

@Info(name = "Blur", version = "1.0", author = "tterrag1098")
public class BlurMod extends Mod {

    private long start;

    @Override
    public void load() {
        EventBus.register(this);
    }

    @EventListener
    public void onRenderTick(RenderTickEvent e) {
        if (Hydon.SETTINGS.blurEnabled) {
            ShaderGroup sg = Minecraft.getMinecraft().entityRenderer.getShaderGroup();
            try {
                if (!Minecraft.getMinecraft().entityRenderer.isShaderActive()) {
                    return;
                }
                Field field = ReflectionUtils
                        .getField(ShaderGroup.class, new String[]{"d", "listShaders"});
                assert field != null;
                field.setAccessible(true);
                List<Shader> shaderList = (List<Shader>) field.get(sg);
                for (Shader s : shaderList) {
                    ShaderUniform su = s.getShaderManager().getShaderUniform("Progress");
                    if (su != null) {
                        int fadeTime = 350;
                        su.set(Math.min((System.currentTimeMillis() - start) / (float) fadeTime, 1));
                    }
                }
            } catch (IllegalArgumentException | IllegalAccessException ex) {
                Throwables.propagate(ex);
            }
        }
    }

    @EventListener
    public void onGuiDisplayed(GuiDisplayEvent e) {
        if (e.getGuiScreen() == null || e.getGuiScreen() instanceof GuiChat) {
            GuiUtils.unloadShader();
        } else if (Minecraft.getMinecraft().theWorld != null && Minecraft
            .getMinecraft().theWorld.playerEntities.contains(Minecraft.getMinecraft().thePlayer) && !Minecraft.getMinecraft().entityRenderer.isShaderActive()) {
            start = System.currentTimeMillis();
            GuiUtils.applyShader(new ResourceLocation("minecraft", "shaders/post/fade_in_blur.json"));
        }
    }
}

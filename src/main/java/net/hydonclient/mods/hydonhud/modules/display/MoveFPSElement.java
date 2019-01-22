package net.hydonclient.mods.hydonhud.modules.display;

import net.hydonclient.gui.main.HydonMainGui;
import net.hydonclient.managers.HydonManagers;
import net.hydonclient.mods.hydonhud.HydonHUD;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

import java.awt.*;

public class MoveFPSElement extends GuiScreen {

    private HydonHUD core;

    private int lastPositionX;
    private int lastPositionY;

    public MoveFPSElement(HydonHUD core) {
        this.core = core;
    }

    @Override
    public void onGuiClosed() {
        HydonManagers.INSTANCE.getConfigManager().save();
        super.onGuiClosed();
        if (Minecraft.getMinecraft().theWorld == null) {
            HydonMainGui.INSTANCE.initGui();
        }
    }

    @Override
    public void initGui() {
        lastPositionX = core.getConfig().fpsX;
        lastPositionY = core.getConfig().fpsY;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();

        if (core.getConfig().FPS_SHADOW && !core.getConfig().FPS_PARENTHESES) {
            core.drawStringWithShadow("fps: " + Minecraft.getDebugFPS(), core.getConfig().fpsX, core.getConfig().fpsY,
                    new Color(core.getConfig().FPS_RED, core.getConfig().FPS_GREEN, core.getConfig().FPS_BLUE).getRGB());

        } else if (core.getConfig().FPS_PARENTHESES) {
            core.drawStringWithShadow("(fps: " + Minecraft.getDebugFPS() + ")", core.getConfig().fpsX, core.getConfig().fpsY,
                    new Color(core.getConfig().FPS_RED, core.getConfig().FPS_GREEN, core.getConfig().FPS_BLUE).getRGB());

        } else if (!core.getConfig().FPS_SHADOW && !core.getConfig().FPS_PARENTHESES) {
            core.drawString("fps: " + Minecraft.getDebugFPS(), core.getConfig().fpsX, core.getConfig().fpsY,
                    new Color(core.getConfig().FPS_RED, core.getConfig().FPS_GREEN, core.getConfig().FPS_BLUE).getRGB());

        } else {
            core.drawString("(fps: " + Minecraft.getDebugFPS() + ")", core.getConfig().fpsX, core.getConfig().fpsY,
                    new Color(core.getConfig().FPS_RED, core.getConfig().FPS_GREEN, core.getConfig().FPS_BLUE).getRGB());
        }
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {
        core.getConfig().fpsX = mouseX;
        core.getConfig().fpsY = mouseY;

        super.mouseClickMove(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        super.mouseReleased(mouseX, mouseY, state);
    }
}

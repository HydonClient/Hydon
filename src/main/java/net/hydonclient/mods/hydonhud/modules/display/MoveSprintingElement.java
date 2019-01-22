package net.hydonclient.mods.hydonhud.modules.display;

import net.hydonclient.gui.main.HydonMainGui;
import net.hydonclient.managers.HydonManagers;
import net.hydonclient.mods.hydonhud.HydonHUD;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

import java.awt.*;

public class MoveSprintingElement extends GuiScreen {

    private HydonHUD core;

    private int lastPositionX;
    private int lastPositionY;

    public MoveSprintingElement(HydonHUD core) {
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
        lastPositionX = core.getConfig().statusX;
        lastPositionY = core.getConfig().statusY;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();

        if (core.getConfig().SPRINT_SHADOW && !core.getConfig().SPRINT_PARENTHESES) {
            core.drawStringWithShadow("sprinting", core.getConfig().statusX, core.getConfig().statusY,
                    new Color(core.getConfig().STATUS_RED, core.getConfig().STATUS_GREEN, core.getConfig().STATUS_BLUE).
                            getRGB());

        } else if (core.getConfig().SPRINT_PARENTHESES) {
            core.drawStringWithShadow("(sprinting)", core.getConfig().statusX, core.getConfig().statusY,
                    new Color(core.getConfig().STATUS_RED, core.getConfig().STATUS_GREEN, core.getConfig().STATUS_BLUE).
                            getRGB());

        } else if (!core.getConfig().SPRINT_SHADOW && !core.getConfig().SPRINT_PARENTHESES) {
            core.drawString("sprinting", core.getConfig().statusX, core.getConfig().statusY,
                    new Color(core.getConfig().STATUS_RED, core.getConfig().STATUS_GREEN, core.getConfig().STATUS_BLUE).
                            getRGB());

        } else {
            core.drawString("(sprinting)", core.getConfig().statusX, core.getConfig().statusY,
                    new Color(core.getConfig().STATUS_RED, core.getConfig().STATUS_GREEN, core.getConfig().STATUS_BLUE).
                            getRGB());
        }
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {
        core.getConfig().statusX = mouseX;
        core.getConfig().statusY = mouseY;

        super.mouseClickMove(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        super.mouseReleased(mouseX, mouseY, state);
    }
}

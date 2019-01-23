package net.hydonclient.mods.hydonhud.modules.display;

import net.hydonclient.gui.main.HydonMainGui;
import net.hydonclient.managers.HydonManagers;
import net.hydonclient.mods.hydonhud.HydonHUD;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

public class MovePotionStatusElement extends GuiScreen {

    private HydonHUD core;

    private int lastPositionX;
    private int lastPositionY;

    public MovePotionStatusElement(HydonHUD core) {
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
        lastPositionX = core.getConfig().potionStatusX;
        lastPositionY = core.getConfig().potionStatusY;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();

        if (core.getConfig().POTIONSTATUS_SHADOW && !core.getConfig().POTIONSTATUS_PARENTHESES) {
            core.drawStringWithShadow("Effect" + core.getConfig().getSeparator().getSeparatorOption() + "Duration",
                    core.getConfig().potionStatusX, core.getConfig().potionStatusY,
                    16777215);

        } else if (core.getConfig().POTIONSTATUS_PARENTHESES) {
            core.drawStringWithShadow("(Effect" + core.getConfig().getSeparator().getSeparatorOption() + "Duration)",
                    core.getConfig().potionStatusX, core.getConfig().potionStatusY,
                    16777215);

        } else if (!core.getConfig().POTIONSTATUS_SHADOW && !core.getConfig().POTIONSTATUS_PARENTHESES) {
            core.drawString("Effect" + core.getConfig().getSeparator().getSeparatorOption() + "Duration",
                    core.getConfig().potionStatusX, core.getConfig().potionStatusY,
                    16777215);

        } else {
            core.drawString("(Effect" + core.getConfig().getSeparator().getSeparatorOption() + "Duration)",
                    core.getConfig().potionStatusX, core.getConfig().potionStatusY,
                    16777215);
        }
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {
        core.getConfig().potionStatusX = mouseX;
        core.getConfig().potionStatusY = mouseY;

        super.mouseClickMove(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        super.mouseReleased(mouseX, mouseY, state);
    }
}

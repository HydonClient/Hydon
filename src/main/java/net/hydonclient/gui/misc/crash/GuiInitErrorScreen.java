package net.hydonclient.gui.misc.crash;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.crash.CrashReport;

public class GuiInitErrorScreen extends GuiScreen {

    private CrashReport report;

    public GuiInitErrorScreen(CrashReport report) {
        this.report = report;
    }

    @Override
    public void initGui() {
        mc.setIngameNotInFocus();
        buttonList.clear();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        drawCenteredString(fontRendererObj, "Minecraft failed to start", width / 2, height / 4 - 40, 0xFFFFFF);

        int textColor = 0xD0D0D0;
        int x = width / 2 - 155;
        int y = height / 4;

        drawString(fontRendererObj, "An error during startup prevented Minecraft from starting", x, y, textColor);
        drawCenteredString(fontRendererObj, report.getFile() != null ? "\u00A7n" + report.getFile().getName() : "[Error saving report, see log]", width / 2, y += 11, 0x00FF00);

        super.drawScreen(mouseX, mouseY, partialTicks);
    }
}

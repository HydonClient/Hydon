package net.hydonclient.gui.misc.crash;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiOptionButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.crash.CrashReport;

import java.io.IOException;

public class GuiCrashScreen extends GuiScreen {

    private CrashReport report;

    public GuiCrashScreen(CrashReport report) {
        this.report = report;
    }

    @Override
    public void initGui() {
        super.initGui();
        GuiOptionButton mainMenuButton = new GuiOptionButton(0, width / 2 - 70, height / 2 + 22, 150, 20, "Return to title screen");
        buttonList.add(mainMenuButton);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        super.actionPerformed(button);
        if (button.id == 0) {
            mc.displayGuiScreen(new GuiMainMenu());
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        drawCenteredString(fontRendererObj, "Minecraft crashed", width / 2, height / 2 - 10, 0xFFFFFF);

        int textColor = 0xD0D0D0;
        int x = width / 2 - 105;
        int y = height / 2;

        drawString(fontRendererObj, "Minecraft ran into a problem and crashed.", x, y, textColor);
        drawCenteredString(fontRendererObj, report.getFile() != null ? "\u00A7n" + report.getFile().getName() : "[Error saving report, see log]", width / 2, y + 11, 0x00FF00);

        super.drawScreen(mouseX, mouseY, partialTicks);
    }
}

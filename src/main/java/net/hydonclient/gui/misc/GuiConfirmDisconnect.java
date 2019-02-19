package net.hydonclient.gui.misc;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;

import java.io.IOException;

public class GuiConfirmDisconnect extends GuiScreen {

    private int i = -16;

    @Override
    public void initGui() {
        buttonList.clear();
        buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 96 + i, 98, 20, I18n.format("menu.disconnect")));
        buttonList.add(new GuiButton(1, this.width / 2 + 5, this.height / 4 + 96 + i, 98, 20, I18n.format("gui.cancel")));
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        fontRendererObj.drawStringWithShadow("Are you sure you want to disconnect?", (this.width >> 1) - 90, this.height / 4 + 72 + i, 16777215);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        switch (button.id) {
            case 0:
                boolean integratedServerRunning = Minecraft.getMinecraft().isIntegratedServerRunning();
                button.enabled = false;
                Minecraft.getMinecraft().theWorld.sendQuittingDisconnectingPacket();
                Minecraft.getMinecraft().loadWorld(null);

                if (integratedServerRunning) {
                    Minecraft.getMinecraft().displayGuiScreen(new GuiMainMenu());
                } else {
                    Minecraft.getMinecraft().displayGuiScreen(new GuiMultiplayer(new GuiMainMenu()));
                }
                break;
            case 1:
                Minecraft.getMinecraft().displayGuiScreen(null);
                break;
        }
    }
}

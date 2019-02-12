package net.hydonclient.mixinsimp.client.gui;

import net.hydonclient.Hydon;
import net.hydonclient.gui.main.HydonMainGui;
import net.hydonclient.gui.misc.GuiConfirmDisconnect;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiShareToLan;
import net.minecraft.client.gui.achievement.GuiAchievements;
import net.minecraft.client.gui.achievement.GuiStats;

import java.util.List;

public class HydonGuiIngameMenu extends GuiScreen {

    private GuiIngameMenu guiIngameMenu;

    public HydonGuiIngameMenu(GuiIngameMenu guiIngameMenu) {
        this.guiIngameMenu = guiIngameMenu;
    }

    public void initGui(List<GuiButton> buttonList) {
        int i = -16;
        buttonList.add(new GuiButton(8, guiIngameMenu.width / 2 - 100, guiIngameMenu.height / 4 + 72 + i, 98, 20, "Servers"));
        buttonList.add(new GuiButton(9, guiIngameMenu.width / 2 + 2, guiIngameMenu.height / 4 + 72 + i, 98, 20, "Hydon Settings"));
    }

    public void actionPerformed(GuiButton button) {
        switch (button.id) {
            case 0:
                mc.displayGuiScreen(new GuiOptions(this, mc.gameSettings));
                break;

            case 1:
                if (Hydon.SETTINGS.confirmDisconnect) {
                    Minecraft.getMinecraft().displayGuiScreen(new GuiConfirmDisconnect());
                    break;

                } else {
                    boolean integratedServerRunning = Minecraft.getMinecraft().isIntegratedServerRunning();
                    button.enabled = false;
                    Minecraft.getMinecraft().theWorld.sendQuittingDisconnectingPacket();
                    Minecraft.getMinecraft().loadWorld(null);

                    if (integratedServerRunning) {
                        Minecraft.getMinecraft().displayGuiScreen(new GuiMainMenu());
                    } else {
                        Minecraft.getMinecraft().displayGuiScreen(new GuiMultiplayer(new GuiMainMenu()));
                    }
                }
                break;

            case 2:
                break;
            case 3:
                break;

            case 4:
                mc.displayGuiScreen(null);
                mc.setIngameFocus();
                break;

            case 5:
                mc.displayGuiScreen(new GuiAchievements(this, mc.thePlayer.getStatFileWriter()));
                break;

            case 6:
                mc.displayGuiScreen(new GuiStats(this, mc.thePlayer.getStatFileWriter()));
                break;

            case 7:
                mc.displayGuiScreen(new GuiShareToLan(this));
                break;

            case 8:
                Minecraft.getMinecraft().displayGuiScreen(new GuiMultiplayer(Minecraft.getMinecraft().currentScreen));
                break;

            case 9:
                Minecraft.getMinecraft().displayGuiScreen(HydonMainGui.INSTANCE);
                break;
        }
    }
}

package net.hydonclient.mixinsimp.client.gui;

import net.hydonclient.gui.main.HydonMainGui;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiScreen;

import java.util.List;

public class HydonGuiIngameMenu extends GuiScreen {

    private GuiIngameMenu guiIngameMenu;

    public HydonGuiIngameMenu(GuiIngameMenu guiIngameMenu) {
        this.guiIngameMenu = guiIngameMenu;
    }

    public void initGui(List<GuiButton> buttonList) {
        int i = -16;
        buttonList.add(new GuiButton(8, guiIngameMenu.width / 2 - 100, guiIngameMenu.height / 4 + 72 + i, 98, 20,  "Servers"));
        buttonList.add(new GuiButton(9, guiIngameMenu.width / 2 + 2, guiIngameMenu.height / 4 + 72 + i, 98, 20,"Hydon Settings"));
    }

    public void actionPerformed(GuiButton button) {
        if (button.id == 8) {
            Minecraft.getMinecraft().displayGuiScreen(new GuiMultiplayer(Minecraft.getMinecraft().currentScreen));
        }
        if (button.id == 9) {
            Minecraft.getMinecraft().displayGuiScreen(HydonMainGui.INSTANCE);
        }
    }
}

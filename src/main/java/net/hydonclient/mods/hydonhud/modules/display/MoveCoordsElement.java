package net.hydonclient.mods.hydonhud.modules.display;

import net.hydonclient.gui.main.HydonMainGui;
import net.hydonclient.managers.HydonManagers;
import net.hydonclient.mods.hydonhud.HydonHUD;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;

import java.awt.*;
import java.text.DecimalFormat;

public class MoveCoordsElement extends GuiScreen {

    private HydonHUD core;

    private int lastPositionX;
    private int lastPositionY;

    public MoveCoordsElement(HydonHUD core) {
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
        lastPositionX = core.getConfig().coordsX;
        lastPositionY = core.getConfig().coordsY;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();

        EntityPlayer player = Minecraft.getMinecraft().thePlayer;
        String coords;
        if (player != null) {
            StringBuilder expandedCoordinates = new StringBuilder("0");

            if (core.getConfig().PRECISION > 0) {
                expandedCoordinates.append(".");

                for (int i = 0; i < core.getConfig().PRECISION; i++) {
                    expandedCoordinates.append("0");
                }

                DecimalFormat format = new DecimalFormat(expandedCoordinates.toString());

                if (!core.getConfig().COORDS_PARENTHESES) {
                    coords = ("x: " + format.format(player.posX) +
                            ", y: " + format.format(player.posY) +
                            ", z: " + format.format(player.posZ));
                } else {
                    coords = ("(x: " + format.format(player.posX) +
                            ", y: " + format.format(player.posY) +
                            ", z: " + format.format(player.posZ) + ")");
                }

                if (core.getConfig().COORDINATES && !Minecraft.getMinecraft().gameSettings.showDebugInfo) {
                    if (core.getConfig().COORDS_SHADOW) {
                        core.drawStringWithShadow(coords, core.getConfig().coordsX, core.getConfig().coordsY,
                                new Color(core.getConfig().COORDS_RED, core.getConfig().COORDS_GREEN, core.getConfig().COORDS_BLUE).getRGB());
                    } else {
                        core.drawString(coords, core.getConfig().coordsX, core.getConfig().coordsY,
                                new Color(core.getConfig().COORDS_RED, core.getConfig().COORDS_GREEN, core.getConfig().COORDS_BLUE).getRGB());
                    }
                }
            }
        }
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {
        core.getConfig().coordsX = mouseX;
        core.getConfig().coordsY = mouseY;

        super.mouseClickMove(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        super.mouseReleased(mouseX, mouseY, state);
    }
}

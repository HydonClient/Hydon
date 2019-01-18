package pw.cinque.keystrokesmod.gui;

import net.minecraft.client.gui.GuiScreen;
import pw.cinque.keystrokesmod.KeystrokesMod;
import pw.cinque.keystrokesmod.gui.key.KeyHolder;

/**
 * This {@code GuiScreen} allows the user to change the position of the {@link KeyHolder}.
 */
public class GuiKeystrokes extends GuiScreen {

    private final KeystrokesMod keystrokesMod;
    private int lastMouseX;
    private int lastMouseY;

    @java.beans.ConstructorProperties({"keystrokesMod"})
    public GuiKeystrokes(KeystrokesMod keystrokesMod) {
        this.keystrokesMod = keystrokesMod;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();

        keystrokesMod.getKeyHolder().draw(mouseX - lastMouseX, mouseY - lastMouseY, -1, -1);
        lastMouseX = mouseX;
        lastMouseY = mouseY;
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int button) {
        if (button == 0) {
            lastMouseX = mouseX;
            lastMouseY = mouseY;
            keystrokesMod.getKeyHolder().onMousePress(mouseX, mouseY);
        }
    }

    @Override
    public void onGuiClosed() {
        keystrokesMod.getKeyHolder().onMouseRelease();
    }

}

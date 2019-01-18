package pw.cinque.keystrokesmod;

import net.hydonclient.event.EventListener;
import net.hydonclient.event.events.render.RenderGameOverlayEvent;
import net.hydonclient.event.events.render.RenderTickEvent;
import net.minecraft.client.Minecraft;
import pw.cinque.keystrokesmod.gui.GuiKeystrokes;

/**
 * This class draws the keystrokes on the screen when the {@link GuiKeystrokes} screen isn't open.
 */
public class KeystrokesRenderer {

    private final KeystrokesMod keystrokesMod;

    @java.beans.ConstructorProperties({"keystrokesMod"})
    public KeystrokesRenderer(KeystrokesMod keystrokesMod) {
        this.keystrokesMod = keystrokesMod;
    }

    @EventListener
    public void onRenderTick(RenderTickEvent event) {
        if (Minecraft.getMinecraft().inGameHasFocus && !Minecraft.getMinecraft().gameSettings.showDebugInfo) {
            keystrokesMod.getKeyHolder().draw(0, 0, -1, -1);
        }
    }

}

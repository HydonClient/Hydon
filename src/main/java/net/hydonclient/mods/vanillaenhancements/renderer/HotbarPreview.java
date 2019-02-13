package net.hydonclient.mods.vanillaenhancements.renderer;

import net.hydonclient.Hydon;
import net.hydonclient.event.EventListener;
import net.hydonclient.event.events.render.RenderGameOverlayEvent;
import net.hydonclient.mods.vanillaenhancements.VanillaEnhancements;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;

public class HotbarPreview {

    private VanillaEnhancements core;

    public HotbarPreview(VanillaEnhancements core) {
        this.core = core;
    }

    @EventListener
    public void renderHotbarNumbers(RenderGameOverlayEvent event) {
        if (Hydon.SETTINGS.hotbarNumbers) {
            int x = core.getResolution().getScaledWidth() / 2 - 87;
            int y = core.getResolution().getScaledHeight() - 18;
            int[] hotbarKeys = getHotbarKeys();

            for (int slot = 0; slot < 9; ++slot) {
                core.getMinecraft().fontRendererObj.drawString(getKeyString(hotbarKeys[slot]), x + slot * 20, y, 16777215, Hydon.SETTINGS.hotbarNumberShadow);
            }
        }
    }

    private String getKeyString(int key) {
        return (key < 0) ? ("M" + (key + 101)) : ((key < 256) ? Keyboard.getKeyName(key) : String.format("%c", (char) (key - 256)).toUpperCase());
    }

    private int[] getHotbarKeys() {
        int[] result = new int[9];
        KeyBinding[] hotbarBinds = core.getMinecraft().gameSettings.keyBindsHotbar;
        for (int i = 0; i < Math.min(result.length, hotbarBinds.length); ++i) {
            result[i] = hotbarBinds[i].getKeyCode();
        }

        return result;
    }
}

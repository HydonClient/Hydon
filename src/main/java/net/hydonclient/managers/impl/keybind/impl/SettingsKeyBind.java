package net.hydonclient.managers.impl.keybind.impl;

import net.hydonclient.gui.main.HydonMainGui;
import net.hydonclient.managers.impl.keybind.HydonKeyBind;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;

public class SettingsKeyBind extends HydonKeyBind {

    /**
     * The constructor for the Settings keybind
     * <p>
     * name is set to "Open Settings"
     * key is set to "`"
     */
    public SettingsKeyBind() {
        super("Open Settings", Keyboard.KEY_GRAVE);
    }

    /**
     * When the player's screen isn't on any GUI, it will open the HydonMainGui
     */
    @Override
    public void onPress() {
        if (Minecraft.getMinecraft().currentScreen == null) {
            Minecraft.getMinecraft().displayGuiScreen(HydonMainGui.INSTANCE);
        }
    }
}

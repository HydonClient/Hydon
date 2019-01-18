package net.hydonclient.managers.impl.keybind.impl;

import net.hydonclient.gui.main.HydonMainGui;
import net.hydonclient.managers.impl.keybind.HydonKeyBind;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;

public class SettingsKeyBind extends HydonKeyBind {

    public SettingsKeyBind() {
        super("Open Settings", Keyboard.KEY_GRAVE);
    }

    @Override
    public void onPress() {
        if (Minecraft.getMinecraft().currentScreen == null) {
            Minecraft.getMinecraft().displayGuiScreen(HydonMainGui.INSTANCE);
        }
    }
}

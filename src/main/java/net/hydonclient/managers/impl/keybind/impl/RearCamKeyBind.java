package net.hydonclient.managers.impl.keybind.impl;

import net.hydonclient.managers.impl.keybind.HydonKeyBind;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;

public class RearCamKeyBind extends HydonKeyBind {

    public RearCamKeyBind() {
        super("Rear Cam", Keyboard.KEY_R);
    }

    @Override
    public void onPress() {
        Minecraft.getMinecraft().gameSettings.thirdPersonView = 2;
    }

    @Override
    public void onRelease() {
        Minecraft.getMinecraft().gameSettings.thirdPersonView = 0;
    }
}

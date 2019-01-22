package net.hydonclient.managers.impl.keybind.impl;

import net.hydonclient.managers.impl.keybind.HydonKeyBind;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;

public class RearCamKeyBind extends HydonKeyBind {

    /**
     * The constructor for the RearCam keybind
     * <p>
     * name is set to "Rear Cam"
     * key is set to "R"
     */
    public RearCamKeyBind() {
        super("Rear Cam", Keyboard.KEY_R);
    }

    /**
     * When the player holds the key down, it will set their perspective
     * to be facing their face
     */
    @Override
    public void onPress() {
        Minecraft.getMinecraft().gameSettings.thirdPersonView = 2;
    }

    /**
     * When the player lets go of the key, it will set their perspective
     * to be normal
     */
    @Override
    public void onRelease() {
        Minecraft.getMinecraft().gameSettings.thirdPersonView = 0;
    }
}

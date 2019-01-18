package net.hydonclient.managers.impl.keybind.impl;

import net.hydonclient.event.EventBus;
import net.hydonclient.managers.impl.keybind.HydonKeyBind;
import net.hydonclient.util.ChatColor;
import net.hydonclient.util.ChatUtils;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;

public class PerspectiveKeyBind extends HydonKeyBind {

    public static boolean toggled = false;

    public static float modifiedYaw;
    public static float modifiedPitch;

    // TODO: Add a toggle between if you want the keybind to activate by being held, or by being pressed once

    public PerspectiveKeyBind() {
        super("Perspective", Keyboard.KEY_P);
        EventBus.register(this);
    }

    public static void enable() {
        if (!toggled) {
            ChatUtils.addChatMessage("360 Degree PerspectiveKeyBind " + ChatColor.GREEN + "enabled" + ChatColor.GRAY + ".");
            Minecraft.getMinecraft().gameSettings.thirdPersonView = 1;
            toggled = true;

            modifiedYaw = Minecraft.getMinecraft().thePlayer.cameraYaw;
            modifiedPitch = Minecraft.getMinecraft().thePlayer.cameraPitch;
        } else {
            disable();
        }
    }

    public static void disable() {
        if (toggled) {
            ChatUtils.addChatMessage("360 Degree PerspectiveKeyBind " + ChatColor.RED + "disabled" + ChatColor.GRAY + ".");
            Minecraft.getMinecraft().gameSettings.thirdPersonView = 0;
            toggled = false;
        } else {
            enable();
        }
    }

    @Override
    public void onPress() {
        if (!toggled) {
            enable();
        }
    }

    @Override
    public void onRelease() {
        disable();
    }
}

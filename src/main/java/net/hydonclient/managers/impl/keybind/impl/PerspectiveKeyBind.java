package net.hydonclient.managers.impl.keybind.impl;

import net.hydonclient.Hydon;
import net.hydonclient.event.EventBus;
import net.hydonclient.managers.impl.keybind.HydonKeyBind;
import net.hydonclient.util.maps.ChatColor;
import net.hydonclient.util.ChatUtils;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;

public class PerspectiveKeyBind extends HydonKeyBind {

    /**
     * The state of which the player has it enabled or not
     */
    public static boolean toggled = false;

    /**
     * The vertical position of the head
     */
    public static float modifiedYaw;

    /**
     * The horizontal position of the head
     */
    public static float modifiedPitch;

    /**
     * The constructor for the Perspective keybind
     * <p>
     * name is set to "360 Degree Perspective"
     * key is set to "P"
     */
    public PerspectiveKeyBind() {
        super("360 Degree Perspective", Keyboard.KEY_P);
        EventBus.register(this);
    }

    /**
     * When the player presses the keybind, this is called
     * It will print a message in their chat saying it has been enabled
     * and set their perspective to be behind them
     */
    private static void enable() {
        if (!toggled) {
            ChatUtils.addChatMessage("360 Degree Perspective " + ChatColor.GREEN + "enabled" + ChatColor.GRAY + ".");
            Minecraft.getMinecraft().gameSettings.thirdPersonView = 1;
            toggled = true;

            modifiedYaw = Minecraft.getMinecraft().thePlayer.cameraYaw;
            modifiedPitch = Minecraft.getMinecraft().thePlayer.cameraPitch;
        } else {
            disable();
        }
    }

    /**
     * When the player presses the keybind or releases it and enable();
     * is currently called, it will print a message in their chat saying it has been disabled
     * and set their perspective back to normal
     */
    public static void disable() {
        if (toggled) {
            ChatUtils.addChatMessage("360 Degree Perspective " + ChatColor.RED + "disabled" + ChatColor.GRAY + ".");
            Minecraft.getMinecraft().gameSettings.thirdPersonView = 0;
            toggled = false;
        } else {
            enable();
        }
    }

    /**
     * If the player doesn't have the key set to be held, it will enable / disable
     * the current state
     */
    @Override
    public void onPress() {
        if (!toggled) {
            enable();
        } else if (!Hydon.SETTINGS.HELD_PERSPECTIVE) {
            disable();
        }
    }

    /**
     * If the player has the key set to be held, it will disable
     * the current state
     */
    @Override
    public void onRelease() {
        if (Hydon.SETTINGS.HELD_PERSPECTIVE) {
            disable();
        }
    }
}

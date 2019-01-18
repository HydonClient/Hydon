package net.hydonclient.managers.impl.keybind.impl;

import net.hydonclient.Hydon;
import net.hydonclient.event.EventBus;
import net.hydonclient.event.EventListener;
import net.hydonclient.event.events.game.UpdateEvent;
import net.hydonclient.managers.impl.keybind.HydonKeyBind;
import net.hydonclient.mixins.client.settings.MixinKeyBinding;
import net.hydonclient.util.ChatUtils;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;

public class ToggleSprintKeyBind extends HydonKeyBind {

    private static boolean sprinting = false;

    public ToggleSprintKeyBind() {
        super("Toggle Sprint", Keyboard.KEY_V);
        EventBus.register(this);
    }

    @Override
    public void onPress() {
        if (Hydon.SETTINGS.togglesprintEnabled) {
            if (sprinting) {
                ((MixinKeyBinding) Minecraft.getMinecraft().gameSettings.keyBindSprint).setPressed(false);
                ChatUtils.addChatMessage("§7ToggleSprint §cdisabled§7.");
            } else {
                ((MixinKeyBinding) Minecraft.getMinecraft().gameSettings.keyBindSprint).setPressed(true);
                ChatUtils.addChatMessage("§7ToggleSprint §aenabled§7.");
            }
            sprinting = !sprinting;
        }
    }

    @EventListener
    public void onTick(UpdateEvent event) {
        if (Hydon.SETTINGS.togglesprintEnabled) {
            if (sprinting) {
                ((MixinKeyBinding) Minecraft.getMinecraft().gameSettings.keyBindSprint).setPressed(true);
            }

            /* If the player disables the Togglesprint keybind while it's activated, it'll stop their sprinting */
            if (!sprinting && Hydon.SETTINGS.stopSprintingAfterReleased) {
                Minecraft.getMinecraft().thePlayer.setSprinting(false);
            }
        }
    }
}

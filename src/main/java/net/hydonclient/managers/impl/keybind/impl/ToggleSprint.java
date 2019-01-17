package net.hydonclient.managers.impl.keybind.impl;

import net.hydonclient.event.EventBus;
import net.hydonclient.event.EventListener;
import net.hydonclient.event.events.game.UpdateEvent;
import net.hydonclient.managers.impl.keybind.HydonKeyBind;
import net.hydonclient.mixins.client.settings.MixinKeyBinding;
import net.hydonclient.util.ChatUtils;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;

public class ToggleSprint extends HydonKeyBind {

    private static boolean toggleSprint = false;

    public ToggleSprint() {
        super("ToggleSprint", Keyboard.KEY_R);
        EventBus.register(this);
    }

    @Override
    public void onPress() {
        if (toggleSprint) {
            ((MixinKeyBinding) Minecraft.getMinecraft().gameSettings.keyBindSprint).setPressed(false);
            ChatUtils.addChatMessage("§7ToggleSprint §cdisabled§7.");
        } else {
            ((MixinKeyBinding) Minecraft.getMinecraft().gameSettings.keyBindSprint).setPressed(true);
            ChatUtils.addChatMessage("§7ToggleSprint §aenabled§7.");
        }
        toggleSprint = !toggleSprint;
    }

    @EventListener
    public void onTick(UpdateEvent event) {
        if (toggleSprint) {
            ((MixinKeyBinding) Minecraft.getMinecraft().gameSettings.keyBindSprint).setPressed(true);
        }

        /* If the player disables the Togglesprint keybind while it's activated, it'll stop their sprint TODO: (make this an option to toggle on and off) */
        if (!toggleSprint) {
            Minecraft.getMinecraft().thePlayer.setSprinting(false);
        }
    }
}

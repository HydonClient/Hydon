package net.hydonclient.managers.impl.keybind.impl;

import net.hydonclient.event.EventBus;
import net.hydonclient.event.EventListener;
import net.hydonclient.event.events.game.UpdateEvent;
import net.hydonclient.managers.HydonManagers;
import net.hydonclient.managers.impl.config.SaveVal;
import net.hydonclient.managers.impl.keybind.HydonKeyBind;
import net.hydonclient.mixins.client.settings.MixinKeyBinding;
import net.hydonclient.util.ChatUtils;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;

public class ToggleSprintKeyBind extends HydonKeyBind {

    private static boolean TOGGLESPRINT = false;

    @SaveVal
    public static boolean ENABLE_TOGGLESPRINT = true;

    @SaveVal
    public static boolean STOP_SPRINT_AFTER_RELEASE = false;

    public ToggleSprintKeyBind() {
        super("Toggle Sprint", Keyboard.KEY_V);
        EventBus.register(this);
        HydonManagers.INSTANCE.getConfigManager().register(this);
    }

    @Override
    public void onPress() {
        if (ENABLE_TOGGLESPRINT) {
            if (TOGGLESPRINT) {
                ((MixinKeyBinding) Minecraft.getMinecraft().gameSettings.keyBindSprint).setPressed(false);
                ChatUtils.addChatMessage("§7ToggleSprint §cdisabled§7.");
            } else {
                ((MixinKeyBinding) Minecraft.getMinecraft().gameSettings.keyBindSprint).setPressed(true);
                ChatUtils.addChatMessage("§7ToggleSprint §aenabled§7.");
            }
            TOGGLESPRINT = !TOGGLESPRINT;
        }
    }

    @EventListener
    public void onTick(UpdateEvent event) {
        if (ENABLE_TOGGLESPRINT) {
            if (TOGGLESPRINT) {
                ((MixinKeyBinding) Minecraft.getMinecraft().gameSettings.keyBindSprint).setPressed(true);
            }

            /* If the player disables the Togglesprint keybind while it's activated, it'll stop their sprint TODO: (make this an option to toggle on and off) */
            if (!TOGGLESPRINT && STOP_SPRINT_AFTER_RELEASE) {
                Minecraft.getMinecraft().thePlayer.setSprinting(false);
            }
        }
    }
}

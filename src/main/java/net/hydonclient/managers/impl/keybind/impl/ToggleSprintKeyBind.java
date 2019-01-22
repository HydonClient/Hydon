package net.hydonclient.managers.impl.keybind.impl;

import net.hydonclient.Hydon;
import net.hydonclient.event.EventBus;
import net.hydonclient.event.EventListener;
import net.hydonclient.event.events.game.UpdateEvent;
import net.hydonclient.event.events.game.WorldChangedEvent;
import net.hydonclient.managers.impl.keybind.HydonKeyBind;
import net.hydonclient.mixins.client.settings.MixinKeyBinding;
import net.hydonclient.util.ChatUtils;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;

public class ToggleSprintKeyBind extends HydonKeyBind {

    /**
     * The state of which the player is sprinting
     */
    private static boolean sprinting = false;

    /**
     * The constructor for the Togglesprint keybind
     * <p>
     * name is set to "Toggle Sprint"
     * key is set to "V"
     */
    public ToggleSprintKeyBind() {
        super("Toggle Sprint", Keyboard.KEY_V);
        EventBus.register(this);
    }

    /**
     * When the player presses the key, it will toggle the state of togglesprint
     * and print a message saying it was either enabled or disabled.
     */
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

    /**
     * When the player has Hydon.SETTINGS.stopSprintingAfterReleased enabled and they press the key
     * It will stop them from sprinting the next tick
     *
     * @param event tick event
     */
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

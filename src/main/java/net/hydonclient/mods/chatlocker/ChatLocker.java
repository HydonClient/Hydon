package net.hydonclient.mods.chatlocker;

import net.hydonclient.event.EventBus;
import net.hydonclient.event.EventListener;
import net.hydonclient.event.events.game.ChatEvent;
import net.hydonclient.event.events.game.ChatMessageSendEvent;
import net.hydonclient.managers.HydonManagers;
import net.hydonclient.managers.impl.config.SaveVal;
import net.hydonclient.mods.Mod;

import net.hydonclient.mods.chatlocker.command.ChatCommand;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.network.play.client.C01PacketChatMessage;
import net.minecraft.util.ChatComponentText;

@Mod.Info(name = "ChatLocker", author = "asbyth", version = "1.0")
public class ChatLocker extends Mod {

    @SaveVal
    public static String PREFIX = "";

    @SaveVal
    public static boolean CHATLOCKER = false;

    @Override
    public void load() {
        HydonManagers.INSTANCE.getCommandManager().register(new ChatCommand());
        HydonManagers.INSTANCE.getConfigManager().register(this);
        EventBus.register(this);
    }

    public static String getPrefix() {
        return PREFIX;
    }

    public static void setPrefix(String newPrefix) {
        ChatLocker.PREFIX = newPrefix;
    }

    @EventListener
    public void onChatEvent(ChatMessageSendEvent event) {
        if (CHATLOCKER) {
            String rawMessage = event.getMessage();
            String message;
            event.setCancelled(true);
            if (rawMessage.startsWith("/") && !PREFIX.startsWith("/")) {
                return;
            } else {
                message = getPrefix() + " " + rawMessage;
                Minecraft.getMinecraft().thePlayer.sendQueue.addToSendQueue(new C01PacketChatMessage(message));
            }
        }
    }
}

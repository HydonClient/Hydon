package net.hydonclient.integrations.compactchat;

import net.hydonclient.Hydon;
import net.hydonclient.event.EventListener;
import net.hydonclient.event.events.game.ChatEvent;
import net.hydonclient.util.maps.ChatColor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiNewChat;

/**
 * Complete credit to the Hyperium team for this (if they're the ones that made it, afaik they did)
 * - asbyth
 */
public class CompactChat {

    /**
     * Instance of CompactChat
     */
    private static CompactChat INSTANCE;

    /**
     * Previously logged message
     */
    private String lastMessage = "";

    /**
     * The line the message is on
     */
    private int line = 0;

    /**
     * The amount of times the message has been posted
     */
    private int amount = 0;

    /**
     * The getInstance method for CompactChat so it can be used in other classes
     * without static calls
     *
     * @return the instance
     */
    public static CompactChat getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CompactChat();
        }
        return INSTANCE;
    }

    /**
     * Delete a message and append a number value (1) when the previous message is a duplicate
     *
     * @param event the event being used
     */
    @EventListener
    public void onChat(ChatEvent event) {
        if (Hydon.SETTINGS.COMPACT_CHAT) {
            if (!event.isCancelled()) {
                GuiNewChat guiNewChat = Minecraft.getMinecraft().ingameGUI.getChatGUI();

                if (lastMessage.equals(event.getChat().getUnformattedText())) {
                    guiNewChat.deleteChatLine(line);

                    amount++;
                    lastMessage = event.getChat().getUnformattedText();

                    event.getChat().appendText(ChatColor.GRAY + " (" + amount + ")");
                } else {
                    amount = 1;
                    lastMessage = event.getChat().getUnformattedText();
                }
                line++;
                if (!event.isCancelled()) {
                    guiNewChat.printChatMessageWithOptionalDeletion(event.getChat(), line);
                }

                if (line > 256) {
                    line = 0;
                }
                event.setCancelled(true);
            }
        }
    }
}

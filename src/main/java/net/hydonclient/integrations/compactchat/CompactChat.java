package net.hydonclient.integrations.compactchat;

import net.hydonclient.event.EventListener;
import net.hydonclient.event.events.game.ChatEvent;
import net.hydonclient.util.ChatColor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiNewChat;

public class CompactChat {

    // TODO: Add a toggle for this

    private static CompactChat INSTANCE;

    private String lastMessage = "";

    private int line = 0;
    private int amount = 0;

    public static CompactChat getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CompactChat();
        }
        return INSTANCE;
    }

    /**
     * Complete credit to the Hyperium team for this (if they're the ones that made it, afaik they did)
     * - asbyth
     */

    @EventListener
    public void onChat(ChatEvent event) {
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

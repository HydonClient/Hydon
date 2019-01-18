package net.hydonclient.event.events.game;

import net.hydonclient.event.CancellableEvent;
import net.minecraft.util.IChatComponent;

public class ChatMessageReceivedEvent extends CancellableEvent {

    private String message;
    private IChatComponent chatComponent;

    /**
     * Fired when the client receives a message.
     *
     * @param message The message string
     * @param chatComponent The chat component
     */
    public ChatMessageReceivedEvent(String message, IChatComponent chatComponent) {
        this.message = message;
        this.chatComponent = chatComponent;
    }

    public String getMessage() {
        return message;
    }

    public IChatComponent getChatComponent() {
        return chatComponent;
    }
}

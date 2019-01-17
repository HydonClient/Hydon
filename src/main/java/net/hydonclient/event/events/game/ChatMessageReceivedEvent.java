package net.hydonclient.event.events.game;

import net.hydonclient.event.Event;

public class ChatMessageReceivedEvent extends Event {

    private String message;

    /**
     * Fired when the client receives a message.
     * @param message The message string
     */
    public ChatMessageReceivedEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}

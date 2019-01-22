package net.hydonclient.event.events.game;

import net.hydonclient.event.CancellableEvent;

public class ChatMessageSendEvent extends CancellableEvent {

    private String message;

    /**
     * Fired when the client sends a message
     *
     * @param message the sent message
     */
    public ChatMessageSendEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}

package net.hydonclient.event.events.game;

import net.hydonclient.event.Event;

public class ChatMessageReceivedEvent extends Event {

    private String message;

    public ChatMessageReceivedEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}

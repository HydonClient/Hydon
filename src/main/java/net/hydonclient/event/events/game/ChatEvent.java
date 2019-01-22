package net.hydonclient.event.events.game;

import net.hydonclient.event.CancellableEvent;
import net.minecraft.util.IChatComponent;

public class ChatEvent extends CancellableEvent {

    private final IChatComponent chat;

    /**
     * Fired when the client receives a message.
     *
     * @param chat Chat Component
     */
    public ChatEvent(IChatComponent chat) {
        this.chat = chat;
    }

    public IChatComponent getChat() {
        return chat;
    }
}

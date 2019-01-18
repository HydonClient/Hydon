package net.hydonclient.event.events.network.chat;

import net.hydonclient.event.CancellableEvent;
import net.minecraft.util.IChatComponent;

public class ServerChatEvent extends CancellableEvent {

    private final byte type;
    private IChatComponent chat;

    public ServerChatEvent(byte type, IChatComponent chat) {
        this.type = type;
        this.chat = chat;
    }

    public byte getType() {
        return type;
    }

    public IChatComponent getChat() {
        return chat;
    }

    public void setChat(IChatComponent chat) {
        this.chat = chat;
    }
}

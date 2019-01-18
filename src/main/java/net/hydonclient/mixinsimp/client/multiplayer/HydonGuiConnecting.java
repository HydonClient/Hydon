package net.hydonclient.mixinsimp.client.multiplayer;

import net.hydonclient.event.EventBus;
import net.hydonclient.event.events.network.ServerJoinEvent;

public class HydonGuiConnecting {

    public void connect(String ip, int port) {
        EventBus.call(new ServerJoinEvent(ip, port));
    }
}

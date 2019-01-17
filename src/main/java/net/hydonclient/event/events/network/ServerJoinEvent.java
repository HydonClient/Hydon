package net.hydonclient.event.events.network;

import net.hydonclient.event.Event;

public class ServerJoinEvent extends Event {

    private final String server;
    private final int port;

    public ServerJoinEvent(String server, int port) {
        this.server = server;
        this.port = port;
    }

    public String getServer() {
        return server;
    }

    public int getPort() {
        return port;
    }
}

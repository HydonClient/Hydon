package net.hydonclient.mixinsimp.client.gui;

import net.hydonclient.event.EventBus;
import net.hydonclient.event.events.network.ServerLeaveEvent;

public class HydonGuiDisconnected {

    public void init() {
        EventBus.call(new ServerLeaveEvent());
    }
}

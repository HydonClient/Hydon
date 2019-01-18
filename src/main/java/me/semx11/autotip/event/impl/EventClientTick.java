package me.semx11.autotip.event.impl;

import me.semx11.autotip.Autotip;
import me.semx11.autotip.event.Event;
import net.hydonclient.event.EventListener;
import net.hydonclient.event.events.game.UpdateEvent;

public class EventClientTick implements Event {

    private final Autotip autotip;

    public EventClientTick(Autotip autotip) {
        this.autotip = autotip;
    }

    @EventListener
    public void onClientTick(UpdateEvent event) {
        autotip.getMessageUtil().flushQueues();
        if (autotip.isInitialized()) {
            autotip.getStatsManager().saveCycle();
        }
    }

}
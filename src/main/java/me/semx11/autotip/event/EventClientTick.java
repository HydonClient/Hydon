package me.semx11.autotip.event;

import me.semx11.autotip.Autotip;
import net.hydonclient.event.Event;
import net.hydonclient.event.EventListener;
import net.hydonclient.event.events.game.UpdateEvent;

public class EventClientTick extends Event {

    private final Autotip autotip;

    public EventClientTick(Autotip autotip) {
        this.autotip = autotip;
    }

    @EventListener
    public void onTick(UpdateEvent event) {
        autotip.getMessageUtil().flushQueues();

        if (autotip.isInitialized()) {
            autotip.getStatsManager().saveCycle();
        }
    }
}

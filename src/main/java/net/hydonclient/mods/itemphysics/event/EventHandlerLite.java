package net.hydonclient.mods.itemphysics.event;

import net.hydonclient.event.EventListener;
import net.hydonclient.event.events.game.UpdateEvent;
import net.hydonclient.mods.itemphysics.physics.ClientPhysics;

public class EventHandlerLite {

    /**
     * Set the Client Physics tick to be the nano time of the system
     *
     * @param event invoked every tick
     */
    @EventListener
    public void onTick(UpdateEvent event) {
        ClientPhysics.tick = System.nanoTime();
    }
}

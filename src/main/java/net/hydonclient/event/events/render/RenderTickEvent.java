package net.hydonclient.event.events.render;

import net.hydonclient.event.Event;
import net.minecraft.client.gui.ScaledResolution;

public class RenderTickEvent extends Event {

    private ScaledResolution scaledResolution;

    /**
     * Fires every render tick.
     * @param scaledResolution
     */
    public RenderTickEvent(ScaledResolution scaledResolution) {
        this.scaledResolution = scaledResolution;
    }

    public ScaledResolution getScaledResolution() {
        return scaledResolution;
    }
}

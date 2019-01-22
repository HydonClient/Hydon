package net.hydonclient.event.events.render;

import net.hydonclient.event.Event;
import net.minecraft.client.gui.ScaledResolution;

public class RenderGameOverlayEvent extends Event {

    private ScaledResolution scaledResolution;
    private float partialTicks;

    /**
     * Fires when the game starts rendering the overlay (e.g. xp bar)
     *
     * @param scaledResolution the players monitor resolution
     * @param partialTicks     the world tick
     */
    public RenderGameOverlayEvent(ScaledResolution scaledResolution, float partialTicks) {
        this.scaledResolution = scaledResolution;
        this.partialTicks = partialTicks;
    }

    public ScaledResolution getScaledResolution() {
        return scaledResolution;
    }

    public float getPartialTicks() {
        return partialTicks;
    }
}

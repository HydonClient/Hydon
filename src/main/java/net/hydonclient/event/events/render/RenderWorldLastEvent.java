package net.hydonclient.event.events.render;

import net.hydonclient.event.Event;
import net.minecraft.client.renderer.RenderGlobal;

public class RenderWorldLastEvent extends Event {

    public final RenderGlobal context;
    public final float partialTicks;

    /**
     * Fired upon rendering the players hand
     *
     * @param context      the global renderer context, used for detecting when a hand is over a block?
     * @param partialTicks the world tick
     */
    public RenderWorldLastEvent(RenderGlobal context, float partialTicks) {
        this.context = context;
        this.partialTicks = partialTicks;
    }
}

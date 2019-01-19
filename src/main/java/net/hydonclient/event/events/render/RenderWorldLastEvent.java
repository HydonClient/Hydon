package net.hydonclient.event.events.render;

import net.hydonclient.event.Event;
import net.minecraft.client.renderer.RenderGlobal;

public class RenderWorldLastEvent extends Event {

    public final RenderGlobal context;
    public final float partialTicks;

    public RenderWorldLastEvent(RenderGlobal context, float partialTicks) {
        this.context = context;
        this.partialTicks = partialTicks;
    }
}

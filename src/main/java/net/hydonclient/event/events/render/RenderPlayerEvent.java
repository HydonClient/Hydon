package net.hydonclient.event.events.render;

import net.hydonclient.event.Event;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.RenderManager;

public class RenderPlayerEvent extends Event {

    private final AbstractClientPlayer entity;
    private final RenderManager renderManager;

    private final double x;
    private final double y;
    private final double z;

    private final float partialTicks;

    /**
     * Fired when a player is being rendered
     * @param entity the player entitiy
     * @param renderManager the render manager
     * @param x the x position of the player
     * @param y the y position of the player
     * @param z the z position of the player
     * @param partialTicks
     */
    public RenderPlayerEvent(AbstractClientPlayer entity, RenderManager renderManager, double x, double y, double z, float partialTicks) {
        this.entity = entity;
        this.renderManager = renderManager;

        this.x = x;
        this.y = y;
        this.z = z;

        this.partialTicks = partialTicks;
    }

    public AbstractClientPlayer getEntity() {
        return entity;
    }
    public RenderManager getRenderManager() {
        return renderManager;
    }

    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }
    public double getZ() {
        return z;
    }

    public float getPartialTicks() {
        return partialTicks;
    }
}

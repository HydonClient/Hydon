package net.hydonclient.event.events.render;

import net.hydonclient.event.CancellableEvent;
import net.minecraft.entity.item.EntityItem;

public class RenderItemEvent extends CancellableEvent {

    private EntityItem entity;
    private double x, y, z;
    private float entityYaw, partialTicks;

    public RenderItemEvent(EntityItem entity, double x, double y, double z, float entityYaw,
        float partialTicks) {
        this.entity = entity;
        this.x = x;
        this.y = y;
        this.z = z;
        this.entityYaw = entityYaw;
        this.partialTicks = partialTicks;
    }

    public EntityItem getEntity() {
        return entity;
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

    public float getEntityYaw() {
        return entityYaw;
    }

    public float getPartialTicks() {
        return partialTicks;
    }
}

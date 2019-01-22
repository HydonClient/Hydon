package net.hydonclient.event.events.render;

import net.hydonclient.event.CancellableEvent;
import net.minecraft.entity.Entity;

public class EntityRenderEvent extends CancellableEvent {

    /**
     * The entity being rendered
     */
    private final Entity entityIn;

    /**
     * The position of the entity (x, y, z) - (side to side, up to down, forwards to backwards)
     */
    private final float posX;
    private final float posY;
    private final float posZ;

    /**
     * The rotation of the entity (x, y) (horizontal, vertical)
     */
    private final float pitch;
    private final float yaw;

    /**
     * The scale of the entity
     */
    private final float scale;

    /**
     * The event constructor
     *
     * @param entityIn the entity
     * @param posX     the side to side horizontal position of the entity
     * @param posY     the up to down position of the entity
     * @param posZ     the forwards and backwards position of the entity
     * @param pitch    the horizontal rotation of the entity
     * @param yaw      the vertical rotation of the entity
     * @param scale    the scale of the entity
     */
    public EntityRenderEvent(Entity entityIn, float posX, float posY, float posZ, float pitch, float yaw, float scale) {
        this.entityIn = entityIn;

        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;

        this.pitch = pitch;
        this.yaw = yaw;

        this.scale = scale;
    }

    /**
     * Get the entity
     *
     * @return the entity
     */
    public Entity getEntityIn() {
        return entityIn;
    }

    /**
     * Get the x coordinate of the entity
     *
     * @return the x coordinate
     */
    public float getPosX() {
        return posX;
    }

    /**
     * Get the y coordinate of the entity
     *
     * @return the y coordinate
     */
    public float getPosY() {
        return posY;
    }

    /**
     * Get the z coordinate of the entity
     *
     * @return the z coordinate
     */
    public float getPosZ() {
        return posZ;
    }

    /**
     * Get the rotation of the entity
     *
     * @return the horizontal rotation
     */
    public float getPitch() {
        return pitch;
    }

    /**
     * Get the rotation of the entity
     *
     * @return the vertical rotation
     */
    public float getYaw() {
        return yaw;
    }

    /**
     * Get the scale of the entity
     *
     * @return the scale
     */
    public float getScale() {
        return scale;
    }
}

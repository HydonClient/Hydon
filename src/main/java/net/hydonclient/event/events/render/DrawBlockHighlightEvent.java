package net.hydonclient.event.events.render;

import net.hydonclient.event.CancellableEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MovingObjectPosition;

public class DrawBlockHighlightEvent extends CancellableEvent {

    private final EntityPlayer player;

    private final MovingObjectPosition targetedBlock;

    private final float partialTicks;

    /**
     * Fired when hovering over a block
     *
     * @param player        the player
     * @param targetedBlock the block they're looking at
     * @param partialTicks  the world tick
     */
    public DrawBlockHighlightEvent(EntityPlayer player, MovingObjectPosition targetedBlock, float partialTicks) {

        this.player = player;
        this.targetedBlock = targetedBlock;
        this.partialTicks = partialTicks;
    }

    public final EntityPlayer getPlayer() {
        return player;
    }

    public final MovingObjectPosition getTargetedBlock() {
        return targetedBlock;
    }

    public final float getPartialTicks() {
        return partialTicks;
    }
}

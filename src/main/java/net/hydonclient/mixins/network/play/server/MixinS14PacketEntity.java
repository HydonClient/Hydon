package net.hydonclient.mixins.network.play.server;

import net.minecraft.entity.Entity;
import net.minecraft.network.play.server.S14PacketEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(S14PacketEntity.class)
public class MixinS14PacketEntity {

    @Shadow
    protected int entityId;

    /**
     * @author asbyth
     * @reason NPE
     */
    @Overwrite
    public Entity getEntity(World worldIn) {
        return worldIn != null ? worldIn.getEntityByID(this.entityId) : null;
    }
}

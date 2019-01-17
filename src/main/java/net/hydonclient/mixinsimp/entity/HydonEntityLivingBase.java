package net.hydonclient.mixinsimp.entity;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.Vec3;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

public class HydonEntityLivingBase {

    private EntityLivingBase entityLivingBase;

    public HydonEntityLivingBase(EntityLivingBase entityLivingBase) {
        this.entityLivingBase = entityLivingBase;
    }

    public void getLook(float partialTicks, CallbackInfoReturnable<Vec3> ci, Vec3 look) {
        EntityLivingBase base = entityLivingBase;

        if (base instanceof EntityPlayerSP) {
            ci.setReturnValue(look);
        }
    }
}

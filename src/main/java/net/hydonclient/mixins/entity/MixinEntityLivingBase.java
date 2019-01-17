package net.hydonclient.mixins.entity;

import net.hydonclient.mixinsimp.entity.HydonEntityLivingBase;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityLivingBase.class)
public abstract class MixinEntityLivingBase extends MixinEntity {

    private HydonEntityLivingBase impl = new HydonEntityLivingBase((EntityLivingBase) (Object) this);

    /**
     * @author prplz
     * @reason MouseDelayFix - Fixes a bug introduced in 1.8 that causes the player's head to not be properly located when moving the mouse
     */
    @Inject(method = "getLook", at = @At("HEAD"), cancellable = true)
    private void getLook(float partialTicks, CallbackInfoReturnable<Vec3> ci) {
        impl.getLook(partialTicks, ci, super.getLook(partialTicks));
    }
}

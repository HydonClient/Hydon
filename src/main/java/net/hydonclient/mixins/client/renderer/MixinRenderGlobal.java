package net.hydonclient.mixins.client.renderer;

import net.hydonclient.mixinsimp.client.renderer.HydonRenderGlobal;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MovingObjectPosition;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RenderGlobal.class)
public class MixinRenderGlobal {

    private HydonRenderGlobal impl = new HydonRenderGlobal((RenderGlobal) (Object) this);

    @Inject(method = "drawSelectionBox", at = @At("HEAD"), cancellable = true)
    private void drawSelectionBox(EntityPlayer player, MovingObjectPosition movingObjectPositionIn, int p_72731_3_, float partialTicks, CallbackInfo ci) {
        impl.drawSelectionBox(player, movingObjectPositionIn, p_72731_3_, partialTicks, ci);
    }
}

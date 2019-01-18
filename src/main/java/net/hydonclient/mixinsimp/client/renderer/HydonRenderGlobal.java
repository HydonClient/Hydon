package net.hydonclient.mixinsimp.client.renderer;

import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MovingObjectPosition;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public class HydonRenderGlobal {

    private RenderGlobal renderGlobal;

    public HydonRenderGlobal(RenderGlobal renderGlobal) {
        this.renderGlobal = renderGlobal;
    }

    public void drawSelectionBox(EntityPlayer player, MovingObjectPosition movingObjectPositionIn, int p_72731_3_, float partialTicks, CallbackInfo ci) {
        if (HydonEntityRenderer.isCancelBox) {
            HydonEntityRenderer.isCancelBox = false;
            ci.cancel();
        }
    }
}

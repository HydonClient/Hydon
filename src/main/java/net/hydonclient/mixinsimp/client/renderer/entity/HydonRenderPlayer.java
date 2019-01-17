package net.hydonclient.mixinsimp.client.renderer.entity;

import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.entity.RenderPlayer;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public class HydonRenderPlayer {

    private RenderPlayer renderPlayer;

    public HydonRenderPlayer(RenderPlayer renderPlayer) {
        this.renderPlayer = renderPlayer;
    }

    public void renderRightArm(AbstractClientPlayer clientPlayer, CallbackInfo ci) {
        ModelPlayer modelplayer =  renderPlayer.getMainModel();
        modelplayer.isRiding = modelplayer.isSneak = false;
    }
}

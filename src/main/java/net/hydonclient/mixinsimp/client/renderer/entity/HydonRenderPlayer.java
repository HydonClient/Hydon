package net.hydonclient.mixinsimp.client.renderer.entity;

import net.hydonclient.event.EventBus;
import net.hydonclient.event.events.render.RenderPlayerEvent;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public class HydonRenderPlayer {

    private RenderPlayer renderPlayer;

    public HydonRenderPlayer(RenderPlayer renderPlayer) {
        this.renderPlayer = renderPlayer;
    }

    public void doRender(AbstractClientPlayer entity, double x, double y, double z, float entityYaw, float partialTicks, CallbackInfo ci, RenderManager renderManager) {
        EventBus.call(new RenderPlayerEvent(entity, renderManager, x, y, z, partialTicks));
    }

    public void renderRightArm(AbstractClientPlayer clientPlayer, CallbackInfo ci) {
        ModelPlayer modelplayer = renderPlayer.getMainModel();
        modelplayer.isRiding = modelplayer.isSneak = false;
    }
}

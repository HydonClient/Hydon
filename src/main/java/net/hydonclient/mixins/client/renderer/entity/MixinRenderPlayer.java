package net.hydonclient.mixins.client.renderer.entity;

import net.hydonclient.mixinsimp.client.renderer.entity.HydonRenderPlayer;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RenderPlayer.class)
public abstract class MixinRenderPlayer extends RendererLivingEntity<AbstractClientPlayer> {

    private HydonRenderPlayer impl = new HydonRenderPlayer((RenderPlayer) (Object) this);

    public MixinRenderPlayer(RenderManager renderManagerIn, ModelBase modelBaseIn, float shadowSizeIn) {
        super(renderManagerIn, modelBaseIn, shadowSizeIn);
    }

    @Shadow
    public abstract ModelPlayer getMainModel();

    @Inject(method = "renderRightArm", at = @At(value = "FIELD", ordinal = 3))
    private void renderRightArm(AbstractClientPlayer clientPlayer, CallbackInfo ci) {
        impl.renderRightArm(clientPlayer, ci);
    }
}

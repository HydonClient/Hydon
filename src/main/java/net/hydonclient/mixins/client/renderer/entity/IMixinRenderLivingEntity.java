package net.hydonclient.mixins.client.renderer.entity;

import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.entity.EntityLivingBase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(RendererLivingEntity.class)
public interface IMixinRenderLivingEntity<T extends EntityLivingBase> {

    @Invoker
    boolean callSetBrightness(T entityLivingBaseIn, float partialTicks, boolean combineTextures);

    @Invoker
    void callUnsetBrightness();
}

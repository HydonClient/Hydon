package net.hydonclient.mixins.client.renderer;

import net.minecraft.client.renderer.EntityRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(EntityRenderer.class)
public interface IMixinEntityRenderer {

    @Accessor
    void setCloudFog(boolean cloudFog);
}

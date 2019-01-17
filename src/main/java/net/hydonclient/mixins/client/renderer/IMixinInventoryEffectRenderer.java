package net.hydonclient.mixins.client.renderer;

import net.minecraft.client.renderer.InventoryEffectRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(InventoryEffectRenderer.class)
public interface IMixinInventoryEffectRenderer {

    @Accessor
    void setHasActivePotionEffects(boolean hasActivePotionEffects);
}

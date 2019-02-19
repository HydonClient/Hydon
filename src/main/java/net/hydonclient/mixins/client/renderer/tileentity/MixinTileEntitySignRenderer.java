package net.hydonclient.mixins.client.renderer.tileentity;

import net.hydonclient.mixinsimp.client.renderer.tileentity.HydonTileEntitySignRenderer;
import net.minecraft.client.renderer.tileentity.TileEntitySignRenderer;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntitySign;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TileEntitySignRenderer.class)
public abstract class MixinTileEntitySignRenderer extends TileEntitySpecialRenderer<TileEntitySign> {

    private HydonTileEntitySignRenderer impl = new HydonTileEntitySignRenderer();

    @Inject(method = "renderTileEntityAt", at = @At("HEAD"), cancellable = true)
    private void renderTileEntityAt(TileEntitySign te, double x, double y, double z, float partialTicks, int destroyStage, CallbackInfo ci) {
        impl.renderTileEntityAt(te, x, y, z, partialTicks, destroyStage, ci);
    }
}

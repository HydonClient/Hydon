package net.hydonclient.mixinsimp.client.renderer.tileentity;

import net.hydonclient.Hydon;
import net.minecraft.tileentity.TileEntitySign;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public class HydonTileEntitySignRenderer {

    public void renderTileEntityAt(TileEntitySign te, double x, double y, double z, float partialTicks, int destroyStage, CallbackInfo ci) {
        if (Hydon.SETTINGS.disableSigns) {
            ci.cancel();
        }
    }
}
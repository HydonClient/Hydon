package net.hydonclient.mixinsimp.world.chunk;

import net.hydonclient.Hydon;
import net.minecraft.client.Minecraft;
import net.minecraft.util.BlockPos;
import net.minecraft.world.EnumSkyBlock;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

public class HydonChunk {

    public void getLightFor(EnumSkyBlock type, BlockPos pos, CallbackInfoReturnable<Integer> cir) {
        if (!Minecraft.getMinecraft().isIntegratedServerRunning() && Hydon.SETTINGS.fullbright) {
            cir.setReturnValue(15);
        }
    }

    public void getLightSubtracted(BlockPos pos, int amount, CallbackInfoReturnable<Integer> cir) {
        if (!Minecraft.getMinecraft().isIntegratedServerRunning() && Hydon.SETTINGS.fullbright) {
            cir.setReturnValue(15);
        }
    }
}

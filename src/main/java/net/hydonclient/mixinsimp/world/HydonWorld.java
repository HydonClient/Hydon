package net.hydonclient.mixinsimp.world;

import net.hydonclient.Hydon;
import net.minecraft.client.Minecraft;
import net.minecraft.util.BlockPos;
import net.minecraft.world.EnumSkyBlock;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

public class HydonWorld {

    public void checkLightFor(EnumSkyBlock lightType, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        if (!Minecraft.getMinecraft().isIntegratedServerRunning() && Hydon.SETTINGS.fullbright) {
            cir.setReturnValue(false);
        }
    }

    public void getLightFromNeighborsFor(EnumSkyBlock type, BlockPos pos, CallbackInfoReturnable<Integer> cir) {
        if (!Minecraft.getMinecraft().isIntegratedServerRunning() && Hydon.SETTINGS.fullbright) {
            cir.setReturnValue(15);
        }
    }

    public void getLightFromNeighbor(BlockPos pos, CallbackInfoReturnable<Integer> ci) {
        if (!Minecraft.getMinecraft().isIntegratedServerRunning() && Hydon.SETTINGS.fullbright) {
            ci.setReturnValue(15);
        }
    }

    public void getRawLight(BlockPos pos, EnumSkyBlock lightType, CallbackInfoReturnable<Integer> ci) {
        if (!Minecraft.getMinecraft().isIntegratedServerRunning() && Hydon.SETTINGS.fullbright) {
            ci.setReturnValue(15);
        }
    }

    public void getLight(BlockPos pos, CallbackInfoReturnable<Integer> ci) {
        if (!Minecraft.getMinecraft().isIntegratedServerRunning() && Hydon.SETTINGS.fullbright) {
            ci.setReturnValue(15);
        }
    }

    public void getLight(BlockPos pos, boolean checkNeighbors, CallbackInfoReturnable<Integer> ci) {
        if (!Minecraft.getMinecraft().isIntegratedServerRunning() && Hydon.SETTINGS.fullbright) {
            ci.setReturnValue(15);
        }
    }
}

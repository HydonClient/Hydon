package net.hydonclient.mixins.world;

import net.hydonclient.mixinsimp.world.HydonWorld;
import net.minecraft.util.BlockPos;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(World.class)
public abstract class MixinWorld {

    private HydonWorld impl = new HydonWorld();

    /**
     * @author 2PI & prplz
     * @reason VoidFlickerFix - Fixes the Sky height
     */
    @Overwrite
    public double getHorizon() {
        return 0.0D;
    }

    /**
     * Removes lighting updates to:
     * increase framerate a bit
     * create a 'fullbright' effect
     *
     * @param lightType the type of light
     * @param pos       the block position
     * @param cir       callback
     */
    @Inject(method = "checkLightFor", at = @At("HEAD"), cancellable = true)
    private void checkLightFor(EnumSkyBlock lightType, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        impl.checkLightFor(lightType, pos, cir);
    }

    /**
     * Removes lighting updates to:
     * increase framerate a bit
     * create a 'fullbright' effect
     *
     * @param type the type of light
     * @param pos  the block position
     * @param cir  callback
     */
    @Inject(method = "getLightFromNeighborsFor", at = @At("HEAD"), cancellable = true)
    private void getLightFromNeighborsFor(EnumSkyBlock type, BlockPos pos, CallbackInfoReturnable<Integer> cir) {
        impl.getLightFromNeighborsFor(type, pos, cir);
    }

    /**
     * Removes lighting updates to:
     * increase framerate a bit
     * create a 'fullbright' effect
     *
     * @param pos the block position
     * @param cir callback
     */
    @Inject(method = "getLightFromNeighbors", at = @At("HEAD"), cancellable = true)
    private void getLightFromNeighbor(BlockPos pos, CallbackInfoReturnable<Integer> cir) {
        impl.getLightFromNeighbor(pos, cir);
    }

    /**
     * Removes lighting updates to:
     * increase framerate a bit
     * create a 'fullbright' effect
     *
     * @param pos       the block position
     * @param lightType the type of light
     * @param cir       callback
     */
    @Inject(method = "getRawLight", at = @At("HEAD"), cancellable = true)
    private void getRawLight(BlockPos pos, EnumSkyBlock lightType, CallbackInfoReturnable<Integer> cir) {
        impl.getRawLight(pos, lightType, cir);
    }

    /**
     * Removes lighting updates to:
     * increase framerate a bit
     * create a 'fullbright' effect
     *
     * @param pos the block position
     * @param cir callback
     */
    @Inject(method = "getLight(Lnet/minecraft/util/BlockPos;)I", at = @At("HEAD"), cancellable = true)
    private void getLight(BlockPos pos, CallbackInfoReturnable<Integer> cir) {
        impl.getLight(pos, cir);
    }

    /**
     * Removes lighting updates to:
     * increase framerate a bit
     * create a 'fullbright' effect
     *
     * @param pos            the block position
     * @param checkNeighbors check the neighboring blocks
     * @param cir            callback
     */
    @Inject(method = "getLight(Lnet/minecraft/util/BlockPos;Z)I", at = @At("HEAD"), cancellable = true)
    private void getLight(BlockPos pos, boolean checkNeighbors, CallbackInfoReturnable<Integer> cir) {
        impl.getLight(pos, checkNeighbors, cir);
    }
}

package net.hydonclient.mixins.world.chunk;

import net.hydonclient.mixinsimp.world.chunk.HydonChunk;
import net.minecraft.util.BlockPos;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Chunk.class)
public abstract class MixinChunk {

    @Shadow private boolean hasEntities;
    @Shadow @Final private World worldObj;
    @Shadow private long lastSaveTime;
    private HydonChunk impl = new HydonChunk();

    /**
     * Removes lighting updates to:
     * increase framerate a bit
     * create a 'fullbright' effect
     *
     * @param type the type of light
     * @param pos  the block position
     * @param cir  callback
     */
    @Inject(method = "getLightFor", at = @At("HEAD"), cancellable = true)
    private void getLightFor(EnumSkyBlock type, BlockPos pos, CallbackInfoReturnable<Integer> cir) {
        impl.getLightFor(type, pos, cir);
    }

    /**
     * Removes lighting updates to:
     * increase framerate a bit
     * create a 'fullbright' effect
     *
     * @param pos    the block position
     * @param amount amount of light
     * @param cir    callback
     */
    @Inject(method = "getLightSubtracted", at = @At("HEAD"), cancellable = true)
    private void getLightSubtracted(BlockPos pos, int amount, CallbackInfoReturnable<Integer> cir) {
        impl.getLightSubtracted(pos, amount, cir);
    }

    /**
     * @author asbyth
     * @reason concurrent chunk saving
     */
    @Overwrite
    public boolean needsSaving(boolean save) {
        if (save) {
            return (hasEntities && worldObj.getTotalWorldTime() != lastSaveTime);
        }

        return (hasEntities) && worldObj.getTotalWorldTime() >= lastSaveTime + 2111L;
    }
}

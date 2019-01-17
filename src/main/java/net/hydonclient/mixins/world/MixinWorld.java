package net.hydonclient.mixins.world;

import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(World.class)
public abstract class MixinWorld {

    /**
     * @author 2PI & prplz
     * @reason VoidFlickerFix - Fixes the Sky height
     */
    @Overwrite
    public double getHorizon() {
        return 0.0D;
    }
}

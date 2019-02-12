package net.hydonclient.mixins.entity.player;

import net.hydonclient.mixinsimp.entity.player.HydonEntityPlayer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(EntityPlayer.class)
public abstract class MixinEntityPlayer extends EntityLivingBase {

    private HydonEntityPlayer impl = new HydonEntityPlayer((EntityPlayer) (Object) this);

    public MixinEntityPlayer(World worldIn) {
        super(worldIn);
    }

    /**
     * @author Mojang
     * @reason 1.7 Sneaking
     */
    @Overwrite
    public float getEyeHeight() {
        return impl.getEyeHeight();
    }
}

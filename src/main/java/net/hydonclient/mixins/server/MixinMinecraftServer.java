package net.hydonclient.mixins.server;

import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(MinecraftServer.class)
public class MixinMinecraftServer {

    /**
     * @author Runemoro
     * @reason Speedup world creation speed
     */
    @Overwrite
    public void initialWorldChunkLoad() {}
}

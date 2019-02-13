package net.hydonclient.mixins.server;

import net.hydonclient.util.patches.IPatchedMinecraftServer;
import net.minecraft.profiler.Profiler;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(MinecraftServer.class)
public class MixinMinecraftServer implements IPatchedMinecraftServer {

    @Shadow @Final public Profiler theProfiler;
    private List<Profiler.Result> lastProfilerData = null;
    private String profilerName = "root";

    /**
     * @author Runemoro
     * @reason Speedup world creation speed
     */
    @Overwrite
    public void initialWorldChunkLoad() {}

    @Inject(method = "tick", at = @At("HEAD"))
    private void tick(CallbackInfo ci) {
        if (theProfiler.profilingEnabled) {
            lastProfilerData = theProfiler.getProfilingData(profilerName);
        } else {
            lastProfilerData = null;
        }
    }

    @Override
    public List<Profiler.Result> getLastProfilerData() {
        return lastProfilerData;
    }

    @Override
    public void setProfilerName(String profilerName) {
        this.profilerName = profilerName;
    }

    @Override
    public String getProfilerName() {
        return profilerName;
    }
}

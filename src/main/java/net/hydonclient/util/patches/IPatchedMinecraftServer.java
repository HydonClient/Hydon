package net.hydonclient.util.patches;

import net.minecraft.profiler.Profiler;

import java.util.List;

public interface IPatchedMinecraftServer {
    List<Profiler.Result> getLastProfilerData();
    void setProfilerName(String profilerName);
    String getProfilerName();
}

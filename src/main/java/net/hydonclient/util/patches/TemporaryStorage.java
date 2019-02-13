package net.hydonclient.util.patches;

import net.minecraft.client.renderer.chunk.CompiledChunk;

public class TemporaryStorage {
    public static ThreadLocal<CompiledChunk> currentCompiledChunk = new ThreadLocal<>();
}

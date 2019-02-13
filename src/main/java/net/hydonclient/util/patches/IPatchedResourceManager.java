package net.hydonclient.util.patches;

import net.minecraft.util.ResourceLocation;

public interface IPatchedResourceManager {

    boolean hasResource(ResourceLocation location);
}

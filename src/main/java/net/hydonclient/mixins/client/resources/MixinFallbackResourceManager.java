package net.hydonclient.mixins.client.resources;

import net.hydonclient.util.patches.IPatchedResourceManager;
import net.minecraft.client.resources.FallbackResourceManager;
import net.minecraft.client.resources.IResourcePack;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.List;

@Mixin(FallbackResourceManager.class)
public class MixinFallbackResourceManager implements IPatchedResourceManager {
    @Shadow @Final protected List<IResourcePack> resourcePacks;

    @Override
    public boolean hasResource(ResourceLocation location) {
        return resourcePacks.stream().anyMatch(pack -> pack.resourceExists(location));
    }
}

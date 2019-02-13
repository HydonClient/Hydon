package net.hydonclient.mixins.client.resources;

import net.hydonclient.util.patches.IPatchedResourceManager;
import net.minecraft.client.resources.FallbackResourceManager;
import net.minecraft.client.resources.SimpleReloadableResourceManager;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Map;

@Mixin(SimpleReloadableResourceManager.class)
public class MixinSimpleReloadableResourceManager implements IPatchedResourceManager {

    @Shadow
    @Final
    private Map<String, FallbackResourceManager> domainResourceManagers;

    @Override
    public boolean hasResource(ResourceLocation location) {
        FallbackResourceManager manager = domainResourceManagers.get(location.getResourceDomain());
        return manager instanceof IPatchedResourceManager && ((IPatchedResourceManager) manager).hasResource(location);
    }
}

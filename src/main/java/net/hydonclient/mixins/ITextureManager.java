package net.hydonclient.mixins;

import java.util.List;
import java.util.Map;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.renderer.texture.ITickable;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(TextureManager.class)
public interface ITextureManager {

    @Accessor
    List<ITickable> getListTickables();

    @Accessor
    Map<ResourceLocation, ITextureObject> getMapTextureObjects();

    @Accessor
    Logger getLogger();

}

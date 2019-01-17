package net.hydonclient.mixins.client.resources;

import net.minecraft.client.resources.AbstractResourcePack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.io.IOException;
import java.io.InputStream;

@Mixin(AbstractResourcePack.class)
public interface IMixinAbstractResourcePack {

    @Invoker
    InputStream callGetInputStreamByName(String name) throws IOException;
}

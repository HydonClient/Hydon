package net.hydonclient.mixins;

import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Minecraft.class)
public interface IMixinMinecraft {

    @Accessor
    void setDisplayWidth(int width);

    @Accessor
    void setDisplayHeight(int height);
}

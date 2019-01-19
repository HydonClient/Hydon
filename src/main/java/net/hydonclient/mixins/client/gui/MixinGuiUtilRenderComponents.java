package net.hydonclient.mixins.client.gui;

import net.hydonclient.mixinsimp.client.gui.HydonGuiUtilRenderComponents;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiUtilRenderComponents;
import net.minecraft.util.IChatComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import java.util.List;

@Mixin(GuiUtilRenderComponents.class)
public abstract class MixinGuiUtilRenderComponents {

    /**
     * @author asbyth
     * @reason Chat formatting resets when it goes over a line
     */
    @Overwrite
    public static List<IChatComponent> splitText(IChatComponent p_178908_0_, int p_178908_1_, FontRenderer p_178908_2_, boolean p_178908_3_, boolean p_178908_4_) {
        return HydonGuiUtilRenderComponents.splitText(p_178908_0_, p_178908_1_, p_178908_2_, p_178908_3_, p_178908_4_);
    }
}

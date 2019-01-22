package net.hydonclient.mixins.client.gui;

import java.util.List;

import net.minecraft.client.gui.ChatLine;
import net.minecraft.client.gui.GuiNewChat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(GuiNewChat.class)
public interface IMixinGuiNewChat {

    @Accessor
    void setIsScrolled(boolean isScrolled);

    @Accessor
    List<ChatLine> getDrawnChatLines();

    @Accessor
    int getScrollPos();

    @Accessor
    boolean getIsScrolled();
}

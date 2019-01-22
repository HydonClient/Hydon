package net.hydonclient.mixins.client.gui;

import java.util.List;

import net.hydonclient.mixinsimp.client.gui.HydonGuiNewChat;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ChatLine;
import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.util.IChatComponent;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiNewChat.class)
public abstract class MixinGuiNewChat {

    @Shadow
    @Final
    private List<ChatLine> chatLines;

    @Shadow
    @Final
    private List<ChatLine> drawnChatLines;

    @Shadow
    private boolean isScrolled;

    @Shadow
    private int scrollPos;

    @Shadow
    @Final
    private Minecraft mc;

    private HydonGuiNewChat impl = new HydonGuiNewChat((GuiNewChat) (Object) this);

    /**
     * Posted once a message is sent into the chat.
     */
    @Inject(method = "printChatMessage", at = @At("HEAD"), cancellable = true)
    private void printChatMessage(IChatComponent chatComponent, CallbackInfo ci) {
        impl.printChatMessage(chatComponent, ci);
    }

    /**
     * @author Mojang
     * @reason Changes the default chat-scrolling limit of 100 -> 512
     */
    @Overwrite
    private void setChatLine(IChatComponent chatComponent, int chatLineId, int updateCounter,
                             boolean displayOnly) {
        impl.setChatLine(chatComponent, chatLineId, updateCounter, displayOnly, scrollPos,
                isScrolled, drawnChatLines, chatLines, mc);
    }

    /**
     * @author Koding
     */
    @Overwrite
    public void drawChat(int updateCounter) {
        impl.drawChat(updateCounter);
    }
}

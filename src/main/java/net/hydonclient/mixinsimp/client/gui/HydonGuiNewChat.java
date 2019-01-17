package net.hydonclient.mixinsimp.client.gui;

import net.hydonclient.mixins.client.gui.IMixinGuiNewChat;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ChatLine;
import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.client.gui.GuiUtilRenderComponents;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MathHelper;

import java.util.List;

public class HydonGuiNewChat {

    private GuiNewChat guiNewChat;

    public HydonGuiNewChat(GuiNewChat guiNewChat) {
        this.guiNewChat = guiNewChat;
    }

    public void setChatLine(IChatComponent chatComponent, int chatLineId, int updateCounter, boolean displayOnly, int scrollPos, boolean isScrolled, List<ChatLine> drawnChatLines, List<ChatLine> chatLines, Minecraft mc) {
        if (chatLineId != 0) {
            guiNewChat.deleteChatLine(chatLineId);
        }

        int i = MathHelper.floor_float((float) guiNewChat.getChatWidth() / guiNewChat.getChatScale());
        List<IChatComponent> list = GuiUtilRenderComponents.splitText(chatComponent, i, mc.fontRendererObj, false, false);
        boolean flag = guiNewChat.getChatOpen();

        for (IChatComponent ichatcomponent : list) {
            if (flag && scrollPos > 0) {
                ((IMixinGuiNewChat) guiNewChat).setIsScrolled(isScrolled);
                guiNewChat.scroll(1);
            }

            drawnChatLines.add(0, new ChatLine(updateCounter, ichatcomponent, chatLineId));
        }

        while (drawnChatLines.size() > 512) {
            drawnChatLines.remove(drawnChatLines.size() - 1);
        }

        if (!displayOnly) {
            chatLines.add(0, new ChatLine(updateCounter, chatComponent, chatLineId));

            while (chatLines.size() > 512) {
                chatLines.remove(chatLines.size() - 1);
            }
        }
    }
}

package net.hydonclient.mixinsimp.client.gui;

import java.util.List;

import net.hydonclient.Hydon;
import net.hydonclient.event.EventBus;
import net.hydonclient.event.events.game.ChatEvent;
import net.hydonclient.mixins.client.gui.IMixinGuiNewChat;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ChatLine;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.client.gui.GuiUtilRenderComponents;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer.EnumChatVisibility;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MathHelper;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public class HydonGuiNewChat {

    private GuiNewChat guiNewChat;

    public HydonGuiNewChat(GuiNewChat guiNewChat) {
        this.guiNewChat = guiNewChat;
    }

    public void printChatMessage(IChatComponent chatComponent, CallbackInfo ci) {
        ChatEvent event = new ChatEvent(chatComponent);

        EventBus.call(event);

        if (event.isCancelled()) {
            ci.cancel();
        } else {
            if (event.getChat() != chatComponent) {
                guiNewChat.printChatMessageWithOptionalDeletion(event.getChat(), 0);
                ci.cancel();
            }
        }
    }

    public void setChatLine(IChatComponent chatComponent, int chatLineId, int updateCounter,
                            boolean displayOnly, int scrollPos, boolean isScrolled, List<ChatLine> drawnChatLines,
                            List<ChatLine> chatLines, Minecraft mc) {
        if (chatLineId != 0) {
            guiNewChat.deleteChatLine(chatLineId);
        }

        int i = MathHelper
                .floor_float((float) guiNewChat.getChatWidth() / guiNewChat.getChatScale());
        List<IChatComponent> list = GuiUtilRenderComponents
                .splitText(chatComponent, i, mc.fontRendererObj, false, false);
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

    public void drawChat(int updateCounter) {
        if (Minecraft.getMinecraft().gameSettings.chatVisibility != EnumChatVisibility.HIDDEN) {
            int i = guiNewChat.getLineCount();
            boolean flag = false;
            int j = 0;
            int k = ((IMixinGuiNewChat) guiNewChat).getDrawnChatLines().size();
            float f = Minecraft.getMinecraft().gameSettings.chatOpacity * 0.9F + 0.1F;

            if (k > 0) {
                if (Minecraft.getMinecraft().currentScreen instanceof GuiChat) {
                    flag = true;
                }

                float f1 = guiNewChat.getChatScale();
                int l = MathHelper
                        .ceiling_float_int((float) guiNewChat.getChatWidth() / f1);
                GlStateManager.pushMatrix();
                GlStateManager.translate(2.0F, 20.0F, 0.0F);
                GlStateManager.scale(f1, f1, 1.0F);

                for (int i1 = 0; i1 + ((IMixinGuiNewChat) guiNewChat).getScrollPos() < ((IMixinGuiNewChat) guiNewChat).getDrawnChatLines().size() && i1 < i; ++i1) {
                    ChatLine chatline = ((IMixinGuiNewChat) guiNewChat).getDrawnChatLines().get(i1 + ((IMixinGuiNewChat) guiNewChat).getScrollPos());

                    if (chatline != null) {
                        int j1 = updateCounter - chatline.getUpdatedCounter();

                        if (j1 < 200 || flag) {
                            double d0 = (double) j1 / 200.0D;
                            d0 = 1.0D - d0;
                            d0 = d0 * 10.0D;
                            d0 = MathHelper.clamp_double(d0, 0.0D, 1.0D);
                            d0 = d0 * d0;
                            int l1 = (int) (255.0D * d0);

                            if (flag) {
                                l1 = 255;
                            }

                            l1 = (int) ((float) l1 * f);
                            ++j;

                            if (l1 > 3) {
                                int i2 = 0;
                                int j2 = -i1 * 9;
                                if (!Hydon.SETTINGS.FAST_CHAT) {
                                    Gui.drawRect(i2, j2 - 9, i2 + l + 4, j2, l1 / 2 << 24);
                                }
                                String s = chatline.getChatComponent().getFormattedText();
                                GlStateManager.enableBlend();
                                Minecraft.getMinecraft().fontRendererObj
                                        .drawStringWithShadow(s, (float) i2, (float) (j2 - 8),
                                                16777215 + (l1 << 24));
                                GlStateManager.disableAlpha();
                                GlStateManager.disableBlend();
                            }
                        }
                    }
                }

                if (flag) {
                    int k2 = Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT;
                    GlStateManager.translate(-3.0F, 0.0F, 0.0F);
                    int l2 = k * k2 + k;
                    int i3 = j * k2 + j;
                    int j3 = ((IMixinGuiNewChat) guiNewChat).getScrollPos() * i3 / k;
                    int k1 = i3 * i3 / l2;

                    if (l2 != i3) {
                        int k3 = j3 > 0 ? 170 : 96;
                        int l3 = ((IMixinGuiNewChat) guiNewChat).getIsScrolled() ? 13382451 : 3355562;
                        Gui.drawRect(0, -j3, 2, -j3 - k1, l3 + (k3 << 24));
                        Gui.drawRect(2, -j3, 1, -j3 - k1, 13421772 + (k3 << 24));
                    }
                }

                GlStateManager.popMatrix();
            }
        }
    }
}

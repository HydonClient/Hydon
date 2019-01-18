package me.semx11.autotip.universal;

import me.semx11.autotip.Autotip;
import me.semx11.autotip.chat.ChatColor;
import me.semx11.autotip.util.MinecraftVersion;
import net.hydonclient.event.events.game.ChatMessageReceivedEvent;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.event.ClickEvent;
import net.minecraft.event.ClickEvent.Action;
import net.minecraft.event.HoverEvent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.IChatComponent;

public class UniversalUtil {

    private static Autotip autotip;

    public static void setAutotip(Autotip autotip) {
        UniversalUtil.autotip = autotip;
    }

    public static MinecraftVersion getMinecraftVersion() {
        return MinecraftVersion.V1_8_9;
    }

    public static String getUnformattedText(ChatMessageReceivedEvent event) {
        IChatComponent component = event.getChatComponent();
        return getUnformattedText(component);
    }

    public static String getUnformattedText(IChatComponent component) {
        if (component == null) {
            return null;
        }
        String text = component.getUnformattedText();
        return ChatColor.stripFormatting(text);
    }

    public static String getHoverText(ChatMessageReceivedEvent event) {
        IChatComponent component = event.getChatComponent();
        return getHoverText(component);
    }

    public static String getHoverText(IChatComponent component) {
        if (component == null) {
            return null;
        }
        ChatStyle style = component.getChatStyle();
        HoverEvent hoverEvent = style.getChatHoverEvent();
        if (hoverEvent == null) {
            return null;
        }
        IChatComponent hoverComponent = hoverEvent.getValue();
        return getUnformattedText(hoverComponent);
    }

    public static void addChatMessage(String text) {
        addChatMessage(newComponent(text));
    }

    public static void addChatMessage(String text, String url, String hoverText) {
        addChatMessage(newComponent(text, url, hoverText));
    }

    private static void addChatMessage(IChatComponent component) {
        EntityPlayerSP thePlayer = autotip.getMinecraft().thePlayer;
        thePlayer.addChatMessage(component);
    }

    private static IChatComponent newComponent(String text) {
        return new ChatComponentText(text);
    }

    private static IChatComponent newComponent(String text, String url, String hoverText) {
        ChatStyle chatStyle = new ChatStyle();
        ClickEvent clickEvent;
        HoverEvent hoverEvent;

        if (url != null && !url.equals("")) {
            clickEvent = new ClickEvent(Action.OPEN_URL, url);
            chatStyle.setChatClickEvent(clickEvent);
        }

        if (hoverText != null && !hoverText.equals("")) {
            hoverEvent = new HoverEvent(HoverEvent.Action.SHOW_TEXT, newComponent(hoverText));
            chatStyle.setChatHoverEvent(hoverEvent);
        }

        IChatComponent chatComponent = newComponent(text);

        return chatComponent.setChatStyle(chatStyle);
    }

    private static boolean isLegacy() {
        switch (autotip.getMcVersion()) {
            case V1_8:
            case V1_8_8:
            case V1_8_9:
                return true;
            case V1_9:
            case V1_9_4:
            case V1_10:
            case V1_10_2:
            case V1_11:
            case V1_11_2:
            case V1_12:
            case V1_12_1:
            case V1_12_2:
                return false;
            default:
                return false;
        }
    }

}

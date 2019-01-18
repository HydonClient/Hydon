package me.semx11.autotip.universal;

import static me.semx11.autotip.universal.ReflectionUtil.findClazz;
import static me.semx11.autotip.universal.ReflectionUtil.findMethod;
import static me.semx11.autotip.universal.ReflectionUtil.getConstructor;
import static me.semx11.autotip.universal.ReflectionUtil.getEnum;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import me.semx11.autotip.Autotip;
import me.semx11.autotip.chat.ChatColor;
import me.semx11.autotip.util.ErrorReport;
import me.semx11.autotip.util.MinecraftVersion;
import net.hydonclient.event.events.game.ChatMessageReceivedEvent;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.event.HoverEvent;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.IChatComponent;

public class UniversalUtil {

    private static Autotip autotip;

    private static Class<?> componentClass;
    private static Class<?> textComponentClass;

    private static Method addChatMethod;

    private static Class<?> chatStyleClass;

    private static Class<?> clickEventClass;
    private static Class<?> clickEventActionClass;

    private static Class<?> hoverEventClass;
    private static Class<?> hoverEventActionClass;

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

    public static String getFormattedText(Object component) {
        try {
            return (String) findMethod(
                componentClass,
                new String[]{"func_150254_d", "getFormattedText"}
            ).invoke(component);
        } catch (IllegalAccessException | InvocationTargetException e) {
            ErrorReport.reportException(e);
            return "";
        }
    }

    public static void addChatMessage(String text) {
        addChatMessage(newComponent(text));
    }

    public static void addChatMessage(String text, String url, String hoverText) {
        addChatMessage(newComponent(text, url, hoverText));
    }

    private static void addChatMessage(Object component) {
        EntityPlayerSP thePlayer = autotip.getMinecraft().thePlayer;
        try {
            addChatMethod.invoke(thePlayer, component);
        } catch (InvocationTargetException | IllegalAccessException e) {
            ErrorReport.reportException(e);
        }
    }

    private static Object newComponent(String text) {
        try {
            return getConstructor(textComponentClass, String.class).newInstance(text);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            ErrorReport.reportException(e);
            return null;
        }
    }

    private static Object newComponent(String text, String url, String hoverText) {
        try {
            Object chatStyle = chatStyleClass.newInstance();
            Object clickEvent;
            Object hoverEvent;

            if (url != null && !url.equals("")) {
                clickEvent = getConstructor(
                    clickEventClass,
                    clickEventActionClass,
                    String.class
                ).newInstance(getEnum(clickEventActionClass, "OPEN_URL"), url);

                findMethod(
                    chatStyleClass,
                    new String[]{"func_150241_a", "setChatClickEvent"}, // 1.8 - 1.12.2
                    clickEventClass
                ).invoke(chatStyle, clickEvent);
            }

            if (hoverText != null && !hoverText.equals("")) {
                hoverEvent = getConstructor(
                    hoverEventClass,
                    hoverEventActionClass,
                    componentClass
                ).newInstance(getEnum(hoverEventActionClass, "SHOW_TEXT"), newComponent(hoverText));

                findMethod(
                    chatStyleClass,
                    new String[]{"func_150209_a", "setChatHoverEvent"}, // 1.8 - 1.12.2
                    hoverEventClass
                ).invoke(chatStyle, hoverEvent);
            }

            Object chatComponent = newComponent(text);

            return findMethod(
                textComponentClass,
                new String[]{"func_150255_a", "setChatStyle"}, // 1.8 - 1.12.2
                chatStyleClass
            ).invoke(chatComponent, chatStyle);

        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            ErrorReport.reportException(e);
            return null;
        }
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

    static {
        componentClass = findClazz(
            "net.minecraft.util.IChatComponent", // 1.8 - 1.8.9
            "net.minecraft.util.text.ITextComponent" // 1.9 - 1.12.2
        );
        textComponentClass = findClazz(
            "net.minecraft.util.ChatComponentText", // 1.8 - 1.8.9
            "net.minecraft.util.text.TextComponentString" // 1.9 - 1.12.2
        );
        addChatMethod = findMethod(
            EntityPlayerSP.class,
            new String[]{
                "func_145747_a", // 1.8  - 1.8.9  | 1.11 - 1.12.2
                "func_146105_b", // 1.9  - 1.10.2
                "addChatMessage", // 1.8  - 1.8.9  | 1.11 - 1.12.2
                "addChatComponentMessage", // 1.9  - 1.10.2
                "sendMessage" // 1.11 - 1.12.2
            },
            componentClass
        );
        chatStyleClass = findClazz(
            "net.minecraft.util.ChatStyle", // 1.8 - 1.8.9
            "net.minecraft.util.text.Style" // 1.9 - 1.12.2
        );
        clickEventClass = findClazz(
            "net.minecraft.event.ClickEvent", // 1.8 - 1.8.9
            "net.minecraft.util.text.event.ClickEvent" // 1.9 - 1.12.2
        );
        clickEventActionClass = findClazz(
            "net.minecraft.event.ClickEvent$Action", // 1.8 - 1.8.9
            "net.minecraft.util.text.event.ClickEvent$Action" // 1.9 - 1.12.2
        );
        hoverEventClass = findClazz(
            "net.minecraft.event.HoverEvent", // 1.8 - 1.8.9
            "net.minecraft.util.text.event.HoverEvent" // 1.9 - 1.12.2
        );
        hoverEventActionClass = findClazz(
            "net.minecraft.event.HoverEvent$Action", // 1.8 - 1.8.9
            "net.minecraft.util.text.event.HoverEvent$Action" // 1.9 - 1.12.2
        );
    }

}

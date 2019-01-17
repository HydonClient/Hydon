package net.hydonclient.managers.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.hydonclient.event.EventBus;
import net.hydonclient.event.EventListener;
import net.hydonclient.event.events.game.UpdateEvent;
import net.hydonclient.managers.HydonManagers;
import net.hydonclient.managers.impl.keybind.HydonKeyBind;
import net.hydonclient.managers.impl.keybind.KeyBindConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;
import org.reflections.Reflections;

public class KeybindHandler {

    private List<HydonKeyBind> keyBindList = new ArrayList<>();
    private KeyBindConfig keyBindConfig = new KeyBindConfig();
    private Map<Integer, Boolean> keyMap;

    public void register(HydonKeyBind keyBind) {
        keyBindList.add(keyBind);

        List<KeyBinding> newBinds = new ArrayList<>(
            Arrays.asList(Minecraft.getMinecraft().gameSettings.keyBindings));
        newBinds.add(keyBind);
        Minecraft.getMinecraft().gameSettings.keyBindings = newBinds
            .toArray(new KeyBinding[0]);
    }

    public void loadBinds() {
        HydonManagers.INSTANCE.getConfigManager().register(keyBindConfig);
        EventBus.register(this);

        new Reflections("net.hydonclient.managers.impl.keybind.impl")
            .getSubTypesOf(HydonKeyBind.class).forEach(aClass -> {
            try {
                HydonKeyBind keyBind = aClass.newInstance();
                keyBindList.add(keyBind);

                List<KeyBinding> newBinds = new ArrayList<>(
                    Arrays.asList(Minecraft.getMinecraft().gameSettings.keyBindings));
                newBinds.add(keyBind);
                Minecraft.getMinecraft().gameSettings.keyBindings = newBinds
                    .toArray(new KeyBinding[0]);
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        });
    }

    public void loadPrevBinds() {
        for (HydonKeyBind keyBind : keyBindList) {
            if (keyBindConfig.KEY_VALUES.containsKey(keyBind.getKeyDescription())) {
                keyBind.setKeyCodeNoConfig(
                    Math.toIntExact(
                        Math.round(keyBindConfig.KEY_VALUES.get(keyBind.getKeyDescription()))));
            }
        }
    }

    @EventListener
    public void onUpdate(UpdateEvent e) {
        if (keyMap == null) {
            keyMap = new HashMap<>();
        }

        if (Minecraft.getMinecraft().currentScreen == null) {
            for (HydonKeyBind keybind : getKeybinds()) {
                int key = keybind.getKeyCode();
                boolean pressed = Keyboard.isKeyDown(key);

                if (pressed) {
                    keybind.onHeld();
                }

                keyMap.putIfAbsent(key, false);
                boolean keyBool = keyMap.get(key);

                if (!keyBool && pressed) {
                    keybind.onPress();
                    keyMap.replace(key, true);
                } else if (!pressed && keyBool) {
                    keybind.onRelease();
                    keyMap.replace(key, false);
                }
            }
        }
    }

    public List<HydonKeyBind> getKeybinds() {
        return keyBindList;
    }

    public KeyBindConfig getKeyBindConfig() {
        return keyBindConfig;
    }
}

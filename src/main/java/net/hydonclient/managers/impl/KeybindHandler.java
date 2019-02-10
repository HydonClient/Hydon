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

    /**
     * List of keybinds that are initialized with - extends HydonKeyBind
     */
    private List<HydonKeyBind> keyBindList = new ArrayList<>();

    /**
     * Configuration of all the keybinds to be saved that are initialized with - extends HydonKeyBind
     */
    private KeyBindConfig keyBindConfig = new KeyBindConfig();

    /**
     * The map of keybinds
     */
    private Map<Integer, Boolean> keyMap;

    /**
     * Register commands that are initialized with this
     *
     * @param keyBind the keybind class initialized with - extends HydonKeyBind
     */
    public void register(HydonKeyBind keyBind) {
        keyBindList.add(keyBind);

        List<KeyBinding> newBinds = new ArrayList<>(
                Arrays.asList(Minecraft.getMinecraft().gameSettings.keyBindings));
        newBinds.add(keyBind);
        Minecraft.getMinecraft().gameSettings.keyBindings = newBinds
                .toArray(new KeyBinding[0]);
    }

    /**
     * Load the classes that are initialized with - extends HydonKeyBind
     */
    public void loadBinds() {
        HydonManagers.INSTANCE.getConfigManager().register(keyBindConfig);
        EventBus.register(this);

//        new Reflections("net.hydonclient.managers.impl.keybind.impl")
//                .getSubTypesOf(HydonKeyBind.class).forEach(aClass -> {
//            try {
//                HydonKeyBind keyBind = aClass.newInstance();
//                keyBindList.add(keyBind);
//
//                List<KeyBinding> newBinds = new ArrayList<>(
//                        Arrays.asList(Minecraft.getMinecraft().gameSettings.keyBindings));
//                newBinds.add(keyBind);
//                Minecraft.getMinecraft().gameSettings.keyBindings = newBinds
//                        .toArray(new KeyBinding[0]);
//            } catch (InstantiationException | IllegalAccessException e) {
//                e.printStackTrace();
//            }
//        });
    }

    /**
     * Set the previously set keybinds that arent in the configuration
     */
    public void loadPrevBinds() {
        for (HydonKeyBind keyBind : keyBindList) {
            if (keyBindConfig.KEY_VALUES.containsKey(keyBind.getKeyDescription())) {
                keyBind.setKeyCodeNoConfig(
                        Math.toIntExact(
                                Math.round(keyBindConfig.KEY_VALUES.get(keyBind.getKeyDescription()))));
            }
        }
    }

    /**
     * If the keybind is pressed, held, or released, update the current event
     *
     * @param e the event being used
     */
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

    /**
     * The list of keybinds that are initialized with - extends HydonKeyBind
     *
     * @return the list of keybinds
     */
    public List<HydonKeyBind> getKeybinds() {
        return keyBindList;
    }

    /**
     * The configuration of the keybinds that are initialized with - extends HydonKeyBind
     *
     * @return the configuration
     */
    public KeyBindConfig getKeyBindConfig() {
        return keyBindConfig;
    }
}

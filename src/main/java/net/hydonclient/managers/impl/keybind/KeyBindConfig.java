package net.hydonclient.managers.impl.keybind;

import java.util.HashMap;
import java.util.Map;

import net.hydonclient.managers.impl.config.SaveVal;

public class KeyBindConfig {

    /**
     * The configuration for keybinds
     * Anything that's initialized with - extends HydonKeyBind
     * and saved with - HydonManagers.getConfigManager().registerConfig(new Class(anything else));
     * will be saved into Hydon/config.json under KEY_VALUES
     * <p>
     * It will use the LWJGL keycode number instead of the key name
     */
    @SaveVal
    public Map<String, Double> KEY_VALUES = new HashMap<>();

}

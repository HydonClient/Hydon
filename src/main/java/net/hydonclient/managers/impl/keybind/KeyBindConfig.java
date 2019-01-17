package net.hydonclient.managers.impl.keybind;

import java.util.HashMap;
import java.util.Map;
import net.hydonclient.managers.impl.config.SaveVal;

public class KeyBindConfig {

    @SaveVal
    public Map<String, Double> KEY_VALUES = new HashMap<>();

}

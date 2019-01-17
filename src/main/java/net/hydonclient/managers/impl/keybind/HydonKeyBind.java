package net.hydonclient.managers.impl.keybind;

import net.hydonclient.managers.HydonManagers;
import net.minecraft.client.settings.KeyBinding;

public class HydonKeyBind extends KeyBinding {

    private String description, category;
    private int keyCode;

    public HydonKeyBind(String description, int keyCode) {
        this(description, keyCode, "Hydon");
    }

    public HydonKeyBind(String description, int keyCode, String category) {
        super(description, keyCode, category);

        this.description = description;
        this.keyCode = keyCode;
        this.category = category;
    }

    @Override
    public String getKeyDescription() {
        return this.description;
    }

    @Override
    public int getKeyCodeDefault() {
        return this.keyCode;
    }

    @Override
    public int getKeyCode() {
        return this.keyCode;
    }

    @Override
    public String getKeyCategory() {
        return this.category;
    }

    public void setKeyCodeNoConfig(int keyCode) {
        this.keyCode = keyCode;
        super.setKeyCode(keyCode);
    }

    @Override
    public void setKeyCode(int keyCode) {
        setKeyCodeNoConfig(keyCode);

        KeyBindConfig keybindConfig = HydonManagers.INSTANCE.getKeybindHandler().getKeyBindConfig();
        keybindConfig.KEY_VALUES.putIfAbsent(description, (double) keyCode);
        keybindConfig.KEY_VALUES.replace(description, (double) keyCode);
    }

    public void onPress() {
    }

    public void onHeld() {
    }

    public void onRelease() {
    }

}

package net.hydonclient.managers.impl.keybind;

import net.hydonclient.managers.HydonManagers;
import net.minecraft.client.settings.KeyBinding;

public class HydonKeyBind extends KeyBinding {

    /**
     * The name and category of the keybind
     */
    private String description, category;

    /**
     * The key for the keybind
     */
    private int keyCode;

    /**
     * The constructor for keybinds with a default Category
     *
     * @param description the name of the keybind
     * @param keyCode     the key for the keybind
     */
    public HydonKeyBind(String description, int keyCode) {
        this(description, keyCode, "Hydon");
    }

    /**
     * The constructor for keybinds with a changeable Category
     *
     * @param description the name of the keybind
     * @param keyCode     the key for the keybind
     * @param category    the name of the keybind category
     */
    public HydonKeyBind(String description, int keyCode, String category) {
        super(description, keyCode, category);

        this.description = description;
        this.keyCode = keyCode;
        this.category = category;
    }

    /**
     * Get the name of the keybind
     *
     * @return the name of the keybind
     */
    @Override
    public String getKeyDescription() {
        return this.description;
    }

    /**
     * Get the default key for the keybind
     *
     * @return the default key
     */
    @Override
    public int getKeyCodeDefault() {
        return this.keyCode;
    }

    /**
     * Get the key for the keybind
     *
     * @return the key
     */
    @Override
    public int getKeyCode() {
        return this.keyCode;
    }

    /**
     * Get the name of the category the keybind is set to
     *
     * @return the name of the category
     */
    @Override
    public String getKeyCategory() {
        return this.category;
    }

    /**
     * Set an unchangeable keybind
     *
     * @param keyCode the key
     */
    public void setKeyCodeNoConfig(int keyCode) {
        this.keyCode = keyCode;
        super.setKeyCode(keyCode);
    }

    /**
     * Set the key for the keybind
     *
     * @param keyCode the key
     */
    @Override
    public void setKeyCode(int keyCode) {
        setKeyCodeNoConfig(keyCode);

        KeyBindConfig keybindConfig = HydonManagers.INSTANCE.getKeybindHandler().getKeyBindConfig();
        keybindConfig.KEY_VALUES.putIfAbsent(description, (double) keyCode);
        keybindConfig.KEY_VALUES.replace(description, (double) keyCode);
    }

    /**
     * When the key is pressed, what will happen?
     */
    public void onPress() {
    }

    /**
     * When the key is held, what will happen?
     */
    public void onHeld() {
    }

    /**
     * When the key is released, what will happen?
     */
    public void onRelease() {
    }

}

package net.hydonclient.mods.vanillaenhancements.gui;

import net.hydonclient.gui.main.HydonMainGui;
import net.hydonclient.gui.main.element.impl.SettingsToggle;
import net.hydonclient.gui.main.tab.SettingController;
import net.hydonclient.gui.main.tab.SettingGroup;
import net.hydonclient.gui.main.tab.SettingsDropdownElement;
import net.hydonclient.mods.vanillaenhancements.config.VEConfiguration;
import net.minecraft.client.gui.GuiScreen;

public class VESettings extends GuiScreen {

    public static final HydonMainGui INSTANCE = new HydonMainGui();

    private SettingController controller = new SettingController();

    public VESettings() {
        SettingsDropdownElement dropdownElement = new SettingsDropdownElement("Vanilla Enhancements");

        SettingGroup hotBarElements = new SettingGroup("Hotbar Elements");

        hotBarElements.addElements(
                new SettingsToggle("Amplifier Preview", VEConfiguration.AMPLIFIER_PREVIEW, result -> VEConfiguration.AMPLIFIER_PREVIEW = result));
        hotBarElements.addElements(
                new SettingsToggle("Arrow Counter", VEConfiguration.ARROW_COUNTER, result -> VEConfiguration.ARROW_COUNTER = result));
        hotBarElements.addElements(
                new SettingsToggle("Damage Preview", VEConfiguration.DAMAGE_PREVIEW, result -> VEConfiguration.DAMAGE_PREVIEW = result));

        SettingGroup inventoryElements = new SettingGroup("Inventory Elements");
        inventoryElements.addElements(
                new SettingsToggle("Protection Potential", VEConfiguration.ARMOR_PROT_POTENTIAL, result -> VEConfiguration.ARMOR_PROT_POTENTIAL = result));
        inventoryElements.addElements(
                new SettingsToggle("Projectile Protection Potential", VEConfiguration.ARMOR_PROJ_PROT_POTENTIAL, result -> VEConfiguration.ARMOR_PROJ_PROT_POTENTIAL = result));

        dropdownElement.addElements(hotBarElements, inventoryElements);

        controller.addElements(dropdownElement);
    }
}

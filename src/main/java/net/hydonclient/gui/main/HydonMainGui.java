package net.hydonclient.gui.main;

import java.io.IOException;

import net.hydonclient.Hydon;
import net.hydonclient.gui.main.element.impl.SettingsButton;
import net.hydonclient.gui.main.element.impl.SettingsToggle;
import net.hydonclient.gui.main.tab.SettingController;
import net.hydonclient.gui.main.tab.SettingGroup;
import net.hydonclient.gui.main.tab.SettingsDropdownElement;
import net.hydonclient.util.GuiUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.MathHelper;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class HydonMainGui extends GuiScreen {

    public static final HydonMainGui INSTANCE = new HydonMainGui();

    public SettingGroup currentGroup = null;
    public int leftSideOffset, rightSideOffset = 0;
    private SettingController controller = new SettingController();

    public HydonMainGui() {
        SettingsDropdownElement dropdownElement = new SettingsDropdownElement("Dropdown 1");

        SettingGroup testGroupOne = new SettingGroup("Test 1");
        testGroupOne.addElements(
                new SettingsButton("Button 1", () -> System.out.println("Button 1 pressed")));
        testGroupOne.addElements(
                new SettingsButton("Button 2", () -> System.out.println("Button 2 pressed")));
        testGroupOne.addElements(new SettingsToggle("Example Toggle", false, result -> {
        }));

        SettingGroup testGroupTwo = new SettingGroup("Test 2");
        testGroupTwo.addElements(
                new SettingsButton("Button 3", () -> System.out.println("Button 3 pressed")));
        testGroupTwo.addElements(
                new SettingsButton("Button 4", () -> System.out.println("Button 4 pressed")));

        dropdownElement.addElements(testGroupOne, testGroupTwo);

        SettingsDropdownElement dropdownElementTwo = new SettingsDropdownElement("Dropdown 2");
        dropdownElementTwo.addElements(testGroupOne, testGroupTwo);

        controller.addElements(dropdownElement, dropdownElementTwo);

        /* General Configuration */
        SettingsDropdownElement generalElement = new SettingsDropdownElement("General");

        SettingGroup misc = new SettingGroup("Miscellaneous");
        SettingGroup togglesprint = new SettingGroup("Togglesprint");

        misc.addElements(new SettingsToggle("Fast Chat", Hydon.SETTINGS.fastChat, result -> Hydon.SETTINGS.fastChat = result));
        misc.addElements(
                new SettingsToggle("GUI Blur", Hydon.SETTINGS.blurEnabled, result -> Hydon.SETTINGS.blurEnabled = result));

        togglesprint.addElements(
                new SettingsToggle("Togglesprint", Hydon.SETTINGS.togglesprintEnabled, result -> Hydon.SETTINGS.togglesprintEnabled = result));
        togglesprint.addElements(
                new SettingsToggle("Stop after released", Hydon.SETTINGS.stopSprintingAfterReleased, result -> Hydon.SETTINGS.stopSprintingAfterReleased = result));

        generalElement.addElements(misc, togglesprint);

        controller.addElements(generalElement);


        /* Old Animations Configuration */
        SettingsDropdownElement oldAnimationsElement = new SettingsDropdownElement("Animations");

        SettingGroup animationElements = new SettingGroup("HUD Items");

        animationElements.addElements(
                new SettingsToggle("1.7 Debug", Hydon.SETTINGS.oldDebugMenu, result -> Hydon.SETTINGS.oldDebugMenu = result));

        oldAnimationsElement.addElements(animationElements);

        controller.addElements(oldAnimationsElement);


        /* Auto GG Configuration */
        SettingsDropdownElement autoGGElement = new SettingsDropdownElement("Auto GG");

        SettingGroup autoGG = new SettingGroup("AutoGG");

        autoGG.addElements(
                new SettingsToggle("AutoGG", Hydon.SETTINGS.autoGGEnabled, result -> Hydon.SETTINGS.autoGGEnabled = result));

        autoGGElement.addElements(autoGG);

        controller.addElements(autoGGElement);


        /* Cosmetics Configuration */
        SettingsDropdownElement cosmeticElement = new SettingsDropdownElement("Cosmetics");

        SettingGroup staffCosmetics = new SettingGroup("Staff Modules");

        staffCosmetics.addElements(
                new SettingsToggle("Wings", Hydon.SETTINGS.wingsEnabled, result -> Hydon.SETTINGS.wingsEnabled = result));

        cosmeticElement.addElements(staffCosmetics);

        controller.addElements(cosmeticElement);


        /* Improvements Configuration */
        SettingsDropdownElement improvements = new SettingsDropdownElement("Improvements");
        SettingGroup framerateImprovements = new SettingGroup("Framerate");
        SettingGroup generalImprovements = new SettingGroup("General");

        /*
         * Framerate Improvements
         * Anything that would generally improve framerate should go here
         */
        framerateImprovements.addElements(
                new SettingsToggle("Hide Armorstands", Hydon.SETTINGS.disableArmorstands, result -> Hydon.SETTINGS.disableArmorstands = result));
        framerateImprovements.addElements(
                new SettingsToggle("Hide Signs", Hydon.SETTINGS.disableSigns, result -> Hydon.SETTINGS.disableSigns = result));
        framerateImprovements.addElements(
                new SettingsToggle("Hide Item Frames", Hydon.SETTINGS.disableItemFrames, result -> Hydon.SETTINGS.disableItemFrames = result));

        /*
         * General Improvements
         * Anything that would aid someone in ease of access should go here
         */
        generalImprovements.addElements(
                new SettingsToggle("Windowed Fullscreen", Hydon.SETTINGS.windowedFullscreen, result -> Hydon.SETTINGS.windowedFullscreen = result));

        improvements.addElements(framerateImprovements, generalImprovements);
        controller.addElements(improvements);


        /* Vanilla Enhancements Configuration */
        SettingsDropdownElement veElement = new SettingsDropdownElement("Vanilla Enhancements");

        /*
         * Hotbar Elements
         * Anything that would show up on the Hotbar should show up here
         */
        SettingGroup hotBarElements = new SettingGroup("Hotbar Elements");

        hotBarElements.addElements(
                new SettingsToggle("Amplifier Preview", Hydon.SETTINGS.ampPreview, result -> Hydon.SETTINGS.ampPreview = result));
        hotBarElements.addElements(
                new SettingsToggle("Arrow Counter", Hydon.SETTINGS.arrowCounter, result -> Hydon.SETTINGS.arrowCounter = result));
        hotBarElements.addElements(
                new SettingsToggle("Damage Preview", Hydon.SETTINGS.damagePreview, result -> Hydon.SETTINGS.damagePreview = result));

        /*
         * Inventory Elements
         * Anything that would show up in the inventory should show up here
         */
        SettingGroup inventoryElements = new SettingGroup("Inventory Elements");
        inventoryElements.addElements(
                new SettingsToggle("Protection Potential", Hydon.SETTINGS.protPotential, result -> Hydon.SETTINGS.protPotential = result));
        inventoryElements.addElements(
                new SettingsToggle("Proj. Protection Potential", Hydon.SETTINGS.projPotential, result -> Hydon.SETTINGS.projPotential = result));

        /*
         * Other Elements
         * Anything that shows up outside of the inventory and isn't on the Hotbar should show up here
         */
        SettingGroup miscElements = new SettingGroup("Other Elements");
        miscElements.addElements(
                new SettingsToggle("Third Person Crosshair", Hydon.SETTINGS.thirdPersonCrosshair, result -> Hydon.SETTINGS.thirdPersonCrosshair = result));
        miscElements.addElements(
                new SettingsToggle("Compact Chat", Hydon.SETTINGS.compactChat, result -> Hydon.SETTINGS.compactChat = result));

        veElement.addElements(hotBarElements, inventoryElements, miscElements);

        controller.addElements(veElement);
    }

    @Override
    public void initGui() {
        ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());

        this.buttonList.add(new GuiButton(1, scaledResolution.getScaledWidth() / 2 - 40,
                scaledResolution.getScaledHeight() - 20, 80, 20, "Back"));
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();

        GuiUtils.drawBG();
        controller.draw();
        if (currentGroup != null) {
            currentGroup.draw();
        }

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        controller.mouseClicked(mouseButton, mouseX, mouseY);
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    public void handleMouseInput() throws IOException {
        ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
        int mouseOffset = MathHelper.clamp_int(Mouse.getEventDWheel(), -1, 1);
        int mouseX = (int) GuiUtils.getMouse().getX();
        if (mouseX <= 150) {
            leftSideOffset += mouseOffset * 10;
        } else if (mouseX >= scaledResolution.getScaledWidth() - 150) {
            rightSideOffset += mouseOffset * 10;
        }
        super.handleMouseInput();
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) {
        if (keyCode == Keyboard.KEY_ESCAPE) {
            Minecraft.getMinecraft().displayGuiScreen(null);
        }
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if (button.id == 1) {
            Minecraft.getMinecraft().displayGuiScreen(null);
        }
        super.actionPerformed(button);
    }
}

package net.hydonclient.gui.main;

import java.io.IOException;
import net.hydonclient.Hydon;
import net.hydonclient.gui.main.element.impl.SettingsToggle;
import net.hydonclient.gui.main.tab.SettingController;
import net.hydonclient.gui.main.tab.SettingGroup;
import net.hydonclient.gui.main.tab.SettingsDropdownElement;
import net.hydonclient.managers.impl.keybind.impl.ToggleSprintKeyBind;
import net.hydonclient.mods.autogg.config.AutoGGConfig;
import net.hydonclient.mods.blur.BlurMod;
import net.hydonclient.mods.oldanimations.config.OldAnimationsConfig;
import net.hydonclient.mods.vanillaenhancements.config.VEConfiguration;
import net.hydonclient.mods.wings.Wings;
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
        /* the top boye because he is most important */
        SettingsDropdownElement generalElement = new SettingsDropdownElement("General");

        SettingGroup misc = new SettingGroup("Miscellaneous");
        SettingGroup toggleSprint = new SettingGroup("Togglesprint");

        misc.addElements(new SettingsToggle("Fast Chat", Hydon.SETTINGS.fastChat,
            result -> Hydon.SETTINGS.fastChat = result));
        misc.addElements(
            new SettingsToggle("GUI Blur", BlurMod.BLUR_ENABLED,
                result -> BlurMod.BLUR_ENABLED = result));

        toggleSprint.addElements(
            new SettingsToggle("Togglesprint", ToggleSprintKeyBind.ENABLE_TOGGLESPRINT,
                result -> ToggleSprintKeyBind.ENABLE_TOGGLESPRINT = result));
        toggleSprint.addElements(
            new SettingsToggle("Stop after released", ToggleSprintKeyBind.STOP_SPRINT_AFTER_RELEASE,
                result -> ToggleSprintKeyBind.STOP_SPRINT_AFTER_RELEASE = result));

        generalElement.addElements(misc, toggleSprint);

        controller.addElements(generalElement);

        SettingsDropdownElement oldAnimationsElement = new SettingsDropdownElement("Animations");

        SettingGroup animationElements = new SettingGroup("HUD Items");

        animationElements.addElements(
            new SettingsToggle("1.7 Debug", OldAnimationsConfig.OLD_DEBUG_MENU,
                result -> OldAnimationsConfig.OLD_DEBUG_MENU = result));

        oldAnimationsElement.addElements(animationElements);

        controller.addElements(oldAnimationsElement);

        SettingsDropdownElement autoGGElement = new SettingsDropdownElement("Auto GG");

        SettingGroup autoGG = new SettingGroup("AutoGG");

        autoGG.addElements(
            new SettingsToggle("AutoGG", AutoGGConfig.ENABLED,
                result -> AutoGGConfig.ENABLED = result));

        autoGGElement.addElements(autoGG);

        controller.addElements(autoGGElement);

        SettingsDropdownElement cosmeticElement = new SettingsDropdownElement("Cosmetics");

        SettingGroup staffCosmetics = new SettingGroup("Staff Modules");

        staffCosmetics.addElements(
            new SettingsToggle("Wings", Wings.WINGS_ENABLED,
                result -> Wings.WINGS_ENABLED = result));

        cosmeticElement.addElements(staffCosmetics);

        controller.addElements(cosmeticElement);

        SettingsDropdownElement veElement = new SettingsDropdownElement("Vanilla Enhancements");

        SettingGroup hotBarElements = new SettingGroup("Hotbar Elements");

        hotBarElements.addElements(
            new SettingsToggle("Amplifier Preview", VEConfiguration.AMPLIFIER_PREVIEW,
                result -> VEConfiguration.AMPLIFIER_PREVIEW = result));
        hotBarElements.addElements(
            new SettingsToggle("Arrow Counter", VEConfiguration.ARROW_COUNTER,
                result -> VEConfiguration.ARROW_COUNTER = result));
        hotBarElements.addElements(
            new SettingsToggle("Damage Preview", VEConfiguration.DAMAGE_PREVIEW,
                result -> VEConfiguration.DAMAGE_PREVIEW = result));

        SettingGroup inventoryElements = new SettingGroup("Inventory Elements");
        inventoryElements.addElements(
            new SettingsToggle("Protection Potential", VEConfiguration.ARMOR_PROT_POTENTIAL,
                result -> VEConfiguration.ARMOR_PROT_POTENTIAL = result));
        inventoryElements.addElements(
            new SettingsToggle("Proj. Protection Potential",
                VEConfiguration.ARMOR_PROJ_PROT_POTENTIAL,
                result -> VEConfiguration.ARMOR_PROJ_PROT_POTENTIAL = result));

        SettingGroup miscElements = new SettingGroup("Other Elements");
        miscElements.addElements(
            new SettingsToggle("Third Person Crosshair", VEConfiguration.THIRD_PERSON_CROSSHAIR,
                result -> VEConfiguration.THIRD_PERSON_CROSSHAIR = result));
        miscElements.addElements(
            new SettingsToggle("Compact Chat", VEConfiguration.COMPACT_CHAT,
                result -> VEConfiguration.COMPACT_CHAT = result));

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

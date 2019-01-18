package net.hydonclient.gui.main;

import java.io.IOException;
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

    private HydonMainGui() {
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
    }

    @Override
    public void initGui() {
        ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());

        this.buttonList.add(new GuiButton(1, scaledResolution.getScaledWidth() / 2 - 40,
            scaledResolution.getScaledHeight() - 20, 80, 20, "Back"));
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
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

package pw.cinque.keystrokesmod;

import net.hydonclient.event.EventBus;
import net.hydonclient.event.EventListener;
import net.hydonclient.event.events.game.UpdateEvent;
import net.hydonclient.event.events.gui.MouseInputEvent;
import net.hydonclient.managers.HydonManagers;
import net.hydonclient.mods.Mod;
import net.hydonclient.mods.Mod.Info;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import org.lwjgl.input.Mouse;
import pw.cinque.keystrokesmod.gui.GuiKeystrokes;
import pw.cinque.keystrokesmod.gui.key.FillerKey;
import pw.cinque.keystrokesmod.gui.key.Key;
import pw.cinque.keystrokesmod.gui.key.KeyHolder;
import pw.cinque.keystrokesmod.util.ClickCounter;

/**
 * The mod's base class.
 */
@Info(name = "Keystrokes Mod", version = "v5", author = "fyu")
public class KeystrokesMod extends Mod {

    private KeyHolder keyHolder;
    private GuiKeystrokes gui;
    private final ClickCounter leftClickCounter = new ClickCounter();
    private final ClickCounter rightClickCounter = new ClickCounter();
    private boolean showGui;
    private boolean leftMouseButton, rightMouseButton;

    @Override
    public void load() {
        HydonManagers.INSTANCE.getCommandManager().register(new KeystrokesCommand(this));
        EventBus.register(new KeystrokesRenderer(this));
        EventBus.register(this);
        buildKeyHolder();

        gui = new GuiKeystrokes(this);
    }

    @EventListener
    public void onClientTick(UpdateEvent event) {
        if (showGui) {
            Minecraft.getMinecraft().displayGuiScreen(gui);
            showGui = false;
        }
    }

    @EventListener
    public void onMouseInput(MouseInputEvent event) {
        boolean m = Mouse.isButtonDown(0);
        if (m != leftMouseButton) {
            leftMouseButton = m;
            if (m) {
                leftClickCounter.onClick();
            }
        }

        boolean m2 = Mouse.isButtonDown(1);
        if (m2 != rightMouseButton) {
            rightMouseButton = m2;
            if (m2) {
                rightClickCounter.onClick();
            }
        }
    }

    public void openGui() {
        showGui = true;
    }

    private void buildKeyHolder() {
        GameSettings gameSettings = Minecraft.getMinecraft().gameSettings;

        // the key layout is currently not configurable
        Key none = new FillerKey();
        Key keyW = new Key(gameSettings.keyBindForward);
        Key keyA = new Key(gameSettings.keyBindLeft);
        Key keyS = new Key(gameSettings.keyBindBack);
        Key keyD = new Key(gameSettings.keyBindRight);
        Key leftMouse = new Key(gameSettings.keyBindAttack).setLeftMouse();
        Key rightMouse = new Key(gameSettings.keyBindUseItem).setRightMouse();
        Key keySpaceBar = new Key(gameSettings.keyBindJump).setSpaceBar();

        keyHolder = new KeyHolder.Builder(this)
            .setWidth(82)
            .setGapSize(2)
            .addRow(none, keyW, none)
            .addRow(keyA, keyS, keyD)
            .addRow(leftMouse, rightMouse)
            .addRow(keySpaceBar)
            .build();
    }

    public KeyHolder getKeyHolder() {
        return this.keyHolder;
    }

    public GuiKeystrokes getGui() {
        return this.gui;
    }

    public ClickCounter getLeftClickCounter() {
        return this.leftClickCounter;
    }

    public ClickCounter getRightClickCounter() {
        return this.rightClickCounter;
    }

    public boolean isShowGui() {
        return this.showGui;
    }
}

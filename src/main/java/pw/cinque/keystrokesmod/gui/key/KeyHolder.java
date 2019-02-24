package pw.cinque.keystrokesmod.gui.key;

import com.google.common.base.Preconditions;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.hydonclient.Hydon;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;
import pw.cinque.keystrokesmod.KeystrokesMod;
import pw.cinque.keystrokesmod.util.Position;

/**
 * This class holds {@link Key}s stored in rows that will be drawn to the screen. It also forwards
 * mouse events to the keys.
 */
public class KeyHolder {

    private final List<Row> rows = new ArrayList<>();
    private double gapSize;
    private final Position position = new Position();
    private boolean dragging;
    private double width;
    private double height;
    private int displayWidth;
    private int displayHeight;
    private int scaledDisplayWidth;
    private int scaledDisplayHeight;
    private Color color = Color.WHITE;
    //    private boolean usingChroma = true;
//    private boolean outlineEnabled = true;
    // used by the chroma effect to calculate the hue for every key
    private double keyOffset;

    public KeyHolder() {
    }

    /**
     * Notifies the {@code KeyHolder} that the left mouse button was pressed.
     *
     * @param mouseX The mouse's X coordinate
     * @param mouseY The mouse's Y coordinate
     */
    public void onMousePress(int mouseX, int mouseY) {
        int x = position.getX();
        int y = position.getY();

        if (mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height) {
            dragging = true;
        }
    }

    /**
     * Notifies the {@code KeyHolder} that the left mouse button has been released.
     */
    public void onMouseRelease() {
        dragging = false;
    }

    /**
     * Draws the keys to the screen.
     *
     * @param mouseDeltaX The change in the mouse's X coordinate since the last call
     * @param mouseDeltaY The change in the mouse's Y coordinate since the last call
     */
    public void draw(int mouseDeltaX, int mouseDeltaY, int overrideX, int overrideY) {
        Minecraft mc = Minecraft.getMinecraft();

        if (displayWidth != mc.displayWidth || displayHeight != mc.displayHeight) {
            ScaledResolution resolution = new ScaledResolution(mc);

            position.updateScreenSize(scaledDisplayWidth = resolution.getScaledWidth(),
                    scaledDisplayHeight = resolution.getScaledHeight());

            displayWidth = mc.displayWidth;
            displayHeight = mc.displayHeight;
        }

        if (dragging) {
            position.translate(mouseDeltaX, mouseDeltaY);
        }

        if (position.getX() < 0.0) {
            position.setX(0);
        } else if (position.getX() + width > scaledDisplayWidth) {
            position.setX((int) (scaledDisplayWidth - width));
        }

        if (position.getY() < 0.0) {
            position.setY(0);
        } else if (position.getY() + height > scaledDisplayHeight) {
            position.setY((int) (scaledDisplayHeight - height));
        }

        GL11.glPushMatrix();
        GL11.glTranslated(overrideX != -1 ? overrideX : position.getX(),
                overrideY != -1 ? overrideY : position.getY(), 0.0);

        GlStateManager.disableAlpha();
        GlStateManager.enableBlend();
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        for (Row row : rows) {
            GL11.glPushMatrix();

            for (Key key : row.keys) {
                key.draw(row.keyWidth, row.height);
                double offset = row.keyWidth + gapSize;
                GL11.glTranslated(offset, 0.0, 0.0);
                keyOffset += offset / width;
            }

            GL11.glPopMatrix();
            GL11.glTranslated(0.0, row.height + gapSize, 0.0);
            keyOffset = 0.0f;
        }

        GL11.glPopMatrix();
    }

    /**
     * Gets the color for the current key. If the chroma effect is enabled, this method will
     * automatically calculate the correct hue for the key's position.
     *
     * @param offset The distance between the key position and the position of where you're drawing
     * @param invert If {@code true} the color is inverted
     * @return The ARGB color
     */
    int getColor(double offset, boolean invert) {
        int color;

        if (Hydon.SETTINGS.KEYSTROKES_CHROMA) {
            // if chroma effect is enabled, use black instead of the actual inverted color
            if (invert) {
                color = 0xFF000000;
            } else {
                // Cycle through color spectrum for 2 seconds
                float hue = System.currentTimeMillis() % 2000L / 2000.0f;
                // Adjust hue based on key position
                hue += (keyOffset + offset / width) * 0.3;
                color = Color.HSBtoRGB(hue, 1.0f, 1.0f);
            }
        } else {
            color = this.color.getRGB();

            if (invert) {
                color ^= 0x00FFFFFF;
            }
        }

        return color;
    }

    public double getWidth() {
        return this.width;
    }

    public double getHeight() {
        return this.height;
    }

    public boolean isOutlineEnabled() {
        return Hydon.SETTINGS.KEYSTROKES_OUTLINE;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * This class is used to create a {@link KeyHolder} instance. Usage:
     * <ol>
     * <li>Set the width using {@link Builder#setWidth} and the gap size using {@link
     * Builder#setGapSize}.
     * <li>Add the keys using {@link Builder#addRow}.
     * <li>Call {@link Builder#build} to get the created {@code KeyHolder} instance.
     * </ol>
     */
    public static class Builder {

        private final KeystrokesMod keystrokesMod;
        private KeyHolder keyHolder = new KeyHolder();

        @java.beans.ConstructorProperties({"keystrokesMod"})
        public Builder(KeystrokesMod keystrokesMod) {
            this.keystrokesMod = keystrokesMod;
        }

        /**
         * Sets the total width of the {@code KeyHolder}.
         *
         * @param width The width
         * @throws IllegalArgumentException If {@code width} is lower than or equal to zero
         */
        public Builder setWidth(int width) {
            Preconditions.checkArgument(width > 0);
            keyHolder.width = width;
            return this;
        }

        /**
         * Sets the distance between keys. This distance is only used for rows that are added
         * <b>after</b> this method has been called, previous rows will not be updated.
         *
         * @param gapSize The gap size
         * @throws IllegalArgumentException If {@code gapSize} is lower than or equal to zero
         */
        public Builder setGapSize(int gapSize) {
            Preconditions.checkArgument(gapSize > 0);
            keyHolder.gapSize = gapSize;
            return this;
        }

        /**
         * Adds a row of keys.
         *
         * @param keys The {@code Key}s in this row
         * @throws IllegalStateException If this method is called before the width or gap size are
         *                               set.
         * @see Builder#setWidth
         * @see Builder#setGapSize
         */
        public Builder addRow(Key... keys) {
            Preconditions.checkState(keyHolder.width != -1 && keyHolder.gapSize != -1);

            for (Key key : keys) {
                key.setParent(keyHolder);
                key.setKeystrokesMod(keystrokesMod);
            }

            double keyWidth = (keyHolder.width - keyHolder.gapSize * (keys.length - 1))
                    / keys.length;
            double height = Arrays.stream(keys).mapToDouble(Key::getHeight).max().orElse(0.0);
            keyHolder.rows.add(new Row(keys, keyWidth, height));
            return this;
        }

        /**
         * Gets the {@code KeyHolder} instance which contains the keys that were added using the
         * {@link Builder#addRow} method.
         *
         * @return The created {@code KeyHolder}
         * @throws IllegalStateException If this method is called before any rows have been added.
         * @see Builder#addRow
         */
        public KeyHolder build() {
            Preconditions.checkState(!keyHolder.rows.isEmpty());
            keyHolder.height = keyHolder.rows.stream().mapToDouble(Row::getHeight).sum()
                    + keyHolder.gapSize * (keyHolder.rows.size() - 1);
            return keyHolder;
        }

    }

    private static class Row {

        private final Key[] keys;
        private final double keyWidth;
        private final double height;

        @java.beans.ConstructorProperties({"keys", "keyWidth", "height"})
        public Row(Key[] keys, double keyWidth, double height) {
            this.keys = keys;
            this.keyWidth = keyWidth;
            this.height = height;
        }

        public Key[] getKeys() {
            return this.keys;
        }

        public double getKeyWidth() {
            return this.keyWidth;
        }

        public double getHeight() {
            return this.height;
        }
    }

}

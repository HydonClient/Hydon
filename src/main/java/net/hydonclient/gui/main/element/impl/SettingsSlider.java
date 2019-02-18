package net.hydonclient.gui.main.element.impl;

import java.awt.Color;

import net.hydonclient.gui.main.element.SettingsElement;
import net.hydonclient.ttf.HydonFonts;
import net.hydonclient.ttf.MinecraftFontRenderer;
import net.hydonclient.util.GuiUtils;
import net.hydonclient.util.maps.Images;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;

public class SettingsSlider extends SettingsElement {

    /**
     * The sliders values
     */
    private double sliderValue;

    /**
     * The sliders prefix
     */
    private String dispString;

    /**
     * If the slider is being dragged, return true
     */
    private boolean dragging;

    /**
     * If return true, show the decimal point
     */
    private boolean showDecimal;

    /**
     * The minimum value of the sliders configuration
     */
    private double minValue;

    /**
     * The maximum value of the sliders configuration
     */
    private double maxValue;

    /**
     * How precise do you want the slider to be?
     */
    private int precision;

    /**
     * The sliders suffix
     */
    private String suffix;

    /**
     * Combines the dispString, values, and suffix
     */
    private String displayString;

    private Listener listener;

    /**
     * The constructor for sliders
     *
     * @param prefix     the name of the slider
     * @param suf        the suffix of the slider
     * @param minVal     the minimum value of the slider
     * @param maxVal     the maximum value of the slider
     * @param currentVal the currently set value of the slider
     * @param showDec    return true if you want to expand the decimal
     * @param listener   the end-result of the slider
     */
    public SettingsSlider(String prefix, String suf, double minVal, double maxVal,
                          double currentVal, boolean showDec, Listener listener) {
        this.width = 130;
        this.height = 30;
        minValue = minVal;
        maxValue = maxVal;
        sliderValue = (currentVal - minValue) / (maxValue - minValue);
        dispString = prefix;
        suffix = suf;
        showDecimal = showDec;
        this.listener = listener;
        String val;

        if (showDecimal) {
            val = Double.toString(sliderValue * (maxValue - minValue) + minValue);
            precision = Math.min(val.substring(val.indexOf(".") + 1).length(), 4);
        } else {
            val = Integer
                    .toString((int) Math.round(sliderValue * (maxValue - minValue) + minValue));
            precision = 0;
        }

        displayString = dispString + val + suffix;
    }

    /**
     * If the mouse is dragging, update the slider
     *
     * @param mouseX the current mouse x location
     * @param mouseY the current mouse y location
     */
    @Override
    public void mouseDragged(int mouseX, int mouseY) {
        if (!GuiUtils.isHovered(this.x, this.y, this.width, this.height)) {
            return;
        }

        if (this.dragging) {
            this.sliderValue = (mouseX - (this.x + 4)) / (float) (this.width);
            updateSlider();
        }

        Gui.drawRect(this.x + (int) (this.sliderValue * (float) (this.width)), this.y, 8,
                this.height, new Color(255, 255, 255, 20).getRGB());
    }

    /**
     * Draw the slider
     *
     * @param x location of the note on the x axis
     * @param y location of the note on the y axis
     */
    @Override
    public void draw(int x, int y) {
        this.x = x + 5;
        this.y = y;

        MinecraftFontRenderer fontRenderer = HydonFonts.FONT_REGULAR;

//        Gui.drawRect(this.x, this.y, this.x + this.width, this.y + this.height,
//                new Color(255, 255, 255, 20).getRGB());
        GlStateManager.enableBlend();
        Gui.drawRect(this.x, this.y + 15, this.x + this.width, this.y + this.height,
                new Color(255, 255, 255, 20).getRGB());
        Gui.drawRect(this.x + (int) (this.sliderValue * this.width), this.y + 15,
                this.x + (int) (this.sliderValue * this.width) + 5,
                this.y + this.height, new Color(255, 255, 255, 20).getRGB());

        fontRenderer
                .drawCenteredString(displayString, this.x + this.width / 2f, this.y + 5, 0xffffff);
    }

    /**
     * If the mouse button is clicked, start dragging the slider and update it
     *
     * @param button the mouse button being clicked
     * @param mouseX the current mouse x location
     * @param mouseY the current mouse y location
     */
    @Override
    public void mouseClicked(int button, int mouseX, int mouseY) {
        if (!GuiUtils.isHovered(this.x, this.y, this.width, this.height)) {
            return;
        }

        this.sliderValue = (float) (mouseX - (this.x + 4)) / (float) (this.width);
        updateSlider();
        this.dragging = true;
    }

    private void updateSlider() {
        if (this.sliderValue < 0.0F) {
            this.sliderValue = 0.0F;
        }

        if (this.sliderValue > 1.0F) {
            this.sliderValue = 1.0F;
        }

        StringBuilder val;

        if (showDecimal) {
            val = new StringBuilder(
                    Double.toString(sliderValue * (maxValue - minValue) + minValue));

            if (val.substring(val.indexOf(".") + 1).length() > precision) {
                val = new StringBuilder(val.substring(0, val.indexOf(".") + precision + 1));

                if (val.toString().endsWith(".")) {
                    val = new StringBuilder(val.substring(0, val.indexOf(".") + precision));
                }
            } else {
                while (val.substring(val.indexOf(".") + 1).length() < precision) {
                    val.append("0");
                }
            }
        } else {
            val = new StringBuilder(Integer
                    .toString((int) Math.round(sliderValue * (maxValue - minValue) + minValue)));
        }

        displayString = dispString + val + suffix;

        listener.onChanged(getValue());
    }

    /**
     * Once the mouse button is released, stop dragging the slider
     *
     * @param mouseX the current mouse x location
     * @param mouseY the current mouse y location
     */
    @Override
    public void mouseReleased(int mouseX, int mouseY) {
        this.dragging = false;
    }

    /**
     * Get the value as an integer
     *
     * @return the integer value
     */
    public int getValueInt() {
        return (int) Math.round(sliderValue * (maxValue - minValue) + minValue);
    }

    /**
     * Get the value as a double
     *
     * @return the double value
     */
    public double getValue() {
        return sliderValue * (maxValue - minValue) + minValue;
    }

    /**
     * Set the value of the slider
     *
     * @param value the value of the slider
     */
    public void setValue(double value) {
        this.sliderValue = (value - minValue) / (maxValue - minValue);
    }

    @Override
    public void setWidth(int width) {
        this.width = width - 8;
    }

    /**
     * The class for the end-result of the slider
     */
    public interface Listener {

        /**
         * When the slider is changed, what will be the new value for the configuration
         *
         * @param value the new configuration value
         */
        void onChanged(double value);
    }
}
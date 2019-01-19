package net.hydonclient.gui.main.element.impl;

import java.awt.Color;
import net.hydonclient.gui.main.element.SettingsElement;
import net.hydonclient.ttf.HydonFonts;
import net.hydonclient.ttf.MinecraftFontRenderer;
import net.minecraft.client.gui.Gui;

public class SettingsSlider extends SettingsElement {

    private double sliderValue;

    private String dispString;

    private boolean dragging;
    private boolean showDecimal;

    private double minValue;
    private double maxValue;
    private int precision;

    private String suffix;
    private String displayString;

    private Listener listener;

    public SettingsSlider(String prefix, String suf, double minVal, double maxVal,
        double currentVal, boolean showDec, Listener listener) {
        this.width = 140;
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

    @Override
    public void mouseDragged(int mouseX, int mouseY) {
        if (this.dragging) {
            this.sliderValue = (mouseX - (this.x + 4)) / (float) (this.width - 8);
            updateSlider();
        }

        Gui.drawRect(this.x + (int) (this.sliderValue * (float) (this.width - 8)), this.y, 8,
            this.height, new Color(0, 0, 0, 20).getRGB());
    }

    @Override
    public void draw(int x, int y) {
        this.x = x + 5;
        this.y = y;

        MinecraftFontRenderer fontRenderer = HydonFonts.PRODUCT_SANS_REGULAR;

        Gui.drawRect(this.x, this.y, this.x + this.width, this.y + this.height,
            new Color(0, 0, 0, 20).getRGB());
        Gui.drawRect(this.x, this.y + 15, this.x + this.width, this.y + this.height,
            new Color(0, 0, 0, 20).getRGB());
        Gui.drawRect(this.x + (int) (this.sliderValue * (float) (this.width - 8)), this.y + 15,
            this.x + (int) (this.sliderValue * (float) (this.width - 8)) + 8,
            this.y + this.height, new Color(0, 0, 0, 20).getRGB());

        fontRenderer
            .drawCenteredString(displayString, this.x + this.width / 2f, this.y + 5, 0xffffff);
    }

    @Override
    public void mouseClicked(int button, int mouseX, int mouseY) {
        this.sliderValue = (float) (mouseX - (this.x + 4)) / (float) (this.width - 8);
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

    @Override
    public void mouseReleased(int mouseX, int mouseY) {
        this.dragging = false;
    }

    public int getValueInt() {
        return (int) Math.round(sliderValue * (maxValue - minValue) + minValue);
    }

    public double getValue() {
        return sliderValue * (maxValue - minValue) + minValue;
    }

    public void setValue(double d) {
        this.sliderValue = (d - minValue) / (maxValue - minValue);
    }

    public interface Listener {

        void onChanged(double value);
    }
}
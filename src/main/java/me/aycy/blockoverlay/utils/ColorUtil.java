package me.aycy.blockoverlay.utils;

import net.hydonclient.Hydon;

import java.awt.Color;

public final class ColorUtil {
    public static Color getChroma() {

        int chromaSpeed = Hydon.SETTINGS.boChromaSpeed;
        double time = System.currentTimeMillis() % (18000L / chromaSpeed) / (18000.0f / chromaSpeed);
        Color chroma = Color.getHSBColor((float) time, 1.0f, 1.0f);

        return new Color(chroma.getRed(), chroma.getGreen(), chroma.getBlue(), Hydon.SETTINGS.boAlpha);
    }

    public static Color getConfigColor() {
        return new Color(Hydon.SETTINGS.boRed, Hydon.SETTINGS.boGreen, Hydon.SETTINGS.boBlue, Hydon.SETTINGS.boAlpha);
    }
}

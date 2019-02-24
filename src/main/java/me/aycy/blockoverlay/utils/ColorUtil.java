package me.aycy.blockoverlay.utils;

import net.hydonclient.Hydon;

import java.awt.Color;

public final class ColorUtil {
    public static Color getChroma() {

        int chromaSpeed = Hydon.SETTINGS.BLOCKOVERLAY_CHROMA_SPEED;
        double time = System.currentTimeMillis() % (18000L / chromaSpeed) / (18000.0f / chromaSpeed);
        Color chroma = Color.getHSBColor((float) time, 1.0f, 1.0f);

        return new Color(chroma.getRed(), chroma.getGreen(), chroma.getBlue(), Hydon.SETTINGS.BLOCKOVERLAY_ALPHA);
    }

    public static Color getConfigColor() {
        return new Color(Hydon.SETTINGS.BLOCKOVERLAY_RED, Hydon.SETTINGS.BLOCKOVERLAY_GREEN, Hydon.SETTINGS.BLOCKOVERLAY_BLUE, Hydon.SETTINGS.BLOCKOVERLAY_ALPHA);
    }
}

package net.hydonclient.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

public class ResolutionUtil {

    public static ScaledResolution getCurrent() {
        return new ScaledResolution(Minecraft.getMinecraft());
    }

    public static double getImageScaleFactor() {
        return getCurrent().getScaledHeight_double() / getCurrent().getScaledWidth_double()
                / 8d;
    }

}

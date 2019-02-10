package net.hydonclient.ttf;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;

import net.hydonclient.Hydon;

public class HydonFonts {

    public static MinecraftFontRenderer FONT_REGULAR;
    public static MinecraftFontRenderer FONT_BOLD;

    static {
        try {
            FONT_REGULAR = new MinecraftFontRenderer(Font.createFont(Font.TRUETYPE_FONT,
                    Hydon.class.getResourceAsStream("/assets/minecraft/fonts/RobotoCondensed-Regular.ttf"))
                    .deriveFont(20f), true, true);
            FONT_BOLD = new MinecraftFontRenderer(Font.createFont(Font.TRUETYPE_FONT,
                    Hydon.class.getResourceAsStream("/assets/minecraft/fonts/RobotoCondensed-Bold.ttf"))
                    .deriveFont(30f), true, true);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }
    }

}

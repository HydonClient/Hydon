package net.hydonclient.ttf;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;

import net.hydonclient.Hydon;

public class HydonFonts {

    public static MinecraftFontRenderer PRODUCT_SANS_REGULAR;
    public static MinecraftFontRenderer PRODUCT_SANS_BOLD;

    static {
        try {
            PRODUCT_SANS_REGULAR = new MinecraftFontRenderer(Font.createFont(Font.TRUETYPE_FONT,
                    Hydon.class.getResourceAsStream("/assets/minecraft/fonts/Product-Sans-Regular.ttf"))
                    .deriveFont(20f), true, true);
            PRODUCT_SANS_BOLD = new MinecraftFontRenderer(Font.createFont(Font.TRUETYPE_FONT,
                    Hydon.class.getResourceAsStream("/assets/minecraft/fonts/Product-Sans-Bold.ttf"))
                    .deriveFont(30f), true, true);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }
    }

}

package net.hydonclient.util;

import com.google.common.collect.Maps;
import java.awt.image.BufferedImage;
import java.util.Map;
import net.minecraft.client.renderer.texture.DynamicTexture;
import org.lwjgl.opengl.GL11;

public class DynamicImageUtil {

    private static Map<String, DynamicTexture> textureMap = Maps.newHashMap();

    public static void addTexture(String name, BufferedImage image) {
        textureMap.put(name, new DynamicTexture(image));
    }

    public static void bindTexture(String name) {
        if (textureMap.containsKey(name)) {
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureMap.get(name).getGlTextureId());
        }
    }

    public static Map<String, DynamicTexture> getTextureMap() {
        return textureMap;
    }
}

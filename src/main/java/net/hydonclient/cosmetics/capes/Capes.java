package net.hydonclient.cosmetics.capes;

import com.mojang.authlib.minecraft.MinecraftProfileTexture;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IImageBuffer;
import net.minecraft.client.renderer.ThreadDownloadImageData;
import net.minecraft.util.ResourceLocation;

public class Capes {

    private static final Map<UUID, ResourceLocation> capes = new HashMap<>();

    /**
     * Loads a cape from the cache or from the website.
     *
     * @param uuid the uuid of the player
     * @param url  the url to fetch from
     */
    public static void loadCape(UUID uuid, String url) {
        if (url == null || url.isEmpty()) {
            return;
        }

        MinecraftProfileTexture mpt = new MinecraftProfileTexture(url, new HashMap<>());
        ResourceLocation rl = new ResourceLocation("skins/" + mpt.getHash());

        IImageBuffer iib = new IImageBuffer() {
            public void skinAvailable() {
                capes.put(uuid, rl);
            }

            public BufferedImage parseUserSkin(BufferedImage bufferedImage) {
                return parseCape(bufferedImage);
            }
        };

        ThreadDownloadImageData textureCape = new ThreadDownloadImageData(null, mpt.getUrl(), null, iib);
        Minecraft.getMinecraft().getTextureManager().loadTexture(rl, textureCape);
    }

    /**
     * Parse the cape image
     *
     * @param img the buffered image of the cape
     */
    private static BufferedImage parseCape(BufferedImage img) {
        int imageWidth = 64;
        int imageHeight = 32;
        int srcWidth = img.getWidth();

        for (int srcHeight = img.getHeight(); imageWidth < srcWidth || imageHeight < srcHeight;
             imageHeight *= 2) {
            imageWidth *= 2;
        }

        BufferedImage imgNew = new BufferedImage(imageWidth, imageHeight, 2);
        Graphics g = imgNew.getGraphics();
        g.drawImage(img, 0, 0, null);
        g.dispose();
        return imgNew;
    }

    /**
     * Get the capes of users
     * If they have one, add it
     * If they don't, ignore
     *
     * @return the capes
     */
    public static Map<UUID, ResourceLocation> getCapes() {
        return capes;
    }
}

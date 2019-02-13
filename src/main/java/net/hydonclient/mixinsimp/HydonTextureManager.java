package net.hydonclient.mixinsimp;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.Callable;
import net.hydonclient.mixins.ITextureManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.renderer.texture.ITickableTextureObject;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.util.ReportedException;
import net.minecraft.util.ResourceLocation;

public class HydonTextureManager {

    private HashMap<String, ITextureObject> textures = new HashMap<>();

    private TextureManager textureManager;

    public HydonTextureManager(TextureManager textureManager) {
        this.textureManager = textureManager;
    }

    public boolean loadTickableTexture(ResourceLocation textureLocation,
        ITickableTextureObject textureObj) {
        if (textureManager.loadTexture(textureLocation, textureObj)) {
            ((ITextureManager) textureManager).getListTickables().add(textureObj);
            textures.put(textureLocation.toString(), textureObj);
            return true;
        } else {
            return false;
        }
    }

    public boolean loadTexture(ResourceLocation textureLocation, ITextureObject textureObj) {
        boolean flag = true;

        ITextureObject textureCopy = textures.get(textureLocation.toString());
        if (textureCopy != null) {
            textureObj = textureCopy;
        }

        try {
            textureObj.loadTexture(Minecraft.getMinecraft().getResourceManager());
        } catch (IOException ioexception) {
            ((ITextureManager) textureManager).getLogger()
                .warn((String) ("Failed to load texture: " + textureLocation),
                    (Throwable) ioexception);
            textureObj = TextureUtil.missingTexture;
            ((ITextureManager) this).getMapTextureObjects()
                .put(textureLocation, (ITextureObject) textureObj);
            flag = false;
        } catch (Throwable throwable) {
            final ITextureObject p_110579_2_f = textureObj;
            CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Registering texture");
            CrashReportCategory crashreportcategory = crashreport
                .makeCategory("Resource location being registered");
            crashreportcategory.addCrashSection("Resource location", textureLocation);
            crashreportcategory
                .addCrashSectionCallable("Texture object class", new Callable<String>() {
                    public String call() throws Exception {
                        return p_110579_2_f.getClass().getName();
                    }
                });
            throw new ReportedException(crashreport);
        }

        ((ITextureManager) textureManager).getMapTextureObjects()
            .put(textureLocation, (ITextureObject) textureObj);
        return flag;
    }

}

package net.hydonclient.gui;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import javax.imageio.ImageIO;
import net.hydonclient.ttf.HydonFonts;
import net.hydonclient.ttf.MinecraftFontRenderer;
import net.hydonclient.util.DynamicImageUtil;
import net.hydonclient.util.GuiUtils;
import net.hydonclient.util.Images;
import net.hydonclient.util.Multithreading;
import net.hydonclient.util.ResolutionUtil;
import net.hydonclient.util.WebUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class GuiHydonCredits extends GuiScreen {

    private static JsonArray authorsArray = new JsonArray();
    private static final String API_ENDPOINT = "https://api.github.com/repos/HydonClient/Hydon/contributors";

    private int yOffset;

    @Override
    public void initGui() {
        ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());

        this.buttonList.add(new GuiButton(1, scaledResolution.getScaledWidth() / 4,
            scaledResolution.getScaledHeight() - 30, scaledResolution.getScaledWidth() / 2, 20,
            "Back"));

        Multithreading.run(() -> {
            try {
                JsonParser jsonParser = new JsonParser();
                String json = WebUtil.httpGet(API_ENDPOINT);
                authorsArray = jsonParser.parse(json).getAsJsonArray();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void handleMouseInput() throws IOException {
        int scrollState = Mouse.getEventDWheel();
        if (scrollState > 0) {
            yOffset += 10;
        } else if (scrollState < 0) {
            yOffset -= 10;
        }

        super.handleMouseInput();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        MinecraftFontRenderer fontRenderer = HydonFonts.PRODUCT_SANS_REGULAR;
        GuiUtils.drawBG();
        ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());

        double logoScaleFactor = ResolutionUtil.getImageScaleFactor() / 1.3;
        int logoWidth = (int) (Images.LOGO.getWidth() * logoScaleFactor);
        int logoHeight = (int) (Images.LOGO.getHeight() * logoScaleFactor);
        int logoX = (ResolutionUtil.getCurrent().getScaledWidth() - logoWidth) / 2;
        int logoY = 10;

        AtomicInteger yHeight = new AtomicInteger(logoHeight + logoY + 10 + yOffset);
        AtomicBoolean flipBoolean = new AtomicBoolean();

        if (authorsArray.size() == 0) {
            fontRenderer.drawCenteredStringWithShadow("Fetching contributors...",
                scaledResolution.getScaledWidth() / 2f, scaledResolution.getScaledHeight() / 2f,
                0xffffff);
        }

        authorsArray.forEach(jsonElement -> {
            JsonObject jsonObject = jsonElement.getAsJsonObject();

            if (!DynamicImageUtil.getTextureMap()
                .containsKey(jsonObject.get("login").getAsString())) {
                try {
                    DynamicImageUtil.addTexture(jsonObject.get("login").getAsString(),
                        ImageIO.read(new URL(jsonObject.get("avatar_url").getAsString())));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            DynamicImageUtil.bindTexture(jsonObject.get("login").getAsString());
            drawModalRectWithCustomSizedTexture(
                flipBoolean.get() ? scaledResolution.getScaledWidth() / 2
                    : scaledResolution.getScaledWidth() / 6, yHeight.get(), 0, 0, 20, 20, 20, 20);

            fontRenderer.drawStringWithShadow(jsonObject.get("login").getAsString(),
                flipBoolean.get() ? scaledResolution.getScaledWidth() / 2f + 30
                    : scaledResolution.getScaledWidth() / 6f + 30, yHeight.get(), 0xffffff);
            fontRenderer
                .drawStringWithShadow(jsonObject.get("contributions").getAsInt() + " contributions",
                    flipBoolean.get() ? scaledResolution.getScaledWidth() / 2f + 30
                        : scaledResolution.getScaledWidth() / 6f + 30, yHeight.get() + 10,
                    0xffffff);

            if (flipBoolean.get()) {
                yHeight.addAndGet(25);
            }

            flipBoolean.set(!flipBoolean.get());
        });

        GuiUtils.overlayBackground(0, logoHeight + 20, Minecraft.getMinecraft());
        GuiUtils.overlayBackground(scaledResolution.getScaledHeight() - 50,
            scaledResolution.getScaledHeight(), Minecraft.getMinecraft());

        this.mc.getTextureManager().bindTexture(Images.LOGO.getLocation());

        GlStateManager.color(0.0f, 0.0f, 0.0f, 0.2f);
        Gui.drawModalRectWithCustomSizedTexture(logoX + 2, logoY + 2, 0, 0, logoWidth, logoHeight,
            logoWidth, logoHeight);

        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        Gui.drawModalRectWithCustomSizedTexture(logoX, logoY, 0, 0, logoWidth, logoHeight,
            logoWidth, logoHeight);

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) {
        if (keyCode == Keyboard.KEY_ESCAPE) {
            Minecraft.getMinecraft().displayGuiScreen(null);
        }
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        if (button.id == 1) {
            Minecraft.getMinecraft().displayGuiScreen(null);
        }
    }
}
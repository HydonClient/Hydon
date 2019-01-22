package net.hydonclient.mixinsimp.client.gui;

import net.hydonclient.Hydon;
import net.hydonclient.event.EventBus;
import net.hydonclient.event.events.render.RenderGameOverlayEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.boss.BossStatus;
import net.minecraft.scoreboard.ScoreObjective;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

public class HydonGuiIngame {

    private GuiIngame guiIngame;

    public HydonGuiIngame(GuiIngame guiIngame) {
        this.guiIngame = guiIngame;
    }

    public void renderGameOverlay(float partialTicks, CallbackInfo ci) {
        EventBus.call(new RenderGameOverlayEvent(new ScaledResolution(Minecraft.getMinecraft()), partialTicks));
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
    }

    public void showCrosshair(CallbackInfoReturnable<Boolean> cir) {
        if (!Hydon.SETTINGS.thirdPersonCrosshair
                && Minecraft.getMinecraft().gameSettings.thirdPersonView > 0) {
            cir.setReturnValue(false);
        }
    }

    public void displayTitle(String title, String subTitle, int timeFadeIn, int displayTime,
                             int timeFadeOut, CallbackInfo ci) {
        if (Hydon.SETTINGS.disableTitles) {
            ci.cancel();
        }
    }

    public void renderBossHealth() {
        if (BossStatus.bossName != null && BossStatus.statusBarTime > 0
                && !Hydon.SETTINGS.disableBossBar) {
            --BossStatus.statusBarTime;
            ScaledResolution scaledresolution = new ScaledResolution(Minecraft.getMinecraft());
            int i = scaledresolution.getScaledWidth();
            int j = 182;
            int k = i / 2 - j / 2;
            int l = (int) (BossStatus.healthScale * (float) (j + 1));
            int i1 = 12;

            if (!Hydon.SETTINGS.disableBossFooter) {
                guiIngame.drawTexturedModalRect(k, i1, 0, 74, j, 5);
                guiIngame.drawTexturedModalRect(k, i1, 0, 74, j, 5);

                if (l > 0) {
                    guiIngame.drawTexturedModalRect(k, i1, 0, 79, l, 5);
                }
            }

            String s = BossStatus.bossName;
            guiIngame.getFontRenderer()
                    .drawStringWithShadow(s,
                            (float) (i / 2 - guiIngame.getFontRenderer().getStringWidth(s) / 2),
                            (float) (i1 - 10), 16777215);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            Minecraft.getMinecraft().getTextureManager().bindTexture(GuiIngame.icons);
        }
    }

    public void renderScoreboard(ScoreObjective objective, ScaledResolution scaledRes, CallbackInfo ci) {
        if (Hydon.SETTINGS.disableScoreboard) {
            ci.cancel();
        }
    }
}

package net.hydonclient.mixins.client.gui;

import net.hydonclient.Hydon;
import net.hydonclient.util.GuiUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiSlot;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.MathHelper;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(GuiSlot.class)
public abstract class MixinGuiSlot {

    @Shadow
    protected boolean field_178041_q;
    @Shadow
    protected int mouseX;
    @Shadow
    protected int mouseY;

    @Shadow
    protected abstract int getScrollBarX();

    @Shadow
    protected abstract void bindAmountScrolled();

    @Shadow
    @Final
    protected Minecraft mc;
    @Shadow
    protected int left;
    @Shadow
    protected int bottom;
    @Shadow
    protected float amountScrolled;
    @Shadow
    protected int right;
    @Shadow
    protected int top;
    @Shadow
    protected int width;

    @Shadow
    public abstract int getListWidth();

    @Shadow
    protected boolean hasListHeader;

    @Shadow
    protected abstract void drawListHeader(int p_148129_1_, int p_148129_2_,
                                           Tessellator p_148129_3_);

    @Shadow
    protected int height;

    @Shadow
    public abstract int func_148135_f();

    @Shadow
    protected abstract int getContentHeight();

    @Shadow
    protected abstract void func_148142_b(int p_148142_1_, int p_148142_2_);

    @Shadow
    protected abstract int getSize();

    @Shadow
    @Final
    protected int slotHeight;
    @Shadow
    protected int headerPadding;

    @Shadow
    protected abstract void func_178040_a(int p_178040_1_, int p_178040_2_,
                                          int p_178040_3_);

    @Shadow
    protected boolean showSelectionBox;

    @Shadow
    protected abstract boolean isSelected(int slotIndex);

    @Shadow
    protected abstract void drawSlot(int entryID, int p_180791_2_, int p_180791_3_,
                                     int p_180791_4_,
                                     int mouseXIn, int mouseYIn);

    /**
     * @author KodingKing
     */
    @Overwrite
    public void drawScreen(int mouseXIn, int mouseYIn, float p_148128_3_) {
        GlStateManager.enableAlpha();
        if (this.field_178041_q) {
            this.mouseX = mouseXIn;
            this.mouseY = mouseYIn;
            int i = this.getScrollBarX();
            int j = i + 6;
            this.bindAmountScrolled();
            GlStateManager.disableLighting();
            GlStateManager.disableFog();
            Tessellator tessellator = Tessellator.getInstance();
            WorldRenderer worldrenderer = tessellator.getWorldRenderer();

            ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());

            GlStateManager.disableAlpha();
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glDisable(GL11.GL_DEPTH_TEST);
            GL11.glDepthMask(false);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glDisable(GL11.GL_ALPHA_TEST);

            this.mc.getTextureManager().bindTexture(Hydon.SETTINGS.getCurrentBackground().getLocation());
            Gui.drawModalRectWithCustomSizedTexture(this.left, this.top, this.left, this.top,
                    this.right - this.left, this.bottom - this.top, sr.getScaledWidth(),
                    sr.getScaledHeight());
            GlStateManager.enableAlpha();

            int k = this.left + this.width / 2 - this.getListWidth() / 2 + 2;
            int l = this.top + 4 - (int) this.amountScrolled;

            if (this.hasListHeader) {
                this.drawListHeader(k, l, tessellator);
            }

            this.drawSelectionBox(k, l, mouseXIn, mouseYIn);
            GlStateManager.disableDepth();

            this.overlayBackground(0, this.top, 255, 255);
            this.overlayBackground(this.bottom, this.height, 255, 255);

            GlStateManager.shadeModel(7425);
            GlStateManager.disableTexture2D();
            int j1 = this.func_148135_f();

            if (j1 > 0) {
                int k1 =
                        (this.bottom - this.top) * (this.bottom - this.top) / this.getContentHeight();
                k1 = MathHelper.clamp_int(k1, 32, this.bottom - this.top - 8);
                int l1 = (int) this.amountScrolled * (this.bottom - this.top - k1) / j1 + this.top;

                if (l1 < this.top) {
                    l1 = this.top;
                }

                worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
                worldrenderer.pos((double) i, (double) this.bottom, 0.0D).tex(0.0D, 1.0D)
                        .color(0, 0, 0, 60).endVertex();
                worldrenderer.pos((double) j, (double) this.bottom, 0.0D).tex(1.0D, 1.0D)
                        .color(0, 0, 0, 60).endVertex();
                worldrenderer.pos((double) j, (double) this.top, 0.0D).tex(1.0D, 0.0D)
                        .color(0, 0, 0, 60).endVertex();
                worldrenderer.pos((double) i, (double) this.top, 0.0D).tex(0.0D, 0.0D)
                        .color(0, 0, 0, 60).endVertex();
                tessellator.draw();
                worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
                worldrenderer.pos((double) i, (double) (l1 + k1), 0.0D).tex(0.0D, 1.0D)
                        .color(128, 128, 128, 255).endVertex();
                worldrenderer.pos((double) j, (double) (l1 + k1), 0.0D).tex(1.0D, 1.0D)
                        .color(128, 128, 128, 255).endVertex();
                worldrenderer.pos((double) j, (double) l1, 0.0D).tex(1.0D, 0.0D)
                        .color(128, 128, 128, 255).endVertex();
                worldrenderer.pos((double) i, (double) l1, 0.0D).tex(0.0D, 0.0D)
                        .color(128, 128, 128, 255).endVertex();
                tessellator.draw();
                worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
                worldrenderer.pos((double) i, (double) (l1 + k1 - 1), 0.0D).tex(0.0D, 1.0D)
                        .color(192, 192, 192, 255).endVertex();
                worldrenderer.pos((double) (j - 1), (double) (l1 + k1 - 1), 0.0D).tex(1.0D, 1.0D)
                        .color(192, 192, 192, 255).endVertex();
                worldrenderer.pos((double) (j - 1), (double) l1, 0.0D).tex(1.0D, 0.0D)
                        .color(192, 192, 192, 255).endVertex();
                worldrenderer.pos((double) i, (double) l1, 0.0D).tex(0.0D, 0.0D)
                        .color(192, 192, 192, 255).endVertex();
                tessellator.draw();
            }

            GlStateManager.enableAlpha();
            this.func_148142_b(mouseXIn, mouseYIn);
            GlStateManager.enableTexture2D();
            GlStateManager.shadeModel(7424);
            GlStateManager.enableAlpha();
            GlStateManager.disableBlend();
        }
    }

    /**
     * @author KodingKing
     */
    @Overwrite
    protected void overlayBackground(int startY, int endY, int startAlpha, int endAlpha) {
        GuiUtils.overlayBackground(startY, endY, this.mc);
    }

    /**
     * @author Koding
     */
    @Overwrite
    protected void drawSelectionBox(int p_148120_1_, int p_148120_2_, int mouseXIn, int mouseYIn) {
        int i = this.getSize();
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();

        for (int j = 0; j < i; ++j) {
            int k = p_148120_2_ + j * this.slotHeight + this.headerPadding;
            int l = this.slotHeight - 4;

            if (k > this.bottom || k + l < this.top) {
                this.func_178040_a(j, p_148120_1_, k);
            }

            if (this.showSelectionBox && this.isSelected(j)) {
                int i1 = this.left + (this.width / 2 - this.getListWidth() / 2);
                int j1 = this.left + this.width / 2 + this.getListWidth() / 2;
                GlStateManager.color(0.0f, 0.0f, 0.0f, 20f / 255f);
                GlStateManager.disableTexture2D();
                worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
                worldrenderer.pos((double) i1, (double) (k + l + 2), 0.0D).tex(0.0D, 1.0D)
                        .color(0, 0, 0, 40).endVertex();
                worldrenderer.pos((double) j1, (double) (k + l + 2), 0.0D).tex(1.0D, 1.0D)
                        .color(0, 0, 0, 40).endVertex();
                worldrenderer.pos((double) j1, (double) (k - 2), 0.0D).tex(1.0D, 0.0D)
                        .color(0, 0, 0, 40).endVertex();
                worldrenderer.pos((double) i1, (double) (k - 2), 0.0D).tex(0.0D, 0.0D)
                        .color(0, 0, 0, 40).endVertex();
                tessellator.draw();
                GlStateManager.enableTexture2D();
            }

            this.drawSlot(j, p_148120_1_, k, l, mouseXIn, mouseYIn);
        }
    }
}

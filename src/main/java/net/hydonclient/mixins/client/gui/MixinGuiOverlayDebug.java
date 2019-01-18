package net.hydonclient.mixins.client.gui;

import net.hydonclient.Hydon;
import net.hydonclient.mods.oldanimations.OldAnimations;
import net.hydonclient.mods.oldanimations.config.OldAnimationsConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiOverlayDebug;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.chunk.RenderChunk;
import net.minecraft.entity.Entity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.world.chunk.Chunk;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(GuiOverlayDebug.class)
public abstract class MixinGuiOverlayDebug {

    @Shadow
    @Final
    private Minecraft mc;

    @Shadow
    protected abstract void renderDebugInfoLeft();

    @Shadow
    protected abstract void renderDebugInfoRight(ScaledResolution p_175239_1_);

    @Shadow
    protected abstract void renderLagometer();

    /**
     * @author Mojang
     * @reason 1.7 Debug Overlay
     */
    @Overwrite
    public void renderDebugInfo(ScaledResolution scaledResolutionIn) {
        this.mc.mcProfiler.startSection("debug");
        GlStateManager.pushMatrix();
        if (OldAnimationsConfig.OLD_DEBUG_MENU) {
            this.renderOldDebugInfoLeft(scaledResolutionIn);
            this.renderOldDebugInfoRight(scaledResolutionIn);
            GlStateManager.popMatrix();
            this.mc.mcProfiler.endSection();
            return;
        }
        this.renderDebugInfoLeft();
        this.renderDebugInfoRight(scaledResolutionIn);
        GlStateManager.popMatrix();
        if (this.mc.gameSettings.showLagometer) {
            this.renderLagometer();
        }
        this.mc.mcProfiler.endSection();
    }


    private void renderOldDebugInfoLeft(ScaledResolution scaledResolutionIn) {
        FontRenderer fontRenderer = mc.fontRendererObj;

        fontRenderer.drawStringWithShadow(
                "Hydon " + Hydon.VERSION + " (" + Minecraft.getDebugFPS() + " fps, " + RenderChunk.renderChunksUpdated + " chunk updates)",
                2, 2, 16777215);

        fontRenderer.drawStringWithShadow(
                mc.renderGlobal.getDebugInfoRenders(),
                2, 12, 16777215);

        fontRenderer.drawStringWithShadow(
                mc.renderGlobal.getDebugInfoEntities(),
                2, 22, 16777215);

        fontRenderer.drawStringWithShadow(
                "P: " + mc.effectRenderer.getStatistics() + ". T: " + mc.theWorld.getDebugLoadedEntities(),
                2, 32, 16777215);

        fontRenderer.drawStringWithShadow(
                mc.theWorld.getProviderName(),
                2, 42, 16777215);

        int posX = MathHelper.floor_double(mc.thePlayer.posX);
        int posY = MathHelper.floor_double(mc.thePlayer.posY);
        int posZ = MathHelper.floor_double(mc.thePlayer.posZ);

        /* X Coordinate renderer */
        fontRenderer.drawStringWithShadow(
                String.format("x: %.5f (%d) // c: %d (%d)",
                        this.mc.thePlayer.posX,
                        posX,
                        posX >> 4,
                        posX & 15),
                2, 64, 14737632);

        /* Y Coordinate renderer */
        fontRenderer.drawStringWithShadow(
                String.format("y: %.3f (feet pos, %.3f eyes pos)",
                        this.mc.thePlayer.getEntityBoundingBox().minY,
                        this.mc.thePlayer.posY),
                2, 72, 14737632);

        /* Z Coordinate renderer */
        fontRenderer.drawStringWithShadow(
                String.format("z: %.5f (%d) // c: %d (%d)",
                        this.mc.thePlayer.posZ,
                        posZ,
                        posZ >> 4,
                        posZ & 15),
                2, 80, 14737632);

        Entity entity = mc.getRenderViewEntity();
        EnumFacing direction = entity.getHorizontalFacing();
        int yaw = MathHelper.floor_double((double) (this.mc.thePlayer.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;

        fontRenderer.drawStringWithShadow(
                "f: " + yaw + " (" + direction + ") / " + MathHelper.wrapAngleTo180_float(this.mc.thePlayer.rotationYaw),
                2, 88, 14737632);

        if (mc.theWorld != null && !mc.theWorld.isAirBlock(new BlockPos(posX, posY, posZ))) {
            Chunk chunk = mc.theWorld.getChunkFromBlockCoords(new BlockPos(posX, posY, posZ));

            fontRenderer.drawStringWithShadow(
                    "lc: " + (chunk.getTopFilledSegment() + 15) +
                            " b: " + chunk.getBiome(new BlockPos(posX & 15, 64, posZ & 15),
                            mc.theWorld.getWorldChunkManager()).biomeName + " bl: " + chunk.getBlockLightOpacity(new BlockPos(posX & 15, posY, posZ & 15))
                            + " sl: " + chunk.getBlockLightOpacity(new BlockPos(posX & 15, posY, posZ & 15)) +
                            " rl: " + chunk.getBlockLightOpacity(new BlockPos(posX & 15, posY, posZ & 15)),
                    2, 96, 14737632);
        }

        if (mc.entityRenderer != null && mc.entityRenderer.isShaderActive()) {
            fontRenderer.drawStringWithShadow(
                    String.format("shader: %s",
                            this.mc.entityRenderer.getShaderGroup().getShaderGroupName()),
                    2, 104, 14737632);
        }
    }

    private void renderOldDebugInfoRight(ScaledResolution scaledResolutionIn) {
        FontRenderer fontRenderer = this.mc.fontRendererObj;

        int scaledWidth = scaledResolutionIn.getScaledWidth();

        long maxMemory = Runtime.getRuntime().maxMemory();
        long totalMemory = Runtime.getRuntime().totalMemory();
        long freeMemory = Runtime.getRuntime().freeMemory();
        long usedMemory = totalMemory - freeMemory;

        String memoryStr = "Used memory: " + usedMemory * 100L / maxMemory + "% (" + usedMemory / 1024L / 1024L + "MB) of " + maxMemory / 1024L / 1024L + "MB";
        fontRenderer.drawStringWithShadow(
                memoryStr,
                scaledWidth - fontRenderer.getStringWidth(memoryStr) - 2, 2, 14737632);

        memoryStr = "Allocated memory: " + totalMemory * 100L / maxMemory + "% (" + totalMemory / 1024L / 1024L + "MB)";
        fontRenderer.drawStringWithShadow(
                memoryStr,
                scaledWidth - fontRenderer.getStringWidth(memoryStr) - 2, 12, 14737632);
    }
}
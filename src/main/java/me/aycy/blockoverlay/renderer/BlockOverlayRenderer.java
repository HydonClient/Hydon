package me.aycy.blockoverlay.renderer;

import java.awt.Color;
import me.aycy.blockoverlay.BlockOverlay;
import me.aycy.blockoverlay.utils.BlockOverlayMode;
import me.aycy.blockoverlay.utils.ColorUtil;
import me.aycy.blockoverlay.utils.RenderUtil;
import net.hydonclient.Hydon;
import net.hydonclient.event.EventListener;
import net.hydonclient.event.events.render.DrawBlockHighlightEvent;
import net.hydonclient.event.events.render.RenderWorldLastEvent;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.WorldSettings;
import org.lwjgl.opengl.GL11;

public class BlockOverlayRenderer {

    @EventListener
    public void onDrawBlockHighlight(DrawBlockHighlightEvent event) {
        if (BlockOverlay.mc.thePlayer != null && BlockOverlay.mc.theWorld != null
            && Hydon.SETTINGS.getBoMode() != BlockOverlayMode.DEFAULT && (
            BlockOverlay.mc.playerController.getCurrentGameType() == WorldSettings.GameType.SURVIVAL
                || Minecraft.getMinecraft().playerController.getCurrentGameType()
                == WorldSettings.GameType.CREATIVE)) {
            event.setCancelled(true);

            if (Hydon.SETTINGS.getBoMode() != BlockOverlayMode.NONE) {
                this.drawBlockOverlay(event.getPartialTicks());
            }
        }
    }

    @EventListener
    public void onRenderWorldLast(RenderWorldLastEvent event) {
        if (BlockOverlay.mc.thePlayer != null && BlockOverlay.mc.theWorld != null
            && Hydon.SETTINGS.boPersistent && Hydon.SETTINGS.getBoMode() != BlockOverlayMode.NONE
            && Hydon.SETTINGS.getBoMode() != BlockOverlayMode.DEFAULT
            && (BlockOverlay.mc.playerController.getCurrentGameType()
            == WorldSettings.GameType.ADVENTURE ||
            BlockOverlay.mc.playerController.getCurrentGameType()
                == WorldSettings.GameType.SPECTATOR)) {
            this.drawBlockOverlay(event.partialTicks);
        }
    }

    private void drawBlockOverlay(float partialTicks) {
        MovingObjectPosition mouseOver = BlockOverlay.mc.objectMouseOver;

        if (mouseOver == null
            || mouseOver.typeOfHit != MovingObjectPosition.MovingObjectType.BLOCK) {
            return;
        }

        Block block = BlockOverlay.mc.theWorld.getBlockState(mouseOver.getBlockPos()).getBlock();

        if (block == null || block == Blocks.air || block == Blocks.barrier ||
            block == Blocks.water || block == Blocks.flowing_water || block == Blocks.lava
            || block == Blocks.flowing_lava) {
            return;
        }

        EntityPlayerSP player = BlockOverlay.mc.thePlayer;

        double x = player.lastTickPosX + (player.posX - player.lastTickPosX) * partialTicks;
        double y = player.lastTickPosY + (player.posY - player.lastTickPosY) * partialTicks;
        double z = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * partialTicks;

        double expandAmount = 0.0020000000949949026;
        boolean isOutlined = Hydon.SETTINGS.boLineWidth > 0.0f;

        AxisAlignedBB boundingBox = block
            .getSelectedBoundingBox(BlockOverlay.mc.theWorld, mouseOver.getBlockPos())
            .offset(-x, -y, -z).expand(expandAmount, expandAmount, expandAmount);

        Color color =
            Hydon.SETTINGS.isBoChroma() ? ColorUtil.getChroma() : ColorUtil.getConfigColor();

        GlStateManager.pushMatrix();
        GlStateManager.disableAlpha();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.disableTexture2D();
        GlStateManager.depthMask(false);

        if (Hydon.SETTINGS.boIgnoreDepth) {
            GlStateManager.disableDepth();
        }

        if (isOutlined) {
            GL11.glLineWidth((float) Hydon.SETTINGS.boLineWidth);
        }

        switch (Hydon.SETTINGS.getBoMode()) {
            case SIDE_OUTLINE: {
                RenderUtil.drawSide(boundingBox, mouseOver.sideHit, color, false, isOutlined);
                break;
            }

            case SIDE_FULL: {
                RenderUtil.drawSide(boundingBox, mouseOver.sideHit, color, true, true);
                break;
            }

            case OUTLINE: {
                if (isOutlined) {
                    RenderUtil.setGLColor(color);
                    RenderGlobal.drawSelectionBoundingBox(boundingBox);
                    break;
                }
                break;
            }

            case FULL: {
                RenderUtil.drawFilledBoundingBox(boundingBox, color, isOutlined);
                break;
            }
        }

        GL11.glLineWidth(2.0f);

        GlStateManager.enableDepth();
        GlStateManager.depthMask(true);
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.popMatrix();
    }
}

package me.aycy.blockoverlay.utils;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.EnumFacing;
import net.minecraft.client.renderer.RenderGlobal;

import java.awt.Color;

import net.minecraft.util.AxisAlignedBB;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.Tessellator;

public final class RenderUtil {
    private static Tessellator tessellator;
    private static WorldRenderer worldRenderer;

    public static void drawFilledBoundingBox(AxisAlignedBB box, Color color, boolean outlined) {
        setGLColor(color);
        drawFilledTopFace(box);
        drawFilledBottomFace(box);
        drawFilledNorthFace(box);
        drawFilledEastFace(box);
        drawFilledSouthFace(box);
        drawFilledWestFace(box);
        if (outlined) {
            setGLColor(color, 255);
            RenderGlobal.drawSelectionBoundingBox(box);
        }
    }

    public static void drawSide(AxisAlignedBB box, EnumFacing face, Color color, boolean filled, boolean outlined) {
        if (filled) {
            setGLColor(color);
        }

        switch (face) {
            case UP: {
                if (filled) {
                    drawFilledTopFace(box);
                }
                if (outlined) {
                    setGLColor(color, filled ? 255 : color.getAlpha());
                    drawOutlinedTopFace(box);
                    break;
                }
                break;
            }

            case DOWN: {
                if (filled) {
                    drawFilledBottomFace(box);
                }
                if (outlined) {
                    setGLColor(color, filled ? 255 : color.getAlpha());
                    drawOutlinedBottomFace(box);
                    break;
                }
                break;
            }

            case NORTH: {
                if (filled) {
                    drawFilledNorthFace(box);
                }
                if (outlined) {
                    setGLColor(color, filled ? 255 : color.getAlpha());
                    drawOutlinedNorthFace(box);
                    break;
                }
                break;
            }

            case EAST: {
                if (filled) {
                    drawFilledEastFace(box);
                }
                if (outlined) {
                    setGLColor(color, filled ? 255 : color.getAlpha());
                    drawOutlinedEastFace(box);
                    break;
                }
                break;
            }

            case SOUTH: {
                if (filled) {
                    drawFilledSouthFace(box);
                }
                if (outlined) {
                    setGLColor(color, filled ? 255 : color.getAlpha());
                    drawOutlinedSouthFace(box);
                    break;
                }
                break;
            }

            case WEST: {
                if (filled) {
                    drawFilledWestFace(box);
                }
                if (outlined) {
                    setGLColor(color, filled ? 255 : color.getAlpha());
                    drawOutlinedWestFace(box);
                    break;
                }
                break;
            }
        }
    }

    //TODO: fix how the rendering works on this

    private static void drawFilledTopFace(AxisAlignedBB box) {
        RenderUtil.worldRenderer.begin(7, DefaultVertexFormats.POSITION);
        RenderUtil.worldRenderer.pos(box.maxX, box.maxY, box.maxZ).endVertex();
        RenderUtil.worldRenderer.pos(box.minX, box.maxY, box.maxZ).endVertex();
        RenderUtil.worldRenderer.pos(box.minX, box.maxY, box.minZ).endVertex();
        RenderUtil.worldRenderer.pos(box.maxX, box.maxY, box.minZ).endVertex();
        RenderUtil.tessellator.draw();
    }

    private static void drawOutlinedTopFace(AxisAlignedBB box) {
        RenderUtil.worldRenderer.begin(3, DefaultVertexFormats.POSITION);
        RenderUtil.worldRenderer.pos(box.maxX, box.maxY, box.maxZ).endVertex();
        RenderUtil.worldRenderer.pos(box.minX, box.maxY, box.maxZ).endVertex();
        RenderUtil.worldRenderer.pos(box.minX, box.maxY, box.minZ).endVertex();
        RenderUtil.worldRenderer.pos(box.maxX, box.maxY, box.minZ).endVertex();
        RenderUtil.worldRenderer.pos(box.maxX, box.maxY, box.maxZ).endVertex();
        RenderUtil.tessellator.draw();
    }

    private static void drawFilledBottomFace(AxisAlignedBB box) {
        RenderUtil.worldRenderer.begin(7, DefaultVertexFormats.POSITION);
        RenderUtil.worldRenderer.pos(box.maxX, box.minY, box.minZ).endVertex();
        RenderUtil.worldRenderer.pos(box.minX, box.minY, box.minZ).endVertex();
        RenderUtil.worldRenderer.pos(box.minX, box.minY, box.maxZ).endVertex();
        RenderUtil.worldRenderer.pos(box.maxX, box.minY, box.maxZ).endVertex();
        RenderUtil.tessellator.draw();
    }

    private static void drawOutlinedBottomFace(AxisAlignedBB box) {
        RenderUtil.worldRenderer.begin(3, DefaultVertexFormats.POSITION);
        RenderUtil.worldRenderer.pos(box.maxX, box.minY, box.minZ).endVertex();
        RenderUtil.worldRenderer.pos(box.minX, box.minY, box.minZ).endVertex();
        RenderUtil.worldRenderer.pos(box.minX, box.minY, box.maxZ).endVertex();
        RenderUtil.worldRenderer.pos(box.maxX, box.minY, box.maxZ).endVertex();
        RenderUtil.worldRenderer.pos(box.maxX, box.minY, box.minZ).endVertex();
        RenderUtil.tessellator.draw();
    }

    private static void drawFilledNorthFace(AxisAlignedBB box) {
        RenderUtil.worldRenderer.begin(7, DefaultVertexFormats.POSITION);
        RenderUtil.worldRenderer.pos(box.maxX, box.maxY, box.minZ).endVertex();
        RenderUtil.worldRenderer.pos(box.minX, box.maxY, box.minZ).endVertex();
        RenderUtil.worldRenderer.pos(box.minX, box.minY, box.minZ).endVertex();
        RenderUtil.worldRenderer.pos(box.maxX, box.minY, box.minZ).endVertex();
        RenderUtil.tessellator.draw();
    }

    private static void drawOutlinedNorthFace(AxisAlignedBB box) {
        RenderUtil.worldRenderer.begin(3, DefaultVertexFormats.POSITION);
        RenderUtil.worldRenderer.pos(box.maxX, box.maxY, box.minZ).endVertex();
        RenderUtil.worldRenderer.pos(box.minX, box.maxY, box.minZ).endVertex();
        RenderUtil.worldRenderer.pos(box.minX, box.minY, box.minZ).endVertex();
        RenderUtil.worldRenderer.pos(box.maxX, box.minY, box.minZ).endVertex();
        RenderUtil.worldRenderer.pos(box.maxX, box.maxY, box.minZ).endVertex();
        RenderUtil.tessellator.draw();
    }

    private static void drawFilledEastFace(AxisAlignedBB box) {
        RenderUtil.worldRenderer.begin(7, DefaultVertexFormats.POSITION);
        RenderUtil.worldRenderer.pos(box.maxX, box.maxY, box.maxZ).endVertex();
        RenderUtil.worldRenderer.pos(box.maxX, box.maxY, box.minZ).endVertex();
        RenderUtil.worldRenderer.pos(box.maxX, box.minY, box.minZ).endVertex();
        RenderUtil.worldRenderer.pos(box.maxX, box.minY, box.maxZ).endVertex();
        RenderUtil.tessellator.draw();
    }

    private static void drawOutlinedEastFace(AxisAlignedBB box) {
        RenderUtil.worldRenderer.begin(3, DefaultVertexFormats.POSITION);
        RenderUtil.worldRenderer.pos(box.maxX, box.maxY, box.maxZ).endVertex();
        RenderUtil.worldRenderer.pos(box.maxX, box.maxY, box.minZ).endVertex();
        RenderUtil.worldRenderer.pos(box.maxX, box.minY, box.minZ).endVertex();
        RenderUtil.worldRenderer.pos(box.maxX, box.minY, box.maxZ).endVertex();
        RenderUtil.worldRenderer.pos(box.maxX, box.maxY, box.maxZ).endVertex();
        RenderUtil.tessellator.draw();
    }

    private static void drawFilledSouthFace(AxisAlignedBB box) {
        RenderUtil.worldRenderer.begin(7, DefaultVertexFormats.POSITION);
        RenderUtil.worldRenderer.pos(box.minX, box.maxY, box.maxZ).endVertex();
        RenderUtil.worldRenderer.pos(box.maxX, box.maxY, box.maxZ).endVertex();
        RenderUtil.worldRenderer.pos(box.maxX, box.minY, box.maxZ).endVertex();
        RenderUtil.worldRenderer.pos(box.minX, box.minY, box.maxZ).endVertex();
        RenderUtil.tessellator.draw();
    }

    private static void drawOutlinedSouthFace(AxisAlignedBB box) {
        RenderUtil.worldRenderer.begin(3, DefaultVertexFormats.POSITION);
        RenderUtil.worldRenderer.pos(box.minX, box.maxY, box.maxZ).endVertex();
        RenderUtil.worldRenderer.pos(box.maxX, box.maxY, box.maxZ).endVertex();
        RenderUtil.worldRenderer.pos(box.maxX, box.minY, box.maxZ).endVertex();
        RenderUtil.worldRenderer.pos(box.minX, box.minY, box.maxZ).endVertex();
        RenderUtil.worldRenderer.pos(box.minX, box.maxY, box.maxZ).endVertex();
        RenderUtil.tessellator.draw();
    }

    private static void drawFilledWestFace(AxisAlignedBB box) {
        RenderUtil.worldRenderer.begin(7, DefaultVertexFormats.POSITION);
        RenderUtil.worldRenderer.pos(box.minX, box.maxY, box.minZ).endVertex();
        RenderUtil.worldRenderer.pos(box.minX, box.maxY, box.maxZ).endVertex();
        RenderUtil.worldRenderer.pos(box.minX, box.minY, box.maxZ).endVertex();
        RenderUtil.worldRenderer.pos(box.minX, box.minY, box.minZ).endVertex();
        RenderUtil.tessellator.draw();
    }

    private static void drawOutlinedWestFace(AxisAlignedBB box) {
        RenderUtil.worldRenderer.begin(3, DefaultVertexFormats.POSITION);
        RenderUtil.worldRenderer.pos(box.minX, box.maxY, box.minZ).endVertex();
        RenderUtil.worldRenderer.pos(box.minX, box.maxY, box.maxZ).endVertex();
        RenderUtil.worldRenderer.pos(box.minX, box.minY, box.maxZ).endVertex();
        RenderUtil.worldRenderer.pos(box.minX, box.minY, box.minZ).endVertex();
        RenderUtil.worldRenderer.pos(box.minX, box.maxY, box.minZ).endVertex();
        RenderUtil.tessellator.draw();
    }

    public static void setGLColor(Color color) {
        setGLColor(color, color.getAlpha());
    }

    private static void setGLColor(Color color, int alpha) {
        GlStateManager.color(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, alpha / 255.0f);
    }

    static {
        tessellator = Tessellator.getInstance();
        worldRenderer = RenderUtil.tessellator.getWorldRenderer();
    }
}

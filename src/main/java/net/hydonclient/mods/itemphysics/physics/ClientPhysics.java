package net.hydonclient.mods.itemphysics.physics;

import net.hydonclient.Hydon;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.util.Random;

public class ClientPhysics {

    /**
     * Minecraft instance
     */
    public static Minecraft mc = Minecraft.getMinecraft();

    /**
     * The nanotick of the system, updated every tick
     */
    public static long tick;

    /**
     * The rotation speed
     */
    private static double rotation;

    /**
     * Generate a new rotation every item thrown
     */
    private static Random random = new Random();

    /**
     * Get the texture of the dropped item
     *
     * @return the texture for the item
     */
    private static ResourceLocation getEntityTexture() {
        return TextureMap.locationBlocksTexture;
    }

    /**
     * Render the item when it's dropped
     *
     * @param entity       the item
     * @param x            the x position of the item
     * @param y            the y position of the item
     * @param z            the z position of the item
     * @param entityYaw    the rotation of the item
     * @param partialTicks the world tick
     */
    public static void doRender(Entity entity,
                                double x, double y, double z,
                                float entityYaw, float partialTicks) {
        rotation = (double) (System.nanoTime() - tick) /
                2500000 * Hydon.SETTINGS.rotateSpeed;

        if (!mc.inGameHasFocus) {
            rotation = 0;
        }

        EntityItem item = ((EntityItem) entity);

        ItemStack stack = item.getEntityItem();

        int itemID;

        if (stack != null && stack.getItem() != null) {
            itemID = Item.getIdFromItem(stack.getItem()) + stack.getMetadata();
        } else {
            itemID = 187;
        }

        random.setSeed((long) itemID);

        mc.getTextureManager().bindTexture(
                getEntityTexture());

        mc.getTextureManager().getTexture(
                getEntityTexture()).
                setBlurMipmap(false, false);

        GlStateManager.
                enableRescaleNormal();
        GlStateManager.
                alphaFunc(516, 0.1F);
        GlStateManager.
                enableBlend();

        RenderHelper.
                enableStandardItemLighting();

        GlStateManager.
                tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.
                pushMatrix();

        IBakedModel model = mc.getRenderItem().getItemModelMesher().getItemModel(stack);

        boolean is3D = model.isGui3d();
        boolean is3D1 = model.isGui3d();
        int items = getModelCount(stack);

        GlStateManager.
                translate((float) x, (float) y, (float) z);

        if (is3D) {
            GlStateManager.
                    scale(0.5F, 0.5F, 0.5F);
        }

        GL11.
                glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
        GL11.
                glRotatef(item.rotationYaw, 0.0F, 0.0F, 1.0F);

        if (is3D) {
            GlStateManager.
                    translate(0, 0, -0.08);
        } else {
            GlStateManager.
                    translate(0, 0, -0.0f);
        }

        if (is3D || mc.getRenderManager().options != null) {
            if (is3D) {
                if (!item.onGround) {
                    double rotation = ClientPhysics.rotation * 2;

                    item.rotationPitch += rotation;
                }
            } else {


                if (!Double.isNaN(item.posX) && !Double.isNaN(item.posY) && !Double.isNaN(item.posZ)
                        && item.worldObj != null) {
                    if (item.onGround) {
                        item.rotationPitch = 0;
                    } else {
                        double rotation = ClientPhysics.rotation * 2;

                        item.rotationPitch += rotation;
                    }
                }
            }

            GlStateManager.
                    rotate(item.rotationPitch, 1, 0, 0.0F);
        }

        GlStateManager.
                color(1.0F, 1.0F, 1.0F, 1.0F);

        for (int k = 0; k < items; ++k) {
            if (is3D1) {
                GlStateManager.
                        pushMatrix();

                if (k > 0) {

                    float xRotation = (random.nextFloat() * 2.0F - 1.0F) * 0.15F;
                    float yRotation = (random.nextFloat() * 2.0F - 1.0F) * 0.15F;
                    float zRotation = (random.nextFloat() * 2.0F - 1.0F) * 0.15F;

                    GlStateManager.
                            translate(xRotation, yRotation, zRotation);
                }

                mc.getRenderItem().
                        renderItem(stack, model);

                GlStateManager.
                        popMatrix();
            } else {
                GlStateManager.
                        pushMatrix();

                mc.getRenderItem().
                        renderItem(stack, model);

                GlStateManager.
                        popMatrix();
                GlStateManager.
                        translate(0.0F, 0.0F, 0.05375F);
            }
        }

        GlStateManager.
                popMatrix();
        GlStateManager.
                disableRescaleNormal();
        GlStateManager.
                disableBlend();

        mc.getTextureManager().
                bindTexture(getEntityTexture());
        mc.getTextureManager().
                getTexture(getEntityTexture()).
                restoreLastBlurMipmap();
    }

    /**
     * Get the item and the amount of items surrounding it in an area
     * Combine them when there's enough
     *
     * @param stack the item
     * @return the amount of merged stacks
     */
    private static int getModelCount(ItemStack stack) {
        int mergedStack = 1;

        if (stack.stackSize > 48) {
            mergedStack = 5;
        } else if (stack.stackSize > 32) {
            mergedStack = 4;
        } else if (stack.stackSize > 16) {
            mergedStack = 3;
        } else if (stack.stackSize > 1) {
            mergedStack = 2;
        }

        return mergedStack;
    }
}

package net.hydonclient.mixinsimp.client.renderer;

import net.hydonclient.event.EventBus;
import net.hydonclient.event.events.render.DrawBlockHighlightEvent;
import net.hydonclient.managers.impl.keybind.impl.Perspective;
import net.hydonclient.mixins.client.renderer.IMixinEntityRenderer;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBed;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import org.lwjgl.opengl.Display;

public class HydonEntityRenderer {

    private EntityRenderer entityRenderer;

    static boolean isCancelBox;

    public HydonEntityRenderer(EntityRenderer entityRenderer) {
        this.entityRenderer = entityRenderer;
    }

    public void orientCamera(float partialTicks, float thirdPersonDistanceTemp, float thirdPersonDistance, boolean cloudFog, Minecraft mc){
        Entity entity = mc.getRenderViewEntity();

        float eyeHeight = entity.getEyeHeight();

        double x = entity.prevPosX + (entity.posX - entity.prevPosX) * partialTicks;
        double y = entity.prevPosY + (entity.posY - entity.prevPosY) * partialTicks + eyeHeight;
        double z = entity.prevPosZ + (entity.posZ - entity.prevPosZ) * partialTicks;

        if (entity instanceof EntityLivingBase && ((EntityLivingBase) entity).isPlayerSleeping()) {
            ++eyeHeight;

            GlStateManager.translate(0.0f, 0.3f, 0.0f);
            if (!mc.gameSettings.debugCamEnable) {
                final BlockPos blockpos = new BlockPos(entity);
                final IBlockState state = mc.theWorld.getBlockState(blockpos);
                Block block = state.getBlock();

                if (block == Blocks.bed) {
                    int rotation = state.getValue(BlockBed.FACING).getHorizontalIndex();
                    GlStateManager.rotate((float) (rotation * 90), 0.0F, 1.0F, 0.0F);
                }

                GlStateManager.rotate(entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * partialTicks + 180.0f, 0.0f, -1.0f, 0.0f);
                GlStateManager.rotate(entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks, -1.0f, 0.0f, 0.0f);
            }
        } else if (mc.gameSettings.thirdPersonView > 0) {
            double playerFOV = thirdPersonDistanceTemp + (thirdPersonDistance - thirdPersonDistanceTemp) * partialTicks;

            if (mc.gameSettings.debugCamEnable) {
                GlStateManager.translate(0.0f, 0.0f, (float) (-playerFOV));
            } else {
                float yaw = entity.rotationYaw;
                float pitch = entity.rotationPitch;

                if (Perspective.toggled) {
                    yaw = Perspective.modifiedYaw;
                    pitch = Perspective.modifiedPitch;
                }

                if (mc.gameSettings.thirdPersonView == 2) {
                    pitch += 180.0f;
                }

                final double camX = -MathHelper.sin(yaw / 180.0f * 3.1415927f) * MathHelper.cos(pitch / 180.0f * 3.1415927f) * playerFOV;
                final double camY = MathHelper.cos(yaw / 180.0f * 3.1415927f) * MathHelper.cos(pitch / 180.0f * 3.1415927f) * playerFOV;
                final double camZ = -MathHelper.sin(pitch / 180.0f * 3.1415927f) * playerFOV;

                for (int i = 0; i < 8; ++i) {

                    float f4 = (i & 0x1) * 2 - 1;
                    float f5 = (i >> 1 & 0x1) * 2 - 1;
                    float f6 = (i >> 2 & 0x1) * 2 - 1;

                    f4 *= 0.1f;
                    f5 *= 0.1f;
                    f6 *= 0.1f;

                    final MovingObjectPosition movingobjectposition = mc.theWorld.rayTraceBlocks(new Vec3(x + f4, y + f5, z + f6), new Vec3(x - camX + f4 + f6, y - camZ + f5, z - camY + f6));

                    if (movingobjectposition != null) {
                        double blockDistance = movingobjectposition.hitVec.distanceTo(new Vec3(x, y, z));

                        if (blockDistance < playerFOV) {
                            playerFOV = blockDistance;
                        }
                    }
                }

                if (mc.gameSettings.thirdPersonView == 2) {
                    GlStateManager.rotate(180.0f, 0.0f, 1.0f, 0.0f);
                }

                if (Perspective.toggled) {
                    GlStateManager.rotate(Perspective.modifiedPitch - pitch, 1.0f, 0.0f, 0.0f);
                    GlStateManager.rotate(Perspective.modifiedYaw - yaw, 0.0f, 1.0f, 0.0f);

                    GlStateManager.translate(0.0f, 0.0f, (float) (-playerFOV));

                    GlStateManager.rotate(yaw - Perspective.modifiedYaw, 0.0f, 1.0f, 0.0f);
                    GlStateManager.rotate(pitch - Perspective.modifiedPitch, 1.0f, 0.0f, 0.0f);
                } else {
                    GlStateManager.rotate(entity.rotationPitch - pitch, 1.0f, 0.0f, 0.0f);
                    GlStateManager.rotate(entity.rotationYaw - yaw, 0.0f, 1.0f, 0.0f);

                    GlStateManager.translate(0.0f, 0.0f, (float) (-playerFOV));

                    GlStateManager.rotate(yaw - entity.rotationYaw, 0.0f, 1.0f, 0.0f);
                    GlStateManager.rotate(pitch - entity.rotationPitch, 1.0f, 0.0f, 0.0f);
                }
            }
        } else {
            GlStateManager.translate(0.0f, 0.0f, -0.1f);
        }
        if (!mc.gameSettings.debugCamEnable) {
            float yaw = entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * partialTicks + 180.0f;
            final float pitch = entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks;

            final float roll = 0.0f;

            if (entity instanceof EntityAnimal) {
                final EntityAnimal entityanimal = (EntityAnimal) entity;
                yaw = entityanimal.prevRotationYawHead + (entityanimal.rotationYawHead - entityanimal.prevRotationYawHead) * partialTicks + 180.0f;
            }

            if (Perspective.toggled) {
                GlStateManager.rotate(roll, 0.0f, 0.0f, 1.0f);
                GlStateManager.rotate(Perspective.modifiedPitch, 1.0f, 0.0f, 0.0f);
                GlStateManager.rotate(Perspective.modifiedYaw + 180.0f, 0.0f, 1.0f, 0.0f);

            } else {
                GlStateManager.rotate(roll, 0.0f, 0.0f, 1.0f);
                GlStateManager.rotate(pitch, 1.0f, 0.0f, 0.0f);
                GlStateManager.rotate(yaw, 0.0f, 1.0f, 0.0f);
            }
        }

        GlStateManager.translate(0.0f, -eyeHeight, 0.0f);
        x = entity.prevPosX + (entity.posX - entity.prevPosX) * partialTicks;
        y = entity.prevPosY + (entity.posY - entity.prevPosY) * partialTicks + eyeHeight;
        z = entity.prevPosZ + (entity.posZ - entity.prevPosZ) * partialTicks;

        ((IMixinEntityRenderer) entityRenderer).setCloudFog(mc.renderGlobal.hasCloudFog(x, y, z, partialTicks));

    }

    public void updateCameraAndRender() {
        boolean bool = Display.isActive();
        if (Minecraft.getMinecraft().inGameHasFocus && bool) {
            if (Perspective.toggled && Minecraft.getMinecraft().gameSettings.thirdPersonView != 1) {
                Perspective.disable();
            }

            if (Perspective.toggled) {
                Minecraft.getMinecraft().mouseHelper.mouseXYChange();

                float f = Minecraft.getMinecraft().gameSettings.mouseSensitivity * 0.6F + 0.2F;

                float f1 = f * f * f * 8.0F;
                float f2 = (float) Minecraft.getMinecraft().mouseHelper.deltaX * f1;
                float f3 = (float) Minecraft.getMinecraft().mouseHelper.deltaY * f1;

                Perspective.modifiedYaw += f2 / 8.0F;
                Perspective.modifiedPitch += f3 / 8.0F;

                if (Math.abs(Perspective.modifiedPitch) > 90.0F) {
                    if (Perspective.modifiedPitch > 0.0F) {
                        Perspective.modifiedPitch = 90.0F;
                    } else {
                        Perspective.modifiedPitch = -90.0F;
                    }
                }
            }
        }
    }

    public void renderWorldPass(float partialTicks, Minecraft mc) {
        DrawBlockHighlightEvent event = new DrawBlockHighlightEvent(((EntityPlayer) mc.getRenderViewEntity()), mc.objectMouseOver, partialTicks);

        EventBus.call(event);

        if (event.isCancelled()) {
            isCancelBox = true;
        }
    }
}

package net.hydonclient.mixins.client.renderer;

import net.hydonclient.managers.impl.keybind.impl.Perspective;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBed;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.shader.ShaderGroup;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(EntityRenderer.class)
public class MixinEntityRenderer {

    @Shadow
    public static boolean anaglyphEnable;
    @Shadow
    private float thirdPersonDistance;
    @Shadow
    private float thirdPersonDistanceTemp;
    @Shadow
    private boolean cloudFog;
    @Shadow
    private long prevFrameTime;
    @Shadow
    private float smoothCamYaw;
    @Shadow
    private float smoothCamPitch;
    @Shadow
    private float smoothCamFilterX;
    @Shadow
    private float smoothCamFilterY;
    @Shadow
    private float smoothCamPartialTicks;
    @Shadow
    private ShaderGroup theShaderGroup;
    @Shadow
    private boolean useShader;
    @Shadow
    private long renderEndNanoTime;

    @Shadow
    public void setupOverlayRendering() {
    }

    @Shadow
    public void renderWorld(float partialTicks, long finishTimeNano) {
    }

    /**
     * @author Canalex
     * @reason 360 Perspective Degree mod
     */
    @Overwrite
    private void orientCamera(float partialTicks) {
        Entity entity = Minecraft.getMinecraft().getRenderViewEntity();
        float eyeHeight = entity.getEyeHeight();

        double x = entity.prevPosX + (entity.posX - entity.prevPosX) * (double) partialTicks;
        double y = entity.prevPosY + (entity.posY - entity.prevPosY) * (double) partialTicks + (double) eyeHeight;
        double z = entity.prevPosZ + (entity.posZ - entity.prevPosZ) * (double) partialTicks;

        if (entity instanceof EntityLivingBase && ((EntityLivingBase) entity).isPlayerSleeping()) {
            eyeHeight = (float) ((double) eyeHeight + 1.0D);
            GlStateManager.translate(0.0F, 0.3F, 0.0F);

            if (!Minecraft.getMinecraft().gameSettings.debugCamEnable) {
                BlockPos blockPos = new BlockPos(entity);
                IBlockState state = Minecraft.getMinecraft().theWorld.getBlockState(blockPos);
                Block block = state.getBlock();

                if (block == Blocks.bed) {
                    int facing = state.getValue(BlockBed.FACING).getHorizontalIndex();
                    GlStateManager.rotate((float) (facing * 90), 0.0F, 1.0F, 0.0F);
                }

                GlStateManager.rotate(entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * partialTicks + 180.0F, 0.0F, -1.0F, 0.0F);
                GlStateManager.rotate(entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks, -1.0F, 0.0F, 0.0F);
            }
        } else if (Minecraft.getMinecraft().gameSettings.thirdPersonView > 0) {
            double thirdPersonFOV = (double) (this.thirdPersonDistanceTemp + (this.thirdPersonDistance - this.thirdPersonDistanceTemp) * partialTicks);

            if (Minecraft.getMinecraft().gameSettings.debugCamEnable) {
                GlStateManager.translate(0.0F, 0.0F, (float) (-thirdPersonFOV));
            } else {
                float yaw = entity.rotationYaw;
                float pitch = entity.rotationPitch;

                if (Perspective.toggled) {
                    yaw = Perspective.modifiedYaw;
                    pitch = Perspective.modifiedPitch;
                }

                if (Minecraft.getMinecraft().gameSettings.thirdPersonView == 2) {
                    pitch += 180.0F;
                }

                double camX = (double) (-MathHelper.sin(yaw / 180.0F * (float) Math.PI) * MathHelper.cos(pitch / 180.0F * (float) Math.PI)) * thirdPersonFOV;
                double camY = (double) (MathHelper.cos(yaw / 180.0F * (float) Math.PI) * MathHelper.cos(pitch / 180.0F * (float) Math.PI)) * thirdPersonFOV;
                double camZ = (double) (-MathHelper.sin(pitch / 180.0F * (float) Math.PI)) * thirdPersonFOV;

                for (int i = 0; i < 8; ++i) {
                    float ok = (float) ((i & 1) * 2 - 1);
                    float ok2 = (float) ((i >> 1 & 1) * 2 - 1);
                    float ok3 = (float) ((i >> 2 & 1) * 2 - 1);
                    ok = ok * 0.1F;
                    ok2 = ok2 * 0.1F;
                    ok3 = ok3 * 0.1F;
                    MovingObjectPosition movingobjectposition = Minecraft.getMinecraft().theWorld.rayTraceBlocks(new Vec3(x + (double) ok, y + (double) ok2, z + (double) ok3), new Vec3(x - camX + (double) ok + (double) ok3, y - camZ + (double) ok2, z - camY + (double) ok3));

                    if (movingobjectposition != null) {
                        double distanceToBlock = movingobjectposition.hitVec.distanceTo(new Vec3(x, y, z));

                        if (distanceToBlock < thirdPersonFOV) {
                            thirdPersonFOV = distanceToBlock;
                        }
                    }
                }

                if (Minecraft.getMinecraft().gameSettings.thirdPersonView == 2) {
                    GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
                }

                if (Perspective.toggled) {
                    GlStateManager.rotate(Perspective.modifiedPitch - pitch, 1.0F, 0.0F, 0.0F);
                    GlStateManager.rotate(Perspective.modifiedYaw - yaw, 0.0F, 1.0F, 0.0F);
                    GlStateManager.translate(0.0F, 0.0F, (float) (-thirdPersonFOV));
                    GlStateManager.rotate(yaw - Perspective.modifiedYaw, 0.0F, 1.0F, 0.0F);
                    GlStateManager.rotate(pitch - Perspective.modifiedPitch, 1.0F, 0.0F, 0.0F);
                } else {
                    GlStateManager.rotate(entity.rotationPitch - pitch, 1.0F, 0.0F, 0.0F);
                    GlStateManager.rotate(entity.rotationYaw - yaw, 0.0F, 1.0F, 0.0F);
                    GlStateManager.translate(0.0F, 0.0F, (float) (-thirdPersonFOV));
                    GlStateManager.rotate(yaw - entity.rotationYaw, 0.0F, 1.0F, 0.0F);
                    GlStateManager.rotate(pitch - entity.rotationPitch, 1.0F, 0.0F, 0.0F);
                }
            }
        } else {
            GlStateManager.translate(0.0F, 0.0F, -0.1F);
        }

        if (!Minecraft.getMinecraft().gameSettings.debugCamEnable) {
            if (Perspective.toggled) {
                GlStateManager.rotate(Perspective.modifiedPitch, 1.0F, 0.0F, 0.0F);
                GlStateManager.rotate(Perspective.modifiedYaw + 180.0F, 0.0F, 1.0F, 0.0F);
            } else {
                GlStateManager.rotate(entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks, 1.0F, 0.0F, 0.0F);
            }

            if (entity instanceof EntityAnimal) {
                EntityAnimal entityanimal = (EntityAnimal) entity;
                GlStateManager.rotate(entityanimal.prevRotationYawHead + (entityanimal.rotationYawHead - entityanimal.prevRotationYawHead) * partialTicks + 180.0F, 0.0F, 1.0F, 0.0F);
            } else {
                GlStateManager.rotate(entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * partialTicks + 180.0F, 0.0F, 1.0F, 0.0F);
            }
        }

        GlStateManager.translate(0.0F, -eyeHeight, 0.0F);
        x = entity.prevPosX + (entity.posX - entity.prevPosX) * (double) partialTicks;
        y = entity.prevPosY + (entity.posY - entity.prevPosY) * (double) partialTicks + (double) eyeHeight;
        z = entity.prevPosZ + (entity.posZ - entity.prevPosZ) * (double) partialTicks;
        this.cloudFog = Minecraft.getMinecraft().renderGlobal.hasCloudFog(x, y, z, partialTicks);
    }

    /**
     * @author Canalex
     * @reason 360 Perspective Degree mod
     */
    @Overwrite
    public void updateCameraAndRender(float p_181560_1_, long p_181560_2_) {
        boolean flag = Display.isActive();

        if (!flag && Minecraft.getMinecraft().gameSettings.pauseOnLostFocus && (!Minecraft.getMinecraft().gameSettings.touchscreen || !Mouse.isButtonDown(1))) {
            if (Minecraft.getSystemTime() - this.prevFrameTime > 500L) {
                Minecraft.getMinecraft().displayInGameMenu();
            }
        } else {
            this.prevFrameTime = Minecraft.getSystemTime();
        }

        Minecraft.getMinecraft().mcProfiler.startSection("mouse");

        if (flag && Minecraft.isRunningOnMac && Minecraft.getMinecraft().inGameHasFocus && !Mouse.isInsideWindow()) {
            Mouse.setGrabbed(false);
            Mouse.setCursorPosition(Display.getWidth() / 2, Display.getHeight() / 2);
            Mouse.setGrabbed(true);
        }

        if (Minecraft.getMinecraft().inGameHasFocus && flag) {
            if (Perspective.toggled && Minecraft.getMinecraft().gameSettings.thirdPersonView != 1) {
                Perspective.disable();
            }

            Minecraft.getMinecraft().mouseHelper.mouseXYChange();
            float f = Minecraft.getMinecraft().gameSettings.mouseSensitivity * 0.6F + 0.2F;
            float f1 = f * f * f * 8.0F;
            float f2 = (float) Minecraft.getMinecraft().mouseHelper.deltaX * f1;
            float f3 = (float) Minecraft.getMinecraft().mouseHelper.deltaY * f1;
            int i = 1;

            if (Minecraft.getMinecraft().gameSettings.invertMouse) {
                i = -1;
            }

            if (Minecraft.getMinecraft().gameSettings.smoothCamera) {
                this.smoothCamYaw += f2;
                this.smoothCamPitch += f3;
                float f4 = p_181560_1_ - this.smoothCamPartialTicks;
                this.smoothCamPartialTicks = p_181560_1_;
                f2 = this.smoothCamFilterX * f4;
                f3 = this.smoothCamFilterY * f4;

                if (Perspective.toggled ) {

                    // Modifying pitch and yaw values.
                    Perspective.modifiedYaw += f2 / 8.0F;
                    Perspective.modifiedPitch += f3 / 8.0F;


                    // Range check.
                    if (Math.abs(Perspective.modifiedPitch) > 90.0F) {
                        if (Perspective.modifiedPitch > 0.0F) {
                            Perspective.modifiedPitch = 90.0F;
                        } else {
                            Perspective.modifiedPitch = -90.0F;
                        }
                    }
                } else {
                    Minecraft.getMinecraft().thePlayer.setAngles(f2, f3 * (float) i);
                }
            } else if (Perspective.toggled) {
                Perspective.modifiedYaw += f2 / 8.0F;
                Perspective.modifiedPitch += f3 / 8.0F;

                if (Math.abs(Perspective.modifiedPitch) > 90.0F) {
                    if (Perspective.modifiedPitch > 0.0F) {
                        Perspective.modifiedPitch = 90.0F;
                    } else {
                        Perspective.modifiedPitch = -90.0F;
                    }
                }
            } else {
                this.smoothCamYaw = 0.0F;
                this.smoothCamPitch = 0.0F;
                Minecraft.getMinecraft().thePlayer.setAngles(f2, f3 * (float) i);
            }
        }

        Minecraft.getMinecraft().mcProfiler.endSection();

        if (!Minecraft.getMinecraft().skipRenderWorld) {
            anaglyphEnable = Minecraft.getMinecraft().gameSettings.anaglyph;
            final ScaledResolution scaledresolution = new ScaledResolution(Minecraft.getMinecraft());
            int i1 = scaledresolution.getScaledWidth();
            int j1 = scaledresolution.getScaledHeight();
            final int k1 = Mouse.getX() * i1 / Minecraft.getMinecraft().displayWidth;
            final int l1 = j1 - Mouse.getY() * j1 / Minecraft.getMinecraft().displayHeight - 1;
            int i2 = Minecraft.getMinecraft().gameSettings.limitFramerate;

            if (Minecraft.getMinecraft().theWorld != null) {
                Minecraft.getMinecraft().mcProfiler.startSection("level");
                int j = Math.min(Minecraft.getDebugFPS(), i2);
                j = Math.max(j, 60);
                long k = System.nanoTime() - p_181560_2_;
                long l = Math.max((long) (1000000000 / j / 4) - k, 0L);
                this.renderWorld(p_181560_1_, System.nanoTime() + l);

                if (OpenGlHelper.shadersSupported) {
                    Minecraft.getMinecraft().renderGlobal.renderEntityOutlineFramebuffer();

                    if (this.theShaderGroup != null && this.useShader) {
                        GlStateManager.matrixMode(5890);
                        GlStateManager.pushMatrix();
                        GlStateManager.loadIdentity();
                        this.theShaderGroup.loadShaderGroup(p_181560_1_);
                        GlStateManager.popMatrix();
                    }

                    Minecraft.getMinecraft().getFramebuffer().bindFramebuffer(true);
                }

                this.renderEndNanoTime = System.nanoTime();
                Minecraft.getMinecraft().mcProfiler.endStartSection("gui");

                if (!Minecraft.getMinecraft().gameSettings.hideGUI || Minecraft.getMinecraft().currentScreen != null) {
                    GlStateManager.alphaFunc(516, 0.1F);
                    Minecraft.getMinecraft().ingameGUI.renderGameOverlay(p_181560_1_);
                }

                Minecraft.getMinecraft().mcProfiler.endSection();
            } else {
                GlStateManager.viewport(0, 0, Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight);
                GlStateManager.matrixMode(5889);
                GlStateManager.loadIdentity();
                GlStateManager.matrixMode(5888);
                GlStateManager.loadIdentity();
                this.setupOverlayRendering();
                this.renderEndNanoTime = System.nanoTime();
            }

            if (Minecraft.getMinecraft().currentScreen != null) {
                GlStateManager.clear(256);

                try {
                    Minecraft.getMinecraft().currentScreen.drawScreen(k1, l1, p_181560_1_);
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }
        }
    }
}

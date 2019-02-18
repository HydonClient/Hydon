package net.hydonclient.mixins.client.renderer;

import net.hydonclient.Hydon;
import net.hydonclient.util.patches.IPatchedTextureAtlasSprite;
import net.minecraft.client.Minecraft;

import static net.minecraft.client.renderer.GlStateManager.*;

import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.MathHelper;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemRenderer.class)
public class MixinItemRenderer {

    @Shadow
    @Final
    private Minecraft mc;

    @Shadow private float prevEquippedProgress;

    @Shadow private float equippedProgress;

    @Shadow private ItemStack itemToRender;

    @Inject(method = "renderFireInFirstPerson", at = @At("HEAD"))
    private void beforeRenderFireInFirstPerson(CallbackInfo ci) {
        ((IPatchedTextureAtlasSprite) mc.getTextureMapBlocks().getAtlasSprite("minecraft:blocks/fire_layer_1")).markNeedsAnimationUpdate();
    }

    /**
     * @author Mojang
     * @reason 1.7 animations
     */
    @Overwrite
    public void transformFirstPersonItem(float equipProgress, float swingProgress) {
        if (Hydon.SETTINGS.oldBow && mc != null && mc.thePlayer != null &&
                mc.thePlayer.getItemInUse() != null && mc.thePlayer.getItemInUse().getItem() != null &&
                Item.getIdFromItem(mc.thePlayer.getItemInUse().getItem()) == 261) {
            translate(0.0f, 0.05f, 0.04f);
        }

        if (Hydon.SETTINGS.oldRod && mc != null && mc.thePlayer != null &&
                mc.thePlayer.getItemInUse() != null && mc.thePlayer.getItemInUse().getItem() != null &&
                Item.getIdFromItem(mc.thePlayer.getItemInUse().getItem()) == 346) {
            translate(0.08f, -0.027f, -0.33f);
            scale(0.93f, 1.0f, 1.0f);
        }

        if (Hydon.SETTINGS.oldBlockHit && mc != null && mc.thePlayer != null &&
                mc.thePlayer.isSwingInProgress && mc.thePlayer.getCurrentEquippedItem() != null &&
                !mc.thePlayer.isEating() && !mc.thePlayer.isBlocking()) {
            scale(0.85f, 0.85f, 0.85f);
            translate(-0.078f, 0.003f, 0.05f);
        }

        translate(0.56F, -0.52F, -0.71999997F);
        translate(0.0F, equipProgress * -0.6F, 0.0F);
        rotate(45.0F, 0.0F, 1.0F, 0.0F);
        float f = MathHelper.sin(swingProgress * swingProgress * (float) Math.PI);
        float f1 = MathHelper.sin(MathHelper.sqrt_float(swingProgress) * (float) Math.PI);
        rotate(f * -20.0F, 0.0F, 1.0F, 0.0F);
        rotate(f1 * -20.0F, 0.0F, 0.0F, 1.0F);
        rotate(f1 * -80.0F, 1.0F, 0.0F, 0.0F);
        scale(0.4F, 0.4F, 0.4F);
    }

    /**
     * @author Mojang
     * @reason 1.7 animations
     */
    @Overwrite
    public void renderItemInFirstPerson(float partialTicks) {
        float progress = 1.0F - (prevEquippedProgress + (equippedProgress - prevEquippedProgress) * partialTicks);
        AbstractClientPlayer player = mc.thePlayer;
        float swing = player.getSwingProgress(partialTicks);
        float prevpitch = player.prevRotationPitch + (player.rotationPitch - player.prevRotationPitch) * partialTicks;
        float prevyaw = player.prevRotationYaw + (player.rotationYaw - player.prevRotationYaw) * partialTicks;
        ((IMixinItemRenderer) this).callRotateArroundXAndY(prevpitch, prevyaw);
        ((IMixinItemRenderer) this).callSetLightMapFromPlayer(player);
        ((IMixinItemRenderer) this).callRotateWithPlayerRotations((EntityPlayerSP) player, partialTicks);
        enableRescaleNormal();
        pushMatrix();

        if (itemToRender != null) {
            if (itemToRender.getItem() == Items.filled_map) {
                ((IMixinItemRenderer) this).callRenderItemMap(player, prevpitch, progress, swing);
            } else if (player.getItemInUseCount() > 0) {
                EnumAction action = itemToRender.getItemUseAction();

                switch (action) {
                    case NONE:
                        transformFirstPersonItem(progress, 0.0F);
                        break;

                    case EAT:
                    case DRINK:
                        ((IMixinItemRenderer) this).callPerformDrinking(player, partialTicks);
                        if (Hydon.SETTINGS.oldEating) {
                            transformFirstPersonItem(progress, swing);
                            break;
                        } else {
                            transformFirstPersonItem(progress, 0.0F);
                            break;
                        }

                    case BLOCK:
                        if (Hydon.SETTINGS.oldBlockHit) {
                            transformFirstPersonItem(progress, swing);
                            ((IMixinItemRenderer) this).callDoBlockTransformations();
                            scale(0.83f, 0.88f, 0.85f);
                            translate(-0.3f, 0.1f, 0.0f);
                            break;
                        } else {
                            transformFirstPersonItem(progress, 0f);
                            ((IMixinItemRenderer) this).callDoBlockTransformations();
                            break;
                        }

                    case BOW:
                        if (Hydon.SETTINGS.oldBow) {
                            transformFirstPersonItem(progress, swing);
                            ((IMixinItemRenderer) this).callDoBowTransformations(partialTicks, player);
                            translate(0.0F, 0.1F, -0.15F);
                            break;
                        } else {
                            transformFirstPersonItem(progress, 0.0F);
                            ((IMixinItemRenderer) this).callDoBowTransformations(partialTicks, player);
                            break;
                        }
                }
            } else {
                ((IMixinItemRenderer) this).callDoItemUsedTransformations(swing);
                transformFirstPersonItem(progress, swing);
            }

            ((IMixinItemRenderer) this).callRenderItem(player, itemToRender, ItemCameraTransforms.TransformType.FIRST_PERSON);
        } else if (!player.isInvisible()) {
            ((IMixinItemRenderer) this).callRenderPlayerArm(player, progress, swing);
        }

        popMatrix();
        disableRescaleNormal();
        RenderHelper.disableStandardItemLighting();
    }
}

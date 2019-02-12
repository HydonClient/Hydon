package net.hydonclient.mixins.client.model;

import net.hydonclient.Hydon;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ModelBiped.class)
public class MixinModelBiped extends ModelBase {

    @Shadow
    public ModelRenderer bipedHead;

    @Shadow
    public ModelRenderer bipedRightArm;

    @Shadow
    public ModelRenderer bipedLeftArm;

    @Shadow
    public ModelRenderer bipedRightLeg;

    @Shadow
    public ModelRenderer bipedLeftLeg;

    @Shadow
    public int heldItemLeft;

    @Shadow
    public int heldItemRight;

    @Shadow
    public ModelRenderer bipedBody;

    @Shadow
    public boolean isSneak;

    @Shadow
    public boolean aimedBow;

    @Shadow
    public ModelRenderer bipedHeadwear;

    /**
     * @author Mojang
     * @reason 1.7 Blocking & item holding
     */
    @Overwrite
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
        this.bipedHead.rotateAngleY = netHeadYaw / 57.295776f;
        this.bipedHead.rotateAngleX = headPitch / 57.295776f;
        this.bipedRightArm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662f + 3.1415927f) * 2.0f * limbSwingAmount * 0.5f;
        this.bipedLeftArm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662f) * 2.0f * limbSwingAmount * 0.5f;
        this.bipedRightArm.rotateAngleZ = 0.0f;
        this.bipedLeftArm.rotateAngleZ = 0.0f;
        this.bipedRightLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662f) * 1.4f * limbSwingAmount;
        this.bipedLeftLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662f + 3.1415927f) * 1.4f * limbSwingAmount;
        this.bipedRightLeg.rotateAngleY = 0.0f;
        this.bipedLeftLeg.rotateAngleY = 0.0f;

        if (isRiding) {
            ModelRenderer bipedRightArm = this.bipedRightArm;
            bipedRightArm.rotateAngleX -= 0.62831855f;
            ModelRenderer bipedLeftArm = this.bipedLeftArm;
            bipedLeftArm.rotateAngleX -= 0.62831855f;
            this.bipedRightLeg.rotateAngleX = -1.2566371f;
            this.bipedLeftLeg.rotateAngleX = -1.2566371f;
            this.bipedRightLeg.rotateAngleY = 0.31415927f;
            this.bipedLeftLeg.rotateAngleY = -0.31415927f;
        }

        if (this.heldItemLeft != 0) {
            this.bipedLeftArm.rotateAngleX = this.bipedLeftArm.rotateAngleX * 0.5f - 0.31415927f * this.heldItemLeft;
        }

        this.bipedRightArm.rotateAngleY = 0.0f;
        this.bipedRightArm.rotateAngleZ = 0.0f;

        switch (heldItemRight) {
            case 1: {
                this.bipedRightArm.rotateAngleX = this.bipedRightArm.rotateAngleX * 0.5f - 0.31415927f * this.heldItemRight;
                break;
            }
            case 3: {
                this.bipedRightArm.rotateAngleX = this.bipedRightArm.rotateAngleX * 0.5f - 0.31415927f * this.heldItemRight;
                if (!Hydon.SETTINGS.oldBlocking) {
                    this.bipedRightArm.rotateAngleY = -0.5235988f;
                    break;
                }
                break;
            }
        }


        this.bipedLeftArm.rotateAngleY = 0.0f;
        if (this.swingProgress > -9990.0f) {
            float f = this.swingProgress;
            this.bipedBody.rotateAngleY = MathHelper.sin(MathHelper.sqrt_float(f) * 3.1415927f * 2.0f) * 0.2f;
            this.bipedRightArm.rotationPointZ = MathHelper.sin(this.bipedBody.rotateAngleY) * 5.0f;
            this.bipedRightArm.rotationPointX = -MathHelper.cos(this.bipedBody.rotateAngleY) * 5.0f;
            this.bipedLeftArm.rotationPointZ = -MathHelper.sin(this.bipedBody.rotateAngleY) * 5.0f;
            this.bipedLeftArm.rotationPointX = MathHelper.cos(this.bipedBody.rotateAngleY) * 5.0f;
            ModelRenderer bipedRightArm2 = this.bipedRightArm;
            bipedRightArm2.rotateAngleY += this.bipedBody.rotateAngleY;
            ModelRenderer bipedLeftArm2 = this.bipedLeftArm;
            bipedLeftArm2.rotateAngleY += this.bipedBody.rotateAngleY;
            ModelRenderer bipedLeftArm3 = this.bipedLeftArm;
            bipedLeftArm3.rotateAngleX += this.bipedBody.rotateAngleY;
            f = 1.0f - this.swingProgress;
            f *= f;
            f *= f;
            f = 1.0f - f;
            float f2 = MathHelper.sin(f * 3.1415927f);
            float f3 = MathHelper.sin(this.swingProgress * 3.1415927f) * -(this.bipedHead.rotateAngleX - 0.7f) * 0.75f;
            this.bipedRightArm.rotateAngleX -= (float) (f2 * 1.2 + f3);
            ModelRenderer bipedRightArm3 = this.bipedRightArm;
            bipedRightArm3.rotateAngleY += this.bipedBody.rotateAngleY * 2.0f;
            ModelRenderer bipedRightArm4 = this.bipedRightArm;
            bipedRightArm4.rotateAngleZ += MathHelper.sin(this.swingProgress * 3.1415927f) * -0.4f;
        }

        if (isSneak) {
            this.bipedBody.rotateAngleX = 0.5f;
            ModelRenderer bipedRightArm5 = this.bipedRightArm;
            bipedRightArm5.rotateAngleX += 0.4f;
            ModelRenderer bipedLeftArm4 = this.bipedLeftArm;
            bipedLeftArm4.rotateAngleX += 0.4f;
            this.bipedRightLeg.rotationPointZ = 4.0f;
            this.bipedLeftLeg.rotationPointZ = 4.0f;
            this.bipedRightLeg.rotationPointY = 9.0f;
            this.bipedLeftLeg.rotationPointY = 9.0f;
            this.bipedHead.rotationPointY = 1.0f;
        } else {
            this.bipedBody.rotateAngleX = 0.0f;
            this.bipedRightLeg.rotationPointZ = 0.1f;
            this.bipedLeftLeg.rotationPointZ = 0.1f;
            this.bipedRightLeg.rotationPointY = 12.0f;
            this.bipedLeftLeg.rotationPointY = 12.0f;
            this.bipedHead.rotationPointY = 0.0f;
        }

        ModelRenderer bipedRightArm6 = this.bipedRightArm;
        bipedRightArm6.rotateAngleZ += MathHelper.cos(ageInTicks * 0.09f) * 0.05f + 0.05f;
        ModelRenderer bipedLeftArm5 = this.bipedLeftArm;
        bipedLeftArm5.rotateAngleZ -= MathHelper.cos(ageInTicks * 0.09f) * 0.05f + 0.05f;
        ModelRenderer bipedRightArm7 = this.bipedRightArm;
        bipedRightArm7.rotateAngleX += MathHelper.sin(ageInTicks * 0.067f) * 0.05f;
        ModelRenderer bipedLeftArm6 = this.bipedLeftArm;
        bipedLeftArm6.rotateAngleX -= MathHelper.sin(ageInTicks * 0.067f) * 0.05f;

        if (aimedBow) {
            float f4 = 0.0f;
            float f5 = 0.0f;
            this.bipedRightArm.rotateAngleZ = 0.0f;
            this.bipedLeftArm.rotateAngleZ = 0.0f;
            this.bipedRightArm.rotateAngleY = -(0.1f - f4 * 0.6f) + this.bipedHead.rotateAngleY;
            this.bipedLeftArm.rotateAngleY = 0.1f - f4 * 0.6f + this.bipedHead.rotateAngleY + 0.4f;
            this.bipedRightArm.rotateAngleX = -1.5707964f + this.bipedHead.rotateAngleX;
            this.bipedLeftArm.rotateAngleX = -1.5707964f + this.bipedHead.rotateAngleX;
            ModelRenderer bipedRightArm8 = this.bipedRightArm;
            bipedRightArm8.rotateAngleX -= f4 * 1.2f - f5 * 0.4f;
            ModelRenderer bipedLeftArm7 = this.bipedLeftArm;
            bipedLeftArm7.rotateAngleX -= f4 * 1.2f - f5 * 0.4f;
            ModelRenderer bipedRightArm9 = this.bipedRightArm;
            bipedRightArm9.rotateAngleZ += MathHelper.cos(ageInTicks * 0.09f) * 0.05f + 0.05f;
            ModelRenderer bipedLeftArm8 = this.bipedLeftArm;
            bipedLeftArm8.rotateAngleZ -= MathHelper.cos(ageInTicks * 0.09f) * 0.05f + 0.05f;
            ModelRenderer bipedRightArm10 = this.bipedRightArm;
            bipedRightArm10.rotateAngleX += MathHelper.sin(ageInTicks * 0.067f) * 0.05f;
            ModelRenderer bipedLeftArm9 = this.bipedLeftArm;
            bipedLeftArm9.rotateAngleX -= MathHelper.sin(ageInTicks * 0.067f) * 0.05f;
        }

        copyModelAngles(bipedHead, bipedHeadwear);
    }
}
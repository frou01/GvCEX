package hmgww2.render;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gvclib.entity.EntityBases;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

@SideOnly(Side.CLIENT)
public class ModelSoldier extends ModelBiped
{
    public ModelSoldier()
    {
        this(0.0F, false);
    }

    protected ModelSoldier(float par1, float par2, int par3, int par4)
    {
        super(par1, par2, par3, par4);
    }

    public ModelSoldier(float par1, boolean par2)
    {
        super(par1, 0.0F, 64, 32);
    }

    /**
     * Sets the model's various rotation angles. For bipeds, par1 and par2 are used for animating the movement of arms
     * and legs, where par1 represents the time(so that arms and legs swing back and forth) and par2 represents how
     * "far" arms and legs can swing at most.
     */
    public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, Entity par7Entity)
    {
        super.setRotationAngles(par1, par2, par3, par4, par5, par6, par7Entity);
        float var8 = MathHelper.sin(this.onGround * (float)Math.PI);
        float var9 = MathHelper.sin((1.0F - (1.0F - this.onGround) * (1.0F - this.onGround)) * (float)Math.PI);
        this.bipedRightArm.rotateAngleZ = 0.0F;
        this.bipedLeftArm.rotateAngleZ = 0.0F;
        this.bipedRightArm.rotateAngleY = -(0.1F - var8 * 0.6F) + this.bipedHead.rotateAngleY;
        this.bipedLeftArm.rotateAngleY = 0.1F - var8 * 0.6F + this.bipedHead.rotateAngleY + 0.4F;
        this.bipedRightArm.rotateAngleX = -((float)Math.PI / 2F) + this.bipedHead.rotateAngleX;
        this.bipedLeftArm.rotateAngleX = -((float)Math.PI / 2F) + this.bipedHead.rotateAngleX;
        this.bipedRightArm.rotateAngleX -= var8 * 1.2F - var9 * 0.4F;
        this.bipedLeftArm.rotateAngleX -= var8 * 1.2F - var9 * 0.4F;
        this.bipedRightArm.rotateAngleZ += MathHelper.cos(par3 * 0.09F) * 0.05F + 0.05F;
        this.bipedLeftArm.rotateAngleZ -= MathHelper.cos(par3 * 0.09F) * 0.05F + 0.05F;
        this.bipedRightArm.rotateAngleX += MathHelper.sin(par3 * 0.067F) * 0.05F;
        this.bipedLeftArm.rotateAngleX -= MathHelper.sin(par3 * 0.067F) * 0.05F;
        if (par7Entity instanceof EntityBases && par7Entity != null) {
        	EntityBases en = (EntityBases) par7Entity;
        	if (this.isSneak || en.sneak)
            {
                this.bipedBody.rotateAngleX = 0.5F;
                this.bipedRightLeg.rotationPointZ = 4.0F;
                this.bipedLeftLeg.rotationPointZ = 4.0F;
                this.bipedRightLeg.rotationPointY = 9.0F;
                this.bipedLeftLeg.rotationPointY = 9.0F;
                this.bipedHead.rotationPointY = 1.0F;
                this.bipedHeadwear.rotationPointY = 1.0F;
            }
            else
            {
                this.bipedBody.rotateAngleX = 0.0F;
                this.bipedRightLeg.rotationPointZ = 0.1F;
                this.bipedLeftLeg.rotationPointZ = 0.1F;
                this.bipedRightLeg.rotationPointY = 12.0F;
                this.bipedLeftLeg.rotationPointY = 12.0F;
                this.bipedHead.rotationPointY = 0.0F;
                this.bipedHeadwear.rotationPointY = 0.0F;
            }
        }
        
    }
}

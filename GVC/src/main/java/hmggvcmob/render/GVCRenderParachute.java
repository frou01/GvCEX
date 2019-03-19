
package hmggvcmob.render;


import hmggvcmob.entity.EntityParachute;
import hmggvcmob.render.model.GVCModelAPC;
import hmggvcmob.render.model.ModelParachute;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;


public class GVCRenderParachute extends RenderLiving
{ 
	
	private static final ResourceLocation skeletonTextures = new ResourceLocation("gvcmob:textures/model/Parachute.png");
	private float scale;
 
 	public GVCRenderParachute() 
	{
 		super(new ModelParachute(), 0.5F);
 		this.scale = 6;
	}

 	protected void preRenderScale(EntityParachute par1EntityGhast, float par2)
    {
    	float scale = 8;
        GL11.glScalef(this.scale, this.scale, this.scale);
    }

 	protected void preRenderCallback(EntityLivingBase par1EntityLivingBase, float par2)
    {
    	this.preRenderScale((EntityParachute)par1EntityLivingBase, par2);
        float scale = 8*0.2f;
        GL11.glScalef(scale, scale, scale);
    }
 	@Override
	public void doRender(EntityLiving p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_){

 		super.doRender( p_76986_1_,  p_76986_2_,  p_76986_4_,  p_76986_6_,  p_76986_8_,  p_76986_9_);
	}
	@Override
	protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
		return this.skeletonTextures; 
	} 
 } 

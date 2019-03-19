
package hmggvcmob.render;

 
import hmggvcmob.entity.guerrilla.GVCEntityAPC;
import hmggvcmob.render.model.GVCModelAPC;
import net.minecraft.entity.EntityLiving;
import org.lwjgl.opengl.GL11;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation; 

 

public class GVCRenderAPC extends RenderLiving
{ 
	
	private static final ResourceLocation skeletonTextures = new ResourceLocation("gvcmob:textures/mob/apc.png"); 
	private float scale;
 
 	public GVCRenderAPC() 
	{
 		super(new GVCModelAPC(), 0.5F);
 		this.scale = 1.25f;
	}

 	protected void preRenderScale(GVCEntityAPC par1EntityGhast, float par2)
    {
    	float scale = GVCEntityAPC.getMobScale();
        GL11.glScalef(this.scale, this.scale, this.scale);
    }

 	protected void preRenderCallback(EntityLivingBase par1EntityLivingBase, float par2)
    {
    	this.preRenderScale((GVCEntityAPC)par1EntityLivingBase, par2);
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

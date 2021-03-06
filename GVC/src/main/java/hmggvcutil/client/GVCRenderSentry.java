
package hmggvcutil.client;

 
import hmggvcutil.entity.GVCEntitySentry;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation; 

 

public class GVCRenderSentry extends RenderLiving
{ 
	
	private static final ResourceLocation skeletonTextures = new ResourceLocation("gvcguns:textures/entity/mob/sentry.png"); 
	private float scale;
	private static GVCModelSentry modelSentry =  new GVCModelSentry();
 
 	public GVCRenderSentry() 
	{ 
 		 
 		super(modelSentry, 0.5F);
 		this.scale = 1;
	} 
 	
 	protected void preRenderScale(GVCEntitySentry par1EntityGhast, float par2)
    {
    	float scale = GVCEntitySentry.getMobScale()*2f;
        GL11.glScalef(this.scale, this.scale, this.scale);
    }

 	protected void preRenderCallback(EntityLivingBase par1EntityLivingBase, float par2)
    {
    	this.preRenderScale((GVCEntitySentry)par1EntityLivingBase, par2);
        float scale = GVCEntitySentry.getMobScale()*0.3f;
        GL11.glScalef(scale, scale, scale);
    }
 	
	@Override
	protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
		return this.skeletonTextures; 
	} 
 } 

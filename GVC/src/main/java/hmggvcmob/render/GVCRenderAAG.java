
package hmggvcmob.render;

 
import hmggvcmob.entity.guerrilla.GVCEntityAAG;
import hmggvcmob.render.model.GVCModelAAG;
import org.lwjgl.opengl.GL11;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation; 

 

public class GVCRenderAAG extends RenderLiving
{ 
	
	private static final ResourceLocation skeletonTextures = new ResourceLocation("gvcmob:textures/mob/AAG.png"); 
	private float scale;
 
 	public GVCRenderAAG() 
	{ 
 		 
 		super(new GVCModelAAG(), 0.5F);
 		this.scale = 1;
	} 
 	
 	protected void preRenderScale(GVCEntityAAG par1EntityGhast, float par2)
    {
    	float scale = GVCEntityAAG.getMobScale()*2f;
        GL11.glScalef(this.scale, this.scale, this.scale);
    }

 	protected void preRenderCallback(EntityLivingBase par1EntityLivingBase, float par2)
    {
    	this.preRenderScale((GVCEntityAAG)par1EntityLivingBase, par2);
        float scale = GVCEntityAAG.getMobScale()*0.3f;
        GL11.glScalef(scale, scale, scale);
    }
 	
	@Override
	protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
		return this.skeletonTextures; 
	} 
 } 

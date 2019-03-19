
package hmggvcmob.render;

 
import hmggvcmob.entity.guerrilla.GVCEntityHeli;
import hmggvcmob.render.model.GVCModelHeli;
import org.lwjgl.opengl.GL11;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation; 

 

public class GVCRenderHeli extends RenderLiving
{ 
	
	private static final ResourceLocation skeletonTextures = new ResourceLocation("gvcmob:textures/mob/heli.png"); 
	private float scale;
 
 	public GVCRenderHeli() 
	{ 
 		 
 		super(new GVCModelHeli(), 0.5F);
 		this.scale = 1;
	} 
 	
 	protected void preRenderScale(GVCEntityHeli par1EntityGhast, float par2)
    {
    	float scale = GVCEntityHeli.getMobScale()*2f;
        GL11.glScalef(this.scale, this.scale, this.scale);
    }

 	protected void preRenderCallback(EntityLivingBase par1EntityLivingBase, float par2)
    {
    	this.preRenderScale((GVCEntityHeli)par1EntityLivingBase, par2);
        float scale = GVCEntityHeli.getMobScale()*0.3f;
        GL11.glScalef(scale, scale, scale);
    }
 	
	@Override
	protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
		return this.skeletonTextures; 
	} 
 } 

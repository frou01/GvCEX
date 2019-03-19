
package hmggvcmob.render;

 
import hmggvcmob.entity.guerrilla.GVCEntityJeep;
import hmggvcmob.render.model.GVCModelJeep;
import org.lwjgl.opengl.GL11;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation; 

 

public class GVCRenderJeep extends RenderLiving
{ 
	
	private static final ResourceLocation skeletonTextures = new ResourceLocation("gvcmob:textures/mob/jeep.png"); 
	private float scale;
 
 	public GVCRenderJeep() 
	{ 
 		 
 		super(new GVCModelJeep(), 0.5F);
 		this.scale = 1.5f;
	} 
 	
 	protected void preRenderScale(GVCEntityJeep par1EntityGhast, float par2)
    {
        GL11.glScalef(this.scale, this.scale, this.scale);
    }

 	protected void preRenderCallback(EntityLivingBase par1EntityLivingBase, float par2)
    {
    	this.preRenderScale((GVCEntityJeep)par1EntityLivingBase, par2);
    }
 	
	@Override
	protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
		return this.skeletonTextures; 
	} 
 } 

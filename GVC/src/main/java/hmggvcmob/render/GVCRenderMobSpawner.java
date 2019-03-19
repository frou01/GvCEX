
package hmggvcmob.render;

 
import hmggvcmob.render.model.GVCModelFlag;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.entity.EntityLiving; 
import net.minecraft.util.ResourceLocation; 

 

public class GVCRenderMobSpawner extends RenderBiped 
{ 
	
	private static final ResourceLocation skeletonTextures = new ResourceLocation("gvcmob:textures/mob/guerrilla_mobspawner.png"); 

 
 	public GVCRenderMobSpawner() 
	{ 
 		
 		super(new GVCModelFlag(), 0.5F);
	} 
 
 
 	
 	@Override 
 	protected ResourceLocation getEntityTexture(EntityLiving par1EntityLiving) 
 	{ 
 		return this.skeletonTextures; 
 	} 
 } 

 
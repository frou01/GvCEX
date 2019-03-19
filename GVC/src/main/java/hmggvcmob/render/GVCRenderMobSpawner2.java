
package hmggvcmob.render;

 
import hmggvcmob.render.model.GVCModelFlag;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.entity.EntityLiving; 
import net.minecraft.util.ResourceLocation; 

 

public class GVCRenderMobSpawner2 extends RenderBiped 
{ 
	
	private static final ResourceLocation skeletonTextures = new ResourceLocation("gvcmob:textures/mob/guerrilla_mobspawner2.png"); 

 
 	public GVCRenderMobSpawner2() 
	{ 
 		
 		super(new GVCModelFlag(), 0.5F);
	} 
 
 
 	
 	@Override 
 	protected ResourceLocation getEntityTexture(EntityLiving par1EntityLiving) 
 	{ 
 		return this.skeletonTextures; 
 	} 
 } 

 
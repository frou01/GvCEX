
package hmgww2.render; 

 
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLiving; 
import net.minecraft.util.ResourceLocation; 

 

public class RenderUSSR_S extends RenderBiped
{ 
	
	private ResourceLocation skeletonTextures = new ResourceLocation("hmgww2:textures/mob/rus/RUS_S.png"); 

 
 	public RenderUSSR_S()
	{ 
 		 
 		super(new ModelSoldier(), 0.5F); 
	} 
 	
 	@Override 
 	protected ResourceLocation getEntityTexture(EntityLiving par1EntityLiving) 
 	{ 
 			return this.skeletonTextures; 
 		
 	} 
 } 
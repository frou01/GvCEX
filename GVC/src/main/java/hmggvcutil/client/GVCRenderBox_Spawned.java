
package hmggvcutil.client;


import hmggvcutil.entity.GVCEntityBoxSpawner;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;


public class GVCRenderBox_Spawned extends RenderLiving
{ 
	
	private static final ResourceLocation skeletonTextures = new ResourceLocation("gvcguns:textures/entity/mob/box_spawned.png");
	private float scale;
 
 	public GVCRenderBox_Spawned() 
	{ 
 		 
 		super(new GVCModelBox(), 0.5F); 
 		this.scale = 1;
	} 
 	
 	protected void preRenderScale(GVCEntityBoxSpawner par1EntityGhast, float par2)
    {
    	float scale = GVCEntityBoxSpawner.getMobScale()*2f;
        GL11.glScalef(this.scale, this.scale, this.scale);
    }

 	protected void preRenderCallback(EntityLivingBase par1EntityLivingBase, float par2)
    {
    	this.preRenderScale((GVCEntityBoxSpawner) par1EntityLivingBase, par2);
        float scale = GVCEntityBoxSpawner.getMobScale()*0.3f;
        GL11.glScalef(scale, scale, scale);
    }
 	
	@Override
	protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
		return this.skeletonTextures; 
	} 
 } 

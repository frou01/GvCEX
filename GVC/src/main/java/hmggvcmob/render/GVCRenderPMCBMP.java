
package hmggvcmob.render;


import hmggvcmob.entity.friend.GVCEntityPMCBMP;
import hmggvcmob.render.model.ModelBMP;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import org.lwjgl.opengl.GL11;


public class GVCRenderPMCBMP extends RenderLiving
{

	private static final ResourceLocation skeletonTextures = new ResourceLocation("gvcmob:textures/mob/PMCBMP-1.png");
	private float scale;


 	public GVCRenderPMCBMP()
	{ 
 		super(new ModelBMP(), 1F);
 		this.scale = 1.0f;
	} 

 	protected void preRenderScale(GVCEntityPMCBMP par1EntityGhast, float par2)
    {
        GL11.glScalef(this.scale, this.scale, this.scale);
    }

 	protected void preRenderCallback(EntityLivingBase par1EntityLivingBase, float par2)
    {
    	this.preRenderScale((GVCEntityPMCBMP)par1EntityLivingBase, par2);
    }
 	
	@Override
	protected ResourceLocation getEntityTexture(Entity par1EntityLiving) {
		return this.skeletonTextures;
	}

	
 } 

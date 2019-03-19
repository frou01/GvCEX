
package hmggvcmob.render;


import hmggvcmob.entity.friend.GVCEntityPMCT90Tank;
import hmggvcmob.render.model.T90A;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import org.lwjgl.opengl.GL11;


public class GVCRenderTankT90PMC extends RenderLiving
{

	private static final ResourceLocation skeletonTextures = new ResourceLocation("gvcmob:textures/mob/T90A.png");
	private static final ResourceLocation skeletonTextures2 = new ResourceLocation("gvcmob:textures/mob/T90A.png");
	private static final ResourceLocation skeletonTextures3 = new ResourceLocation("gvcmob:textures/mob/T90A.png");
	private static final ResourceLocation skeletonTextures4 = new ResourceLocation("gvcmob:textures/mob/T90A.png");
	private float scale;


 	public GVCRenderTankT90PMC()
	{ 
 		super(new T90A(), 1F);
 		this.scale = 0.5f;
	} 

 	protected void preRenderScale(GVCEntityPMCT90Tank par1EntityGhast, float par2)
    {
        GL11.glScalef(this.scale, this.scale, this.scale);
    }

 	protected void preRenderCallback(EntityLivingBase par1EntityLivingBase, float par2)
    {
    	this.preRenderScale((GVCEntityPMCT90Tank)par1EntityLivingBase, par2);
    }
 	
	@Override
	protected ResourceLocation getEntityTexture(Entity par1EntityLiving) {
		World world = Minecraft.getMinecraft().theWorld;
			if(world.getBiomeGenForCoords( (int)par1EntityLiving.posX, (int)par1EntityLiving.posZ) == BiomeGenBase.desert
					||world.getBiomeGenForCoords((int)par1EntityLiving.posX, (int)par1EntityLiving.posZ) == BiomeGenBase.desertHills
					||world.getBiomeGenForCoords((int)par1EntityLiving.posX, (int)par1EntityLiving.posZ) == BiomeGenBase.savanna
					||world.getBiomeGenForCoords((int)par1EntityLiving.posX, (int)par1EntityLiving.posZ) == BiomeGenBase.savannaPlateau){
	 			return this.skeletonTextures2;
	 		}else if(world.getBiomeGenForCoords( (int)par1EntityLiving.posX, (int)par1EntityLiving.posZ) == BiomeGenBase.taiga
					||world.getBiomeGenForCoords((int)par1EntityLiving.posX, (int)par1EntityLiving.posZ) == BiomeGenBase.taigaHills){
	 			return this.skeletonTextures2;
	 		}else{
	 		return this.skeletonTextures; 
	 		}
		//return null; 
	}

	
 } 

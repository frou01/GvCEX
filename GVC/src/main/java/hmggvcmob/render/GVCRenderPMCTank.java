
package hmggvcmob.render;

 
import hmggvcmob.entity.friend.GVCEntityPMCTank;
import hmggvcmob.render.model.GVCModelSoldierTank;
import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation; 
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

 

public class GVCRenderPMCTank extends RenderLiving
{ 
	
	private static final ResourceLocation skeletonTextures = new ResourceLocation("gvcmob:textures/mob/tankm1_pmc.png"); 
	private static final ResourceLocation skeletonTextures2 = new ResourceLocation("gvcmob:textures/mob/tankm1_pmc_d.png"); 
	private static final ResourceLocation skeletonTextures3 = new ResourceLocation("gvcmob:textures/mob/tankm1_pmc_t.png"); 
	private static final ResourceLocation skeletonTextures4 = new ResourceLocation("gvcmob:textures/mob/tankm1_pmc.png"); 
	private float scale;
 
 	public GVCRenderPMCTank() 
	{ 
 		 
 		super(new GVCModelSoldierTank(), 0.5F);
 		this.scale = 2;
	} 
 	
 	protected void preRenderScale(GVCEntityPMCTank par1EntityGhast, float par2)
    {
    	float scale = GVCEntityPMCTank.getMobScale()*2f;
        GL11.glScalef(this.scale, this.scale, this.scale);
    }

 	protected void preRenderCallback(EntityLivingBase par1EntityLivingBase, float par2)
    {
    	this.preRenderScale((GVCEntityPMCTank)par1EntityLivingBase, par2);
        float scale = GVCEntityPMCTank.getMobScale()*0.3f;
        GL11.glScalef(scale, scale, scale);
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
	} 
 } 

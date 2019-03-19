
package hmggvcmob.render;

 
import hmggvcmob.GVCMobPlus;
import hmggvcmob.render.model.GVCModelGuerrilla;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderBiped; 
import net.minecraft.entity.EntityLiving; 
import net.minecraft.util.ResourceLocation; 
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

 

public class GVCRenderGuerrilla extends RenderBiped 
{ 
	
	private String Texturename = new String("gvcmob:textures/mob/guerrilla");
	private ResourceLocation guerrillaTex = new ResourceLocation("gvcmob:textures/mob/guerrilla.png");
	private ResourceLocation gurrillaTex2 = new ResourceLocation("gvcmob:textures/mob/guerrilla_d.png");
	private ResourceLocation gurrillaTex3 = new ResourceLocation("gvcmob:textures/mob/guerrilla_t.png");
	private ResourceLocation gurrillaTex4 = new ResourceLocation("gvcmob:textures/mob/guerrilla_j.png");
	private ResourceLocation gurrillaTex5 = new ResourceLocation("gvcmob:textures/mob/guerrilla_hell.png");
	private ResourceLocation gurrillaTex6 = new ResourceLocation("gvcmob:textures/mob/solider.png");

 
 	public GVCRenderGuerrilla() 
	{
 		super(new GVCModelGuerrilla(), 0.5F);
	}
 	public GVCRenderGuerrilla(String texturename)
	{
 		super(new GVCModelGuerrilla(), 0.5F);
 		Texturename = "gvcmob:textures/mob/" + texturename;
		guerrillaTex =  new ResourceLocation(Texturename + ".png");
		gurrillaTex2 =  new ResourceLocation(Texturename + "_d.png");
		gurrillaTex3 =  new ResourceLocation(Texturename + "_t.png");
		gurrillaTex4 =  new ResourceLocation(Texturename + "_j.png");
		gurrillaTex5 =  new ResourceLocation(Texturename + ".png");
		gurrillaTex6 =  new ResourceLocation("gvcmob:textures/mob/solider.png");
	}
 	public static GVCRenderGuerrilla onlyoneTexture(String texturename)
	{
 		GVCRenderGuerrilla guerrillarender = new GVCRenderGuerrilla();
		guerrillarender.Texturename = "gvcmob:textures/mob/" + texturename;
		guerrillarender.guerrillaTex =  new ResourceLocation( guerrillarender.Texturename + ".png");
		guerrillarender.gurrillaTex2 =  new ResourceLocation(guerrillarender.Texturename + ".png");
		guerrillarender.gurrillaTex3 =  new ResourceLocation(guerrillarender.Texturename + ".png");
		guerrillarender.gurrillaTex4 =  new ResourceLocation(guerrillarender.Texturename + ".png");
		guerrillarender.gurrillaTex5 =  new ResourceLocation(guerrillarender.Texturename + ".png");
		guerrillarender.gurrillaTex6 =  new ResourceLocation("gvcmob:textures/mob/solider.png");
		return guerrillarender;
	}
 
 
 	
 	@Override 
 	protected ResourceLocation getEntityTexture(EntityLiving par1EntityLiving) 
 	{ 
 		World world = Minecraft.getMinecraft().theWorld;
 		
 		if(GVCMobPlus.cfg_modeGorC){

 	 		if(world.getBiomeGenForCoords( (int)par1EntityLiving.posX, (int)par1EntityLiving.posZ) == BiomeGenBase.desert
 					||world.getBiomeGenForCoords((int)par1EntityLiving.posX, (int)par1EntityLiving.posZ) == BiomeGenBase.desertHills
 					||world.getBiomeGenForCoords((int)par1EntityLiving.posX, (int)par1EntityLiving.posZ) == BiomeGenBase.savanna
 					||world.getBiomeGenForCoords((int)par1EntityLiving.posX, (int)par1EntityLiving.posZ) == BiomeGenBase.savannaPlateau){
 	 			return this.gurrillaTex2;
 	 		}else if(world.getBiomeGenForCoords( (int)par1EntityLiving.posX, (int)par1EntityLiving.posZ) == BiomeGenBase.taiga
 					||world.getBiomeGenForCoords((int)par1EntityLiving.posX, (int)par1EntityLiving.posZ) == BiomeGenBase.taigaHills){
 	 			return this.gurrillaTex3;
 	 		}else if(world.getBiomeGenForCoords( (int)par1EntityLiving.posX, (int)par1EntityLiving.posZ) == BiomeGenBase.jungle
 					||world.getBiomeGenForCoords((int)par1EntityLiving.posX, (int)par1EntityLiving.posZ) == BiomeGenBase.jungleEdge
 					||world.getBiomeGenForCoords((int)par1EntityLiving.posX, (int)par1EntityLiving.posZ) == BiomeGenBase.jungleHills
 					||world.getBiomeGenForCoords((int)par1EntityLiving.posX, (int)par1EntityLiving.posZ) == BiomeGenBase.roofedForest
 					){
 	 			return this.gurrillaTex4;
 	 		}else if(world.getBiomeGenForCoords( (int)par1EntityLiving.posX, (int)par1EntityLiving.posZ) == BiomeGenBase.desert){
 	 			return this.gurrillaTex4;
 	 		}
 		}else{
 	 			return this.gurrillaTex6;
 		}
 			return this.guerrillaTex;
 	} 
 } 

 
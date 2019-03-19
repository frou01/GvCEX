
package hmggvcmob.render;

 
import hmggvcmob.entity.guerrilla.GVCEntityHeli;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;


public class GVCRenderHeliobj extends Render
{ 
	
	private static final ResourceLocation skeletonTextures = new ResourceLocation("gvcmob:textures/mob/tank.png"); 
	private static final ResourceLocation skeletonTextures2 = new ResourceLocation("gvcmob:textures/mob/tank_d.png"); 
	private static final ResourceLocation skeletonTextures3 = new ResourceLocation("gvcmob:textures/mob/tank_t.png"); 
	private static final ResourceLocation skeletonTextures4 = new ResourceLocation("gvcmob:textures/mob/tank.png"); 
	private static final ResourceLocation skeletonTexturesz = new ResourceLocation("gvcmob:textures/model/heli.png");
	private static final ResourceLocation skeletonTextureszz = new ResourceLocation("gvcmob:textures/model/helikkk.png");
	private static final IModelCustom tankk = AdvancedModelLoader.loadModel(new ResourceLocation("gvcmob:textures/model/heli.obj"));
	private static final IModelCustom tankkk = AdvancedModelLoader.loadModel(new ResourceLocation("gvcmob:textures/model/helikkk.obj"));
	private float scale;
	private float iii;
	
 
 	public GVCRenderHeliobj() 
	{  
 		this.scale = 2;
	} 
 	
 	public void doRender(Entity p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_)
    {
        this.doRender((GVCEntityHeli)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }
 	
	public void doRender(GVCEntityHeli p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_,float p_76986_9_) {
 		this.bindEntityTexture(p_76986_1_);
        GL11.glPushMatrix();
        GL11.glTranslatef((float)p_76986_2_, (float)p_76986_4_, (float)p_76986_6_);
        GL11.glRotatef(180F, 0.0F, 1.0F, 0.0F);
        //GL11.glRotatef(p_76986_1_.prevRotationYaw + (p_76986_1_.rotationYaw - p_76986_1_.prevRotationYaw) * p_76986_9_ - 360.0F, 0.0F, 1.0F, 0.0F);
        //GL11.glRotatef(p_76986_1_.prevRotationPitch + (p_76986_1_.rotationPitch - p_76986_1_.prevRotationPitch) * p_76986_9_, 0.0F, 0.0F, 1.0F);
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        
        GL11.glRotatef(180.0F - p_76986_8_, 0.0F, 1.0F, 0.0F);
        if(p_76986_1_.deathTicks > 0){
			GL11.glColor4f(0.1F, 0.1F, 0.1F, 1F);
		}
        if(p_76986_1_.deathTicks > 0){
    		GL11.glTranslatef(0, 1.6F, 0);
    		GL11.glRotatef(p_76986_1_.deathTicks*6, 0.0F, 1.0F, 0.0F);
    		GL11.glTranslatef(0, -1.6F, 0);
    	}
 		tankkk.renderPart("mat_00");
 		
 		if(iii < 360F){
 			GL11.glRotatef(p_76986_1_.prevRotationYaw + (p_76986_1_.rotationYaw - p_76986_1_.prevRotationYaw) * p_76986_9_ - iii, 0.0F, 1.0F, 0.0F);
 			tankkk.renderPart("mat1");
 			iii = iii + 20F;
 		}else{
 			iii = 0F;
 		}
 		
 		
 		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        GL11.glPopMatrix();
        //this.func_110827_b((EntityLiving) p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
	} 
	
	private void glMatrixForRender()
    {
    	GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
        GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(0.0F, 0.0F, 0.0F, 1.0F);
      GL11.glTranslatef(0.0F, 0.0F, 0.0F);
    }
	
	public void doRender(EntityLiving p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_)
    {
	        this.doRender((GVCEntityHeli)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
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
	protected ResourceLocation getEntityTexture(Entity par1EntityLiving) {
		World world = Minecraft.getMinecraft().theWorld;
			return this.skeletonTextureszz; 
	}

	
 } 

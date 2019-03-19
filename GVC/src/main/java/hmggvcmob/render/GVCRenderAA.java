
package hmggvcmob.render;

 
import hmggvcmob.entity.GVCEntityAA;
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


public class GVCRenderAA extends Render
{ 
	
	private static final ResourceLocation skeletonTextureszz = new ResourceLocation("gvcmob:textures/model/AA.png");
	private static final IModelCustom tankkk = AdvancedModelLoader.loadModel(new ResourceLocation("gvcmob:textures/model/AA.obj"));
	private float scale;
	private float iii;
	
 
 	public GVCRenderAA() 
	{  
 		this.scale = 2;
	} 
 	
 	public void doRender(Entity p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_)
    {
        this.doRender((GVCEntityAA)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }
 	
	public void doRender(GVCEntityAA p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_,float p_76986_9_) {
 		this.bindEntityTexture(p_76986_1_);
        GL11.glPushMatrix();
        GL11.glTranslatef((float)p_76986_2_, (float)p_76986_4_, (float)p_76986_6_);
        GL11.glRotatef(180F, 0.0F, 1.0F, 0.0F);
        //GL11.glRotatef(180.0F - p_76986_8_, 0.0F, 1.0F, 0.0F);
        
        GL11.glRotatef(p_76986_1_.kakudo, 0.0F, 1.0F, 0.0F);
        
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);

 		tankkk.renderPart("mat1");
 		
 		if(iii < 360F){
 			GL11.glRotatef(p_76986_1_.prevRotationYaw + (p_76986_1_.rotationYaw - p_76986_1_.prevRotationYaw) * p_76986_9_ - iii, 0.0F, 0.0F, 1.0F);
 			tankkk.renderPart("mat2");
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
	        this.doRender((GVCEntityAA)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }
	
 	protected void preRenderScale(GVCEntityAA par1EntityGhast, float par2)
    {
    	float scale = GVCEntityAA.getMobScale()*2f;
        GL11.glScalef(this.scale, this.scale, this.scale);
    }

 	protected void preRenderCallback(EntityLivingBase par1EntityLivingBase, float par2)
    {
    	this.preRenderScale((GVCEntityAA)par1EntityLivingBase, par2);
        float scale = GVCEntityAA.getMobScale()*0.3f;
        GL11.glScalef(scale, scale, scale);
    }
 	
	@Override
	protected ResourceLocation getEntityTexture(Entity par1EntityLiving) {
		World world = Minecraft.getMinecraft().theWorld;
			return this.skeletonTextureszz; 
	}

	
 } 

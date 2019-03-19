
package hmggvcmob.render;


import hmggvcmob.entity.guerrilla.GVCEntityDrawn;
import hmggvcmob.entity.guerrilla.GVCEntityDrawn;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;


public class GVCRenderDrawn extends Render
{

	private static final ResourceLocation skeletonTextures = new ResourceLocation("gvcmob:textures/model/ISDrawn.png");
	private static final IModelCustom heli = AdvancedModelLoader.loadModel(new ResourceLocation("gvcmob:textures/model/ISDrawn.mqo"));
	private float scale;
	private float iii;


 	public GVCRenderDrawn()
	{  
 		this.scale = 2;
	} 
 	
 	public void doRender(Entity p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_)
    {
        this.doRender((GVCEntityDrawn)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }
 	
	public void doRender(GVCEntityDrawn entity, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float partialTicks) {
		this.bindEntityTexture(entity);
		GL11.glPushMatrix();
		GL11.glTranslatef((float) p_76986_2_, (float) p_76986_4_, (float) p_76986_6_);
		GL11.glRotatef(180F, 0.0F, 1.0F, 0.0F);
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);

		if(entity.deathTicks > 0){
			GL11.glColor4f(0.1F, 0.1F, 0.1F, 1F);
		}

		GL11.glRotatef(180.0F - (entity.bodyrotationYaw +(entity.bodyrotationYaw - entity.prevbodyrotationYaw) * partialTicks), 0.0F, 1.0F, 0.0F);
		GL11.glTranslatef(0, 1.6F, 0);
		//this.renderAngle(entity, 1);
		GL11.glRotatef(entity.bodyrotationPitch, 1.0F, 0.0F, 0.0F);
		GL11.glRotatef(entity.bodyrotationRoll, 0.0F, 0.0F, 1.0F);
		GL11.glTranslatef(0, -1.6F, 0);
		if(entity.deathTicks > 0){
			GL11.glTranslatef(0, 1.6F, 0);
			GL11.glRotatef(entity.deathTicks*6, 0.0F, 1.0F, 0.0F);
			GL11.glTranslatef(0, -1.6F, 0);
		}
		heli.renderPart("obj1");
		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		GL11.glPopMatrix();
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
	        this.doRender((GVCEntityDrawn)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }
	
 	protected void preRenderScale(GVCEntityDrawn par1EntityGhast, float par2)
    {
    	float scale = GVCEntityDrawn.getMobScale()*2f;
        GL11.glScalef(this.scale, this.scale, this.scale);
    }

 	protected void preRenderCallback(EntityLivingBase par1EntityLivingBase, float par2)
    {
    	this.preRenderScale((GVCEntityDrawn)par1EntityLivingBase, par2);
        float scale = GVCEntityDrawn.getMobScale()*0.3f;
        GL11.glScalef(scale, scale, scale);
    }
 	
	@Override
	protected ResourceLocation getEntityTexture(Entity par1EntityLiving) {
		World world = Minecraft.getMinecraft().theWorld;
			return this.skeletonTextures;
	}

	
 } 

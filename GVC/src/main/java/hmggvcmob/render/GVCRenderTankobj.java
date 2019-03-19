
package hmggvcmob.render;

import hmggvcmob.entity.guerrilla.GVCEntityTank;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

public class GVCRenderTankobj extends Render {

	private static final ResourceLocation skeletonTexturesz = new ResourceLocation("gvcmob:textures/mob/kai/T34_85.png");
	private static final IModelCustom tankk = AdvancedModelLoader.loadModel(new ResourceLocation("gvcmob:textures/mob/kai/T-34-85.mqo"));
	private float scale;

	public GVCRenderTankobj() {
		this.scale = 2;
	}

	public void doRender(Entity p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_,
			float p_76986_9_) {
		this.doRender((GVCEntityTank) p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
	}

	/*private double func_110828_a(double p_110828_1_, double p_110828_3_, double p_110828_5_)
    {
        return p_110828_1_ + (p_110828_3_ - p_110828_1_) * p_110828_5_;
    }
	*/
	
	float xsxs;
	public void doRender(GVCEntityTank entity, double p_76986_2_, double p_76986_4_, double p_76986_6_,
			float entityYaw, float partialTicks) {
		this.bindEntityTexture(entity);
		GL11.glPushMatrix();
		GL11.glTranslatef((float) p_76986_2_, (float) p_76986_4_, (float) p_76986_6_);
		GL11.glRotatef(180F, 0.0F, 1.0F, 0.0F);
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);

		if(entity.deathTicks > 0){
			GL11.glColor4f(0.1F, 0.1F, 0.1F, 1F);
		}

		GL11.glRotatef(180.0F - (entity.baseLogic.bodyrotationYaw + (entity.baseLogic.bodyrotationYaw - entity.baseLogic.prevbodyrotationYaw) * partialTicks), 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(entity.baseLogic.bodyrotationPitch, 1.0F, 0.0F, 0.0F);
		GL11.glRotatef(entity.baseLogic.bodyrotationRoll, 0.0F, 0.0F, 1.0F);
		tankk.renderPart("Body");
		tankk.renderPart("FuelTank");
		tankk.renderPart("Accessory");
		tankk.renderPart("Cat");
		tankk.renderPart("Fender");
		tankk.renderPart("Roller");
		tankk.renderPart("Shaft");
		//GL11.glRotatef(-(180.0F - entityYaw), 0.0F, 1.0F, 0.0F);

		{
			GL11.glTranslatef(0F, 0F, 0.4488F);
			GL11.glRotatef(-(entity.baseLogic.turretrotationYaw * partialTicks + entity.baseLogic.prevturretrotationYaw * (1-partialTicks)), 0.0F, 1.0F, 0.0F);
			GL11.glTranslatef(0F, 0F, -0.4488F);
			tankk.renderPart("Turret");
			GL11.glTranslatef(0F, 2.06F, 1.58F);
			GL11.glRotatef(entity.baseLogic.turretrotationPitch, 1.0F, 0.0F, 0.0F);
			GL11.glTranslatef(0F, -2.06F, -1.58F);
			tankk.renderPart("Cannon");
		}

		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		GL11.glPopMatrix();
		
		
	}
	
	public void doRender(EntityLiving p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_,
			float p_76986_8_, float p_76986_9_) {
		this.doRender((GVCEntityTank) p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity par1EntityLiving) {
		return this.skeletonTexturesz;
	}

}

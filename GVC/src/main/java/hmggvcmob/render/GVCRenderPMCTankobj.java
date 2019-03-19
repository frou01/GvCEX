
package hmggvcmob.render;

import hmggvcmob.entity.friend.GVCEntityPMCTank;
import hmggvcmob.entity.friend.GVCEntityPMCTank;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

public class GVCRenderPMCTankobj extends Render {


	private static final ResourceLocation skeletonTexturesz = new ResourceLocation("gvcmob:textures/mob/kai/Kpz-70.png");
	private static final IModelCustom bodyobj = AdvancedModelLoader.loadModel(new ResourceLocation("gvcmob:textures/mob/kai/Kpz-70-body.mqo"));
	private static final IModelCustom turretobj = AdvancedModelLoader.loadModel(new ResourceLocation("gvcmob:textures/mob/kai/Kpz-70-turret.mqo"));
	private static final IModelCustom catobj = AdvancedModelLoader.loadModel(new ResourceLocation("gvcmob:textures/mob/kai/Kpz-70-cat.mqo"));
	private float scale;

	public GVCRenderPMCTankobj() {
		this.scale = 2;
	}

	public void doRender(Entity p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_,
						 float p_76986_9_) {
		this.doRender((GVCEntityPMCTank) p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
	}

	/*private double func_110828_a(double p_110828_1_, double p_110828_3_, double p_110828_5_)
    {
        return p_110828_1_ + (p_110828_3_ - p_110828_1_) * p_110828_5_;
    }
	*/

	float xsxs;
	public void doRender(GVCEntityPMCTank entity, double p_76986_2_, double p_76986_4_, double p_76986_6_,
						 float entityYaw, float partialTicks) {
		this.bindEntityTexture(entity);
		GL11.glPushMatrix();
		GL11.glTranslatef((float) p_76986_2_, (float) p_76986_4_, (float) p_76986_6_);
		GL11.glRotatef(180F, 0.0F, 1.0F, 0.0F);
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glScalef(1.8f,1.8f,1.8f);

		GL11.glRotatef(180.0F - (entity.baseLogic.bodyrotationYaw + (entity.baseLogic.bodyrotationYaw - entity.baseLogic.prevbodyrotationYaw) * partialTicks), 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(entity.baseLogic.bodyrotationPitch, 1.0F, 0.0F, 0.0F);
		GL11.glRotatef(entity.baseLogic.bodyrotationRoll, 0.0F, 0.0F, 1.0F);
		GL11.glTranslatef(0, 2.5F, 0);
		GL11.glTranslatef(0, -2.5F, 0);
		bodyobj.renderAll();
		catobj.renderAll();
		//GL11.glRotatef(-(180.0F - entityYaw), 0.0F, 1.0F, 0.0F);

		{
			GL11.glTranslatef(0, 0, 0.136f);
			GL11.glRotatef(-(entity.baseLogic.turretrotationYaw * partialTicks + entity.baseLogic.prevturretrotationYaw * (1-partialTicks)), 0.0F, 1.0F, 0.0F);
			GL11.glTranslatef(0, 0, -0.136f);
			turretobj.renderPart("turret");
			GL11.glTranslatef(0F, 0.94f, 1.0217f);
			GL11.glRotatef(entity.baseLogic.turretrotationPitch, 1.0F, 0.0F, 0.0F);
			GL11.glTranslatef(0F, -0.94f, -1.0217f);
			turretobj.renderPart("cannon");
			GL11.glTranslatef(0F, 0.94f, 1.0217f);
			GL11.glRotatef(-entity.baseLogic.turretrotationPitch, 1.0F, 0.0F, 0.0F);
			GL11.glTranslatef(0F, -0.94f, -1.0217f);



			GL11.glTranslatef(0.615F, 1.31f, -0.0432f);
			GL11.glRotatef(-entity.subturretrotationYaw, 0.0F, 1.0F, 0.0F);
			GL11.glTranslatef(-0.615F, -1.31f, 0.0432f);
			turretobj.renderPart("subturret");
			GL11.glTranslatef(0.615F, 1.31f, -0.13f);
			GL11.glRotatef(entity.subturretrotationPitch, 1.0F, 0.0F, 0.0F);
			GL11.glTranslatef(-0.615F, -1.31f, 0.13f);
			turretobj.renderPart("subgun");
		}

		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		GL11.glPopMatrix();


	}

	public void doRender(EntityLiving p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_,
						 float p_76986_8_, float p_76986_9_) {
		this.doRender((GVCEntityPMCTank) p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity par1EntityLiving) {
		return this.skeletonTexturesz;
	}

}

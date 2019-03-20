
package hmgww2.render;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import hmgww2.entity.EntityRUS_TankH;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

public class RenderRUS_TankH extends Render {

	private static final ResourceLocation skeletonTexturesz = new ResourceLocation("hmgww2:textures/mob/rus/KV2.png");
	private static final IModelCustom tankk = AdvancedModelLoader.loadModel(new ResourceLocation("hmgww2:textures/mob/rus/KV2.obj"));
	private float scale;

	public RenderRUS_TankH() {
		this.scale = 2;
	}

	public void doRender(Entity p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_,
			float p_76986_9_) {
		this.doRender((EntityRUS_TankH) p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
	}

	/*private double func_110828_a(double p_110828_1_, double p_110828_3_, double p_110828_5_)
    {
        return p_110828_1_ + (p_110828_3_ - p_110828_1_) * p_110828_5_;
    }
	*/
	
	float xsxs;
	public void doRender(EntityRUS_TankH entity, double p_76986_2_, double p_76986_4_, double p_76986_6_,
			float entityYaw, float partialTicks) {
		this.bindEntityTexture(entity);
		GL11.glPushMatrix();
		GL11.glTranslatef((float) p_76986_2_, (float) p_76986_4_, (float) p_76986_6_);
		GL11.glRotatef(180F, 0.0F, 1.0F, 0.0F);
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);

		if(entity.deathTicks > 0){
			GL11.glColor4f(0.1F, 0.1F, 0.1F, 1F);
		}
		
		GL11.glRotatef(180.0F - entityYaw, 0.0F, 1.0F, 0.0F);
		GL11.glTranslatef(0, 2.5F, 0);
		this.renderAngle(entity, 1);
		GL11.glTranslatef(0, -2.5F, 0);
			tankk.renderPart("mat1");
		GL11.glRotatef(-(180.0F - entityYaw), 0.0F, 1.0F, 0.0F);
		
		{
			GL11.glRotatef(180.0F - entity.rotation, 0.0F, 1.0F, 0.0F);
			tankk.renderPart("mat4");
			if(entity.getMobMode() == 0){
				tankk.renderPart("mat30");
			}
			GL11.glTranslatef(0F, 2.6F, 1.2F);
			GL11.glRotatef(entity.rotationp, 1.0F, 0.0F, 0.0F);
			GL11.glTranslatef(0F, -2.6F, -1.2F);
			tankk.renderPart("mat5");
		}
		
		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		GL11.glPopMatrix();
		
		
	}

	private void renderAngle(EntityRUS_TankH p_76986_1_, int i){
		{
			GL11.glRotatef(-(p_76986_1_.angletime), 1.0F, 0.0F, 0.0F);
		}
	}
	
	public void doRender(EntityLiving p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_,
			float p_76986_8_, float p_76986_9_) {
		this.doRender((EntityRUS_TankH) p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity par1EntityLiving) {
		return this.skeletonTexturesz;
	}

}

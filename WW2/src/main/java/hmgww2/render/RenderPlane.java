package hmgww2.render;

import hmggvcmob.entity.IRideableTank;
import hmggvcmob.entity.Iplane;
import hmggvcmob.entity.PlaneBaseLogic;
import hmggvcmob.entity.TankBaseLogic;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class RenderPlane extends Render {
	
	private ResourceLocation skeletonTexturesz = new ResourceLocation("hmgww2:textures/mob/rus/T34_76.png");
	private IModelCustom tankk = AdvancedModelLoader.loadModel(new ResourceLocation("hmgww2:textures/mob/rus/T34_76.obj"));
	
	double[] perapos = new double[]{0,1.2,0};
	
	public RenderPlane(String texture, String model,double[] perapos) {
		skeletonTexturesz = new ResourceLocation(texture);
		tankk = AdvancedModelLoader.loadModel(new ResourceLocation(model));
		this.perapos = perapos;
	}
	
	@Override
	protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
		return skeletonTexturesz;
	}

	/*private double func_110828_a(double p_110828_1_, double p_110828_3_, double p_110828_5_)
    {
        return p_110828_1_ + (p_110828_3_ - p_110828_1_) * p_110828_5_;
    }
	*/
	
	float xsxs;
	public void doRender(Entity entity, double p_76986_2_, double p_76986_4_, double p_76986_6_,
	                     float entityYaw, float partialTicks) {
		if(entity instanceof Iplane){
			this.bindEntityTexture(entity);
			GL11.glPushMatrix();
			GL11.glTranslatef((float) p_76986_2_, (float) p_76986_4_, (float) p_76986_6_);
			GL11.glRotatef(180F, 0.0F, 1.0F, 0.0F);
			GL11.glEnable(GL12.GL_RESCALE_NORMAL);
			PlaneBaseLogic baseLogic = ((Iplane) entity).getBaseLogic();
			GL11.glTranslatef((float) baseLogic.rotcenter[0], (float) baseLogic.rotcenter[1], (float) baseLogic.rotcenter[2]);
			GL11.glRotatef(180.0F - (baseLogic.bodyrotationYaw + (baseLogic.bodyrotationYaw - baseLogic.prevbodyrotationYaw) * partialTicks), 0.0F, 1.0F, 0.0F);
			GL11.glRotatef(baseLogic.bodyrotationPitch, 1.0F, 0.0F, 0.0F);
			GL11.glRotatef(baseLogic.bodyrotationRoll, 0.0F, 0.0F, 1.0F);
			GL11.glTranslatef((float) -baseLogic.rotcenter[0], (float) -baseLogic.rotcenter[1], (float) -baseLogic.rotcenter[2]);
			tankk.renderPart("obj1");
			if(entity.onGround){
				tankk.renderPart("obj8");
			}
			GL11.glTranslatef((float) perapos[0], (float) perapos[1], (float) perapos[2]);
			GL11.glRotatef((baseLogic.perapos + (baseLogic.perapos - baseLogic.prevperapos) * partialTicks), 0.0F, 0.0F, 1.0F);
			GL11.glTranslatef((float) -perapos[0], (float) -perapos[1], (float) -perapos[2]);
			tankk.renderPart("obj7");
			GL11.glPopMatrix();
		}
		
//		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
//		GL11.glPopMatrix();
//
//		GL11.glPushMatrix();
//		GL11.glTranslatef((float) p_76986_2_, (float) p_76986_4_, (float) p_76986_6_);
//		GL11.glRotatef(180F, 0.0F, 1.0F, 0.0F);
//		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
//
//		if(entity.deathTicks > 0){
//			GL11.glColor4f(0.1F, 0.1F, 0.1F, 1F);
//		}
//
//		GL11.glRotatef(180.0F - entityYaw, 0.0F, 1.0F, 0.0F);
//		GL11.glTranslatef(0, 2.5F, 0);
//		this.renderAngle(entity, 1);
//		GL11.glTranslatef(0, -2.5F, 0);
//			tankk.renderPart("mat1");
//		GL11.glRotatef(-(180.0F - entityYaw), 0.0F, 1.0F, 0.0F);
//
//		{
////			GL11.glRotatef(180.0F - entity.rotation, 0.0F, 1.0F, 0.0F);
//			tankk.renderPart("mat4");
//			GL11.glTranslatef(0F, 2.08F, 1.05F);
////			GL11.glRotatef(entity.rotationp, 1.0F, 0.0F, 0.0F);
//			GL11.glTranslatef(0F, -2.08F, -1.05F);
//			tankk.renderPart("mat5");
//		}
//
//		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
//		GL11.glPopMatrix();
	
	
	}
}

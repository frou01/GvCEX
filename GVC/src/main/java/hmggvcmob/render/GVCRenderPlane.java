
package hmggvcmob.render;

import hmvehicle.entity.parts.logics.PlaneBaseLogic;
import hmggvcmob.entity.friend.GVCEntityPlane;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.glAlphaFunc;

public class GVCRenderPlane extends Render {
	private static final ResourceLocation skeletonTexturesz = new ResourceLocation("gvcmob:textures/mob/MiG-21.png");
	private static final IModelCustom model = AdvancedModelLoader.loadModel(new ResourceLocation("gvcmob:textures/mob/MiG-21.mqo"));

	public GVCRenderPlane() {
	}

	public void doRender(Entity p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_,
			float p_76986_9_) {
		this.doRender((GVCEntityPlane) p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
	}

	/*private double func_110828_a(double p_110828_1_, double p_110828_3_, double p_110828_5_)
    {
        return p_110828_1_ + (p_110828_3_ - p_110828_1_) * p_110828_5_;
    }
	*/
	public void doRender(GVCEntityPlane entity, double p_76986_2_, double p_76986_4_, double p_76986_6_,
						 float entityYaw, float partialTicks) {
		PlaneBaseLogic planeBaseLogic = (PlaneBaseLogic) entity.getBaseLogic();
		GL11.glPushMatrix();
		GL11.glAlphaFunc(GL11.GL_GREATER, 0);
		GL11.glEnable(GL_BLEND);
		GL11.glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		bindEntityTexture(entity);
		GL11.glTranslatef((float) p_76986_2_, (float) p_76986_4_, (float) p_76986_6_);
		GL11.glTranslatef(0, 2.5f, 0);
		//this.renderAngle(entity, 1);
		GL11.glRotatef(180F, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(180.0F - (planeBaseLogic.bodyrotationYaw +(planeBaseLogic.bodyrotationYaw - planeBaseLogic.prevbodyrotationYaw) * partialTicks), 0.0F, 1.0F, 0.0F);
		GL11.glRotatef((planeBaseLogic.bodyrotationPitch +(planeBaseLogic.bodyrotationPitch - planeBaseLogic.prevbodyrotationPitch) * partialTicks), 1.0F, 0.0F, 0.0F);
		GL11.glRotatef((planeBaseLogic.bodyrotationRoll +(planeBaseLogic.bodyrotationRoll - planeBaseLogic.prevbodyrotationRoll) * partialTicks), 0.0F, 0.0F, 1.0F);
		GL11.glTranslatef(0, -2.5f, 8.1108f);

		int pass = MinecraftForgeClient.getRenderPass();
		if(pass == 1) {
			glEnable(GL_BLEND);
			glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
			GL11.glDepthMask(false);
			glAlphaFunc(GL_LESS, 1);
		}else {
			GL11.glDepthMask(true);
			glAlphaFunc(GL_EQUAL, 1);
		}
		if (!entity.onGround) {
			model.renderPart("leftWheelCover");
			model.renderPart("rightWheelCover");
		} else {
			model.renderPart("leftWheelCover");
			model.renderPart("rightWheelCover");
			model.renderPart("leftWheel");
			model.renderPart("rightWheel");
		}
		model.renderPart("body");
		model.renderPart("bone");
		model.renderPart("mainwing");
		for(int mid = 0;mid < entity.getsubTurrets().length;mid++){
			if(entity.getsubTurrets()[mid].readyload)
				model.renderPart("missile" + (mid + 1));
		}

		GL11.glPushMatrix();
		GL11.glTranslatef(1.9927f, 1.5105f, -10.7406f);
		GL11.glRotatef(planeBaseLogic.rollladder,120.7f,-2.89f,-30.68f);
		GL11.glTranslatef(-1.9927f, -1.5105f, 10.7406f);
		model.renderPart("LAileron");
		GL11.glPopMatrix();

		GL11.glPushMatrix();
		GL11.glTranslatef(-1.9927f, 1.5105f, -10.7406f);
		GL11.glRotatef(planeBaseLogic.rollladder,-120.7f,-2.89f,-30.68f);
		GL11.glTranslatef(1.9927f, -1.5105f, 10.7406f);
		model.renderPart("RAileron");
		GL11.glPopMatrix();

		GL11.glPushMatrix();
		GL11.glTranslatef(0.6834f, 1.5139f, -10.8821f);
		GL11.glRotatef(-10 * (float)planeBaseLogic.flaplevel/100,119.64f,-0.33f,0);
		GL11.glTranslatef(-0.6834f, -1.5139f, 10.8821f);
		model.renderPart("Lflap");
		GL11.glPopMatrix();

		GL11.glPushMatrix();
		GL11.glTranslatef(-0.6834f, 1.5139f, -10.8821f);
		GL11.glRotatef(10 * (float)planeBaseLogic.flaplevel/100,-119.64f,-0.33f,0);
		GL11.glTranslatef(0.6834f, -1.5139f, 10.8821f);
		model.renderPart("Rflap");
		GL11.glPopMatrix();

		GL11.glPushMatrix();
		GL11.glTranslatef(0.4882f, 1.5925f, -14.0326f);
		GL11.glRotatef(planeBaseLogic.pitchladder*3,125.85f,4.84f,-98.44f);
		GL11.glTranslatef(-0.4882f, -1.5925f, 14.0326f);
		model.renderPart("Lelevator");
		GL11.glPopMatrix();

		model.renderPart("tailwing");
		GL11.glPushMatrix();
		GL11.glTranslatef(-0.4882f, 1.5925f, -14.0326f);
		GL11.glRotatef(-planeBaseLogic.pitchladder*3,-125.85f,4.84f,-98.44f);
		GL11.glTranslatef(0.4882f, -1.5925f, 14.0326f);
		model.renderPart("Relevator");
		GL11.glPopMatrix();

		model.renderPart("tailwingV");
		GL11.glPushMatrix();
		GL11.glTranslatef(0, 2.4143f, -14.0213f);
		GL11.glRotatef(planeBaseLogic.yawladder*3,0,140.98f,-130.03f);
		GL11.glTranslatef(0, -2.4143f, 14.0213f);
		model.renderPart("YawLadder");
		GL11.glPopMatrix();

		if(planeBaseLogic.gearprogress > 10) {
			GL11.glPushMatrix();
			GL11.glTranslatef(1.6891f, 1.4900f, -9.4107f);
			GL11.glRotatef(-90 * (100 - planeBaseLogic.gearprogress) / 100, 1, 0, 1);
			GL11.glTranslatef(-1.6891f, -1.4900f, 9.4107f);
			model.renderPart("Lgear");
			GL11.glPopMatrix();

			GL11.glPushMatrix();
			GL11.glTranslatef(0.3463f, 1.0753f, -8.1108f);
			GL11.glRotatef(-90 * (100 - planeBaseLogic.gearprogress) / 100, 0, 0, 1);
			GL11.glTranslatef(-0.3463f, -1.0753f, 8.1108f);
			model.renderPart("LgearCover");
			GL11.glPopMatrix();

			GL11.glPushMatrix();
			GL11.glTranslatef(-1.6891f, 1.4900f, -9.4107f);
			GL11.glRotatef(90 * (100 - planeBaseLogic.gearprogress) / 100, -1, 0, 1);
			GL11.glTranslatef(1.6891f, -1.4900f, 9.4107f);
			model.renderPart("Rgear");
			GL11.glPopMatrix();

			GL11.glPushMatrix();
			GL11.glTranslatef(-0.3463f, 1.0753f, -8.1108f);
			GL11.glRotatef(90 * (100 - planeBaseLogic.gearprogress) / 100, 0, 0, 1);
			GL11.glTranslatef(0.3463f, -1.0753f, 8.1108f);
			model.renderPart("RgearCover");
			GL11.glPopMatrix();
		}

		GL11.glPushMatrix();
		GL11.glTranslatef(0, 0.9934f, -4.5152f);
		GL11.glRotatef(-90 * (100 - planeBaseLogic.gearprogress) / 100, 1, 0, 0);
		GL11.glTranslatef(0, -0.9934f, 4.5152f);
		model.renderPart("Cgear");
		GL11.glPopMatrix();

		GL11.glPushMatrix();
		GL11.glTranslatef(0.1606f, 1.0239f, -3.918f);
		GL11.glRotatef(-105 * (100 - planeBaseLogic.gearprogress) / 100, 0, 0.08715574274f, 0.99619469809f);
		GL11.glTranslatef(-0.1606f, -1.0239f, 3.918f);
		model.renderPart("CgearCoverL");
		GL11.glPopMatrix();

		GL11.glPushMatrix();
		GL11.glTranslatef(-0.1606f, 1.0239f, -3.918f);
		GL11.glRotatef(105 * (100 - planeBaseLogic.gearprogress) / 100, 0, 0.08715574274f, 0.99619469809f);
		GL11.glTranslatef(0.1606f, -1.0239f, 3.918f);
		model.renderPart("CgearCoverR");
		GL11.glPopMatrix();
		model.renderPart("pod");
		model.renderPart("incockpit");

		GL11.glPushMatrix();
		GL11.glTranslatef(0, 1.4627f, -4.3287f);
		GL11.glRotatef((planeBaseLogic.pitchladder), 1, 0, 0);
		GL11.glRotatef((planeBaseLogic.rollladder), 0, 0, 1);
		GL11.glTranslatef(0, -1.4627f, 4.3287f);
		model.renderPart("Lever");
		GL11.glPopMatrix();

		if(planeBaseLogic.throttle>9.5){
			float lastBrightnessX = OpenGlHelper.lastBrightnessX;
			float lastBrightnessY = OpenGlHelper.lastBrightnessY;
			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240, 240);
			RenderHelper.disableStandardItemLighting();
			GL11.glColor4f(1,1,1,(planeBaseLogic.throttle-9.5f)*2);
			model.renderPart("A/F");
			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)lastBrightnessX, (float)lastBrightnessY);
			RenderHelper.enableStandardItemLighting();
			GL11.glColor4f(1,1,1,1);
		}

		model.renderPart("canopi");
		GL11.glDepthMask(true);
		GL11.glAlphaFunc(GL11.GL_GREATER, 0);
		glDisable(GL_BLEND);
		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		GL11.glPopMatrix();
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity par1EntityLiving) {
		return this.skeletonTexturesz;
	}

}

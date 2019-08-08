
package hmggvcmob.render;

import cpw.mods.fml.client.FMLClientHandler;
import hmggvcmob.entity.friend.GVCEntityPMCHeli;
import handmadevehicle.entity.parts.logics.PlaneBaseLogic;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraftforge.client.MinecraftForgeClient;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

import static org.lwjgl.opengl.GL11.*;

public class GVCRenderPMCHeliobj extends Render {
	private static final ResourceLocation skeletonTexturesz = new ResourceLocation("gvcmob:textures/mob/kai/Mi-24.png");
	private static final ResourceLocation hudtexture = new ResourceLocation("gvcmob:textures/mob/kai/Mi-24.png");
	private static final IModelCustom tankk = AdvancedModelLoader.loadModel(new ResourceLocation("gvcmob:textures/mob/kai/Mi-24.mqo"));

	public GVCRenderPMCHeliobj() {
	}

	public void doRender(Entity p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_,
			float p_76986_9_) {
		this.doRender((GVCEntityPMCHeli) p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
	}

	/*private double func_110828_a(double p_110828_1_, double p_110828_3_, double p_110828_5_)
    {
        return p_110828_1_ + (p_110828_3_ - p_110828_1_) * p_110828_5_;
    }
	*/
	public void doRender(GVCEntityPMCHeli entity, double p_76986_2_, double p_76986_4_, double p_76986_6_,
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
		GL11.glTranslatef(0, -2.5f, 0);
		int pass = MinecraftForgeClient.getRenderPass();
		if(pass == 1) {

//		tankk.renderPart("HUDPlate");
			GL11.glDepthMask(true);
			{
				GL11.glPushMatrix();
				GL11.glTranslatef(-0.0022F, 5.0671f, -0.0027F);
				GL11.glRotatef((float) (planeBaseLogic.prevperapos + (planeBaseLogic.perapos - planeBaseLogic.prevperapos) * partialTicks), 0.0F, 1.0F, 0.0F);
				GL11.glTranslatef(0.0022F, -5.0671f, 0.0027F);
				glColor4f(1, 1, 1, (float) 1 - (planeBaseLogic.throttle / 5f) / 3);
				tankk.renderPart("mainrot");
				glColor4f(1, 1, 1, (float) (planeBaseLogic.throttle / 5f) / 3);
				GL11.glDepthMask(false);
				tankk.renderPart("maineffect");
				GL11.glDepthMask(true);
				GL11.glPopMatrix();
			}
			{
				GL11.glPushMatrix();
				GL11.glTranslatef(-0.7575F, 4.8072f, -13.2466f);
				GL11.glRotatef( (planeBaseLogic.prevperapos + (planeBaseLogic.perapos - planeBaseLogic.prevperapos) * partialTicks) * 3, 1.0F, 0.0F, 0.0F);
				GL11.glTranslatef(0.7575F, -4.8072f, 13.2466f);
				glColor4f(1, 1, 1, (float) 1 - (planeBaseLogic.throttle / 5f) / 3);
				tankk.renderPart("tail");
				glColor4f(1, 1, 1, (float) (planeBaseLogic.throttle / 5f) / 3);
				GL11.glDepthMask(false);
				tankk.renderPart("taileffect");
				GL11.glDepthMask(true);
				GL11.glPopMatrix();
			}
			glColor4f(1, 1, 1, 1);
			if (planeBaseLogic.ispilot(FMLClientHandler.instance().getClientPlayerEntity()) && FMLClientHandler.instance().getClient().gameSettings.thirdPersonView == 0) {
				GL11.glDepthMask(false);
				tankk.renderPart("HUDPlate");
				GL11.glDisable(GL11.GL_LIGHTING);
				float lastBrightnessX = OpenGlHelper.lastBrightnessX;
				float lastBrightnessY = OpenGlHelper.lastBrightnessY;
				OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240, 240);
				glDisable(GL_DEPTH_TEST);
				tankk.renderPart("sight");
				glEnable(GL_DEPTH_TEST);
				GL11.glEnable(GL11.GL_LIGHTING);
				OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)lastBrightnessX, (float)lastBrightnessY);
			}
//			if (planeBaseLogic.riddenByEntities[1] == FMLClientHandler.instance().getClientPlayerEntity() && FMLClientHandler.instance().getClient().gameSettings.thirdPersonView == 0) {
//
//			}
			{
				GL11.glPushMatrix();
				GL11.glTranslatef(0.1095f,1.128f,6.517f);
				GL11.glRotatef((float) -planeBaseLogic.seatInfos[1].maingun.turretrotationYaw,0,1,0);
				GL11.glTranslatef(-0.1095f,-1.128f,-6.517f);
				tankk.renderPart("GunnerHudBase");
				GL11.glTranslatef(0.1117f,1.377f,6.517f);
				GL11.glRotatef((float) planeBaseLogic.seatInfos[1].maingun.turretrotationPitch,1,0,0);
				GL11.glTranslatef(-0.1117f,-1.377f,-6.517f);
				tankk.renderPart("GunnerHudBase2");
				
				glClear(GL_STENCIL_BUFFER_BIT);
				glEnable(GL_STENCIL_TEST);
				glStencilMask(1);
				
				glStencilFunc(
						GL_ALWAYS,   // GLenum func
						1,          // GLint ref
						~0);// GLuint mask
				glStencilOp(
						GL_KEEP,
						GL_KEEP,
						GL_REPLACE);
				GL11.glDepthMask(false);
				tankk.renderPart("GunnerHudPlate");
				GL11.glPopMatrix();
				
				GL11.glDisable(GL11.GL_LIGHTING);
				float lastBrightnessX = OpenGlHelper.lastBrightnessX;
				float lastBrightnessY = OpenGlHelper.lastBrightnessY;
				OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240, 240);
				glDisable(GL_DEPTH_TEST);
				
				GL11.glPushMatrix();
				GL11.glTranslatef(0.1176f,0.4499f,6.443f);
				GL11.glRotatef((float) -planeBaseLogic.seatInfos[1].maingun.turretrotationYaw,0,1,0);
				GL11.glTranslatef(-0.1176f,-0.4499f,-6.443f);
				GL11.glTranslatef(0,0.8190f,6.4489f);
				GL11.glRotatef((float) planeBaseLogic.seatInfos[1].maingun.turretrotationPitch,1,0,0);
				GL11.glTranslatef(0,-0.8190f,-6.4489f);
				
				
				glStencilFunc(
						GL_EQUAL,   // GLenum func
						1,          // GLint ref
						~0);// GLuint mask
				glStencilOp(GL_KEEP, GL_KEEP, GL_KEEP);
				tankk.renderPart("GunnerReticle");
				glDisable(GL_STENCIL_TEST);
				GL11.glPopMatrix();
				glEnable(GL_DEPTH_TEST);
				GL11.glEnable(GL11.GL_LIGHTING);
				OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)lastBrightnessX, (float)lastBrightnessY);
			}
			GL11.glDisable(GL_BLEND);
		}else {
			GL11.glDepthMask(true);
			if (planeBaseLogic.gearprogress < 10) {
				tankk.renderPart("leftWheelCover");
				tankk.renderPart("rightWheelCover");
			} else {
				tankk.renderPart("leftWheelCover");
				tankk.renderPart("rightWheelCover");
				tankk.renderPart("leftWheel");
				tankk.renderPart("rightWheel");
			}
			tankk.renderPart("body");
			tankk.renderPart("leftwing");
			tankk.renderPart("rightwing");
			tankk.renderPart("GunpodR");
			tankk.renderPart("GunpodL");
			tankk.renderPart("RocketpodR");
			tankk.renderPart("RocketpodL");
			tankk.renderPart("frontWheel");
			tankk.renderPart("pilot_canopi_bone");
			tankk.renderPart("gunner_canopi_bone");
			tankk.renderPart("exhaust_port");

			{
				GL11.glPushMatrix();
				GL11.glTranslatef(0.1176f,0.4499f,6.443f);
				GL11.glRotatef((float) -planeBaseLogic.seatInfos[1].maingun.turretrotationYaw,0,1,0);
				GL11.glTranslatef(-0.1176f,-0.4499f,-6.443f);
				tankk.renderPart("FgunTurretY");
				GL11.glTranslatef(0,0.8190f,6.4489f);
				GL11.glRotatef((float) planeBaseLogic.seatInfos[1].maingun.turretrotationPitch,1,0,0);
				GL11.glTranslatef(0,-0.8190f,-6.4489f);
				tankk.renderPart("FgunTurretP");
				tankk.renderPart("Fgun");
				GL11.glPopMatrix();
			}
			boolean flag = true;
//			for (EntityChild aseat : entity.childEntities) {
//				if (aseat != null) {
//					if (aseat.idinmasterEntityt == 4) {
//						break;
//					}
//					if (aseat.riddenByEntity != null) {
//						flag = false;
//						break;
//					}
//				}
//			}
			if (flag) tankk.renderPart("door");
			tankk.renderPart("pilotseat");
			tankk.renderPart("gunnerseat");
			tankk.renderPart("inside");
			GL11.glDepthMask(false);
			tankk.renderPart("gunner_canpi_glass");
			tankk.renderPart("pilot_canopi_Glass");
			GL11.glDepthMask(true);
		}
		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		GL11.glPopMatrix();
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity par1EntityLiving) {
		return this.skeletonTexturesz;
	}

}

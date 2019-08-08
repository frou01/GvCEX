package hmgww2.render;

import cpw.mods.fml.client.FMLClientHandler;
import handmadevehicle.entity.parts.ITank;
import handmadevehicle.entity.parts.ModifiedBoundingBox;
import handmadevehicle.entity.parts.logics.TankBaseLogic;
import handmadevehicle.entity.parts.turrets.TurretObj;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import static handmadevehicle.CLProxy.drawOutlinedBoundingBox;
import static net.minecraft.client.renderer.entity.RenderManager.debugBoundingBox;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;

public class RenderTank extends Render {
	
	private ResourceLocation skeletonTexturesz = new ResourceLocation("hmgww2:textures/mob/rus/T34_76.png");
	private IModelCustom tankk = AdvancedModelLoader.loadModel(new ResourceLocation("hmgww2:textures/mob/rus/T34_76.obj"));
	
	float[] turretpos = new float[3];
	float[] turretpitchpos = new float[3];
	boolean user_onMainTurret = false;
	boolean hassubturret = false;
	boolean subturret_onMainTurret = false;
	float[] subturretpos = new float[3];
	float[] subturretpitchpos = new float[3];
	
	public RenderTank(String texture,String model,boolean user_onMainTurret) {
		skeletonTexturesz = new ResourceLocation(texture);
		tankk = AdvancedModelLoader.loadModel(new ResourceLocation(model));
		this.user_onMainTurret = user_onMainTurret;
	}
	public RenderTank(String texture,String model,boolean user_onMainTurret,boolean subturret_onMainTurret,float[] subturretpos,float[] subturretpitchpos) {
		this(texture,model,user_onMainTurret);
		this.hassubturret = true;
		this.subturret_onMainTurret = subturret_onMainTurret;
		this.subturretpos = subturretpos;
		this.subturretpitchpos = subturretpitchpos;
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
		if(entity instanceof ITank){
			this.bindEntityTexture(entity);
			GL11.glPushMatrix();
			GL11.glTranslatef((float) p_76986_2_, (float) p_76986_4_, (float) p_76986_6_);
			GL11.glRotatef(180F, 0.0F, 1.0F, 0.0F);
			GL11.glEnable(GL12.GL_RESCALE_NORMAL);
			TankBaseLogic baseLogic = (TankBaseLogic) ((ITank) entity).getBaseLogic();
			GL11.glRotatef(180.0F - (baseLogic.bodyrotationYaw + (baseLogic.bodyrotationYaw - baseLogic.prevbodyrotationYaw) * partialTicks), 0.0F, 1.0F, 0.0F);
			GL11.glRotatef(baseLogic.bodyrotationPitch, 1.0F, 0.0F, 0.0F);
			GL11.glRotatef(baseLogic.bodyrotationRoll, 0.0F, 0.0F, 1.0F);
			GL11.glPushMatrix();
			tankk.renderPart("mat1");
			tankk.renderPart("obj1");
			if(!user_onMainTurret && ((ITank) entity).standalone()){
				tankk.renderPart("mat30");
				tankk.renderPart("obj30");
			}
			if(hassubturret && !subturret_onMainTurret){
				GL11.glPushMatrix();
				TurretObj subturret = ((ITank) entity).getTurrets()[1];
				GL11.glTranslatef(subturretpos[0], subturretpos[1], subturretpos[2]);
				GL11.glRotatef((float) -(subturret.turretrotationYaw), 0.0F, 1.0F, 0.0F);
				GL11.glTranslatef(-subturretpos[0], -subturretpos[1], -subturretpos[2]);
				tankk.renderPart("mat6");
				tankk.renderPart("obj6");
				GL11.glTranslatef(subturretpitchpos[0], subturretpitchpos[1], subturretpitchpos[2]);
				GL11.glRotatef((float) subturret.turretrotationPitch, 1.0F, 0.0F, 0.0F);
				GL11.glTranslatef(-subturretpitchpos[0], -subturretpitchpos[1], -subturretpitchpos[2]);
				tankk.renderPart("mat7");
				tankk.renderPart("obj7");
				GL11.glPopMatrix();
			}
			//GL11.glRotatef(-(180.0F - entityYaw), 0.0F, 1.0F, 0.0F);
			
			TurretObj main = ((ITank) entity).getMainTurret();
			if(main != null){
				GL11.glTranslatef((float) main.onMotherPos.x, (float) main.onMotherPos.y, (float) -main.onMotherPos.z);
				GL11.glTranslatef((float) main.turretYawCenterpos.x, (float) main.turretYawCenterpos.y, (float) main.turretYawCenterpos.z);
				GL11.glRotatef((float) -(main.turretrotationYaw), 0.0F, 1.0F, 0.0F);
				GL11.glTranslatef((float) -main.turretYawCenterpos.x, (float) -main.turretYawCenterpos.y, (float) -main.turretYawCenterpos.z);
				GL11.glTranslatef((float) -main.onMotherPos.x, (float) -main.onMotherPos.y, (float) main.onMotherPos.z);
				tankk.renderPart("mat4");
				tankk.renderPart("obj4");
				if(user_onMainTurret && ((ITank) entity).standalone()){
					tankk.renderPart("mat30");
					tankk.renderPart("obj30");
				}
				if(hassubturret && subturret_onMainTurret){
					GL11.glPushMatrix();
					TurretObj subturret = ((ITank) entity).getTurrets()[1];
					GL11.glTranslatef(subturretpos[0], subturretpos[1], subturretpos[2]);
					GL11.glRotatef((float) -(subturret.turretrotationYaw), 0.0F, 1.0F, 0.0F);
					GL11.glTranslatef(-subturretpos[0], -subturretpos[1], -subturretpos[2]);
					tankk.renderPart("mat6");
					tankk.renderPart("obj6");
					GL11.glTranslatef(subturretpitchpos[0], subturretpitchpos[1], subturretpitchpos[2]);
					GL11.glRotatef((float) subturret.turretrotationPitch, 1.0F, 0.0F, 0.0F);
					GL11.glTranslatef(-subturretpitchpos[0], -subturretpitchpos[1], -subturretpitchpos[2]);
					tankk.renderPart("mat7");
					tankk.renderPart("obj7");
					GL11.glPopMatrix();
				}
				GL11.glTranslatef((float) main.onMotherPos.x, (float) main.onMotherPos.y, (float) -main.onMotherPos.z);
				GL11.glTranslatef((float) main.turretPitchCenterpos.x, (float) main.turretPitchCenterpos.y, (float) main.turretPitchCenterpos.z);
//				GL11.glTranslatef(turretpitchpos[0], turretpitchpos[1], turretpitchpos[2]);
				GL11.glRotatef((float) main.turretrotationPitch, 1.0F, 0.0F, 0.0F);
//				GL11.glTranslatef(-turretpitchpos[0], -turretpitchpos[1], -turretpitchpos[2]);
				GL11.glTranslatef((float) -main.turretPitchCenterpos.x, (float) -main.turretPitchCenterpos.y, (float) -main.turretPitchCenterpos.z);
				GL11.glTranslatef((float) -main.onMotherPos.x, (float) -main.onMotherPos.y, (float) main.onMotherPos.z);
				tankk.renderPart("mat5");
				tankk.renderPart("obj5");
				if(baseLogic.riddenbyPlayer() && FMLClientHandler.instance().getClient().gameSettings.thirdPersonView == 0) {
					float lastBrightnessX = OpenGlHelper.lastBrightnessX;
					float lastBrightnessY = OpenGlHelper.lastBrightnessY;
					OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240, 240);
					RenderHelper.disableStandardItemLighting();
					GL11.glDepthMask(true);
					GL11.glDisable(GL_DEPTH_TEST);
					GL11.glDepthFunc(GL11.GL_ALWAYS);//強制描画
					tankk.renderPart("sight");
					GL11.glDepthFunc(GL11.GL_LEQUAL);
					GL11.glEnable(GL_DEPTH_TEST);
					RenderHelper.enableStandardItemLighting();
					OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)lastBrightnessX, (float)lastBrightnessY);
				}
			}
			GL11.glPopMatrix();
			GL11.glPushMatrix();
			if(debugBoundingBox && entity.boundingBox instanceof ModifiedBoundingBox){
				GL11.glDepthMask(false);
				GL11.glDisable(GL11.GL_TEXTURE_2D);
				GL11.glDisable(GL11.GL_LIGHTING);
				GL11.glDisable(GL11.GL_CULL_FACE);
				GL11.glDisable(GL11.GL_BLEND);
				ModifiedBoundingBox axisalignedbb = (ModifiedBoundingBox) entity.boundingBox;
				drawOutlinedBoundingBox(axisalignedbb, 16777215);
				GL11.glEnable(GL11.GL_TEXTURE_2D);
				GL11.glEnable(GL11.GL_LIGHTING);
				GL11.glEnable(GL11.GL_CULL_FACE);
				GL11.glDisable(GL11.GL_BLEND);
				GL11.glDepthMask(true);
			}
			GL11.glPopMatrix();
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

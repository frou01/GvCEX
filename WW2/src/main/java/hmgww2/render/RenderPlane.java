package hmgww2.render;

import handmadevehicle.Utils;
import handmadevehicle.entity.parts.Hasmode;
import handmadevehicle.entity.parts.IMultiTurretVehicle;
import handmadevehicle.entity.parts.IPlane;
import handmadevehicle.entity.parts.ModifiedBoundingBox;
import handmadevehicle.entity.parts.logics.PlaneBaseLogic;
import handmadevehicle.entity.parts.turrets.TurretObj;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import javax.vecmath.Quat4d;
import javax.vecmath.Vector3d;
import java.util.ArrayList;

import static handmadevehicle.CLProxy.drawOutlinedBoundingBox;
import static java.lang.Math.toDegrees;
import static net.minecraft.client.renderer.entity.RenderManager.debugBoundingBox;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_EQUAL;
import static org.lwjgl.opengl.GL11.glAlphaFunc;

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
		if(entity instanceof IPlane){
			PlaneBaseLogic baseLogic = (PlaneBaseLogic) ((IPlane) entity).getBaseLogic();
			int pass = MinecraftForgeClient.getRenderPass();
			this.bindEntityTexture(entity);
			baseLogic.riderPosUpdate_forRender(new Vector3d(entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * partialTicks,
					                                               entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * partialTicks,
					                                               entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * partialTicks));
			GL11.glPushMatrix();
			GL11.glTranslatef((float) p_76986_2_, (float) p_76986_4_, (float) p_76986_6_);
			GL11.glRotatef(180F, 0.0F, 1.0F, 0.0F);
			GL11.glEnable(GL12.GL_RESCALE_NORMAL);
			GL11.glTranslatef((float) baseLogic.planeInfo.rotcenter[0], (float) baseLogic.planeInfo.rotcenter[1], (float) baseLogic.planeInfo.rotcenter[2]);
			
			Quat4d tempquat = new Quat4d();
			tempquat.interpolate(baseLogic.prevbodyRot,baseLogic.bodyRot, partialTicks);
			double[] xyz = Utils.eulerfrommatrix(Utils.matrixfromQuat(tempquat));
//			baseLogic.riderPosUpdate_forRender(new Vector3d(entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * partialTicks,
//					                                               entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * partialTicks,
//					                                               entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * partialTicks));
			baseLogic.bodyrotationYaw = (float) toDegrees(xyz[1]);
			baseLogic.bodyrotationPitch = (float) toDegrees(xyz[0]);
			baseLogic.bodyrotationRoll = (float) toDegrees(xyz[2]);
			
			GL11.glRotatef(180.0F - (baseLogic.bodyrotationYaw), 0.0F, 1.0F, 0.0F);
			GL11.glRotatef(baseLogic.bodyrotationPitch, 1.0F, 0.0F, 0.0F);
			GL11.glRotatef(baseLogic.bodyrotationRoll, 0.0F, 0.0F, 1.0F);
			GL11.glTranslatef((float) -baseLogic.planeInfo.rotcenter[0], (float) -baseLogic.planeInfo.rotcenter[1], (float) -baseLogic.planeInfo.rotcenter[2]);
			GL11.glPushMatrix();
			
			if(pass == 1) {
				glEnable(GL_BLEND);
				glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
				GL11.glDepthMask(false);
				glAlphaFunc(GL_LESS, 1);
			}else {
				GL11.glDepthMask(true);
				glAlphaFunc(GL_EQUAL, 1);
			}
			
			tankk.renderPart("obj1");
			tankk.renderPart("mat1");
			
			if(baseLogic.serverx){
				tankk.renderPart("AirBrake");
			}else {
				tankk.renderPart("AirBrakeFold");
			}
			
			if(((Hasmode) entity).standalone()){
				tankk.renderPart("obj30");
				tankk.renderPart("mat30");
			}
			if(baseLogic.gearprogress > 10){
				tankk.renderPart("obj8");
				tankk.renderPart("mat8");
			}
			GL11.glPushMatrix();
			{
				GL11.glTranslatef((float) perapos[0], (float) perapos[1], (float) perapos[2]);
				GL11.glRotatef((baseLogic.perapos + (baseLogic.perapos - baseLogic.prevperapos) * partialTicks), 0.0F, 0.0F, 1.0F);
				GL11.glTranslatef((float) -perapos[0], (float) -perapos[1], (float) -perapos[2]);
				tankk.renderPart("obj7");
				tankk.renderPart("mat7");
			}
			GL11.glPopMatrix();
			GL11.glPushMatrix();
			if(baseLogic.turrets != null && entity instanceof IMultiTurretVehicle){
				
				TurretObj[] turretObjs = baseLogic.turrets;
				
				for(int i = 0;i < turretObjs.length;i++){
					TurretObj aturretobj = turretObjs[i];
					GL11.glPushMatrix();
					GL11.glTranslatef((float)aturretobj.onMotherPos.x,(float)aturretobj.onMotherPos.y,(float)-aturretobj.onMotherPos.z);
					GL11.glRotatef((float) -aturretobj.turretrotationYaw, 0.0F, 1.0F, 0.0F);
					GL11.glTranslatef((float)-aturretobj.onMotherPos.x,(float)-aturretobj.onMotherPos.y,(float)aturretobj.onMotherPos.z);
					tankk.renderPart("Turret" + i);
					GL11.glTranslatef((float)aturretobj.onMotherPos.x,(float)aturretobj.onMotherPos.y,(float)-aturretobj.onMotherPos.z);
					GL11.glTranslatef((float)aturretobj.prefab_turret.turretPitchCenterpos.x,(float)aturretobj.prefab_turret.turretPitchCenterpos.y,(float)aturretobj.prefab_turret.turretPitchCenterpos.z);
					GL11.glRotatef((float) aturretobj.turretrotationPitch, 1.0F, 0.0F, 0.0F);
					GL11.glTranslatef((float)-aturretobj.prefab_turret.turretPitchCenterpos.x,(float)-aturretobj.prefab_turret.turretPitchCenterpos.y,(float)-aturretobj.prefab_turret.turretPitchCenterpos.z);
					GL11.glTranslatef((float)-aturretobj.onMotherPos.x,(float)-aturretobj.onMotherPos.y,(float)aturretobj.onMotherPos.z);
					tankk.renderPart("Turret" + i + "Cannon");
					
					if(aturretobj.readyload)tankk.renderPart("Turret" + i + "Mag");
					
					renderchild(aturretobj.getChilds(),"Turret" + i);
					
					if(aturretobj.prefab_turret.hasReflexSight) {
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
						if (pass != 1) {
							GL11.glDepthMask(false);
							glAlphaFunc(GL_ALWAYS, 1);
							glColorMask(
									false,   // GLboolean red
									false,   // GLboolean green
									false,   // GLboolean blue
									false);
						}
						tankk.renderPart("ReticlePlate" + i);
						if (pass != 1) {
							GL11.glDepthMask(true);
							glAlphaFunc(GL_EQUAL, 1);
							glColorMask(
									true,   // GLboolean red
									true,   // GLboolean green
									true,   // GLboolean blue
									true);
						}
						
						GL11.glDisable(GL11.GL_LIGHTING);
						float lastBrightnessX = OpenGlHelper.lastBrightnessX;
						float lastBrightnessY = OpenGlHelper.lastBrightnessY;
						OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240, 240);
						glDisable(GL_DEPTH_TEST);
						
						
						glStencilFunc(
								GL_EQUAL,   // GLenum func
								1,          // GLint ref
								~0);// GLuint mask
						
						glStencilOp(GL_KEEP, GL_KEEP, GL_KEEP);
						glAlphaFunc(GL_ALWAYS, 1);
						GL11.glDepthMask(false);
						
						GL11.glDepthFunc(GL11.GL_ALWAYS);//強制描画
						tankk.renderPart("Reticle" + i);
						GL11.glDepthFunc(GL11.GL_LEQUAL);
						GL11.glDepthMask(true);
						glDisable(GL_STENCIL_TEST);
						glEnable(GL_DEPTH_TEST);
						GL11.glEnable(GL11.GL_LIGHTING);
						OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)lastBrightnessX, (float)lastBrightnessY);
						if(pass == 1) {
							glEnable(GL_BLEND);
							glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
							GL11.glDepthMask(false);
							glAlphaFunc(GL_LESS, 1);
						}else {
							GL11.glDepthMask(true);
							glAlphaFunc(GL_EQUAL, 1);
						}
					}
					GL11.glPopMatrix();
				}
			}
			
//			if (baseLogic.ispilot(FMLClientHandler.instance().getClientPlayerEntity())&& FMLClientHandler.instance().getClient().gameSettings.thirdPersonView == 0)
			{
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
				if (pass != 1) {
					GL11.glDepthMask(false);
					glAlphaFunc(GL_ALWAYS, 1);
					glColorMask(
							false,   // GLboolean red
							false,   // GLboolean green
							false,   // GLboolean blue
							false);
				}
				tankk.renderPart("sightPlate");
				if (pass != 1) {
					GL11.glDepthMask(true);
					glAlphaFunc(GL_EQUAL, 1);
					glColorMask(
							true,   // GLboolean red
							true,   // GLboolean green
							true,   // GLboolean blue
							true);
				}
				
				GL11.glDisable(GL11.GL_LIGHTING);
				float lastBrightnessX = OpenGlHelper.lastBrightnessX;
				float lastBrightnessY = OpenGlHelper.lastBrightnessY;
				OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240, 240);
				glDisable(GL_DEPTH_TEST);
				
				
				glStencilFunc(
						GL_EQUAL,   // GLenum func
						1,          // GLint ref
						~0);// GLuint mask
				
				glStencilOp(GL_KEEP, GL_KEEP, GL_KEEP);
				glAlphaFunc(GL_ALWAYS, 1);
				GL11.glDepthMask(false);
				
				GL11.glDepthFunc(GL11.GL_ALWAYS);//強制描画
				tankk.renderPart("sight");
				GL11.glDepthFunc(GL11.GL_LEQUAL);
				GL11.glDepthMask(true);
				glDisable(GL_STENCIL_TEST);
				glEnable(GL_DEPTH_TEST);
				GL11.glEnable(GL11.GL_LIGHTING);
				OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)lastBrightnessX, (float)lastBrightnessY);
				if(pass == 1) {
					glEnable(GL_BLEND);
					glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
					GL11.glDepthMask(false);
					glAlphaFunc(GL_LESS, 1);
				}else {
					GL11.glDepthMask(true);
					glAlphaFunc(GL_EQUAL, 1);
				}
			}
			
			GL11.glPopMatrix();
			GL11.glDepthMask(true);
			GL11.glAlphaFunc(GL11.GL_GREATER, 0);
			glDisable(GL_BLEND);
			GL11.glDisable(GL12.GL_RESCALE_NORMAL);
			
			
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
	
	private void renderchild(ArrayList<TurretObj> childturretObjs, String motherID){
		for(int ic = 0;ic < childturretObjs.size();ic++){
			TurretObj achild = childturretObjs.get(ic);
			GL11.glPushMatrix();
			GL11.glTranslatef((float)achild.onMotherPos.x,(float)achild.onMotherPos.y,(float)-achild.onMotherPos.z);
			GL11.glRotatef((float) achild.turretrotationYaw, 1.0F, 0.0F, 0.0F);
			GL11.glTranslatef((float)-achild.onMotherPos.x,(float)-achild.onMotherPos.y,(float)achild.onMotherPos.z);
			tankk.renderPart(motherID + "child" + ic);
			GL11.glTranslatef((float)achild.onMotherPos.x,(float)achild.onMotherPos.y,(float)-achild.onMotherPos.z);
			GL11.glTranslatef((float)achild.prefab_turret.turretPitchCenterpos.x,(float)achild.prefab_turret.turretPitchCenterpos.y,(float)-achild.prefab_turret.turretPitchCenterpos.z);
			GL11.glRotatef((float) achild.turretrotationYaw, 1.0F, 0.0F, 0.0F);
			GL11.glTranslatef((float)-achild.prefab_turret.turretPitchCenterpos.x,(float)-achild.prefab_turret.turretPitchCenterpos.y,(float)achild.prefab_turret.turretPitchCenterpos.z);
			GL11.glTranslatef((float)-achild.onMotherPos.x,(float)-achild.onMotherPos.y,(float)achild.onMotherPos.z);
			tankk.renderPart(motherID + "child" + ic + "Cannon");
			renderchild(achild.getChilds(),motherID + "child" + ic);
			GL11.glPopMatrix();
		}
	}
}

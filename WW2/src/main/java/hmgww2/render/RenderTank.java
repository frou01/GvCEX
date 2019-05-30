package hmgww2.render;

import hmvehicle.entity.parts.ITank;
import hmvehicle.entity.parts.logics.TankBaseLogic;
import hmvehicle.entity.parts.turrets.TurretObj;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

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
	
	public RenderTank(String texture,String model,float[] turretpos,float[] turretpitchpos,boolean user_onMainTurret) {
		skeletonTexturesz = new ResourceLocation(texture);
		tankk = AdvancedModelLoader.loadModel(new ResourceLocation(model));
		this.turretpos = turretpos;
		this.turretpitchpos = turretpitchpos;
		this.user_onMainTurret = user_onMainTurret;
	}
	public RenderTank(String texture,String model,float[] turretpos,float[] turretpitchpos,boolean user_onMainTurret,boolean subturret_onMainTurret,float[] subturretpos,float[] subturretpitchpos) {
		this(texture,model,turretpos,turretpitchpos,user_onMainTurret);
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
				GL11.glTranslatef(turretpos[0], turretpos[1], turretpos[2]);
				GL11.glRotatef((float) -(main.turretrotationYaw), 0.0F, 1.0F, 0.0F);
				GL11.glTranslatef(-turretpos[0], -turretpos[1], -turretpos[2]);
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
				GL11.glTranslatef(turretpitchpos[0], turretpitchpos[1], turretpitchpos[2]);
				GL11.glRotatef((float) main.turretrotationPitch, 1.0F, 0.0F, 0.0F);
				GL11.glTranslatef(-turretpitchpos[0], -turretpitchpos[1], -turretpitchpos[2]);
				tankk.renderPart("mat5");
				tankk.renderPart("obj5");
			}
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

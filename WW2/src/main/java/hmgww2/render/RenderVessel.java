package hmgww2.render;

import hmgww2.entity.EntityBases_Ship;
import handmadevehicle.entity.parts.IMultiTurretVehicle;
import handmadevehicle.entity.parts.ITank;
import handmadevehicle.entity.parts.ModifiedBoundingBox;
import handmadevehicle.entity.parts.logics.TankBaseLogicLogic;
import handmadevehicle.entity.parts.turrets.TurretObj;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import java.util.ArrayList;

import static handmadevehicle.CLProxy.drawOutlinedBoundingBox;
import static net.minecraft.client.renderer.entity.RenderManager.debugBoundingBox;

public class RenderVessel extends Render {
	
	private ResourceLocation skeletonTexturesz = new ResourceLocation("hmgww2:textures/mob/rus/T34_76.png");
	private IModelCustom tankk = AdvancedModelLoader.loadModel(new ResourceLocation("hmgww2:textures/mob/rus/T34_76.obj"));
	
	public RenderVessel(String texture, String model) {
		skeletonTexturesz = new ResourceLocation(texture);
		tankk = AdvancedModelLoader.loadModel(new ResourceLocation(model));
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
		if(entity instanceof IMultiTurretVehicle && entity instanceof EntityBases_Ship){
			this.bindEntityTexture(entity);
			GL11.glPushMatrix();
			GL11.glTranslatef((float) p_76986_2_, (float) p_76986_4_, (float) p_76986_6_);
			GL11.glRotatef(180F, 0.0F, 1.0F, 0.0F);
			GL11.glEnable(GL12.GL_RESCALE_NORMAL);
			TankBaseLogicLogic baseLogic = (TankBaseLogicLogic) ((ITank) entity).getBaseLogic();
			GL11.glRotatef(180.0F - (baseLogic.bodyrotationYaw + (baseLogic.bodyrotationYaw - baseLogic.prevbodyrotationYaw) * partialTicks), 0.0F, 1.0F, 0.0F);
			GL11.glRotatef(baseLogic.bodyrotationPitch, 1.0F, 0.0F, 0.0F);
			GL11.glRotatef(baseLogic.bodyrotationRoll, 0.0F, 0.0F, 1.0F);
			tankk.renderPart("mat1");
			tankk.renderPart("obj1");
			if(((EntityBases_Ship) entity).usingSP){
				tankk.renderPart("SPSymbol");
			}else {
				tankk.renderPart("notSPSymbol");
			}
			TurretObj[] turretObjs = ((ITank) entity).getmotherTurrets();
			if(turretObjs != null)
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
				renderchild(aturretobj.getChilds(),"Turret" + i);
				GL11.glPopMatrix();
			}
			//GL11.glRotatef(-(180.0F - entityYaw), 0.0F, 1.0F, 0.0F);
			
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
	
	private void renderchild(ArrayList<TurretObj> childturretObjs,String motherID){
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

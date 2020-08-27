package hmggvcmob.ai;

import handmadevehicle.entity.EntityDummy_rider;
import handmadevehicle.entity.EntityVehicle;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;

import javax.vecmath.Vector3d;
import java.util.Random;

import static handmadevehicle.Utils.canMoveEntity;

public class EntityAndPos {
	public EntityLiving entity;
	double currentSpeed = 0;
	Vector3d pos = new Vector3d();
	Vector3d randMiserVec;
	public EntityAndPos(EntityLiving entity){
		this.entity = entity;
		pos.set(this.entity.posX,this.entity.posY,this.entity.posZ);
		Random random = new Random();
		randMiserVec = new Vector3d(random.nextDouble(),0,random.nextDouble());
		randMiserVec.scale(random.nextDouble() * 8 - 4);
	}
	public Vector3d getPos(){
		return pos;
	}
	public double[] getArrayPos(){
		return new double[]{pos.x,pos.y,pos.z};
	}

	public final void set(double x, double y, double z) {
		pos.set(x,y,z);
	}
	public final void set(Vector3d vector3d,double speed) {
		if(entity.getAttackTarget() == null && canMoveEntity(entity)) {
			pos.set(vector3d.x, vector3d.y, vector3d.z);
			entity.getNavigator().tryMoveToXYZ(pos.x, pos.y, pos.z, currentSpeed = speed);
			if(entity.ridingEntity != null && entity.ridingEntity instanceof EntityDummy_rider){
				((EntityDummy_rider) entity.ridingEntity).linkedBaseLogic.mc_Entity.getNavigator().tryMoveToXYZ(vector3d.x, vector3d.y, vector3d.z, currentSpeed = speed);
				if(entity.getNavigator().getPath() == null)entity.getNavigator().setPath(((EntityDummy_rider) entity.ridingEntity).linkedBaseLogic.mc_Entity.getNavigator().getPath(),currentSpeed = speed);
			}
		}
	}
	public final void set_withRand(Vector3d vector3d,double speed) {
		vector3d.add(randMiserVec);
		this.set(vector3d,speed);
	}
	public int repathCool = 0;
	public void update(){
		if(repathCool<0 || entity.getNavigator().noPath()) {
			repathCool = 10;
			if (currentSpeed == 0) currentSpeed = 1;
			entity.getNavigator().tryMoveToXYZ(pos.x, pos.y, pos.z, currentSpeed);
			if (entity.ridingEntity != null && entity.ridingEntity instanceof EntityDummy_rider) {
				((EntityDummy_rider) entity.ridingEntity).linkedBaseLogic.mc_Entity.getNavigator().tryMoveToXYZ(pos.x, pos.y, pos.z, currentSpeed);
				if (entity.getNavigator().getPath() == null) {
					entity.getNavigator().setPath(((EntityDummy_rider) entity.ridingEntity).linkedBaseLogic.mc_Entity.getNavigator().getPath(), currentSpeed);
				}
			}
		}else {
			repathCool--;
		}
	}
}

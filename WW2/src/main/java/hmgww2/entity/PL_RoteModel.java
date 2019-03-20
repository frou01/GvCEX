package hmgww2.entity;

import gvclib.entity.EntityBases;

public class PL_RoteModel {
	protected static void rotemodel(EntityBases entity, float f){
		entity.rotationYawHead = entity.rotationYaw + f;
		entity.rotationYaw = entity.rotationYawHead + f;
	//	entity.prevRotationYaw = entity.prevRotationYawHead + f;
	//	entity.prevRotationYawHead = entity.prevRotationYawHead + f;
		entity.renderYawOffset = entity.prevRotationYawHead + f;
    	//entity.rotation =entity.prevRotationYawHead + f;
	}
}

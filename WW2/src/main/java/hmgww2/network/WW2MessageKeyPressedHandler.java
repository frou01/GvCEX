package hmgww2.network;
import java.util.List;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import hmgww2.entity.*;
import hmgww2.mod_GVCWW2;
import hmgww2.entity.EntityUSSRBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentTranslation;
 
public class WW2MessageKeyPressedHandler implements IMessageHandler<WW2MessageKeyPressed, IMessage> {
 
    @Override
    public IMessage onMessage(WW2MessageKeyPressed message, MessageContext ctx) {
//        EntityPlayer entityPlayer = ctx.getServerHandler().playerEntity;
//        //受け取ったMessageクラスのkey変数の数字をチャットに出力
//
//        if(message.key == 3){
//        	if (entityPlayer.getEquipmentInSlot(4) != null
//					&& (entityPlayer.getEquipmentInSlot(4).getItem() == mod_GVCWW2.armor_jpn)) {
//        	this.OrderEntityJPN(entityPlayer,3,2);
//        	//entityPlayer.addChatComponentMessage(new ChatComponentText("hmgww2.followjpn.name"));
//        	entityPlayer.addChatMessage(new ChatComponentTranslation("hmgww2.followjpn.name", new Object[0]));
//        	}
//        	if (entityPlayer.getEquipmentInSlot(4) != null
//					&& (entityPlayer.getEquipmentInSlot(4).getItem() == mod_GVCWW2.armor_usa)) {
//        	this.OrderEntityUSA(entityPlayer,3,2);
//        	//entityPlayer.addChatComponentMessage(new ChatComponentText("Follow"));
//        	entityPlayer.addChatMessage(new ChatComponentTranslation("hmgww2.followusa.name", new Object[0]));
//        	}
//        	if (entityPlayer.getEquipmentInSlot(4) != null
//					&& (entityPlayer.getEquipmentInSlot(4).getItem() == mod_GVCWW2.armor_ger)) {
//        	this.OrderEntityGER(entityPlayer,3,2);
//        	entityPlayer.addChatMessage(new ChatComponentTranslation("hmgww2.followger.name", new Object[0]));
//        	}
//        	if (entityPlayer.getEquipmentInSlot(4) != null
//					&& (entityPlayer.getEquipmentInSlot(4).getItem() == mod_GVCWW2.armor_rus)) {
//        	this.OrderEntityRUS(entityPlayer,3,2);
//        	entityPlayer.addChatMessage(new ChatComponentTranslation("hmgww2.followrus.name", new Object[0]));
//        	}
//        }
//        if(message.key == 4){
//        	if (entityPlayer.getEquipmentInSlot(4) != null
//					&& (entityPlayer.getEquipmentInSlot(4).getItem() == mod_GVCWW2.armor_jpn)) {
//        	this.OrderEntityJPN(entityPlayer,4,2);
//        	//entityPlayer.addChatComponentMessage(new ChatComponentText("Wait"));
//        	entityPlayer.addChatMessage(new ChatComponentTranslation("hmgww2.waitjpn.name", new Object[0]));
//        	}
//        	if (entityPlayer.getEquipmentInSlot(4) != null
//					&& (entityPlayer.getEquipmentInSlot(4).getItem() == mod_GVCWW2.armor_usa)) {
//        	this.OrderEntityUSA(entityPlayer,4,2);
//        	//entityPlayer.addChatComponentMessage(new ChatComponentText("Wait"));
//        	entityPlayer.addChatMessage(new ChatComponentTranslation("hmgww2.waitusa.name", new Object[0]));
//        	}
//        	if (entityPlayer.getEquipmentInSlot(4) != null
//					&& (entityPlayer.getEquipmentInSlot(4).getItem() == mod_GVCWW2.armor_ger)) {
//        	this.OrderEntityGER(entityPlayer,4,2);
//        	entityPlayer.addChatMessage(new ChatComponentTranslation("hmgww2.waitger.name", new Object[0]));
//        	}
//        	if (entityPlayer.getEquipmentInSlot(4) != null
//					&& (entityPlayer.getEquipmentInSlot(4).getItem() == mod_GVCWW2.armor_rus)) {
//        	this.OrderEntityRUS(entityPlayer,4,2);
//        	entityPlayer.addChatMessage(new ChatComponentTranslation("hmgww2.waitrus.name", new Object[0]));
//        	}
//        }
//        if(message.key == 0){
//        	if (entityPlayer.getEquipmentInSlot(4) != null
//					&& (entityPlayer.getEquipmentInSlot(4).getItem() == mod_GVCWW2.armor_jpn)) {
//        	this.OrderEntityJPN(entityPlayer,0,2);
//        	entityPlayer.addChatMessage(new ChatComponentTranslation("hmgww2.freejpn.name", new Object[0]));
//        	}
//        	if (entityPlayer.getEquipmentInSlot(4) != null
//					&& (entityPlayer.getEquipmentInSlot(4).getItem() == mod_GVCWW2.armor_usa)) {
//        	this.OrderEntityUSA(entityPlayer,0,2);
//        	//entityPlayer.addChatComponentMessage(new ChatComponentText("Free"));
//        	entityPlayer.addChatMessage(new ChatComponentTranslation("hmgww2.freeusa.name", new Object[0]));
//        	}
//        	if (entityPlayer.getEquipmentInSlot(4) != null
//					&& (entityPlayer.getEquipmentInSlot(4).getItem() == mod_GVCWW2.armor_ger)) {
//        	this.OrderEntityGER(entityPlayer,0,2);
//        	entityPlayer.addChatMessage(new ChatComponentTranslation("hmgww2.freeger.name", new Object[0]));
//        	}
//        	if (entityPlayer.getEquipmentInSlot(4) != null
//					&& (entityPlayer.getEquipmentInSlot(4).getItem() == mod_GVCWW2.armor_rus)) {
//        	this.OrderEntityRUS(entityPlayer,0,2);
//        	entityPlayer.addChatMessage(new ChatComponentTranslation("hmgww2.freerus.name", new Object[0]));
//        	}
//        }
//        if(message.key == 1){
//        	if (entityPlayer.getEquipmentInSlot(4) != null
//					&& (entityPlayer.getEquipmentInSlot(4).getItem() == mod_GVCWW2.armor_jpn)) {
//        	this.OrderEntityJPN(entityPlayer,1,2);
//        	entityPlayer.addChatMessage(new ChatComponentTranslation("hmgww2.flagjpn.name", new Object[0]));
//        	}
//        	if (entityPlayer.getEquipmentInSlot(4) != null
//					&& (entityPlayer.getEquipmentInSlot(4).getItem() == mod_GVCWW2.armor_usa)) {
//        	this.OrderEntityUSA(entityPlayer,1,2);
//        	//entityPlayer.addChatComponentMessage(new ChatComponentText("Flag"));
//        	entityPlayer.addChatMessage(new ChatComponentTranslation("hmgww2.flagusa.name", new Object[0]));
//        	}
//        	if (entityPlayer.getEquipmentInSlot(4) != null
//					&& (entityPlayer.getEquipmentInSlot(4).getItem() == mod_GVCWW2.armor_ger)) {
//        	this.OrderEntityGER(entityPlayer,1,2);
//        	entityPlayer.addChatMessage(new ChatComponentTranslation("hmgww2.flagger.name", new Object[0]));
//        	}
//        	if (entityPlayer.getEquipmentInSlot(4) != null
//					&& (entityPlayer.getEquipmentInSlot(4).getItem() == mod_GVCWW2.armor_rus)) {
//        	this.OrderEntityRUS(entityPlayer,1,2);
//        	entityPlayer.addChatMessage(new ChatComponentTranslation("hmgww2.flagrus.name", new Object[0]));
//        	}
//        }
//        if(message.key == 5){
//        	if (entityPlayer.getEquipmentInSlot(4) != null
//					&& (entityPlayer.getEquipmentInSlot(4).getItem() == mod_GVCWW2.armor_jpn)) {
//        	this.OrderEntityJPN(entityPlayer,8,0);
//        	entityPlayer.addChatMessage(new ChatComponentTranslation("hmgww2.vfreejpn.name", new Object[0]));
//        	}
//        	if (entityPlayer.getEquipmentInSlot(4) != null
//					&& (entityPlayer.getEquipmentInSlot(4).getItem() == mod_GVCWW2.armor_usa)) {
//        	this.OrderEntityUSA(entityPlayer,8,0);
//        	//entityPlayer.addChatComponentMessage(new ChatComponentText("VehicleFree"));
//        	entityPlayer.addChatMessage(new ChatComponentTranslation("hmgww2.vfreeusa.name", new Object[0]));
//        	}
//        	if (entityPlayer.getEquipmentInSlot(4) != null
//					&& (entityPlayer.getEquipmentInSlot(4).getItem() == mod_GVCWW2.armor_ger)) {
//        	this.OrderEntityGER(entityPlayer,8,0);
//        	entityPlayer.addChatMessage(new ChatComponentTranslation("hmgww2.vfreeger.name", new Object[0]));
//        	}
//        	if (entityPlayer.getEquipmentInSlot(4) != null
//					&& (entityPlayer.getEquipmentInSlot(4).getItem() == mod_GVCWW2.armor_rus)) {
//        	this.OrderEntityRUS(entityPlayer,8,0);
//        	entityPlayer.addChatMessage(new ChatComponentTranslation("hmgww2.vfreerus.name", new Object[0]));
//        	}
//        }
//        if(message.key == 6){
//        	if (entityPlayer.getEquipmentInSlot(4) != null
//					&& (entityPlayer.getEquipmentInSlot(4).getItem() == mod_GVCWW2.armor_jpn)) {
//        	this.OrderEntityJPN(entityPlayer,8,1);
//        	entityPlayer.addChatMessage(new ChatComponentTranslation("hmgww2.vwaitjpn.name", new Object[0]));
//        	}
//        	if (entityPlayer.getEquipmentInSlot(4) != null
//					&& (entityPlayer.getEquipmentInSlot(4).getItem() == mod_GVCWW2.armor_usa)) {
//        	this.OrderEntityUSA(entityPlayer,8,1);
//        	//entityPlayer.addChatComponentMessage(new ChatComponentText("VehicleWait"));
//        	entityPlayer.addChatMessage(new ChatComponentTranslation("hmgww2.vwaitusa.name", new Object[0]));
//        	}
//        	if (entityPlayer.getEquipmentInSlot(4) != null
//					&& (entityPlayer.getEquipmentInSlot(4).getItem() == mod_GVCWW2.armor_ger)) {
//        	this.OrderEntityGER(entityPlayer,8,1);
//        	entityPlayer.addChatMessage(new ChatComponentTranslation("hmgww2.vwaitger.name", new Object[0]));
//        	}
//        	if (entityPlayer.getEquipmentInSlot(4) != null
//					&& (entityPlayer.getEquipmentInSlot(4).getItem() == mod_GVCWW2.armor_rus)) {
//        	this.OrderEntityRUS(entityPlayer,8,1);
//        	entityPlayer.addChatMessage(new ChatComponentTranslation("hmgww2.vwaitrus.name", new Object[0]));
//        	}
//        }
//        if(message.key == 7){
//        	this.RidingEntity(entityPlayer);
//        	//entityPlayer.addChatComponentMessage(new ChatComponentText("RidingVehicle"));
//        	entityPlayer.addChatMessage(new ChatComponentTranslation("hmgww2.ridejpn.name", new Object[0]));
//        }
//
//        EntityPlayer player = mod_GVCWW2.proxy.getEntityPlayerInstance();
//        if(message.key == 11){
//        	EntityLivingBase en = (EntityLivingBase) entityPlayer.worldObj.getEntityByID(message.fre);
//        	if (entityPlayer.ridingEntity instanceof EntityLivingBase && entityPlayer.ridingEntity != null) {
//        		EntityLivingBase ba = (EntityLivingBase) entityPlayer.ridingEntity;
//        		if(ba ==en && ba instanceof EntityBases){
//        			EntityBases base = (EntityBases) en;
//            		base.server1 = true;
//        		}
//        	}
//        }
//        if(message.key == 12){
//        	EntityLivingBase en = (EntityLivingBase) entityPlayer.worldObj.getEntityByID(message.fre);
//        	if (entityPlayer.ridingEntity instanceof EntityLivingBase && entityPlayer.ridingEntity != null) {
//        		EntityLivingBase ba = (EntityLivingBase) entityPlayer.ridingEntity;
//        		if(ba ==en && ba instanceof EntityBases){
//        			EntityBases base = (EntityBases) en;
//            		base.server2 = true;
//        		}
//        	}
//        }
//        if(message.key == 13){
//        	EntityLivingBase en = (EntityLivingBase) entityPlayer.worldObj.getEntityByID(message.fre);
//        	if (entityPlayer.ridingEntity instanceof EntityLivingBase && entityPlayer.ridingEntity != null) {
//        		EntityLivingBase ba = (EntityLivingBase) entityPlayer.ridingEntity;
//        		if(ba ==en && ba instanceof EntityBases){
//        			EntityBases base = (EntityBases) en;
//            		base.serverspace = true;
//        		}
//        	}
//        }
//        if(message.key == 14){
//        	EntityLivingBase en = (EntityLivingBase) entityPlayer.worldObj.getEntityByID(message.fre);
//        	if (entityPlayer.ridingEntity instanceof EntityLivingBase && entityPlayer.ridingEntity != null) {
//        		EntityLivingBase ba = (EntityLivingBase) entityPlayer.ridingEntity;
//        		if(ba ==en && ba instanceof EntityBases){
//        			EntityBases base = (EntityBases) en;
//            		base.serverx = true;
//        		}
//        	}
//        }
//        return null;
//    }
//
//    public void OrderEntityJPN(EntityPlayer entityPlayer, int i, int j){
//    	Entity entity2 = null;
//		List llist1 = entityPlayer.worldObj.getEntitiesWithinAABBExcludingEntity(entity2,
//				entityPlayer.boundingBox.addCoord(entityPlayer.motionX, entityPlayer.motionY, entityPlayer.motionZ).expand(20D, 30D, 20D));
//		if (llist1 != null) {
//			for (int lj = 0; lj < llist1.size(); lj++) {
//				Entity entity1 = (Entity) llist1.get(lj);
//				if (entity1.canBeCollidedWith()) {
//					if (entity1 instanceof EntityJPNBase  && entity1 != null)
//                    {
//						EntityJPNBase mob = (EntityJPNBase) entity1;
//						{
//							if(i != 8){
//							    mob.setFlagMode(i);
//							}
//							if(j != 2){
//								mob.setMobMode(j);
//							}
//						}
//					}
//				}
//			}
//		}
//		List llist2 = entityPlayer.worldObj.getEntitiesWithinAABBExcludingEntity(entity2,
//				entityPlayer.boundingBox.addCoord(entityPlayer.motionX, entityPlayer.motionY, entityPlayer.motionZ).expand(120D, 80D, 120D));
//		if (llist2 != null) {
//			for (int lj = 0; lj < llist2.size(); lj++) {
//				Entity entity1 = (Entity) llist2.get(lj);
//				if (entity1.canBeCollidedWith()) {
//					if (entity1 instanceof EntityJPN_Fighter  && entity1 != null)
//                    {
//						EntityJPN_Fighter mob = (EntityJPN_Fighter) entity1;
//						{
//							if(i != 8){
//							    mob.setFlagMode(i);
//							}
//							if(j != 2){
//								mob.setMobMode(j);
//							}
//						}
//					}
//					if (entity1 instanceof EntityJPN_FighterA  && entity1 != null)
//                    {
//						EntityJPN_FighterA mob = (EntityJPN_FighterA) entity1;
//						{
//							if(i != 8){
//							    mob.setFlagMode(i);
//							}
//							if(j != 2){
//								mob.setMobMode(j);
//							}
//						}
//					}
//				}
//			}
//		}
//    }
//    public void OrderEntityUSA(EntityPlayer entityPlayer, int i, int j){
//    	Entity entity2 = null;
//		List llist1 = entityPlayer.worldObj.getEntitiesWithinAABBExcludingEntity(entity2,
//				entityPlayer.boundingBox.addCoord(entityPlayer.motionX, entityPlayer.motionY, entityPlayer.motionZ).expand(20D, 30D, 20D));
//		if (llist1 != null) {
//			for (int lj = 0; lj < llist1.size(); lj++) {
//				Entity entity1 = (Entity) llist1.get(lj);
//				if (entity1.canBeCollidedWith()) {
//					if (entity1 instanceof EntityUSABase  && entity1 != null)
//                    {
//						EntityUSABase mob = (EntityUSABase) entity1;
//						{
//							if(i != 8){
//							    mob.setFlagMode(i);
//							}
//							if(j != 2){
//								mob.setMobMode(j);
//							}
//						}
//					}
//				}
//			}
//		}
//		List llist2 = entityPlayer.worldObj.getEntitiesWithinAABBExcludingEntity(entity2,
//				entityPlayer.boundingBox.addCoord(entityPlayer.motionX, entityPlayer.motionY, entityPlayer.motionZ).expand(120D, 80D, 120D));
//		if (llist2 != null) {
//			for (int lj = 0; lj < llist2.size(); lj++) {
//				Entity entity1 = (Entity) llist2.get(lj);
//				if (entity1.canBeCollidedWith()) {
//					if (entity1 instanceof EntityUSA_Fighter  && entity1 != null)
//                    {
//						EntityUSA_Fighter mob = (EntityUSA_Fighter) entity1;
//						{
//							if(i != 8){
//							    mob.setFlagMode(i);
//							}
//							if(j != 2){
//								mob.setMobMode(j);
//							}
//						}
//					}
//					if (entity1 instanceof EntityUSA_FighterA  && entity1 != null)
//                    {
//						EntityUSA_FighterA mob = (EntityUSA_FighterA) entity1;
//						{
//							if(i != 8){
//							    mob.setFlagMode(i);
//							}
//							if(j != 2){
//								mob.setMobMode(j);
//							}
//						}
//					}
//				}
//			}
//		}
//    }
//    public void OrderEntityGER(EntityPlayer entityPlayer, int i, int j){
//    	Entity entity2 = null;
//		List llist1 = entityPlayer.worldObj.getEntitiesWithinAABBExcludingEntity(entity2,
//				entityPlayer.boundingBox.addCoord(entityPlayer.motionX, entityPlayer.motionY, entityPlayer.motionZ).expand(20D, 30D, 20D));
//		if (llist1 != null) {
//			for (int lj = 0; lj < llist1.size(); lj++) {
//				Entity entity1 = (Entity) llist1.get(lj);
//				if (entity1.canBeCollidedWith()) {
//					if (entity1 instanceof EntityGERBase  && entity1 != null)
//                    {
//						EntityGERBase mob = (EntityGERBase) entity1;
//						{
//							if(i != 8){
//							    mob.setFlagMode(i);
//							}
//							if(j != 2){
//								mob.setMobMode(j);
//							}
//						}
//					}
//				}
//			}
//		}
//		List llist2 = entityPlayer.worldObj.getEntitiesWithinAABBExcludingEntity(entity2,
//				entityPlayer.boundingBox.addCoord(entityPlayer.motionX, entityPlayer.motionY, entityPlayer.motionZ).expand(120D, 80D, 120D));
//		if (llist2 != null) {
//			for (int lj = 0; lj < llist2.size(); lj++) {
//				Entity entity1 = (Entity) llist2.get(lj);
//				if (entity1.canBeCollidedWith()) {
//					if (entity1 instanceof EntityGER_Fighter  && entity1 != null)
//                    {
//						EntityGER_Fighter mob = (EntityGER_Fighter) entity1;
//						{
//							if(i != 8){
//							    mob.setFlagMode(i);
//							}
//							if(j != 2){
//								mob.setMobMode(j);
//							}
//						}
//					}
//					if (entity1 instanceof EntityGER_FighterA  && entity1 != null)
//                    {
//						EntityGER_FighterA mob = (EntityGER_FighterA) entity1;
//						{
//							if(i != 8){
//							    mob.setFlagMode(i);
//							}
//							if(j != 2){
//								mob.setMobMode(j);
//							}
//						}
//					}
//				}
//			}
//		}
//    }
//    public void OrderEntityRUS(EntityPlayer entityPlayer, int i, int j){
//    	Entity entity2 = null;
//		List llist1 = entityPlayer.worldObj.getEntitiesWithinAABBExcludingEntity(entity2,
//				entityPlayer.boundingBox.addCoord(entityPlayer.motionX, entityPlayer.motionY, entityPlayer.motionZ).expand(20D, 30D, 20D));
//		if (llist1 != null) {
//			for (int lj = 0; lj < llist1.size(); lj++) {
//				Entity entity1 = (Entity) llist1.get(lj);
//				if (entity1.canBeCollidedWith()) {
//					if (entity1 instanceof EntityUSSRBase && entity1 != null)
//                    {
//						EntityUSSRBase mob = (EntityUSSRBase) entity1;
//						{
//							if(i != 8){
//							    mob.setFlagMode(i);
//							}
//							if(j != 2){
//								mob.setMobMode(j);
//							}
//						}
//					}
//				}
//			}
//		}
//		List llist2 = entityPlayer.worldObj.getEntitiesWithinAABBExcludingEntity(entity2,
//				entityPlayer.boundingBox.addCoord(entityPlayer.motionX, entityPlayer.motionY, entityPlayer.motionZ).expand(120D, 80D, 120D));
//		if (llist2 != null) {
//			for (int lj = 0; lj < llist2.size(); lj++) {
//				Entity entity1 = (Entity) llist2.get(lj);
//				if (entity1.canBeCollidedWith()) {
//					if (entity1 instanceof EntityUSSR_Fighter && entity1 != null)
//                    {
//						EntityUSSR_Fighter mob = (EntityUSSR_Fighter) entity1;
//						{
//							if(i != 8){
//							    mob.setFlagMode(i);
//							}
//							if(j != 2){
//								mob.setMobMode(j);
//							}
//						}
//					}
//					if (entity1 instanceof EntityUSSR_FighterA && entity1 != null)
//                    {
//						EntityUSSR_FighterA mob = (EntityUSSR_FighterA) entity1;
//						{
//							if(i != 8){
//							    mob.setFlagMode(i);
//							}
//							if(j != 2){
//								mob.setMobMode(j);
//							}
//						}
//					}
//				}
//			}
//		}
	    return null;
    }
    
    
//
//
//
//
//
//    public void OrderEntity(EntityPlayer entityPlayer, int i){
//    	Entity entity2 = null;
//		List llist1 = entityPlayer.worldObj.getEntitiesWithinAABBExcludingEntity(entity2,
//				entityPlayer.boundingBox.addCoord(entityPlayer.motionX, entityPlayer.motionY, entityPlayer.motionZ).expand(40D, 40D, 40D));
//		if (llist1 != null) {
//			for (int lj = 0; lj < llist1.size(); lj++) {
//				Entity entity1 = (Entity) llist1.get(lj);
//				if (entity1.canBeCollidedWith()) {
//					if (entity1 instanceof EntityBases  && entity1 != null)
//                    {
//						EntityBases mob = (EntityBases) entity1;
//						{
//							mob.setFlagMode(i);
//						}
//					}
//				}
//			}
//		}
//    }
//    public void OrderEntity2(EntityPlayer entityPlayer, int i){
//    	Entity entity2 = null;
//		List llist1 = entityPlayer.worldObj.getEntitiesWithinAABBExcludingEntity(entity2,
//				entityPlayer.boundingBox.addCoord(entityPlayer.motionX, entityPlayer.motionY, entityPlayer.motionZ).expand(40D, 40D, 40D));
//		if (llist1 != null) {
//			for (int lj = 0; lj < llist1.size(); lj++) {
//				Entity entity1 = (Entity) llist1.get(lj);
//				if (entity1.canBeCollidedWith()) {
//					if (entity1 instanceof EntityBases  && entity1 != null)
//                    {
//						EntityBases mob = (EntityBases) entity1;
//						{
//							mob.setMobMode(i);
//						}
//					}
//				}
//			}
//		}
//    }
//    public void RidingEntity(EntityPlayer entityPlayer){
//    	Entity entity2 = null;
//		List llist1 = entityPlayer.worldObj.getEntitiesWithinAABBExcludingEntity(entity2,
//				entityPlayer.boundingBox.addCoord(entityPlayer.motionX, entityPlayer.motionY, entityPlayer.motionZ).expand(5D, 5D, 5D));
//		if (llist1 != null) {
//			for (int lj = 0; lj < llist1.size(); lj++) {
//				Entity entity1 = (Entity) llist1.get(lj);
//				if (entity1.canBeCollidedWith()) {
//					if (entity1 instanceof EntityBases  && entity1 != null)
//                    {
//						EntityBases mob = (EntityBases) entity1;
//						if(!entityPlayer.worldObj.isRemote && mob.vehicle)
//						{
//							entityPlayer.mountEntity(mob);
//						}
//					}
//				}
//			}
//		}
//    }
}
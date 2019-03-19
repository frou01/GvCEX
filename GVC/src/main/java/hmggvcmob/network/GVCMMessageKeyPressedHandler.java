package hmggvcmob.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import hmggvcmob.GVCMobPlus;
import hmggvcmob.entity.IControlable;
import hmggvcmob.entity.ITank;
import hmggvcmob.entity.TankBaseLogic;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class GVCMMessageKeyPressedHandler implements IMessageHandler<GVCMMessageKeyPressed, IMessage> {
 
    @Override
    public IMessage onMessage(GVCMMessageKeyPressed message, MessageContext ctx) {
        EntityPlayer entityPlayer = ctx.getServerHandler().playerEntity;
        EntityPlayer player = GVCMobPlus.proxy.getEntityPlayerInstance();
        if(entityPlayer.ridingEntity != null){
        	if(entityPlayer.ridingEntity instanceof ITank) {
				TankBaseLogic baseLogic = ((ITank) entityPlayer.ridingEntity).getBaseLogic();
				baseLogic.setControl_RightClick(false);
				baseLogic.setControl_LeftClick(false);
				baseLogic.setControl_Space(false);
				baseLogic.setControl_x(false);
				baseLogic.setControl_w(false);
				baseLogic.setControl_a(false);
				baseLogic.setControl_s(false);
				baseLogic.setControl_d(false);
				baseLogic.setControl_f(false);
			}else
        	if(entityPlayer.ridingEntity instanceof IControlable) {
				((IControlable) entityPlayer.ridingEntity).setControl_RightClick(false);
				((IControlable) entityPlayer.ridingEntity).setControl_LeftClick(false);
				((IControlable) entityPlayer.ridingEntity).setControl_Space(false);
				((IControlable) entityPlayer.ridingEntity).setControl_x(false);
				((IControlable) entityPlayer.ridingEntity).setControl_w(false);
				((IControlable) entityPlayer.ridingEntity).setControl_a(false);
				((IControlable) entityPlayer.ridingEntity).setControl_s(false);
				((IControlable) entityPlayer.ridingEntity).setControl_d(false);
				((IControlable) entityPlayer.ridingEntity).setControl_f(false);
			}
		}
		for(int i : message.keys){

			EntityLivingBase en = (EntityLivingBase) entityPlayer.worldObj.getEntityByID(message.targetID);

			if(entityPlayer.ridingEntity instanceof ITank) {
				TankBaseLogic baseLogic = ((ITank) entityPlayer.ridingEntity).getBaseLogic();
				switch (i) {
					case 11:
						baseLogic.setControl_LeftClick(true);
						break;
					case 12:
						baseLogic.setControl_RightClick(true);
						break;
					case 13:
						baseLogic.setControl_Space(true);
						break;
					case 14:
						baseLogic.setControl_x(true);
						break;
					case 16:
						baseLogic.setControl_w(true);
						break;
					case 17:
						baseLogic.setControl_a(true);
						break;
					case 18:
						baseLogic.setControl_s(true);
						break;
					case 19:
						baseLogic.setControl_d(true);
						break;
					case 20:
						baseLogic.setControl_f(true);
						break;
				}
			}else
			if (en == entityPlayer.ridingEntity && en instanceof IControlable) {
				switch (i) {
					case 11:
						((IControlable)entityPlayer.ridingEntity).setControl_LeftClick(true);
						break;
					case 12:
						((IControlable)entityPlayer.ridingEntity).setControl_RightClick(true);
					case 13:
						((IControlable)entityPlayer.ridingEntity).setControl_Space(true);
					case 14:
						((IControlable)entityPlayer.ridingEntity).setControl_x(true);
					case 16:
						((IControlable)entityPlayer.ridingEntity).setControl_w(true);
					case 17:
						((IControlable)entityPlayer.ridingEntity).setControl_a(true);
					case 18:
						((IControlable)entityPlayer.ridingEntity).setControl_s(true);
					case 19:
						((IControlable)entityPlayer.ridingEntity).setControl_d(true);
					case 20:
						((IControlable)entityPlayer.ridingEntity).setControl_f(true);
				}
			}
//			if(i== 11){
//				EntityLivingBase en = (EntityLivingBase) entityPlayer.worldObj.getEntityByID(message.targetID);
//				if (en == entityPlayer.ridingEntity && en instanceof IControlable){
//					((IControlable)entityPlayer.ridingEntity).setControl_LeftClick(true);
//				}
//			}
//			if(i== 12){
//				EntityLivingBase en = (EntityLivingBase) entityPlayer.worldObj.getEntityByID(message.targetID);
//				if (en == entityPlayer.ridingEntity && en instanceof IControlable){
//					((IControlable)entityPlayer.ridingEntity).setControl_RightClick(true);
//				}
//			}
//			if(i== 13){
//				EntityLivingBase en = (EntityLivingBase) entityPlayer.worldObj.getEntityByID(message.targetID);
//				if (en == entityPlayer.ridingEntity && en instanceof IControlable){
//					((IControlable)entityPlayer.ridingEntity).setControl_Space(true);
//				}
//			}
//			if(i== 14){
//				EntityLivingBase en = (EntityLivingBase) entityPlayer.worldObj.getEntityByID(message.targetID);
//				if (en == entityPlayer.ridingEntity && en instanceof IControlable){
//					((IControlable)entityPlayer.ridingEntity).setControl_x(true);
//				}
//			}
//			if(i== 16){
//				EntityLivingBase en = (EntityLivingBase) entityPlayer.worldObj.getEntityByID(message.targetID);
//				if (en == entityPlayer.ridingEntity && en instanceof IControlable){
//					((IControlable)entityPlayer.ridingEntity).setControl_w(true);
//				}
//			}
//			if(i== 17){
//				EntityLivingBase en = (EntityLivingBase) entityPlayer.worldObj.getEntityByID(message.targetID);
//				if (en == entityPlayer.ridingEntity && en instanceof IControlable){
//					((IControlable)entityPlayer.ridingEntity).setControl_a(true);
//				}
//			}
//			if(i== 18){
//				EntityLivingBase en = (EntityLivingBase) entityPlayer.worldObj.getEntityByID(message.targetID);
//				if (en == entityPlayer.ridingEntity && en instanceof IControlable){
//					((IControlable)entityPlayer.ridingEntity).setControl_s(true);
//				}
//			}
//			if(i== 19){
//				EntityLivingBase en = (EntityLivingBase) entityPlayer.worldObj.getEntityByID(message.targetID);
//				if (en == entityPlayer.ridingEntity && en instanceof IControlable){
//					((IControlable)entityPlayer.ridingEntity).setControl_d(true);
//				}
//			}
//			if(i== 20){
//				EntityLivingBase en = (EntityLivingBase) entityPlayer.worldObj.getEntityByID(message.targetID);
//				if (en == entityPlayer.ridingEntity && en instanceof IControlable){
//					((IControlable)entityPlayer.ridingEntity).setControl_f(true);
//				}
//			}
		}
        
        return null;
    }
}
package hmvehicle.handles;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import hmvehicle.entity.parts.HasBaseLogic;
import hmvehicle.entity.parts.IControlable;
import hmvehicle.entity.parts.logics.IbaseLogic;
import hmvehicle.entity.parts.logics.TankBaseLogic;
import hmvehicle.packets.HMVMMessageKeyPressed;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

import static handmadeguns.HandmadeGunsCore.proxy;

public class HMVMMessageKeyPressedHandler implements IMessageHandler<HMVMMessageKeyPressed, IMessage> {
 
    @Override
    public IMessage onMessage(HMVMMessageKeyPressed message, MessageContext ctx) {
	    World worldObj;
	    if(ctx.side.isServer()) {
		    worldObj = ctx.getServerHandler().playerEntity.worldObj;
	    }else{
		    worldObj = proxy.getCilentWorld();
	    }
	    Entity en = worldObj.getEntityByID(message.targetID);
        if(en != null){
        	if(en instanceof HasBaseLogic) {
		        IbaseLogic baseLogic = ((HasBaseLogic) en).getBaseLogic();
				baseLogic.setControl_RightClick(false);
				baseLogic.setControl_LeftClick(false);
				baseLogic.setControl_Space(false);
				baseLogic.setControl_x(false);
				baseLogic.setControl_w(false);
				baseLogic.setControl_a(false);
				baseLogic.setControl_s(false);
				baseLogic.setControl_d(false);
				baseLogic.setControl_f(false);
			}/*else
        	if(en instanceof IControlable) {
				((IControlable) en).setControl_RightClick(false);
				((IControlable) en).setControl_LeftClick(false);
				((IControlable) en).setControl_Space(false);
				((IControlable) en).setControl_x(false);
				((IControlable) en).setControl_w(false);
				((IControlable) en).setControl_a(false);
				((IControlable) en).setControl_s(false);
				((IControlable) en).setControl_d(false);
				((IControlable) en).setControl_f(false);
			}*/
		}
		for(int i : message.keys){

			if(en instanceof HasBaseLogic) {
				IbaseLogic baseLogic = ((HasBaseLogic) en).getBaseLogic();
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
			}/*else
			if (en instanceof IControlable) {
				switch (i) {
					case 11:
						((IControlable)en).setControl_LeftClick(true);
						break;
					case 12:
						((IControlable)en).setControl_RightClick(true);
					case 13:
						((IControlable)en).setControl_Space(true);
					case 14:
						((IControlable)en).setControl_x(true);
					case 16:
						((IControlable)en).setControl_w(true);
					case 17:
						((IControlable)en).setControl_a(true);
					case 18:
						((IControlable)en).setControl_s(true);
					case 19:
						((IControlable)en).setControl_d(true);
					case 20:
						((IControlable)en).setControl_f(true);
				}
			}*/
		}
        
        return null;
    }
}
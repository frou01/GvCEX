package hmggvcmob.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import hmggvcmob.entity.guerrilla.GVCEntityGK;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

import static handmadeguns.HandmadeGunsCore.proxy;

public class GVCXMMessageGKdata implements IMessageHandler<GVCXMPacketSyncGKdata, IMessage> {
    @Override//IMessageHandlerのメソッド
    public IMessage onMessage(GVCXMPacketSyncGKdata message, MessageContext ctx) {
        //クライアントへ送った際に、EntityPlayerインスタンスはこのように取れる。
        //EntityPlayer player = SamplePacketMod.proxy.getEntityPlayerInstance();
        //サーバーへ送った際に、EntityPlayerインスタンス（EntityPlayerMPインスタンス）はこのように取れる。
        //EntityPlayer entityPlayer = ctx.getServerHandler().playerEntity;
        //Do something.
        World world;
//        System.out.println("debug");
        if(ctx.side.isServer()) {
            world = ctx.getServerHandler().playerEntity.worldObj;
        }else{
            world = proxy.getCilentWorld();
        }
        try {
            if(world != null){
                Entity tgtEntity = world.getEntityByID(message.entityid);
                if(message.data != null&&tgtEntity != null && tgtEntity instanceof GVCEntityGK) {
                    ((GVCEntityGK)tgtEntity).combattask_2 = message.data.combattask_2;
                    ((GVCEntityGK)tgtEntity).combattask_4 = message.data.combattask_4;
                    ((GVCEntityGK)tgtEntity).kickprogeress = message.data.cooltime_3;







                }
            }
//        bullet = message.bullet.setdata(bullet);
//        System.out.println("bullet "+ bullet);
        }catch (ClassCastException e) {
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
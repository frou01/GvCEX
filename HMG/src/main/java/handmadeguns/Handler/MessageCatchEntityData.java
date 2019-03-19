package handmadeguns.Handler;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import handmadeguns.entity.bullets.HMGEntityBulletBase;
import handmadeguns.network.PacketFixClientbullet;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

import static handmadeguns.HandmadeGunsCore.proxy;


public class MessageCatchEntityData implements IMessageHandler<PacketFixClientbullet, IMessage> {
    @Override//IMessageHandlerのメソッド
    public IMessage onMessage(PacketFixClientbullet message, MessageContext ctx) {
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
                if(message.data != null&&tgtEntity != null && tgtEntity instanceof HMGEntityBulletBase) {
                    tgtEntity.motionX = message.data.motionX;
                    tgtEntity.motionY = message.data.motionY;
                    tgtEntity.motionZ = message.data.motionZ;
                    tgtEntity.posX = message.data.posX;
                    tgtEntity.posY = message.data.posY;
                    tgtEntity.posZ = message.data.posZ;
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

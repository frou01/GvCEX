package handmadeguns.Handler;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import handmadeguns.entity.bullets.HMGEntityBulletCartridge;
import handmadeguns.HandmadeGunsCore;
import handmadeguns.network.PacketDropCartridge;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;

import static handmadeguns.HandmadeGunsCore.HMG_proxy;


public class MessageCatcher_dropCartridge implements IMessageHandler<PacketDropCartridge, IMessage> {
    @Override//IMessageHandlerのメソッド
    public IMessage onMessage(PacketDropCartridge message, MessageContext ctx) {
        if(HandmadeGunsCore.cfg_canEjectCartridge) {
            //クライアントへ送った際に、EntityPlayerインスタンスはこのように取れる。
            //EntityPlayer player = SamplePacketMod.proxy.getEntityPlayerInstance();
            //サーバーへ送った際に、EntityPlayerインスタンス（EntityPlayerMPインスタンス）はこのように取れる。
            //EntityPlayer entityPlayer = ctx.getServerHandler().playerEntity;
            //Do something.
            World world;
//        System.out.println("debug");
            if (ctx.side.isServer()) {
                world = ctx.getServerHandler().playerEntity.worldObj;
            } else {
                world = HMG_proxy.getCilentWorld();
            }
            try {
                if (world != null) {
                    Entity shooter = world.getEntityByID(message.shooterid);
                    if (shooter != null && shooter instanceof EntityLivingBase) {
                        HMGEntityBulletCartridge var8;
                        if (message.type != -1) {
                            var8 = new HMGEntityBulletCartridge(world, (EntityLivingBase) shooter, message.type);
                        } else {
//                        System.out.println("modelname" + message.modelname);
                            var8 = new HMGEntityBulletCartridge(world, (EntityLivingBase) shooter, -1, message.modelname);
                        }
                        world.spawnEntityInWorld(var8);
                    }
                }
//        bullet = message.bullet.setdata(bullet);
//        System.out.println("bullet "+ bullet);
            } catch (ClassCastException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}

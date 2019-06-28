package handmadeguns.Handler;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import handmadeguns.network.PacketPlaysound;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

import static handmadeguns.HandmadeGunsCore.proxy;


public class MessageCatcher_Playsound implements IMessageHandler<PacketPlaysound, IMessage> {
    @Override//IMessageHandlerのメソッド
    public IMessage onMessage(PacketPlaysound message, MessageContext ctx) {
        World world;
//        System.out.println("debug");
        if(ctx.side.isServer()) {
            world = ctx.getServerHandler().playerEntity.worldObj;
        }else{
            world = proxy.getCilentWorld();
        }
        try {
            if(world != null){
                Entity shooter = world.getEntityByID(message.shooterid);
                if(shooter != null) {
                    if(!world.isRemote)
                        shooter.worldObj.playSoundEffect(shooter.posX,shooter.posY,shooter.posZ, message.sound, message.level, message.speed);
                    else {
                        if(message.isreload)
                            proxy.playsoundatEntity_reload(message.sound, message.level, message.speed, shooter, false);
                        else if(message.time != -1)
                            proxy.playsoundatEntity(message.sound, message.level, message.speed, shooter, false, message.time);
                        else
                            proxy.playsoundat(message.sound, message.level, message.speed, message.speed, shooter.posX, shooter.posY , shooter.posZ);
                    }
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

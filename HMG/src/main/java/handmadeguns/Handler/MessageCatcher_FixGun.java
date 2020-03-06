package handmadeguns.Handler;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import handmadeguns.entity.PlacedGunEntity;
import handmadeguns.items.guns.HMGItem_Unified_Guns;
import handmadeguns.network.PacketFixGun;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import static handmadeguns.HandmadeGunsCore.HMG_proxy;

public class MessageCatcher_FixGun implements IMessageHandler<PacketFixGun, IMessage> {
    @Override
    public IMessage onMessage(PacketFixGun message, MessageContext ctx) {
        World world;
//        System.out.println("debug");
        if(ctx.side.isServer()) {
            world = ctx.getServerHandler().playerEntity.worldObj;
        }else{
            world = HMG_proxy.getCilentWorld();
        }
        try {
            if(world != null){
                Entity shooter = world.getEntityByID(message.shooterid);
                if(shooter != null && shooter instanceof EntityLivingBase && ((EntityLivingBase) shooter).getHeldItem().getItem() instanceof HMGItem_Unified_Guns) {

                    if(((HMGItem_Unified_Guns) ((EntityLivingBase) shooter).getHeldItem().getItem()).gunInfo.fixAsEntity){
                        Vec3 vec3 = Vec3.createVectorHelper(shooter.posX, shooter.posY + shooter.getEyeHeight(), shooter.posZ);
                        Vec3 playerlook = ((EntityLivingBase) shooter).getLook(1.0f);
                        playerlook = Vec3.createVectorHelper(playerlook.xCoord * 256, playerlook.yCoord * 256, playerlook.zCoord * 256);
                        Vec3 vec31 = Vec3.createVectorHelper(shooter.posX + playerlook.xCoord, shooter.posY + shooter.getEyeHeight() + playerlook.yCoord, shooter.posZ + playerlook.zCoord);
                        MovingObjectPosition movingobjectposition = shooter.worldObj.func_147447_a(vec3, vec31, false, true, false);
                        if(movingobjectposition != null && movingobjectposition.hitVec.distanceTo(Vec3.createVectorHelper(shooter.posX,shooter.posY,shooter.posZ))<2) {
                            PlacedGunEntity gunEntity = new PlacedGunEntity(world, ((EntityLivingBase) shooter).getHeldItem());
                            gunEntity.setLocationAndAngles(movingobjectposition.hitVec.xCoord, movingobjectposition.hitVec.yCoord, movingobjectposition.hitVec.zCoord, ((EntityLivingBase) shooter).rotationYawHead, 0);
                            gunEntity.rotationYawGun = ((EntityLivingBase) shooter).rotationYawHead;
                            world.spawnEntityInWorld(gunEntity);
                            if(shooter instanceof EntityPlayer){
                                ((EntityPlayer) shooter).inventory.setInventorySlotContents(((EntityPlayer) shooter).inventory.currentItem,null);
                            }
                        }
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

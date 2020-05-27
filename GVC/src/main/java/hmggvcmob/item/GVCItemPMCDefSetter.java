package hmggvcmob.item;

import handmadeguns.Util.GunsUtils;
import handmadevehicle.entity.EntityDummy_rider;
import handmadevehicle.entity.parts.Modes;
import hmggvcmob.entity.friend.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import javax.vecmath.Vector3d;
import java.util.ArrayList;
import java.util.List;

import static handmadevehicle.Utils.*;
import static hmggvcmob.GVCMobPlus.tabgvcm;
import static hmggvcutil.GVCUtils.platoonMatched;

public class GVCItemPMCDefSetter extends Item {
    int mode = 0;
    public GVCItemPMCDefSetter(){
        this.setCreativeTab(tabgvcm);
        setMaxStackSize(64);
    }
    /**
     * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
     */
    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer entityPlayer)
    {
        if(!world.isRemote) {
            if (entityPlayer.isSneaking()) {
                if (mode == 0) {
                    mode = 1;
                    entityPlayer.addChatComponentMessage(new ChatComponentTranslation(
                            "Tracking command mode"));
                } else if(mode == 1){
                    mode = 2;
                    entityPlayer.addChatComponentMessage(new ChatComponentTranslation(
                            "LZ set mode"));
                }else if(mode == 2){
                    mode = 0;
                    entityPlayer.addChatComponentMessage(new ChatComponentTranslation(
                            "Defence position setter mode"));
                }
            }else {
                EntityPMCBase target = searchEntity(itemStack, entityPlayer, world);
                if (target != null) {
                    if (target.getPlatoon() == null)target.makePlatoon();
                    else {
                        target.enlistPlatoon();
                        target = (EntityPMCBase) target.getPlatoon().leader.entity;
                    }
                    switch (mode) {
                        case 1:
                            target.getPlatoon().platoonTargetEntity = entityPlayer;
                            target.leaderEntity_name = entityPlayer.getCommandSenderName();
                            target.changePlatoonOrder(Modes.Follow);
                            entityPlayer.addChatComponentMessage(new ChatComponentTranslation(
                                    "Follow"));
                            break;
                        case 0:
                            Vector3d frontVec = getjavaxVecObj(getLook(1,entityPlayer.getRotationYawHead(),entityPlayer.rotationPitch));
                            frontVec.scale(-1);
                            Vec3 vec3 = Vec3.createVectorHelper(entityPlayer.posX, entityPlayer.posY + entityPlayer.getEyeHeight(), entityPlayer.posZ);
                            Vec3 playerlook = getMinecraftVecObj(frontVec);
                            playerlook.xCoord *= -1;
                            playerlook.yCoord *= -1;
                            playerlook.zCoord *= -1;

                            playerlook = Vec3.createVectorHelper(playerlook.xCoord * 256, playerlook.yCoord * 256, playerlook.zCoord * 256);

                            Vec3 vec31 = Vec3.createVectorHelper(entityPlayer.posX + playerlook.xCoord, entityPlayer.posY + entityPlayer.getEyeHeight() + playerlook.yCoord, entityPlayer.posZ + playerlook.zCoord);
                            MovingObjectPosition movingobjectposition = GunsUtils.getmovingobjectPosition_forBlock(world,vec3, vec31, false, true, false);//è’ìÀÇ∑ÇÈÉuÉçÉbÉNÇí≤Ç◊ÇÈ
                            if(movingobjectposition != null && movingobjectposition.hitVec != null) {
                                target.getPlatoon().setPlatoonTargetPos(new double[]{movingobjectposition.blockX,
                                        movingobjectposition.blockY,
                                        movingobjectposition.blockZ});
                            }
                            target.changePlatoonOrder(Modes.Go);
                            entityPlayer.addChatComponentMessage(new ChatComponentTranslation(
                                    "MoveToTargetPos : " + target.getPlatoon().PlatoonTargetPos));
                            break;
                        case 2:
                            target.changePlatoonOrder(Modes.Land);
                            entityPlayer.addChatComponentMessage(new ChatComponentTranslation(
                                    "Landing"));
                            break;
                    }
                }
            }
        }
        return itemStack;
    }

    public EntityPMCBase searchEntity(ItemStack itemStack , EntityPlayer entityPlayer, World world)
    {
        if(!world.isRemote) {
            List list = world.getEntitiesWithinAABBExcludingEntity(entityPlayer, entityPlayer.boundingBox.expand(10, 10, 10));
            if (list != null && !list.isEmpty()) {
                for (Object o : list) {
                    Entity PMC = (Entity) o;
                    if (PMC instanceof EntityPMCBase && platoonMatched(itemStack.hasDisplayName() ? itemStack.getDisplayName() :null, (EntityPMCBase) PMC) && canMoveEntity(PMC)) {
                        return (EntityPMCBase) PMC;
                    }
                }
            }
            list = world.loadedEntityList;
            if (list != null && !list.isEmpty()) {
                for (Object o : list) {
                    Entity PMC = (Entity) o;
                    if (PMC instanceof EntityPMCBase && platoonMatched(itemStack.hasDisplayName() ? itemStack.getDisplayName() :null, (EntityPMCBase) PMC) && canMoveEntity(PMC)) {
                        return (EntityPMCBase) PMC;
                    }
                }
            }
        }
        return null;
    }

}

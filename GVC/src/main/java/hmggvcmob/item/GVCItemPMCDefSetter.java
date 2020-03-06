package hmggvcmob.item;

import handmadeguns.Util.GunsUtils;
import handmadevehicle.entity.EntityDummy_rider;
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
import java.util.List;

import static handmadevehicle.Utils.*;
import static hmggvcmob.GVCMobPlus.tabgvcm;

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
                } else if(mode == 2){
                    mode = 0;
                    entityPlayer.addChatComponentMessage(new ChatComponentTranslation(
                            "Defence position setter mode"));
                } else if(mode == 1){
                    mode = 2;
                    entityPlayer.addChatComponentMessage(new ChatComponentTranslation(
                            "LZ set mode"));
                }
            }else {
                if(mode == 1){
                    List list = world.getEntitiesWithinAABBExcludingEntity(entityPlayer, entityPlayer.boundingBox.expand(10, 10, 10));
                    if (list != null && !list.isEmpty()) {
                        for (Object o : list) {
                            Entity PMC = (Entity) o;
                            if (PMC instanceof EntityPMCBase && platoonMatched(itemStack, (EntityPMCBase) PMC)) {
                                entityPlayer.addChatComponentMessage(new ChatComponentTranslation(
                                        "[infantry]I'll Follow Leader"));
                                ((EntityPMCBase) PMC).joinPlatoon(null);
                                ((EntityPMCBase) PMC).setTargetCampPosition(null);
                                ((EntityPMCBase) PMC).setLeaderPlayer(entityPlayer);
                                ((EntityPMCBase) PMC).makePlatoon();
                                ((EntityPMCBase) PMC).mode = 1;
                            }
                        }
                    }
                    list = world.loadedEntityList;
                    if (list != null && !list.isEmpty()) {
                        for (Object o : list) {
                            Entity PMC = (Entity) o;
                            if (PMC instanceof EntityPMCBase && platoonMatched(itemStack, (EntityPMCBase) PMC) && PMC.ridingEntity instanceof EntityDummy_rider) {
                                if(((EntityDummy_rider) PMC.ridingEntity).linkedBaseLogic.ispilot(PMC)) {
                                    entityPlayer.addChatComponentMessage(new ChatComponentTranslation(
                                            "[vehicle]I'll Follow Leader"));
                                    ((EntityPMCBase) PMC).joinPlatoon(null);
                                    ((EntityPMCBase) PMC).setTargetCampPosition(null);
                                    ((EntityPMCBase) PMC).setLeaderPlayer(entityPlayer);
                                    ((EntityPMCBase) PMC).makePlatoon();
                                    ((EntityPMCBase) PMC).mode = 1;
                                }
                            }
                        }
                    }
                }else if(mode == 2){
                    List list = world.loadedEntityList;
                    if (list != null && !list.isEmpty()) {
                        for (Object o : list) {
                            Entity PMC = (Entity) o;
                            if (PMC instanceof EntityPMCBase && platoonMatched(itemStack, (EntityPMCBase) PMC) && PMC.ridingEntity instanceof EntityDummy_rider) {

                                if(((EntityDummy_rider) PMC.ridingEntity).linkedBaseLogic.ispilot(PMC)) {
                                    entityPlayer.addChatComponentMessage(new ChatComponentTranslation(
                                            "[vehicle]Roger I'll Landing"));
                                    ((EntityPMCBase) PMC).setLeaderPlayer(entityPlayer);
                                    ((EntityPMCBase) PMC).joinPlatoon(null);
                                    ((EntityPMCBase) PMC).setTargetCampPosition(new int[]{(int) entityPlayer.posX, (int) entityPlayer.posY, (int) entityPlayer.posZ});
                                    ((EntityPMCBase) PMC).setCommandState(2);
                                }
                            }
                        }
                    }
                }else if(mode == 0){
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
                    if(movingobjectposition != null && movingobjectposition.hitVec != null){
                        setTargetPos(itemStack,entityPlayer,world,
                                movingobjectposition.blockX,
                                movingobjectposition.blockY,
                                movingobjectposition.blockZ);
                    }
                }
            }
        }
        return itemStack;
    }
    public void setTargetPos(ItemStack itemStack , EntityPlayer entityPlayer, World world, int x, int y, int z)
    {
        if(!world.isRemote) {
            List
            list = world.loadedEntityList;
            if (list != null && !list.isEmpty()) {
                for (Object o : list) {
                    Entity PMC = (Entity) o;
                    if (PMC instanceof EntityPMCBase && platoonMatched(itemStack, (EntityPMCBase) PMC) && PMC.ridingEntity instanceof EntityDummy_rider) {
                        if(((EntityDummy_rider) PMC.ridingEntity).linkedBaseLogic.ispilot(PMC)&&mode == 0) {
                            entityPlayer.addChatComponentMessage(new ChatComponentTranslation(
                                    "[Vehicle] Defense  " + x + "," + z));
                            ((EntityPMCBase) PMC).mode = 0;
                            ((EntityPMCBase) PMC).setTargetCampPosition(new int[]{x,  y,  z});
                            ((EntityPMCBase) PMC).joinPlatoon(null);
                            ((EntityPMCBase) PMC).makePlatoon();
                            break;
                        }
                    }
                }
            }
            list = world.getEntitiesWithinAABBExcludingEntity(entityPlayer, entityPlayer.boundingBox.expand(10, 10, 10));
            if (list != null && !list.isEmpty()) {
                for (Object o : list) {
                    Entity PMC = (Entity) o;
                    if (PMC instanceof EntityPMCBase && platoonMatched(itemStack, (EntityPMCBase) PMC)) {
                        if (mode == 0) {
                            entityPlayer.addChatComponentMessage(new ChatComponentTranslation(
                                    "[infantry] Defense  " + x + "," + z + "!"));
                            ((EntityPMCBase) PMC).mode = 0;
                            ((EntityPMCBase) PMC).setTargetCampPosition(new int[]{x,  y,  z});
                            ((EntityPMCBase) PMC).joinPlatoon(null);
                            ((EntityPMCBase) PMC).makePlatoon();
                            break;
                        }
                    }
                }
            }
        }
    }
    public boolean platoonMatched(ItemStack itemStack,EntityPMCBase PMC){
        return (
                (!itemStack.hasDisplayName()&&PMC.platoonName == null)
                        ||
                        (itemStack.hasDisplayName()&&PMC.platoonName != null&&PMC.platoonName.equals(itemStack.getDisplayName()))
        );
    }
}

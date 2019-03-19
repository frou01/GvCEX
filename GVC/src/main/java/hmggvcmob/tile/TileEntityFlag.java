package hmggvcmob.tile;

import com.mojang.authlib.GameProfile;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import hmggvcmob.IflagBattler;
import hmggvcmob.block.GVCBlockFlag;
import hmggvcmob.entity.EntityParachute;
import hmggvcmob.entity.IGVCmob;
import hmggvcmob.entity.TU95;
import hmggvcmob.entity.friend.EntitySoBase;
import hmggvcmob.entity.friend.EntitySoBases;
import hmggvcmob.entity.guerrilla.EntityGBases;
import hmggvcmob.network.GVCMPacketHandler;
import hmggvcmob.network.GVCMPacketSyncFlagdata;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static hmggvcmob.GVCMobPlus.cfg_flagspawninterval;
import static java.lang.Math.*;


public class TileEntityFlag extends TileEntity
{
    private static final String __OBFID = "CL_00000360";
    public Class<? extends EntityLivingBase>[] TeamEntityclass;
    Random rnd = new Random();
    public ResourceLocation flagtexture = new ResourceLocation("gvcmob:textures/model/pflagtexture.png");
    public int flagHeight;
    private Block flagblcokinstace;
    private int respawncycle;
    private int nextSpawnGroupnm;
    public List<Entity> spawnedEntities = new ArrayList<Entity>();
    public void readFromNBT(NBTTagCompound p_145839_1_)
    {
        super.readFromNBT(p_145839_1_);
        flagHeight = p_145839_1_.getInteger("flagHeight");
        respawncycle = p_145839_1_.getInteger("respawncycle");

    }
    public void writeToNBT(NBTTagCompound p_145841_1_)
    {
        super.writeToNBT(p_145841_1_);
        p_145841_1_.setInteger("flagHeight",flagHeight);
        p_145841_1_.setInteger("respawncycle",respawncycle);
    }
    public TileEntityFlag(Class[] teamentitys,GVCBlockFlag block){
        super();
        TeamEntityclass = teamentitys;
        flagtexture = block.flagtexture;
        respawncycle = cfg_flagspawninterval;
    }
    public TileEntityFlag(){
        super();
    }
    @Override
    public Packet getDescriptionPacket() {
        NBTTagCompound nbtTagCompound = new NBTTagCompound();
        this.writeToNBT(nbtTagCompound);
        return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, -1, nbtTagCompound);
    }

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
        this.readFromNBT(pkt.func_148857_g());
    }
    public void updateEntity()
    {
        super.updateEntity();
        flagblcokinstace = worldObj.getBlock(xCoord, yCoord, zCoord);
        if(!worldObj.isRemote) {
            GVCMPacketSyncFlagdata packet = new GVCMPacketSyncFlagdata();
            packet.x = xCoord;
            packet.y = yCoord;
            packet.z = zCoord;
            packet.height = flagHeight;
            GVCMPacketHandler.INSTANCE.sendToAll(packet);
            if (TeamEntityclass == null) {
                if (flagblcokinstace instanceof GVCBlockFlag) {
                    TeamEntityclass = ((GVCBlockFlag) flagblcokinstace).TeamEntityclass;
                }
            } else {
                List nearEntitys = worldObj.getEntitiesWithinAABBExcludingEntity(worldObj.getClosestPlayer(xCoord, yCoord, zCoord, -1), AxisAlignedBB.getBoundingBox(xCoord - 8, yCoord - 8, zCoord - 8, xCoord + 8, yCoord + 8, zCoord + 8));
                Block enemyflag = null;
                int temp = 0;
                List<Object> tempremove = new ArrayList<Object>();
                for (Object te : nearEntitys) {
                    if (!(te instanceof EntityLiving)) {
                        tempremove.add(te);
                        continue;
                    }
                    boolean flag = true;
                    if(te instanceof IflagBattler) {
                        if(!((IflagBattler)te).isthisFlagIsEnemys(flagblcokinstace)) {
                            flagHeight -= 2;
                            flag = false;
                        }
                    }else {
                        for (Class tcl : TeamEntityclass) {
                            if (te.getClass() == tcl) {
                                flagHeight -= 2;
                                flag = false;
                                break;
                            }
                        }
                    }
                    if (flag && te instanceof IflagBattler) {
                        flagHeight += 2;
                        enemyflag = ((IflagBattler) te).getFlag();
                        if(enemyflag == flagblcokinstace){
                            flagHeight -=2;
                        }
                    }
                }
                if(flagHeight<0){
                    flagHeight =0;
                }
                nearEntitys.removeAll(tempremove);
                if (flagHeight > 10000 && enemyflag != null) {
                    worldObj.setBlock(this.xCoord, this.yCoord, this.zCoord, enemyflag);
                }
                if (spawnedEntities.size() < 20 && nearEntitys.size() < 16 && worldObj.getBlock(xCoord, yCoord+2, zCoord).isAir(worldObj,xCoord, yCoord+2, zCoord)) {
                    if(respawncycle<0){
                        nextSpawnGroupnm = 16 + rnd.nextInt(16);
//                    System.out.println("debug");
                        if ((flagblcokinstace) instanceof GVCBlockFlag) {
                            TeamEntityclass = ((GVCBlockFlag) flagblcokinstace).TeamEntityclass;
                            int spawnheight = worldObj.getHeightValue(xCoord, zCoord) + 120;
                            for (int i = 0; i < nextSpawnGroupnm; i++) {
                                int TeamEntitysNumber = rnd.nextInt(TeamEntityclass.length);
                                Entity entityskeleton = null;
                                try {
                                    entityskeleton = TeamEntityclass[TeamEntitysNumber].getConstructor(World.class).newInstance(worldObj);
                                } catch (NoSuchMethodException e) {
                                    e.printStackTrace();
                                } catch (IllegalAccessException e) {
                                    e.printStackTrace();
                                } catch (InstantiationException e) {
                                    e.printStackTrace();
                                } catch (InvocationTargetException e) {
                                    e.printStackTrace();
                                }
                                if (entityskeleton != null) {
                                    EntityParachute entityParachute = new EntityParachute(worldObj);
                                    entityskeleton.setLocationAndAngles(xCoord + 0.5 - 5 + rnd.nextInt(10), spawnheight - 5 + rnd.nextInt(10), zCoord + 0.5 - 5 + rnd.nextInt(10), 0, 0.0F);
                                    entityParachute.setLocationAndAngles(xCoord + 0.5 - 5 + rnd.nextInt(10), spawnheight - 5 + rnd.nextInt(10), zCoord + 0.5 - 5 + rnd.nextInt(10), 0, 0.0F);
                                    if (entityskeleton instanceof EntitySoBases) {
                                        ((EntitySoBases) entityskeleton).addRandomArmor();
                                    } else if (entityskeleton instanceof EntityGBases) {
                                        ((EntityGBases) entityskeleton).addRandomArmor();
                                    }
                                    worldObj.spawnEntityInWorld(entityskeleton);
                                    worldObj.spawnEntityInWorld(entityParachute);
                                    entityParachute.riddenByEntity = entityskeleton;
                                    entityskeleton.ridingEntity = entityParachute;
                                    spawnedEntities.add(entityskeleton);
                                    if (entityskeleton instanceof IGVCmob) ((IGVCmob) entityskeleton).setspawnedtile(this);
                                }
                            }
                        }
                        respawncycle = cfg_flagspawninterval;
//                        respawncycle = 500;
                    }else if(respawncycle  == 50) {
                        int spawnheight = worldObj.getHeightValue(xCoord, zCoord) + 120;
                        if ((flagblcokinstace) instanceof GVCBlockFlag) {
                            if (((GVCBlockFlag) flagblcokinstace).withPlane) {
                                TU95 motherplane = new TU95(worldObj);
                                float f = rnd.nextInt(360);
                                Entity closetplayer = worldObj.getClosestPlayer(xCoord,yCoord,zCoord,FMLCommonHandler.instance().getMinecraftServerInstance().getConfigurationManager().getViewDistance() * 16);
                                if(closetplayer != null) {
                                    float planeoffsetX = (float)closetplayer.posX - xCoord + (float) sin(toRadians(f)) * FMLCommonHandler.instance().getMinecraftServerInstance().getConfigurationManager().getViewDistance() * 16;
                                    float planeoffsetZ = (float)closetplayer.posZ - zCoord + (float) cos(toRadians(f)) * FMLCommonHandler.instance().getMinecraftServerInstance().getConfigurationManager().getViewDistance() * 16;
                                    motherplane.setLocationAndAngles(xCoord + 0.5 + planeoffsetX, spawnheight - 5 + rnd.nextInt(10), zCoord + 0.5 + planeoffsetZ, 0, 0.0F);
                                    motherplane.motionX = -planeoffsetX / sqrt(planeoffsetX * planeoffsetX + planeoffsetZ * planeoffsetZ) * 12.84722222222222;
                                    motherplane.motionZ = -planeoffsetZ / sqrt(planeoffsetX * planeoffsetX + planeoffsetZ * planeoffsetZ) * 12.84722222222222;
                                    motherplane.fuse = 100;
                                    worldObj.spawnEntityInWorld(motherplane);
                                }
                            }
                        }
                    }
                }
                respawncycle--;
            }
        }
    }

    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getRenderBoundingBox()
    {
        AxisAlignedBB bb = INFINITE_EXTENT_AABB;
        Block type = getBlockType();
        if (type != null && type != Blocks.beacon)
        {
            AxisAlignedBB cbb = type.getCollisionBoundingBoxFromPool(worldObj, xCoord, yCoord, zCoord);
            if (cbb != null)
            {
                bb = cbb;
            }
            bb = bb.expand(3,3,3);
        }
        return bb;
    }

}
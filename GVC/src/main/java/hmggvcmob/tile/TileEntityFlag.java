package hmggvcmob.tile;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import hmggvcmob.IflagBattler;
import hmggvcmob.block.GVCBlockFlag;
import hmggvcmob.camp.CampObj;
import hmggvcmob.camp.CampObjAndPos;
import hmggvcmob.network.GVCMPacketHandler;
import hmggvcmob.network.GVCMPacketSyncFlagdata;
import hmggvcmob.world.WorldSavedData_Flag;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

import javax.vecmath.Vector3d;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static hmggvcmob.GVCMobPlus.campsHash;
import static hmggvcmob.GVCMobPlus.cfg_flagspawninterval;


public class TileEntityFlag extends TileEntity
{
    Random rnd = new Random();
    public int flagHeight;
    public int respawncycle;
    private int nextSpawnGroupnm;
	public CampObj campObj;
	public CampObjAndPos campObjAndPos;
    WorldSavedData_Flag worldSavedData_flag;
    public void readFromNBT(NBTTagCompound p_145839_1_)
    {
        super.readFromNBT(p_145839_1_);
        flagHeight = p_145839_1_.getInteger("flagHeight");
        respawncycle = p_145839_1_.getInteger("respawncycle");
        campObj = campsHash.get(p_145839_1_.getString("campObj"));
        campObjAndPos = new CampObjAndPos(new int[]{xCoord,yCoord,zCoord},campObj);
    }
    public void writeToNBT(NBTTagCompound p_145841_1_)
    {
        super.writeToNBT(p_145841_1_);
        p_145841_1_.setInteger("flagHeight",flagHeight);
        p_145841_1_.setInteger("respawncycle",respawncycle);
        p_145841_1_.setString("campObj",campObj.campName);
    }
    public TileEntityFlag(CampObj campObj){
        super();
        this.campObj = campObj;
        this.campObjAndPos = new CampObjAndPos(new int[]{xCoord,yCoord,zCoord},campObj);
        this.flagHeight = campObj.maxFlagHeight;
        respawncycle = cfg_flagspawninterval;
    }
    public TileEntityFlag(){
    }

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
        this.readFromNBT(pkt.func_148857_g());
    }
    public void updateEntity()
    {
        super.updateEntity();
        if(!worldObj.isRemote) {
            //todo https://twitter.com/frou_0/status/1196046528533561344
            this.campObjAndPos.flagPos[0] = xCoord;
            this.campObjAndPos.flagPos[1] = yCoord;
            this.campObjAndPos.flagPos[2] = zCoord;
            this.campObjAndPos.campObj = this.campObj;
            if(worldSavedData_flag == null) worldSavedData_flag = WorldSavedData_Flag.get(worldObj);
            Chunk flagChunk = worldObj.getChunkFromBlockCoords(xCoord,zCoord);
            CampObjAndPos campObj_Camp;
            boolean campChanged = false;
            ChunkCoordIntPair flagChunkCoordIntPair = flagChunk.getChunkCoordIntPair();
            if((campObj_Camp = worldSavedData_flag.campObjHashMap.get(flagChunkCoordIntPair)) != null){
                if(campObj_Camp.campObj != this.campObj) {
                    worldObj.setBlockToAir(xCoord, yCoord, zCoord);
                    this.invalidate();
                    removeFlagData();
                    return;
                }
            } else {
                if(worldSavedData_flag.isDirty()){
                    setFlagData();
                }
            }
            {

                int k = MathHelper.floor_double(yCoord / 16.0D);

                if (k < 0)
                {
                    k = 0;
                }

                if (k >= flagChunk.entityLists.length)
                {
                    k = flagChunk.entityLists.length - 1;
                }
                List nearEntitys = flagChunk.entityLists[k];
                int friendEntityNum = 0;
                int enemyEntityNum = 0;
                CampObj nextCamp = this.campObj;
                campObjAndPos.attacked = false;
                for (Object te : nearEntitys) {
                    if(te instanceof IflagBattler){
                        if(((IflagBattler) te).isThisFriendCamp(this.campObj)){
                            friendEntityNum++;
                            flagHeight++;
                        }
                        if(((IflagBattler) te).isThisAttackAbleCamp(this.campObj)){
                            enemyEntityNum++;
                            nextCamp = ((IflagBattler) te).getCampObj();
                            flagHeight--;
                            campObjAndPos.attacked = true;
                        }
                    }
                }
                if(flagHeight<0){
                    if(this.campObj.isOneTime){
                        worldSavedData_flag.campObjHashMap.remove(flagChunkCoordIntPair);
                        worldObj.setBlockToAir(xCoord,yCoord,zCoord);
                        this.invalidate();
                        return;
                    }else {
                        flagHeight = 0;
                        removeFlagData();
                        campChanged = true;
                        this.campObj = nextCamp;
                        this.campObjAndPos.campObj = this.campObj;
                        replaceFlagData();
                        worldObj.setBlock(xCoord,yCoord,zCoord,this.campObj.campsBlock);
                        worldSavedData_flag.setDirty(true);
                    }
                }else if(flagHeight > this.campObj.maxFlagHeight){
                    flagHeight = this.campObj.maxFlagHeight;
                }
                if(friendEntityNum >= enemyEntityNum && friendEntityNum + enemyEntityNum < 16){
                    if(respawncycle<0){
                        SpawnNewEntities();
                        respawncycle = cfg_flagspawninterval;
                    }
//                    System.out.println("" + respawncycle);
                    respawncycle--;
                }
            }

            GVCMPacketSyncFlagdata packet = new GVCMPacketSyncFlagdata();
            packet.x = xCoord;
            packet.y = yCoord;
            packet.z = zCoord;
            packet.height = flagHeight;
            packet.respawncycle = respawncycle;
            packet.campChanged = campChanged;
            packet.campName = this.campObj.campName;
            GVCMPacketHandler.INSTANCE.sendToAll(packet);
        }
    }

    public void setFlagData(){
        Chunk flagChunk = worldObj.getChunkFromBlockCoords(xCoord,zCoord);
        ChunkCoordIntPair flagChunkCoordIntPair = flagChunk.getChunkCoordIntPair();
        if(worldSavedData_flag == null) worldSavedData_flag = WorldSavedData_Flag.get(worldObj);
        for(int x = -campObj.flagWidth;x <= campObj.flagWidth;x++) for(int z = -campObj.flagWidth;z <= campObj.flagWidth;z++) {
            ChunkCoordIntPair current = new ChunkCoordIntPair(flagChunkCoordIntPair.chunkXPos + x,flagChunkCoordIntPair.chunkZPos + z);
            if(!worldSavedData_flag.campObjHashMap.containsKey(current))
                worldSavedData_flag.campObjHashMap.put(
                        current,
                        campObjAndPos);
        }
    }
    public void replaceFlagData(){
        worldSavedData_flag.setDirty(true);
        Chunk flagChunk = worldObj.getChunkFromBlockCoords(xCoord,zCoord);
        ChunkCoordIntPair flagChunkCoordIntPair = flagChunk.getChunkCoordIntPair();
        if(worldSavedData_flag == null) worldSavedData_flag = WorldSavedData_Flag.get(worldObj);
        for(int x = -campObj.flagWidth;x <= campObj.flagWidth;x++) for(int z = -campObj.flagWidth;z <= campObj.flagWidth;z++) {
            worldSavedData_flag.campObjHashMap.put(
                    new ChunkCoordIntPair(flagChunkCoordIntPair.chunkXPos + x,flagChunkCoordIntPair.chunkZPos + z),
                    campObjAndPos);
        }
    }

    public void removeFlagData(){
        worldSavedData_flag.setDirty(true);
        Chunk flagChunk = worldObj.getChunkFromBlockCoords(xCoord,zCoord);
        ChunkCoordIntPair flagChunkCoordIntPair = flagChunk.getChunkCoordIntPair();
        if(worldSavedData_flag == null) worldSavedData_flag = WorldSavedData_Flag.get(worldObj);
        worldSavedData_flag.setDirty(true);
        for(int x = -campObj.flagWidth;x <= campObj.flagWidth;x++) for(int z = -campObj.flagWidth;z <= campObj.flagWidth;z++) {
            ChunkCoordIntPair current = new ChunkCoordIntPair(flagChunkCoordIntPair.chunkXPos + x,flagChunkCoordIntPair.chunkZPos + z);
            CampObjAndPos campObjAndPos = worldSavedData_flag.campObjHashMap.get(current);
            if(campObjAndPos == null ||
                    !(worldObj.getBlock(campObjAndPos.flagPos[0],campObjAndPos.flagPos[1],campObjAndPos.flagPos[2]) instanceof GVCBlockFlag))
                worldSavedData_flag.campObjHashMap.remove(
                    current);
        }
    }

    public void SpawnNewEntities(){
        nextSpawnGroupnm = 6 + rnd.nextInt(12);
        for(int cnt = 0;cnt < nextSpawnGroupnm;cnt++) {
            int entityType = rnd.nextInt(campObj.teamEntityClasses.length);
            Entity newEntity;
            try {
                newEntity = campObj.teamEntityClasses[entityType].getConstructor(World.class).newInstance(worldObj);
                newEntity.setLocationAndAngles(this.xCoord + getRandomPos(),this.yCoord+1,this.zCoord + getRandomPos()
                        ,rnd.nextFloat()*360,0);
                if(newEntity instanceof EntityLiving){
                    ((EntityLiving) newEntity).onSpawnWithEgg(null);
                }
                if(!newEntity.isEntityInsideOpaqueBlock())
                    worldObj.spawnEntityInWorld(newEntity);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public float getRandomPos(){
        return (rnd.nextFloat()-0.5f)*4;
    }

    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getRenderBoundingBox()
    {
        return INFINITE_EXTENT_AABB;
    }

}
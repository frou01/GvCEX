package handmadevehicle.SlowPathFinder;

import net.minecraft.entity.Entity;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.pathfinding.PathFinder;
import net.minecraft.util.MathHelper;
import net.minecraft.world.ChunkCache;
import net.minecraft.world.World;

import javax.vecmath.Vector3d;

public class WorldForPathfind
{

    public World world;
    public WorldForPathfind(World worldobj){
        world = worldobj;
    }
    public SlowPathfinder slowPathfinder;
    public int serchingposX;
    public int serchingposY;
    public int serchingposZ;


   public PathEntity getEntityPathToXYZ(Entity entity, int targetX, int targetY, int targetZ, float searchRange, boolean isWoddenDoorAllowed, boolean isMovementBlockAllowed, boolean isPathingInWater, boolean canEntityDrown)
    {
        if(slowPathfinder != null && slowPathfinder.isserchingpath){
            PathEntity returnval = slowPathfinder.serchPath();
            return returnval;
        }else {
            int l = MathHelper.floor_double(entity.posX);
            int i1 = MathHelper.floor_double(entity.posY);
            int j1 = MathHelper.floor_double(entity.posZ);
            serchingposX = MathHelper.floor_double(entity.posX);
            serchingposY = MathHelper.floor_double(entity.posY);
            serchingposZ = MathHelper.floor_double(entity.posZ);
            int k1 = (int) (searchRange + 8.0F);//検索範囲
            int l1 = l - k1;
            int i2 = i1 - k1;
            int j2 = j1 - k1;
            int k2 = l + k1;
            int l2 = i1 + k1;
            int i3 = j1 + k1;
            ChunkChaceForPathfind chunkcache = new ChunkChaceForPathfind(this, l1, i2, j2, k2, l2, i3, 0);
//        PathEntity pathentity = (new SlowPathfinder(chunkcache, isWoddenDoorAllowed, isMovementBlockAllowed, isPathingInWater, canEntityDrown)).createEntityPathTo(entity, targetX, targetY, targetZ, searchRange);
            PathEntity backup = null;
            if(slowPathfinder != null)backup = slowPathfinder.currentReturnVal;
            slowPathfinder = new SlowPathfinder(chunkcache, isWoddenDoorAllowed, isMovementBlockAllowed, isPathingInWater, canEntityDrown);
            slowPathfinder.currentReturnVal = backup;
            PathEntity pathentity = slowPathfinder.createEntityPathTo(entity, targetX, targetY, targetZ, searchRange);
            if(!slowPathfinder.isserchingpath) {
                return pathentity;
            }else {
                return null;
            }
        }
    }


    public PathEntity getPathEntityToEntity(Entity p_72865_1_, Entity p_72865_2_, float searchRange, boolean isWoddenDoorAllowed, boolean isMovementBlockAllowed, boolean isPathingInWater, boolean canEntityDrown)
    {
        int i = MathHelper.floor_double(p_72865_1_.posX);
        int j = MathHelper.floor_double(p_72865_1_.posY + 1.0D);
        int k = MathHelper.floor_double(p_72865_1_.posZ);
        int l = (int)(searchRange + 16.0F);
        int i1 = i - l;
        int j1 = j - l;
        int k1 = k - l;
        int l1 = i + l;
        int i2 = j + l;
        int j2 = k + l;
        ChunkChaceForPathfind chunkcache = new ChunkChaceForPathfind(this, i1, j1, k1, l1, i2, j2, 0);

        slowPathfinder = new SlowPathfinder(chunkcache, isWoddenDoorAllowed, isMovementBlockAllowed, isPathingInWater, canEntityDrown);
        PathEntity pathentity = slowPathfinder.createEntityPathTo(p_72865_1_, p_72865_2_,searchRange);
        if(!slowPathfinder.isserchingpath) {
//
            return pathentity;
        }else {
            System.out.println("debug");
            return null;
        }
    }
}
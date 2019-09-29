package handmadevehicle.SlowPathFinder;

import net.minecraft.entity.Entity;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

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


   public PathEntity getEntityPathToXYZ(Entity p_72844_1_, int p_72844_2_, int p_72844_3_, int p_72844_4_, float p_72844_5_, boolean p_72844_6_, boolean p_72844_7_, boolean p_72844_8_, boolean p_72844_9_)
    {
        if(/*((p_72844_2_-serchingposX)*(p_72844_2_-serchingposX)+(p_72844_3_-serchingposY)*(p_72844_3_-serchingposY)+(p_72844_4_-serchingposZ)*(p_72844_4_-serchingposZ)<10) &&*/ slowPathfinder != null && slowPathfinder.isserchingpath){
            PathEntity returnval = slowPathfinder.serchPath();
            return returnval;
        }else {
            int l = MathHelper.floor_double(p_72844_1_.posX);
            int i1 = MathHelper.floor_double(p_72844_1_.posY);
            int j1 = MathHelper.floor_double(p_72844_1_.posZ);
            serchingposX = MathHelper.floor_double(p_72844_1_.posX);
            serchingposY = MathHelper.floor_double(p_72844_1_.posY);
            serchingposZ = MathHelper.floor_double(p_72844_1_.posZ);
            int k1 = (int) (p_72844_5_ + 8.0F);//検索範囲
            int l1 = l - k1;
            int i2 = i1 - k1;
            int j2 = j1 - k1;
            int k2 = l + k1;
            int l2 = i1 + k1;
            int i3 = j1 + k1;
            ChunkChaceForPathfind chunkcache = new ChunkChaceForPathfind(this, l1, i2, j2, k2, l2, i3, 0);
//        PathEntity pathentity = (new SlowPathfinder(chunkcache, p_72844_6_, p_72844_7_, p_72844_8_, p_72844_9_)).createEntityPathTo(p_72844_1_, p_72844_2_, p_72844_3_, p_72844_4_, p_72844_5_);
            slowPathfinder = new SlowPathfinder(chunkcache, p_72844_6_, p_72844_7_, p_72844_8_, p_72844_9_);
            PathEntity pathentity = slowPathfinder.createEntityPathTo(p_72844_1_, p_72844_2_, p_72844_3_, p_72844_4_, p_72844_5_);
            if(!slowPathfinder.isserchingpath) {
//
                return pathentity;
            }else {
//                System.out.println("debug");
                return null;
            }
        }
    }
}
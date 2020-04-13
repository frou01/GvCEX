package handmadevehicle.SlowPathFinder;

import handmadevehicle.entity.parts.IVehicle;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.util.IntHashMap;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;

public class SlowPathfinder {

    private final IBlockAccess worldMap;
    private boolean canEntityDrown;
    private boolean isPathingInWater;
    private boolean isMovementBlockAllowed;
    private boolean isWoddenDoorAllowed;
    private Path_forSlow path =  new Path_forSlow();
    private IntHashMap pointMap = new IntHashMap();
    private PathPoint_slow[] pathOptions = new PathPoint_slow[32];
    public boolean isserchingpath;
    Entity theEntity;
    PathPoint_slow start;
    PathPoint_slow end;
    PathPoint_slow pathpoint1;
    PathPoint_slow current;
    int count = 0;
    int retrycount = 0;

    public SlowPathfinder(IBlockAccess p_i2137_1_)
    {
        this.worldMap = p_i2137_1_;
    }
    public SlowPathfinder(IBlockAccess p_i2137_1_, boolean p_i2137_2_, boolean p_i2137_3_, boolean p_i2137_4_, boolean p_i2137_5_)
    {
        this.worldMap = p_i2137_1_;
        this.isWoddenDoorAllowed = p_i2137_2_;
        this.isMovementBlockAllowed = p_i2137_3_;
        this.isPathingInWater = p_i2137_4_;
        this.canEntityDrown = p_i2137_5_;
        isserchingpath = true;
        retrycount = 0;
    }
    public PathEntity createEntityPathTo(Entity p_75859_1_, int p_75859_2_, int p_75859_3_, int p_75859_4_, float p_75859_5_)
    {
        return this.createEntityPathTo(p_75859_1_, (double)((float)p_75859_2_ + 0.5F), (double)((float)p_75859_3_ + 0.5F), (double)((float)p_75859_4_ + 0.5F), p_75859_5_);
    }
    public PathEntity createEntityPathTo(Entity p_75856_1_, Entity p_75856_2_, float p_75856_3_)
    {
        return this.createEntityPathTo(p_75856_1_, p_75856_2_.posX, p_75856_2_.boundingBox.minY, p_75856_2_.posZ, p_75856_3_);
    }

    private PathEntity createEntityPathTo(Entity p_75857_1_, double p_75857_2_, double p_75857_4_, double p_75857_6_, float p_75857_8_)
    {
        this.path.clearPath();
        this.pointMap.clearMap();
        boolean flag = this.isPathingInWater;
        int i = MathHelper.floor_double(p_75857_1_.boundingBox.minY + 0.5D);

        if (this.canEntityDrown && p_75857_1_.isInWater())
        {
            i = (int)p_75857_1_.boundingBox.minY;

            for (Block block = this.worldMap.getBlock(MathHelper.floor_double(p_75857_1_.posX), i, MathHelper.floor_double(p_75857_1_.posZ)); block == Blocks.flowing_water || block == Blocks.water; block = this.worldMap.getBlock(MathHelper.floor_double(p_75857_1_.posX), i, MathHelper.floor_double(p_75857_1_.posZ)))
            {
                ++i;
            }

            flag = this.isPathingInWater;
            this.isPathingInWater = false;
        }
        else
        {
            i = MathHelper.floor_double(p_75857_1_.boundingBox.minY + 0.5D);
        }

        PathPoint_slow pathpoint2;
        PathPoint_slow pathpoint;
        PathPoint_slow pathpoint1;
        if(p_75857_1_ instanceof IVehicle){
            pathpoint2 = this.openPoint(MathHelper.floor_double(p_75857_1_.boundingBox.minX), i, MathHelper.floor_double(p_75857_1_.boundingBox.minZ));
            pathpoint = this.openPoint(MathHelper.floor_double(p_75857_2_ - (double)(p_75857_1_.width / 2.0F + 2.5)), MathHelper.floor_double(p_75857_4_), MathHelper.floor_double(p_75857_6_ - (double)(p_75857_1_.width / 2.0F + 2.5)));
            pathpoint1 = new PathPoint_slow(MathHelper.floor_float(p_75857_1_.width + 5.0F), MathHelper.floor_float(p_75857_1_.height + 5.0F), MathHelper.floor_float(p_75857_1_.width + 5.0F));
        }else {
            pathpoint2 = this.openPoint(MathHelper.floor_double(p_75857_1_.boundingBox.minX), i, MathHelper.floor_double(p_75857_1_.boundingBox.minZ));
            pathpoint = this.openPoint(MathHelper.floor_double(p_75857_2_ - (double)(p_75857_1_.width / 2.0F)), MathHelper.floor_double(p_75857_4_), MathHelper.floor_double(p_75857_6_ - (double)(p_75857_1_.width / 2.0F)));
            pathpoint1 = new PathPoint_slow(MathHelper.floor_float(p_75857_1_.width + 1.0F), MathHelper.floor_float(p_75857_1_.height + 1.0F), MathHelper.floor_float(p_75857_1_.width + 1.0F));
        }

        PathEntity pathentity = this.addToPath(p_75857_1_, pathpoint2, pathpoint, pathpoint1, p_75857_8_);
        this.isPathingInWater = flag;
        return pathentity;
    }

    private final PathPoint_slow openPoint(int p_75854_1_, int p_75854_2_, int p_75854_3_)
    {
        int l = PathPoint_slow.makeHash(p_75854_1_, p_75854_2_, p_75854_3_);
        PathPoint_slow pathpoint = (PathPoint_slow)this.pointMap.lookup(l);

        if (pathpoint == null)
        {
            pathpoint = new PathPoint_slow(p_75854_1_, p_75854_2_, p_75854_3_);
            this.pointMap.addKey(l, pathpoint);
        }

        return pathpoint;
    }
    private PathEntity addToPath(Entity p_75861_1_, PathPoint_slow start, PathPoint_slow end, PathPoint_slow p_75861_4_, float p_75861_5_)
    {
        theEntity = p_75861_1_;
        start.totalPathDistance = 0.0F;
        start.distanceToNext = start.distanceToSquared(end);
        start.distanceToTarget = start.distanceToNext;
        this.path.clearPath();
        this.path.addPoint(start);
        current = start;
        this.start = start;
        this.end = end;
        pathpoint1 = p_75861_4_;
        this.count = 0;
        return serchPath();
    }
    public PathEntity currentReturnVal;
    public PathEntity serchPath(){
        retrycount++;
        int cntmax = 32;
        for(int cnt = 0; cnt<cntmax; cnt++) {
            if (!this.path.isPathEmpty()) {
//                System.out.println("debug" + path);
                PathPoint_slow pathpoint4 = this.path.dequeue();
//                System.out.println("debug" + (path));
//                System.out.println("debug" + (pathpoint4));
//                System.out.println("debug" + (path.count));
                if (pathpoint4.equals(end)) {
                    isserchingpath = false;
                    return this.createEntityPath(start, end);
                }

                if (pathpoint4.distanceToSquared(end) < current.distanceToSquared(end)) {
                    current = pathpoint4;
                }

                pathpoint4.isFirst = true;
                int i = this.findPathOptions(theEntity, pathpoint4, pathpoint1, end, 512);

                for (int j = 0; j < i; ++j) {
//                    System.out.println("debug j = " + j);
                    PathPoint_slow pathpoint5 = this.pathOptions[j];
                    float f1 = pathpoint4.totalPathDistance + pathpoint4.distanceToSquared(pathpoint5);

                    if (!pathpoint5.isAssigned() || f1 < pathpoint5.totalPathDistance) {
                        pathpoint5.previous = pathpoint4;
                        pathpoint5.totalPathDistance = f1;
                        pathpoint5.distanceToNext = pathpoint5.distanceToSquared(end);

                        if (pathpoint5.isAssigned()) {
                            this.path.changeDistance(pathpoint5, pathpoint5.totalPathDistance + pathpoint5.distanceToNext);
                        } else {
                            pathpoint5.distanceToTarget = pathpoint5.totalPathDistance + pathpoint5.distanceToNext;
                            this.path.addPoint(pathpoint5);
                        }
                    }
                }
            }else break;
        }

        if(this.path.isPathEmpty()) {//検索終了
            isserchingpath = false;
            if (current == start) {
                return null;
            } else {
                return currentReturnVal = this.createEntityPath(start, current);
            }
        }else if(this.retrycount>8) {
//            System.out.println("debug" + returnval);
            isserchingpath = false;
            if (current == start) {
                return null;
            } else {
                return currentReturnVal = this.createEntityPath(start, current);
            }
        }else {
            isserchingpath = true;
            return currentReturnVal;
        }
    }
    private PathEntity createEntityPath(PathPoint_slow p_75853_1_, PathPoint_slow p_75853_2_)
    {
        int i = 1;
        PathPoint_slow pathpoint2;

        for (pathpoint2 = p_75853_2_; pathpoint2.previous != null; pathpoint2 = pathpoint2.previous)
        {
            ++i;
        }

        PathPoint[] apathpoint = new PathPoint[i];
        pathpoint2 = p_75853_2_;
        --i;

        for (apathpoint[i] = p_75853_2_; pathpoint2.previous != null; apathpoint[i] = pathpoint2)
        {
            pathpoint2 = pathpoint2.previous;
            --i;
        }

        return new PathEntity(apathpoint);
    }
    private int findPathOptions(Entity p_75860_1_, PathPoint_slow p_75860_2_, PathPoint_slow p_75860_3_, PathPoint_slow p_75860_4_, float p_75860_5_)
    {
        int i = 0;
        byte b0 = 0;

        if (this.getVerticalOffset(p_75860_1_, p_75860_2_.xCoord, p_75860_2_.yCoord + 1, p_75860_2_.zCoord, p_75860_3_) == 1)
        {
            b0 = 1;
        }

        PathPoint_slow pathpoint3 = this.getSafePoint(p_75860_1_, p_75860_2_.xCoord, p_75860_2_.yCoord, p_75860_2_.zCoord + 1, p_75860_3_, b0);
        PathPoint_slow pathpoint4 = this.getSafePoint(p_75860_1_, p_75860_2_.xCoord - 1, p_75860_2_.yCoord, p_75860_2_.zCoord, p_75860_3_, b0);
        PathPoint_slow pathpoint5 = this.getSafePoint(p_75860_1_, p_75860_2_.xCoord + 1, p_75860_2_.yCoord, p_75860_2_.zCoord, p_75860_3_, b0);
        PathPoint_slow pathpoint6 = this.getSafePoint(p_75860_1_, p_75860_2_.xCoord, p_75860_2_.yCoord, p_75860_2_.zCoord - 1, p_75860_3_, b0);

        if (pathpoint3 != null && !pathpoint3.isFirst && pathpoint3.distanceTo(p_75860_4_) < p_75860_5_)
        {
            this.pathOptions[i++] = pathpoint3;
        }

        if (pathpoint4 != null && !pathpoint4.isFirst && pathpoint4.distanceTo(p_75860_4_) < p_75860_5_)
        {
            this.pathOptions[i++] = pathpoint4;
        }

        if (pathpoint5 != null && !pathpoint5.isFirst && pathpoint5.distanceTo(p_75860_4_) < p_75860_5_)
        {
            this.pathOptions[i++] = pathpoint5;
        }

        if (pathpoint6 != null && !pathpoint6.isFirst && pathpoint6.distanceTo(p_75860_4_) < p_75860_5_)
        {
            this.pathOptions[i++] = pathpoint6;
        }

        return i;
    }
    private PathPoint_slow getSafePoint(Entity p_75858_1_, int p_75858_2_, int p_75858_3_, int p_75858_4_, PathPoint_slow p_75858_5_, int p_75858_6_)
    {
        PathPoint_slow pathpoint1 = null;
        int i1 = this.getVerticalOffset(p_75858_1_, p_75858_2_, p_75858_3_, p_75858_4_, p_75858_5_);

        if (i1 == 2)
        {
            return this.openPoint(p_75858_2_, p_75858_3_, p_75858_4_);
        }
        else
        {
            if (i1 == 1)
            {
                pathpoint1 = this.openPoint(p_75858_2_, p_75858_3_, p_75858_4_);
            }

            if (pathpoint1 == null && p_75858_6_ > 0 && i1 != -3 && i1 != -4 && this.getVerticalOffset(p_75858_1_, p_75858_2_, p_75858_3_ + p_75858_6_, p_75858_4_, p_75858_5_) == 1)
            {
                pathpoint1 = this.openPoint(p_75858_2_, p_75858_3_ + p_75858_6_, p_75858_4_);
                p_75858_3_ += p_75858_6_;
            }

            if (pathpoint1 != null)
            {
                int j1 = 0;
                int k1 = 0;

                while (p_75858_3_ > 0)
                {
                    k1 = this.getVerticalOffset(p_75858_1_, p_75858_2_, p_75858_3_ - 1, p_75858_4_, p_75858_5_);

                    if (false && k1 == -1)
                    {
                        return null;
                    }

                    if (k1 != 1)
                    {
                        break;
                    }

                    if (j1++ >= p_75858_1_.getMaxSafePointTries())
                    {
                        return null;
                    }

                    --p_75858_3_;

                    if (p_75858_3_ > 0)
                    {
                        pathpoint1 = this.openPoint(p_75858_2_, p_75858_3_, p_75858_4_);
                    }
                }

                if (k1 == -2)
                {
                    return null;
                }
            }

            return pathpoint1;
        }
    }
    public int getVerticalOffset(Entity p_75855_1_, int p_75855_2_, int p_75855_3_, int p_75855_4_, PathPoint p_75855_5_)
    {
        return func_82565_a(p_75855_1_, p_75855_2_, p_75855_3_, p_75855_4_, p_75855_5_, isPathingInWater, isMovementBlockAllowed, isWoddenDoorAllowed);
    }
    public static int func_82565_a(Entity p_82565_0_, int p_82565_1_, int p_82565_2_, int p_82565_3_, PathPoint p_82565_4_, boolean p_82565_5_, boolean p_82565_6_, boolean p_82565_7_)
    {
        boolean flag3 = false;

        for (int l = p_82565_1_; l < p_82565_1_ + p_82565_4_.xCoord; ++l)
        {
            for (int i1 = p_82565_2_; i1 < p_82565_2_ + p_82565_4_.yCoord; ++i1)
            {
                for (int j1 = p_82565_3_; j1 < p_82565_3_ + p_82565_4_.zCoord; ++j1)
                {
                    Block block = p_82565_0_.worldObj.getBlock(l, i1, j1);

                    if (block.getMaterial() != Material.air)
                    {
                        if (block == Blocks.trapdoor)
                        {
                            flag3 = true;
                        }
                        else if (block != Blocks.flowing_water && block != Blocks.water)
                        {
                            if (!p_82565_7_ && block == Blocks.wooden_door)
                            {
                                return 0;
                            }
                        }
                        else
                        {
                            if (p_82565_5_)
                            {
                                return -1;
                            }

                            flag3 = true;
                        }

                        int k1 = block.getRenderType();

                        if (p_82565_0_.worldObj.getBlock(l, i1, j1).getRenderType() == 9)
                        {
                            int j2 = MathHelper.floor_double(p_82565_0_.posX);
                            int l1 = MathHelper.floor_double(p_82565_0_.posY);
                            int i2 = MathHelper.floor_double(p_82565_0_.posZ);

                            if (p_82565_0_.worldObj.getBlock(j2, l1, i2).getRenderType() != 9 && p_82565_0_.worldObj.getBlock(j2, l1 - 1, i2).getRenderType() != 9)
                            {
                                return -3;
                            }
                        }
                        else if (!block.getBlocksMovement(p_82565_0_.worldObj, l, i1, j1) && (!p_82565_6_ || block != Blocks.wooden_door))
                        {
                            if (k1 == 11 || block == Blocks.fence_gate || k1 == 32)
                            {
                                return -3;
                            }

                            if (block == Blocks.trapdoor)
                            {
                                return -4;
                            }

                            Material material = block.getMaterial();

                            if (material != Material.lava)
                            {
                                return 0;
                            }

                            if (!p_82565_0_.handleLavaMovement())
                            {
                                return -2;
                            }
                        }
                    }
                }
            }
        }

        return flag3 ? 2 : 1;
    }
}

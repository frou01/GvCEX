package handmadeguns.Util;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFence;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class Utils {
	public static Vec3 getLook(float p_70676_1_, Entity entity)
	{
	    float f1;
	    float f2;
	    float f3;
	    float f4;
	    
	    
	    if (p_70676_1_ == 1.0F)
	    {
	        f1 = MathHelper.cos(-(entity instanceof EntityLivingBase ? ((EntityLivingBase)entity).rotationYawHead : entity.rotationYaw) * 0.017453292F - (float)Math.PI);
	        f2 = MathHelper.sin(-(entity instanceof EntityLivingBase ? ((EntityLivingBase)entity).rotationYawHead : entity.rotationYaw) * 0.017453292F - (float)Math.PI);
	        f3 = -MathHelper.cos(-entity.rotationPitch * 0.017453292F);
	        f4 = MathHelper.sin(-entity.rotationPitch * 0.017453292F);
	        return Vec3.createVectorHelper((double)(f2 * f3), (double)f4, (double)(f1 * f3));
	    }else {
	        return null;
	    }
	}
	
	public static Vec3 RotationVector_byAxisVector(Vec3 axis, Vec3 tovec, float angle)
	{
	    double axisVectorX = axis.xCoord;
	    double axisVectorY = axis.yCoord;
	    double axisVectorZ = axis.zCoord;
	    double toVectorX = tovec.xCoord;
	    double toVectorY = tovec.yCoord;
	    double toVectorZ = tovec.zCoord;
	    double angleRad = (double)angle / 180.0D * Math.PI;
	    double sintheta = Math.sin(angleRad);
	    double costheta = Math.cos(angleRad);
	    double returnVectorX = (axisVectorX * axisVectorX * (1 - costheta) + costheta)               * toVectorX + (axisVectorX * axisVectorY * (1 - costheta) - axisVectorZ * sintheta) * toVectorY + (axisVectorZ * axisVectorX * (1 - costheta) + axisVectorY * sintheta) * toVectorZ;
	    double returnVectorY = (axisVectorX * axisVectorY * (1 - costheta) + axisVectorZ * sintheta) * toVectorX + (axisVectorY * axisVectorY * (1 - costheta) + costheta)               * toVectorY + (axisVectorY * axisVectorZ * (1 - costheta) - axisVectorX * sintheta) * toVectorZ;
	    double returnVectorZ = (axisVectorZ * axisVectorX * (1 - costheta) - axisVectorY * sintheta) * toVectorX + (axisVectorY * axisVectorZ * (1 - costheta) + axisVectorX * sintheta) * toVectorY + (axisVectorZ * axisVectorZ * (1 - costheta) + costheta)               * toVectorZ;
	    
	    return Vec3.createVectorHelper(returnVectorX, returnVectorY, returnVectorZ);
	}
	
	public static MovingObjectPosition getmovingobjectPosition_forBlock(World worldObj, Vec3 start, Vec3 end, boolean p_147447_3_, boolean p_147447_4_, boolean p_147447_5_){
		return getmovingobjectPosition_forBlock(worldObj, start, end, p_147447_3_, p_147447_4_, p_147447_5_,3);
	}
	static int penerateCnt;
	public static MovingObjectPosition getmovingobjectPosition_forBlock(World worldObj, Vec3 start, Vec3 end, boolean p_147447_3_, boolean p_147447_4_, boolean p_147447_5_,int penerateCnt_in){
		penerateCnt = penerateCnt_in;
	    if (!Double.isNaN(start.xCoord) && !Double.isNaN(start.yCoord) && !Double.isNaN(start.zCoord))
	    {
	        if (!Double.isNaN(end.xCoord) && !Double.isNaN(end.yCoord) && !Double.isNaN(end.zCoord))
	        {
	            int i = MathHelper.floor_double(end.xCoord);
	            int j = MathHelper.floor_double(end.yCoord);
	            int k = MathHelper.floor_double(end.zCoord);
	            int l = MathHelper.floor_double(start.xCoord);
	            int i1 = MathHelper.floor_double(start.yCoord);
	            int j1 = MathHelper.floor_double(start.zCoord);
	            Block block = worldObj.getBlock(l, i1, j1);
	            int k1 = worldObj.getBlockMetadata(l, i1, j1);
	            
	            if ((!p_147447_4_ || block.getCollisionBoundingBoxFromPool(worldObj, l, i1, j1) != null) && isCollidableBlock(block) && block.canCollideCheck(k1, p_147447_3_))
	            {
	                MovingObjectPosition movingobjectposition = block.collisionRayTrace(worldObj, l, i1, j1, start, end);
	                
	                if (movingobjectposition != null)
	                {
	                    return movingobjectposition;
	                }
	            }
	            
	            MovingObjectPosition movingobjectposition2 = null;
	            k1 = 200;
	            
	            while (k1-- >= 0)
	            {
	                if (Double.isNaN(start.xCoord) || Double.isNaN(start.yCoord) || Double.isNaN(start.zCoord))
	                {
	                    return null;
	                }
	                
	                if (l == i && i1 == j && j1 == k)
	                {
	                    return p_147447_5_ ? movingobjectposition2 : null;
	                }
	                
	                boolean flag6 = true;
	                boolean flag3 = true;
	                boolean flag4 = true;
	                double d0 = 999.0D;
	                double d1 = 999.0D;
	                double d2 = 999.0D;
	                
	                if (i > l)
	                {
	                    d0 = (double)l + 1.0D;
	                }
	                else if (i < l)
	                {
	                    d0 = (double)l + 0.0D;
	                }
	                else
	                {
	                    flag6 = false;
	                }
	                
	                if (j > i1)
	                {
	                    d1 = (double)i1 + 1.0D;
	                }
	                else if (j < i1)
	                {
	                    d1 = (double)i1 + 0.0D;
	                }
	                else
	                {
	                    flag3 = false;
	                }
	                
	                if (k > j1)
	                {
	                    d2 = (double)j1 + 1.0D;
	                }
	                else if (k < j1)
	                {
	                    d2 = (double)j1 + 0.0D;
	                }
	                else
	                {
	                    flag4 = false;
	                }
	                
	                double d3 = 999.0D;
	                double d4 = 999.0D;
	                double d5 = 999.0D;
	                double d6 = end.xCoord - start.xCoord;
	                double d7 = end.yCoord - start.yCoord;
	                double d8 = end.zCoord - start.zCoord;
	                
	                if (flag6)
	                {
	                    d3 = (d0 - start.xCoord) / d6;
	                }
	                
	                if (flag3)
	                {
	                    d4 = (d1 - start.yCoord) / d7;
	                }
	                
	                if (flag4)
	                {
	                    d5 = (d2 - start.zCoord) / d8;
	                }
	                byte b0;
	                
	                if (d3 < d4 && d3 < d5)
	                {
	                    if (i > l)
	                    {
	                        b0 = 4;
	                    }
	                    else
	                    {
	                        b0 = 5;
	                    }
	                    
	                    start.xCoord = d0;
	                    start.yCoord += d7 * d3;
	                    start.zCoord += d8 * d3;
	                }
	                else if (d4 < d5)
	                {
	                    if (j > i1)
	                    {
	                        b0 = 0;
	                    }
	                    else
	                    {
	                        b0 = 1;
	                    }
	                    
	                    start.xCoord += d6 * d4;
	                    start.yCoord = d1;
	                    start.zCoord += d8 * d4;
	                }
	                else
	                {
	                    if (k > j1)
	                    {
	                        b0 = 2;
	                    }
	                    else
	                    {
	                        b0 = 3;
	                    }
	                    
	                    start.xCoord += d6 * d5;
	                    start.yCoord += d7 * d5;
	                    start.zCoord = d2;
	                }
	                
	                Vec3 vec32 = Vec3.createVectorHelper(start.xCoord, start.yCoord, start.zCoord);
	                l = (int)(vec32.xCoord = (double)MathHelper.floor_double(start.xCoord));
	                
	                if (b0 == 5)
	                {
	                    --l;
	                    ++vec32.xCoord;
	                }
	                
	                i1 = (int)(vec32.yCoord = (double)MathHelper.floor_double(start.yCoord));
	                
	                if (b0 == 1)
	                {
	                    --i1;
	                    ++vec32.yCoord;
	                }
	                
	                j1 = (int)(vec32.zCoord = (double)MathHelper.floor_double(start.zCoord));
	                
	                if (b0 == 3)
	                {
	                    --j1;
	                    ++vec32.zCoord;
	                }
	                
	                Block block1 = worldObj.getBlock(l, i1, j1);
	                int l1 = worldObj.getBlockMetadata(l, i1, j1);
	                
	                if (!p_147447_4_ || isCollidableBlock(block1) && block1.getCollisionBoundingBoxFromPool(worldObj, l, i1, j1) != null)
	                {
	                    if ( block1.canCollideCheck(l1, p_147447_3_))
	                    {
	                        MovingObjectPosition movingobjectposition1 = block1.collisionRayTrace(worldObj, l, i1, j1, start, end);
	                        
	                        if (movingobjectposition1 != null)
	                        {
	                            return movingobjectposition1;
	                        }
	                    }
	                    else
	                    {
	                        movingobjectposition2 = new MovingObjectPosition(l, i1, j1, b0, start, false);
	                    }
	                }
	            }
	            
	            return p_147447_5_ ? movingobjectposition2 : null;
	        }
	        else
	        {
	            return null;
	        }
	    }
	    else
	    {
	        return null;
	    }
	}
	
	public static boolean isCollidableBlock(Block block){
	    return !((((block.getMaterial() == Material.air) || (block.getMaterial() == Material.plants) || (block.getMaterial() == Material.leaves) || (block.getMaterial() == Material.fire) || ((
	                                                                                                                block.getMaterial() == Material.glass ||
	                                                                                                                        block instanceof BlockFence ||
	                                                                                                                        block instanceof BlockFenceGate ||
	                                                                                                                        block == Blocks.iron_bars) && --penerateCnt>0/*こっちは貫通回数減少*/))));
	}
}

package hmggvcmob;

import hmggvcutil.GVCUtils;
import hmggvcmob.entity.guerrilla.*;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;

public class GVCTileEntityMobSpawner extends TileEntity
{
    public float field_145972_a;
    public float field_145975_i;
    public int field_145973_j;
    private int field_145974_k;
    private static final String __OBFID = "CL_00000355";

    public void updateEntity()
    {
        super.updateEntity();

        if (this.worldObj.getTotalWorldTime() % 80L == 0L){
        	int x = MathHelper.floor_double(xCoord);
        	int y = MathHelper.floor_double(yCoord);
        	int z = MathHelper.floor_double(zCoord);
        	int i1 = this.worldObj.rand.nextInt(10);
        	int j = this.worldObj.rand.nextInt(2);
        	int k = this.worldObj.rand.nextInt(11);
        	int i2 = this.worldObj.rand.nextInt(3)+1;

        			if (!this.worldObj.isRemote){
        				for (int i0 = 0; i0 < i2; ++i0){
        				this.SpwanGuerrilla(i1, j+2, k, 7);
        				}
            	}
        }
        
    }
    
    
    public void mobspawn(int par1, int par2, int par3){
    	int x = MathHelper.floor_double(par1);
    	int y = MathHelper.floor_double(par2);
    	int z = MathHelper.floor_double(par3);
    	int i1 = this.worldObj.rand.nextInt(10);
    	int j = this.worldObj.rand.nextInt(2);
    	int k = this.worldObj.rand.nextInt(11);
    	int i2 = this.worldObj.rand.nextInt(3)+1;

    			if (!this.worldObj.isRemote){
    				for (int i0 = 0; i0 < i2; ++i0){
    				this.SpwanGuerrilla(i1, j+2, k, 7);
    				}
        	}
    }
    
    public void SpwanGuerrilla(int x, int y, int z, int pa){
    	int i = this.worldObj.rand.nextInt(10);
    	int j = this.worldObj.rand.nextInt(11);
    	//int i = 0;
    	   if(this.worldObj.rand.nextInt(pa) == 0
    			   ||this.worldObj.rand.nextInt(pa) == 1
    			   ||this.worldObj.rand.nextInt(pa) == 2){
    	   GVCEntityGuerrilla entityskeleton = new GVCEntityGuerrilla(worldObj);
           entityskeleton.setLocationAndAngles(this.xCoord+i+x-5, this.yCoord+y, this.zCoord+j+z-5, 1, 0.0F);
           entityskeleton.setCurrentItemOrArmor(0, new ItemStack(GVCUtils.fn_ak74));
           worldObj.spawnEntityInWorld(entityskeleton);
               }
    	   if(this.worldObj.rand.nextInt(pa) == 4){
        	   GVCEntityGuerrillaRPG entityskeleton = new GVCEntityGuerrillaRPG(worldObj);
               entityskeleton.setLocationAndAngles(this.xCoord+i+x-5, this.yCoord+y, this.zCoord+j+z-5, 1, 0.0F);
               entityskeleton.setCurrentItemOrArmor(0, new ItemStack(GVCUtils.fn_rpg7));
               worldObj.spawnEntityInWorld(entityskeleton);
               }
    	   if(this.worldObj.rand.nextInt(pa) == 5){
        	   GVCEntityGuerrillaMG entityskeleton = new GVCEntityGuerrillaMG(worldObj);
               entityskeleton.setLocationAndAngles(this.xCoord+i+x-5, this.yCoord+y-5, this.zCoord+j+z-5, 1, 0.0F);
               entityskeleton.setCurrentItemOrArmor(0, new ItemStack(GVCUtils.fn_pkm));
               worldObj.spawnEntityInWorld(entityskeleton);
               }
    	   if(this.worldObj.rand.nextInt(pa) == 6){
        	   GVCEntityGuerrillaSP entityskeleton = new GVCEntityGuerrillaSP(worldObj);
               entityskeleton.setLocationAndAngles(this.xCoord+i+x-5, this.yCoord+y-5, this.zCoord+j+z-5, 1, 0.0F);
               entityskeleton.setCurrentItemOrArmor(0, new ItemStack(GVCUtils.fn_svd));
               worldObj.spawnEntityInWorld(entityskeleton);
               }
    	   if(this.worldObj.rand.nextInt(pa) == 7){
        	   GVCEntityGuerrillaBM entityskeleton = new GVCEntityGuerrillaBM(worldObj);
               entityskeleton.setLocationAndAngles(this.xCoord+i+x-5, this.yCoord+y-5, this.zCoord+j+z-5, 1, 0.0F);
               //entityskeleton.setCurrentItemOrArmor(0, new ItemStack(GVCUtils.fn_pkm));
               worldObj.spawnEntityInWorld(entityskeleton);
               }
    }
    
}
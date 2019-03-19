package hmggvcmob.entity.friend;

import hmggvcutil.GVCUtils;
import hmggvcmob.GVCMobPlus;
import hmggvcmob.entity.guerrilla.*;
import net.minecraft.block.Block;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class GVCEntityFlag extends EntityGolem 
{
    private static final String __OBFID = "CL_00001650";

    public GVCEntityFlag(World p_i1692_1_)
    {
        super(p_i1692_1_);
        this.setSize(0.4F, 1.8F);
        //this.getNavigator().setAvoidsWater(true);
        //this.tasks.addTask(1, new EntityAIArrowAttack(this, 1.25D, 20, 10.0F));
        //this.tasks.addTask(2, new EntityAIWander(this, 1.0D));
        //this.tasks.addTask(3, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
        //this.tasks.addTask(4, new EntityAILookIdle(this));
        //this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityLiving.class, 0, true, false, IMob.mobSelector));
    }

    /**
     * Returns true if the newer Entity AI code should be run
     */
    public boolean isAIEnabled()
    {
        return true;
    }

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(100.0D);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0D);
        this.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(100D);
    }

    

    protected Item getDropItem()
    {
        return GVCMobPlus.fn_flagegg;
    }

    public void onUpdate()
    {
    	int x = MathHelper.floor_double(this.posX);
    	int y = MathHelper.floor_double(this.posY);
    	int z = MathHelper.floor_double(this.posZ);
//    	if(this.worldObj.getBlock(x, y-1, z) == Blocks.redstone_block){
//    		if(this.worldObj.getBlock(x+1, y, z) == Blocks.redstone_block){
//    			if (!this.worldObj.isRemote){
//    				this.SpwanGuerrilla((int)GVCMobPlus.cfg_guerrillasrach-10, 0, 0, GVCMobPlus.cfg_flagspawnlevel);}
//        	}
//    		if(this.worldObj.getBlock(x, y, z+1) == Blocks.redstone_block){
//    			if (!this.worldObj.isRemote){
//    				this.SpwanGuerrilla(0, 0, (int)GVCMobPlus.cfg_guerrillasrach-10, GVCMobPlus.cfg_flagspawnlevel);}
//        	}
//    		if(this.worldObj.getBlock(x-1, y, z) == Blocks.redstone_block){
//    			if (!this.worldObj.isRemote){
//    				this.SpwanGuerrilla(-(int)GVCMobPlus.cfg_guerrillasrach+10, 0, 0, GVCMobPlus.cfg_flagspawnlevel);}
//        	}
//    		if(this.worldObj.getBlock(x, y, z-1) == Blocks.redstone_block){
//    			if (!this.worldObj.isRemote){
//    				this.SpwanGuerrilla(0, 0, -(int)GVCMobPlus.cfg_guerrillasrach+10, GVCMobPlus.cfg_flagspawnlevel);}
//        	}
//    	}
//    	if(this.worldObj.getBlock(x, y-1, z) == Blocks.iron_block){
//    		if(this.worldObj.getBlock(x+1, y, z) == Blocks.iron_block){
//    			if (!this.worldObj.isRemote){
//    				this.SpwanGuerrilla(30, 0, 0, 120);}
//        	}
//    		if(this.worldObj.getBlock(x, y, z+1) == Blocks.iron_block){
//    			if (!this.worldObj.isRemote){
//    				this.SpwanGuerrilla(0, 0, 30, 120);}
//        	}
//    		if(this.worldObj.getBlock(x-1, y, z) == Blocks.iron_block){
//    			if (!this.worldObj.isRemote){
//    				this.SpwanGuerrilla(-30, 0, 0, 120);}
//        	}
//    		if(this.worldObj.getBlock(x, y, z-1) == Blocks.iron_block){
//    			if (!this.worldObj.isRemote){
//    				this.SpwanGuerrilla(0, 0, -30, 120);}
//        	}
//    	}
    	super.onUpdate();
    }

    public void SpwanGuerrilla(int x, int y, int z, int pa){
    	int i = this.worldObj.rand.nextInt(10);
    	//int i = 0;
    	   if(this.worldObj.rand.nextInt(pa) == 0
    			   ||this.worldObj.rand.nextInt(pa) == 1
    			   ||this.worldObj.rand.nextInt(pa) == 2){
    	   GVCEntityGuerrilla entityskeleton = new GVCEntityGuerrilla(worldObj);
           entityskeleton.setLocationAndAngles(this.posX+i+x, this.posY+y, this.posZ+i+z, this.rotationYaw, 0.0F);
           entityskeleton.setCurrentItemOrArmor(0, new ItemStack(GVCUtils.fn_ak74));
           worldObj.spawnEntityInWorld(entityskeleton);
               }
    	   /*if(this.worldObj.rand.nextInt(pa) == 3){
    	   GVCEntityGuerrillaP entityskeleton = new GVCEntityGuerrillaP(worldObj);
           entityskeleton.setLocationAndAngles(this.posX+i+x, this.posY+y+20, this.posZ+i+z, this.rotationYaw, 0.0F);
           entityskeleton.setCurrentItemOrArmor(0, new ItemStack(GVCUtils.fn_aks74u));
           worldObj.spawnEntityInWorld(entityskeleton);
               }*/
    	   if(this.worldObj.rand.nextInt(pa) == 4){
        	   GVCEntityGuerrillaRPG entityskeleton = new GVCEntityGuerrillaRPG(worldObj);
               entityskeleton.setLocationAndAngles(this.posX+i+x, this.posY+y, this.posZ+i+z, this.rotationYaw, 0.0F);
               entityskeleton.setCurrentItemOrArmor(0, new ItemStack(GVCUtils.fn_rpg7));
               worldObj.spawnEntityInWorld(entityskeleton);
               }
    	   if(this.worldObj.rand.nextInt(pa) == 5){
        	   GVCEntityGuerrillaMG entityskeleton = new GVCEntityGuerrillaMG(worldObj);
               entityskeleton.setLocationAndAngles(this.posX+i+x, this.posY+y, this.posZ+i+z, this.rotationYaw, 0.0F);
               entityskeleton.setCurrentItemOrArmor(0, new ItemStack(GVCUtils.fn_pkm));
               worldObj.spawnEntityInWorld(entityskeleton);
               }
    	   if(this.worldObj.rand.nextInt(pa) == 6){
        	   GVCEntityGuerrillaSP entityskeleton = new GVCEntityGuerrillaSP(worldObj);
               entityskeleton.setLocationAndAngles(this.posX+i+x, this.posY+y, this.posZ+i+z, this.rotationYaw, 0.0F);
               entityskeleton.setCurrentItemOrArmor(0, new ItemStack(GVCUtils.fn_svd));
               worldObj.spawnEntityInWorld(entityskeleton);
               }
    	   if(this.worldObj.rand.nextInt(pa) == 7){
        	   GVCEntityGuerrillaBM entityskeleton = new GVCEntityGuerrillaBM(worldObj);
               entityskeleton.setLocationAndAngles(this.posX+i+x, this.posY+y, this.posZ+i+z, this.rotationYaw, 0.0F);
               //entityskeleton.setCurrentItemOrArmor(0, new ItemStack(GVCUtils.fn_pkm));
               worldObj.spawnEntityInWorld(entityskeleton);
               }
    }
    
    /**
     * Returns the sound this mob makes when it is hurt.
     */
    protected String getHurtSound()
    {
        return "mob.irongolem.hit";
    }

    /**
     * Returns the sound this mob makes on death.
     */
    protected String getDeathSound()
    {
        return "mob.irongolem.death";
    }

    protected void func_145780_a(int p_145780_1_, int p_145780_2_, int p_145780_3_, Block p_145780_4_)
    {
        this.playSound("mob.irongolem.walk", 1.0F, 1.0F);
    }
    
}
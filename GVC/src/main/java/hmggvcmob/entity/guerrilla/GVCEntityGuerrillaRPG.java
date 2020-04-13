package hmggvcmob.entity.guerrilla;


import handmadeguns.items.guns.HMGItem_Unified_Guns;
import handmadevehicle.SlowPathFinder.WorldForPathfind;
import hmggvcmob.ai.*;
import hmggvcmob.entity.IHasVehicleGacha;
import hmggvcutil.GVCUtils;
import net.minecraft.block.Block;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import static hmggvcmob.GVCMobPlus.*;

public class GVCEntityGuerrillaRPG extends EntityGBase implements IHasVehicleGacha
{
    AIBreakBlock breakBlock;
    public GVCEntityGuerrillaRPG(World par1World)
    {
        super(par1World);
        this.setSize(0.6F, 1.8F);
        if(cfg_blockdestory)this.tasks.addTask(1,new AIBuilder(this,worldForPathfind));
        this.tasks.addTask(2,aiAttackGun = new AIAttackGun(this,40,5,10,15,true,true,new WorldForPathfind(worldObj)));
        spread = 3;
        canuseAlreadyPlacedGun = false;
        canusePlacedGun = false;
    }
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(movespeed = 0.13000000417232513D);
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20.0D);
		this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(cfg_guerrillasrach);
        //this.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(30.0D);
    }
    
    public void addRandomArmor()
    {
        super.addRandomArmor();
        if(rnd.nextInt(10)!=-0) {
            //TODO AAミソを対地で使うと強すぎる
            this.setCurrentItemOrArmor(0, new ItemStack((Item) Guns_RR.get(rnd.nextInt(Guns_RR.size()))));
        }
    }

    /**
     * Drop 0-2 items of this living's type
     */
    protected void dropFewItems(boolean par1, int par2)
    {
        int var3;
        int var4;
        var3 = this.rand.nextInt(3 + par2);
        for (var4 = 0; var4 < var3; ++var4)
        {
            this.dropItem(Items.gunpowder, 1);
        }

        var3 = this.rand.nextInt(3 + par2);
        for (var4 = 0; var4 < var3; ++var4)
        {
            this.dropItem(Items.emerald, 1);
        }

        var3 = this.rand.nextInt(3 + par2);
        for (var4 = 0; var4 < var3; ++var4)
        {
            this.dropItem(GVCUtils.fn_cm, 1);
        }

        var3 = this.rand.nextInt(3 + par2);
        if(this.getHeldItem()!=null){
            this.entityDropItem(this.getHeldItem(), 1);
            if(this.getHeldItem().getItem() instanceof HMGItem_Unified_Guns){
                for (var4 = 0; var4 < var3; ++var4)
                {
                    dropMagazine();
                }
            }
        }
        this.setCurrentItemOrArmor(0,null);
    }



    public void onUpdate()
    {
    	super.onUpdate();
    	if(!worldObj.isRemote) {
            if (this.getAttackTarget() != null) {
                this.setSneaking(true);
            } else {
                this.setSneaking(false);
            }
            if (cfg_cansetIED && getHeldItem() == null && this.ridingEntity == null && this.getAttackTarget() == null && rnd.nextInt(100) == 1) {
//            worldObj.getBlock((int)(this.posX + this.getLookVec().xCoord),(int)(this.posY + this.getLookVec().yCoord),(int)(this.posZ + this.getLookVec().zCoord));
                if (worldObj.isAirBlock((int) (this.posX - this.getLookVec().xCoord), (int) (this.posY - this.getLookVec().yCoord), (int) (this.posZ - this.getLookVec().zCoord)) && !worldObj.isAirBlock((int) (this.posX - this.getLookVec().xCoord), (int) (this.posY - this.getLookVec().yCoord)-1, (int) (this.posZ - this.getLookVec().zCoord))) {
                    worldObj.setBlock((int) (this.posX - this.getLookVec().xCoord), (int) (this.posY - this.getLookVec().yCoord), (int) (this.posZ - this.getLookVec().zCoord), GVCUtils.fn_ied);
                }
            }

            if(this.getAttackTarget() != null && getHeldItem() ==null)this.getLookHelper().setLookPositionWithEntity(this.getAttackTarget(),180,180);
//            if (cfg_blockdestroy) {
//                boolean isdiging = false;
//                Block block;
//                int tx = (int) (this.posX);
//                int ty = (int) (this.posY) + 1;
//                int tz = (int) (this.posZ);
//                if (45 <= this.rotationYawHead && this.rotationYawHead < 135) {
//                    tx--;
//                } else if ((135 <= this.rotationYawHead && this.rotationYawHead <= 180) || (-180 <= this.rotationYawHead && this.rotationYawHead < -135)) {
//                    tz--;
//                } else if (-135 <= this.rotationYawHead && this.rotationYawHead < -45) {
//                    tx++;
//                } else if ((-45 <= this.rotationYawHead && this.rotationYawHead <= 0) || (0 <= this.rotationYawHead && this.rotationYawHead < 45)) {
//                    tz++;
//                }
//                if (this.rotationPitch < -45) {
//                    ty++;
//                }
//                if (this.rotationPitch > 45) {
//                    ty--;
//                }
//                block = worldObj.getBlock(tx, ty, tz);
//                if (block.isAir(worldObj, tx, ty, tz)) {
//                    block = worldObj.getBlock(tx, ty -= 1, tz);
//                }
//                if (block.isAir(worldObj, tx, ty, tz)) {
//                    block = worldObj.getBlock(tx, ty -= 1, tz);
//                }
//                if (!worldObj.isRemote && !block.isAir(worldObj, tx, ty, tz) && getAttackTarget() != null) {
//                    this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.1);
//                    (block = worldObj.getBlock(tx, ty, tz)).onBlockClicked(worldObj, tx, ty, tz, null);
//                    isdiging = true;
////            System.out.println("debug" + digcount + " , " + block.getBlockHardness(this.worldObj,tx,ty,tz)*80);
//                    if (this.digcount >= block.getBlockHardness(this.worldObj, tx, ty, tz) * 80) {
//                        this.worldObj.setBlockToAir(tx, ty, tz);
//                        this.worldObj.playAuxSFX(1012, tx, ty, tz, 0);
//                        this.worldObj.playAuxSFX(2001, tx, ty, tz, Block.getIdFromBlock(block));
//                        digcount = 0;
//                    }
//                } else {
//                    this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.23000000417232513D);
//                }
//                if (isdiging) {
//                    digcount++;
//                } else {
//                    digcount = 0;
//                }
//            }
        }
    }

    
	public boolean isConverting() {
		return false;
	}
	
    protected String getLivingSound()
    {
        return "mob.skeleton.say";
    }

    protected String getHurtSound()
    {
        return "mob.skeleton.hurt";
    }

    protected String getDeathSound()
    {
        return "mob.skeleton.death";
    }

    protected void func_145780_a(int p_145780_1_, int p_145780_2_, int p_145780_3_, Block p_145780_4_)
    {
        this.playSound("mob.skeleton.step", 0.15F, 1.0F);
    }
}

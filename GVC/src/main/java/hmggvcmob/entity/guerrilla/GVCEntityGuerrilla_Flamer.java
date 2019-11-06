package hmggvcmob.entity.guerrilla;


import handmadeguns.items.guns.HMGItem_Unified_Guns;
import handmadevehicle.SlowPathFinder.WorldForPathfind;
import hmggvcutil.GVCUtils;
import hmggvcmob.ai.AIAttackGun;
import net.minecraft.block.Block;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIOpenDoor;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

import java.util.Random;

import static hmggvcmob.GVCMobPlus.Guns_FL;
import static hmggvcmob.GVCMobPlus.cfg_guerrillasrach;

public class GVCEntityGuerrilla_Flamer extends EntityGBase
{
    public GVCEntityGuerrilla_Flamer(World par1World)
    {
        super(par1World);
        this.setSize(0.6F, 1.8F);
        this.tasks.addTask(1,aiAttackGun = new AIAttackGun(this,20,10,80,30,true,true,new WorldForPathfind(worldObj)));
        this.tasks.removeTask(new EntityAIOpenDoor(this, true));
        spread = 5;
        //独自射撃処理
    }
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(movespeed = 0.33000000417232513D);
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20.0D);
        this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(cfg_guerrillasrach);
        //this.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(30.0D);
    }
    
    public void addRandomArmor()
    {
        super.addRandomArmor();
        Random rnd = new Random();

        this.setCurrentItemOrArmor(0, new ItemStack((Item)Guns_FL.get(rnd.nextInt(Guns_FL.size()))));
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
//        for (var4 = 0; var4 < var3; ++var4)
//        {
//            this.dropItem(GVCUtils.fn_magazine, 1);
//        }

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

    protected void dropRareDrop(int par1)
    {
            this.entityDropItem(new ItemStack(Items.skull, 1, 1), 0.0F);
    }

    public void onUpdate()
    {
    	super.onUpdate();
    }
    
	public boolean isConverting() {
		return false;
	}
	
    protected String getLivingSound()
    {
        return "mob.blaze.breathe";
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
    public boolean attackEntityFrom(DamageSource source, float par2)
    {
        if(source.isFireDamage())return false;
        return super.attackEntityFrom(source,par2);

    }
}

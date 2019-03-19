package hmggvcmob.entity.guerrilla;


import handmadeguns.items.guns.HMGItem_Unified_Guns;
import hmggvcutil.GVCUtils;
import hmggvcmob.ai.AIAttackGun;
import net.minecraft.block.Block;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import static hmggvcmob.GVCMobPlus.Guns_SG;
import static hmggvcmob.GVCMobPlus.cfg_guerrillasrach;

public class GVCEntityGuerrillaSG extends EntityGBase
{
    public GVCEntityGuerrillaSG(World par1World)
    {
        super(par1World);
        this.setSize(0.6F, 1.8F);
        this.tasks.addTask(1,aiAttackGun = new AIAttackGun(this,30,3,10,15,10,true));
        spread = 5;
        canuseAlreadyPlacedGun = false;
        canusePlacedGun = false;
    }
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(movespeed = 0.23000000417232513D);
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20.0D);
        this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(cfg_guerrillasrach);
        //this.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(30.0D);
    }
    
    public void addRandomArmor()
    {
        super.addRandomArmor();
        if(Guns_SG.size()>0) {
            this.setCurrentItemOrArmor(0, new ItemStack((Item) Guns_SG.get(rnd.nextInt(Guns_SG.size()))));
        }
    }
    protected Item func_146068_u()
    {
        return GVCUtils.fn_shell;
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
                    this.dropItem(((HMGItem_Unified_Guns) this.getHeldItem().getItem()).magazine, 1);
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

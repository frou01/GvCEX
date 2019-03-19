package hmggvcmob.entity.friend;


import net.minecraft.block.Block;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.world.World;

public class GVCEntitySoldier_HeliSpawner extends EntitySoBase
{
    public GVCEntitySoldier_HeliSpawner(World par1World)
    {
        super(par1World);
        setSize(20,10);
    }
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(movespeed = 0.33000000417232513D);
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20.0D);
        this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(60.0D);
        //this.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(30.0D);
    }
    
    public void addRandomArmor()
    {
        super.addRandomArmor();
    }
    public void onUpdate(){
        if(!worldObj.isRemote) {
            GVCEntitySoldierHeli soldierHeli = new GVCEntitySoldierHeli(worldObj);
            soldierHeli.copyLocationAndAnglesFrom(this);
            worldObj.spawnEntityInWorld(soldierHeli);
        }
        setDead();
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

package hmgww2.entity;


import hmgww2.mod_GVCWW2;
import net.minecraft.block.Block;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EntityUSSR_S extends EntityUSSRBase
{
    public EntityUSSR_S(World par1World)
    {
        super(par1World);
        this.setSize(0.4F, 1.8F);
	    aiAttackGun.burstcool = 0;
	    aiAttackGun.minshootrange = 0;
	    aiAttackGun.bursttime = 30;
	    aiAttackGun.assault = true;
	    aiAttackGun.assaultrange = 40;
	    spread = 4;
    }
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(movespeed = 0.33000000417232513D);
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20.0D);
	}
	
	protected void dropFewItems(boolean p_70628_1_, int p_70628_2_)
	{
		this.dropItem(Items.gunpowder, this.worldObj.rand.nextInt(3)+1);
	}

    public void addRandomArmor()
    {
        super.addRandomArmor();
        int iii = this.worldObj.rand.nextInt(10);
        if(iii == 0){
        	this.setCurrentItemOrArmor(0, new ItemStack(mod_GVCWW2.gun_ppsh41));
	        canuseAlreadyPlacedGun = true;
	        canPlacedGun = true;
	        aiAttackGun.assaultrange = 0;
        }else if(iii == 1){
        	this.setCurrentItemOrArmor(0, new ItemStack(mod_GVCWW2.gun_ppsh41));
	        aiAttackGun.assaultrange = 0;
        }else if(iii == 2){
        	this.setCurrentItemOrArmor(0, new ItemStack(mod_GVCWW2.gun_dp28));
	        aiAttackGun.minshootrange = 20;
	        aiAttackGun.assaultrange = 30;
	        canuseAlreadyPlacedGun = true;
	        canPlacedGun = true;
        }else if(iii == 3){
	        if(this.worldObj.rand.nextInt(2) == 0)
		        this.setCurrentItemOrArmor(0, new ItemStack(mod_GVCWW2.gun_svt40));
	        else
		        this.setCurrentItemOrArmor(0, new ItemStack(mod_GVCWW2.gun_m1891));
	        aiAttackGun.assaultrange = 10;
        }else if(iii == 4){
        	this.setCurrentItemOrArmor(0, new ItemStack(mod_GVCWW2.gun_ptrd));
	        aiAttackGun.minshootrange = 20;
	        aiAttackGun.assaultrange = 40;
        }else if(iii == 5){
        	if(this.worldObj.rand.nextInt(3) == 0)
		        this.setCurrentItemOrArmor(0, new ItemStack(mod_GVCWW2.gun_ptrs));
        	else
		        this.setCurrentItemOrArmor(0, new ItemStack(mod_GVCWW2.gun_ptrd));
	        aiAttackGun.minshootrange = 10;
	        aiAttackGun.assaultrange = 20;
        }else
        {
	        if(this.worldObj.rand.nextInt(2) == 0)
	        	this.setCurrentItemOrArmor(0, new ItemStack(mod_GVCWW2.gun_svt40));
	        else
		        this.setCurrentItemOrArmor(0, new ItemStack(mod_GVCWW2.gun_m1891));
	        aiAttackGun.minshootrange = 0;
	        aiAttackGun.assaultrange = 20;
        }
        this.setCurrentItemOrArmor(4, new ItemStack(mod_GVCWW2.armor_rus));
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

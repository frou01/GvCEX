package hmgww2.entity;


import hmgww2.mod_GVCWW2;
import net.minecraft.block.Block;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EntityUSA_S extends EntityUSABase {
	public EntityUSA_S(World par1World) {
		super(par1World);
		this.setSize(0.5F, 1.8F);
		aiAttackGun.burstcool = 8;
		aiAttackGun.minshootrange = 20;
		aiAttackGun.bursttime = 10;
		aiAttackGun.assault = true;
		aiAttackGun.assaultrange = 40;
		spread = 3;
		interval = 4;
	}

	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(movespeed = 0.33000000417232513D);
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20.0D);
	}

	protected void dropFewItems(boolean p_70628_1_, int p_70628_2_) {
		this.dropItem(mod_GVCWW2.b_magazine, 1);
		this.dropItem(Items.gunpowder, this.worldObj.rand.nextInt(3) + 1);
		if (this.worldObj.rand.nextInt(10) == 0) {
			this.dropItem(mod_GVCWW2.gun_p38, 1);
		}
	}

	public void addRandomArmor() {
		super.addRandomArmor();
		addGun(type);
		this.setCurrentItemOrArmor(4, new ItemStack(mod_GVCWW2.armor_usa));
	}

	public void addGun(int type) {
		int iii = this.worldObj.rand.nextInt(10);
		if (iii == 0) {
			this.setCurrentItemOrArmor(0, new ItemStack(mod_GVCWW2.gun_m1917));
			canuseAlreadyPlacedGun = true;
			canPlacedGun = true;
			aiAttackGun.assaultrange = 40;
		} else if (iii == 1) {
			this.setCurrentItemOrArmor(0, new ItemStack(mod_GVCWW2.gun_bar));
			canuseAlreadyPlacedGun = true;
			aiAttackGun.assaultrange = 40;
			canPlacedGun = true;
		} else if (iii == 2) {
			this.setCurrentItemOrArmor(0, new ItemStack(mod_GVCWW2.gun_bar));
			canuseAlreadyPlacedGun = true;
			aiAttackGun.assaultrange = 40;
			canPlacedGun = true;
		} else if (iii == 3) {
			this.setCurrentItemOrArmor(0, new ItemStack(mod_GVCWW2.gun_m1t));
			aiAttackGun.minshootrange = 10;
			aiAttackGun.assaultrange = 20;
		} else if (iii == 4) {
			this.setCurrentItemOrArmor(0, new ItemStack(mod_GVCWW2.gun_boys));
			aiAttackGun.minshootrange = 30;
			aiAttackGun.assaultrange = 40;
		} else if (iii == 5) {
			this.setCurrentItemOrArmor(0, new ItemStack(mod_GVCWW2.gun_m1b));
			aiAttackGun.minshootrange = 15;
			aiAttackGun.assaultrange = 30;
		} else {
			this.setCurrentItemOrArmor(0, new ItemStack(mod_GVCWW2.gun_m1g));
			aiAttackGun.minshootrange = 20;
			aiAttackGun.assaultrange = 30;
		}
	}

	public void onUpdate() {
		super.onUpdate();

	}

	public boolean isConverting() {
		return false;
	}

	protected String getLivingSound() {
		return "mob.skeleton.say";
	}

	protected String getHurtSound() {
		return "mob.skeleton.hurt";
	}

	protected String getDeathSound() {
		return "mob.skeleton.death";
	}

	protected void func_145780_a(int p_145780_1_, int p_145780_2_, int p_145780_3_, Block p_145780_4_) {
		this.playSound("mob.skeleton.step", 0.15F, 1.0F);
	}
}

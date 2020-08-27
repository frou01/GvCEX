package hmgww2.entity;


import hmgww2.mod_GVCWW2;
import net.minecraft.block.Block;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EntityJPN_S extends EntityJPNBase {
	public EntityJPN_S(World par1World) {
		super(par1World);
		this.setSize(0.4F, 1.65F);
		aiAttackGun.burstcool = 5;
		aiAttackGun.minshootrange = 0;
		aiAttackGun.bursttime = 10;
		aiAttackGun.assault = true;
		aiAttackGun.assaultrange = 30;
		spread = 1;
		interval = 10;
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
		int type = this.worldObj.rand.nextInt(10);
		addGun(type);
		this.setCurrentItemOrArmor(4, new ItemStack(mod_GVCWW2.armor_jpn));
	}

	public void addGun(int type) {
		if (type == 0) {
			this.setCurrentItemOrArmor(0, new ItemStack(mod_GVCWW2.gun_type4Auto));
			aiAttackGun.minshootrange = 30;
			aiAttackGun.assaultrange = 60;
		} else if (type == 1) {
			this.setCurrentItemOrArmor(0, new ItemStack(mod_GVCWW2.gun_type100));
			aiAttackGun.minshootrange = 30;
			aiAttackGun.assaultrange = 30;
		} else if (type == 2) {
			this.setCurrentItemOrArmor(0, new ItemStack(mod_GVCWW2.gun_type38));
			canuseAlreadyPlacedGun = true;
			canPlacedGun = true;
			aiAttackGun.assaultrange = 30;
		} else if (type == 3) {
			this.setCurrentItemOrArmor(0, new ItemStack(mod_GVCWW2.gun_type99lmg));
			canuseAlreadyPlacedGun = true;
			canPlacedGun = true;
			aiAttackGun.assaultrange = 80;
			aiAttackGun.minshootrange = 20;
		} else if (type == 4) {
			this.setCurrentItemOrArmor(0, new ItemStack(mod_GVCWW2.gun_type38));
			aiAttackGun.assaultrange = 0;
		} else if (type == 5) {
			this.setCurrentItemOrArmor(0, new ItemStack(mod_GVCWW2.gun_rota_cannon));
			aiAttackGun.assaultrange = 30;
		} else {
			this.setCurrentItemOrArmor(0, new ItemStack(mod_GVCWW2.gun_type38));
			aiAttackGun.minshootrange = 30;
			aiAttackGun.assaultrange = 60;
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

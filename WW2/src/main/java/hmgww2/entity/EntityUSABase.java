package hmgww2.entity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gvclib.entity.EntityBases;
import hmgww2.mod_GVCWW2;
import hmgww2.blocks.BlockUSAFlagBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemSword;
import net.minecraft.world.World;

public class EntityUSABase extends EntityBases {

	public float roo;
	public int deathTicks;
	public int type = 0;

	public EntityUSABase(World par1World) {
		super(par1World);
		this.flagbase = new BlockUSAFlagBase();
		this.flag = mod_GVCWW2.b_flag_usa;
		this.flag2 = mod_GVCWW2.b_flag2_usa;
		this.flag3 = mod_GVCWW2.b_flag3_usa;
		this.flag4 = mod_GVCWW2.b_flag4_usa;
		this.fri = this;
	}

	public boolean CanAttack(Entity entity) {
		if (!(entity instanceof EntityUSABase) && entity instanceof EntityGolem && !entity.isDead  && ((EntityLivingBase) entity).getHealth() > 0.0F) {
			return true;
		} else if (entity instanceof IMob && !entity.isDead && ((EntityLivingBase) entity).getHealth() > 0.0F) {
			return true;
		} else if (entity instanceof EntityPlayer) {
			EntityPlayer entityplayer = (EntityPlayer) entity;
			if(entityplayer.ridingEntity == this){
				return false;
			}else
			if (entityplayer.getEquipmentInSlot(4) != null && (entityplayer.getEquipmentInSlot(4).getItem() != null)) {
				if (entityplayer.getEquipmentInSlot(4) != null
						&& (entityplayer.getEquipmentInSlot(4).getItem() == mod_GVCWW2.armor_usa)) {
					return false;
				}
				if (entityplayer.getEquipmentInSlot(4) != null
						&& (entityplayer.getEquipmentInSlot(4).getItem() == Items.golden_helmet)) {
					return false;
				} else {
					return true;
				}
			} else if (entityplayer.getCurrentEquippedItem()!= null && 
					(entityplayer.getCurrentEquippedItem().getItem() instanceof ItemBow
					|| entityplayer.getCurrentEquippedItem().getItem() instanceof ItemSword)) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	@SideOnly(Side.CLIENT)
	public int getBrightnessForRender(float par1) {
		return 15728880;
	}

	/**
	 * Gets how bright this entity is.
	 */
	public float getBrightness(float par1) {
		return 1.0F;
	}

	public boolean canAttackClass(Class par1Class) {
		return EntityCreature.class != par1Class;
	}

	/**
	 * Returns true if the newer Entity AI code should be run
	 */
	public boolean isAIEnabled() {
		return true;
	}

	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
	}

	protected void addRandomArmor() {
		super.addRandomArmor();
	}

	public boolean isConverting() {
		return false;
	}

	public static float getMobScale() {
		// TODO 自動生成されたメソッド・スタブ
		return 8;
	}

}

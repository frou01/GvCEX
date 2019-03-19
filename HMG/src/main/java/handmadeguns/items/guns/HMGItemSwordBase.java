package handmadeguns.items.guns;



import com.google.common.collect.Multimap;

//import net.minecraftforge.fml.common.FMLCommonHandler;
//import net.minecraftforge.fml.relauncher.Side;
//import net.minecraftforge.fml.common.SidedProxy;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.ObfuscationReflectionHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;


import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IIconRegister;
//import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.item.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.inventory.Slot;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.event.ForgeEventFactory;



public class HMGItemSwordBase extends Item {

	public static final String[] bowPullIconNameArray = new String[] {"", "", ""};
	//private final Item.ToolMaterial field_150933_b;
	private static final String __OBFID = "CL_00000072";
	public static final String Tag_Cycle		= "Cycle";
	public static final String Tag_CycleCount	= "CycleCount";

	//public static final String[] bowPullIconNameArray = new String[] {"proceedreload", "proceedreload", "0"};
	public String[] iconNames;
	protected IIcon[] icons;
	@SideOnly(Side.CLIENT)
	private IIcon[] iconArray;

	public float f;
	public int j;

	public int isreload;
	public int retime;
	public int reloadtime;

	public static int maxdamege;

	public static boolean canreload;

	public int firetime;
	public int firegrenadetime;
	public int retimegrenade;


	public int powor;
	public float speed;
	public float bure;
	public double recoil;
	public float scopezoom;
	public int cycle;
	public float ex;
	public boolean canex;
	public String sound;
	public String soundre;
	public boolean canobj;
	public int firetype;
	public boolean rendercross;

	public static int righttype;
	public int right;



	public float bureads;
	public double recoilads;

	public static int bulletcount;

	public static String ads;

	public int aaa;
	public static boolean grenadekey;

	public HMGItemSwordBase() {
		super();
		//iconNames = new String[] {"proceedreload", ""};
		//maxdamege = this.getMaxDamage();
	}

	public boolean checkTags(ItemStack pitemstack) {
		if (pitemstack.hasTagCompound()) {
			return true;
		}
		NBTTagCompound ltags = new NBTTagCompound();
		pitemstack.setTagCompound(ltags);
		ltags.setInteger("Reload", 0x0000);
		ltags.setByte("Bolt", (byte)0);
		NBTTagCompound lammo = new NBTTagCompound();
		for (int li = 0; li < getMaxDamage(); li++) {
			lammo.setLong(Integer.toString(li), 0L);
		}
		//ltags.setCompoundTag("Ammo", lammo);
		return false;
	}

	protected boolean cycleBolt(ItemStack pItemstack) {
		checkTags(pItemstack);
		NBTTagCompound lnbt = pItemstack.getTagCompound();
		byte lb = lnbt.getByte("Bolt");
		if (lb <= 0) {
//				if (pReset) resetBolt(pItemstack);
			return true;
		} else {
			lnbt.setByte("Bolt", --lb);
			return false;
		}
	}

	protected void resetBolt(ItemStack pItemstack) {
		checkTags(pItemstack);
		pItemstack.getTagCompound().setByte("Bolt", getCycleCount(pItemstack));
	}

	public byte getCycleCount(ItemStack pItemstack) {
		return (byte)1;
	}

	public static void updateCheckinghSlot(Entity pEntity, ItemStack pItemstack) {
		if (pEntity instanceof EntityPlayerMP) {
			EntityPlayerMP lep = (EntityPlayerMP)pEntity;
			Container lctr = lep.openContainer;
			for (int li = 0; li < lctr.inventorySlots.size(); li++) {
				ItemStack lis = ((Slot)lctr.getSlot(li)).getStack();
				if (lis == pItemstack) {
					lctr.inventoryItemStacks.set(li, pItemstack.copy());
					break;
				}
			}
		}
	}
	/**
	 * Called when the player Left Clicks (attacks) an entity.
	 * Processed before damage is done, if return value is true further processing is canceled
	 * and the entity is not attacked.
	 *
	 * @param stack The Item being used
	 * @param player The player that is attacking
	 * @param entity The entity being attacked
	 * @return True to cancel the rest of the interaction.
	 */
	public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity)
	{
		return false;
	}





	public float func_150893_a(ItemStack p_150893_1_, Block p_150893_2_)
	{

		return 1.0F;


	}

	/**
	 * Returns True is the item is renderer in full 3D when hold.
	 */
	public boolean isFull3D()
	{
		return true;
	}

	public ItemStack onEaten(ItemStack p_77654_1_, World p_77654_2_, EntityPlayer p_77654_3_)
	{
		return p_77654_1_;
	}

	/**
	 * returns the action that specifies what animation to play when the items is being used
	 */
	public EnumAction getItemUseAction(ItemStack par1ItemStack)
	{
		//return EnumAction.bow;
		return EnumAction.block;
	}

	public int getMaxItemUseDuration(ItemStack par1ItemStack)
	{
		return 7200;
	}



	public boolean func_150897_b(Block p_150897_1_)
	{
		return p_150897_1_ == Blocks.web;
	}

}

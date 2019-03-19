package handmadeguns.items.guns;

import java.util.List;

import com.google.common.collect.Multimap;

//import net.minecraftforge.fml.common.FMLCommonHandler;
//import net.minecraftforge.fml.relauncher.Side;
//import net.minecraftforge.fml.common.SidedProxy;


import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;


public class HMGXItemGun_Sword extends HMGItemSwordBase {
	public float attackDamage;
	public float attackDamage2;
	public static String ads;
	public boolean isinRoot;

	public HMGXItemGun_Sword(int p, float s, float b, double r, int rt, float at, float cz, String sd, String sdre, boolean rc, int ri) {
		super();
		this.maxStackSize = 1;
		this.attackDamage = at;
		//this.retime = 30;
		this.powor = p;
		this.speed = s;
		this.bure = b;
		this.recoil = r;
		this.bureads = b/5;
		this.recoilads = r/2;
		this.reloadtime = rt;
		this.scopezoom = cz;
		this.sound = sd;
		this.soundre = sdre;
		this.rendercross = rc;
		this.right = ri;
	}

	public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
	{
		String powor = String.valueOf(this.powor + EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, par1ItemStack));
		String speed = String.valueOf(this.speed);
		String bure = String.valueOf(this.bure);
		String recoil = String.valueOf(this.recoil);
		String retime = String.valueOf(this.reloadtime);
		String nokori = String.valueOf(getMaxDamage() - par1ItemStack.getItemDamage());

		par3List.add(EnumChatFormatting.RED + "Remaining damage " + StatCollector.translateToLocal(nokori));
		//par3List.add(EnumChatFormatting.WHITE + "FireDamege " + "+" + StatCollector.translateToLocal(powor));
		//par3List.add(EnumChatFormatting.WHITE + "BlletSpeed " + "+" + StatCollector.translateToLocal(speed));
		//par3List.add(EnumChatFormatting.WHITE + "BlletSpread "+ "+" + StatCollector.translateToLocal(bure));
		//par3List.add(EnumChatFormatting.WHITE + "Recoil " + "+" + StatCollector.translateToLocal(recoil));
		par3List.add(EnumChatFormatting.YELLOW + "ReloadTime " + "+" + StatCollector.translateToLocal(retime));
		// par3List.add(EnumChatFormatting.YELLOW + "MagazimeType " + StatCollector.translateToLocal("ARMagazine"));
		if(!(this.scopezoom == 1.0f)){
			String scopezoom = String.valueOf(this.scopezoom);
			par3List.add(EnumChatFormatting.WHITE + "ScopeZoom " + "x" + StatCollector.translateToLocal(scopezoom));
		}
		//par3List.add(EnumChatFormatting.WHITE + "FirePowor " + StatCollector.translateToLocal("600"));
	}



	public void onUpdate(ItemStack itemstack, World world, Entity entity, int i, boolean flag)
	{
//		EntityPlayer entityplayer = (EntityPlayer)entity;
//		int s;
//		int li = getMaxDamage() - itemstack.getItemDamage();
//		boolean lflag = cycleBolt(itemstack);
//		boolean var5 = entityplayer.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, itemstack) > 0;
//		Item item = itemstack.getItem();

		if(flag) {
			if (itemstack.getItemDamage() == this.getMaxDamage()) {
				this.attackDamage2 = 0;
			} else {
				this.attackDamage2 = this.attackDamage;
			}


			if (itemstack.getItemDamage() == this.getMaxDamage()) {
				this.isreload = 1;
			}
			if (!world.isRemote) {
				//if(this.isreload == 1 ||GVCItemGunBase.isreload == 1){
				if (this.isreload == 1) {
					this.isreload = 0;
					if (entity != null && entity instanceof EntityPlayer) {
						EntityPlayer entityplayer = (EntityPlayer) entity;
						boolean var5 = entityplayer.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, itemstack) > 0;
						if (var5 || entityplayer.inventory.hasItem(Items.arrow)) {
							if (flag) {
								{
									++this.retime;
									if (this.retime == this.reloadtime) {
										this.retime = 0;

										getReload(itemstack, world, entityplayer);

									}
								}
							}
						}
					}
				}
			}
		}
		super.onUpdate(itemstack, world, entity, i, flag);
	}

	@Override
	public byte getCycleCount(ItemStack pItemstack)
	{
		return 1;
	}

	public void onPlayerStoppedUsing(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer, int par4){

	}

	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
	{
		par3EntityPlayer.setItemInUse(par1ItemStack, this.getMaxItemUseDuration(par1ItemStack));
		return par1ItemStack;
	}

	public void getReload(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
	{
		int li = getMaxDamage() - par1ItemStack.getItemDamage();
		boolean linfinity = EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, par1ItemStack) > 0;

		par1ItemStack.damageItem(li, par3EntityPlayer);
		setDamage(par1ItemStack, -this.getMaxDamage());
		if (!linfinity) {
			par3EntityPlayer.inventory.consumeInventoryItem(Items.arrow);}
		//par2World.playSoundAtEntity(par3EntityPlayer, "random.click", 1.0F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
		par2World.playSoundAtEntity(par3EntityPlayer, this.soundre, 1.0F, 1.0F);
		//return par1ItemStack;
	}

	public float func_150893_a(ItemStack p_150893_1_, Block p_150893_2_)
	{
		if (p_150893_2_ == Blocks.web)
		{
			return 15.0F;
		}
		else
		{
			Material material = p_150893_2_.getMaterial();
			return material != Material.plants && material != Material.vine && material != Material.coral && material != Material.leaves && material != Material.gourd ? 1.0F : 1.5F;
		}
	}

	/**
	 * Current implementations of this method in child classes do not use the entry argument beside ev. They just raise
	 * the damage on the stack.
	 */
	public boolean hitEntity(ItemStack p_77644_1_, EntityLivingBase p_77644_2_, EntityLivingBase p_77644_3_)
	{

		if(p_77644_1_.getItemDamage() == this.getMaxDamage())
		{
			p_77644_1_.damageItem(0, p_77644_3_);
		}else{
			p_77644_1_.damageItem(1, p_77644_3_);
		}


		return true;
	}

	public boolean onBlockDestroyed(ItemStack p_150894_1_, World p_150894_2_, Block p_150894_3_, int p_150894_4_, int p_150894_5_, int p_150894_6_, EntityLivingBase p_150894_7_)
	{
		if ((double)p_150894_3_.getBlockHardness(p_150894_2_, p_150894_4_, p_150894_5_, p_150894_6_) != 0.0D)
		{
			p_150894_1_.damageItem(2, p_150894_7_);
		}

		return true;
	}
	public Multimap getItemAttributeModifiers()
	{
		Multimap multimap = super.getItemAttributeModifiers();
		multimap.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), new AttributeModifier(field_111210_e, "Weapon modifier", (double)this.attackDamage, 0));
		return multimap;
	}
}

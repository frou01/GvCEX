package handmadeguns.items;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Multimap;


import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
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
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraft.inventory.Slot;


	public class HMGItemBattlePack extends Item {
		public static List Guns = new ArrayList();
		public NBTTagCompound nbt;
		public int stack;
		public String[] itemnames;

		//public HMGItemBattlePack(List addit) {
		public HMGItemBattlePack(Item[] addit,String[] itemnames, int sta) {
			//this.Guns = addit;
			//nbt.setByteArray("", addit);
			this.stack = sta;
			this.itemnames = itemnames;
			this.maxStackSize = 64;
		}
		
		public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
	    {
			
			if (!par3EntityPlayer.capabilities.isCreativeMode)
	        {
	            --par1ItemStack.stackSize;
	        }
			int iii = par2World.rand.nextInt(stack);
			String[] itemID = itemnames[iii].split(":");
			if (!par2World.isRemote)
			{
				if(itemID.length == 1) {
					par3EntityPlayer.dropItem(GameRegistry.findItem("HandmadeGuns", itemnames[iii]), 1);
				}
				else {
					par3EntityPlayer.dropItem(GameRegistry.findItem(itemID[0],itemID[1]), 1);
				}
			}
			
			
			/*
			int ii = HandmadeGunsCore.guns.size();
			int iii = par2World.rand.nextInt(ii);
			for(int is = 0; is < ii; ++is){
				Item gun = (Item)HandmadeGunsCore.guns.get(is);
				if(is == iii){
					if (!par2World.isRemote)
         	        {
					par3EntityPlayer.dropItem(gun, 1);
         	        }
				}
				
				
				
			}*/
			/*
			int ii = this.Guns.size();
			int iii = par2World.rand.nextInt(ii);
			for(int is = 0; is < ii; ++is){
				Item gun = (Item)this.Guns.get(is);
				if(is == iii){
					if (!par2World.isRemote)
         	        {
					par3EntityPlayer.dropItem(gun, 1);
         	        }
				}
				
				
				
			}*/
			
			
			//par3EntityPlayer.dropItem(mod_HandmadeGuns.guns, 1);
			
			
			return par1ItemStack;
	    }
		
		
		
		
		
		
		
		
		
		
		
}

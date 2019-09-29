package handmadevehicle.inventory;

import handmadevehicle.entity.parts.logics.BaseLogic;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class InventoryVehicle implements IInventory {
	public ItemStack[] items;
	BaseLogic baseLogic;
	//todo 車両側にインベントリをもたせておく。同期処理については、putstack時点でinventoryにアクセスするようなので考慮する必要は無し。
	public InventoryVehicle(BaseLogic baseLogic)
	{
		this.baseLogic = baseLogic;
		
		//currentItem = inventoryPlayer.getCurrentItem();
		//currentItem = stack;
		
		//InventorySize
		items = new ItemStack[baseLogic.info.weaponSlotNum+baseLogic.info.cargoSlotNum];
	}
	
	@Override
	public int getSizeInventory()
	{
		return items.length;
	}
	
	@Override
	public ItemStack getStackInSlot(int slot)
	{
		return items[slot];
	}
	
	@Override
	public ItemStack decrStackSize(int p_70298_1_, int p_70298_2_)
	{
		if (this.items[p_70298_1_] != null)
		{
			ItemStack itemstack;
			
			if (this.items[p_70298_1_].stackSize <= p_70298_2_)
			{
				itemstack = this.items[p_70298_1_];
				this.items[p_70298_1_] = null;
				this.markDirty();
				return itemstack;
			}
			else
			{
				itemstack = this.items[p_70298_1_].splitStack(p_70298_2_);
				
				if (this.items[p_70298_1_].stackSize == 0)
				{
					this.items[p_70298_1_] = null;
				}
				
				this.markDirty();
				return itemstack;
			}
		}
		return null;
	}
	
	@Override
	public ItemStack getStackInSlotOnClosing(int p_70304_1_)
	{
		if (this.items[p_70304_1_] != null)
		{
			ItemStack itemstack = this.items[p_70304_1_];
			this.items[p_70304_1_] = null;
			return itemstack;
		}
		return null;
	}
	
	@Override
	public void setInventorySlotContents(int p_70299_1_, ItemStack p_70299_2_)
	{
		this.items[p_70299_1_] = p_70299_2_;
		if (p_70299_2_ != null && p_70299_2_.stackSize > this.getInventoryStackLimit())
		{
			p_70299_2_.stackSize = this.getInventoryStackLimit();
		}
		
		this.markDirty();
	}
	
	@Override
	public String getInventoryName()
	{
		return "InventoryItem";
	}
	
	@Override
	public boolean hasCustomInventoryName()
	{
		return false;
	}
	
	@Override
	public int getInventoryStackLimit()
	{
		return 64;
	}
	
	@Override
	public void markDirty() {}
	
	@Override
	public boolean isUseableByPlayer(EntityPlayer p_70300_1_)
	{
		return true;
	}
	
	@Override
	public void openInventory()
	{
	
	}
	
	@Override
	public void closeInventory()
	{
	
	}
	@Override
	public boolean isItemValidForSlot(int p_94041_1_, ItemStack p_94041_2_)
	{
		return true;
	}
}

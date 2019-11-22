package handmadevehicle.inventory;

import handmadevehicle.entity.parts.logics.BaseLogic;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class InventoryVehicle implements IInventory {
	public ItemStack[] items;
	public BaseLogic baseLogic;
	public boolean needSync = true;


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
		if (p_70299_2_ != null && p_70299_2_.stackSize > this.getInventoryStackLimit()) {
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
	public void markDirty() {needSync = true;}
	
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
	public boolean isItemValidForSlot(int slotID, ItemStack checkingStack)
	{
		if(slotID<baseLogic.prefab_vehicle.weaponSlotNum && checkingStack != null){
			String itemName = checkingStack.getUnlocalizedName();
			if(baseLogic.prefab_vehicle.weaponSlot_linkedTurret_StackWhiteList.get(slotID).length == 0)return true;
			for(String whiteList: baseLogic.prefab_vehicle.weaponSlot_linkedTurret_StackWhiteList.get(slotID)) {
				if("item.".concat(whiteList).equals(itemName)){
					return true;
				}
			}
			return false;
		}
		return true;
	}
}

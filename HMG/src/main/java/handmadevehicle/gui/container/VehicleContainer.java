package handmadevehicle.gui.container;

import handmadevehicle.entity.EntityVehicle;
import handmadevehicle.inventory.InventoryVehicle;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class VehicleContainer extends Container {
	public InventoryVehicle inventoryVehicle;
	public IInventory userInventory;
	public int numRows;
	public int numRowsWeapon;
	public int numLinesWeapon;
	public int numRowsCargo;
	public int yoffset_Cargo;
	public int yoffset_playerInventory;
	public VehicleContainer(EntityPlayer player){
		if(player.ridingEntity instanceof EntityVehicle) {
			userInventory = player.inventory;
			inventoryVehicle = ((EntityVehicle) player.ridingEntity).getBaseLogic().inventoryVehicle;

			numRowsWeapon = inventoryVehicle.baseLogic.info.weaponSlotNum / 9;
			int raw;
			int line;
			for (raw = 0; raw < numRowsWeapon; ++raw)
			{
				for (line = 0; line < 9; ++line)
				{
					this.addSlotToContainer(new SlotModded(inventoryVehicle, line + raw * 9,
							8 + line * 18, 18 + raw * 18));
				}
			}
			for (line = 0; line < inventoryVehicle.baseLogic.info.weaponSlotNum%9; ++line)
			{
				this.addSlotToContainer(new SlotModded(inventoryVehicle, line + (numRowsWeapon) * 9, 8 + line * 18, 18 + (numRowsWeapon) * 18));
			}

			yoffset_Cargo = (numRowsWeapon +
					(numLinesWeapon = (inventoryVehicle.baseLogic.info.weaponSlotNum%9) > 0 ? 1:0)) * 18;

			numRowsCargo = inventoryVehicle.baseLogic.info.cargoSlotNum / 9;
			for (raw = 0; raw < numRowsCargo; ++raw)
			{
				for (line = 0; line < 9; ++line)
				{
					this.addSlotToContainer(new SlotModded(inventoryVehicle, inventoryVehicle.baseLogic.info.weaponSlotNum + line + raw * 9, 8 + line * 18, 18 + raw * 18 + yoffset_Cargo));
				}
			}
			for (line = 0; line < inventoryVehicle.baseLogic.info.cargoSlotNum%9; ++line)
			{
				this.addSlotToContainer(new SlotModded(inventoryVehicle, inventoryVehicle.baseLogic.info.weaponSlotNum + line + (numRowsCargo) * 9, 8 + line * 18, 18 + (numRowsCargo) * 18 + yoffset_Cargo));
			}

			numRows = numRowsCargo + numRowsWeapon +
					(inventoryVehicle.baseLogic.info.weaponSlotNum%9 > 0 ? 1:0)+
					(inventoryVehicle.baseLogic.info.cargoSlotNum%9 > 0 ? 1:0);


			yoffset_playerInventory = (numRows - 4) * 18;
			for (raw = 0; raw < 3; ++raw)
			{
				for (line = 0; line < 9; ++line)
				{
					this.addSlotToContainer(new SlotModded(userInventory, line + raw * 9 + 9, 8 + line * 18, 103 + raw * 18 + yoffset_playerInventory));
				}
			}

			for (line = 0; line < 9; ++line)
			{
				this.addSlotToContainer(new SlotModded(userInventory, line, 8 + line * 18, 161 + yoffset_playerInventory));
			}
		}else {
			System.out.println("failed:open vehicle Inventory by " + player);
		}
	}
	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return true;
	}
	public ItemStack transferStackInSlot(EntityPlayer entityPlayer, int slotID)
	{
		ItemStack itemstack = null;
		Slot slot = (Slot)this.inventorySlots.get(slotID);
		
		if (slot != null && slot.getHasStack())
		{
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();
			
			if (slot.inventory == userInventory)
			{
				if (!this.mergeItemStack(itemstack1, 0, inventoryVehicle.getSizeInventory(), false))
				{
					return null;
				}
			}
			else if (!this.mergeItemStack(itemstack1, inventoryVehicle.getSizeInventory(), inventoryVehicle.getSizeInventory() + userInventory.getSizeInventory() - 4, true))
			{
				return null;
			}
			
			if (itemstack1.stackSize == 0)
			{
				slot.putStack((ItemStack)null);
			}
			else
			{
				slot.onSlotChanged();
			}
		}
		
		return itemstack;
	}

	protected boolean mergeItemStack(ItemStack moveingStack, int p_75135_2_, int p_75135_3_, boolean p_75135_4_)
	{
		boolean flag1 = false;
		int k = p_75135_2_;

		if (p_75135_4_)
		{
			k = p_75135_3_ - 1;
		}

		Slot slot;
		ItemStack itemstack1;

		if (moveingStack.isStackable())
		{
			while (moveingStack.stackSize > 0 && (!p_75135_4_ && k < p_75135_3_ || (p_75135_4_ && k >= p_75135_2_)))
			{
				slot = (Slot)this.inventorySlots.get(k);
				itemstack1 = slot.getStack();

				if (itemstack1 != null && itemstack1.getItem() == moveingStack.getItem() && (!moveingStack.getHasSubtypes() || moveingStack.getItemDamage() == itemstack1.getItemDamage()) && ItemStack.areItemStackTagsEqual(moveingStack, itemstack1))
				{
					int l = itemstack1.stackSize + moveingStack.stackSize;

					if (l <= moveingStack.getMaxStackSize())
					{
						moveingStack.stackSize = 0;
						itemstack1.stackSize = l;
						slot.onSlotChanged();
						flag1 = true;
					}
					else if (itemstack1.stackSize < moveingStack.getMaxStackSize())
					{
						moveingStack.stackSize -= moveingStack.getMaxStackSize() - itemstack1.stackSize;
						itemstack1.stackSize = moveingStack.getMaxStackSize();
						slot.onSlotChanged();
						flag1 = true;
					}
				}

				if (p_75135_4_)
				{
					--k;
				}
				else
				{
					++k;
				}
			}
		}

		if (moveingStack.stackSize > 0)
		{
			if (p_75135_4_)
			{
				k = p_75135_3_ - 1;
			}
			else
			{
				k = p_75135_2_;
			}

			while (!p_75135_4_ && k < p_75135_3_ || p_75135_4_ && k >= p_75135_2_)
			{
				slot = (Slot)this.inventorySlots.get(k);
				itemstack1 = slot.getStack();

				if (itemstack1 == null && slot.isItemValid(moveingStack))
				{
					slot.putStack(moveingStack.copy());
					slot.onSlotChanged();
					moveingStack.stackSize = 0;
					flag1 = true;
					break;
				}

				if (p_75135_4_)
				{
					--k;
				}
				else
				{
					++k;
				}
			}
		}

		return flag1;
	}

	public ItemStack slotClick(int p_75144_1_, int p_75144_2_, int p_75144_3_, EntityPlayer p_75144_4_){
		try {
			return super.slotClick(p_75144_1_, p_75144_2_, p_75144_3_, p_75144_4_);
		}catch (Exception e){
			e.printStackTrace();
			return null;
		}
	}
}

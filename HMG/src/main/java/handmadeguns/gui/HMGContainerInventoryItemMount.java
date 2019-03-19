package handmadeguns.gui;
 

import handmadeguns.entity.HMGEntityItemMount;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
 
public class HMGContainerInventoryItemMount extends Container
{
    private HMGEntityItemMount inventory;
 
    private IInventory holderInventory;
    
    public HMGContainerInventoryItemMount(IInventory inventoryPlayer, ItemStack itemstack, HMGEntityItemMount en)
    {
    	//this.holderInventory = ((ItemInventory)itemstack.getItem()).getInventoryFromItemStack(itemstack);
        //inventory = new InventoryItem(inventoryPlayer, itemstack);
    	//inventory = //((GVCItemGunBase)itemstack.getItem()).getInventoryFromItemStack(inventoryPlayer, itemstack);
    	inventory = en;
        inventory.openInventory();
 
        int i = 2 * 18 + 1;
 /*
        for (int j = 0; j < 6; ++j)
        {
            for (int k = 0; k < 9; ++k)
            {
                //this.addSlotToContainer(new Slot(inventory, k + j * 9, 8 + k * 18, 18 + j * 18));
            	this.addSlotToContainer(new Slot(inventory, k + j * 9, 8 + k * 18, 18 + j * 18));
            }
        }*/
        //for (int j = 0; j < 6; ++j)
        {
            //for (int k = 0; k < 9; ++k)
            {
                //this.addSlotToContainer(new Slot(inventory, k + j * 9, 8 + k * 18, 18 + j * 18));
            	this.addSlotToContainer(new Slot(inventory, 1, 8 + 5 * 18, 18 + 1 * 18));
            	this.addSlotToContainer(new Slot(inventory, 2, 8 + 2 * 18, 18 + 1 * 18));
            	this.addSlotToContainer(new Slot(inventory, 3, 8 + 0 * 18, 18 + 4 * 18));
            	//this.addSlotToContainer(new Slot(inventory, 4, 8 + 2 * 18, 18 + 5 * 18));
            	
            	//this.addSlotToContainer(new Slot(inventory, 5, 8 + 7 * 18, 18 + 5 * 18));
            }
        }
 
        for (int j = 0; j < 3; ++j)
        {
            for (int k = 0; k < 9; ++k)
            {
                this.addSlotToContainer(new Slot(inventoryPlayer, k + j * 9 + 9, 8 + k * 18, 103 + j * 18 + i));
            }
        }
 
        for (int j = 0; j < 9; ++j)
        {
            this.addSlotToContainer(new Slot(inventoryPlayer, j, 8 + j * 18, 161 + i));
        }
 
    }
 


	/*
        Containerが開いてられるか
     */
    @Override
    public boolean canInteractWith(EntityPlayer p_75145_1_)
    {
        return true;
    }
 
    @Override
    public ItemStack transferStackInSlot(EntityPlayer p_82846_1_, int p_82846_2_)
    {
        ItemStack itemstack = null;
        Slot slot = (Slot)this.inventorySlots.get(p_82846_2_);
 
        if (slot != null && slot.getHasStack())
        {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();
 
            if (p_82846_2_ < this.inventory.getSizeInventory())
            {
                if (!this.mergeItemStack(itemstack1, this.inventory.getSizeInventory(), this.inventorySlots.size(), true))
                {
                    return null;
                }
            }
            if (!this.mergeItemStack(itemstack1, 0, this.inventory.getSizeInventory(), false))
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
 
    /*
        Containerを閉じるときに呼ばれる
     */
    @Override
    public void onContainerClosed(EntityPlayer p_75134_1_)
    {
        super.onContainerClosed(p_75134_1_);
        this.inventory.closeInventory();
    }
}
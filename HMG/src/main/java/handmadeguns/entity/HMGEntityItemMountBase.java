package handmadeguns.entity;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public abstract class HMGEntityItemMountBase extends EntityCreature implements IInventory
{
	protected ItemStack[] minecartContainerItems = new ItemStack[36];
	protected int rack1;
	protected int rack2;
	protected int rack3;
	protected int rack4;
	
	protected ItemStack item1;
	protected ItemStack item2;
	protected ItemStack item3;
	protected ItemStack item4;
	
    /**
     * When set to true, the minecart will drop all items when setDead() is called. When false (such as when travelling
     * dimensions) it preserves its contents.
     */
    private boolean dropContentsWhenDead = true;
    private static final String __OBFID = "CL_00001674";

    //public  inventory;
    public int rightweapon;
    public int leftweapon;
    
    public HMGEntityItemMountBase(World p_i1716_1_)
    {
        super(p_i1716_1_);
    }
    
    protected boolean canDespawn()
    {
        return false;
    }
    
    /**
     * Returns the stack in slot i
     */
    public ItemStack getStackInSlot(int p_70301_1_)
    {
        return this.minecartContainerItems[p_70301_1_];
    }

    /**
     * Removes from an inventory slot (first arg) up to a specified number (second arg) of items and returns them in a
     * new stack.
     */
    public ItemStack decrStackSize(int p_70298_1_, int p_70298_2_)
    {
        if (this.minecartContainerItems[p_70298_1_] != null)
        {
            ItemStack itemstack;

            if (this.minecartContainerItems[p_70298_1_].stackSize <= p_70298_2_)
            {
                itemstack = this.minecartContainerItems[p_70298_1_];
                this.minecartContainerItems[p_70298_1_] = null;
                return itemstack;
            }
            else
            {
                itemstack = this.minecartContainerItems[p_70298_1_].splitStack(p_70298_2_);

                if (this.minecartContainerItems[p_70298_1_].stackSize == 0)
                {
                    this.minecartContainerItems[p_70298_1_] = null;
                }

                return itemstack;
            }
        }
        else
        {
            return null;
        }
    }

    /**
     * When some containers are closed they call this on each slot, then drop whatever it returns as an EntityItem -
     * like when you close a workbench GUI.
     */
    public ItemStack getStackInSlotOnClosing(int p_70304_1_)
    {
        if (this.minecartContainerItems[p_70304_1_] != null)
        {
            ItemStack itemstack = this.minecartContainerItems[p_70304_1_];
            this.minecartContainerItems[p_70304_1_] = null;
            return itemstack;
        }
        else
        {
            return null;
        }
    }

    /**
     * Sets the given item stack to the specified slot in the inventory (can be crafting or armor sections).
     */
    public void setInventorySlotContents(int p_70299_1_, ItemStack p_70299_2_)
    {
        this.minecartContainerItems[p_70299_1_] = p_70299_2_;

        if (p_70299_2_ != null && p_70299_2_.stackSize > this.getInventoryStackLimit())
        {
            p_70299_2_.stackSize = this.getInventoryStackLimit();
        }
    }

    /**
     * For tile entities, ensures the chunk containing the tile entity is saved to disk later - the game won't think it
     * hasn't changed and skip it.
     */
    public void markDirty() {}

    /**
     * Do not make give this method the name canInteractWith because it clashes with Container
     */
    public boolean isUseableByPlayer(EntityPlayer p_70300_1_)
    {
        return this.isDead ? false : p_70300_1_.getDistanceSqToEntity(this) <= 64.0D;
    }

    public void openInventory() {}

    public void closeInventory() {}

    /**
     * Returns true if automation is allowed to insert the given stack (ignoring stack size) into the given slot.
     */
    public boolean isItemValidForSlot(int p_94041_1_, ItemStack p_94041_2_)
    {
        return true;
    }

    /**
     * Returns the name of the inventory
     */
    public String getInventoryName()
    {
        return //this.getCommandSenderName();
        		"InventoryItem";
    }

    /**
     * Returns the maximum stack size for a inventory slot.
     */
    public int getInventoryStackLimit()
    {
        return 64;
    }

    /**
     * Teleports the entity to another dimension. Params: Dimension number to teleport to
     */
    public void travelToDimension(int p_71027_1_)
    {
        this.dropContentsWhenDead = false;
        super.travelToDimension(p_71027_1_);
    }

    /**
     * Will get destroyed next tick.
     */
    public void setDead()
    {
        if (this.dropContentsWhenDead)
        {
            for (int i = 0; i < this.getSizeInventory(); ++i)
            {
                ItemStack itemstack = this.getStackInSlot(i);

                if (itemstack != null)
                {
                    float f = this.rand.nextFloat() * 0.8F + 0.1F;
                    float f1 = this.rand.nextFloat() * 0.8F + 0.1F;
                    float f2 = this.rand.nextFloat() * 0.8F + 0.1F;

                    while (itemstack.stackSize > 0)
                    {
                        int j = this.rand.nextInt(21) + 10;

                        if (j > itemstack.stackSize)
                        {
                            j = itemstack.stackSize;
                        }

                        itemstack.stackSize -= j;
                        EntityItem entityitem = new EntityItem(this.worldObj, this.posX + (double)f, this.posY + (double)f1, this.posZ + (double)f2, new ItemStack(itemstack.getItem(), j, itemstack.getItemDamage()));

                        if (itemstack.hasTagCompound())
                        {
                            entityitem.getEntityItem().setTagCompound((NBTTagCompound)itemstack.getTagCompound().copy());
                        }

                        float f3 = 0.05F;
                        entityitem.motionX = (double)((float)this.rand.nextGaussian() * f3);
                        entityitem.motionY = (double)((float)this.rand.nextGaussian() * f3 + 0.2F);
                        entityitem.motionZ = (double)((float)this.rand.nextGaussian() * f3);
                        if(!this.worldObj.isRemote) {
                        this.worldObj.spawnEntityInWorld(entityitem);
                        }
                    }
                }
            }
        }

        super.setDead();
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound p_70014_1_)
    {
        super.writeEntityToNBT(p_70014_1_);
        NBTTagList nbttaglist = new NBTTagList();

        for (int i = 0; i < this.minecartContainerItems.length; ++i)
        {
            if (this.minecartContainerItems[i] != null)
            {
                NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                nbttagcompound1.setByte("Slot", (byte)i);
                this.minecartContainerItems[i].writeToNBT(nbttagcompound1);
                nbttaglist.appendTag(nbttagcompound1);
            }
        }

        p_70014_1_.setTag("Items", nbttaglist);
        p_70014_1_.setInteger("Muki", getMuki());
        p_70014_1_.setInteger("rack1", getrack1());
        
        if (this.getDisplayedItem() != null)
        {
            p_70014_1_.setTag("Item", this.getDisplayedItem().writeToNBT(new NBTTagCompound()));
        }
        if (this.getDisplayedItem2() != null)
        {
            p_70014_1_.setTag("Item2", this.getDisplayedItem2().writeToNBT(new NBTTagCompound()));
        }
        if (this.getDisplayedItem3() != null)
        {
            p_70014_1_.setTag("Item3", this.getDisplayedItem3().writeToNBT(new NBTTagCompound()));
        }
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound p_70037_1_)
    {
        super.readEntityFromNBT(p_70037_1_);
        NBTTagList nbttaglist = p_70037_1_.getTagList("Items", 10);
        this.minecartContainerItems = new ItemStack[this.getSizeInventory()];

        for (int i = 0; i < nbttaglist.tagCount(); ++i)
        {
            NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
            int j = nbttagcompound1.getByte("Slot") & 255;

            if (j >= 0 && j < this.minecartContainerItems.length)
            {
                this.minecartContainerItems[j] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
            }
        }
        setMuki(p_70037_1_.getInteger("Muki"));
        setrack1(p_70037_1_.getInteger("rack1"));
        
        {
        	NBTTagCompound nbttagcompound1 = p_70037_1_.getCompoundTag("Item");
            if (nbttagcompound1 != null && !nbttagcompound1.hasNoTags())
            {
                this.setDisplayedItem(ItemStack.loadItemStackFromNBT(nbttagcompound1));
            }
        }
        {
        	NBTTagCompound nbttagcompound1 = p_70037_1_.getCompoundTag("Item2");
            if (nbttagcompound1 != null && !nbttagcompound1.hasNoTags())
            {
                this.setDisplayedItem2(ItemStack.loadItemStackFromNBT(nbttagcompound1));
            }
        }
        {
        	NBTTagCompound nbttagcompound1 = p_70037_1_.getCompoundTag("Item3");
            if (nbttagcompound1 != null && !nbttagcompound1.hasNoTags())
            {
                this.setDisplayedItem3(ItemStack.loadItemStackFromNBT(nbttagcompound1));
            }
        }
    }
    protected void entityInit() {
		super.entityInit();
		this.dataWatcher.addObject(20, new Integer((int)0));
		this.dataWatcher.addObject(21, new Integer((int)0));
		this.getDataWatcher().addObjectByDataType(22, 5);
		this.getDataWatcher().addObjectByDataType(23, 5);
		this.getDataWatcher().addObjectByDataType(24, 5);
    }
    public void setMuki(int integer) {
    	this.dataWatcher.updateObject(20, Integer.valueOf(integer));
	}
    public int getMuki() {
    	return this.dataWatcher.getWatchableObjectInt(20);
	}
    public void setrack1(int integer) {
    	this.dataWatcher.updateObject(21, Integer.valueOf(integer));
	}
    public int getrack1() {
    	return this.dataWatcher.getWatchableObjectInt(21);
	}
    
    public ItemStack getDisplayedItem()
    {
        return this.getDataWatcher().getWatchableObjectItemStack(22);
    }
    public void setDisplayedItem(ItemStack p_82334_1_)
    {
        if (p_82334_1_ != null)
        {
            p_82334_1_ = p_82334_1_.copy();
            p_82334_1_.stackSize = 1;
        }

        this.getDataWatcher().updateObject(22, p_82334_1_);
        this.getDataWatcher().setObjectWatched(22);
    }
    public ItemStack getDisplayedItem2()
    {
        return this.getDataWatcher().getWatchableObjectItemStack(23);
    }
    public void setDisplayedItem2(ItemStack p_82334_1_)
    {
        if (p_82334_1_ != null)
        {
            p_82334_1_ = p_82334_1_.copy();
            p_82334_1_.stackSize = 1;
        }
        this.getDataWatcher().updateObject(23, p_82334_1_);
        this.getDataWatcher().setObjectWatched(23);
    }
    public ItemStack getDisplayedItem3()
    {
        return this.getDataWatcher().getWatchableObjectItemStack(24);
    }
    public void setDisplayedItem3(ItemStack p_82334_1_)
    {
        if (p_82334_1_ != null)
        {
            p_82334_1_ = p_82334_1_.copy();
            p_82334_1_.stackSize = 1;
        }
        this.getDataWatcher().updateObject(24, p_82334_1_);
        this.getDataWatcher().setObjectWatched(24);
    }
    
    protected void applyDrag()
    {
        int i = 15 - Container.calcRedstoneFromInventory(this);
        float f = 0.98F + (float)i * 0.001F;
        this.motionX *= (double)f;
        this.motionY *= 0.0D;
        this.motionZ *= (double)f;
    }
}
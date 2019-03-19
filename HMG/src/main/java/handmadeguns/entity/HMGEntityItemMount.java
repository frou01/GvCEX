package handmadeguns.entity;

import handmadeguns.HandmadeGunsCore;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class HMGEntityItemMount extends HMGEntityItemMountBase 
{
    private static final String __OBFID = "CL_00001650";

    public HMGEntityItemMount(World p_i1692_1_)
    {
        super(p_i1692_1_);
        this.setSize(1F, 1F);
    }

    /**
     * Returns true if the newer Entity AI code should be run
     */
    public boolean isAIEnabled()
    {
        return true;
    }
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(1.0D);
        this.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(100D);
    }

    @Override
    public void applyEntityCollision(Entity p_70108_1_)
    {
    }
    /**
     * Adds to the current velocity of the entity. Args: x, y, z
     */
    public void addVelocity(double p_70024_1_, double p_70024_3_, double p_70024_5_)
    {
        this.motionX += 0;
        this.motionY += 0;
        this.motionZ += 0;
        this.isAirBorne = true;
    }
    
	@Override
	public int getSizeInventory() {
		return 27;
	}

	@Override
	public boolean hasCustomInventoryName() {
		return false;
	}
	
	public void onUpdate()
    {
		super.onUpdate();
        this.motionY = 0;
        this.motionX = 0;
        this.motionZ = 0;
        this.setrack1(0);
        this.openInventory();
        for (int i1 = 0; i1 < this.getSizeInventory(); ++i1) {
			ItemStack itemstacki = this.getStackInSlot(i1);
			if (i1 == 1) {
				if(itemstacki != null){
				this.setDisplayedItem(itemstacki);
				}/*else{
					this.setDisplayedItem(null);
				}*/
			}
			if (i1 == 2) {
				if(itemstacki != null){
					this.setDisplayedItem2(itemstacki);
					}/*else{
						this.setDisplayedItem2(null);
					}*/
			}
			if (i1 == 3) {
				if(itemstacki != null){
					this.setDisplayedItem3(itemstacki);
					}/*else{
						this.setDisplayedItem3(null);
					}*/
			}
        }
    }
	protected void dropFewItems(boolean p_70628_1_, int p_70628_2_)
    {
        this.dropItem(HandmadeGunsCore.hmg_handing, 1);
    }
	/**
     * First layer of player interaction
     */
	public boolean interact(EntityPlayer p_130002_1_)
    {
		if(!p_130002_1_.isSneaking()){
			NBTTagCompound nbt = p_130002_1_.getEntityData();
			{
				nbt.setInteger("rackid", this.getEntityId());
			}
		if (!this.worldObj.isRemote)
        {
		p_130002_1_.openGui(HandmadeGunsCore.INSTANCE, 1, p_130002_1_.worldObj, (int)p_130002_1_.posX, (int)p_130002_1_.posY, (int)p_130002_1_.posZ);
        }
		}else{
			if(this.getMuki() < 3){
				this.setMuki(this.getMuki() + 1);
			}else if(this.getMuki() >= 3){
				this.setMuki(0);
			}
		}
		
		return super.interact(p_130002_1_);
    }
	
	/**
     * Called when the entity is attacked.
     */
    public boolean attackEntityFrom(DamageSource p_70097_1_, float p_70097_2_)
    {
        if (this.isEntityInvulnerable())
        {
            return false;
        }
        else
        {
            return super.attackEntityFrom(p_70097_1_, p_70097_2_);
        }
    }
}
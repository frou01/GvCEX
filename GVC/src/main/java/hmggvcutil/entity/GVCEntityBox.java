package hmggvcutil.entity;

import hmggvcutil.GVCUtils;
import net.minecraft.entity.*;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

import static handmadeguns.HandmadeGunsCore.proxy;

public class GVCEntityBox extends EntityLiving
{
    public boolean hasItems;
    public boolean interact(EntityPlayer p_70085_1_) {
        this.mountEntity(p_70085_1_);
        if(this.ridingEntity == p_70085_1_){
            return false;
        }else {
            return true;
        }
    }
    public GVCEntityBox(World par1World)
    {
        super(par1World);
        //this.tasks.addTask(3, new EntityAIFleeSun(this, 1.0D));
        //this.tasks.addTask(6, new EntityAILookIdle(this));
        this.setSize(1,0.5f);
        hasItems = true;
        
    }

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(1.0D);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0);
    }

    /**
     * Returns true if the newer Entity AI code should be run
     */
    public boolean isAIEnabled()
    {
        return false;
    }

    

    public boolean attackEntityAsMob(Entity par1Entity)
    {
        return false;
    }

    /**
     * Get this Entity's EnumCreatureAttribute
     */

    @Override
    public ItemStack getHeldItem() {
        return null;
    }

    @Override
    public ItemStack getEquipmentInSlot(int p_71124_1_) {
        return null;
    }


    /**
     * Handles updating while being ridden by an entity
     */
    public void updateRidden()
    {
        super.updateRidden();

    }
    @Override
    public double getYOffset() {
        if(ridingEntity instanceof EntityPlayer) {
            if(worldObj.isRemote){
                if(proxy.getEntityPlayerInstance() == ridingEntity) {
                    if (ridingEntity.isSneaking())
                        return (yOffset - 2.0F);
                    else
                        return (yOffset - 1.1F);
                }else {
                    if (ridingEntity.isSneaking())
                        return (yOffset);
                    else
                        return (yOffset+1);
                }
            }
        }
        return (4f);
    }
    @Override
    public void onUpdate(){
        super.onUpdate();
        if (this.ridingEntity instanceof EntityLivingBase)
        {
            EntityLivingBase var1 = (EntityLivingBase)this.ridingEntity;
            this.rotationYaw = var1.rotationYawHead;
            if(var1.isSneaking())
            var1.addPotionEffect(new PotionEffect(Potion.invisibility.id, 100, 1));
        }
    }

    public boolean attackEntityFrom(DamageSource source, float par2)
    {
        if(source.getEntity() != null && !worldObj.isRemote) {
            this.setDead();
            EntityItem entityItem = new EntityItem(worldObj, this.posX, this.posY, this.posZ, new ItemStack(GVCUtils.fn_box));
            worldObj.spawnEntityInWorld(entityItem);
        }
        return true;
    }
    /**
     * Drop 0-2 items of this living's type
     */
    protected void dropFewItems(boolean par1, int par2)
    {

    }

    protected void dropRareDrop(int par1)
    {
        
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        hasItems = par1NBTTagCompound.getBoolean("hasItems");
        
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
    {
        par1NBTTagCompound.setBoolean("hasItems",hasItems);
        //par1NBTTagCompound.setByte("SkeletonType", (byte)this.getSkeletonType());
    }

    /**
     * Sets the held item, or an armor slot. Slot 0 is held item. Slot 1-4 is armor. Params: Item, slot
     */
    public void setCurrentItemOrArmor(int par1, ItemStack par2ItemStack)
    {
    }

    @Override
    public ItemStack[] getLastActiveItems() {
        return new ItemStack[0];
    }

    public static float getMobScale() {
		return 5;
	}
}

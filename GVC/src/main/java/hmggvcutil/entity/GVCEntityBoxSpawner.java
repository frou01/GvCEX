package hmggvcutil.entity;

import handmadeguns.items.guns.HMGItem_Unified_Guns;
import hmggvcutil.GVCUtils;
import hmggvcmob.entity.guerrilla.GVCEntityGuerrilla;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.Random;

import static handmadeguns.HMGAddGunsNew.Guns;
import static handmadeguns.HandmadeGunsCore.proxy;
import static hmggvcmob.GVCMobPlus.Guns_AR;

public class GVCEntityBoxSpawner extends EntityMob
{
	
    private static final String __OBFID = "CL_00001697";
    public boolean hasItems;
    public boolean interact(EntityPlayer p_70085_1_) {
        if(!worldObj.isRemote) {
            kill();
            return false;
        }
        return false;
    }
    public GVCEntityBoxSpawner(World par1World)
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
    public EnumCreatureAttribute getCreatureAttribute()
    {
        return EnumCreatureAttribute.UNDEAD;
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
    /**
     * Drop 0-2 items of this living's type
     */
    protected void dropFewItems(boolean par1, int par2)
    {
        int var3;
        int var4;


        var3 = this.rand.nextInt(3 + par2);
        if(!worldObj.isRemote && this.rand.nextInt(8) == 0){
            int var12 = MathHelper.floor_double((double)(this.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
            GVCEntityGuerrilla entityskeleton = new GVCEntityGuerrilla(worldObj);
            entityskeleton.setLocationAndAngles(this.posX, this.posY, this.posZ, var12, 0.0F);
            Random rnd = new Random();
            entityskeleton.setCurrentItemOrArmor(0, new ItemStack((Item)Guns_AR.get(rnd.nextInt(Guns_AR.size()))));
            worldObj.spawnEntityInWorld(entityskeleton);
        }else {
            this.dropItem(GVCUtils.fn_box, 1);
            if (hasItems) {
                Item gun = (Item) Guns.get(new Random().nextInt(Guns.size()));
                if (gun instanceof HMGItem_Unified_Guns && ((HMGItem_Unified_Guns) gun).magazine != null) {
                    for (var4 = 0; var4 < var3; ++var4) {
                        this.dropItem(((HMGItem_Unified_Guns) gun).magazine, 3);
                    }
                }
                this.dropItem(gun, 1);
            }
        }
    }

    protected void dropRareDrop(int par1)
    {
        
            this.entityDropItem(new ItemStack(Items.skull, 1, 1), 0.0F);
        
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
        super.setCurrentItemOrArmor(par1, par2ItemStack);

        
    }

	public static float getMobScale() {
		return 5;
	}
}

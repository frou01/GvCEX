package hmggvcmob.entity;

import handmadeguns.HMGAddGunsNew;
import handmadeguns.items.guns.HMGItem_Unified_Guns;
import handmadeguns.items.guns.HMGXItemGun_Sword;
import hmggvcutil.entity.GVCEntityBox;
import hmggvcmob.GVCMobPlus;
import hmggvcmob.tile.TileEntityFlag;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.Random;

import static handmadeguns.HMGAddAttachment.Magazines;
import static handmadeguns.HandmadeGunsCore.proxy;
import static hmggvcutil.GVCUtils.fn_cm;
import static hmggvcutil.GVCUtils.fn_health;
import static hmggvcmob.GVCMobPlus.fn_pmcegg;
import static hmggvcmob.GVCMobPlus.gvcmx_reqsupport_arty;

public class EntitySupplyBox extends GVCEntityBox implements IGVCmob{
    public boolean hasItems;
    public TileEntity spawnedtile = null;
    public boolean interact(EntityPlayer p_70085_1_) {
        return false;
    }
    public EntitySupplyBox(World par1World)
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
        return (0f);
    }
    @Override
    public void onUpdate(){
        super.onUpdate();
        if(onGround){
            if(!worldObj.isRemote)dropFewItems(false,0);
            setDead();
        }
    }

    public boolean attackEntityFrom(DamageSource source, float par2)
    {
        if(source.getEntity() != null && !worldObj.isRemote) {
            this.setDead();
        }
        return true;
    }
    /**
     * Drop 0-2 items of this living's type
     */
    protected void dropFewItems(boolean par1, int par2)
    {
        ItemStack itemStack = null;
        switch (rand.nextInt(20)){
            case 0:
            case 1:
                itemStack = new ItemStack(fn_cm,16);
                break;
            case 2:
            case 3:
            case 4:
            case 5:
                itemStack = new ItemStack(Magazines.get(rand.nextInt(Magazines.size())),8);
                break;
            case 6:
            case 7:
            case 8:
                Item gun = (Item) HMGAddGunsNew.Guns.get(new Random().nextInt(HMGAddGunsNew.Guns.size()));
                while ((gun instanceof HMGItem_Unified_Guns && !((HMGItem_Unified_Guns) gun).gunInfo.isinRoot) || (gun instanceof HMGXItemGun_Sword && !((HMGXItemGun_Sword) gun).isinRoot)){
                    gun = (Item) HMGAddGunsNew.Guns.get(new Random().nextInt(HMGAddGunsNew.Guns.size()));
                }
                itemStack = new ItemStack(gun,1);
                break;
            case 9:
            case 10:
                itemStack = new ItemStack(Blocks.planks,64);
                itemStack.setItemDamage(0);
                break;
            case 11:
                for(int num = 0;num < 2;num++) {
                    itemStack = new ItemStack(Items.enchanted_book, 1);
                    Enchantment enchantment = Enchantment.enchantmentsBookList[this.rand.nextInt(Enchantment.enchantmentsBookList.length)];
                    int i1 = MathHelper.getRandomIntegerInRange(this.rand, enchantment.getMinLevel(), enchantment.getMaxLevel());
                    itemStack = Items.enchanted_book.getEnchantedItemStack(new EnchantmentData(enchantment, i1));
                    EntityItem entityItem = new EntityItem(worldObj, this.posX, this.posY, this.posZ, itemStack);
                    worldObj.spawnEntityInWorld(entityItem);
                }
                return;
            case 12:
                itemStack = new ItemStack(Blocks.stone,64);
                break;
            case 13:
                gun = (Item) GVCMobPlus.Guns_RR.get(new Random().nextInt(GVCMobPlus.Guns_RR.size()));
                while (!((HMGItem_Unified_Guns)gun).gunInfo.isinRoot){
                    gun = (Item) GVCMobPlus.Guns_RR.get(new Random().nextInt(GVCMobPlus.Guns_RR.size()));
                }
                itemStack = new ItemStack(gun,1);
                break;
            case 14:
            case 20:
                itemStack = new ItemStack(fn_health,16);
                break;
            case 15:
            case 19:
                itemStack = new ItemStack(gvcmx_reqsupport_arty,16);
                break;
            case 16:
            case 17:
            case 18:
                itemStack = new ItemStack(fn_pmcegg,16);
                break;
        }
        if(itemStack != null) {
            EntityItem entityItem = new EntityItem(worldObj, this.posX, this.posY, this.posZ, itemStack);
            worldObj.spawnEntityInWorld(entityItem);
        }

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
    public void setDead(){
        super.setDead();
        if(spawnedtile != null && spawnedtile instanceof TileEntityFlag)((TileEntityFlag) spawnedtile).spawnedEntities.remove(this);
    }

    @Override
    public float getviewWide() {
        return 0;
    }

    @Override
    public boolean canSeeTarget(Entity target) {
        return false;
    }

    @Override
    public boolean canhearsound(Entity target) {
        return false;
    }

    @Override
    public void setspawnedtile(TileEntity flag) {
        spawnedtile = flag;
    }
}

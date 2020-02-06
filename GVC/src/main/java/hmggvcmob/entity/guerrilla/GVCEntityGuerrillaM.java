package hmggvcmob.entity.guerrilla;

import hmggvcutil.GVCUtils;

import java.util.Calendar;

import hmggvcmob.GVCMobPlus;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIFleeSun;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIRestrictSun;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.AchievementList;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import static hmggvcmob.GVCMobPlus.cfg_mob_setCamp;
import static hmggvcmob.GVCMobPlus.fn_Guerrillaflag;

public class GVCEntityGuerrillaM extends EntityMob
{
    private EntityAIAttackOnCollide aiAttackOnCollide = new EntityAIAttackOnCollide(this, EntityPlayer.class, 1.2D, false);
    private static final String __OBFID = "CL_00001697";

    public GVCEntityGuerrillaM(World par1World)
    {
        super(par1World);
        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAIRestrictSun(this));
        this.tasks.addTask(3, new EntityAIFleeSun(this, 1.0D));
        this.tasks.addTask(5, new EntityAIWander(this, 1.0D));
        this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(6, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityVillager.class, 0, false));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityGolem.class, 0, false));
    }

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.35D);
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20.0D);
        this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(GVCMobPlus.cfg_guerrillasrach);
        //this.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(30.0D);
    }
    /*
    public void onUpdate()
    {
    	super.onUpdate();
    	if (!this.worldObj.isRemote)
        {
        	if(GVCMobPlus.cfg_canspawnhell == false){
        		if(this.worldObj.getBiomeGenForCoords(chunkCoordX, chunkCoordZ) == BiomeGenBase.hell){
        			this.setDead();
        		}
        	}
        	if(GVCMobPlus.cfg_canspawnsky == false){
        		if(this.worldObj.getBiomeGenForCoords(chunkCoordX, chunkCoordZ) == BiomeGenBase.sky){
        			this.setDead();
        		}
        	}
        }
    }
    */
    public void onLivingUpdate()
    {
        if (!this.worldObj.isRemote)
        {
            if (cfg_mob_setCamp && this.worldObj.canBlockSeeTheSky(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ)) && this.worldObj.rand.nextInt(1200) == 0)
            {
                this.createEnderPortal(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ));
                this.setDead();
            }
        }
        super.onLivingUpdate();
    }

    /**
     * Creates the ender portal leading back to the normal world after defeating the enderdragon.
     */
    private void createEnderPortal(int par1, int par2, int par3)
    {
        //1
        for(int i=0;i<7;i++){
            for(int l=0;l<4;l++) {
                for (int k = 0; k < 7; k++) {
                    if(this.worldObj.getBlock(par1 + i - 3, par2 + l, par3 + k - 3) != Blocks.obsidian)
                        this.worldObj.setBlock(par1 + i - 3, par2 + l, par3 + k - 3, Blocks.air);
                    else return;
                }
            }
        }
        this.worldObj.setBlock(par1+3, par2+0, par3-3, Blocks.fence);
        this.worldObj.setBlock(par1+3, par2+0, par3+3, Blocks.fence);

        this.worldObj.setBlock(par1-3, par2+0, par3-3, Blocks.fence);
        this.worldObj.setBlock(par1-3, par2+0, par3+3, Blocks.fence);

        //2
        this.worldObj.setBlock(par1+3, par2+1, par3-3, Blocks.fence);
        this.worldObj.setBlock(par1+3, par2+1, par3+3, Blocks.fence);

        this.worldObj.setBlock(par1-3, par2+1, par3-3, Blocks.fence);
        this.worldObj.setBlock(par1-3, par2+1, par3+3, Blocks.fence);


        //3
        this.worldObj.setBlock(par1+3, par2+2, par3-3, Blocks.fence);
        this.worldObj.setBlock(par1+3, par2+2, par3-2, Blocks.fence);
        this.worldObj.setBlock(par1+3, par2+2, par3-1, Blocks.fence);
        this.worldObj.setBlock(par1+3, par2+2, par3, Blocks.fence);
        this.worldObj.setBlock(par1+3, par2+2, par3+1, Blocks.fence);
        this.worldObj.setBlock(par1+3, par2+2, par3+2, Blocks.fence);
        this.worldObj.setBlock(par1+3, par2+2, par3+3, Blocks.fence);

        this.worldObj.setBlock(par1+2, par2+2, par3-3, Blocks.fence);
        this.worldObj.setBlock(par1+2, par2+2, par3-2, Blocks.wool,13,2);
        this.worldObj.setBlock(par1+2, par2+2, par3-1, Blocks.wool,13,2);
        this.worldObj.setBlock(par1+2, par2+2, par3, Blocks.wool,13,2);
        this.worldObj.setBlock(par1+2, par2+2, par3+1, Blocks.wool,13,2);
        this.worldObj.setBlock(par1+2, par2+2, par3+2, Blocks.wool,13,2);
        this.worldObj.setBlock(par1+2, par2+2, par3+3, Blocks.fence);

        this.worldObj.setBlock(par1+1, par2+2, par3-3, Blocks.fence);
        this.worldObj.setBlock(par1+1, par2+2, par3-2, Blocks.wool,13,2);
        this.worldObj.setBlock(par1+1, par2+2, par3-1, Blocks.wool,13,2);
        this.worldObj.setBlock(par1+1, par2+2, par3, Blocks.wool,13,2);
        this.worldObj.setBlock(par1+1, par2+2, par3+1, Blocks.wool,13,2);
        this.worldObj.setBlock(par1+1, par2+2, par3+2, Blocks.wool,13,2);
        this.worldObj.setBlock(par1+1, par2+2, par3+3, Blocks.fence);

        this.worldObj.setBlock(par1, par2+2, par3-3, Blocks.fence);
        this.worldObj.setBlock(par1, par2+2, par3-2, Blocks.wool,13,2);
        this.worldObj.setBlock(par1, par2+2, par3-1, Blocks.wool,13,2);
        this.worldObj.setBlock(par1, par2+2, par3, Blocks.wool,13,2);
        this.worldObj.setBlock(par1, par2+2, par3+1, Blocks.wool,13,2);
        this.worldObj.setBlock(par1, par2+2, par3+2, Blocks.wool,13,2);
        this.worldObj.setBlock(par1, par2+2, par3+3, Blocks.fence);

        this.worldObj.setBlock(par1-1, par2+2, par3-3, Blocks.fence);
        this.worldObj.setBlock(par1-1, par2+2, par3-2, Blocks.wool,13,2);
        this.worldObj.setBlock(par1-1, par2+2, par3-1, Blocks.wool,13,2);
        this.worldObj.setBlock(par1-1, par2+2, par3, Blocks.wool,13,2);
        this.worldObj.setBlock(par1-1, par2+2, par3+1, Blocks.wool,13,2);
        this.worldObj.setBlock(par1-1, par2+2, par3+2, Blocks.wool,13,2);
        this.worldObj.setBlock(par1-1, par2+2, par3+3, Blocks.fence);

        this.worldObj.setBlock(par1-2, par2+2, par3-3, Blocks.fence);
        this.worldObj.setBlock(par1-2, par2+2, par3-2, Blocks.wool,13,2);
        this.worldObj.setBlock(par1-2, par2+2, par3-1, Blocks.wool,13,2);
        this.worldObj.setBlock(par1-2, par2+2, par3, Blocks.wool,13,2);
        this.worldObj.setBlock(par1-2, par2+2, par3+1, Blocks.wool,13,2);
        this.worldObj.setBlock(par1-2, par2+2, par3+2, Blocks.wool,13,2);
        this.worldObj.setBlock(par1-2, par2+2, par3+3, Blocks.fence);

        this.worldObj.setBlock(par1-3, par2+2, par3-3, Blocks.fence);
        this.worldObj.setBlock(par1-3, par2+2, par3-2, Blocks.fence);
        this.worldObj.setBlock(par1-3, par2+2, par3-1, Blocks.fence);
        this.worldObj.setBlock(par1-3, par2+2, par3, Blocks.fence);
        this.worldObj.setBlock(par1-3, par2+2, par3+1, Blocks.fence);
        this.worldObj.setBlock(par1-3, par2+2, par3+2, Blocks.fence);
        this.worldObj.setBlock(par1-3, par2+2, par3+3, Blocks.fence);

        //飾り
        this.worldObj.setBlock(par1-1, par2+0, par3-2, Blocks.furnace);
        this.worldObj.setBlock(par1-2, par2+0, par3-2, Blocks.furnace);
        this.worldObj.setBlock(par1+1, par2+0, par3-2, Blocks.chest);
        this.worldObj.setBlock(par1+2, par2+0, par3-2, Blocks.chest);



        this.worldObj.setBlock(par1-1, par2+0, par3+2, Blocks.crafting_table);
        this.worldObj.setBlock(par1-2, par2+0, par3+2, Blocks.cauldron);

        this.worldObj.setBlock(par1, par2-1, par3, fn_Guerrillaflag, 2, 2);

    }


    protected void entityInit()
    {
        super.entityInit();
    }

    /**
     * Returns true if the newer Entity AI code should be run
     */
    public boolean isAIEnabled()
    {
        return true;
    }

    /**
     * Returns the sound this mob makes while it's alive.
     */
    protected String getLivingSound()
    {
        return "mob.skeleton.say";
    }

    /**
     * Returns the sound this mob makes when it is hurt.
     */
    protected String getHurtSound()
    {
        return "mob.skeleton.hurt";
    }

    /**
     * Returns the sound this mob makes on death.
     */
    protected String getDeathSound()
    {
        return "mob.skeleton.death";
    }

    protected void func_145780_a(int p_145780_1_, int p_145780_2_, int p_145780_3_, Block p_145780_4_)
    {
        this.playSound("mob.skeleton.step", 0.15F, 1.0F);
    }

    public boolean attackEntityAsMob(Entity par1Entity)
    {

        {
            return false;
        }
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

        if (this.ridingEntity instanceof EntityCreature)
        {
            EntityCreature var1 = (EntityCreature)this.ridingEntity;
            this.renderYawOffset = var1.renderYawOffset;
        }
    }

    /**
     * Called when the mob's health reaches 0.
     */
    public void onDeath(DamageSource par1DamageSource)
    {
        super.onDeath(par1DamageSource);

        if (par1DamageSource.getSourceOfDamage() instanceof EntityArrow && par1DamageSource.getEntity() instanceof EntityPlayer)
        {
            EntityPlayer var2 = (EntityPlayer)par1DamageSource.getEntity();
            double var3 = var2.posX - this.posX;
            double var5 = var2.posZ - this.posZ;

            if (var3 * var3 + var5 * var5 >= 2500.0D)
            {
                var2.triggerAchievement(AchievementList.snipeSkeleton);
            }
        }
    }

    protected Item func_146068_u()
    {
        return GVCUtils.fn_magazine;
    }

    /**
     * Drop 0-2 items of this living's type
     */
    protected void dropFewItems(boolean par1, int par2)
    {
        int var3;
        int var4;

        var3 = this.rand.nextInt(3 + par2);

        for (var4 = 0; var4 < var3; ++var4)
        {
            this.dropItem(Items.gunpowder, 1);
        }

        for (var4 = 0; var4 < var3; ++var4)
        {
            this.dropItem(Items.emerald, 1);
        }

        for (var4 = 0; var4 < var3; ++var4)
        {
            this.dropItem(GVCUtils.fn_magazine, 1);
        }
    }

    protected void dropRareDrop(int par1)
    {

        this.entityDropItem(new ItemStack(Items.skull, 1, 1), 0.0F);

    }

    /**
     * Makes entity wear random armor based on difficulty
     */
    public void addRandomArmor()
    {
        super.addRandomArmor();
        this.setCurrentItemOrArmor(0, new ItemStack(GVCUtils.fn_ak74));
    }

    public IEntityLivingData onSpawnWithEgg(IEntityLivingData par1EntityLivingData)
    {
        par1EntityLivingData = super.onSpawnWithEgg(par1EntityLivingData);

        /*GVCEntityparas entityskeleton1 = new GVCEntityparas(this.worldObj);
        entityskeleton1.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0F);
        entityskeleton1.onSpawnWithEgg((IEntityLivingData)null);
        this.worldObj.spawnEntityInWorld(entityskeleton1);
        this.mountEntity(entityskeleton1);*/



        this.setCanPickUpLoot(this.rand.nextFloat() < 0.55F * this.worldObj.func_147462_b(this.posX, this.posY, this.posZ));

        if (this.getEquipmentInSlot(4) == null)
        {
            Calendar var2 = this.worldObj.getCurrentDate();

            if (var2.get(2) + 1 == 10 && var2.get(5) == 31 && this.rand.nextFloat() < 0.25F)
            {
                this.setCurrentItemOrArmor(4, new ItemStack(this.rand.nextFloat() < 0.1F ? Blocks.lit_pumpkin : Blocks.pumpkin));
                this.equipmentDropChances[4] = 0.0F;
            }
        }

        return par1EntityLivingData;
    }





    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.readEntityFromNBT(par1NBTTagCompound);

    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.writeEntityToNBT(par1NBTTagCompound);
        //par1NBTTagCompound.setByte("SkeletonType", (byte)this.getSkeletonType());
    }

    /**
     * Sets the held item, or an armor slot. Slot 0 is held item. Slot 1-4 is armor. Params: Item, slot
     */
    public void setCurrentItemOrArmor(int par1, ItemStack par2ItemStack)
    {
        super.setCurrentItemOrArmor(par1, par2ItemStack);

        if (!this.worldObj.isRemote && par1 == 0)
        {
        }
    }

    /**
     * Returns the Y Offset of this entity.
     */
    public double getYOffset()
    {
        return super.getYOffset() - 0.5D;
    }
}

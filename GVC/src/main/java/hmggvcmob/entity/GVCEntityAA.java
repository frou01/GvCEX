package hmggvcmob.entity;

import hmggvcutil.GVCUtils;

import java.util.Calendar;
import java.util.List;

import hmggvcmob.entity.guerrilla.GVCEntityGuerrillaP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIArrowAttack;
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
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class GVCEntityAA extends EntityMob implements IRangedAttackMob
{
    private EntityAIArrowAttack aiArrowAttack = new EntityAIArrowAttack(this, 1.0D, 5, 5, 100.0F);
    private EntityAIAttackOnCollide aiAttackOnCollide = new EntityAIAttackOnCollide(this, EntityPlayer.class, 1.2D, false);
    private static final String __OBFID = "CL_00001697";
    public float field_70886_e;
    public float destPos;
    public float field_70884_g;
    public float field_70888_h;
    public float field_70889_i = 1.0F;
    /** X offset to look at */
    private double lookX;
    /** Z offset to look at */
    private double lookZ;

    public float kakudo;

    int ii = 0;

    public GVCEntityAA(World par1World)
    {
        super(par1World);
        this.setSize(4.0F, 4.0F);
        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAIRestrictSun(this));
        this.tasks.addTask(3, new EntityAIFleeSun(this, 1.0D));
        this.tasks.addTask(5, new EntityAIWander(this, 1.0D));
        this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 16.0F));
        this.tasks.addTask(6, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityVillager.class, 0, false));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityGolem.class, 0, false));

        if (par1World != null && !par1World.isRemote)
        {
            this.setCombatTask();
        }
    }

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.15D);
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(90.0D);
        this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(100D);
        this.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(30.0D);
    }

    public void attackEntityWithRangedAttack(EntityLivingBase par1EntityLivingBase, float par2)
    {
//        GVCEntityBulletGe var3 = new GVCEntityBulletGe(this.worldObj, this);
//        double var4 = par1EntityLivingBase.posX - this.posX;
//        double var6 = par1EntityLivingBase.posY + (double)par1EntityLivingBase.getEyeHeight() - 1.100000023841858D - var3.posY;
//        double var8 = par1EntityLivingBase.posZ - this.posZ;
//        float var10 = MathHelper.sqrt_double(var4 * var4 + var8 * var8) * 0.2F;
//        var3.setThrowableHeading(var4, var6 + (double)var10, var8, 1.6F, 12.0F);
//        this.playSound("gvcguns:gvcguns.fire", 4.0F, 1.0F);
//        this.worldObj.spawnEntityInWorld(var3);
//        if(GVCMobPlus.cfg_canEjectCartridge){
//            GVCEntityBulletCartridge var30 = new GVCEntityBulletCartridge(this.worldObj, this, 1);
//            var30.setThrowableHeading(var4, var6 + (double)var10, var8, -0.1F, 12.0F);
//            //this.playSound("gvcguns:gvcguns.grenade", 1.0F, 1.5F);
//            this.worldObj.spawnEntityInWorld(var30);
//        }
//        if (this.worldObj.rand.nextInt(30) == 0)
//        {
//            GVCEntityGrenade var30 = new GVCEntityGrenade(this.worldObj, this);
//            var30.setThrowableHeading(var4, var6 + (double)var10, var8, 1.0F, 12.0F);
//            this.playSound("gvcguns:gvcguns.grenade", 1.0F, 1.5F);
//            this.worldObj.spawnEntityInWorld(var30);
//        }
//
//        //this.worldObj.spawnEntityInWorld(var3);
    }

    protected Entity findPlayerToAttack()
    {
        double d0 = 100.0D;
        return this.worldObj.getClosestVulnerablePlayerToEntity(this, d0);
    }


    protected boolean canDespawn()
    {
        return false;
    }


    public boolean attackEntityFrom(DamageSource par1DamageSource, float par2)
    {
        return super.attackEntityFrom(par1DamageSource, par2);
    }


    public void onLivingUpdate()
    {
        super.onLivingUpdate();
        this.field_70888_h = this.field_70886_e;
        this.field_70884_g = this.destPos;
        this.destPos = (float)((double)this.destPos + (double)(this.onGround ? -1 : 4) * 0.3D);

        if (this.destPos < 0.0F)
        {
            this.destPos = 0.0F;
        }

        if (this.destPos > 1.0F)
        {
            this.destPos = 1.0F;
        }

        if (!this.onGround && this.field_70889_i < 1.0F)
        {
            this.field_70889_i = 1.0F;
        }

        this.field_70889_i = (float)((double)this.field_70889_i * 0.9D);

        if (!this.onGround && this.motionY < 0.0D)
        {
            this.motionY *= 0.3D;
        }

        this.field_70886_e += this.field_70889_i * 2.0F;


    }

    public int ss = 0;
    public int sss;
    public void onUpdate()
    {
        super.onUpdate();
        if(this.worldObj.rand.nextInt(2) == 0){
            this.worldObj.playSoundAtEntity(this, "gvcmob:gvcmob.heli", 4.0F, 1.0F);
        }

        int i = this.worldObj.rand.nextInt(30)+5;
        int genY = this.worldObj.getHeightValue((int)this.posX, (int)this.posZ)+12+i;

        if (this.posY < genY)
        {
            this.motionY += 0.2D;
        }

        ++sss;
        if(sss >= 0 && sss < 600){
            this.motionX += 0.1D;
            this.kakudo = -90F;
        }else if(sss >= 600 && sss < 1200){
            this.motionZ += 0.1D;
            this.kakudo = -180F;
        }else if(sss >= 1200 && sss < 1800){
            this.motionX -= 0.1D;
            this.kakudo = 90F;
        }else if(sss >= 1800 && sss < 2400){
            this.motionZ -= 0.1D;
            this.kakudo = 0F;
        }else{
            sss = 0;
            this.kakudo = 0F;
        }


        double d0 = (Math.PI * 2D) * this.getRNG().nextDouble();
        this.lookX = Math.cos(d0);
        this.lookZ = Math.sin(d0);
        this.getLookHelper().setLookPosition(this.posX + this.lookX, this.posY + (double)this.getEyeHeight(), this.posZ + this.lookZ, 10.0F, (float)this.getVerticalFaceSpeed());





        if(ii > 8){
            EntityMob entity = null;
            List llist = this.worldObj.getEntitiesWithinAABBExcludingEntity(entity, this.boundingBox.addCoord(this.motionX, this.motionY, this.motionZ).expand(100.0D, 100.0D, 100.0D));
            //double d0 = 8.0D;
            //EntityLivingBase entitylivingbase = this;
            if(llist!=null){
                for (int lj = 0; lj < llist.size(); lj++)
                {
                    Entity entity1 = (Entity)llist.get(lj);
                    //Entity entity1 = (Entity)llist.get(0);
                    if (entity1.canBeCollidedWith())
                    {
                        if (entity1 instanceof EntityGolem  || entity1 instanceof EntityPlayer  && entity1 != null)
                        {
//                            if(entity1.posY < this.posY){
//                                GVCEntityBulletGe var3 = new GVCEntityBulletGe(this.worldObj, this);
//                                double var4 = entity1.posX - this.posX;
//                                double var6 = entity1.posY + (double)entity1.getEyeHeight() - 1.100000023841858D - var3.posY;
//                                double var8 = entity1.posZ - this.posZ;
//                                float var10 = MathHelper.sqrt_double(var4 * var4 + var8 * var8) * 0.2F;
//                                var3.setThrowableHeading(var4, var6 + (double)var10, var8, 2.0F, 12.0F);
//                                this.playSound("gvcguns:gvcguns.fire", 1.0F, 1.0F);
//                                this.worldObj.spawnEntityInWorld(var3);
//
//
//                	       /*GVCEntitySentryBullet var31 = new GVCEntitySentryBullet(this.worldObj, this);
//                	        double var41 = entity1.posX - this.posX;
//                	        double var61 = entity1.posY + (double)entity1.getEyeHeight() - 2.100000023841858D - var31.posY;
//                	        double var81 = entity1.posZ - this.posZ;
//                	        float var101 = MathHelper.sqrt_double(var41 * var41 + var81 * var81) * 0.2F;
//                	        var31.setThrowableHeading(var41, var61 + (double)var101, var81, 1.6F, 0.0F);
//                	        this.playSound("gvcguns:gvcguns.fire", 1.0F, 1.5F);
//                	        this.worldObj.spawnEntityInWorld(var31);
//                			*/
//                            }
                        }
                    }
                }
            }
            ii = 0;
        }else{
            ++ii;
        }



    }

    public void onDeath(DamageSource par1DamageSource)
    {
        super.onDeath(par1DamageSource);
        this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, 3.5F, false);
        if (!this.worldObj.isRemote)
        {
            GVCEntityGuerrillaP entityskeleton = new GVCEntityGuerrillaP(this.worldObj);
            entityskeleton.setLocationAndAngles(this.posX+2, this.posY+3, this.posZ, this.rotationYaw, 0.0F);
            entityskeleton.setCurrentItemOrArmor(0, new ItemStack(GVCUtils.fn_aks74u));
            this.worldObj.spawnEntityInWorld(entityskeleton);

            GVCEntityGuerrillaP entityskeleton2 = new GVCEntityGuerrillaP(this.worldObj);
            entityskeleton2.setLocationAndAngles(this.posX-2, this.posY+3, this.posZ, this.rotationYaw, 0.0F);
            entityskeleton2.setCurrentItemOrArmor(0, new ItemStack(GVCUtils.fn_aks74u));
            this.worldObj.spawnEntityInWorld(entityskeleton2);
        }
    }

    protected void entityInit()
    {
        super.entityInit();
        this.dataWatcher.addObject(13, new Byte((byte)0));
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
        return "none";
    }

    /**
     * Returns the sound this mob makes when it is hurt.
     */
    protected String getHurtSound()
    {
        return "mob.irongolem.hit";
    }

    /**
     * Returns the sound this mob makes on death.
     */
    protected String getDeathSound()
    {
        return "mob.irongolem.death";
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



    public IEntityLivingData onSpawnWithEgg(IEntityLivingData par1EntityLivingData)
    {
        par1EntityLivingData = super.onSpawnWithEgg(par1EntityLivingData);

        /*GVCEntityparas entityskeleton1 = new GVCEntityparas(this.worldObj);
        entityskeleton1.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0F);
        entityskeleton1.onSpawnWithEgg((IEntityLivingData)null);
        this.worldObj.spawnEntityInWorld(entityskeleton1);
        this.mountEntity(entityskeleton1);*/

        {
            this.tasks.addTask(4, this.aiArrowAttack);
            this.addRandomArmor();
            this.enchantEquipment();
        }

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
     * sets this entity's combat AI.
     */
    public void setCombatTask()
    {
        this.tasks.removeTask(this.aiAttackOnCollide);
        this.tasks.removeTask(this.aiArrowAttack);
        ItemStack var1 = this.getHeldItem();


        this.tasks.addTask(4, this.aiArrowAttack);

    }





    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.readEntityFromNBT(par1NBTTagCompound);

        this.setCombatTask();
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
            this.setCombatTask();
        }
    }

    /**
     * Returns the Y Offset of this entity.
     */
    public double getYOffset()
    {
        return super.getYOffset() - 0.5D;
    }

    public static float getMobScale() {
        return 8;
    }
}

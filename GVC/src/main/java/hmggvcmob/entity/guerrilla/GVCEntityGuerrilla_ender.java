package hmggvcmob.entity.guerrilla;


import handmadeguns.items.guns.HMGItem_Unified_Guns;
import hmggvcmob.ai.AIAttackGun;
import hmggvcutil.GVCUtils;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;

import java.util.Random;

import static hmggvcmob.GVCMobPlus.Guns_AR;
import static hmggvcmob.GVCMobPlus.cfg_guerrillasrach;

public class GVCEntityGuerrilla_ender extends EntityGBase
{
    private int teleportDelay;
    public GVCEntityGuerrilla_ender(World par1World)
    {
        super(par1World);
        this.setSize(0.6F, 1.8F);
        this.tasks.addTask(1,aiAttackGun = new AIAttackGun(this,60,0,5,15,30,true));
//        this.tasks.removeTask(new EntityAIOpenDoor(this, true));
        spread = 1;
        //独自射撃処理
    }
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(movespeed = 0.4D);
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20.0D);
        this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(cfg_guerrillasrach);
        //this.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(30.0D);
    }
    
    public void addRandomArmor()
    {
        super.addRandomArmor();
        Random rnd = new Random();

        this.setCurrentItemOrArmor(0, new ItemStack((Item)Guns_AR.get(rnd.nextInt(Guns_AR.size()))));
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

        var3 = this.rand.nextInt(3 + par2);
        for (var4 = 0; var4 < var3; ++var4)
        {
            this.dropItem(Items.emerald, 1);
        }
        var3 = this.rand.nextInt(2 + par2);
        for (var4 = 0; var4 < var3; ++var4)
        {
            this.dropItem(Items.diamond, 1);
        }
        var3 = this.rand.nextInt(5 + par2);
        for (var4 = 0; var4 < var3; ++var4)
        {
            this.dropItem(Items.ender_pearl, 1);
        }

        var3 = this.rand.nextInt(3 + par2);
        for (var4 = 0; var4 < var3; ++var4)
        {
            this.dropItem(GVCUtils.fn_cm, 1);
        }
//        for (var4 = 0; var4 < var3; ++var4)
//        {
//            this.dropItem(GVCUtils.fn_magazine, 1);
//        }

        var3 = this.rand.nextInt(3 + par2);
        if(this.getHeldItem()!=null){
            this.entityDropItem(this.getHeldItem(), 1);
            if(this.getHeldItem().getItem() instanceof HMGItem_Unified_Guns){
                for (var4 = 0; var4 < var3; ++var4)
                {
                    this.dropItem(((HMGItem_Unified_Guns) this.getHeldItem().getItem()).magazine, 1);
                }
            }
        }
        this.setCurrentItemOrArmor(0,null);
    }

    protected void dropRareDrop(int par1)
    {
            this.entityDropItem(new ItemStack(Items.skull, 1, 1), 0.0F);
    }

    public void onUpdate()
    {
    	super.onUpdate();
        if (!this.worldObj.isRemote && this.isEntityAlive()) {
            if (this.getAttackTarget() != null) {
                if (this.getAttackTarget() instanceof EntityPlayer) {
                    if (this.getAttackTarget().getDistanceSqToEntity(this) > 1024) {
                        this.teleportToEntity(this.getAttackTarget());
                        this.teleportDelay = 0;
                    }
                }else if(teleportDelay++ >200 && rnd.nextInt(5) == 0){
                    this.teleportRandomly();
                    this.teleportDelay = 0;
                }
            } else {
                this.teleportDelay = 0;
            }
        }
    }
    
    protected boolean teleportRandomly() {
        double d0 = this.posX + (this.rand.nextDouble() - 0.5D) * 64.0D;
        double d1 = this.posY + (double)(this.rand.nextInt(64) - 32);
        double d2 = this.posZ + (this.rand.nextDouble() - 0.5D) * 64.0D;
        return this.teleportTo(d0, d1, d2);
    }
    
    protected boolean teleportToEntity(Entity p_70816_1_) {
        Vec3 vec3 = Vec3.createVectorHelper(this.posX - p_70816_1_.posX, this.boundingBox.minY + (double)(this.height / 2.0F) - p_70816_1_.posY + (double)p_70816_1_.getEyeHeight(), this.posZ - p_70816_1_.posZ);
        vec3 = vec3.normalize();
        double d0 = 16.0D;
        double d1 = this.posX + (this.rand.nextDouble() - 0.5D) * 8.0D - vec3.xCoord * d0;
        double d2 = this.posY + (double)(this.rand.nextInt(16) - 8) - vec3.yCoord * d0;
        double d3 = this.posZ + (this.rand.nextDouble() - 0.5D) * 8.0D - vec3.zCoord * d0;
        return this.teleportTo(d1, d2, d3);
    }
    protected boolean teleportTo(double p_70825_1_, double p_70825_3_, double p_70825_5_) {
        EnderTeleportEvent event = new EnderTeleportEvent(this, p_70825_1_, p_70825_3_, p_70825_5_, 0.0F);
        if (MinecraftForge.EVENT_BUS.post(event)) {
            return false;
        } else {
            double d3 = this.posX;
            double d4 = this.posY;
            double d5 = this.posZ;
            this.posX = event.targetX;
            this.posY = event.targetY;
            this.posZ = event.targetZ;
            boolean flag = false;
            int i = MathHelper.floor_double(this.posX);
            int j = MathHelper.floor_double(this.posY);
            int k = MathHelper.floor_double(this.posZ);
            if (this.worldObj.blockExists(i, j, k)) {
                boolean flag1 = false;
                
                while(!flag1 && j > 0) {
                    Block block = this.worldObj.getBlock(i, j - 1, k);
                    if (block.getMaterial().blocksMovement()) {
                        flag1 = true;
                    } else {
                        --this.posY;
                        --j;
                    }
                }
                
                if (flag1) {
                    this.setPosition(this.posX, this.posY, this.posZ);
                    if (this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox).isEmpty() && !this.worldObj.isAnyLiquid(this.boundingBox)) {
                        flag = true;
                    }
                }
            }
            
            if (!flag) {
                this.setPosition(d3, d4, d5);
                return false;
            } else {
                short short1 = 128;
                
                for(int l = 0; l < short1; ++l) {
                    double d6 = (double)l / ((double)short1 - 1.0D);
                    float f = (this.rand.nextFloat() - 0.5F) * 0.2F;
                    float f1 = (this.rand.nextFloat() - 0.5F) * 0.2F;
                    float f2 = (this.rand.nextFloat() - 0.5F) * 0.2F;
                    double d7 = d3 + (this.posX - d3) * d6 + (this.rand.nextDouble() - 0.5D) * (double)this.width * 2.0D;
                    double d8 = d4 + (this.posY - d4) * d6 + this.rand.nextDouble() * (double)this.height;
                    double d9 = d5 + (this.posZ - d5) * d6 + (this.rand.nextDouble() - 0.5D) * (double)this.width * 2.0D;
                    this.worldObj.spawnParticle("portal", d7, d8, d9, (double)f, (double)f1, (double)f2);
                }
                
                this.worldObj.playSoundEffect(d3, d4, d5, "mob.endermen.portal", 1.0F, 1.0F);
                this.playSound("mob.endermen.portal", 1.0F, 1.0F);
                return true;
            }
        }
    }
	public boolean isConverting() {
		return false;
	}
	
    protected String getLivingSound()
    {
        return "mob.skeleton.say";
    }

    protected String getHurtSound()
    {
        return "mob.skeleton.hurt";
    }

    protected String getDeathSound()
    {
        return "mob.skeleton.death";
    }

    protected void func_145780_a(int p_145780_1_, int p_145780_2_, int p_145780_3_, Block p_145780_4_)
    {
        this.playSound("mob.skeleton.step", 0.15F, 1.0F);
    }
}

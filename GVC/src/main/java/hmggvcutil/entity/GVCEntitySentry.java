package hmggvcutil.entity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import hmggvcutil.entity.ai.AIAttackGun;
import hmggvcutil.entity.ai.AITarget_AntiAir;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityFlying;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.monster.IMob;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import static hmggvcutil.GVCUtils.fn_m240b;

public class GVCEntitySentry extends EntityGolem
{
	//private static final EntityCreature EntityCreature = null;
	//private EntityAIAttackOnCollide aiAttackOnCollide = new EntityAIAttackOnCollide(this, EntityPlayer.class, 1.2D, false);
	
    public GVCEntitySentry(World par1World)
    {
        super(par1World);
        this.setSize(0.4F, 1.8F);
        this.getNavigator().setAvoidsWater(true);
        this.tasks.addTask(1, new AIAttackGun(this, 80, 1, 1000, false));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true));
        this.targetTasks.addTask(2, new AITarget_AntiAir(this, EntityLiving.class, 0, true, false, IMob.mobSelector));
        this.targetTasks.addTask(3, new AITarget_AntiAir(this, EntityGhast.class, 0, true));
        this.targetTasks.addTask(3, new AITarget_AntiAir(this, EntityCreeper.class, 0, true));
        this.targetTasks.addTask(3, new AITarget_AntiAir(this, EntityFlying.class, 0, true));
        addRandomArmor();
    }

    
    @SideOnly(Side.CLIENT)
    public int getBrightnessForRender(float par1)
    {
        return 15728880;
    }

    /**
     * Gets how bright this entity is.
     */
    public float getBrightness(float par1)
    {
        return 1.0F;
    }
    
    
    
    public boolean canAttackClass(Class par1Class)
    {
        return EntityCreature.class != par1Class;
    }
    
    
    protected boolean canDespawn()
    {
        return false;
    }
    
    
    public void onUpdate(){
        super.onUpdate();
        this.getHeldItem().getItem().onUpdate(this.getHeldItem(),worldObj,this,0,true);
        this.setSneaking(true);
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
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0D);
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(40.0D);
        this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(80.0D);
        this.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(30.0D);
    }


    protected void addRandomArmor()
    {
        super.addRandomArmor();
        this.setCurrentItemOrArmor(0, new ItemStack(fn_m240b));
    }

    public IEntityLivingData onSpawnWithEgg(IEntityLivingData par1EntityLivingData)
    {
        par1EntityLivingData = super.onSpawnWithEgg(par1EntityLivingData);

        this.addRandomArmor();
        this.enchantEquipment();


        this.setCanPickUpLoot(false);

        return par1EntityLivingData;
    }


	public boolean isConverting() {
		return false;
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

    protected void func_145780_a(int p_145780_1_, int p_145780_2_, int p_145780_3_, Block p_145780_4_)
    {
        this.playSound("mob.irongolem.walk", 1.0F, 1.0F);
    }

	public static float getMobScale() {
		return 4;
	}
}

package hmggvcmob.entity.friend;


import handmadevehicle.SlowPathFinder.WorldForPathfind;
import hmggvcmob.entity.IHasVehicleGacha;
import hmggvcmob.entity.VehicleSpawnGachaOBJ;
import hmggvcutil.GVCUtils;
import hmggvcmob.ai.AIAttackGun;
import net.minecraft.block.Block;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GVCEntitySoldierRPG extends EntitySoBase implements IHasVehicleGacha
{
    public static VehicleSpawnGachaOBJ[] vehicleSpawnGachaOBJ;
    public static int vehilceGacha_rate_sum = 0;

    public GVCEntitySoldierRPG(World par1World)
    {
        super(par1World);
        this.setSize(0.6F, 1.8F);
		this.tasks.addTask(2, aiAttackGun = new AIAttackGun(this, 120,20, 10, 5, true,true,new WorldForPathfind(worldObj)));
    }
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(movespeed = 0.23000000417232513D);
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20.0D);
        this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(50.0D);
        //this.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(30.0D);
    }

    public void onUpdate(){
        super.onUpdate();
        if(getAttackTarget() != null){
            this.setSneaking(false);
        }else {
            this.setSneaking(false);
        }
    }

    public void addRandomArmor()
    {
        super.addRandomArmor();
        this.setCurrentItemOrArmor(0, new ItemStack(GVCUtils.fn_smaw));
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
    @Override
    public VehicleSpawnGachaOBJ[] getVehicleGacha() {
        return vehicleSpawnGachaOBJ;
    }
    @Override
    public int getVehicleGacha_rate_sum() {
        return vehilceGacha_rate_sum;
    }
}

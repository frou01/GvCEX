package hmggvcmob.entity.friend;

import hmggvcutil.GVCUtils;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GVCXEntitySoldierSpawn extends EntitySoBases
{
    public GVCXEntitySoldierSpawn(World p_i1582_1_) {
        super(p_i1582_1_);
        renderDistanceWeight = 0;
    }
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.33000000417232513D);
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20.0D);
        this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(60.0D);
        //this.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(30.0D);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        setDead();
    }
    public void setDead(){
        super.setDead();
        if(!worldObj.isRemote) {
            int mob = rand.nextInt(4);
            int yaw = rand.nextInt(4);
            if (mob == 0) {


                GVCEntitySoldier entityskeleton = new GVCEntitySoldier(worldObj);
                entityskeleton.setLocationAndAngles(this.posX + 0.5, this.posY, this.posZ + 0.5, yaw, 0.0F);
                entityskeleton.setCurrentItemOrArmor(0, new ItemStack(GVCUtils.fn_m16a4));
                worldObj.spawnEntityInWorld(entityskeleton);

            } else if (mob == 1) {


                GVCEntitySoldierSP entityskeleton = new GVCEntitySoldierSP(worldObj);
                entityskeleton.setLocationAndAngles(this.posX + 0.5, this.posY, this.posZ + 0.5, yaw, 0.0F);
                entityskeleton.setCurrentItemOrArmor(0, new ItemStack(GVCUtils.fn_m110));
                worldObj.spawnEntityInWorld(entityskeleton);

            } else if (mob == 2) {


                GVCEntitySoldierMG entityskeleton = new GVCEntitySoldierMG(worldObj);
                entityskeleton.setLocationAndAngles(this.posX + 0.5, this.posY, this.posZ + 0.5, yaw, 0.0F);
                entityskeleton.setCurrentItemOrArmor(0, new ItemStack(GVCUtils.fn_m240b));
                worldObj.spawnEntityInWorld(entityskeleton);

            } else if (mob == 3) {


                GVCEntitySoldierRPG entityskeleton = new GVCEntitySoldierRPG(worldObj);
                entityskeleton.setLocationAndAngles(this.posX + 0.5, this.posY, this.posZ + 0.5, yaw, 0.0F);
                entityskeleton.setCurrentItemOrArmor(0, new ItemStack(GVCUtils.fn_smaw));
                worldObj.spawnEntityInWorld(entityskeleton);

            }
        }
    }
}

package hmggvcmob.entity.friend;

import handmadevehicle.entity.parts.Modes;
import hmggvcutil.GVCUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class GVCXEntitySoldierSpawn extends Entity
{
    public GVCXEntitySoldierSpawn(World p_i1582_1_) {
        super(p_i1582_1_);
        renderDistanceWeight = 0;
    }

    @Override
    protected void entityInit() {

    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        setDead();
    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound p_70037_1_) {

    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound p_70014_1_) {

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

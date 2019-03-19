package hmggvcmob.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;


public class GVCdummyhitbox extends EntityLiving {
    public Entity master;
    public GVCdummyhitbox(World p_i1582_1_) {
        super(p_i1582_1_);
        this.setSize(0.5f,2);
    }
    @Override
    public void onUpdate(){
        motionX = motionY = motionZ = 0;
        super.onUpdate();
        motionX = motionY = motionZ = 0;
        if(!this.worldObj.isRemote&&(master == null||master.isDead)){
            this.setDead();
        }
    }
    public boolean canBePushed()
    {
        return false;
    }
    public boolean interact(EntityPlayer p_70085_1_) {
        {
            return false;
        }
    }
    public boolean attackEntityFrom(DamageSource source, float par2)
    {
        master.attackEntityFrom(source,par2);
        return false;
    }
    @Override
    public void readEntityFromNBT(NBTTagCompound p_70037_1_) {
        super.readEntityFromNBT(p_70037_1_);
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound p_70014_1_) {
        this.setDead();
    }
    public void applyEntityCollision(Entity p_70108_1_)
    {
    }
    public boolean shouldRiderSit(){
        return true;
    }
    public boolean canDespawn(){
        return false;
    }
}

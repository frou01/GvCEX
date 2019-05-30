package hmvehicle.entity;


import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;
import handmadeguns.entity.EntityHasMaster;
import hmggvcmob.entity.EntityMGAX55;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

import static net.minecraft.util.DamageSource.inWall;

public class EntityChild extends Entity implements IEntityAdditionalSpawnData,EntityHasMaster {
    public Entity master;
    public boolean rideable;
    public int idinmasterEntityt = -1;
    public float thirddist = 4;
    public EntityChild(World p_i1582_1_) {
        super(p_i1582_1_);
        this.setSize(1,2);
        rideable = true;
    }
    @Override
    protected void entityInit() {

    }

    public EntityChild(World p_i1582_1_, float x, float y, boolean rideable) {
        super(p_i1582_1_);
        this.setSize(x,y);
        this.rideable = rideable;
    }
    @Override
    public void onUpdate(){

        motionX = motionY = motionZ = 0;
        super.onUpdate();
        motionX = motionY = motionZ = 0;
        if(master == null||worldObj.getEntityByID(master.getEntityId()) == null||master.isDead || (master instanceof EntityMGAX55 && !((EntityMGAX55) master).isChild(this))){
            setDead();
        }else if(master instanceof EntityLiving){
            if(this.riddenByEntity != null && this.riddenByEntity instanceof EntityLiving){
                ((EntityLiving)this.riddenByEntity).setAttackTarget(((EntityLiving) master).getAttackTarget());
            }
            this.rotationYaw = ((EntityLiving) master).rotationYawHead;
        }
        setPosition(posX,posY,posZ);
    }
    public boolean canBePushed()
    {
        return true;
    }
    public boolean canBeCollidedWith()
    {
        return true;
    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound p_70037_1_) {

    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound p_70014_1_) {
    }
//    @Override
//    public boolean writeToNBTOptional(NBTTagCompound p_70039_1_)
//    {
//        return false;
//    }

    public boolean interactFirst(EntityPlayer p_70085_1_) {
        if(rideable) {
            if (p_70085_1_.ridingEntity != null || (riddenByEntity != null && (riddenByEntity instanceof EntityPlayer) && riddenByEntity != p_70085_1_)) {
                return true;
            }
            if (!worldObj.isRemote) {
                p_70085_1_.mountEntity(this);
            }
            return true;
        }else {
            return true;
        }
    }
    public boolean attackEntityFrom(DamageSource source, float par2)
    {
        if(source != inWall &&master != null)
        master.attackEntityFrom(source,par2);
        return false;
    }
    public void applyEntityCollision(Entity p_70108_1_)
    {
        if(master != null && p_70108_1_ != master) master.applyEntityCollision(p_70108_1_);
    }
    public boolean shouldRiderSit(){
        return true;
    }
    public boolean canDespawn(){
        return false;
    }
    public double getMountedYOffset() {
        return 0.1D;
    }
    public void setsize (float x,float y){
        this.setSize(x,y);
    }

    @Override
    public void writeSpawnData(ByteBuf buffer) {
        buffer.writeFloat(this.width);
        buffer.writeFloat(this.height);
        buffer.writeInt(idinmasterEntityt);
        buffer.writeInt(this.master != null ? this.master.getEntityId():-1);
    }

    @Override
    public void readSpawnData(ByteBuf additionalData) {
        this.width = additionalData.readFloat();
        this.height = additionalData.readFloat();
        this.idinmasterEntityt = additionalData.readInt();
        this.master = worldObj.getEntityByID(additionalData.readInt());
    }

    @Override
    public Entity getmaster() {
        return master;
    }
}

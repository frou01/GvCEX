package handmadevehicle.entity;

import handmadeguns.entity.SpHitCheckEntity;
import handmadevehicle.entity.parts.logics.BaseLogic;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class EntityDummy_rider extends Entity implements SpHitCheckEntity {
    public BaseLogic linkedBaseLogic;
    public int linkedSeatID;
    public EntityDummy_rider(World p_i1582_1_,BaseLogic linkedBaseLogic,int linkedSeatID) {
        super(p_i1582_1_);
        this.linkedBaseLogic = linkedBaseLogic;
        worldObj.loadedEntityList.add(this);//クソッタレハードコードにはこうやって対策してやる！
        //マジでこれで動くんすか・・・
    }

    public void updateRiderPosition(){
        if(linkedBaseLogic.mc_Entity.isDead){
            this.setDead();
        }
    }
    @Override
    protected void entityInit() {

    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound p_70037_1_) {

    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound p_70014_1_) {

    }

    @Override
    public boolean isRidingEntity(Entity entity) {
        return linkedBaseLogic.isRidingEntity(entity);
    }
}

package hmggvcmob.entity;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import static handmadeguns.HandmadeGunsCore.proxy;
import static java.lang.Math.atan2;
import static java.lang.Math.sqrt;

public class TU95 extends Entity {
    public int fuse;
    public boolean soundstarted = false;
    /** Entity motion X */
    public TU95(World p_i1582_1_) {
        super(p_i1582_1_);
        renderDistanceWeight = 1048576;
        ignoreFrustumCheck = true;
    }
    public void onUpdate(){
        super.onUpdate();
//        System.out.println("debug X" + motionX);
//        System.out.println("debug Z" + motionZ);
//        System.out.println();
        this.rotationYaw = (float)(atan2(this.motionX, this.motionZ) * 180.0D / Math.PI);
        moveEntity(motionX,motionY,motionZ);
        if(!worldObj.isRemote && fuse<0){
            setDead();
        }
        if(worldObj.isRemote && !soundstarted){
            proxy.playsoundatBullet("gvcmob:gvcmob.Tu-95prop",16,1,4096*4096,4096*4096,this,true);
            soundstarted = true;
        }
        fuse--;

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
}

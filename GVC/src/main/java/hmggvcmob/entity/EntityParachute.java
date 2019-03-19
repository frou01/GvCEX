package hmggvcmob.entity;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EntityParachute extends EntityLiving {
    public EntityParachute(World p_i1594_1_) {
        super(p_i1594_1_);
        setSize(0.5f,0.5f);
    }

    public void onUpdate(){
        renderDistanceWeight = 120;
        motionY*=0.9;
        super.onUpdate();
        if(riddenByEntity != null)riddenByEntity.fallDistance = 0;
        if(riddenByEntity != null)fallDistance = 0;
        if(isInWater())setDead();
        if(onGround)setDead();
    }
    @Override
    public ItemStack getHeldItem() {
        return null;
    }

    @Override
    public ItemStack getEquipmentInSlot(int p_71124_1_) {
        return null;
    }

    @Override
    public void setCurrentItemOrArmor(int p_70062_1_, ItemStack p_70062_2_) {

    }

    @Override
    public ItemStack[] getLastActiveItems() {
        return new ItemStack[0];
    }
}

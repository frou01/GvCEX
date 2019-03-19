package handmadeguns.entity.bullets;

import handmadeguns.HandmadeGunsCore;
import handmadeguns.entity.IFF;
import littleMaidMobX.LMM_EntityLittleMaid;
import littleMaidMobX.LMM_EntityLittleMaidAvatar;
import littleMaidMobX.LMM_EntityLittleMaidAvatarMP;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

import static handmadeguns.HandmadeGunsCore.islmmloaded;
import static net.minecraft.util.MathHelper.floor_double;

public class HMGEntityBullet_Flame extends HMGEntityBulletBase {
    public HMGEntityBullet_Flame(World world){
        super(world);
    }
    public HMGEntityBullet_Flame(World par1World, Entity par2Entity, int damege, float bspeed, float bure, String bulletTypeName) {
        super(par1World, par2Entity, damege, bspeed, bure, bulletTypeName);
    }
    protected void onImpact(MovingObjectPosition var1)
    {
        super.onImpact(var1);
        if (var1.entityHit != null)
        {
            int var2 = this.Bdamege;

            if(islmmloaded&&(this.thrower instanceof LMM_EntityLittleMaid || this.thrower instanceof LMM_EntityLittleMaidAvatar || this.thrower instanceof LMM_EntityLittleMaidAvatarMP) && HandmadeGunsCore.cfg_FriendFireLMM){
                if (var1.entityHit instanceof LMM_EntityLittleMaid)
                {
                    var2 = 0;
                }
                if (var1.entityHit instanceof LMM_EntityLittleMaidAvatar)
                {
                    var2 = 0;
                }
                if (var1.entityHit instanceof EntityPlayer)
                {
                    var2 = 0;
                }
            }
            if(this.thrower instanceof IFF){
                if(((IFF) this.thrower).is_this_entity_friend(var1.entityHit)){
                    var2 = 0;
                }
            }
            if(var2 != 0)
                var1.entityHit.setFire(5);
        }else {
            this.setDead();
        }
        if(!worldObj.isRemote && var1.hitVec!=null) {
            for (int x = -1; x < 1; x++) {
                for (int y = -1; y < 1; y++) {
                    for (int z = -1; z < 1; z++) {
                        Block checkingBlock = worldObj.getBlock(
                                floor_double(var1.hitVec.xCoord) + x,
                                floor_double(var1.hitVec.yCoord) + y,
                                floor_double(var1.hitVec.zCoord) + z);
                        boolean flag = false;
                        if (checkingBlock == Blocks.air) {
                            for (int nx = -1; nx < 1; nx++)
                                for (int ny = -1; ny < 1; ny++)
                                    for (int nz = -1; nz < 1; nz++) {
                                        Block checkingBlock2 = worldObj.getBlock(
                                                floor_double(var1.hitVec.xCoord) + x + nx,
                                                floor_double(var1.hitVec.yCoord) + y + ny,
                                                floor_double(var1.hitVec.zCoord) + z + nz);
                                        if(checkingBlock2!= Blocks.air && checkingBlock2 != Blocks.fire)flag = true;
                                    }

                            if(flag)worldObj.setBlock(
                                    floor_double(var1.hitVec.xCoord) + x,
                                    floor_double(var1.hitVec.yCoord) + y,
                                    floor_double(var1.hitVec.zCoord) + z, Blocks.fire);
                        }
                    }
                }
            }
        }
    }
    public void onUpdate(){
        super.onUpdate();
        if(!worldObj.isRemote) {
            for (int x = -1; x < 1; x++) {
                for (int y = -1; y < 1; y++) {
                    for (int z = -1; z < 1; z++) {
                        Block checkingBlock = worldObj.getBlock(
                                floor_double(this.posX) + x,
                                floor_double(this.posY) + y,
                                floor_double(this.posZ) + z);
                        boolean flag = false;
                        if (checkingBlock == Blocks.air) {
                            for (int nx = -1; nx < 1; nx++)
                                for (int ny = -1; ny < 1; ny++)
                                    for (int nz = -1; nz < 1; nz++) {
                                Block checkingBlock2 = worldObj.getBlock(
                                        floor_double(this.posX) + x + nx,
                                        floor_double(this.posY) + y + ny,
                                        floor_double(this.posZ) + z + nz);
                                        if(checkingBlock2!= Blocks.air && checkingBlock2 != Blocks.fire)flag = true;
                                    }

                            if(flag)worldObj.setBlock(
                                    floor_double(this.posX) + x,
                                    floor_double(this.posY) + y,
                                    floor_double(this.posZ) + z, Blocks.fire);
                        }
                    }
                }
            }
        }
    }
}

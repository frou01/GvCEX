package handmadeguns.entity.bullets;

//import littleMaidMobX.LMM_EntityLittleMaid;
//import littleMaidMobX.LMM_EntityLittleMaidAvatar;

import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;
import handmadeguns.HMGMessageKeyPressedC;
import handmadeguns.HMGPacketHandler;
import handmadeguns.HandmadeGunsCore;
import handmadeguns.entity.IFF;
import handmadeguns.entity.I_SPdamageHandle;
import io.netty.buffer.ByteBuf;
import littleMaidMobX.LMM_EntityLittleMaid;
import littleMaidMobX.LMM_EntityLittleMaidAvatar;
import littleMaidMobX.LMM_EntityLittleMaidAvatarMP;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.*;
import net.minecraft.world.World;

import static handmadeguns.HandmadeGunsCore.islmmloaded;

public class HMGEntityBullet_AT extends HMGEntityBulletBase implements IEntityAdditionalSpawnData
{
	public HMGEntityBullet_AT(World worldIn) {
		super(worldIn);
	}

	public HMGEntityBullet_AT(World worldIn, Entity throwerIn, int damege, float bspeed, float bure, String modelname) {
		super(worldIn, throwerIn, damege, bspeed, bure,modelname);
	}

	public HMGEntityBullet_AT(World worldIn, double x, double y, double z) {
		super(worldIn, x, y, z);
	}


	/**
	 * Called when this EntityThrowable hits a block or entity.
	 */
	protected void onImpact(MovingObjectPosition var1)
	{
		super.onImpact(var1);
		if (var1.entityHit != null)
		{
			if(var1.entityHit instanceof EntityLivingBase){
				EntityLivingBase lebl = (EntityLivingBase)var1.entityHit;
				lebl.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id,60 * 10, 10));
				lebl.addPotionEffect(new PotionEffect(Potion.blindness.id,60 * 10, 10));
			}
		}
	}
	public void writeSpawnData(ByteBuf buffer){
		super.writeSpawnData(buffer);
	}
	public void readSpawnData(ByteBuf additionalData){
		super.readSpawnData(additionalData);
	}
}

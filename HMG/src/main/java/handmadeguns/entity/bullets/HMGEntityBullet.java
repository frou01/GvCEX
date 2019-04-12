package handmadeguns.entity.bullets;

//import littleMaidMobX.LMM_EntityLittleMaid;
//import littleMaidMobX.LMM_EntityLittleMaidAvatar;

import com.lulan.shincolle.entity.BasicEntityShipHostile;
import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;
import handmadeguns.HMGMessageKeyPressedC;
import handmadeguns.HMGPacketHandler;
import handmadeguns.HandmadeGunsCore;
import handmadeguns.entity.IFF;
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
        import net.minecraft.util.*;
import net.minecraft.world.World;

import static handmadeguns.CommonSideProxyHMG.kanmusu_defence;
import static handmadeguns.HandmadeGunsCore.islmmloaded;
import static handmadeguns.HandmadeGunsCore.isshincoleloaded;

public class HMGEntityBullet extends HMGEntityBulletBase implements IEntityAdditionalSpawnData
{

	public HMGEntityBullet(World worldIn) {
		super(worldIn);
	}
	public HMGEntityBullet(World worldIn, Entity throwerIn, int damege, float bspeed, float bure) {
		super(worldIn, throwerIn, damege, bspeed, bure,"default");
	}
	public HMGEntityBullet(World worldIn, Entity throwerIn, int damege, float bspeed, float bure,String modelname) {
		super(worldIn, throwerIn, damege, bspeed, bure,modelname);
	}

//	public HMGEntityBullet(World worldIn, double x, double y, double z) {
//		super(worldIn, x, y, z);
//	}

	/**
	 * Called when this EntityThrowable hits a block or entity.
	 */
	protected void onImpact(MovingObjectPosition var1)
	{
		super.onImpact(var1);
		if (var1.entityHit != null)
		{
			int var2 = this.Bdamege;
//			System.out.println("debug" + this.thrower +"  "+ var1.entityHit);
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
			float backdeflevel = 0;
			if(this.thrower instanceof IFF){
				if(((IFF) this.thrower).is_this_entity_friend(var1.entityHit)){
					var2 = 0;
				}
			}
			var1.entityHit.hurtResistantTime = 0;

			double moXback = var1.entityHit.motionX;//?m?b?N?o?b?N???????p
			double moYback = var1.entityHit.motionY;//????オ???????p
			double moZback = var1.entityHit.motionZ;//?m?b?N?o?b?N???????p

			if(isshincoleloaded){
				if (var1.entityHit instanceof BasicEntityShipHostile){
					backdeflevel = ((BasicEntityShipHostile) var1.entityHit).getDefValue();
					try {
						kanmusu_defence.set(var1.entityHit,backdeflevel/10);
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					}
				}
			}
			if(var1.entityHit.attackEntityFrom((new EntityDamageSourceIndirect("arrow", this, this.getThrower())).setProjectile(),var2)){
				var1.entityHit.motionX = moXback;
				var1.entityHit.motionY = moYback;
				var1.entityHit.motionZ = moZback;
				Vec3 knockvec = this.getLook((float) knockbackXZ,-this.rotationYaw,-this.rotationPitch);
				if(var1.entityHit instanceof EntityLivingBase){
					if(this.rand.nextDouble() >= ((EntityLivingBase)var1.entityHit).getEntityAttribute(SharedMonsterAttributes.knockbackResistance).getAttributeValue()){
						var1.entityHit.isAirBorne = true;
						var1.entityHit.motionX += knockvec.xCoord;
						var1.entityHit.motionY += knockvec.yCoord + knockbackY;
						var1.entityHit.motionZ += knockvec.zCoord;
					}
				}
			}else if(var1.entityHit.attackEntityFrom((new EntityDamageSourceIndirect("penetrate", this, this.getThrower()).setProjectile()),(float)var2)){
				var1.entityHit.motionX = moXback;
				var1.entityHit.motionY = moYback;
				var1.entityHit.motionZ = moZback;
				Vec3 knockvec = this.getLook((float) knockbackXZ,-this.rotationYaw,-this.rotationPitch);
				if(var1.entityHit instanceof EntityLivingBase){
					if(this.rand.nextDouble() >= ((EntityLivingBase)var1.entityHit).getEntityAttribute(SharedMonsterAttributes.knockbackResistance).getAttributeValue()){
						var1.entityHit.isAirBorne = true;
						var1.entityHit.motionX += knockvec.xCoord;
						var1.entityHit.motionY += knockvec.yCoord + knockbackY;
						var1.entityHit.motionZ += knockvec.zCoord;
					}
				}
			}
			if(isshincoleloaded){
				if (var1.entityHit instanceof BasicEntityShipHostile){
					try {
						kanmusu_defence.set(var1.entityHit,backdeflevel);
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					}
				}
			}
			if (!this.worldObj.isRemote)
			{
				if(this.getThrower() != null&&getThrower() instanceof EntityPlayerMP){
					HMGPacketHandler.INSTANCE.sendTo(new HMGMessageKeyPressedC(10, this.getThrower().getEntityId()),(EntityPlayerMP)this.getThrower());
				}
				this.setDead();
			}
		}else{
			Block lblock = worldObj.getBlock(var1.blockX, var1.blockY, var1.blockZ);
			int lmeta = worldObj.getBlockMetadata(var1.blockX, var1.blockY, var1.blockZ);
			if (checkDestroyBlock(var1, var1.blockX, var1.blockY, var1.blockZ, lblock, lmeta)) {
				if (!this.worldObj.isRemote)
				{
					onBreakBlock(var1, var1.blockX, var1.blockY, var1.blockZ, lblock, lmeta);
				}
			} else {
				for (int i = 0; i < 4; ++i) {
//					worldObj.spawnParticle("snowballpoof", this.posX, this.posY,
					worldObj.spawnParticle("smoke",
							var1.hitVec.xCoord, var1.hitVec.yCoord, var1.hitVec.zCoord,
							0.0D, 0.0D, 0.0D);
				}
				Block block = this.worldObj.getBlock(var1.blockX,
						var1.blockY,
						var1.blockZ);
				if(!block.isAir(worldObj,var1.blockX,
						var1.blockY,
						var1.blockZ)) {
					worldObj.playSoundEffect((float) var1.hitVec.xCoord, (float) var1.hitVec.yCoord, (float) var1.hitVec.zCoord, new ResourceLocation(block.stepSound.getStepResourcePath()).getResourcePath(), (block.stepSound.getVolume() + 1.0F) / 2.0F, block.stepSound.getPitch() * 0.8F);
					this.worldObj.spawnParticle("blockcrack_" + Block.getIdFromBlock(block) + "_" +
									this.worldObj.getBlockMetadata(var1.blockX,
											var1.blockY,
											var1.blockZ)
							, var1.hitVec.xCoord, var1.hitVec.yCoord, var1.hitVec.zCoord, 4.0D * ((double) this.rand.nextFloat() - 0.5D), 0.5D, ((double) this.rand.nextFloat() - 0.5D) * 4.0D);
				}
			}
			if (!this.worldObj.isRemote)
			{
				if(!this.canbounce) this.setDead();
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

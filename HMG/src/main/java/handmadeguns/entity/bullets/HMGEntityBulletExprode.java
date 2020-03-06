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
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import static handmadeguns.HandmadeGunsCore.cfg_blockdestroy;
import static handmadeguns.HandmadeGunsCore.islmmloaded;

public class HMGEntityBulletExprode extends HMGEntityBulletBase implements IEntityAdditionalSpawnData
{
	public float exlevel = 2.5F;
	public MovingObjectPosition hitobjectposition;
	public HMGEntityBulletExprode(World worldIn) {
		super(worldIn);
	}

	public HMGEntityBulletExprode(World worldIn, Entity throwerIn, int damege, float bspeed, float bure) {
		super(worldIn, throwerIn, damege, bspeed, bure,"default");
		this.canbounce = false;
		this.bouncerate = 0.1f;
	}
	public HMGEntityBulletExprode(World worldIn, Entity throwerIn, int damege, float bspeed, float bure, float exl, boolean canex) {
		this(worldIn, throwerIn, damege, bspeed, bure);
		exlevel = exl;
		this.canex = canex;
		this.canbounce = false;
		this.bouncerate = 0.1f;
	}
	public HMGEntityBulletExprode(World worldIn, Entity throwerIn, int damege, float bspeed, float bure, float exl, boolean canex, String modelname) {
		super(worldIn, throwerIn, damege, bspeed, bure, modelname);
		exlevel = exl;
		this.canex = canex;
		this.canbounce = false;
		this.bouncerate = 0.1f;
	}
	public void onUpdate(){
		super.onUpdate();
		if(noex && !this.worldObj.isRemote){
			if (hitedentity != null && hitedentity != this.getThrower() && noex) {
				if(this.getThrower() != null&&getThrower() instanceof EntityPlayerMP){
					HMGPacketHandler.INSTANCE.sendTo(new HMGMessageKeyPressedC(10, this.getThrower().getEntityId()),(EntityPlayerMP)this.getThrower());
				}
				int i = Bdamege;
				if(islmmloaded&&(this.thrower instanceof LMM_EntityLittleMaid || this.thrower instanceof LMM_EntityLittleMaidAvatar || this.thrower instanceof LMM_EntityLittleMaidAvatarMP) && HandmadeGunsCore.cfg_FriendFireLMM){
					if (hitedentity instanceof LMM_EntityLittleMaid)
					{
						i = 0;
					}
					if (hitedentity instanceof LMM_EntityLittleMaidAvatar)
					{
						i = 0;
					}
					if (hitedentity instanceof EntityPlayer)
					{
						i = 0;
					}
				}
				if(this.thrower instanceof IFF){
					if(((IFF) this.thrower).is_this_entity_friend(hitedentity)){
						i = 0;
					}
				}
				hitedentity.hurtResistantTime = 0;
				double moXback = hitedentity.motionX;//ノックバック無効化用
				double moYback = hitedentity.motionY;//跳ね上がり無効化用
				double moZback = hitedentity.motionZ;//ノックバック無効化用
				double posXback = this.posX;
				double posYback = this.posY;
				double posZback = this.posZ;
				this.posX = hitobjectposition.hitVec.xCoord;
				this.posY = hitobjectposition.hitVec.yCoord;
				this.posZ = hitobjectposition.hitVec.zCoord;
				boolean flag;
				if(hitobjectposition.entityHit instanceof I_SPdamageHandle){
					flag = ((I_SPdamageHandle)hitobjectposition.entityHit).attackEntityFrom_with_Info(hitobjectposition,(new EntityDamageSourceIndirect("arrow", this, this.getThrower())).setProjectile(),i);
				}else {
					flag = hitobjectposition.entityHit.attackEntityFrom((new EntityDamageSourceIndirect("arrow", this, this.getThrower())).setProjectile(),i);
				}
				if(flag){
					hitedentity.motionX = moXback;
					hitedentity.motionY = moYback;
					hitedentity.motionZ = moZback;
					Vec3 knockvec = this.getLook((float) knockbackXZ,-this.rotationYaw,-this.rotationPitch);
					if(hitedentity instanceof EntityLivingBase){
						if(this.rand.nextDouble() >= ((EntityLivingBase)hitedentity).getEntityAttribute(SharedMonsterAttributes.knockbackResistance).getAttributeValue()){
							hitedentity.isAirBorne =true;
							hitedentity.motionX += knockvec.xCoord;
							hitedentity.motionY += knockvec.yCoord + knockbackY;
							hitedentity.motionZ += knockvec.zCoord;
						}
					}
					this.setDead();
				}
				this.posX = posXback;
				this.posY = posYback;
				this.posZ = posZback;
//				hitedentity.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), (float)i);
				this.worldObj.spawnParticle("smoke", this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D);
			}
			setDead();
		}
	}

	public HMGEntityBulletExprode(World worldIn, double x, double y, double z) {
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
			if(!noex && !canbounce){
				this.explode(var1.hitVec.xCoord,var1.hitVec.yCoord+0.125,var1.hitVec.zCoord, this.exlevel, this.canex && cfg_blockdestroy);
				hitedentity = var1.entityHit;
				hitobjectposition = var1;
				noex = true;
			}else if(canbounce){
			}
			if (!this.worldObj.isRemote&& noex) {
				this.setDead();
			}
		}else {
			if (!this.worldObj.isRemote && (!canbounce||fuse<=0))
			{
				this.setDead();
				if(!noex){
					this.explode(var1.hitVec.xCoord,var1.hitVec.yCoord+0.125,var1.hitVec.zCoord, this.exlevel, this.canex && cfg_blockdestroy);
				}
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

package handmadevehicle.entity;

import handmadevehicle.AddNewVehicle;
import handmadevehicle.entity.parts.IPlane;
import handmadevehicle.entity.parts.ModifiedBoundingBox;
import handmadevehicle.entity.parts.logics.LogicsBase;
import handmadevehicle.entity.parts.logics.PlaneBaseLogic;
import handmadevehicle.entity.prefab.Prefab_Vehicle_Base;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import javax.vecmath.Quat4d;
import java.util.List;

import static handmadevehicle.HMVehicle.proxy_HMVehicle;

public class EntityVehicle_Plane extends EntityVehicle implements IPlane {
	PlaneBaseLogic baseLogic;
	float maxHealth = 150;
//	ModifiedBoundingBox nboundingbox;
	public EntityVehicle_Plane(World par1World) {
		super(par1World);
		this.init_2(typename);
		this.setSize(5f, 5f);
	}
	public EntityVehicle_Plane(World par1World,String typename) {
		super(par1World);
		this.init_2(typename);
		this.setSize(5f, 5f);
	}
	public void init_2(String typename) {
		this.typename = typename;
		
		ignoreFrustumCheck = true;
		baseLogic = new PlaneBaseLogic(worldObj, this);
		Prefab_Vehicle_Base infos = AddNewVehicle.seachInfo(typename);
		baseLogic.setinfo(infos);
//		baseLogic.slipresist = 4;
		
		ModifiedBoundingBox nboundingbox = new ModifiedBoundingBox(-1.5,0,-1.5,
				                                                          1.5,5,1.5,
				                                                          0,1.5,0,2.5,3,8);
		nboundingbox.rot.set(baseLogic.bodyRot);
		proxy_HMVehicle.replaceBoundingbox(this,nboundingbox);
		((ModifiedBoundingBox)this.boundingBox).update(this.posX,this.posY,this.posZ);
		applyEntityAttributes2();
	}
	
	public double getMountedYOffset() {
		return 0.6D;
	}
	
	
	public void onUpdate() {
		boolean onground = this.onGround;
		super.onUpdate();
		this.onGround = onground;
		baseLogic.onUpdate();
	}
	
	public void setVelocity(double p_70016_1_, double p_70016_3_, double p_70016_5_) {
//		baseLogic.setVelocity(p_70016_1_,p_70016_3_,p_70016_5_);
	}
	
	@Override
	public int getfirecyclesettings1() {
		return 0;
	}
	
	@Override
	public int getfirecycleprogress1() {
		return 0;
	}
	
	@Override
	public int getfirecyclesettings2() {
		return 0;
	}
	
	@Override
	public int getfirecycleprogress2() {
		return 0;
	}
	
	@Override
	public float getbodyrotationYaw() {
		return baseLogic.bodyrotationYaw;
	}
	
	@Override
	public float getthrottle() {
		return (float) baseLogic.throttle;
	}
	
	@Override
	public void setBodyRot(Quat4d quat4d) {
		baseLogic.bodyRot = quat4d;
	}
	
	@Override
	public void setthrottle(float th) {
		baseLogic.throttle = th;
	}
	
	
	
	@Override
	public int getpilotseatid() {
		return 0;
	}
	
	@Override
	public boolean shouldRenderInPass(int pass) {
		return pass == 1 || pass == 0;
	}
	
	@Override
	public LogicsBase getBaseLogic() {
		return baseLogic;
	}
	
	protected void onDeathUpdate() {
		++this.deathTicks;
		if(this.deathTicks > 40) {
			if (worldObj.isRemote) {
				for (int i = 0; i < 5; i++) {
					worldObj.spawnParticle("smoke",
							this.posX + (float) (rand.nextInt(30) - 15) / 10,
							this.posY + (float) (rand.nextInt(30) - 15) / 10 + 1.5f,
							this.posZ + (float) (rand.nextInt(30) - 15) / 10,
							0.0D, 0.2D, 0.0D);
					worldObj.spawnParticle("cloud",
							this.posX + (float) (rand.nextInt(30) - 15) / 10,
							this.posY + (float) (rand.nextInt(30) - 15) / 10 + 1.5f,
							this.posZ + (float) (rand.nextInt(30) - 15) / 10,
							0.0D, 0.3D, 0.0D);
				}
			}
		}
		if (this.deathTicks >= 140) {
			for (int i = 0; i < 15; i++) {
				worldObj.spawnParticle("flame",
						this.posX + (float) (rand.nextInt(20) - 10) / 10,
						this.posY + (float) (rand.nextInt(20) - 10) / 10,
						this.posZ + (float) (rand.nextInt(20) - 10) / 10,
						(rand.nextInt(20) - 10) / 100,
						(rand.nextInt(20) - 10) / 100,
						(rand.nextInt(20) - 10) / 100 );
				worldObj.spawnParticle("smoke",
						this.posX + (float) (rand.nextInt(30) - 15) / 10,
						this.posY + (float) (rand.nextInt(30) - 15) / 10,
						this.posZ + (float) (rand.nextInt(30) - 15) / 10,
						(rand.nextInt(20) - 10) / 100,
						(rand.nextInt(20) - 10) / 100,
						(rand.nextInt(20) - 10) / 100 );
				worldObj.spawnParticle("cloud",
						this.posX + (float) (rand.nextInt(30) - 15) / 10,
						this.posY + (float) (rand.nextInt(30) - 15) / 10,
						this.posZ + (float) (rand.nextInt(30) - 15) / 10,
						(rand.nextInt(20) - 10) / 100,
						(rand.nextInt(20) - 10) / 100,
						(rand.nextInt(20) - 10) / 100 );
			}
			if(this.deathTicks == 150)
				this.setDead();
		}
	}
	
	public boolean isConverting() {
		return false;
	}
	
	public boolean canBePushed() {
		return true;
	}
	
	public void moveFlying(float p_70060_1_, float p_70060_2_, float p_70060_3_) {
	}
	
	public void jump() {
	
	}
	
	public void moveEntityWithHeading(float p_70612_1_, float p_70612_2_) {
	
	}
	
	protected void collideWithNearbyEntities()
	{
		List list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.expand(0.20000000298023224D, 0.0D, 0.20000000298023224D));
		
		if (list != null && !list.isEmpty())
		{
			for (int i = 0; i < list.size(); ++i)
			{
				Entity entity = (Entity)list.get(i);
				
				if (entity.canBePushed() && entity.width > 1.5)
				{
					this.collideWithEntity(entity);
				}
			}
		}
	}
	
	public void applyEntityCollision(Entity p_70108_1_) {
		if (p_70108_1_.riddenByEntity != this && p_70108_1_.ridingEntity != this && p_70108_1_.width > 1.5) {
			double d0 = p_70108_1_.posX - this.posX;
			double d1 = p_70108_1_.posZ - this.posZ;
			double d2 = MathHelper.abs_max(d0, d1);
			
			if (d2 >= 0.009999999776482582D) {
				d2 = (double) MathHelper.sqrt_double(d2);
				d0 /= d2;
				d1 /= d2;
				double d3 = 1.0D / d2;
				
				if (d3 > 1.0D) {
					d3 = 1.0D;
				}
				
				d0 *= d3;
				d1 *= d3;
				d0 *= 0.05000000074505806D;
				d1 *= 0.05000000074505806D;
				d0 *= (double) (1.0F - this.entityCollisionReduction);
				d1 *= (double) (1.0F - this.entityCollisionReduction);
				this.addVelocity(-d0, 0.0D, -d1);
				p_70108_1_.addVelocity(d0, 0.0D, d1);
			}
		}
	}
	
	public void setPosition(double x, double y, double z)
	{
		super.setPosition(x,y,z);
		if(baseLogic != null)baseLogic.setPosition(x,y,z);
	}
	
	@Override
	public void moveEntity(double x, double y, double z){
		baseLogic.moveEntity(x,y,z);
	}
	
	@Override
	public void updateFallState_public(double stepHeight, boolean onground){
		this.updateFallState(stepHeight,onground);
	}
	
	@Override
	public void func_145775_I_public() {
		this.func_145775_I();
	}
	
}

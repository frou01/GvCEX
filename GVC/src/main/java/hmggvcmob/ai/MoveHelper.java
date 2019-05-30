package hmggvcmob.ai;

import hmvehicle.entity.parts.IVehicle;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

public class MoveHelper {
	private EntityLiving entity;
	private int xPos;
	private int yPos;
	private int zPos;
	private double speed;
	private static final String __OBFID = "CL_00001608";
	private int field_75445_i;
	private double field_151497_i;
	private double field_151495_j;
	private double field_151496_k;
	private int failedPathFindingPenalty;

	public MoveHelper(EntityLiving p_i1648_1_, int x, int y, int z, double p_i1648_2_) {
		this.entity = p_i1648_1_;
		this.speed = p_i1648_2_;
		this.xPos = x;
		this.yPos = y;
		this.zPos = z;
	}
	public MoveHelper(EntityLiving p_i1648_1_, double p_i1648_2_) {
		this.entity = p_i1648_1_;
		this.speed = p_i1648_2_;
	}

	private Block getBlocks(int par1, int par2, int par3) {
		return this.entity.worldObj.getBlock(par1, par2, par3);
	}

	public void go() {
		EntityLivingBase entitylivingbase = this.entity.getAttackTarget();
		if (entitylivingbase != null) {
			this.entity.getLookHelper().setLookPositionWithEntity(entitylivingbase, 30.0F, 30.0F);
			double d0 = this.entity.getDistanceSq(entitylivingbase.posX, entitylivingbase.boundingBox.minY, entitylivingbase.posZ);
			double d1 = (double) (this.entity.width * 2.0F * this.entity.width * 2.0F + entitylivingbase.width);
			--this.field_75445_i;
			if ((this.entity.getEntitySenses().canSee(entitylivingbase)) && this.field_75445_i <= 0 && (this.field_151497_i == 0.0D && this.field_151495_j == 0.0D && this.field_151496_k == 0.0D || entitylivingbase.getDistanceSq(this.field_151497_i, this.field_151495_j, this.field_151496_k) >= 1.0D || this.entity.getRNG().nextFloat() < 0.05F)) {
				this.field_151497_i = entitylivingbase.posX;
				this.field_151495_j = entitylivingbase.boundingBox.minY;
				this.field_151496_k = entitylivingbase.posZ;
				this.field_75445_i = failedPathFindingPenalty + 4 + this.entity.getRNG().nextInt(7);

				if (this.entity.getNavigator().getPath() != null) {
					PathPoint finalPathPoint = this.entity.getNavigator().getPath().getFinalPathPoint();
					if (finalPathPoint != null && entitylivingbase.getDistanceSq(finalPathPoint.xCoord, finalPathPoint.yCoord, finalPathPoint.zCoord) < 1) {
						failedPathFindingPenalty = 0;
					} else {
						failedPathFindingPenalty += 10;
					}
				} else {
					failedPathFindingPenalty += 10;
				}
				if (d0 > 1024.0D) {
					this.field_75445_i += 10;
				} else if (d0 > 256.0D) {
					this.field_75445_i += 5;
				}

				if (!this.entity.getNavigator().tryMoveToEntityLiving(entitylivingbase, 2)) {
					this.field_75445_i += 15;
				}
			}
		}
	}

	private void move(Vec3 look, int i) {
		float f1 = this.entity.rotationYawHead * (2 * (float) Math.PI / 360);
		if (i == 1) {
			// this.entity.motionX += MathHelper.sin(this.entity.rotationYawHead
			// * 0.01745329252F - 1.8F) * (this.speed * 0.05);
			// this.entity.motionZ -= MathHelper.cos(this.entity.rotationYawHead
			// * 0.01745329252F - 1.8F) * (this.speed * 0.05);
			entity.motionX -= MathHelper.sin(f1 - 1.8F) * speed * 0.002;
			entity.motionZ += MathHelper.cos(f1 - 1.8F) * speed * 0.002;
		} else if (i == 2) {
			entity.motionX -= MathHelper.sin(f1 + 1.8F) * speed * 0.002;
			entity.motionZ += MathHelper.cos(f1 + 1.8F) * speed * 0.002;
		} else if (i == 3) {
			entity.motionX += MathHelper.sin(f1) * speed * 0.05;
			entity.motionZ -= MathHelper.cos(f1) * speed * 0.05;
		} else {
			// this.entity.motionX = look.xCoord * (this.speed * 0.1);
			// this.entity.motionZ = look.zCoord * (this.speed * 0.1);
			entity.motionX -= MathHelper.sin(f1) * speed * 0.05;
			entity.motionZ += MathHelper.cos(f1) * speed * 0.05;
		}
	}

	public void gotank() {
		float f1 = ((IVehicle)entity).getbodyrotationYaw() * (2 * (float) Math.PI / 360);
		this.entity.moveEntity(-MathHelper.sin(f1) * speed * 0.07,0,+MathHelper.cos(f1) * speed * 0.07);
	}
	public void goship() {
		double d5 = xPos - entity.posX;
		double d7 = zPos - entity.posZ;
		double d6 = yPos - entity.posY;
		double d1 = entity.posY - (yPos);
		double d3 = (double) MathHelper.sqrt_double(d5 * d5 + d7 * d7);
		float f11 = (float) (-(Math.atan2(d1, d3) * 180.0D / Math.PI));

		double ddx = Math.abs(d5);
		double ddz = Math.abs(d7);
		double ix = 0;
		double iz = 0;
		double iy = this.entity.motionY;
		float f1 = this.entity.rotationYawHead * (2 * (float) Math.PI / 360);
		float f2 = this.entity.rotationPitch * (2 * (float) Math.PI / 360);
		Vec3 look = this.entity.getLookVec();
		{
			entity.motionX -= MathHelper.sin(f1) * speed * 0.05;
			entity.motionZ += MathHelper.cos(f1) * speed * 0.05;
		}
	}
	public void setspeed(double value){
		speed = value;
	}
}
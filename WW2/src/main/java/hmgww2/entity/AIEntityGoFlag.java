package hmgww2.entity;

import gvclib.entity.EntityBases;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

public class AIEntityGoFlag {
	private EntityBases entity;
	private int xPos;
	private int yPos;
	private int zPos;
	private double speed;
	private static final String __OBFID = "CL_00001608";

	public AIEntityGoFlag(EntityBases p_i1648_1_, int x, int y, int z, double p_i1648_2_) {
		this.entity = p_i1648_1_;
		this.speed = p_i1648_2_;
		this.xPos = x;
		this.yPos = y;
		this.zPos = z;
	}

	private Block getBlocks(int par1, int par2, int par3) {
		return this.entity.worldObj.getBlock(par1, par2, par3);
	}

	public void go() {
		double d5 = xPos - entity.posX;
		double d7 = zPos - entity.posZ;
		double d6 = yPos - entity.posY;
		double d1 = entity.posY - (yPos);
		double d3 = (double) MathHelper.sqrt_double(d5 * d5 + d7 * d7);
		float f11 = (float) (-(Math.atan2(d1, d3) * 180.0D / Math.PI));
		entity.rotationYawHead = entity.renderYawOffset = entity.rotation = entity.rote = -((float) Math.atan2(d5, d7))
				* 180.0F / (float) Math.PI;
		// entity.rotationp = entity.rotationPitch = -f11 + 10;

		double ddx = Math.abs(d5);
		double ddz = Math.abs(d7);
		double ix = 0;
		double iz = 0;
		double iy = this.entity.motionY;
		float f1 = this.entity.rotationYawHead * (2 * (float) Math.PI / 360);
		float f2 = this.entity.rotationPitch * (2 * (float) Math.PI / 360);
		Vec3 look = this.entity.getLookVec();
		{
			// entity.motionX -= MathHelper.sin(f1) * speed * 0.05;
			// entity.motionZ += MathHelper.cos(f1) * speed * 0.05;
		}

		ix -= MathHelper.sin(f1) * 0.9;//1.05
		iz += MathHelper.cos(f1) * 0.9;//1.05
		
		int ii = MathHelper.floor_double(this.entity.posX);
		int ij = MathHelper.floor_double(this.entity.posZ);
		int ik = MathHelper.floor_double(this.entity.boundingBox.minY);

		if (entity.isCollidedHorizontally) {
			entity.motionY = 0.2D;
		}
		// 溝
		{
			this.move(look, 0);
		}
		
	}

	private void move(Vec3 look, int i) {
		float f1 = this.entity.rotationYawHead * (2 * (float) Math.PI / 360);
		double mx = 0;
		double mz = 0;
		if (i == 1) {
			// this.entity.motionX += MathHelper.sin(this.entity.rotationYawHead
			// * 0.01745329252F - 1.8F) * (this.speed * 0.05);
			// this.entity.motionZ -= MathHelper.cos(this.entity.rotationYawHead
			// * 0.01745329252F - 1.8F) * (this.speed * 0.05);
			mx -= MathHelper.sin(f1 - 1.8F) * speed * 0.02;
			mz += MathHelper.cos(f1 - 1.8F) * speed * 0.02;
		} else if (i == 2) {
			mx -= MathHelper.sin(f1 + 1.8F) * speed * 0.02;
			mz += MathHelper.cos(f1 + 1.8F) * speed * 0.02;
		} else if (i == 3) {
			mx += MathHelper.sin(f1) * speed * 0.1;
			mz -= MathHelper.cos(f1) * speed * 0.1;
		} else {
			// this.entity.motionX = look.xCoord * (this.speed * 0.1);
			// this.entity.motionZ = look.zCoord * (this.speed * 0.1);
			mx -= MathHelper.sin(f1) * speed * 0.1;
			mz += MathHelper.cos(f1) * speed * 0.1;
		}
		if(!entity.worldObj.isRemote){
			entity.moveEntity(mx, entity.motionY, mz);
		}
	}

	public void gotank() {
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
		double mx = 0;
		double mz = 0;
		{
			mx -= MathHelper.sin(f1) * speed * 0.1;
			mz += MathHelper.cos(f1) * speed * 0.1;
		}
		if (entity.isCollidedHorizontally) {
			entity.motionY = 0.2D;
		}
		/*if(!entity.worldObj.isRemote){
			entity.moveEntity(mx, entity.motionY, mz);
		}*/
		 if(entity.throttle < entity.thmax * 0.75f){
			 entity.throttle = entity.throttle + entity.thmaxa;
			}
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

	
	public void gotest() {
		double d5 = xPos - entity.posX;
		double d7 = zPos - entity.posZ;
		double d6 = yPos - entity.posY;
		double d1 = entity.posY - (yPos);
		double d3 = (double) MathHelper.sqrt_double(d5 * d5 + d7 * d7);
		float f11 = (float) (-(Math.atan2(d1, d3) * 180.0D / Math.PI));
		entity.rotationYawHead = entity.renderYawOffset = entity.rotation = entity.rote = -((float) Math.atan2(d5, d7))
				* 180.0F / (float) Math.PI;
		// entity.rotationp = entity.rotationPitch = -f11 + 10;

		double ddx = Math.abs(d5);
		double ddz = Math.abs(d7);
		double ix = 0;
		double iz = 0;
		double iy = this.entity.motionY;
		float f1 = this.entity.rotationYawHead * (2 * (float) Math.PI / 360);
		float f2 = this.entity.rotationPitch * (2 * (float) Math.PI / 360);
		Vec3 look = this.entity.getLookVec();
		{
			// entity.motionX -= MathHelper.sin(f1) * speed * 0.05;
			// entity.motionZ += MathHelper.cos(f1) * speed * 0.05;
		}

		ix -= MathHelper.sin(f1) * 0.9;//1.05
		iz += MathHelper.cos(f1) * 0.9;//1.05
		
		int ii = MathHelper.floor_double(this.entity.posX);
		int ij = MathHelper.floor_double(this.entity.posZ);
		int ik = MathHelper.floor_double(this.entity.boundingBox.minY);

		// 溝
		if (this.getBlocks(ii + (int) ix, ik - 3, ij + (int) iz) == Blocks.air
				&& this.getBlocks(ii + (int) ix, ik - 2, ij + (int) iz) == Blocks.air) {

			double ixx = 0;
			double izz = 0;
			double ixx2 = 0;
			double izz2 = 0;
			for (int i = 1; i < 6; ++i) {
				ixx -= MathHelper.sin(f1 - 1.0F) * i;
				izz += MathHelper.cos(f1 - 1.0F) * i;
				ixx2 -= MathHelper.sin(f1 + 1.0F) * i;
				izz2 += MathHelper.cos(f1 + 1.0F) * i;
				if (this.entity.worldObj.getBlock(ii + (int) ixx, ik - 2, ij + (int) izz) != Blocks.air) {
					this.move(look, 2);
					break;
				} else if (this.entity.worldObj.getBlock(ii + (int) ixx2, ik - 2, ij + (int) izz2) != Blocks.air) {
					this.move(look, 1);
					break;
				} else {
					if (this.entity.worldObj.getBlock(ii + (int) ix, ik - 1, ij + (int) iz) == Blocks.air
							&& (ddx > 8 || ddz > 8) && this.entity.onGround) {
						this.entity.worldObj.setBlock(ii + (int) ix, ik - 1, ij + (int) iz, Blocks.planks, 0, 2);
					}
					this.move(look, 0);
				}
			}

		}
		// 一段段差
		else if (this.entity.worldObj.getBlock(ii + (int) ix, ik + 0, ij + (int) iz) != Blocks.air
				&& !(this.entity.worldObj.getBlock(ii + (int) ix, ik + 1, ij + (int) iz) != Blocks.air)
				&& !(this.entity.worldObj.getBlock(ii + (int) ix, ik + 2, ij + (int) iz) != Blocks.air)) {
			this.entity.motionY = 0.3D;
			this.move(look, 0);
			// this.trymoveside(ix, iz);
		}
		// 2段段差
		else if (// this.entity.worldObj.getBlock((int)this.entity.posX +
					// (int)ix, (int)this.entity.posY + 0,(int) this.entity.posZ
					// + (int)iz)!= Blocks.air &&
		this.entity.worldObj.getBlock(ii + (int) ix, ik + 1, ij + (int) iz) != Blocks.air) {
			double ixx = 0;
			double izz = 0;
			double ixx2 = 0;
			double izz2 = 0;
			for (int i = 0; i < 6; ++i) {
				ixx -= MathHelper.sin(f1 - 1.3F) * i;
				izz += MathHelper.cos(f1 - 1.3F) * i;
				ixx2 -= MathHelper.sin(f1 + 1.3F) * i;
				izz2 += MathHelper.cos(f1 + 1.3F) * i;

				if (this.entity.worldObj.getBlock(ii + (int) ixx, ik + 1, ij + (int) izz) == Blocks.air
						&& this.entity.worldObj.getBlock(ii + (int) ixx, ik + 2, ij + (int) izz) == Blocks.air) {
					this.move(look, 2);
					break;
				} else if (this.entity.worldObj.getBlock(ii + (int) ixx2, ik + 1, ij + (int) izz2) == Blocks.air
						&& this.entity.worldObj.getBlock(ii + (int) ixx2, ik + 2, ij + (int) izz2) == Blocks.air) {
					this.move(look, 1);
					break;
				} else {
					this.entity.motionY = 0.4D;
					if (this.entity.worldObj.getBlock(ii, ik + 0, ij) == Blocks.air && (ddx > 8 || ddz > 8)
							&& this.entity.onGround) {
						this.entity.worldObj.setBlock(ii, ik + 0, ij, Blocks.planks, 0, 2);
					}
					this.move(look, 0);
				}
			}

			if (this.entity.worldObj.rand.nextInt(20) == 0) {
				// this.move(look, 3);
			}
			

		} else {
			this.move(look, 0);
		}
		if (entity.isCollidedHorizontally) {
			entity.motionY = 0.2D;
		}
	}
}
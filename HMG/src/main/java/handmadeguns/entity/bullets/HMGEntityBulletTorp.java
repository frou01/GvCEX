package handmadeguns.entity.bullets;


import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;
import handmadeguns.network.PacketSpawnParticle;
import io.netty.buffer.ByteBuf;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import static handmadeguns.HandmadeGunsCore.cfg_defgravitycof;
import static handmadeguns.HandmadeGunsCore.proxy;

public class HMGEntityBulletTorp extends HMGEntityBulletExprode implements IEntityAdditionalSpawnData
{
	public double draft = 0;
	public HMGEntityBulletTorp(World worldIn) {
		super(worldIn);
	}
	public HMGEntityBulletTorp(World worldIn, Entity throwerIn, int damege, float bspeed, float bure) {
		super(worldIn, throwerIn, damege, bspeed, bure);
		this.canbounce = false;
		this.bouncerate = 0.1f;
	}
	public HMGEntityBulletTorp(World worldIn, Entity throwerIn, int damege, float bspeed, float bure, float exl, boolean canex) {
		this(worldIn, throwerIn, damege, bspeed, bure);
		exlevel = exl;
		this.canex = canex;
		this.canbounce = false;
		this.bouncerate = 0.1f;
	}
	public HMGEntityBulletTorp(World worldIn, Entity throwerIn, int damege, float bspeed, float bure, float exl, boolean canex, String modelname) {
		super(worldIn, throwerIn, damege, bspeed, bure,exl,canex, modelname);
		exlevel = exl;
		this.canex = canex;
		this.canbounce = false;
		this.bouncerate = 0.1f;
	}
	public HMGEntityBulletTorp(World worldIn, double x, double y, double z) {
		super(worldIn, x, y, z);
	}

	public void onUpdate(){
		super.onUpdate();
		resistanceinwater = 1;
		if(isInWater()) {
			if (worldObj.isRemote && smoketexture == null) {
				PacketSpawnParticle packet = new PacketSpawnParticle(posX, posY, posZ, -this.motionX / 8,
						                                                    -this.motionY / 8,
						                                                    -this.motionZ / 8, 1);
				packet.scale = smokeWidth;
				packet.fuse = smoketime;
				if (smokeglow) packet.id += 100;
				proxy.spawnParticles(packet);
			}
		}
		if(ishittingWater()) {
			//下降を停止させる
			this.motionY *= 0.75;
			if(handleWaterMovement() && this.motionY<0.1) {
				this.motionY += (double) gra * cfg_defgravitycof*1.1;
			}
			double f2 = getspeed();
			if (f2 > 0) {
				this.motionX += motionX / f2 * acceleration;
				this.motionY += motionY / f2 * acceleration;
				this.motionZ += motionZ / f2 * acceleration;
//                worldObj.playSoundAtEntity(this, "handmadeguns:handmadeguns." + flyingSound,flyingSoundLV, flyingSoundSP);
			}
		}
	}
	@Override
	public boolean handleWaterMovement()
	{
		if (this.worldObj.handleMaterialAcceleration(this.boundingBox.expand(1.0D, 1.0D, 1.0D).offset(0,draft,0), Material.water, this))
		{
			this.inWater = true;
			extinguish();
		}
		else
		{
			this.inWater = false;
		}
		
		return this.inWater;
	}
	public boolean ishittingWater()
	{
		boolean inWater = false;
		inWater = this.worldObj.handleMaterialAcceleration(this.boundingBox.expand(1.0D, 1.0D, 1.0D).offset(0,0, 0), Material.water, this);
		
		return inWater;
	}
	public boolean changeVector(){
		super.changeVector();
		return true;
	}
	public void writeSpawnData(ByteBuf buffer){
		super.writeSpawnData(buffer);
		buffer.writeDouble(draft);
	}
	public void readSpawnData(ByteBuf additionalData){
		super.readSpawnData(additionalData);
		draft = additionalData.readDouble();
	}
}

package handmadevehicle.audio;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import handmadevehicle.entity.parts.turrets.TurretObj;
import net.minecraft.client.audio.MovingSound;
import net.minecraft.util.ResourceLocation;

import javax.vecmath.Vector3d;

import static handmadeguns.HandmadeGunsCore.HMG_proxy;
import static java.lang.Math.sqrt;

@SideOnly(Side.CLIENT)
public class TurretSound extends MovingSound
{
	private final TurretObj attachedturret;
	private float maxdist;
	private double disttoPlayer = -1;
	
	public TurretSound(TurretObj p_i45105_1_, float maxdist)
	{
		super(new ResourceLocation(p_i45105_1_.getsound()));
		this.attachedturret = p_i45105_1_;
		this.repeat = true;
		this.field_147665_h = 0;
		this.maxdist = maxdist;
		volume = 1;
	}
	
	/**
	 * Updates the JList with a new model.
	 */
	public void update()
	{
		if (attachedturret.currentEntity ==null || this.attachedturret.currentEntity.isDead || (this.attachedturret.motherEntity != null && this.attachedturret.motherEntity.isDead))
		{
			this.donePlaying = true;
		}
		else
		{
			attachedturret.yourSoundIsremain();
			this.xPosF = (float)this.attachedturret.motherPos.x;
			this.yPosF = (float)this.attachedturret.motherPos.y;
			this.zPosF = (float)this.attachedturret.motherPos.z;
			double prevdisttoPlayer = disttoPlayer;
			disttoPlayer = attachedturret.currentEntity.getDistanceSqToEntity(HMG_proxy.getMCInstance().renderViewEntity);
			float soundpitch = attachedturret.getsoundPitch();
			this.field_147663_c = 0.0F;
			volume = 4;
			
			if (disttoPlayer < maxdist * maxdist) {
				
				if(disttoPlayer > volume * volume)volume = (float) (sqrt(disttoPlayer));
				
				Vector3d playerPos = new Vector3d(HMG_proxy.getMCInstance().renderViewEntity.posX, HMG_proxy.getMCInstance().renderViewEntity.posY, HMG_proxy.getMCInstance().renderViewEntity.posZ);
				Vector3d thisPos = new Vector3d(xPosF,yPosF,zPosF);
				Vector3d toPlayerVec = new Vector3d();
				toPlayerVec.sub(playerPos,thisPos);
				toPlayerVec.normalize();
				toPlayerVec.scale(10);
				thisPos.set(playerPos);
				thisPos.add(toPlayerVec);
				this.xPosF = (float) thisPos.x;
				this.yPosF = (float) thisPos.y;
				this.zPosF = (float) thisPos.z;
				if (prevdisttoPlayer != -1) {
					float doppler = (float) (sqrt(prevdisttoPlayer) - sqrt(disttoPlayer));
					float tempsp = (318.8f / (318.8f - doppler * 20f));
					field_147663_c = soundpitch * tempsp;
				}
				if(field_147663_c<0.1){
					this.field_147663_c = 0.0F;
					this.volume = 0.0F;
				}
			}else {
				this.field_147663_c = 0.0F;
				this.volume = 0.0F;
			}
		}
	}
}
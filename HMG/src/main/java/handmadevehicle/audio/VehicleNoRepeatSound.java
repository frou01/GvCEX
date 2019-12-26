package handmadevehicle.audio;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import handmadevehicle.entity.parts.HasLoopSound;
import net.minecraft.client.audio.MovingSound;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

import javax.vecmath.Vector3d;

import static handmadeguns.HandmadeGunsCore.HMG_proxy;
import static java.lang.Math.sqrt;

@SideOnly(Side.CLIENT)
public class VehicleNoRepeatSound extends MovingSound
{
	private final Entity attachedEntity;
	private final HasLoopSound hasLoopSound;
	private final String sound;
	private float maxdist;
	private double disttoPlayer = -1;
	private int tick = 0;
	private int tickMax = 0;
	
	public VehicleNoRepeatSound(String soundname , Entity p_i45105_1_, HasLoopSound hasLoopSound, float maxdist, int time)
	{
		super(new ResourceLocation(soundname));
		this.sound = soundname;
		this.attachedEntity = p_i45105_1_;
		this.repeat = false;
		this.field_147665_h = 0;
		this.maxdist = maxdist;
		this.hasLoopSound = hasLoopSound;
		volume = 4;
		tickMax = time;
	}
	
	/**
	 * Updates the JList with a new model.
	 */
	public void update()
	{
		{
			tick++;
			if(tick > tickMax){
				donePlaying = true;
				return;
			}
			this.xPosF = (float)this.attachedEntity.posX;
			this.yPosF = (float)this.attachedEntity.posY;
			this.zPosF = (float)this.attachedEntity.posZ;
			double prevdisttoPlayer = disttoPlayer;
			disttoPlayer = attachedEntity.getDistanceSqToEntity(HMG_proxy.getMCInstance().renderViewEntity);
			hasLoopSound.yourSoundIsremain(sound);
			float soundpitch = 1;
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
				if(field_147663_c<0.01){
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
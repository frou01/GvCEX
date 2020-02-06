package handmadeguns.client.audio;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import handmadeguns.entity.bullets.HMGEntityBulletBase;
import net.minecraft.client.audio.MovingSound;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

import static handmadeguns.HandmadeGunsCore.HMG_proxy;
import static java.lang.Math.min;
import static java.lang.Math.sqrt;

@SideOnly(Side.CLIENT)
public class BulletSoundHMG extends MovingSound
{
	private final Entity attachedEntity;
	private double disttoPlayer = -1;
	private float savedfield_147663_c;
	private float minspeed;
	private float maxdist;
	protected float primeVolume = 1.0F;
	
	public BulletSoundHMG(Entity p_i45105_1_, String soundName, boolean repeat, float soundLV, float soundSP,float minspeed,float maxdist)
	{
		super(new ResourceLocation(soundName));
		this.attachedEntity = p_i45105_1_;
		this.repeat = repeat;
		this.field_147665_h = 0;
		this.savedfield_147663_c = this.field_147663_c = soundSP;
		this.volume = soundLV;
		this.primeVolume = soundLV;
		this.minspeed = minspeed;
		this.maxdist = maxdist;

		update();
		disttoPlayer = -1;
	}
	
	/**
	 * Updates the JList with a new model.
	 */
	public void update()
	{
		if (this.attachedEntity.isDead)
		{
			this.donePlaying = true;
			this.repeat = false;
		}
		else
		{
			this.volume = this.primeVolume;
			Entity renderViewEntity = HMG_proxy.getMCInstance().renderViewEntity;
			double prevdisttoPlayer = disttoPlayer;
			disttoPlayer = attachedEntity.getDistanceSqToEntity(HMG_proxy.getMCInstance().renderViewEntity);
			this.xPosF = (float)(renderViewEntity.posX + (this.attachedEntity.posX - renderViewEntity.posX)/sqrt(disttoPlayer)*8);
			this.yPosF = (float)(renderViewEntity.posY + renderViewEntity.getEyeHeight() + (this.attachedEntity.posY + attachedEntity.getEyeHeight() - renderViewEntity.posY - renderViewEntity.getEyeHeight())/sqrt(disttoPlayer)*8);
			this.zPosF = (float)(renderViewEntity.posZ + (this.attachedEntity.posZ - renderViewEntity.posZ)/sqrt(disttoPlayer)*8);
			if(attachedEntity.motionX * attachedEntity.motionX + attachedEntity.motionY * attachedEntity.motionY + attachedEntity.motionZ * attachedEntity.motionZ < minspeed)
				this.repeat = false;

			if (disttoPlayer < maxdist * maxdist) {
				if(disttoPlayer > 256){
					volume /=disttoPlayer/256;
				}
				if (!HMG_proxy.getMCInstance().renderViewEntity.canEntityBeSeen(attachedEntity)) volume /= 32;
				if (prevdisttoPlayer != -1) {
					float doppler = (float) (sqrt(prevdisttoPlayer) - sqrt(disttoPlayer));
					float tempsp = (318.8f / (318.8f - doppler * 20f));
					field_147663_c = savedfield_147663_c * tempsp;
				}
			}else {
				this.donePlaying = true;
				this.repeat = false;
				this.volume = 0;
			}
		}
		if(!this.repeat && attachedEntity instanceof HMGEntityBulletBase)((HMGEntityBulletBase) attachedEntity).soundstoped = true;
		if(attachedEntity instanceof HMGEntityBulletBase && !(((HMGEntityBulletBase)attachedEntity).flyingSoundInfo != null && ((HMGEntityBulletBase)attachedEntity).motionX * ((HMGEntityBulletBase)attachedEntity).motionX + ((HMGEntityBulletBase)attachedEntity).motionY * ((HMGEntityBulletBase)attachedEntity).motionY + ((HMGEntityBulletBase)attachedEntity).motionZ * ((HMGEntityBulletBase)attachedEntity).motionZ > ((HMGEntityBulletBase)attachedEntity).flyingSoundInfo.MinBltSP * ((HMGEntityBulletBase)attachedEntity).flyingSoundInfo.MinBltSP && ((HMGEntityBulletBase)attachedEntity).getDistanceSqToEntity(HMG_proxy.getEntityPlayerInstance()) < ((HMGEntityBulletBase)attachedEntity).flyingSoundInfo.MaxDist*((HMGEntityBulletBase)attachedEntity).flyingSoundInfo.MaxDist)) {
			this.donePlaying = true;
			this.repeat = false;
			((HMGEntityBulletBase) attachedEntity).soundstoped = true;
		}
	}
}
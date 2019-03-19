package handmadeguns.client.audio;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.audio.MovingSound;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

import static handmadeguns.HandmadeGunsCore.proxy;
import static java.lang.Math.min;
import static java.lang.Math.sqrt;

@SideOnly(Side.CLIENT)
public class BulletSoundHMG extends MovingSound
{
	private final Entity attachedEntity;
	private static final String __OBFID = "CL_00001118";
	private double disttoPlayer = -1;
	private float savedfield_147663_c;
	private float minspeed;
	private float maxdist;
	
	public BulletSoundHMG(Entity p_i45105_1_, String soundName, boolean repeat, float soundLV, float soundSP,float minspeed,float maxdist)
	{
		super(new ResourceLocation(soundName));
		this.attachedEntity = p_i45105_1_;
		this.repeat = repeat;
		this.field_147665_h = 0;
		this.savedfield_147663_c = this.field_147663_c = soundSP;
		this.volume = soundLV;
		this.minspeed = minspeed;
		this.maxdist = maxdist;
	}
	
	/**
	 * Updates the JList with a new model.
	 */
	public void update()
	{
		if (this.attachedEntity.isDead)
		{
			this.donePlaying = true;
		}
		else
		{
			double prevdisttoPlayer = disttoPlayer;
			disttoPlayer = attachedEntity.getDistanceSqToEntity(proxy.getEntityPlayerInstance());
			if(attachedEntity.motionX * attachedEntity.motionX + attachedEntity.motionY * attachedEntity.motionY + attachedEntity.motionZ * attachedEntity.motionZ < minspeed)volume = 0;
			else {
				if (disttoPlayer < maxdist) {
					if(disttoPlayer > volume*16 * volume*16)volume = (float) (sqrt(disttoPlayer)/16);
					if (prevdisttoPlayer != -1) {
						float doppler = (float) (sqrt(prevdisttoPlayer) - sqrt(disttoPlayer));
						float tempsp = (318.8f / (318.8f - doppler * 20f));
						field_147663_c = savedfield_147663_c * tempsp;
					}
				}
				this.xPosF = (float) this.attachedEntity.posX;
				this.yPosF = (float) this.attachedEntity.posY;
				this.zPosF = (float) this.attachedEntity.posZ;
			}
		}
	}
}
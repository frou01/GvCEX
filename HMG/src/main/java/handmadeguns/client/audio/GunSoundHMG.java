package handmadeguns.client.audio;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import handmadeguns.entity.bullets.HMGEntityBulletBase;
import net.minecraft.client.audio.MovingSound;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

import static handmadeguns.HandmadeGunsCore.HMG_proxy;
import static java.lang.Math.sqrt;

@SideOnly(Side.CLIENT)
public class GunSoundHMG extends MovingSound
{
	private final Entity attachedEntity;
	private double disttoPlayer = -1;
	private float savedfield_147663_c;
	private float maxdist;
	private double primePosX;
	private double primePosY;
	private double primePosZ;
	protected float primeVolume;
	private final int maxTick = 200;
	private int tick = 0;

	public GunSoundHMG(Entity p_i45105_1_, String soundName, float soundLV, float soundSP, float maxdist,
	                   double primePosX,
	                   double primePosY,
	                   double primePosZ)
	{
		super(new ResourceLocation(soundName));
		this.attachedEntity = p_i45105_1_;
		this.repeat = false;
		this.field_147665_h = 0;
		this.savedfield_147663_c = this.field_147663_c = soundSP;
		this.volume = soundLV;
		this.primeVolume = soundLV;
		this.maxdist = maxdist;
		this.primePosX = primePosX;
		this.primePosY = primePosY;
		this.primePosZ = primePosZ;
		update();
	}
	
	/**
	 * Updates the JList with a new model.
	 */
	public void update()
	{
		if (this.attachedEntity.isDead || tick>maxTick)
		{
			this.donePlaying = true;
		}
		else
		{
			Entity renderViewEntity = HMG_proxy.getMCInstance().renderViewEntity;
			double prevdisttoPlayer = disttoPlayer;
			disttoPlayer = HMG_proxy.getMCInstance().renderViewEntity.getDistanceSq(primePosX,
					primePosY,
					primePosZ);
			if(disttoPlayer>64) {
				this.xPosF = (float) (renderViewEntity.posX + (primePosX - renderViewEntity.posX) / sqrt(disttoPlayer) * 8);
				this.yPosF = (float) (renderViewEntity.posY + (primePosY - renderViewEntity.posY) / sqrt(disttoPlayer) * 8);
				this.zPosF = (float) (renderViewEntity.posZ + (primePosZ - renderViewEntity.posZ) / sqrt(disttoPlayer) * 8);
			}else {
				this.xPosF = (float) (primePosX);
				this.yPosF = (float) (primePosY);
				this.zPosF = (float) (primePosZ);
			}
			float soundpitch = savedfield_147663_c;
			this.field_147663_c = soundpitch;
			volume = 8;

			if (disttoPlayer < maxdist * maxdist) {

				if(disttoPlayer > 1024){
					volume /=disttoPlayer/1024;
				}
				if(!HMG_proxy.getMCInstance().renderViewEntity.canEntityBeSeen(attachedEntity))volume /=32;
				if (prevdisttoPlayer != -1) {
					float doppler = (float) (sqrt(prevdisttoPlayer) - sqrt(disttoPlayer));
					float tempsp = (318.8f / (318.8f - doppler * 20f));
					field_147663_c = soundpitch * tempsp;
				}
				if (field_147663_c < 0.01) {
					this.field_147663_c = 0.0F;
					this.volume = 0.0F;
				}
			} else {
				this.field_147663_c = 0.0F;
				this.volume = 0.0F;
			}
			tick++;
		}
	}
}
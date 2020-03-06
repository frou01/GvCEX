package handmadevehicle.audio;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import handmadeguns.event.RenderTickSmoothing;
import handmadevehicle.entity.parts.turrets.TurretObj;
import net.minecraft.client.audio.MovingSound;
import net.minecraft.entity.Entity;
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
	private String sound;
	private boolean killer = false;

	public TurretSound(TurretObj p_i45105_1_, float maxdist)
	{
		super(new ResourceLocation(p_i45105_1_.getsound()));
		sound = p_i45105_1_.getsound();
		this.attachedturret = p_i45105_1_;
		this.repeat = true;
		this.field_147665_h = 0;
		this.maxdist = maxdist;
		update();
	}
	
	/**
	 * Updates the JList with a new model.
	 */
	public void update()
	{
		if (killer || attachedturret.motherEntity == null || this.attachedturret.motherEntity.isDead || attachedturret.turretMoving == 0 ||!(this.attachedturret.gunItem != null && this.attachedturret.gunItem.gunInfo.turretspeedY > 0 && this.attachedturret.gunItem.gunInfo.turretspeedP > 0))
		{
			this.donePlaying = true;
		}
		else
		{
			attachedturret.yourSoundIsremain(sound);
			if(sound == attachedturret.getsound()) {//参照渡しだから問題ないはず

				Entity renderViewEntity = HMG_proxy.getMCInstance().renderViewEntity;
				double prevdisttoPlayer = disttoPlayer;
				disttoPlayer = HMG_proxy.getMCInstance().renderViewEntity.getDistanceSq(attachedturret.pos.x,attachedturret.pos.y,-attachedturret.pos.z);
				if(disttoPlayer>64) {
					this.xPosF = (float) (renderViewEntity.posX + (attachedturret.pos.x - renderViewEntity.posX) / sqrt(disttoPlayer) * 8);
					this.yPosF = (float) (renderViewEntity.posY + (attachedturret.pos.y - renderViewEntity.posY) / sqrt(disttoPlayer) * 8);
					this.zPosF = (float) (renderViewEntity.posZ + (-attachedturret.pos.z - renderViewEntity.posZ) / sqrt(disttoPlayer) * 8);
				}else {
					this.xPosF = (float) (attachedturret.pos.x);
					this.yPosF = (float) (attachedturret.pos.y);
					this.zPosF = (float) (-attachedturret.pos.z);
				}
				float soundpitch = attachedturret.getsoundPitch();
				this.field_147663_c = soundpitch;
				volume = 4;

				if (disttoPlayer < maxdist * maxdist) {

					if(disttoPlayer > 256){
						volume /=disttoPlayer/256;
					}
					if(!HMG_proxy.getMCInstance().renderViewEntity.canEntityBeSeen(attachedturret.motherEntity))volume /=32;
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
			}else {
				killer = true;
			}
		}
	}
}
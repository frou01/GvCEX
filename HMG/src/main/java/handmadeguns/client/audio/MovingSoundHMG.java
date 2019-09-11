package handmadeguns.client.audio;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.audio.MovingSound;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

import static handmadeguns.HandmadeGunsCore.HMG_proxy;
import static java.lang.Math.sqrt;

@SideOnly(Side.CLIENT)
public class MovingSoundHMG extends MovingSound
{
	private final Entity attachedEntity;
	private static final String __OBFID = "CL_00001118";
	public double disttoPlayer = -1;
	public double prevdisttoPlayer = -1;
	public float savedfield_147663_c;
	private int tick = 0;
	private int tickMax = 0;
	
	public MovingSoundHMG(Entity p_i45105_1_,String soundName,boolean repeat,float soundLV,float soundSP,int tickMax)
	{
		super(new ResourceLocation(soundName));
		this.attachedEntity = p_i45105_1_;
		this.repeat = repeat;
		this.field_147665_h = 0;
		this.savedfield_147663_c = this.field_147663_c = soundSP;
		this.volume = soundLV;
		this.tickMax = tickMax;
	}
	
	/**
	 * Updates the JList with a new model.
	 */
	public void update()
	{
		tick++;
		if(tick > tickMax){
			donePlaying = true;
			return;
		}
		if (this.attachedEntity.isDead)
		{
			this.donePlaying = true;
		}
		else
		{
			prevdisttoPlayer = disttoPlayer;
			disttoPlayer = attachedEntity.getDistanceSqToEntity(HMG_proxy.getMCInstance().renderViewEntity);
			if(prevdisttoPlayer != -1) {
				float doppler = (float) (sqrt(prevdisttoPlayer) - sqrt(disttoPlayer));
				float tempsp =  (318.8f / (318.8f - doppler * 20f));
				field_147663_c = savedfield_147663_c * tempsp;
			}
			this.xPosF = (float) this.attachedEntity.posX;
			this.yPosF = (float) this.attachedEntity.posY;
			this.zPosF = (float) this.attachedEntity.posZ;
		}
	}
}
package handmadeguns.client.audio;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.audio.MovingSound;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import static handmadeguns.HandmadeGunsCore.proxy;
import static java.lang.Math.sqrt;

@SideOnly(Side.CLIENT)
public class ReloadSoundHMG extends MovingSound
{
	private final Entity attachedEntity;
	private static final String __OBFID = "CL_00001118";
	
	private ItemStack prevItemstack;
	
	public ReloadSoundHMG(Entity p_i45105_1_, String soundName, boolean repeat, float soundLV, float soundSP)
	{
		super(new ResourceLocation(soundName));
		this.attachedEntity = p_i45105_1_;
		this.repeat = repeat;
		this.field_147665_h = 0;
		this.field_147663_c = soundSP;
		this.volume = soundLV;
		if(p_i45105_1_ instanceof EntityLivingBase)
			prevItemstack = ((EntityLivingBase) p_i45105_1_).getHeldItem();
	}
	
	/**
	 * Updates the JList with a new model.
	 */
	public void update()
	{
		if (this.attachedEntity.isDead ||(attachedEntity instanceof EntityLivingBase &&  prevItemstack != ((EntityLivingBase) attachedEntity).getHeldItem()))
		{
			this.donePlaying = true;
		}
		else
		{
			this.xPosF = (float) this.attachedEntity.posX;
			this.yPosF = (float) this.attachedEntity.posY;
			this.zPosF = (float) this.attachedEntity.posZ;
		}
	}
}
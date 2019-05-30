package handmadeguns.client.audio;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.audio.MovingSound;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.Sys;

import static handmadeguns.HandmadeGunsCore.proxy;
import static java.lang.Math.sqrt;

@SideOnly(Side.CLIENT)
public class ReloadSoundHMG extends MovingSound
{
	private final Entity attachedEntity;
	private static final String __OBFID = "CL_00001118";
	
	private int prevslot;
	
	public ReloadSoundHMG(Entity p_i45105_1_, String soundName, boolean repeat, float soundLV, float soundSP)
	{
		super(new ResourceLocation(soundName));
		this.attachedEntity = p_i45105_1_;
		this.repeat = repeat;
		this.field_147665_h = 0;
		this.field_147663_c = soundSP;
		this.volume = soundLV;
		if(p_i45105_1_ instanceof EntityPlayer)
			prevslot = ((EntityPlayer) p_i45105_1_).inventory.currentItem;
	}
	
	/**
	 * Updates the JList with a new model.
	 */
	public void update()
	{
		if (
				this.attachedEntity.isDead ||
						(attachedEntity instanceof EntityPlayer &&
								 (prevslot != ((EntityPlayer) attachedEntity).inventory.currentItem ||
						       (((EntityPlayer) attachedEntity).getHeldItem() != null && ((EntityPlayer) attachedEntity).getHeldItem().getTagCompound() != null && (((EntityPlayer) attachedEntity).getHeldItem().getTagCompound().getBoolean("CannotReload"))))))
		{
			this.donePlaying = true;
			this.repeat = false;
		}
		else
		{
			this.xPosF = (float) this.attachedEntity.posX;
			this.yPosF = (float) this.attachedEntity.posY;
			this.zPosF = (float) this.attachedEntity.posZ;
		}
	}
}
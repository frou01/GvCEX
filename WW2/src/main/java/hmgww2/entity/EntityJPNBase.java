package hmgww2.entity;

import handmadeguns.entity.IFF;
import handmadeguns.items.guns.HMGItem_Unified_Guns;
import hmggvcmob.SlowPathFinder.WorldForPathfind;
import hmggvcmob.ai.*;
import hmggvcmob.entity.IGVCmob;
import hmggvcmob.entity.IRideableTank;
import hmggvcmob.entity.IdriveableVehicle;
import hmggvcmob.entity.friend.EntitySoBases;
import hmggvcmob.entity.guerrilla.EntityGBase;
import hmggvcmob.entity.guerrilla.EntityGBases;
import hmgww2.Nation;
import littleMaidMobX.LMM_EntityLittleMaid;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;

import static handmadeguns.HandmadeGunsCore.islmmloaded;

public class EntityJPNBase extends EntityBases implements IFF,IGVCmob {
	
	
	public EntityJPNBase(World par1World) {
		super(par1World);
	}
	
	@Override
	public Nation getnation() {
		return Nation.JPN;
	}
}
package hmgww2.entity;

import hmggvcmob.GVCMobPlus;
import hmggvcmob.ai.AITankAttack;
import hmggvcmob.entity.*;
import hmggvcmob.tile.TileEntityFlag;
import hmgww2.Nation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import javax.vecmath.Quat4d;
import javax.vecmath.Vector3d;

import static hmggvcmob.GVCMobPlus.proxy;
import static hmggvcmob.event.GVCMXEntityEvent.soundedentity;
import static hmggvcmob.util.Calculater.transformVecforMinecraft;

public class EntityUSSR_TankBase extends EntityBases_Tank{
	
	public EntityUSSR_TankBase(World par1World) {
		super(par1World);
	}
	
	@Override
	public Nation getnation() {
		return Nation.USSR;
	}
}
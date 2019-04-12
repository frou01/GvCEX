package hmgww2.entity;

import hmggvcmob.entity.IMultiTurretVehicle;
import hmggvcmob.entity.TurretObj;
import hmgww2.Nation;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import javax.vecmath.Vector3d;

import static hmggvcmob.util.Calculater.transformVecByQuat;
import static hmggvcmob.util.Calculater.transformVecforMinecraft;

public class EntityUSA_ShipBase extends EntityBases_Ship
{
	public EntityUSA_ShipBase(World par1World) {
		super(par1World);
	}
	
	@Override
	public Nation getnation() {
		return Nation.USA;
	}
}

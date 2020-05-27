package hmggvcmob.entity.friend;

import cpw.mods.fml.common.FMLCommonHandler;
import hmggvcmob.GVCMobPlus;
import hmggvcmob.ai.AITargetFlag;
import hmggvcmob.ai.PlatoonOBJ;
import hmggvcmob.camp.CampObj;
import hmggvcmob.entity.IPlatoonable;
import hmggvcmob.entity.IflagBattler;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;

import java.util.List;

import static handmadevehicle.Utils.canMoveEntity;
import static hmggvcmob.GVCMobPlus.*;

public class EntitySoBase extends EntitySoBases implements IflagBattler {


	public EntitySoBase(World par1World) {
		super(par1World);
		this.targetTasks.addTask(4, aiTargetFlag = new AITargetFlag(this,this,this));
	}
	
	public boolean getCanSpawnHere()
	{
		return super.getCanSpawnHere() && this.worldObj.difficultySetting != EnumDifficulty.PEACEFUL && spawnedcount<40 && this.isValidLightLevel();
	}
    protected boolean isValidLightLevel()
    {
    	for(int i = 0;i<worldObj.loadedTileEntityList.size();i++) {
			TileEntity tileentity;
			Object aLoadedTileEntityList = worldObj.loadedTileEntityList.get(i);
			tileentity = (TileEntity) aLoadedTileEntityList;
			if (tileentity.getBlockType() == GVCMobPlus.fn_PlayerFlag)return false;
		}
		int i = MathHelper.floor_double(this.posX);
		int j = MathHelper.floor_double(this.boundingBox.minY);
		int k = MathHelper.floor_double(this.posZ);


		if (this.worldObj.getSavedLightValue(EnumSkyBlock.Sky, i, j, k) <= this.rand.nextInt(32))
		{
			return false;
		}
		else
		{
			int l = this.worldObj.getSavedLightValue(EnumSkyBlock.Block,i, j, k);
			int l2= this.worldObj.getBlockLightValue(i, j, k);

			if (this.worldObj.isThundering())
			{
				int i1 = this.worldObj.skylightSubtracted;
				this.worldObj.skylightSubtracted = 10;
				l2 = this.worldObj.getBlockLightValue(i, j, k);
				this.worldObj.skylightSubtracted = i1;
			}

			return l < this.rand.nextInt(6) && l2>9 + this.rand.nextInt(8);
		}
    }

	public int getVerticalFaceSpeed()
	{
		return 90;
	}

	protected boolean canDespawn()
	{
		return getAttackTarget() == null;
	}
	protected boolean func_146066_aG()
	{
		return true;
	}

	@Override
	public CampObj getCampObj() {
		return soldiers;
	}

	@Override
	public boolean isThisAttackAbleCamp(CampObj campObj) {
		return campObj == guerrillas;
	}

	@Override
	public boolean isThisFriendCamp(CampObj campObj) {
		return campObj == forPlayer || campObj == soldiers;
	}

	@Override
	public boolean isThisIgnoreSpawnCamp(CampObj campObj) {
		return campObj == forPlayer || campObj == guerrillas;
	}

	@Override
	public void makePlatoon() {
		this.setPlatoon(new PlatoonOBJ());


		this.enlistPlatoon();
	}

	@Override
	public void enlistPlatoon() {
		List nearEntities = worldObj.getEntitiesWithinAABBExcludingEntity(this,boundingBox.expand(32,32,32));
		for(Object obj:nearEntities){
			Entity entity = (Entity)obj;
			if(entity instanceof EntitySoBase && canMoveEntity(entity) && ((IPlatoonable) entity).getPlatoon() == null){
				((EntitySoBase) entity).setPlatoon(this.platoonOBJ);
			}
		}

		List onVehicleEntity = worldObj.getEntitiesWithinAABBExcludingEntity(this,boundingBox.expand(512,512,512));
		for(Object obj:onVehicleEntity){
			Entity entity = (Entity)obj;
			if(entity instanceof EntitySoBase && canMoveEntity(entity) && ((IPlatoonable) entity).getPlatoon() == null){
				((EntitySoBase) entity).setPlatoon(this.platoonOBJ);
			}
		}
	}

}

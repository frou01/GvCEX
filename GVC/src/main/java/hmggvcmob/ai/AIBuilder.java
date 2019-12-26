package hmggvcmob.ai;

import handmadevehicle.SlowPathFinder.WorldForPathfind;
import hmggvcmob.IflagBattler;
import hmggvcmob.entity.IGVCmob;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import javax.vecmath.Vector3d;

import static handmadevehicle.Utils.*;
import static java.lang.StrictMath.abs;

public class AIBuilder extends EntityAIBase {
	EntityLiving entityBuilder;
	World worldObj;
	WorldForPathfind worldForPathfind;

	Vector3d targetPos;
	private int digingLevel;

	@Override
	public boolean shouldExecute() {
		if(entityBuilder.ridingEntity != null)return false;
		if(((IflagBattler)entityBuilder).getTargetCampPosition() != null){
			targetPos = getJavaxVecFromIntArray(((IflagBattler)entityBuilder).getTargetCampPosition());
		}else if(entityBuilder.getAttackTarget() != null &&
				((IGVCmob)entityBuilder).getAttackGun().setUp() &&
				!entityBuilder.canEntityBeSeen(entityBuilder.getAttackTarget())){
			targetPos = ((IGVCmob)entityBuilder).getSeeingPosition();
			if(targetPos != null){
				((IGVCmob)entityBuilder).getAttackGun().forceStop = true;
				targetPos = new Vector3d(targetPos);
				targetPos.y -=1;
			}
		}else {
			return false;
		}
		if(targetPos != null && isThereObstacle()){
			return true;
		}
		((IGVCmob)entityBuilder).getAttackGun().forceStop = false;
		return false;
	}
	public void startExecuting() {
	}
	public AIBuilder(EntityLiving entityLiving, WorldForPathfind worldForPathfind){
		entityBuilder = entityLiving;
		worldObj = entityLiving.worldObj;
		this.worldForPathfind = worldForPathfind;
	}

	private int making = 0;
	private int breakingTime;
	private Vector3d entityPos;
	private int[] prevDigPos = null;
	@Override
	public void updateTask() {
		super.updateTask();
		making++;
		Vector3d toTargetPos = new Vector3d();
		toTargetPos.sub(targetPos, entityPos);
		toTargetPos.normalize();
		boolean flag = false;
		for (float length = 0; length < 3; length+=0.5) {
			Vector3d temporary = new Vector3d(toTargetPos);
			temporary.scale(length);
			temporary.add(entityPos);
			temporary.y +=1;
			temporary.y +=1;
			flag = false;
			if(!digBlock(temporary))break;
			temporary.y +=1;
			if(!digBlock(temporary))break;
			flag = true;
		}
		if(flag) {
			if (making > 60) {
				toTargetPos.sub(targetPos, entityPos);
				toTargetPos.normalize();
				for (float length = 0; length < 3; length += 0.5) {
					Vector3d temporary = new Vector3d(toTargetPos);
					temporary.scale(length);
					temporary.add(entityPos);
					setBlock(worldObj, temporary, Blocks.planks, false);
				}
				Vector3d temporary = new Vector3d(toTargetPos);
				temporary.scale(3);
				temporary.add(entityPos);
				entityBuilder.getNavigator().setPath(worldForPathfind.getEntityPathToXYZ(entityBuilder,
						MathHelper.floor_double(temporary.x),
						MathHelper.floor_double(temporary.y),
						MathHelper.floor_double(temporary.z),
						80,true, false, false, false),1);
				making = 0;
			} else {
				if (making == 0)
					entityPos = new Vector3d(entityBuilder.posX, entityBuilder.posY - 1, entityBuilder.posZ);
				entityBuilder.getNavigator().clearPathEntity();
				if (entityBuilder.getHeldItem() == null)
					entityBuilder.setCurrentItemOrArmor(0, new ItemStack(Item.getItemFromBlock(Blocks.planks)));
				entityBuilder.setSneaking(true);
				if(making % 10 == 0)entityBuilder.swingItem();
			}
		}else {
			making = 0;
		}
	}
	public boolean digBlock(Vector3d temporary){
		if(getBlock(worldObj, temporary).getMaterial().isSolid()){
			int[] temporaryIntPos =  getIntPosesFromVector(temporary);
			if(prevDigPos == null || temporaryIntPos[0] != prevDigPos[0] ||
					temporaryIntPos[1] != prevDigPos[1] ||
					temporaryIntPos[2] != prevDigPos[2]){
				breakingTime = 0;
				prevDigPos = temporaryIntPos;
			}
			breakingTime++;
			entityBuilder.swingItem();
			if(getBlockHardness(worldObj,temporary)*40<breakingTime) {
				breakingTime=0;
				prevDigPos = null;
				playBlockDestroyEffect(worldObj,temporary);

				++this.breakingTime;
				int i = (int)((float)this.breakingTime / 240.0F * 10.0F);

				if (i != this.digingLevel)
				{
					int[] pos = getIntPosesFromVector(temporary);
					this.entityBuilder.worldObj.destroyBlockInWorldPartially(this.entityBuilder.getEntityId(), pos[0],pos[1],pos[2], i);
					this.digingLevel = i;
				}
				setBlock(worldObj, temporary, Blocks.air, true);
				return true;
			}
			return false;
		}
		return true;
	}

	@Override
	public void resetTask() {
		super.resetTask();
		entityPos = null;
		targetPos = null;
		entityBuilder.setSneaking(false);
		making = 0;
		prevDigPos = null;
		((IGVCmob)entityBuilder).getAttackGun().forceStop = false;
	}

	public boolean isThereObstacle(){
		//åÑä‘ÇíTÇ∑
		entityPos = new Vector3d(entityBuilder.posX, entityBuilder.posY-1, entityBuilder.posZ);
		Vector3d toTargetPos = new Vector3d();
		toTargetPos.sub(targetPos,entityPos);
		toTargetPos.normalize();
		boolean flag = false;
		int checkY = 0;
		for(float length = 0;length < 3;length+=0.5){
			Vector3d temporary = new Vector3d(toTargetPos);
			temporary.scale(length);
			temporary.add(entityPos);
			Block block = getBlock(worldObj,temporary);
			if(!block.getMaterial().isSolid()){
				temporary.y -=1;
				block = getBlock(worldObj,temporary);
				if(!block.getMaterial().isSolid())return true;//ê[Ç≥ÇQÉ}ÉXÇÃåäÇ™Ç†Ç¡ÇΩÇÁénìÆ
			}else if(!flag || (checkY - MathHelper.floor_double(temporary.y))>0){//éŒñ Ç»ÇÁèúãéÇÃïKóvÇÕñ≥Ç¢
				temporary.y +=1;
				Block block1 = getBlock(worldObj,temporary);
				checkY = MathHelper.floor_double(temporary.y);
				flag = block1.getMaterial().isSolid();
				temporary.y +=1;
				Block block2 = getBlock(worldObj,temporary);
				if(block2.getMaterial().isSolid())return true;
			}
		}
		return false;
	}
}

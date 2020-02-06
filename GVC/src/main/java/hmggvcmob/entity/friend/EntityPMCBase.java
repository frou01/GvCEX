package hmggvcmob.entity.friend;

import handmadeguns.entity.IFF;
import handmadeguns.entity.PlacedGunEntity;
import handmadevehicle.entity.EntityDummy_rider;
import handmadevehicle.entity.EntityVehicle;
import hmggvcmob.IflagBattler;
import hmggvcmob.ai.AICommandedEntity;
import hmggvcmob.camp.CampObj;
import hmggvcmob.entity.ICommandedEntity;
import hmggvcmob.entity.IGVCmob;
import hmggvcmob.item.GVCItemPMCDefSetter;
import littleMaidMobX.LMM_EntityLittleMaid;
import net.minecraft.entity.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static handmadeguns.HandmadeGunsCore.HMG_proxy;
import static handmadeguns.HandmadeGunsCore.islmmloaded;
import static handmadevehicle.Utils.canMoveEntity;
import static hmggvcmob.GVCMobPlus.cfg_guerrillacanusePlacedGun;
import static hmggvcmob.GVCMobPlus.forPlayer;

public class EntityPMCBase extends EntitySoBases implements IFF,IGVCmob, ICommandedEntity {

	public int mode = 0;
	public String platoonName = null;

	public boolean interact(EntityPlayer entityPlayer) {
		if(entityPlayer.getHeldItem() != null && entityPlayer.getHeldItem().getItem() instanceof GVCItemPMCDefSetter && entityPlayer.getHeldItem().hasDisplayName()){

			HMG_proxy.setRightclicktimer();
			platoonName = entityPlayer.getHeldItem().getDisplayName();
			this.setCustomNameTag(platoonName);
			return true;
		}
		if (super.interact(entityPlayer)) {
			return false;
		} else if (!this.worldObj.isRemote && (this.riddenByEntity == null || this.riddenByEntity == entityPlayer)) {
			setLeaderPlayer(entityPlayer);
			mode++;
			if(mode>1){
				mode = 0;
			}
			if (mode == 0) {
				entityPlayer.addChatComponentMessage(new ChatComponentTranslation(
						"Keep this position  " + (int)this.posX + "," + (int)this.posZ));
				this.setTargetCampPosition(new int[]{(int) entityPlayer.posX, (int) entityPlayer.posY, (int) entityPlayer.posZ});
				this.platoonLeader = this;

				List list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.expand(2, 2, 2));

				if (list != null && !list.isEmpty()) {
					for (int i = 0; i < list.size(); ++i) {
						Entity entity = (Entity) list.get(i);

						if (this.ridingEntity == null && !entity.isDead) {
							if(cfg_guerrillacanusePlacedGun && canuseAlreadyPlacedGun && !worldObj.isRemote) {
								if (entity.riddenByEntity == null && entity instanceof PlacedGunEntity) {
									this.mountEntity((PlacedGunEntity) entity);
									this.setCurrentItemOrArmor(0, null);
									break;
								}
							}
						}
					}
				}

			}else {
				entityPlayer.addChatComponentMessage(new ChatComponentTranslation(
						"I'll Follow Leader"));
				this.setLeaderPlayer(entityPlayer);
			}
			return true;
		}
		return false;
	}
	public AICommandedEntity aiCommandedEntity;
	public EntityPMCBase(World par1World) {
		super(par1World);
		this.tasks.addTask(3,aiCommandedEntity = new AICommandedEntity(this,worldForPathfind));
	}

	public void onUpdate()
    {
        super.onUpdate();

		if(rand.nextInt(10) == 0)this.addPotionEffect(new PotionEffect(Potion.regeneration.id, 1, 2));
    }

    public boolean getCanSpawnHere()
    {
    	return false;
    }

	protected boolean canDespawn()
	{
		return false;
	}

	@Override
	public boolean is_this_entity_friend(Entity entity) {
		if(entity instanceof EntityPlayer){
			return true;
		}else if(entity instanceof EntitySoBases) {
			return true;
		}else if(islmmloaded && entity instanceof LMM_EntityLittleMaid){
			return true;
		}
		return false;
	}
	public void readEntityFromNBT(NBTTagCompound p_70037_1_)
	{
		super.readEntityFromNBT(p_70037_1_);
		mode = p_70037_1_.getInteger("mode");
		leaderEntity_name = p_70037_1_.getString("leaderEntity_name");
		if(p_70037_1_.hasKey("platoonName")) platoonName = p_70037_1_.getString("platoonName");
	}

	public void writeEntityToNBT(NBTTagCompound p_70014_1_)
	{
		super.writeEntityToNBT(p_70014_1_);
		p_70014_1_.setInteger("mode",mode);
		if(leaderEntity_name != null)p_70014_1_.setString("leaderEntity_name",leaderEntity_name);
		if(platoonName != null)p_70014_1_.setString("platoonName",platoonName);
	}

	public EntityPlayer leaderEntity;
	public String leaderEntity_name;

	@Override
	public EntityPlayer getLeaderPlayer() {
		if (leaderEntity == null && leaderEntity_name != null) {
			leaderEntity = worldObj.getPlayerEntityByName(leaderEntity_name);
		}
		return leaderEntity;
	}

	@Override
	public void setLeaderPlayer(EntityPlayer entityPlayer) {
		leaderEntity = entityPlayer;
		leaderEntity_name = entityPlayer.getCommandSenderName();
	}

	@Override
	public int getCommandState() {
		return mode;
	}

	@Override
	public void setCommandState(int commandState) {
		mode = commandState;
	}

	@Override
	public void makePlatoon() {
		if(canMoveEntity(this) && platoon == null){
			platoon = new ArrayList<>();
			platoonLeader = this;

			List list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, AxisAlignedBB.getBoundingBox(this.posX, this.posY, this.posZ, this.posX + 1.0D, this.posY + 1.0D, this.posZ + 1.0D).expand(32, 32, 32));
			Iterator iterator = list.iterator();

			while (iterator.hasNext())
			{
				Entity entitycreature = (Entity)iterator.next();
				if(entitycreature instanceof ICommandedEntity && canMoveEntity(this) && !(entitycreature.ridingEntity instanceof EntityDummy_rider)){
					platoon.add(entitycreature);
					((ICommandedEntity) entitycreature).joinPlatoon(this);
				}

			}
		}
	}

	@Override
	public void joinPlatoon(IflagBattler iflagBattler) {
		super.joinPlatoon(iflagBattler);
		mode = 0;
	}

	@Override
	public CampObj getCampObj() {
		return forPlayer;
	}
}

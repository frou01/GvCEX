package hmggvcmob.entity;

import hmggvcmob.IflagBattler;
import net.minecraft.entity.player.EntityPlayer;

public interface ICommandedEntity extends IflagBattler {
	EntityPlayer getLeaderPlayer();
	void setLeaderPlayer(EntityPlayer entityPlayer);
	int getCommandState();
	void setCommandState(int commandState);
	@Override
	default int getMobMode() {
		if(getLeaderPlayer() != null)return getCommandState()+1;
		if(getTargetCampPosition() != null)return getCommandState()+1;
		else return 0;
	}
	@Override
	default double[] getTargetpos() {
		if(getTargetCampPosition() != null)
		return new double[]{getTargetCampPosition()[0],getTargetCampPosition()[1],getTargetCampPosition()[2]};
		else if(getLeaderPlayer() != null)
			return new double[]{getLeaderPlayer().posX,getLeaderPlayer().posY,getLeaderPlayer().posZ};
		return null;
	}
}

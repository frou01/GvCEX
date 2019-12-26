package hmggvcmob.camp;

import handmadeguns.client.render.ModelSetAndData;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.scoreboard.Team;

public class CampObj extends Team {
	public String campName;
	public boolean isOneTime = false;
	public boolean isRaidType = false;
	public ModelSetAndData modelSetAndData;
	public String campBlockObjModel = "gvcmob:textures/model/flagmodel.mqo";
	public String campBlockTextureModel = "gvcmob:textures/model/flagtexture.png";
	public String campBlockTextureItem;

	public int flagWidth = 2;
	public int spawnEntitiesAve = 8;
	public int spawnRaiderEntitiesAve = 8;
	public int raiderGroupNum = 8;
	public int maxFlagHeight = 10000;

	public boolean playerIsFriend = false;

	public int flagSpawnInterval = 2400;

	public CampObj[] raiderIgnoreList;

	public Block campsBlock;

	public Class<? extends Entity>[] teamEntityClasses;
	public Class<? extends Entity>[] raiderEntityClasses;

	@Override
	public String getRegisteredName() {
		return campName;
	}

	@Override
	public String formatString(String p_142053_1_) {
		return campName;
	}

	@Override
	public boolean func_98297_h() {
		return false;
	}

	@Override
	public boolean getAllowFriendlyFire() {
		return true;
	}
}

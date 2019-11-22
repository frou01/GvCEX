package hmggvcmob.camp;

import handmadeguns.client.render.ModelSetAndData;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.scoreboard.Team;

public class CampObj extends Team {
	public String campName;
	public boolean isOneTime = false;
	public ModelSetAndData modelSetAndData;
	public String campBlockObjModel = "gvcmob:textures/model/flagmodel.mqo";
	public String campBlockTextureModel = "gvcmob:textures/model/flagtexture.png";
	public String campBlockTextureItem;

	public int flagWidth = 2;
	public int maxFlagHeight = 10000;

	public Block campsBlock;

	public Class<? extends Entity>[] teamEntityClasses;

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

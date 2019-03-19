package hmggvcmob;

import net.minecraft.block.Block;
import net.minecraft.util.Vec3;


public interface IflagBattler {
    public Block getFlag();
    public boolean isthisFlagIsEnemys(Block block);
    public boolean istargetingflag();
    public Vec3 getflagposition();
    public void setflagposition(int x, int y, int z);
}

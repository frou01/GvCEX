package hmggvcmob.entity;

import hmggvcmob.tile.TileEntityFlag;
import net.minecraft.entity.Entity;

public interface IGVCmob {
    //¡“x‰Šúˆ—‚ğ‚Ü‚Æ‚ß‚ÄŒÄ‚×‚é‚æ‚¤‚É‚µ‚æ‚¤
    float getviewWide();
    boolean canSeeTarget(Entity target);
    boolean canhearsound(Entity target);
    void setspawnedtile(TileEntityFlag flag);
}

package hmggvcmob.entity;

import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;

public interface IGVCmob {
    //¡“x‰Šúˆ—‚ğ‚Ü‚Æ‚ß‚ÄŒÄ‚×‚é‚æ‚¤‚É‚µ‚æ‚¤
    float getviewWide();
    boolean canSeeTarget(Entity target);
    boolean canhearsound(Entity target);
}

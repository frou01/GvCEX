package hmggvcmob.entity;

import hmggvcmob.ai.AIAttackGun;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;

import javax.vecmath.Vector3d;

public interface IGVCmob {
    //¡“x‰Šúˆ—‚ğ‚Ü‚Æ‚ß‚ÄŒÄ‚×‚é‚æ‚¤‚É‚µ‚æ‚¤
    float getviewWide();
    boolean canSeeTarget(Entity target);
    boolean canhearsound(Entity target);
    default Vector3d getSeeingPosition(){
        return getAttackGun().getSeeingPosition();
    }
    AIAttackGun getAttackGun();
}

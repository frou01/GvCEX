package hmggvcmob.entity;

import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;

public interface IGVCmob {
    //今度初期処理をまとめて呼べるようにしよう
    float getviewWide();
    boolean canSeeTarget(Entity target);
    boolean canhearsound(Entity target);
    void setspawnedtile(TileEntity flag);
}

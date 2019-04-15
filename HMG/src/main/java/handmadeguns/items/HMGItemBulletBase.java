package handmadeguns.items;

import net.minecraft.item.Item;

public class HMGItemBulletBase extends HMGItemAttachmentBase {
    public float damagemodify = 1;
    public float speedmodify = 1;
    public int fuse = -1;
    public boolean flame = false;
    public boolean explosion = false;
    public boolean blockdestroyex = false;
    public float explosionlevel = 1;
    public String bulletmodel = "default";
    public  FireInfo fireInfo;
}

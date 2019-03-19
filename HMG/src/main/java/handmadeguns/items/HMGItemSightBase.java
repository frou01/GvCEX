package handmadeguns.items;

import net.minecraft.util.ResourceLocation;

public class HMGItemSightBase extends HMGItemAttachmentBase {

    public boolean needgunoffset = false;
    public boolean scopeonly = false;
    public float gunoffset[] = new float[3];
    public float gunrotation[] = new float[3];
    public float zoomlevel = -1;
    public ResourceLocation scopetexture;

    public boolean isnightvision = false;
}

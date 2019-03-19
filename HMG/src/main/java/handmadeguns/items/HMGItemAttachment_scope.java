package handmadeguns.items;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;


public class HMGItemAttachment_scope extends HMGItemSightBase {
	public boolean hasoptionalZoom = false;
	public ResourceLocation scopetexture = null;
	public float zoomlevel;
	public HMGItemAttachment_scope() {
		this.maxStackSize = 1;
	}
}

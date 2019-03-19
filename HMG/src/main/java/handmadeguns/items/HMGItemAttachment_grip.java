package handmadeguns.items;
import net.minecraft.item.Item;


public class HMGItemAttachment_grip extends HMGItemAttachmentBase {
	public float reduceRecoilLevel = 1f;
	public float reduceRecoilLevel_ADS = 1f;
	public float reduceSpreadLevel = 1f;
	public float reduceSpreadLevel_ADS = 1f;
	public boolean isbase = false;
	public float motion = 1;
	public HMGItemAttachment_grip() {
		this.maxStackSize = 1;
	}
}

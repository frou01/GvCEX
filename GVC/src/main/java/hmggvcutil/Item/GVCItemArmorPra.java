package hmggvcutil.Item;
 
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
 
public class GVCItemArmorPra extends ItemArmor {
 
	public static final String armor_layer1 = "gvcguns:textures/armor/praarmor_layer1.png";
    public static final String armor_layer2 = "gvcguns:textures/armor/praarmor_layer2.png";

	
	public GVCItemArmorPra(ArmorMaterial armorMaterial, int type) {
		super(armorMaterial, 0, type);
	}
 
	@Override
	public String getArmorTexture(ItemStack itemStack, Entity entity, int slot, String type) {
		if (this.armorType == 2) {
			return armor_layer2;
		}
		return armor_layer1;
	}
 
}

package hmgww2.items;
 
import hmgww2.Nation;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
 
public class ItemIFFArmor extends ItemArmor {
 
	public static final String armor_layer1 = "gvcguns:textures/armor/praarmor_layer1.png";
    public static final String armor_layer2 = "gvcguns:textures/armor/praarmor_layer2.png";
    public String armor;
    public Nation nation;

	
	public ItemIFFArmor(ArmorMaterial armorMaterial, int type, String tx, Nation nation) {
		super(armorMaterial, 0, type);
		this.armor = tx;
		this.nation = nation;
	}
 
	@Override
	public String getArmorTexture(ItemStack itemStack, Entity entity, int slot, String type) {
		if (this.armorType == 2) {
			return armor;
		}
		return armor;
	}
 
}

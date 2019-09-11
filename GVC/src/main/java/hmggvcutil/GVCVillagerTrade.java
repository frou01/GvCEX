package hmggvcutil;

import java.util.Random;

import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;
import cpw.mods.fml.common.registry.VillagerRegistry.IVillageTradeHandler;

import static handmadeguns.HMGGunMaker.Guns;

public class GVCVillagerTrade implements IVillageTradeHandler {

	@Override
	public void manipulateTradesForVillager(EntityVillager villager,MerchantRecipeList recipeList, Random random) {

		recipeList.add(new MerchantRecipe( new ItemStack(Items.emerald, 2, 0), new ItemStack(GVCUtils.fn_magazine, 4, 0)));
		recipeList.add(new MerchantRecipe( new ItemStack(Items.emerald, 2, 0), new ItemStack(GVCUtils.fn_box, 4, 0)));
		for(int i=0;i<16;i++) {
			recipeList.add(new MerchantRecipe( new ItemStack(Items.emerald, 4, 0), new ItemStack((Item) Guns.get(random.nextInt(Guns.size())), 0, 0)));
		}
		recipeList.add(new MerchantRecipe( new ItemStack((Item) Guns.get(random.nextInt(Guns.size())), 1, 0), new ItemStack(Items.emerald, 4, 0)));
		//}
	}
}
package hmgww2.event;

import cpw.mods.fml.common.registry.GameRegistry;
import hmgww2.mod_GVCWW2;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class RecipeRegistrys {

	public static void recipe(){
		int D = Short.MAX_VALUE;
		GameRegistry.addRecipe(new ItemStack(mod_GVCWW2.b_magazinehg, 2),
				"ig",
				"gg", 
				'i', Items.iron_ingot,
				'g', Items.gunpowder
		);
		GameRegistry.addRecipe(new ItemStack(mod_GVCWW2.gun_grenade, 1),
				" g ",
				"g g", 
				" g ",
				'g', Items.gunpowder
		);
		GameRegistry.addShapelessRecipe(new ItemStack(mod_GVCWW2.b_magazine, 1), new ItemStack(mod_GVCWW2.b_magazinehg, 1,D)
				, new ItemStack(mod_GVCWW2.b_magazinehg, 1,D));
		GameRegistry.addShapelessRecipe(new ItemStack(mod_GVCWW2.b_magazinehg, 2), new ItemStack(mod_GVCWW2.b_magazine, 1,D));
		GameRegistry.addShapelessRecipe(new ItemStack(mod_GVCWW2.b_magazinemg, 1), new ItemStack(mod_GVCWW2.b_magazine, 1,D)
				, new ItemStack(mod_GVCWW2.b_magazine, 1,D), new ItemStack(mod_GVCWW2.b_magazine, 1,D));
		GameRegistry.addShapelessRecipe(new ItemStack(mod_GVCWW2.b_magazine, 3), new ItemStack(mod_GVCWW2.b_magazinemg, 1,D));
		GameRegistry.addShapelessRecipe(new ItemStack(mod_GVCWW2.b_magazinerpg, 1), new ItemStack(mod_GVCWW2.b_magazine, 1,D)
				, new ItemStack(mod_GVCWW2.b_magazine, 1,D), new ItemStack(mod_GVCWW2.b_magazine, 1,D), new ItemStack(mod_GVCWW2.b_magazine, 1,D));
		GameRegistry.addShapelessRecipe(new ItemStack(mod_GVCWW2.b_magazine, 4), new ItemStack(mod_GVCWW2.b_magazinerpg, 1,D));
		GameRegistry.addShapelessRecipe(new ItemStack(mod_GVCWW2.gun_grenadet, 1), new ItemStack(mod_GVCWW2.gun_grenade, 1,D)
				, new ItemStack(mod_GVCWW2.gun_grenade, 1,D), new ItemStack(mod_GVCWW2.gun_grenade, 1,D), new ItemStack(mod_GVCWW2.gun_grenade, 1,D));
		
		GameRegistry.addRecipe(new ItemStack(mod_GVCWW2.armor_jpn, 1),
				"iii",
				"isi", 
				'i', Items.iron_ingot,
				's', new ItemStack(Items.dye, 1, 15)
		);
		GameRegistry.addRecipe(new ItemStack(mod_GVCWW2.armor_usa, 1),
				"iii",
				"isi", 
				'i', Items.iron_ingot,
				's', new ItemStack(Items.dye, 1, 2)
		);
		GameRegistry.addRecipe(new ItemStack(mod_GVCWW2.armor_ger, 1),
				"iii",
				"isi", 
				'i', Items.iron_ingot,
				's', new ItemStack(Items.dye, 1, 7)
		);
		GameRegistry.addRecipe(new ItemStack(mod_GVCWW2.armor_rus, 1),
				"iii",
				"isi", 
				'i', Items.iron_ingot,
				's', new ItemStack(Items.dye, 1, 1)
		);
		
		
		//jpn
		GameRegistry.addRecipe(new ItemStack(mod_GVCWW2.gun_type38, 1),
				"iii",
				" sw", 
				'i', Items.iron_ingot,
				'w', new ItemStack(Blocks.planks, 1, D),
				's', new ItemStack(Items.dye, 1, 15)
		);
		GameRegistry.addRecipe(new ItemStack(mod_GVCWW2.gun_type99lmg, 1),
				"iii",
				" si", 
				'i', Items.iron_ingot,
				's', new ItemStack(Items.dye, 1, 15)
		);
		GameRegistry.addRecipe(new ItemStack(mod_GVCWW2.gun_type100, 1),
				"ii",
				"sw", 
				'i', Items.iron_ingot,
				'w', new ItemStack(Blocks.planks, 1, D),
				's', new ItemStack(Items.dye, 1, 15)
		);
		GameRegistry.addRecipe(new ItemStack(mod_GVCWW2.gun_type4Auto, 1),
				" gg",
				"iii",
				" sw", 
				'g', Blocks.glass,
				'i', Items.iron_ingot,
				'w', new ItemStack(Blocks.planks, 1, D),
				's', new ItemStack(Items.dye, 1, 15)
		);
		GameRegistry.addRecipe(new ItemStack(mod_GVCWW2.gun_rota_cannon, 1),
				"ii ",
				"isi",
				" iw", 
				'i', Items.iron_ingot,
				'w', new ItemStack(Blocks.planks, 1, D),
				's', new ItemStack(Items.dye, 1, 15)
		);
		GameRegistry.addRecipe(new ItemStack(mod_GVCWW2.gun_type14, 1),
				"ii",
				" s", 
				'i', Items.iron_ingot,
				's', new ItemStack(Items.dye, 1, 15)
		);
		
		//usa
		GameRegistry.addRecipe(new ItemStack(mod_GVCWW2.gun_m1g, 1),
				"iii",
				" sw", 
				'i', Items.iron_ingot,
				'w', new ItemStack(Blocks.planks, 1, D),
				's', new ItemStack(Items.dye, 1, 2)
		);
		GameRegistry.addRecipe(new ItemStack(mod_GVCWW2.gun_bar, 1),
				"iii",
				" si", 
				'i', Items.iron_ingot,
				's', new ItemStack(Items.dye, 1, 2)
		);
		GameRegistry.addRecipe(new ItemStack(mod_GVCWW2.gun_m1t, 1),
				"ii",
				"sw", 
				'i', Items.iron_ingot,
				'w', new ItemStack(Blocks.planks, 1, D),
				's', new ItemStack(Items.dye, 1, 2)
		);
		GameRegistry.addRecipe(new ItemStack(mod_GVCWW2.gun_m1917, 1),
				" gg",
				"iii",
				" sw", 
				'g', Blocks.glass,
				'i', Items.iron_ingot,
				'w', new ItemStack(Blocks.planks, 1, D),
				's', new ItemStack(Items.dye, 1, 2)
		);
		GameRegistry.addRecipe(new ItemStack(mod_GVCWW2.gun_m1b, 1),
				"ii ",
				"isi",
				" iw", 
				'i', Items.iron_ingot,
				'w', new ItemStack(Blocks.planks, 1, D),
				's', new ItemStack(Items.dye, 1, 2)
		);
		GameRegistry.addRecipe(new ItemStack(mod_GVCWW2.gun_m1911, 1),
				"ii",
				" s", 
				'i', Items.iron_ingot,
				's', new ItemStack(Items.dye, 1, 2)
		);
		
		//ger
		GameRegistry.addRecipe(new ItemStack(mod_GVCWW2.gun_gew98, 1),
				"iii",
				" sw", 
				'i', Items.iron_ingot,
				'w', new ItemStack(Blocks.planks, 1, D),
				's', new ItemStack(Items.dye, 1, 7)
		);
		GameRegistry.addRecipe(new ItemStack(mod_GVCWW2.gun_mg34, 1),
				"iii",
				" si", 
				'i', Items.iron_ingot,
				's', new ItemStack(Items.dye, 1, 7)
		);
		GameRegistry.addRecipe(new ItemStack(mod_GVCWW2.gun_mp40, 1),
				"ii",
				"sw", 
				'i', Items.iron_ingot,
				'w', new ItemStack(Blocks.planks, 1, D),
				's', new ItemStack(Items.dye, 1, 7)
		);
		GameRegistry.addRecipe(new ItemStack(mod_GVCWW2.gun_gew43, 1),
				" gg",
				"iii",
				" sw", 
				'g', Blocks.glass,
				'i', Items.iron_ingot,
				'w', new ItemStack(Blocks.planks, 1, D),
				's', new ItemStack(Items.dye, 1, 7)
		);
		GameRegistry.addRecipe(new ItemStack(mod_GVCWW2.gun_rpzb54, 1),
				"ii ",
				"isi",
				" iw", 
				'i', Items.iron_ingot,
				'w', new ItemStack(Blocks.planks, 1, D),
				's', new ItemStack(Items.dye, 1, 7)
		);
		GameRegistry.addRecipe(new ItemStack(mod_GVCWW2.gun_p38, 1),
				"ii",
				" s", 
				'i', Items.iron_ingot,
				's', new ItemStack(Items.dye, 1, 7)
		);
		
		//rus
		GameRegistry.addRecipe(new ItemStack(mod_GVCWW2.gun_m1891, 1),
				"iii",
				" sw", 
				'i', Items.iron_ingot,
				'w', new ItemStack(Blocks.planks, 1, D),
				's', new ItemStack(Items.dye, 1, 1)
		);
		GameRegistry.addRecipe(new ItemStack(mod_GVCWW2.gun_dp28, 1),
				"iii",
				" si", 
				'i', Items.iron_ingot,
				's', new ItemStack(Items.dye, 1, 1)
		);
		GameRegistry.addRecipe(new ItemStack(mod_GVCWW2.gun_ppsh41, 1),
				"ii",
				"sw", 
				'i', Items.iron_ingot,
				'w', new ItemStack(Blocks.planks, 1, D),
				's', new ItemStack(Items.dye, 1, 1)
		);
		GameRegistry.addRecipe(new ItemStack(mod_GVCWW2.gun_m1891sr, 1),
				" gg",
				"iii",
				" sw", 
				'g', Blocks.glass,
				'i', Items.iron_ingot,
				'w', new ItemStack(Blocks.planks, 1, D),
				's', new ItemStack(Items.dye, 1, 1)
		);
		GameRegistry.addRecipe(new ItemStack(mod_GVCWW2.gun_tt33, 1),
				"ii",
				" s", 
				'i', Items.iron_ingot,
				's', new ItemStack(Items.dye, 1, 1)
		);
		
		
		
		
		//jpn
		GameRegistry.addRecipe(new ItemStack(mod_GVCWW2.spawn_jpn_s, 1),
				"a",
				"e", 
				'a', mod_GVCWW2.armor_jpn,
				'e', Items.egg
		);
		GameRegistry.addRecipe(new ItemStack(mod_GVCWW2.spawn_jpn_tank, 1),
				" a ",
				"bbb",
				" e ", 
				'a', mod_GVCWW2.armor_jpn,
				'b', Blocks.iron_block,
				'e', Items.egg
		);
		GameRegistry.addShapelessRecipe(new ItemStack(mod_GVCWW2.spawn_jpn_tankaa, 1), new ItemStack(mod_GVCWW2.spawn_jpn_tank, 1));
		GameRegistry.addShapelessRecipe(new ItemStack(mod_GVCWW2.spawn_jpn_tankspg, 1), new ItemStack(mod_GVCWW2.spawn_jpn_tankaa, 1));
		GameRegistry.addShapelessRecipe(new ItemStack(mod_GVCWW2.spawn_jpn_tank, 1), new ItemStack(mod_GVCWW2.spawn_jpn_tankspg, 1));
		GameRegistry.addRecipe(new ItemStack(mod_GVCWW2.spawn_jpn_fighter, 1),
				" a ",
				"ibi",
				" e ", 
				'a', mod_GVCWW2.armor_jpn,
				'b', Blocks.iron_block,
				'i', Items.iron_door,
				'e', Items.egg
		);
		GameRegistry.addShapelessRecipe(new ItemStack(mod_GVCWW2.spawn_jpn_attcker, 1), new ItemStack(mod_GVCWW2.spawn_jpn_fighter, 1));
		
		//usa
				GameRegistry.addRecipe(new ItemStack(mod_GVCWW2.spawn_usa_s, 1),
						"a",
						"e", 
						'a', mod_GVCWW2.armor_usa,
						'e', Items.egg
				);
				GameRegistry.addRecipe(new ItemStack(mod_GVCWW2.spawn_usa_tank, 1),
						" a ",
						"bbb",
						" e ", 
						'a', mod_GVCWW2.armor_usa,
						'b', Blocks.iron_block,
						'e', Items.egg
				);
				GameRegistry.addShapelessRecipe(new ItemStack(mod_GVCWW2.spawn_usa_tankaa, 1), new ItemStack(mod_GVCWW2.spawn_usa_tank, 1));
				GameRegistry.addShapelessRecipe(new ItemStack(mod_GVCWW2.spawn_usa_tankspg, 1), new ItemStack(mod_GVCWW2.spawn_usa_tankaa, 1));
				GameRegistry.addShapelessRecipe(new ItemStack(mod_GVCWW2.spawn_usa_tank, 1), new ItemStack(mod_GVCWW2.spawn_usa_tankspg, 1));
				GameRegistry.addRecipe(new ItemStack(mod_GVCWW2.spawn_usa_fighter, 1),
						" a ",
						"ibi",
						" e ", 
						'a', mod_GVCWW2.armor_usa,
						'b', Blocks.iron_block,
						'i', Items.iron_door,
						'e', Items.egg
				);
				GameRegistry.addShapelessRecipe(new ItemStack(mod_GVCWW2.spawn_usa_attcker, 1), new ItemStack(mod_GVCWW2.spawn_usa_fighter, 1));
		
				//ger
				GameRegistry.addRecipe(new ItemStack(mod_GVCWW2.spawn_ger_s, 1),
						"a",
						"e", 
						'a', mod_GVCWW2.armor_ger,
						'e', Items.egg
				);
				GameRegistry.addRecipe(new ItemStack(mod_GVCWW2.spawn_ger_tank, 1),
						" a ",
						"bbb",
						" e ", 
						'a', mod_GVCWW2.armor_ger,
						'b', Blocks.iron_block,
						'e', Items.egg
				);
				GameRegistry.addShapelessRecipe(new ItemStack(mod_GVCWW2.spawn_ger_tankaa, 1), new ItemStack(mod_GVCWW2.spawn_ger_tank, 1));
				GameRegistry.addShapelessRecipe(new ItemStack(mod_GVCWW2.spawn_ger_tankspg, 1), new ItemStack(mod_GVCWW2.spawn_ger_tankaa, 1));
				GameRegistry.addShapelessRecipe(new ItemStack(mod_GVCWW2.spawn_ger_tank, 1), new ItemStack(mod_GVCWW2.spawn_ger_tankspg, 1));
				GameRegistry.addRecipe(new ItemStack(mod_GVCWW2.spawn_ger_tankh, 1),
						" a ",
						"bbb",
						"beb", 
						'a', mod_GVCWW2.armor_ger,
						'b', Blocks.iron_block,
						'e', Items.egg
				);
				GameRegistry.addRecipe(new ItemStack(mod_GVCWW2.spawn_ger_fighter, 1),
						" a ",
						"ibi",
						" e ", 
						'a', mod_GVCWW2.armor_ger,
						'b', Blocks.iron_block,
						'i', Items.iron_door,
						'e', Items.egg
				);
				GameRegistry.addShapelessRecipe(new ItemStack(mod_GVCWW2.spawn_ger_attcker, 1), new ItemStack(mod_GVCWW2.spawn_ger_fighter, 1));
				
				//rus
				GameRegistry.addRecipe(new ItemStack(mod_GVCWW2.spawn_rus_s, 1),
						"a",
						"e", 
						'a', mod_GVCWW2.armor_rus,
						'e', Items.egg
				);
				GameRegistry.addRecipe(new ItemStack(mod_GVCWW2.spawn_rus_tank, 1),
						" a ",
						"bbb",
						" e ", 
						'a', mod_GVCWW2.armor_rus,
						'b', Blocks.iron_block,
						'e', Items.egg
				);
				GameRegistry.addShapelessRecipe(new ItemStack(mod_GVCWW2.spawn_rus_tankaa, 1), new ItemStack(mod_GVCWW2.spawn_rus_tank, 1));
				GameRegistry.addShapelessRecipe(new ItemStack(mod_GVCWW2.spawn_rus_tankspg, 1), new ItemStack(mod_GVCWW2.spawn_rus_tankaa, 1));
				GameRegistry.addShapelessRecipe(new ItemStack(mod_GVCWW2.spawn_rus_tank, 1), new ItemStack(mod_GVCWW2.spawn_rus_tankspg, 1));
				GameRegistry.addRecipe(new ItemStack(mod_GVCWW2.spawn_rus_tankh, 1),
						" a ",
						"bbb",
						"beb", 
						'a', mod_GVCWW2.armor_rus,
						'b', Blocks.iron_block,
						'e', Items.egg
				);
				GameRegistry.addRecipe(new ItemStack(mod_GVCWW2.spawn_rus_fighter, 1),
						" a ",
						"ibi",
						" e ", 
						'a', mod_GVCWW2.armor_rus,
						'b', Blocks.iron_block,
						'i', Items.iron_door,
						'e', Items.egg
				);
				GameRegistry.addShapelessRecipe(new ItemStack(mod_GVCWW2.spawn_rus_attcker, 1), new ItemStack(mod_GVCWW2.spawn_rus_fighter, 1));
	}
}
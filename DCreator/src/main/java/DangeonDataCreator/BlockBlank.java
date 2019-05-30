package DangeonDataCreator;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class BlockBlank extends Block {
	protected BlockBlank(Material p_i45394_1_) {
		super(p_i45394_1_);
	}
	public BlockBlank(){
		this(Material.rock);
		setCreativeTab(CreativeTabs.tabMisc);
	}
}

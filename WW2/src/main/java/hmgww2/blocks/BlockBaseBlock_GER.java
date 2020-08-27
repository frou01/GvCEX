package hmgww2.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import hmgww2.mod_GVCWW2;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
//import net.minecraft.world.gen.structure.StructureStrongholdPieces;;


public class BlockBaseBlock_GER extends Block {
	@SideOnly(Side.CLIENT)
	private IIcon TopIcon;

	@SideOnly(Side.CLIENT)
	private IIcon SideIcon;

	public BlockBaseBlock_GER() {
		super(Material.iron);
		setHardness(50F);
		setResistance(2000.0F);
		setStepSound(Block.soundTypeStone);
	}

	public void onBlockHarvested(World p_149681_1_, int p_149681_2_, int p_149681_3_, int p_149681_4_, int p_149681_5_, EntityPlayer p_149681_6_) {
	}


	public void onBlockAdded(World par1World, int par1, int par2, int par3) {
		for (int l = 1; l < 64; l++) {
			if ((par1World.getBlock(par1 + 0, par2 - l, par3 + 0) == Blocks.air
					|| par1World.getBlock(par1 + 0, par2 - l, par3 + 0) == Blocks.water
					|| par1World.getBlock(par1 + 0, par2 - l, par3 + 0) == Blocks.flowing_water
					|| par1World.getBlock(par1 + 0, par2 - l, par3 + 0) == Blocks.lava
					|| par1World.getBlock(par1 + 0, par2 - l, par3 + 0) == Blocks.flowing_lava
			)
					|| (par1World.getBlock(par1 + 15, par2 - l, par3 + 15) == Blocks.air
					|| par1World.getBlock(par1 + 15, par2 - l, par3 + 15) == Blocks.water
					|| par1World.getBlock(par1 + 15, par2 - l, par3 + 15) == Blocks.flowing_water
					|| par1World.getBlock(par1 + 15, par2 - l, par3 + 15) == Blocks.flowing_water
					|| par1World.getBlock(par1 + 15, par2 - l, par3 + 15) == Blocks.flowing_lava
			) || (par1World.getBlock(par1 + 15, par2 - l, par3 + 0) == Blocks.air
					|| par1World.getBlock(par1 + 15, par2 - l, par3 + 0) == Blocks.water
					|| par1World.getBlock(par1 + 15, par2 - l, par3 + 0) == Blocks.flowing_water
					|| par1World.getBlock(par1 + 15, par2 - l, par3 + 0) == Blocks.flowing_water
					|| par1World.getBlock(par1 + 15, par2 - l, par3 + 0) == Blocks.flowing_lava
			) || (par1World.getBlock(par1 + 0, par2 - l, par3 + 15) == Blocks.air
					|| par1World.getBlock(par1 + 0, par2 - l, par3 + 15) == Blocks.water
					|| par1World.getBlock(par1 + 0, par2 - l, par3 + 15) == Blocks.flowing_water
					|| par1World.getBlock(par1 + 0, par2 - l, par3 + 15) == Blocks.flowing_water
					|| par1World.getBlock(par1 + 0, par2 - l, par3 + 15) == Blocks.flowing_lava)) {
				for (int i0 = 0; i0 < 16; ++i0) {
					for (int i2 = 0; i2 < 16; ++i2) {
						par1World.setBlock(par1 + i0, par2 - l, par3 + i2, Blocks.stone);
					}
				}
			} else {
				break;
			}
		}

		for (int i0 = 0; i0 < 16; ++i0) {
			for (int i1 = 0; i1 < 16; ++i1) {
				for (int i2 = 0; i2 < 16; ++i2) {
					if (!(par1World.getBlock(par1 + i0, par2 + i1, par3 + i2) instanceof BlockFlagBase)) {
						par1World.setBlock(par1 + i0, par2 + i1, par3 + i2, Blocks.air);
					}
				}
			}
		}
		for (int i0 = 0; i0 < 16; ++i0) {
			for (int i2 = 0; i2 < 16; ++i2) {
				{
					par1World.setBlock(par1 + i0, par2 + 0, par3 + i2, Blocks.dirt);
					par1World.setBlock(par1 + i0, par2 - 1, par3 + i2, Blocks.dirt);
					par1World.setBlock(par1 + i0, par2 - 2, par3 + i2, Blocks.dirt);
				}
			}
		}


		this.BuildTent(par1World, par1 + 10, par2 + 1, par3 + 1);
		this.BuildTent(par1World, par1 + 10, par2 + 1, par3 + 5);
		this.BuildTent(par1World, par1 + 10, par2 + 1, par3 + 9);
		this.BuildTower(par1World, par1 + 1, par2 + 1, par3 + 1);
		this.BuildBox(par1World, par1 + 1, par2 + 1, par3 + 7);
		this.BuildBox(par1World, par1 + 1, par2 + 1, par3 + 10);
		this.BuildBox(par1World, par1 + 1, par2 + 1, par3 + 13);

		this.BuildChest(par1World, par1 + 10, par2 + 1, par3 + 13);
		this.BuildChest2(par1World, par1 + 10, par2 + 1, par3 + 14);
		this.BuildChest3(par1World, par1 + 13, par2 + 1, par3 + 13);
		this.BuildChest3(par1World, par1 + 13, par2 + 1, par3 + 14);


		par1World.setBlock(par1 + 7, par2 + 1, par3 + 7, mod_GVCWW2.b_flag_ger);
	}

	public void BuildChest(World par1World, int par1, int par2, int par3) {
		{
			par1World.setBlock(par1, par2, par3, Blocks.chest);
			TileEntityChest Chest;
			Chest = (TileEntityChest) par1World.getTileEntity(par1, par2, par3);
			{
				for (int i = 0; i < 9; ++i) {
					Chest.setInventorySlotContents(i, new ItemStack(mod_GVCWW2.armor_ger, 1, 0));
				}
				Chest.setInventorySlotContents(9, new ItemStack(mod_GVCWW2.gun_gew98, 1, 0));
				Chest.setInventorySlotContents(10, new ItemStack(mod_GVCWW2.gun_gew98, 1, 0));
				Chest.setInventorySlotContents(11, new ItemStack(mod_GVCWW2.gun_gew98, 1, 0));
				Chest.setInventorySlotContents(13, new ItemStack(mod_GVCWW2.gun_gew43, 1, 0));
				Chest.setInventorySlotContents(14, new ItemStack(mod_GVCWW2.gun_gew43, 1, 0));
				Chest.setInventorySlotContents(12, new ItemStack(mod_GVCWW2.gun_mp40, 1, 0));
				Chest.setInventorySlotContents(15, new ItemStack(mod_GVCWW2.gun_mg34, 1, 0));
				Chest.setInventorySlotContents(16, new ItemStack(mod_GVCWW2.gun_mg34, 1, 0));
				Chest.setInventorySlotContents(17, new ItemStack(mod_GVCWW2.gun_rpzb54, 1, 0));
				for (int i = 18; i < 27; ++i) {
					Chest.setInventorySlotContents(i, new ItemStack(mod_GVCWW2.gun_p38, 1, 0));
				}
			}
		}
	}

	public void BuildChest2(World par1World, int par1, int par2, int par3) {
		{
			par1World.setBlock(par1, par2, par3, Blocks.chest);
			TileEntityChest Chest;
			Chest = (TileEntityChest) par1World.getTileEntity(par1, par2, par3);
			{
				Chest.setInventorySlotContents(0, new ItemStack(mod_GVCWW2.gun_gew98.gunInfo.magazine[0], 1, 0));
				Chest.setInventorySlotContents(1, new ItemStack(mod_GVCWW2.gun_gew98.gunInfo.magazine[0], 1, 0));
				Chest.setInventorySlotContents(3, new ItemStack(mod_GVCWW2.gun_gew98.gunInfo.magazine[0], 1, 0));
				Chest.setInventorySlotContents(4, new ItemStack(mod_GVCWW2.gun_gew43.gunInfo.magazine[0], 1, 0));
				Chest.setInventorySlotContents(2, new ItemStack(mod_GVCWW2.gun_gew43.gunInfo.magazine[0], 1, 0));
				Chest.setInventorySlotContents(5, new ItemStack(mod_GVCWW2.gun_mp40.gunInfo.magazine[0], 1, 0));
				Chest.setInventorySlotContents(6, new ItemStack(mod_GVCWW2.gun_mg34.gunInfo.magazine[0], 1, 0));
				Chest.setInventorySlotContents(7, new ItemStack(mod_GVCWW2.gun_mg34.gunInfo.magazine[0], 1, 0));
				Chest.setInventorySlotContents(8, new ItemStack(mod_GVCWW2.gun_rpzb54.gunInfo.magazine[0], 1, 0));

				Chest.setInventorySlotContents(9, new ItemStack(mod_GVCWW2.gun_gew98.gunInfo.magazine[0], 1, 0));
				Chest.setInventorySlotContents(10, new ItemStack(mod_GVCWW2.gun_gew98.gunInfo.magazine[0], 1, 0));
				Chest.setInventorySlotContents(11, new ItemStack(mod_GVCWW2.gun_gew98.gunInfo.magazine[0], 1, 0));
				Chest.setInventorySlotContents(13, new ItemStack(mod_GVCWW2.gun_gew43.gunInfo.magazine[0], 1, 0));
				Chest.setInventorySlotContents(14, new ItemStack(mod_GVCWW2.gun_gew43.gunInfo.magazine[0], 1, 0));
				Chest.setInventorySlotContents(12, new ItemStack(mod_GVCWW2.gun_mp40.gunInfo.magazine[0], 1, 0));
				Chest.setInventorySlotContents(15, new ItemStack(mod_GVCWW2.gun_mg34.gunInfo.magazine[0], 1, 0));
				Chest.setInventorySlotContents(16, new ItemStack(mod_GVCWW2.gun_mg34.gunInfo.magazine[0], 1, 0));
				Chest.setInventorySlotContents(17, new ItemStack(mod_GVCWW2.gun_rpzb54.gunInfo.magazine[0], 1, 0));

				for (int i = 18; i < 27; ++i) {
					Chest.setInventorySlotContents(i, new ItemStack(mod_GVCWW2.gun_grenade, 4, 0));
				}
			}
		}
	}

	public void BuildChest3(World par1World, int par1, int par2, int par3) {
		{
			par1World.setBlock(par1, par2, par3, Blocks.chest);
			TileEntityChest Chest;
			Chest = (TileEntityChest) par1World.getTileEntity(par1, par2, par3);
			{
				for (int i = 0; i < 9; ++i) {
					Chest.setInventorySlotContents(i, new ItemStack(Items.bread, 2, 0));
				}
				for (int i = 9; i < 18; ++i) {
					Chest.setInventorySlotContents(i, new ItemStack(Items.bread, 2, 0));
				}
				for (int i = 18; i < 27; ++i) {
					Chest.setInventorySlotContents(i, new ItemStack(Items.bread, 2, 0));
				}
			}
		}
	}

	public void BuildTent(World par1World, int par1, int par2, int par3) {
		for (int i0 = 0; i0 < 5; ++i0) {
			for (int i1 = 0; i1 < 3; ++i1) {
				for (int i2 = 0; i2 < 3; ++i2) {
					{
						par1World.setBlock(par1 + i0, par2 + i1, par3 + i2, Blocks.wool, 9, 2);
					}
				}
			}
			par1World.setBlock(par1 + i0, par2 + 2, par3 + 0, Blocks.air);
			par1World.setBlock(par1 + i0, par2 + 2, par3 + 2, Blocks.air);
		}
		for (int i0 = 0; i0 < 4; ++i0) {
			par1World.setBlock(par1 + i0, par2 + 0, par3 + 1, Blocks.air);
			par1World.setBlock(par1 + i0, par2 + 1, par3 + 1, Blocks.air);
		}
		par1World.setBlock(par1 + 1, par2 + 0, par3 + 1, Blocks.bed, 3, 3);
		par1World.setBlock(par1 + 2, par2 + 0, par3 + 1, Blocks.bed, 11, 3);
		par1World.setBlock(par1 + 3, par2 + 0, par3 + 1, Blocks.planks, 0, 2);
	}

	public void BuildTower(World par1World, int par1, int par2, int par3) {
		for (int i0 = 0; i0 < 6; ++i0) {
			for (int i1 = 0; i1 < 3; i1 += 2) {
				for (int i2 = 0; i2 < 3; i2 += 2) {
					{
						par1World.setBlock(par1 + i1, par2 + i0, par3 + i2, Blocks.fence);
					}
				}
			}
			par1World.setBlock(par1 + 1, par2 + i0, par3 + 1, Blocks.air);
		}
		for (int i1 = 0; i1 < 3; ++i1) {
			for (int i2 = 0; i2 < 3; ++i2) {
				{
					par1World.setBlock(par1 + i1, par2 + 6, par3 + i2, Blocks.planks, 0, 2);
					par1World.setBlock(par1 + i1, par2 + 7, par3 + i2, Blocks.fence);
				}
			}
		}
		par1World.setBlock(par1 + 3, par2 + 6, par3 + 1, Blocks.wool, 9, 2);
		par1World.setBlock(par1 + 4, par2 + 6, par3 + 1, Blocks.wool, 9, 2);
		par1World.setBlock(par1 + 1, par2 + 9, par3 + 1, Blocks.air);
	}

	public void BuildBox(World par1World, int par1, int par2, int par3) {
		for (int i0 = 0; i0 < 3; ++i0) {
			for (int i1 = 0; i1 < 2; ++i1) {
				for (int i2 = 0; i2 < 2; ++i2) {
					{
						par1World.setBlock(par1 + i0, par2 + i1, par3 + i2, Blocks.planks, 0, 2);
					}
				}
			}
		}
	}
}
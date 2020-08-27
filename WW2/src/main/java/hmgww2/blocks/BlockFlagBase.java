package hmgww2.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import hmgww2.Nation;
import hmgww2.blocks.tile.FlagType;
import hmgww2.blocks.tile.TileEntityBase;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
//import net.minecraft.world.gen.structure.StructureStrongholdPieces;;

public class BlockFlagBase extends BlockContainer implements ITileEntityProvider {
	Nation nation;
	FlagType flagType;
	int maxs;
	int spawntime;
	public int[] spawnoffset = {0, 0, 0};
	public int flagRange = 15;
	@SideOnly(Side.CLIENT)
	private IIcon TopIcon;

	@SideOnly(Side.CLIENT)
	private IIcon SideIcon;

	public BlockFlagBase() {
		super(Material.iron);
		setHardness(50F);
		setResistance(2000.0F);
		setStepSound(Block.soundTypeStone);
		this.setTickRandomly(true);

	}

	/**
	 * Is this block (a) opaque and (b) a full 1m cube?  This determines whether or not to render the shared face of two
	 * adjacent blocks and also whether the player can attach torches, redstone wire, etc to this block.
	 */
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int par2, int par3, int par4) {
		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1F, 1F, 1F);
	}

	public void onBlockAdded(World p_149726_1_, int p_149726_2_, int p_149726_3_, int p_149726_4_) {
	}

	/**
	 * If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc)
	 */
	public boolean renderAsNormalBlock() {
		return false;
	}

	/**
	 * Called upon block activation (right click on the block.)
	 */
	public boolean onBlockActivated(World par1World, int par1, int par2, int par3, EntityPlayer p_149727_5_, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_) {
		{
			TileEntityBase flag = (TileEntityBase) par1World.getTileEntity(par1, par2, par3);
			if (flag != null) {
				if (flag.spawn) {
					flag.spawn = false;
				} else {
					flag.spawn = true;
				}
			}
			return true;
		}
	}

	/**
	 * Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been
	 * cleared to be reused)
	 */
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World p_149668_1_, int p_149668_2_, int p_149668_3_, int p_149668_4_) {
		return null;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int a) {

		return new TileEntityBase(nation, flagType, maxs, spawntime, spawnoffset, flagRange);
	}
}
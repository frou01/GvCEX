package hmggvcutil.Item;


import hmggvcutil.GVCUtils;
import hmggvcutil.entity.GVCEntityBox;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;


public class GVCItemBox extends Item {

	public GVCItemBox() {
		setCreativeTab(GVCUtils.tabgvc);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
	{
//	     par3EntityPlayer.capabilities.allowFlying = !par3EntityPlayer.capabilities.allowFlying;
		//�f���炵���l�^�@�\�����肪�Ƃ��������܂���()
		return par1ItemStack;
	}
	public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
	{
		if (par3World.isRemote)
		{
			return true;
		}
		else if (par7 != 1)
		{
			return false;
		}
		else
		{
			++par5;
			int var12 = MathHelper.floor_double((double)(par2EntityPlayer.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
			GVCEntityBox entityskeleton = new GVCEntityBox(par3World);
			entityskeleton.hasItems = false;
			entityskeleton.setLocationAndAngles(par4+0.5, par5, par6+0.5, var12, 0.0F);
			par3World.spawnEntityInWorld(entityskeleton);
			--par1ItemStack.stackSize;
			return true;
		}

	}

	public void onUpdate(ItemStack itemstack, World world, Entity entity, int i, boolean flag)
	{

		//world.getGameRules().getGameRuleBooleanValue("keepInventory");

		super.onUpdate(itemstack, world, entity, i, flag);
	}

		/*public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player) {
			//FMLClientHandler.instance().showGuiScreen(player);
			//player.openGui(GVCUtils.INSTANCE, GVCUtils.GUI_ID, world, MathHelper.ceiling_double_int(player.posX), MathHelper.ceiling_double_int(player.posY), MathHelper.ceiling_double_int(player.posZ));
		    return itemStack;
		}


		/*
		public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
	    {
		    for (int ix = 0; ix < 3; ++ix){
			 for (int iy = 0; iy < 3; ++iy){
    		 for (int iz = 0; iz < 3; ++iz){
			Block pBlock = par3World.getBlock(par4-1+ix, par5-1+iy, par6-1+iz);
			int pMetadata = par3World.getBlockMetadata(par4-1+ix, par5-1+iy, par6-1+iz);
			removeBlock(par4-1+ix, par5-1+iy, par6-1+iz, pBlock, pMetadata,par3World);
			pBlock.onBlockDestroyedByPlayer(par3World, par4-1+ix, par5-1+iy, par6-1+iz, pMetadata);
			pBlock.dropBlockAsItem(par3World, par4-1+ix, par5-1+iy, par6-1+iz, pMetadata, pMetadata);


    		 }
    		 }
			}

			return true;
	    }
		protected void removeBlock(int pX, int pY, int pZ, Block pBlock, int pMetadata, World par3World) {
			par3World.playAuxSFX(2001, pX, pY, pZ, Block.getIdFromBlock(pBlock) + (pMetadata << 12));
			par3World.setBlockToAir(pX, pY, pZ);
		}

		/*public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
	    {
			//par3EntityPlayer.displayGUIWorkbench(MathHelper.ceiling_double_int(par3EntityPlayer.posX), MathHelper.ceiling_double_int(par3EntityPlayer.posY), MathHelper.ceiling_double_int(par3EntityPlayer.posZ));
			//par3EntityPlayer.openGui(mod, modGuiId, world, x, y, z)
			return par1ItemStack;
	    }*/

}

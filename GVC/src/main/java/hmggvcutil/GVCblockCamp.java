package hmggvcutil;
 
import java.util.Random;
 
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.SideOnly;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.util.IIcon;
 
public class GVCblockCamp extends Block
{
    @SideOnly(Side.CLIENT)
    private IIcon TopIcon;
 
    @SideOnly(Side.CLIENT)
    private IIcon SideIcon;
 
    public GVCblockCamp() {
        super(Material.rock);
        setCreativeTab(GVCUtils.tabgvc);
        
        setHardness(1.5F);
        setResistance(1.0F);
        setStepSound(Block.soundTypeStone);
	/*setBlockUnbreakable();*/
	/*setTickRandomly(true);*/
	/*disableStats();*/
        setLightOpacity(1);
        setLightLevel(1.0F);
    }
 
    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float posX, float posY, float posZ){
        //�u���b�N���E�N���b�N�����ۂ̓���
    	//world.setBlock(x+3, y+0, z-3, Blocks.fence);
        return true;
    }
    
    @Override
    public void onBlockClicked(World world, int x, int y, int z, EntityPlayer player){
        //�u���b�N�����N���b�N�����ۂ̓���
    }
 
    @Override
    public void onNeighborBlockChange(World par1World, int x, int y, int z, Block neighborBlock){
        //��͂̃u���b�N���X�V���ꂽ�ۂ̓���
    	//par1World.setBlock(x+3, y+0, z-3, Blocks.fence);
    	
        
    }
 
    @Override
    public int quantityDropped(int meta, int fortune, Random random){
        //�h���b�v����A�C�e����Ԃ�
        return quantityDroppedWithBonus(fortune, random);
    }
 
    @Override
    public int quantityDropped(Random random){
        //�h���b�v������ʂ�Ԃ�
        return 1;
    }
    
    public void onBlockAdded(World par1World, int par2, int par3, int par4)
    {
    	/*par1World.setBlock(par2+3, par3+0, par4-3, Blocks.fence);
    	par1World.setBlock(par2+3, par3+0, par4-2, Blocks.wool,13,2);
    	par1World.setBlock(par2+3, par3+0, par4-1, Blocks.wool,13,2);
    	par1World.setBlock(par2+3, par3+0, par4+1, Blocks.wool,13,2);
    	par1World.setBlock(par2+3, par3+0, par4+2, Blocks.wool,13,2);
    	par1World.setBlock(par2+3, par3+0, par4+3, Blocks.fence);
    	
    	par1World.setBlock(par2-3, par3+0, par4-3, Blocks.fence);
    	par1World.setBlock(par2-3, par3+0, par4-2, Blocks.wool,13,2);
    	par1World.setBlock(par2-3, par3+0, par4-1, Blocks.wool,13,2);
    	par1World.setBlock(par2-3, par3+0, par4+1, Blocks.wool,13,2);
    	par1World.setBlock(par2-3, par3+0, par4+2, Blocks.wool,13,2);
    	par1World.setBlock(par2-3, par3+0, par4+3, Blocks.fence);
    	
    	par1World.setBlock(par2-2, par3+0, par4-3, Blocks.wool,13,2);
    	par1World.setBlock(par2-1, par3+0, par4-3, Blocks.wool,13,2);
    	par1World.setBlock(par2+1, par3+0, par4-3, Blocks.wool,13,2);
    	par1World.setBlock(par2+2, par3+0, par4-3, Blocks.wool,13,2);
    	
    	par1World.setBlock(par2-2, par3+0, par4+3, Blocks.wool,13,2);
    	par1World.setBlock(par2-1, par3+0, par4+3, Blocks.wool,13,2);
    	par1World.setBlock(par2+1, par3+0, par4+3, Blocks.wool,13,2);
    	par1World.setBlock(par2+2, par3+0, par4+3, Blocks.wool,13,2);
    	
    	
    	//2
    	par1World.setBlock(par2+3, par3+1, par4-3, Blocks.fence);
    	par1World.setBlock(par2+3, par3+1, par4-2, Blocks.wool,13,2);
    	par1World.setBlock(par2+3, par3+1, par4-1, Blocks.wool,13,2);
    	par1World.setBlock(par2+3, par3+1, par4+1, Blocks.wool,13,2);
    	par1World.setBlock(par2+3, par3+1, par4+2, Blocks.wool,13,2);
    	par1World.setBlock(par2+3, par3+1, par4+3, Blocks.fence);
    	
    	par1World.setBlock(par2-3, par3+1, par4-3, Blocks.fence);
    	par1World.setBlock(par2-3, par3+1, par4-2, Blocks.wool,13,2);
    	par1World.setBlock(par2-3, par3+1, par4-1, Blocks.wool,13,2);
    	par1World.setBlock(par2-3, par3+1, par4+1, Blocks.wool,13,2);
    	par1World.setBlock(par2-3, par3+1, par4+2, Blocks.wool,13,2);
    	par1World.setBlock(par2-3, par3+1, par4+3, Blocks.fence);
    	
    	par1World.setBlock(par2-2, par3+1, par4-3, Blocks.wool,13,2);
    	par1World.setBlock(par2-1, par3+1, par4-3, Blocks.wool,13,2);
    	par1World.setBlock(par2+1, par3+1, par4-3, Blocks.wool,13,2);
    	par1World.setBlock(par2+2, par3+1, par4-3, Blocks.wool,13,2);
    	
    	par1World.setBlock(par2-2, par3+1, par4+3, Blocks.wool,13,2);
    	par1World.setBlock(par2-1, par3+1, par4+3, Blocks.wool,13,2);
    	par1World.setBlock(par2+1, par3+1, par4+3, Blocks.wool,13,2);
    	par1World.setBlock(par2+2, par3+1, par4+3, Blocks.wool,13,2);
    	
    	
    	//3
    	par1World.setBlock(par2+3, par3+2, par4-3, Blocks.fence);
    	par1World.setBlock(par2+3, par3+2, par4-2, Blocks.fence);
    	par1World.setBlock(par2+3, par3+2, par4-1, Blocks.fence);
    	par1World.setBlock(par2+3, par3+2, par4+0, Blocks.fence);
    	par1World.setBlock(par2+3, par3+2, par4+1, Blocks.fence);
    	par1World.setBlock(par2+3, par3+2, par4+2, Blocks.fence);
    	par1World.setBlock(par2+3, par3+2, par4+3, Blocks.fence);
    	
    	par1World.setBlock(par2+2, par3+2, par4-3, Blocks.fence);
    	par1World.setBlock(par2+2, par3+2, par4-2, Blocks.wool,13,2);
    	par1World.setBlock(par2+2, par3+2, par4-1, Blocks.wool,13,2);
    	par1World.setBlock(par2+2, par3+2, par4+0, Blocks.wool,13,2);
    	par1World.setBlock(par2+2, par3+2, par4+1, Blocks.wool,13,2);
    	par1World.setBlock(par2+2, par3+2, par4+2, Blocks.wool,13,2);
    	par1World.setBlock(par2+2, par3+2, par4+3, Blocks.fence);
    	
    	par1World.setBlock(par2+1, par3+2, par4-3, Blocks.fence);
    	par1World.setBlock(par2+1, par3+2, par4-2, Blocks.wool,13,2);
    	par1World.setBlock(par2+1, par3+2, par4-1, Blocks.wool,13,2);
    	par1World.setBlock(par2+1, par3+2, par4+0, Blocks.wool,13,2);
    	par1World.setBlock(par2+1, par3+2, par4+1, Blocks.wool,13,2);
    	par1World.setBlock(par2+1, par3+2, par4+2, Blocks.wool,13,2);
    	par1World.setBlock(par2+1, par3+2, par4+3, Blocks.fence);
    	
    	par1World.setBlock(par2+0, par3+2, par4-3, Blocks.fence);
    	par1World.setBlock(par2+0, par3+2, par4-2, Blocks.wool,13,2);
    	par1World.setBlock(par2+0, par3+2, par4-1, Blocks.wool,13,2);
    	par1World.setBlock(par2+0, par3+2, par4+0, Blocks.wool,13,2);
    	par1World.setBlock(par2+0, par3+2, par4+1, Blocks.wool,13,2);
    	par1World.setBlock(par2+0, par3+2, par4+2, Blocks.wool,13,2);
    	par1World.setBlock(par2+0, par3+2, par4+3, Blocks.fence);
    	
    	par1World.setBlock(par2-1, par3+2, par4-3, Blocks.fence);
    	par1World.setBlock(par2-1, par3+2, par4-2, Blocks.wool,13,2);
    	par1World.setBlock(par2-1, par3+2, par4-1, Blocks.wool,13,2);
    	par1World.setBlock(par2-1, par3+2, par4+0, Blocks.wool,13,2);
    	par1World.setBlock(par2-1, par3+2, par4+1, Blocks.wool,13,2);
    	par1World.setBlock(par2-1, par3+2, par4+2, Blocks.wool,13,2);
    	par1World.setBlock(par2-1, par3+2, par4+3, Blocks.fence);
    	
    	par1World.setBlock(par2-2, par3+2, par4-3, Blocks.fence);
    	par1World.setBlock(par2-2, par3+2, par4-2, Blocks.wool,13,2);
    	par1World.setBlock(par2-2, par3+2, par4-1, Blocks.wool,13,2);
    	par1World.setBlock(par2-2, par3+2, par4+0, Blocks.wool,13,2);
    	par1World.setBlock(par2-2, par3+2, par4+1, Blocks.wool,13,2);
    	par1World.setBlock(par2-2, par3+2, par4+2, Blocks.wool,13,2);
    	par1World.setBlock(par2-2, par3+2, par4+3, Blocks.fence);
    	
    	par1World.setBlock(par2-3, par3+2, par4-3, Blocks.fence);
    	par1World.setBlock(par2-3, par3+2, par4-2, Blocks.fence);
    	par1World.setBlock(par2-3, par3+2, par4-1, Blocks.fence);
    	par1World.setBlock(par2-3, par3+2, par4+0, Blocks.fence);
    	par1World.setBlock(par2-3, par3+2, par4+1, Blocks.fence);
    	par1World.setBlock(par2-3, par3+2, par4+2, Blocks.fence);
    	par1World.setBlock(par2-3, par3+2, par4+3, Blocks.fence);
    	*/
    	 for (int i0 = 0; i0 < 7; ++i0){
         	for (int i1 = 0; i1 < 3; ++i1){
         		par1World.setBlock(par2+i0, par3+i1, par4+0, Blocks.wool,13,2);
         		par1World.setBlock(par2+i0, par3+i1, par4+7, Blocks.wool,13,2);
         		par1World.setBlock(par2+0, par3+i1, par4+i0, Blocks.wool,13,2);
         		par1World.setBlock(par2+7, par3+i1, par4+i0, Blocks.wool,13,2);
         		
         		par1World.setBlock(par2+0, par3+i1, par4+0, Blocks.fence);
             	par1World.setBlock(par2+7, par3+i1, par4+0, Blocks.fence);
             	par1World.setBlock(par2+0, par3+i1, par4+7, Blocks.fence);
             	par1World.setBlock(par2+7, par3+i1, par4+7, Blocks.fence);
         	}
         	
         	
         	par1World.setBlock(par2+i0, par3+2, par4+0, Blocks.fence);
         	par1World.setBlock(par2+i0, par3+2, par4+7, Blocks.fence);
         	par1World.setBlock(par2+0, par3+2, par4+i0, Blocks.fence);
         	par1World.setBlock(par2+7, par3+2, par4+i0, Blocks.fence);
         	
         }
    	
    	//kazari
    	//par1World.setBlock(par2-1, par3+0, par4-2, Blocks.furnace);
    	//par1World.setBlock(par2-2, par3+0, par4-2, Blocks.furnace);
    	//par1World.setBlock(par2+1, par3+0, par4-2, Blocks.chest);
    	//par1World.setBlock(par2+2, par3+0, par4-2, Blocks.chest);
    	
    	//par1World.setBlock(par2-1, par3+0, par4+2, Blocks.crafting_table);
    	//par1World.setBlock(par2-2, par3+0, par4+2, Blocks.cauldron);
    	//par1World.setBlock(par2+1, par3+0, par4+2, Blocks.tnt);
    	//par1World.setBlock(par2+2, par3+0, par4+2, Blocks.tnt);
    	par1World.setBlock(par2+1, par3+0, par4+2, Blocks.bed, 3, 3);
    	par1World.setBlock(par2+2, par3+0, par4+2, Blocks.bed, 3+8, 3);
    	
        //par1World.setBlock(par2, par3, par4, Blocks.air);
        
    }
    
    /*@Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister par1IconRegister)
    {
        this.TopIcon = par1IconRegister.registerIcon("samplemod:block_sample");
        this.SideIcon = par1IconRegister.registerIcon("samplemod:block_sample-side");
    }
 
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int par1, int par2)
    {
         if(par1 == 0 || par1 == 1)
         {
                  return TopIcon;
         }
         if(par1 == 2 || par1 == 3 || par1 == 4 || par1 == 5 || par1 == 6)
         {
                  return SideIcon;
         }
         else
         {
                 return null;
         }
    }*/
}
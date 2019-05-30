package hmggvcmob.block;

import hmggvcmob.GVCMobPlus;
import hmggvcmob.tile.TileEntityFlag;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Random;

import static hmggvcmob.GVCMobPlus.beacon_defensive;
import static hmggvcmob.GVCMobPlus.fn_Supplyflag;

public class GVCBlockFlag extends BlockContainer {
    public boolean withPlane = false;
    public Class[] TeamEntityclass;
    public ResourceLocation flagtexture = new ResourceLocation("gvcmob:textures/model/flagtexture.png");
    Random rnd = new Random();
    public GVCBlockFlag(Class[] TeamEntitys, ResourceLocation flagtexture) {
        super(Material.rock);
        TeamEntityclass = TeamEntitys;
        setCreativeTab(CreativeTabs.tabMisc);
        setHardness(1.5F);
        setResistance(1.0F);
        setStepSound(Block.soundTypeStone);
        this.flagtexture = flagtexture;
    }
    public int tickRate(World p_149738_1_)
    {
        return 1;
    }
    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_)
    {
        if(this == fn_Supplyflag&& !p_149915_1_.isRemote){
            for(Object obj : p_149915_1_.playerEntities){
                if(obj instanceof EntityPlayer){
                    EntityPlayer entityPlayer = (EntityPlayer) obj;
                    entityPlayer.addStat(beacon_defensive,1);
                }
            }
        }
        return new TileEntityFlag(TeamEntityclass,this);
    }
}

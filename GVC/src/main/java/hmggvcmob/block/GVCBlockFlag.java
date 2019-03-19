package hmggvcmob.block;

import hmggvcmob.GVCMobPlus;
import hmggvcmob.tile.TileEntityFlag;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Random;

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
        return 4;
    }
    public void updateTick(World world, int par1, int par2, int par3, Random p_149674_5_)
    {
        world.scheduleBlockUpdate(par1, par2, par3, GVCMobPlus.fn_mobspawner, 10);
        if(!world.isRemote){
            List nearEntitys =  world.getEntitiesWithinAABBExcludingEntity(world.getClosestPlayer(par1, par2, par3,16),AxisAlignedBB.getBoundingBox(par1-8, par2-8, par3-8,par1+8, par2+8, par3+8));
            if(nearEntitys.size()<10) {
                int TeamEntitysNumber = rnd.nextInt(TeamEntityclass.length);
                Entity entityskeleton = null;
                try {
                    entityskeleton = (Entity)TeamEntityclass[TeamEntitysNumber].getConstructor(World.class).newInstance(world);
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
                if (entityskeleton != null) {
                    entityskeleton.setLocationAndAngles(par1 + 0.5, par2 + 1, par3 + 0.5, 0, 0.0F);
                    world.spawnEntityInWorld(entityskeleton);
                }
            }
        }
    }
    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_)
    {
        return new TileEntityFlag(TeamEntityclass,this);
    }
}

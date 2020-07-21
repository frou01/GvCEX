package DungeonGeneratorBase;

import DungeonGeneratorBase.thread.threadTypeGen;
import cpw.mods.fml.common.registry.GameRegistry;
import handmadeguns.HMGGunMaker;
import handmadeguns.entity.PlacedGunEntity;
import handmadeguns.items.guns.HMGItem_Unified_Guns;
import hmggvcmob.GVCMobPlus;
import hmggvcmob.entity.guerrilla.EntityGBase;
import hmggvcmob.entity.guerrilla.EntityGBases;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraftforge.common.util.ForgeDirection;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Random;

import static DungeonGeneratorBase.mod_DungeonGeneratorBase.dungeonDataList;

public class ComponentDungeonBase extends StructureComponent {
    protected int hight = -1;
    protected int id = -1;
    int dir;
    String name;
    DungeonData dungeonData;
    public ComponentDungeonBase() {
        super();
    }
    public ComponentDungeonBase(DungeonData_withSettings dungeonData_withSettings,Random par2Random, int par3, int par4, int id,int dir) {
        DungeonData_withSettings dungeonDataWithSettings;
        dungeonDataWithSettings = dungeonData_withSettings;
        this.name = dungeonDataWithSettings.name;
        this.id = id;
        this.dir = dir;
        dungeonData = dungeonDataWithSettings.dungeonData.get(this.id);
        switch (dir){
            default:
                this.boundingBox = new StructureBoundingBox(dungeonData.minx,  dungeonData.minz, dungeonData.maxx, dungeonData.maxz);
                break;
            case 0:
                this.boundingBox = new StructureBoundingBox(+ dungeonData.minx,  + dungeonData.minz,  + dungeonData.maxx, + dungeonData.maxz);
                boundingBox.offset(par3 + dungeonData.offset[0],0,par4 + dungeonData.offset[2]);
                break;
            case 1:
                this.boundingBox = new StructureBoundingBox(- dungeonData.maxz,  + dungeonData.minx,  - dungeonData.minz, + dungeonData.maxx);
                boundingBox.offset(par3 - dungeonData.offset[2],0,par4 + dungeonData.offset[0]);
                break;
            case 2:
                this.boundingBox = new StructureBoundingBox(+ dungeonData.minz,  - dungeonData.maxx,  + dungeonData.maxz, - dungeonData.minx);
                boundingBox.offset(par3 + dungeonData.offset[2],0,par4 - dungeonData.offset[0]);
                break;
            case 3:
                this.boundingBox = new StructureBoundingBox(- dungeonData.maxx,  - dungeonData.maxz,  - dungeonData.minx, - dungeonData.minz);
                boundingBox.offset(par3 - dungeonData.offset[0],0,par4 - dungeonData.offset[2]);
                break;
        }
    }

    @Override
    //write
    protected void func_143012_a(NBTTagCompound p_143012_1_) {
        p_143012_1_.setInteger("hight",hight);
        p_143012_1_.setInteger("ID",id);
        p_143012_1_.setInteger("DIR",dir);
        p_143012_1_.setString("name",name);
    }

    @Override
    //read
    protected void func_143011_b(NBTTagCompound p_143011_1_) {
        hight = p_143011_1_.getInteger("hight");
        this.id = p_143011_1_.getInteger("ID");
        this.dir = p_143011_1_.getInteger("DIR");
        this.name = p_143011_1_.getString("name");
        dungeonData = dungeonDataList.get(name).dungeonData.get(id);
    }
    public static int getTopSolidOrLiquidBlock(World world , int p_72825_1_, int p_72825_2_) {
        Chunk chunk = world.getChunkFromBlockCoords(p_72825_1_, p_72825_2_);
        int x = p_72825_1_;
        int z = p_72825_2_;
        int k = chunk.getTopFilledSegment() + 15;
        p_72825_1_ &= 15;

        for (p_72825_2_ &= 15; k > 0; --k) {
            Block block = chunk.getBlock(p_72825_1_, k, p_72825_2_);

            if (block.getMaterial().blocksMovement() && block.getMaterial() != Material.leaves && (!block.isFoliage(world, x, k, z) && block != Blocks.water)) {
                return k + 1;
            }
        }

        return -1;
    }
    public static int getTopSolidBlock(World world , int p_72825_1_, int p_72825_2_) {
        Chunk chunk = world.getChunkFromBlockCoords(p_72825_1_, p_72825_2_);
        int x = p_72825_1_;
        int z = p_72825_2_;
        int k = chunk.getTopFilledSegment() + 15;
        p_72825_1_ &= 15;

        for (p_72825_2_ &= 15; k > 0; --k) {
            Block block = chunk.getBlock(p_72825_1_, k, p_72825_2_);

            if (block.getMaterial().blocksMovement() && block.getMaterial() != Material.leaves && (!block.isFoliage(world, x, k, z))) {
                return k + 1;
            }
        }

        return -1;
    }
    protected int getAverageGroundLevel(World p_74889_1_, StructureBoundingBox p_74889_2_)
    {
        int i = 0;
        int j = 0;

        for (int k = this.boundingBox.minZ; k <= this.boundingBox.maxZ; ++k)
        {
            for (int l = this.boundingBox.minX; l <= this.boundingBox.maxX; ++l)
            {
                if (p_74889_2_.isVecInside(l, 64, k))
                {
                    i += Math.max(p_74889_1_.getTopSolidOrLiquidBlock(l, k), p_74889_1_.provider.getAverageGroundLevel());
                    ++j;
                }
            }
        }

        if (j == 0)
        {
            return -1;
        }
        else
        {
            return i / j;
        }
    }

    @Override
    public boolean addComponentParts(World world, Random random, StructureBoundingBox structureboundingbox) {
        if(!dungeonData.overWrite_Ocean && isLiquidInStructureBoundingBox(world,structureboundingbox))return true;
        coordBaseMode = 0;
        if (this.hight < 0)
        {
            this.hight = this.getAverageGroundLevel(world, structureboundingbox);

            if (this.hight < 0)
            {
                return true;
            }
            if (this.hight > dungeonData.MaxHeight)
            {
                this.hight = dungeonData.MaxHeight;
            }else
            if (this.hight < dungeonData.minHeight)
            {
                this.hight = dungeonData.minHeight;
            }
//            System.out.println("" + this.hight);

            this.boundingBox.offset(0, this.hight - this.boundingBox.minY + dungeonData.offset[1], 0);
        }
        int Ystart = boundingBox.minY;

        threadTypeGen thread = new threadTypeGen(structureboundingbox,
                dungeonData,
                world,
                boundingBox,
                dir,
                Ystart,
                random);
        thread.start();
        return true;
    }
    public static void addChestHelper (Random random,World world,int x,int y,int z,BlockPos pos,DungeonData dungeonData){

        TileEntityChest Chest;
        Chest  = (TileEntityChest) world.getTileEntity(x,y,z);
        if(dungeonData.chests.containsKey(pos)){
            ItemAndNum[] items = dungeonData.chests.get(pos).getItemObj();
            for(int id = 0;id < 27;id ++) {
                if(items[id] != null && (items[id].item!= null || items[id].block != null))Chest.setInventorySlotContents(id, items[id].item != null ? new ItemStack(items[id].item,items[id].num) : new ItemStack(items[id].block,items[id].num));
            }
        }else {
            for (int s = 0; s < 9; ++s) {
                Item gun = (Item) HMGGunMaker.Guns.get(random.nextInt(HMGGunMaker.Guns.size()));
                if (gun instanceof HMGItem_Unified_Guns) {
                    if (!((HMGItem_Unified_Guns) gun).gunInfo.canInRoot) gun = null;
                    if (gun != null) {
                        Chest.setInventorySlotContents(s, new ItemStack(gun));
                        if (((HMGItem_Unified_Guns) gun).getcurrentMagazine(null) != null)
                            Chest.setInventorySlotContents(9 + s, new ItemStack(((HMGItem_Unified_Guns) gun).getcurrentMagazine(null)));
                    }
                }
            }
        }
    }
}

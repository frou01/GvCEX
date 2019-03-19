package DungeonGeneratorBase;

import cpw.mods.fml.common.registry.GameRegistry;
import handmadeguns.HMGAddGunsNew;
import handmadeguns.entity.PlacedGunEntity;
import handmadeguns.items.guns.HMGItem_Unified_Guns;
import hmggvcmob.GVCMobPlus;
import hmggvcmob.entity.friend.GVCEntityPMCTank;
import hmggvcmob.entity.guerrilla.EntityGBase;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLadder;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Random;

public abstract class ComponentDungeonBase extends StructureComponent {
    protected int hight = -1;
    protected int id = -1;
    int dir;
    String name;
    DangeonData dangeonData;
    public ComponentDungeonBase() {
        super();
    }
    public ComponentDungeonBase(Random par2Random, int par3, int par4) {
        dangeonData = getDangeonDatas().get(id = 0);
        this.boundingBox = new StructureBoundingBox(par3 + dangeonData.minx, 0, par4 + dangeonData.minz, par3+dangeonData.maxx + 1, 256, par4 + dangeonData.maxz + 1);
    }
    public ComponentDungeonBase(Random par2Random, int par3, int par4, int id,int dir) {
        this.id = id;
        this.dir = dir;
        dangeonData = getDangeonDatas().get(this.id );
        switch (dir){
            default:
                this.boundingBox = new StructureBoundingBox(dangeonData.minx,  dangeonData.minz, dangeonData.maxx, dangeonData.maxz);
                break;
            case 0:
                this.boundingBox = new StructureBoundingBox(+ dangeonData.minx,  + dangeonData.minz,  + dangeonData.maxx, + dangeonData.maxz);
                boundingBox.offset(par3 + dangeonData.offset[0],0,par4 + dangeonData.offset[2]);
                break;
            case 1:
                this.boundingBox = new StructureBoundingBox(- dangeonData.maxz,  + dangeonData.minx,  - dangeonData.minz, + dangeonData.maxx);
                boundingBox.offset(par3 - dangeonData.offset[2],0,par4 + dangeonData.offset[0]);
                break;
            case 2:
                this.boundingBox = new StructureBoundingBox(+ dangeonData.minz,  - dangeonData.maxx,  + dangeonData.maxz, - dangeonData.minx);
                boundingBox.offset(par3 + dangeonData.offset[2],0,par4 - dangeonData.offset[0]);
                break;
            case 3:
                this.boundingBox = new StructureBoundingBox(- dangeonData.maxx,  - dangeonData.maxz,  - dangeonData.minx, - dangeonData.minz);
                boundingBox.offset(par3 - dangeonData.offset[0],0,par4 - dangeonData.offset[2]);
                break;




        }
    }

    @Override
    //write
    protected void func_143012_a(NBTTagCompound p_143012_1_) {
        p_143012_1_.setInteger("hight",hight);
        p_143012_1_.setInteger("ID",id);
        p_143012_1_.setInteger("DIR",dir);
    }

    @Override
    //read
    protected void func_143011_b(NBTTagCompound p_143011_1_) {
        hight = p_143011_1_.getInteger("hight");
        this.id = p_143011_1_.getInteger("ID");
        this.dir = p_143011_1_.getInteger("DIR");
        dangeonData = getDangeonDatas().get(id);
    }
    public abstract ArrayList<DangeonData> getDangeonDatas();
    public int getTopSolidOrLiquidBlock(World world , int p_72825_1_, int p_72825_2_) {
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
        if(!dangeonData.overWrite_Ocean && isLiquidInStructureBoundingBox(world,structureboundingbox))return false;
        coordBaseMode = 0;
        int Ystart = 70;
        if (this.hight < 0)
        {
            this.hight = this.getAverageGroundLevel(world, structureboundingbox);

            if (this.hight < 0)
            {
                return true;
            }
            if (this.hight > dangeonData.MaxHeight)
            {
                this.hight = dangeonData.MaxHeight;
            }else
            if (this.hight < dangeonData.minHeight)
            {
                this.hight = dangeonData.minHeight;
            }

            this.boundingBox.offset(0, this.hight - this.boundingBox.minY + dangeonData.offset[1], 0);
        }
        for (int i = structureboundingbox.minX; i <= structureboundingbox.maxX; ++i) {
            for (int j = structureboundingbox.minZ; j <= structureboundingbox.maxZ; ++j) {
                if (structureboundingbox.isVecInside(i, 64, j)) {
                    int groundlevel = getTopSolidOrLiquidBlock(world,i,j);
                    Ystart = dangeonData.fixed_from_Ground ? groundlevel + dangeonData.offset[1]:boundingBox.minY;
                    if(dangeonData.fillStone_to_Ground){
                        for(int l = 0;l < Ystart - groundlevel;l++){
                            world.setBlock(i, Ystart - l, j, Blocks.stone);
                        }
                    }

                    int inStructuerPosX = i - this.boundingBox.minX;
                    int inStructuerPosZ = j - this.boundingBox.minZ;
                    for (int h2 = -1; h2 < 128; h2++) {
                        BlockPos pos = new BlockPos(0,0,0);
                        switch (dir) {
                            case 0:
                                pos.x = inStructuerPosX;
                                pos.y = h2;
                                pos.z = inStructuerPosZ;
                                break;
                            case 1:
                                pos.x = inStructuerPosZ;
                                pos.y = h2;
                                pos.z = dangeonData.maxz - inStructuerPosX;
                                break;
                            case 2:
                                pos.x = dangeonData.maxx - inStructuerPosZ;
                                pos.y = h2;
                                pos.z = inStructuerPosX;
                                break;
                            case 3:
                                pos.x = dangeonData.maxx - inStructuerPosX;
                                pos.y = h2;
                                pos.z = dangeonData.maxz - inStructuerPosZ;
                                break;
                        }
                        Block temp = dangeonData.blocks.get(pos);
                        if (temp != null) {
                            try {
                                int meta = 0;
                                int dir = 0;
                                if (dangeonData.metas.containsKey(pos)) meta = dangeonData.metas.get(pos);
                                if(temp instanceof BlockStairs) {
                                    dir = meta % 4;
                                    meta -= dir;
                                    switch (this.dir) {
                                        case 0:
                                            break;
                                        case 1:
                                            //-90
                                            //east -> south
                                            if (dir == 0) {
                                                dir = 2;
                                                break;
                                            }
                                            //west -> north
                                            if (dir == 1) {
                                                dir = 3;
                                                break;
                                            }
                                            //south -> west
                                            if (dir == 2) {
                                                dir = 1;
                                                break;
                                            }
                                            //north -> east
                                            if (dir == 3) {
                                                dir = 0;
                                                break;
                                            }
                                        case 2:
                                            //+90
                                            //east -> north
                                            if (dir == 0) {
                                                dir = 3;
                                                break;
                                            }
                                            //west -> south
                                            if (dir == 1) {
                                                dir = 2;
                                                break;
                                            }
                                            //south -> east
                                            if (dir == 2) {
                                                dir = 0;
                                                break;
                                            }
                                            //north -> west
                                            if (dir == 3) {
                                                dir = 1;
                                                break;
                                            }
                                        case 3:
                                            //+180
                                            //east -> west
                                            if (dir == 0) {
                                                dir = 1;
                                                break;
                                            }
                                            //west -> east
                                            if (dir == 1) {
                                                dir = 0;
                                                break;
                                            }
                                            //south -> north
                                            if (dir == 2) {
                                                dir = 3;
                                                break;
                                            }
                                            //north -> south
                                            if (dir == 3) {
                                                dir = 2;
                                                break;
                                            }
                                    }
                                    meta +=dir;
                                }else if(temp instanceof BlockLadder) {
                                    dir = meta;
                                    switch (this.dir) {
                                        case 0:
                                            break;
                                        case 1:
                                            //-90
                                            //east -> south
                                            if (dir == 5) {
                                                dir = 3;
                                                break;
                                            }
                                            //west -> north
                                            if (dir == 4) {
                                                dir = 2;
                                                break;
                                            }
                                            //south -> west
                                            if (dir == 3) {
                                                dir = 4;
                                                break;
                                            }
                                            //north -> east
                                            if (dir == 2) {
                                                dir = 5;
                                                break;
                                            }
                                        case 2:
                                            //+90
                                            //east -> north
                                            if (dir == 5) {
                                                dir = 2;
                                                break;
                                            }
                                            //west -> south
                                            if (dir == 4) {
                                                dir = 3;
                                                break;
                                            }
                                            //south -> east
                                            if (dir == 3) {
                                                dir = 5;
                                                break;
                                            }
                                            //north -> west
                                            if (dir == 2) {
                                                dir = 4;
                                                break;
                                            }
                                        case 3:
                                            //+180
                                            //east -> west
                                            if (dir == 5) {
                                                dir = 4;
                                                break;
                                            }
                                            //west -> east
                                            if (dir == 4) {
                                                dir = 5;
                                                break;
                                            }
                                            //south -> north
                                            if (dir == 3) {
                                                dir = 2;
                                                break;
                                            }
                                            //north -> south
                                            if (dir == 2) {
                                                dir = 3;
                                                break;
                                            }
                                    }
                                    meta = dir;
                                }
                                world.setBlock(i, Ystart + pos.y, j, temp, meta, 2);
                                if (temp == Blocks.chest){
                                    addChestHelper(random,world,i, Ystart + pos.y, j);
                                }else
                                if (temp == Blocks.mob_spawner)
                                    dangeonData.spawnerpos.get(pos).setnormalspawner(world, i, Ystart + pos.y, j);
                                if (temp == GVCMobPlus.fn_mobspawner)
                                    dangeonData.spawnerpos.get(pos).setspawner(world, i, Ystart + pos.y, j);
                            }catch (Exception e){
                                e.printStackTrace();
                                System.out.println("position" + pos);
                            }
                        }
                        Class<? extends Entity> entityClass = dangeonData.entitys.get(pos);
                        if(entityClass != null){
                            try {
                                Constructor<? extends Entity> constructor =  entityClass.getConstructor(World.class);
                                Entity newentity = constructor.newInstance(world);
                                if(newentity instanceof EntityGBase){
                                    ((EntityGBase)newentity).candespawn = false;
                                    ((EntityGBase)newentity).addRandomArmor();
                                }
                                if(newentity instanceof GVCEntityPMCTank){
                                    ((GVCEntityPMCTank) newentity).setMobMode(1);
                                }
                                newentity.setLocationAndAngles(i + 0.5, Ystart + pos.y, j + 0.5,random.nextInt(360)-180,0);
                                world.spawnEntityInWorld(newentity);
                            } catch (NoSuchMethodException e) {
                                e.printStackTrace();
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            } catch (InstantiationException e) {
                                e.printStackTrace();
                            } catch (InvocationTargetException e) {
                                e.printStackTrace();
                            }
                        }
                        String gunname = dangeonData.turrets.get(pos);
                        if(gunname != null){
                            String[] datas = gunname.split(";");
                            Item gunitem = GameRegistry.findItem("HandmadeGuns",datas[0]);
                            if(gunitem != null) {
                                ItemStack itemStack = new ItemStack(gunitem);
                                PlacedGunEntity newentity = new PlacedGunEntity(world, itemStack);
                                newentity.setLocationAndAngles(i + 0.5, Ystart + pos.y, j + 0.5,Float.parseFloat(datas[1]) , 0);
                                world.spawnEntityInWorld(newentity);
                            }
                        }
                    }

                }
            }
        }
        return true;
    }
    private void addChestHelper (Random random,World world,int x,int y,int z){

        TileEntityChest Chest;
        Chest  = (TileEntityChest) world.getTileEntity(x,y,z);
        for (int s = 0; s < 9; ++s){
            Item gun = (Item) HMGAddGunsNew.Guns.get(random.nextInt(HMGAddGunsNew.Guns.size()));
            if(gun instanceof HMGItem_Unified_Guns){
                if(!((HMGItem_Unified_Guns) gun).isinRoot)gun = null;
                if(gun != null){
                    Chest.setInventorySlotContents(s, new ItemStack(gun));
                    if(((HMGItem_Unified_Guns) gun).magazine != null)
                        Chest.setInventorySlotContents(9 + s, new ItemStack(((HMGItem_Unified_Guns) gun).magazine));
                }
            }
        }
    }
}

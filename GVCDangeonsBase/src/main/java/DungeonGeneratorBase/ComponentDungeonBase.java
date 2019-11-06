package DungeonGeneratorBase;

import cpw.mods.fml.common.registry.GameRegistry;
import handmadeguns.HMGGunMaker;
import handmadeguns.entity.PlacedGunEntity;
import handmadeguns.items.guns.HMGItem_Unified_Guns;
import hmggvcmob.GVCMobPlus;
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
    public int getTopSolidBlock(World world , int p_72825_1_, int p_72825_2_) {
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
        int Ystart = 70;
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

            this.boundingBox.offset(0, this.hight - this.boundingBox.minY + dungeonData.offset[1], 0);
        }
        
        for (int i = structureboundingbox.minX; i <= structureboundingbox.maxX; ++i) {
            for (int j = structureboundingbox.minZ; j <= structureboundingbox.maxZ; ++j) {
                if (structureboundingbox.isVecInside(i, 64, j)) {
                    int groundlevel = getTopSolidOrLiquidBlock(world,i,j);
                    int todpethlevel = getTopSolidBlock(world,i,j);
                    Ystart = dungeonData.fixed_from_Ground ? groundlevel + dungeonData.offset[1]:boundingBox.minY;
                    for (int h2 = dungeonData.maxy; h2 < dungeonData.maxy + dungeonData.placeAir; h2++) {
                        world.setBlock(i, this.boundingBox.minY +h2, j, Blocks.air);
                    }
                    if(dungeonData.fillStone_to_Ground){
                        for(int l = todpethlevel; l < this.boundingBox.minY + dungeonData.levelhightoffset; l++){
                            world.setBlock(i, l, j, Blocks.stone);
                        }
                    }
    
                    
                    int inStructuerPosX = i - this.boundingBox.minX;
                    int inStructuerPosZ = j - this.boundingBox.minZ;
                    for (int h2 = -1; h2 <= dungeonData.maxy; h2++) {
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
                                pos.z = dungeonData.maxz - inStructuerPosX;
                                break;
                            case 2:
                                pos.x = dungeonData.maxx - inStructuerPosZ;
                                pos.y = h2;
                                pos.z = inStructuerPosX;
                                break;
                            case 3:
                                pos.x = dungeonData.maxx - inStructuerPosX;
                                pos.y = h2;
                                pos.z = dungeonData.maxz - inStructuerPosZ;
                                break;
                        }
                        Block temp = dungeonData.blocks.get(pos);
                        if (temp != null) {
                            try {
                                int meta = 0;
                                int dir = 0;
                                if (dungeonData.metas.containsKey(pos)) meta = dungeonData.metas.get(pos);
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
                                            //+90
                                            //east -> north
                                            if (dir == 5) {
                                                dir = 2;
                                            }else
                                            //west -> south
                                            if (dir == 4) {
                                                dir = 3;
                                            }else
                                            //south -> east
                                            if (dir == 3) {
                                                dir = 5;
                                            }else
                                            //north -> west
                                            if (dir == 2) {
                                                dir = 4;
                                            }
                                            break;
                                        case 2:
                                            //-90
                                            //east -> south
                                            if (dir == 5) {
                                                dir = 3;
                                            }else
                                            //west -> north
                                            if (dir == 4) {
                                                dir = 2;
                                            }else
                                            //south -> west
                                            if (dir == 3) {
                                                dir = 4;
                                            }else
                                            //north -> east
                                            if (dir == 2) {
                                                dir = 5;
                                            }
                                            break;
                                        case 3:
                                            //+180
                                            //east -> west
                                            if (dir == 5) {
                                                dir = 4;
                                            }else
                                            //west -> east
                                            if (dir == 4) {
                                                dir = 5;
                                            }else
                                            //south -> north
                                            if (dir == 3) {
                                                dir = 2;
                                            }else
                                            //north -> south
                                            if (dir == 2) {
                                                dir = 3;
                                            }
                                            break;
                                    }
                                    meta = dir;
                                }
                                world.setBlock(i, Ystart + pos.y, j, temp, meta, 2);
                                if (temp == Blocks.chest){
                                    addChestHelper(random,world,i, Ystart + pos.y, j,pos);
                                }else
                                if (temp == Blocks.mob_spawner) {
                                    dungeonData.spawnerpos.get(pos).setnormalspawner(world, i, Ystart + pos.y, j);
                                }else
                                if (temp == GVCMobPlus.fn_mobspawner) {
                                    dungeonData.spawnerpos.get(pos).setspawner(world, i, Ystart + pos.y, j);
                                }
                            }catch (Exception e){
                                e.printStackTrace();
                                System.out.println("position" + pos);
                            }
                        }
                        Class<? extends Entity> entityClass = dungeonData.entitys.get(pos);
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
                        String gunname = dungeonData.turrets.get(pos);
                        if(gunname != null){
                            String[] datas = gunname.split(";");
                            Item gunitem = GameRegistry.findItem("HandmadeGuns",datas[0]);
                            if(gunitem != null) {
                                ItemStack itemStack = new ItemStack(gunitem);
                                PlacedGunEntity newentity = new PlacedGunEntity(world, itemStack);
                                float tempdir = Float.parseFloat(datas[1]);
                                float offsetx = 0;
                                float offsety = 0;
                                float offsetz = 0;
                                if(datas.length == 5) {
                                    offsetx = Float.parseFloat(datas[2]);
                                    offsety = Float.parseFloat(datas[3]);
                                    offsetz = Float.parseFloat(datas[4]);
                                }
                                switch (dir){
                                    case 0:break;
                                    case 1:tempdir += 90; break;
                                    case 2:tempdir -= 90;break;
                                    case 3:tempdir += 180;break;
                                }
                                newentity.setLocationAndAngles(i + 0.5 + offsetx
                                        , Ystart + pos.y + offsety
                                        , j + 0.5 + offsetz
                                        ,tempdir, 0);
                                newentity.rotationYawGun = tempdir;
                                System.out.println("" + newentity);
                                world.spawnEntityInWorld(newentity);
                            }
                        }
                    }
                }
            }
        }
        return true;
    }
    private void addChestHelper (Random random,World world,int x,int y,int z,BlockPos pos){

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
                    if (!((HMGItem_Unified_Guns) gun).gunInfo.isinRoot) gun = null;
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

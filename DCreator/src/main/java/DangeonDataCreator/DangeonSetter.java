package DangeonDataCreator;

import hmggvcmob.GVCMobPlus;
import hmggvcmob.tile.TileEntityMobSpawner_Extend;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.world.World;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static DangeonDataCreator.mod_DungeonCreator.blankBlock;
import static handmadeguns.HandmadeGunsCore.proxy;

public class DangeonSetter extends Item {
    int minposx = 0;
    int minposy = 0;
    int minposz = 0;
    int MAXposx = 0;
    int MAXposy = 0;
    int MAXposz = 0;
    boolean isSelecting = false;


    @Override
    public boolean onItemUse(ItemStack itemstack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {

        if(!world.isRemote) {
            if (!isSelecting) {
                minposx = x;
                minposy = y;
                minposz = z;
                isSelecting = true;
            } else {
                isSelecting = false;
                if (minposx > x) {
                    MAXposx = minposx;
                    minposx = x;
                } else {
                    MAXposx = x;
                }
                if (minposy > y) {
                    MAXposy = minposy;
                    minposy = y;
                } else {
                    MAXposy = y;
                }
                if (minposz > z) {
                    MAXposz = minposz;
                    minposz = z;
                } else {
                    MAXposz = z;
                }

                try {
                    File test = new File(proxy.ProxyFile(), "mods" + File.separatorChar + "dungeonCreator" + File.separatorChar + "Test");
                    FileWriter fw = new FileWriter(test);
                    for (int blocky = minposy; blocky <= MAXposy; blocky++) for (int blockx = minposx; blockx <= MAXposx; blockx++) for (int blockz = minposz; blockz <= MAXposz; blockz++) {
                        Block block =world.getBlock(blockx,blocky,blockz);
                        if(block != blankBlock) {
                            if (world.getBlockMetadata(blockx, blocky, blockz) != 0)
                                fw.write((blockx - minposx) + "," + (blocky - minposy) + "," + (blockz - minposz) + "," + Block.blockRegistry.getNameForObject(block) + "," + world.getBlockMetadata(blockx, blocky, blockz) + "\n");
                            else
                                fw.write((blockx - minposx) + "," + (blocky - minposy) + "," + (blockz - minposz) + "," + Block.blockRegistry.getNameForObject(block) + "\n");
                            if (block == Blocks.mob_spawner) {
                                fw.write("Spawner," + (blockx - minposx) + "," + (blocky - minposy) + "," + (blockz - minposz) + "," + ((TileEntityMobSpawner) world.getTileEntity(blockx, blocky, blockz)).func_145881_a().getEntityNameToSpawn() + "\n");
                            }
                            if (block == GVCMobPlus.fn_mobspawner || block == GVCMobPlus.tankspawner || block == GVCMobPlus.APCspawner || block == GVCMobPlus.GKspawner) {
                                fw.write("Spawner," + (blockx - minposx) + "," + (blocky - minposy) + "," + (blockz - minposz) + "," + ((TileEntityMobSpawner_Extend) world.getTileEntity(blockx, blocky, blockz)).func_145881_a().getEntityNameToSpawn() + "\n");
                            }
                        }
                    }
                    fw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        }

        return super.onItemUse(itemstack, player, world, x, y, z, side, hitX, hitY, hitZ);
    }
}

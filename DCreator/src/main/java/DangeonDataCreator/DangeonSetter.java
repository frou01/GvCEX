package DangeonDataCreator;

import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import handmadeguns.entity.PlacedGunEntity;
import hmggvcmob.GVCMobPlus;
import hmggvcmob.tile.TileEntityMobSpawner_Extend;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.passive.EntityAmbientCreature;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.world.World;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static DangeonDataCreator.mod_DungeonCreator.blankBlock;
import static handmadeguns.HandmadeGunsCore.HMG_proxy;

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
                    File test = new File(HMG_proxy.ProxyFile(), "mods" + File.separatorChar + "dungeonCreator" + File.separatorChar + "Test");
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
                            if (block == Blocks.chest) {
                                TileEntityChest chest = (TileEntityChest) world.getTileEntity(blockx,blocky,blockz);
                                ItemStack[] chestContents = new ItemStack[27];
                                boolean flag = false;
                                for(int id = 0;id < chestContents.length;id++){
                                    chestContents[id] = chest.getStackInSlot(id);
                                    flag |= chestContents[id] != null;
                                }
                                if(flag){
                                    StringBuilder chestContents_StringBuilder = new StringBuilder("Chest," + (blockx - minposx) + "," + (blocky - minposy) + "," + (blockz - minposz));
                                    for(ItemStack stack : chestContents){
                                        chestContents_StringBuilder.append(",");
                                        if(stack != null){
                                            GameRegistry.UniqueIdentifier uniqueIdentifier = GameRegistry.findUniqueIdentifierFor(stack.getItem());
                                            chestContents_StringBuilder.append(uniqueIdentifier.modId).append(";").append(uniqueIdentifier.name).append(";").append(stack.stackSize);
                                        }
                                    }
                                    chestContents_StringBuilder.append("\n");
                                    String chestContents_String = chestContents_StringBuilder.toString();
                                    fw.write(chestContents_String);
                                }
                            }
                            if (block == GVCMobPlus.fn_mobspawner || block == GVCMobPlus.tankspawner || block == GVCMobPlus.APCspawner || block == GVCMobPlus.GKspawner) {
                                fw.write("Spawner," + (blockx - minposx) + "," + (blocky - minposy) + "," + (blockz - minposz) + "," + ((TileEntityMobSpawner_Extend) world.getTileEntity(blockx, blocky, blockz)).func_145881_a().getEntityNameToSpawn() + "\n");
                            }
                        }
                        
                    }
                    for(Object entity :world.loadedEntityList){
                        if(entity instanceof Entity){
                            if(((Entity) entity).posX >= minposx && ((Entity) entity).posX <= MAXposx &&
                                       ((Entity) entity).posY >= minposy && ((Entity) entity).posY <= MAXposy &&
                                       ((Entity) entity).posZ >= minposz && ((Entity) entity).posZ <= MAXposz){
                                if(entity instanceof PlacedGunEntity){
                                    int floorposX = (int)((PlacedGunEntity) entity).posX;
                                    int floorposY = (int)((PlacedGunEntity) entity).posY;
                                    int floorposZ = (int)((PlacedGunEntity) entity).posZ;
                                    fw.write("Turret," + (floorposX - minposx) + "," + (floorposY - minposy) + "," + (floorposZ - minposz) + "," + GameRegistry.findUniqueIdentifierFor(((PlacedGunEntity) entity).gunItem).name + "," + ((PlacedGunEntity) entity).rotationYaw + "," +
                                                     (((PlacedGunEntity) entity).posX - (float)floorposX - 0.5) + "," + (((PlacedGunEntity) entity).posY - (float)floorposY) + "," + (((PlacedGunEntity) entity).posZ - (float)floorposZ - 0.5) + "\n");
                                }else if(entity instanceof EntityLiving && !(entity instanceof EntityAmbientCreature)){
                                    EntityRegistry.EntityRegistration er = EntityRegistry.instance().lookupModSpawn((Class<? extends Entity>) entity.getClass(), false);
                                    int floorposX = (int)((EntityLiving) entity).posX;
                                    int floorposY = (int)((EntityLiving) entity).posY;
                                    int floorposZ = (int)((EntityLiving) entity).posZ;
                                    if(er != null)fw.write("Entity," + (floorposX - minposx) + "," + (floorposY - minposy) + "," + (floorposZ - minposz) + "," + er.getModEntityId() + "." + er.getEntityName() + "\n");
                                }
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

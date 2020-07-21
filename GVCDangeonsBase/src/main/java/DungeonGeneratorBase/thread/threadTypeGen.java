package DungeonGeneratorBase.thread;

import DungeonGeneratorBase.*;
import cpw.mods.fml.common.registry.GameRegistry;
import handmadeguns.entity.PlacedGunEntity;
import hmggvcmob.GVCMobPlus;
import hmggvcmob.entity.guerrilla.EntityGBase;
import hmggvcmob.entity.guerrilla.EntityGBases;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraftforge.common.util.ForgeDirection;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Random;

import static DungeonGeneratorBase.ComponentDungeonBase.*;

public class threadTypeGen extends Thread {
	StructureBoundingBox structureboundingbox;
	DungeonData dungeonData;
	World world;
	StructureBoundingBox boundingBox;
	int dir;
	int Ystart;
	Random random;
	public threadTypeGen(StructureBoundingBox structureboundingbox,
	                     DungeonData dungeonData,
	                     World world,
	                     StructureBoundingBox boundingBox,
	                     int dir,
	                     int Ystart,
	                     Random random
	){
		this.structureboundingbox = structureboundingbox;
		this.dungeonData = dungeonData;
		this.world = world;
		this.boundingBox = boundingBox;
		this.dir = dir;
		this.Ystart = Ystart;
		this.random = random;

	}

	public void run() {
		for (int i = structureboundingbox.minX; i <= structureboundingbox.maxX; ++i) {
			for (int j = structureboundingbox.minZ; j <= structureboundingbox.maxZ; ++j) {
				if (structureboundingbox.isVecInside(i, 64, j)) {
					if(dungeonData.fixed_from_Ground){
						int groundlevel = getTopSolidOrLiquidBlock(world,i,j);
						Ystart = groundlevel + dungeonData.offset[1];
					}
					for (int h2 = dungeonData.maxy; h2 < dungeonData.maxy + dungeonData.placeAir; h2++) {
						world.setBlock(i, this.boundingBox.minY +h2, j, Blocks.air);
					}
					if(dungeonData.fillStone_to_Ground && boundingBox.minX <= i && boundingBox.minZ <= j && i <= boundingBox.maxX && j <= boundingBox.maxZ){
						int todpethlevel = getTopSolidBlock(world,i,j);
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
//                                if(temp instanceof BlockStairs) {
//                                    dir = meta % 4;
//                                    meta -= dir;
//                                    switch (this.dir) {
//                                        case 0:
//                                            break;
//                                        case 1:
//                                            //-90
//                                            //east -> south
//                                            if (dir == 0) {
//                                                dir = 2;
//                                                break;
//                                            }
//                                            //west -> north
//                                            if (dir == 1) {
//                                                dir = 3;
//                                                break;
//                                            }
//                                            //south -> west
//                                            if (dir == 2) {
//                                                dir = 1;
//                                                break;
//                                            }
//                                            //north -> east
//                                            if (dir == 3) {
//                                                dir = 0;
//                                                break;
//                                            }
//                                        case 2:
//                                            //+90
//                                            //east -> north
//                                            if (dir == 0) {
//                                                dir = 3;
//                                                break;
//                                            }
//                                            //west -> south
//                                            if (dir == 1) {
//                                                dir = 2;
//                                                break;
//                                            }
//                                            //south -> east
//                                            if (dir == 2) {
//                                                dir = 0;
//                                                break;
//                                            }
//                                            //north -> west
//                                            if (dir == 3) {
//                                                dir = 1;
//                                                break;
//                                            }
//                                        case 3:
//                                            //+180
//                                            //east -> west
//                                            if (dir == 0) {
//                                                dir = 1;
//                                                break;
//                                            }
//                                            //west -> east
//                                            if (dir == 1) {
//                                                dir = 0;
//                                                break;
//                                            }
//                                            //south -> north
//                                            if (dir == 2) {
//                                                dir = 3;
//                                                break;
//                                            }
//                                            //north -> south
//                                            if (dir == 3) {
//                                                dir = 2;
//                                                break;
//                                            }
//                                    }
//                                    meta +=dir;
//                                }else if(temp instanceof BlockLadder) {
//                                    dir = meta;
//                                    switch (this.dir) {
//                                        case 0:
//                                            break;
//                                        case 1:
//                                            //+90
//                                            //east -> north
//                                            if (dir == 5) {
//                                                dir = 2;
//                                            }else
//                                            //west -> south
//                                            if (dir == 4) {
//                                                dir = 3;
//                                            }else
//                                            //south -> east
//                                            if (dir == 3) {
//                                                dir = 5;
//                                            }else
//                                            //north -> west
//                                            if (dir == 2) {
//                                                dir = 4;
//                                            }
//                                            break;
//                                        case 2:
//                                            //-90
//                                            //east -> south
//                                            if (dir == 5) {
//                                                dir = 3;
//                                            }else
//                                            //west -> north
//                                            if (dir == 4) {
//                                                dir = 2;
//                                            }else
//                                            //south -> west
//                                            if (dir == 3) {
//                                                dir = 4;
//                                            }else
//                                            //north -> east
//                                            if (dir == 2) {
//                                                dir = 5;
//                                            }
//                                            break;
//                                        case 3:
//                                            //+180
//                                            //east -> west
//                                            if (dir == 5) {
//                                                dir = 4;
//                                            }else
//                                            //west -> east
//                                            if (dir == 4) {
//                                                dir = 5;
//                                            }else
//                                            //south -> north
//                                            if (dir == 3) {
//                                                dir = 2;
//                                            }else
//                                            //north -> south
//                                            if (dir == 2) {
//                                                dir = 3;
//                                            }
//                                            break;
//                                    }
//                                    meta = dir;
//                                }
								world.setBlock(i, Ystart + pos.y, j, temp, meta, 0);
								switch (this.dir) {
									case 0:
										break;
									case 1:
										//+90
										//north -> west
										temp.rotateBlock(world,i, Ystart + pos.y, j, ForgeDirection.DOWN);
										temp.rotateBlock(world,i, Ystart + pos.y, j,ForgeDirection.DOWN);
										temp.rotateBlock(world,i, Ystart + pos.y, j,ForgeDirection.DOWN);
										break;
									case 2:
										//-90
										//north -> east
										temp.rotateBlock(world,i, Ystart + pos.y, j,ForgeDirection.DOWN);
										break;
									case 3:
										//+180
										//north -> south
										temp.rotateBlock(world,i, Ystart + pos.y, j,ForgeDirection.DOWN);
										temp.rotateBlock(world,i, Ystart + pos.y, j,ForgeDirection.DOWN);
										break;
								}
								if (temp == Blocks.chest && !dungeonData.TileEntitys.containsKey(pos)){
									addChestHelper(random,world,i, Ystart + pos.y, j,pos,dungeonData);
								}else
								if (temp == Blocks.mob_spawner && !dungeonData.TileEntitys.containsKey(pos)) {
									dungeonData.spawnerpos.get(pos).setnormalspawner(world, i, Ystart + pos.y, j);
								}else
								if (temp == GVCMobPlus.fn_mobspawner && !dungeonData.TileEntitys.containsKey(pos)) {
									dungeonData.spawnerpos.get(pos).setspawner(world, i, Ystart + pos.y, j);
								}
							}catch (Exception e){
								e.printStackTrace();
								System.out.println("position" + pos);
							}
						}
						AddVehicleHelper vehicleHelper = dungeonData.vehiclePos.get(pos);
						if(vehicleHelper != null){
							vehicleHelper.setVehicle(world,i + 0.5, Ystart + pos.y, j + 0.5);
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
						if(dungeonData.TileEntitys.containsKey(pos)){
							TileEntityInfo tileEntityInfo = dungeonData.TileEntitys.get(pos);
							TileEntity tileEntity = tileEntityInfo.createAndLoadEntity();
							tileEntity.xCoord = i;
							tileEntity.yCoord = Ystart + pos.y;
							tileEntity.zCoord = j;
							world.setTileEntity(i,Ystart + pos.y,j,tileEntity);
						}
						if(dungeonData.entitys_withData.containsKey(pos)){
							EntityInfo entityInfo = dungeonData.entitys_withData.get(pos);
							Entity entity = entityInfo.createAndLoadEntity(world);
							entity.posX = i + 0.5;
							entity.posY = Ystart + pos.y + 0.5;
							entity.posZ = j + 0.5;
							switch (this.dir) {
								case 0:
									break;
								case 1:
									//+90
									//north -> west
									entity.rotationYaw += 90;
									break;
								case 2:
									//-90
									//north -> east
									entity.rotationYaw += 270;
									break;
								case 3:
									//+180
									//north -> south
									entity.rotationYaw += 180;
									break;
							}
							System.out.println("" + entity);
							if(entity instanceof EntityGBases){
								((EntityGBases) entity).candespawn = false;
							}
							world.spawnEntityInWorld(entity);
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
	}
}

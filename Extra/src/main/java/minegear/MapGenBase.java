package minegear;

import DungeonGeneratorBase.ComponentDungeonBase;
import DungeonGeneratorBase.DangeonData;
import hmggvcmob.GVCMobPlus;
import hmggvcmob.entity.guerrilla.GVCEntityGuerrilla;
import hmggvcutil.GVCUtils;
import hmggvcmob.entity.guerrilla.GVCEntityAAG;
import hmggvcmob.entity.guerrilla.GVCEntityTankT90;
import net.minecraft.init.Blocks;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.structure.MapGenStructure;
import net.minecraft.world.gen.structure.StructureStart;

import java.util.ArrayList;

import static java.lang.Math.abs;
import static minegear.mod_MineGear.cfg_creatBase;
import static minegear.mod_MineGear.cfg_setBase;

public class MapGenBase extends MapGenStructure {
	static ArrayList<DangeonData> dangeondata = new ArrayList<>();

	public MapGenBase(){
		super();
		dangeondata.add(new DangeonData());
		dangeondata.get(0).maxx = 64;
		dangeondata.get(0).maxz = 64;
		dangeondata.get(0).fillStone_to_Ground = true;
		dangeondata.get(0).overWrite_Ocean = true;
		for(int h=1;h<6;h++) {
			for (int i0 = 0; i0 < 64; ++i0) {
				for (int i2 = 0; i2 < 64; ++i2)  {
					for (int i1 = 0; i1 < 16; ++i1){
						dangeondata.get(0).setBlock( i0, i1, i2, Blocks.air);
						dangeondata.get(0).setBlock( i0, -1, i2, Blocks.stone);
						dangeondata.get(0).setBlock( i0, -2, i2, Blocks.stone);
					}
				}
			}
			for (int x = 0; x < 64; ++x) {
				for (int y = 0; y < 5; ++y) {
					dangeondata.get(0).setBlock( 0, y, 0, Blocks.stonebrick);
					dangeondata.get(0).setBlock( 0, y, 63, Blocks.stonebrick);
					dangeondata.get(0).setBlock( 63, y, 0, Blocks.stonebrick);
					dangeondata.get(0).setBlock( 63, y, 63, Blocks.stonebrick);
				}
			}
			addMGTower(3,1,3);
			addMGTower(3,1,58);
			int offsetx = 59;
			int offsetz = 4;
			for (int x = 0; x < 3; x++) {
				dangeondata.get(0).setBlock( offsetx + x, 1, offsetz + 0, Blocks.air);
				dangeondata.get(0).setBlock( offsetx + x, 1, offsetz + 1, Blocks.air);
				dangeondata.get(0).setBlock( offsetx + x, 1, offsetz + 2, Blocks.air);
			}
			offsetx = 59;
			offsetz = 59;
			for (int x = 0; x < 3; x++) {
				dangeondata.get(0).setBlock( offsetx + x, 1, offsetz + 0, Blocks.air);
				dangeondata.get(0).setBlock( offsetx + x, 1, offsetz + 1, Blocks.air);
				dangeondata.get(0).setBlock( offsetx + x, 1, offsetz + 2, Blocks.air);
			}


			for (int x = 0; x < 13; ++x) {
				for (int z = 0; z < 39; ++z) {
					dangeondata.get(0).setBlock( 17 + x, -1, 21 + z, Blocks.stonebrick);
				}
			}
			for (int x = 0; x < 6; ++x) {
				for (int z = 0; z < 64; ++z) {
					dangeondata.get(0).setBlock( 36 + x, -1, z, Blocks.stonebrick);
					dangeondata.get(0).setBlock( 48 + x, -1, z, Blocks.stonebrick);
				}
			}
			for (int x = 0; x < 6; ++x) {
				for (int z = 0; z < 6; ++z) {
					dangeondata.get(0).setBlock( 42 + x, -1, 7 + z, Blocks.stonebrick);
					dangeondata.get(0).setBlock( 42 + x, -1, 51 + z, Blocks.stonebrick);
					dangeondata.get(0).setBlock( 30 + x, -1, 25 + z, Blocks.stonebrick);
					dangeondata.get(0).setBlock( 30 + x, -1, 51 + z, Blocks.stonebrick);
				}
			}

			for (int x = 0; x < 4; ++x) {
				dangeondata.get(0).setBlock( 31 + x, 0, 0, Blocks.air);
				dangeondata.get(0).setBlock( 31 + x, 1, 0, Blocks.air);

				dangeondata.get(0).setBlock( 63, 0, 26 + x, Blocks.air);
				dangeondata.get(0).setBlock( 63, 1, 26 + x, Blocks.air);
				dangeondata.get(0).setBlock( 0, 0, 26 + x, Blocks.air);
				dangeondata.get(0).setBlock( 0, 1, 26 + x, Blocks.air);
			}
			for (int x = 0; x < 14; ++x) {
				dangeondata.get(0).setBlock( 18 + x, 0, 63, Blocks.air);
				dangeondata.get(0).setBlock( 18 + x, 1, 63, Blocks.air);
				dangeondata.get(0).setBlock( 18 + x, 2, 63, Blocks.air);
				dangeondata.get(0).setBlock( 18 + x, 3, 63, Blocks.air);
			}
			addhanger(3, 0, 22);
			addhanger(3, 0, 40);

			addbarracks( 3, 0, 7);
			addbarracks( 11, 0, 7);

			addTower( 18, 0, 5);

			dangeondata.get(0).setBlock( 33, 0, 8, GVCMobPlus.fn_mobspawner, 2, 2);
			dangeondata.get(0).setBlock( 33, 1, 8, GVCUtils.fn_cont);
			dangeondata.get(0).setBlock( 32, 0, 8, GVCUtils.fn_cont);
			dangeondata.get(0).setBlock( 33, 0, 7, GVCUtils.fn_cont);
			dangeondata.get(0).setBlock( 34, 0, 8, GVCUtils.fn_cont);
			dangeondata.get(0).setBlock( 33, 0, 9, GVCUtils.fn_cont);
			dangeondata.get(0).setSpawner(33, 0, 8,"GVCMob.Guerrillamg");
			dangeondata.get(0).setBlock( 45, 0, 17, GVCMobPlus.fn_mobspawner, 2, 2);
			dangeondata.get(0).setBlock( 45, 1, 17, GVCUtils.fn_cont);
			dangeondata.get(0).setBlock( 44, 0, 17, GVCUtils.fn_cont);
			dangeondata.get(0).setBlock( 45, 0, 16, GVCUtils.fn_cont);
			dangeondata.get(0).setBlock( 46, 0, 17, GVCUtils.fn_cont);
			dangeondata.get(0).setBlock( 45, 0, 18, GVCUtils.fn_cont);
			dangeondata.get(0).setSpawner(45, 0, 17,"GVCMob.Guerrillamg");
			//dangeondata.get(0).setBlock(par1+45, par2+1, par3+17, GVCUtils.fn_cont);
			dangeondata.get(0).setBlock( 59, 0, 37, GVCMobPlus.fn_mobspawner, 2, 2);
			dangeondata.get(0).setBlock( 59, 1, 37, GVCUtils.fn_cont);
			dangeondata.get(0).setBlock( 59 - 1, 0, 37, GVCUtils.fn_cont);
			dangeondata.get(0).setBlock( 59, 0, 37 - 1, GVCUtils.fn_cont);
			dangeondata.get(0).setBlock( 59 + 1, 0, 37, GVCUtils.fn_cont);
			dangeondata.get(0).setBlock( 59, 0, 37 + 1, GVCUtils.fn_cont);
			dangeondata.get(0).setSpawner(59, 0, 37,"GVCMob.Jeep");
			//dangeondata.get(0).setBlock(par1+59, par2+1, par3+37, GVCUtils.fn_cont);
			dangeondata.get(0).setBlock( 34, 0, 39, GVCMobPlus.fn_mobspawner, 2, 2);
			dangeondata.get(0).setBlock( 34, 1, 39, GVCUtils.fn_cont);
			dangeondata.get(0).setBlock( 34 - 1, 0, 39, GVCUtils.fn_cont);
			dangeondata.get(0).setBlock( 34, 0, 39 - 1, GVCUtils.fn_cont);
			dangeondata.get(0).setBlock( 34 + 1, 0, 39, GVCUtils.fn_cont);
			dangeondata.get(0).setBlock( 34, 0, 39 + 1, GVCUtils.fn_cont);
			dangeondata.get(0).setSpawner(34, 0, 39,"GVCMob.GK");
			//dangeondata.get(0).setBlock(par1+34, par2+1, par3+39, GVCUtils.fn_cont);
			dangeondata.get(0).setBlock( 45, 0, 59, GVCMobPlus.fn_mobspawner, 2, 2);
			dangeondata.get(0).setBlock( 45, 1, 59, GVCUtils.fn_cont);
			dangeondata.get(0).setBlock( 45 - 1, 0, 59, GVCUtils.fn_cont);
			dangeondata.get(0).setBlock( 45, 0, 59 - 1, GVCUtils.fn_cont);
			dangeondata.get(0).setBlock( 45 + 1, 0, 59, GVCUtils.fn_cont);
			dangeondata.get(0).setBlock( 45, 0, 59 + 1, GVCUtils.fn_cont);
			dangeondata.get(0).setSpawner(45, 0, 59,"GVCMob.Guerrillamg");
			//dangeondata.get(0).setBlock(par1+45, par2+1, par3+59, GVCUtils.fn_cont);


			dangeondata.get(0).setBlock( 58, 0, 11, GVCUtils.fn_cont);
			dangeondata.get(0).setBlock( 58, 1, 11, GVCUtils.fn_cont);
			dangeondata.get(0).setBlock( 59, 0, 11, GVCUtils.fn_cont);
			dangeondata.get(0).setBlock( 58, 0, 12, GVCUtils.fn_cont);

			dangeondata.get(0).setBlock( 45, 0, 25, GVCUtils.fn_cont);
			dangeondata.get(0).setBlock( 46, 0, 26, GVCUtils.fn_cont);
			dangeondata.get(0).setBlock( 45, 1, 25, GVCUtils.fn_cont);
			dangeondata.get(0).setBlock( 46, 1, 26, GVCUtils.fn_cont);

			dangeondata.get(0).setBlock( 55, 0, 34, GVCUtils.fn_cont);
			dangeondata.get(0).setBlock( 55, 1, 34, GVCUtils.fn_cont);
			dangeondata.get(0).setBlock( 55, 0, 33, GVCUtils.fn_cont);
			dangeondata.get(0).setBlock( 56, 0, 34, GVCUtils.fn_cont);

			dangeondata.get(0).setBlock( 58, 0, 49, GVCUtils.fn_cont);
			dangeondata.get(0).setBlock( 58, 1, 49, GVCUtils.fn_cont);
			dangeondata.get(0).setBlock( 57, 0, 49, GVCUtils.fn_cont);
			dangeondata.get(0).setBlock( 58, 0, 48, GVCUtils.fn_cont);

			dangeondata.get(0).setBlock( 9, 0, 58, GVCUtils.fn_cont);
			dangeondata.get(0).setBlock( 8, 0, 58, GVCUtils.fn_cont);
			dangeondata.get(0).setBlock( 9, 0, 59, GVCUtils.fn_cont);
			dangeondata.get(0).setBlock( 9, 1, 58, GVCUtils.fn_cont);
			dangeondata.get(0).setBlock( 8, 0, 58, GVCUtils.fn_cont);
			dangeondata.get(0).setBlock( 10, 0, 59, GVCUtils.fn_cont);

			dangeondata.get(0).setBlock( 34, 0, 46, GVCUtils.fn_cont);
			dangeondata.get(0).setBlock( 34, 1, 46, GVCUtils.fn_cont);
			dangeondata.get(0).setBlock( 33, 0, 46, GVCUtils.fn_cont);
			dangeondata.get(0).setBlock( 34, 0, 47, GVCUtils.fn_cont);


			dangeondata.get(0).setBlock( 0, 0, 0, Blocks.grass);


			dangeondata.get(0).addEntity(32,0,32, GVCEntityTankT90.class);

			dangeondata.get(0).addTurret(44,0,32,"Dshk38",0);
			dangeondata.get(0).addEntity(44,0,32, GVCEntityGuerrilla.class);

			dangeondata.get(0).addTurret(25,0,37,"Dshk38",0);
			dangeondata.get(0).addEntity(25,0,37, GVCEntityGuerrilla.class);
		}
	}
	void addMGTower(int par1, int par2, int par3)
	{
		for (int x = 0; x < 3; ++x){
			for(int y = 0; y < 8; ++y){
				dangeondata.get(0).setBlock(par1+0, par2+y, par3+0, Blocks.iron_bars);
				dangeondata.get(0).setBlock(par1+1, par2+y, par3+0, Blocks.iron_bars);
				dangeondata.get(0).setBlock(par1+2, par2+y, par3+0, Blocks.iron_bars);
				dangeondata.get(0).setBlock(par1+0, par2+y, par3+1, Blocks.iron_bars);
				dangeondata.get(0).setBlock(par1+2, par2+y, par3+1, Blocks.iron_bars);
				dangeondata.get(0).setBlock(par1+0, par2+y, par3+2, Blocks.iron_bars);
				dangeondata.get(0).setBlock(par1+1, par2+y, par3+2, Blocks.iron_bars);
				dangeondata.get(0).setBlock(par1+2, par2+y, par3+2, Blocks.iron_bars);
				dangeondata.get(0).setBlock(par1+x, par2+8, par3+0, Blocks.stonebrick);
				dangeondata.get(0).setBlock(par1+x, par2+8, par3+1, Blocks.stonebrick);
				dangeondata.get(0).setBlock(par1+x, par2+8, par3+2, Blocks.stonebrick);
				dangeondata.get(0).setBlock(par1+x, par2+9, par3+0, Blocks.iron_bars);
				dangeondata.get(0).setBlock(par1+x, par2+9, par3+1, Blocks.iron_bars);
				dangeondata.get(0).setBlock(par1+x, par2+9, par3+2, Blocks.iron_bars);
				dangeondata.get(0).setBlock(par1+1, par2+9, par3+1, Blocks.air);
			}
		}

		dangeondata.get(0).setBlock(par1+1, par2+8, par3+1, GVCMobPlus.fn_mobspawner, 2, 2);
		dangeondata.get(0).setSpawner(par1+1, par2+8, par3+1,"GVCMob.Guerrillarpg");

		dangeondata.get(0).addTurret(par1+1,par2+9,par3+1,"Dshk38",0);
		dangeondata.get(0).addEntity(par1+1,par2+9,par3+1, GVCEntityGuerrilla.class);
		//end
	}
	public void addhanger(int par1, int par2, int par3)
	{
		for (int x = 0; x < 14; ++x){
			for(int y = 0; y < 7; ++y){
				for (int z = 0; z < 14; ++z){
					dangeondata.get(0).setBlock(par1+x, par2+y, par3+0, Blocks.stonebrick);
					dangeondata.get(0).setBlock(par1+x, par2+y, par3+13, Blocks.stonebrick);
					dangeondata.get(0).setBlock(par1+0, par2+y, par3+x, Blocks.stonebrick);
					dangeondata.get(0).setBlock(par1+x, par2+7, par3+z, Blocks.wool,13,2);
				}
			}
		}
		for (int x = 0; x < 14; ++x){
			for (int z = 0; z < 12; ++z){
				dangeondata.get(0).setBlock(par1+x, par2+8, par3+1+z, Blocks.wool,13,2);
			}
		}
		for (int x = 0; x < 14; ++x){
			for (int z = 0; z < 12; ++z){
				dangeondata.get(0).setBlock(par1+x, par2-1, par3+1+z, Blocks.stonebrick,0,2);
			}
		}
		for (int x = 0; x < 14; ++x){
			for (int z = 0; z < 10; ++z){
				dangeondata.get(0).setBlock(par1+x, par2+9, par3+2+z, Blocks.wool,13,2);
			}
		}


		dangeondata.get(0).setBlock(par1+3, par2+6, par3+4, Blocks.redstone_lamp,0,2);
		dangeondata.get(0).setBlock(par1+3, par2+6, par3+9, Blocks.redstone_lamp,0,2);


		dangeondata.get(0).setBlock(par1+7  , par2+0, par3+6, GVCMobPlus.fn_mobspawner, 2, 2);
		dangeondata.get(0).setSpawner(par1+7  , par2+0, par3+6,"GVCMob.Tank");
		dangeondata.get(0).setBlock(par1+7  , par2+1, par3+6, GVCUtils.fn_cont);
		dangeondata.get(0).setBlock(par1+7-1, par2+0, par3+6, GVCUtils.fn_cont);
		dangeondata.get(0).setBlock(par1+7  , par2+0, par3+6-1, GVCUtils.fn_cont);
		dangeondata.get(0).setBlock(par1+7+1, par2+0, par3+6, GVCUtils.fn_cont);
		dangeondata.get(0).setBlock(par1+7  , par2+0, par3+6+1, GVCUtils.fn_cont);
		//dangeondata.get(0).setBlock(par1+34, par2+1, par3+39, GVCUtils.fn_cont);

		dangeondata.get(0).setBlock(par1+3, par2+0, par3+1, GVCMobPlus.fn_mobspawner, 2, 2);
		dangeondata.get(0).setSpawner(par1+3, par2+0, par3+1,"GVCMob.Guerrilla");
		dangeondata.get(0).setBlock(par1+12, par2+0, par3+12, GVCMobPlus.fn_mobspawner, 2, 2);
		dangeondata.get(0).setSpawner(par1+12, par2+0, par3+12,"GVCMob.Guerrilla");

		//end
	}
	public void addbarracks(int par1,int par2,int par3){
		for (int x = 0; x < 6; ++x){
			for(int y = 0; y < 3; ++y){
				for(int z = 0; z < 12; ++z){
					dangeondata.get(0).setBlock(par1+x, par2+y, par3+0, Blocks.wool,13,2);
					dangeondata.get(0).setBlock(par1+x, par2+2, par3+11, Blocks.wool,13,2);
					dangeondata.get(0).setBlock(par1+0, par2+y, par3+z, Blocks.wool,13,2);
					dangeondata.get(0).setBlock(par1+5, par2+y, par3+z, Blocks.wool,13,2);

					dangeondata.get(0).setBlock(par1+3, par2+y, par3+11, Blocks.wool,13,2);
					dangeondata.get(0).setBlock(par1+4, par2+y, par3+11, Blocks.wool,13,2);
				}
			}
		}
		for (int x = 0; x < 4; ++x){
			for(int z = 0; z < 12; ++z){
				dangeondata.get(0).setBlock(par1+1+x, par2+3, par3+z, Blocks.wool,13,2);


			}
		}


		dangeondata.get(0).setBlock(par1+3, par2+0, par3+1, Blocks.bed, 3, 3);
		dangeondata.get(0).setBlock(par1+4, par2+0, par3+1, Blocks.bed, 3+8, 3);
		dangeondata.get(0).setBlock(par1+3, par2+0, par3+3, Blocks.bed, 3, 3);
		dangeondata.get(0).setBlock(par1+4, par2+0, par3+3, Blocks.bed, 3+8, 3);
		dangeondata.get(0).setBlock(par1+3, par2+0, par3+5, Blocks.bed, 3, 3);
		dangeondata.get(0).setBlock(par1+4, par2+0, par3+5, Blocks.bed, 3+8, 3);
		dangeondata.get(0).setBlock(par1+3, par2+0, par3+7, Blocks.bed, 3, 3);
		dangeondata.get(0).setBlock(par1+4, par2+0, par3+7, Blocks.bed, 3+8, 3);
		dangeondata.get(0).setBlock(par1+3, par2+0, par3+9, Blocks.bed, 3, 3);
		dangeondata.get(0).setBlock(par1+4, par2+0, par3+9, Blocks.bed, 3+8, 3);





		dangeondata.get(0).setBlock(par1+2, par2+0, par3+4, GVCMobPlus.fn_mobspawner, 2, 2);
		dangeondata.get(0).setSpawner(par1+2, par2+0, par3+4,"GVCMob.Guerrilla");

		//end
	}
	public void addTower(int par1,int par2,int par3){
		for (int x = 0; x < 12; ++x){
			for(int y = 0; y < 5; ++y){
				for(int z = 0; z < 14; ++z){
					dangeondata.get(0).setBlock(par1+x, par2+y, par3+0, Blocks.stonebrick);
					dangeondata.get(0).setBlock(par1+x, par2+y, par3+13, Blocks.stonebrick);
					dangeondata.get(0).setBlock(par1+0, par2+y, par3+z, Blocks.stonebrick);
					dangeondata.get(0).setBlock(par1+11, par2+y, par3+z, Blocks.stonebrick);
					dangeondata.get(0).setBlock(par1+x, par2+5, par3+z, Blocks.stonebrick);
				}
			}
		}
		dangeondata.get(0).setBlock(par1+2, par2+0, par3+13, Blocks.air);
		dangeondata.get(0).setBlock(par1+3, par2+0, par3+13, Blocks.air);
		dangeondata.get(0).setBlock(par1+2, par2+1, par3+13, Blocks.air);
		dangeondata.get(0).setBlock(par1+3, par2+1, par3+13, Blocks.air);
		for (int x = 0; x < 6; ++x){
			for(int y = 0; y < 13; ++y){
				for(int z = 0; z < 6; ++z){
					dangeondata.get(0).setBlock(par1+1+x, par2+y, par3+3, Blocks.stonebrick);
					dangeondata.get(0).setBlock(par1+1+x, par2+y, par3+8, Blocks.stonebrick);
					dangeondata.get(0).setBlock(par1+1, par2+y, par3+3+z, Blocks.stonebrick);
					dangeondata.get(0).setBlock(par1+6, par2+y, par3+3+z, Blocks.stonebrick);
					dangeondata.get(0).setBlock(par1+1+x, par2+13, par3+3+z, Blocks.stonebrick);
				}
			}
		}
		for (int x = 0; x < 8; ++x){
			for(int y = 0; y < 4; ++y){
				for(int z = 0; z < 8; ++z){
					dangeondata.get(0).setBlock(par1+0+x, par2+15+y, par3+2, Blocks.glass);
					dangeondata.get(0).setBlock(par1+0+x, par2+15+y, par3+9, Blocks.glass);
					dangeondata.get(0).setBlock(par1+0, par2+15+y, par3+2+z, Blocks.glass);
					dangeondata.get(0).setBlock(par1+7, par2+15+y, par3+2+z, Blocks.glass);
					dangeondata.get(0).setBlock(par1+0+x, par2+19, par3+2+z, Blocks.stonebrick);
					dangeondata.get(0).setBlock(par1+0+x, par2+14, par3+2+z, Blocks.stonebrick);
					dangeondata.get(0).setBlock(par1+0, par2+15+y, par3+2, Blocks.stonebrick);
					dangeondata.get(0).setBlock(par1+0, par2+15+y, par3+9, Blocks.stonebrick);
					dangeondata.get(0).setBlock(par1+7, par2+15+y, par3+2, Blocks.stonebrick);
					dangeondata.get(0).setBlock(par1+7, par2+15+y, par3+9, Blocks.stonebrick);
				}
			}
		}
		for(int y = 0; y < 15; ++y){
			dangeondata.get(0).setBlock(par1+3+1, par2+y, par3+4+0, Blocks.stonebrick);

			dangeondata.get(0).setBlock(par1+3+2, par2+y, par3+4+0, Blocks.stonebrick);
			dangeondata.get(0).setBlock(par1+3+2, par2+y, par3+4+1, Blocks.stonebrick);

			dangeondata.get(0).setBlock(par1+3+2, par2+y, par3+4+2, Blocks.stonebrick);
			dangeondata.get(0).setBlock(par1+3+1, par2+y, par3+4+2, Blocks.stonebrick);
			dangeondata.get(0).setBlock(par1+3+0, par2+y, par3+4+2, Blocks.stonebrick);
			if(y!=0 && y!=1&&y != 6&&y!= 7) {
				dangeondata.get(0).setBlock(par1+3+0, par2+y, par3+4+0, Blocks.stonebrick);
				dangeondata.get(0).setBlock(par1+3+0, par2+y, par3+4+1, Blocks.stonebrick);
			}
			if(y==0 || y== 1){
				dangeondata.get(0).setBlock(par1+3+0, par2+y, par3+4+0, Blocks.air);
				dangeondata.get(0).setBlock(par1+3+1, par2+y, par3+4+0, Blocks.air);
				dangeondata.get(0).setBlock(par1+3+2, par2+y, par3+4+0, Blocks.air);
				dangeondata.get(0).setBlock(par1+3+0, par2+y, par3+4-1, Blocks.air);
				dangeondata.get(0).setBlock(par1+3+1, par2+y, par3+4-1, Blocks.air);
				dangeondata.get(0).setBlock(par1+3+2, par2+y, par3+4-1, Blocks.air);
			}
			dangeondata.get(0).setBlock(par1+3+1, par2+y, par3+4+1, Blocks.ladder,2 , 0);
		}
		dangeondata.get(0).setBlock(par1+5, par2+14, par3+6, GVCMobPlus.fn_mobspawner, 2, 2);
		dangeondata.get(0).setSpawner(par1+5, par2+14, par3+6,"GVCMob.Guerrillasg");
		dangeondata.get(0).setBlock(par1+5, par2-1, par3+3, GVCMobPlus.fn_mobspawner, 2, 2);
		dangeondata.get(0).setSpawner(par1+5, par2-1, par3+3,"GVCMob.Guerrillasg");
	}
	@Override
	public String func_143025_a() {
		// 構造物名
		return "GBaseT1";
	}

	@Override
	protected boolean canSpawnStructureAtCoords(int x, int z) {
		return worldObj.getBiomeGenForCoords(x * 16, z * 16) != BiomeGenBase.ocean && worldObj.getBiomeGenForCoords(x * 16, z * 16) != BiomeGenBase.deepOcean && (cfg_setBase && x % 4 == 0 && z % 4 == 0 && rand.nextInt(cfg_creatBase) == 0/* || x == 0 && z == 0*/);
	}

	@Override
	protected StructureStart getStructureStart(int x, int z) {
		return new StructureBaseStart(this.worldObj, this.rand, x, z);
	}
}

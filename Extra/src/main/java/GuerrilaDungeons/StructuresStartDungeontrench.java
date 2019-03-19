package GuerrilaDungeons;

import DungeonGeneratorBase.ComponentDungeonBase;
import DungeonGeneratorBase.DangeonData;
import DungeonGeneratorBase.StructuresStartDungeonBase;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Random;

import static GuerrilaDungeons.mod_TrenchDangeonGenerator.dangeonData;

public class StructuresStartDungeontrench extends StructuresStartDungeonBase {
    public StructuresStartDungeontrench() {}
    public StructuresStartDungeontrench(World par1World, Random par2Random, int par3, int par4) {
        super(par1World,par2Random,par3, par4);
    }
    public ArrayList<DangeonData> getDangeonDatas(){
        return dangeonData;
    }

    @Override
    public ComponentDungeonBase newComponent(World par1World, Random par2Random, int par3, int par4,int id,int dir) {
        return new ComponentDungeontrench(par2Random, par3 * 16, par4 * 16,id,dir);
    }
}

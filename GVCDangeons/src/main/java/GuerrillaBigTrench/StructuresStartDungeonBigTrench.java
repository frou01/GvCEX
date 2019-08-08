package GuerrillaBigTrench;

import DungeonGeneratorBase.ComponentDungeonBase;
import DungeonGeneratorBase.DungeonData;
import DungeonGeneratorBase.StructuresStartDungeonBase;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Random;


public class StructuresStartDungeonBigTrench extends StructuresStartDungeonBase {
    public StructuresStartDungeonBigTrench() {}
    public StructuresStartDungeonBigTrench(World par1World, Random par2Random, int par3, int par4) {
        super(par1World,par2Random,par3, par4);
    }
    public ArrayList<DungeonData> getDangeonDatas(){
        return dungeonData;
    }

    @Override
    public ComponentDungeonBase newComponent(World par1World, Random par2Random, int par3, int par4,int id,int dir) {
        return new ComponentBigTrench(par2Random, par3 * 16, par4 * 16,id,0);
    }
}

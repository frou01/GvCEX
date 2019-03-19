package MetalGearDungeons;

import DungeonGeneratorBase.ComponentDungeonBase;
import DungeonGeneratorBase.DangeonData;
import DungeonGeneratorBase.StructuresStartDungeonBase;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Random;

import static MetalGearDungeons.mod_MetalGearBaseGenerator.dangeonData;


public class StructuresStartDungeonMetalGear extends StructuresStartDungeonBase {
    public StructuresStartDungeonMetalGear() {}
    public StructuresStartDungeonMetalGear(World par1World, Random par2Random, int par3, int par4) {
        super(par1World,par2Random,par3, par4);
    }
    public ArrayList<DangeonData> getDangeonDatas(){
        return dangeonData;
    }

    @Override
    public ComponentDungeonBase newComponent(World par1World, Random par2Random, int par3, int par4,int id,int dir) {
        return new ComponentDungeonMetalGearBase(par2Random, par3 * 16, par4 * 16,id,0);
    }
}

package DungeonGeneratorBase;

import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureStart;

import java.util.ArrayList;
import java.util.Random;


public class StructuresStartDungeonBase extends StructureStart {
    public ArrayList<DungeonData> dungeonData = new ArrayList<DungeonData>();
    DungeonData_withSettings dungeonDataWithSettings;
    
    public StructuresStartDungeonBase() {
        super();
    }

    Random rnd;

    int dir;
    public StructuresStartDungeonBase(DungeonData_withSettings dungeonData_withSettings,World par1World, Random par2Random, int par3, int par4) {
        super(par3, par4);
        dungeonDataWithSettings = dungeonData_withSettings;
        dungeonData = dungeonData_withSettings.dungeonData;
        rnd =par2Random;
        dir = rnd.nextInt(4);
//		System.out.println("x = " + par3*16 + " , z = " + par4*16 + " , length" + length + " , course " + (course?"X":"Z"));
        for (int id = 0; id < dungeonData.size(); id++) {
            components.add(newComponent(par1World,par2Random,par3*16, par4*16,id,dir));
        }

        this.updateBoundingBox();
    }
    public ComponentDungeonBase newComponent(World par1World, Random par2Random, int par3, int par4,int id,int dir){
        return new ComponentDungeonBase(dungeonDataWithSettings,par2Random,par3,par4,id,dir);
    }
}

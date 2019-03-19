package GuerrillaFactory;



import DungeonGeneratorBase.ComponentDungeonBase;
import DungeonGeneratorBase.DangeonData;

import java.util.ArrayList;
import java.util.Random;

import static GuerrillaFactory.mod_FactoryGenerator.dangeonData;

public class ComponentFactory extends ComponentDungeonBase {

    public ComponentFactory() {
        super();
    }
    public ComponentFactory(Random par2Random, int par3, int par4) {
        super(par2Random,par3,par4);
    }
    public ComponentFactory(Random par2Random, int par3, int par4, int id, int dir) {
        super(par2Random,par3,par4,id,0);
    }
    public ArrayList<DangeonData> getDangeonDatas(){
        return dangeonData;
    }
}

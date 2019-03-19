package minegear;

import DungeonGeneratorBase.ComponentDungeonBase;
import DungeonGeneratorBase.DangeonData;

import java.util.ArrayList;
import java.util.Random;

import static minegear.MapGenBase.dangeondata;

public class ComponentDungeonSmallBase extends ComponentDungeonBase {

    public ComponentDungeonSmallBase() {
        super();
    }
    public ComponentDungeonSmallBase(Random par2Random, int par3, int par4) {
        super(par2Random,par3,par4);
    }
    public ComponentDungeonSmallBase(Random par2Random, int par3, int par4, int id, int dir) {
        super(par2Random,par3,par4,id,dir);
    }
    public ArrayList<DangeonData> getDangeonDatas(){
        return dangeondata;
    }
}

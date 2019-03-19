package GuerrilaDungeons;

import DungeonGeneratorBase.ComponentDungeonBase;
import DungeonGeneratorBase.DangeonData;

import java.util.ArrayList;
import java.util.Random;

import static GuerrilaDungeons.mod_TrenchDangeonGenerator.dangeonData;

public class ComponentDungeontrench extends ComponentDungeonBase {

    public ComponentDungeontrench() {
        super();
    }
    public ComponentDungeontrench(Random par2Random, int par3, int par4) {
        super(par2Random,par3,par4);
    }
    public ComponentDungeontrench(Random par2Random, int par3, int par4, int id,int dir) {
        super(par2Random,par3,par4,id,dir);
    }
    public ArrayList<DangeonData> getDangeonDatas(){
        return dangeonData;
    }
}

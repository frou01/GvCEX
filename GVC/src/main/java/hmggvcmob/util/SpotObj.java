package hmggvcmob.util;

import net.minecraft.entity.Entity;
import net.minecraft.scoreboard.Team;

import java.io.Serializable;

import static hmggvcmob.util.SpotType.None;

public class SpotObj implements Serializable{
    private static final long serialVersionUID = 8531245739641223373L;
    
    public String teamname;
    public SpotType type = None;
    public transient Entity target;
    public int targetID = -1;
    public float[] pos = new float[3];
    public int remaintime;
    
    public SpotObj(){}
    
    public SpotObj(Team team,SpotType type,Entity target,int remaintime){
        if(team != null)teamname = team.getRegisteredName();
        this.type = type;
        this.target = target;
        this.targetID = target.getEntityId();
        this.remaintime = remaintime;
    }
    public SpotObj(Team team, SpotType type, float[] pos, int remaintime){
        if(team != null)teamname = team.getRegisteredName();
        this.type = type;
        this.pos = pos;
        this.remaintime = remaintime;
    }
}

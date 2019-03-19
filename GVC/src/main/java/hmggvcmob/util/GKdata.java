package hmggvcmob.util;

import hmggvcmob.entity.guerrilla.GVCEntityGK;

import java.io.Serializable;

public class GKdata implements Serializable {
    public boolean  combattask_2;
    public boolean  combattask_4;
    public int       cooltime_3;
    public GKdata(GVCEntityGK entityin){
        this.combattask_2 = entityin.combattask_2;
        this.combattask_4 = entityin.combattask_4;
        this.cooltime_3 = entityin.kickprogeress;
    }
}

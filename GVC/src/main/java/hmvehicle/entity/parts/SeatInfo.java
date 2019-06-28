package hmvehicle.entity.parts;

import hmvehicle.entity.parts.turrets.TurretObj;
import net.minecraft.item.ItemStack;

public class SeatInfo {
    public double[] pos = new double[3];
    public boolean hasGun;
    public boolean gunTrigger1;
    public boolean gunTrigger2;
    public TurretObj gun;
    public ItemStack gunStack;

}

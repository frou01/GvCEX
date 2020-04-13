package hmggvcmob.entity;

import handmadevehicle.entity.parts.Modes;

import static handmadevehicle.entity.parts.Modes.Wait;

public class PlatoonInfoData {
	public boolean isLeader = false;
	public boolean isOnPlatoon = false;
	public double[] target = new double[3];
	public Modes mode = Wait;
	public String platoonName = null;
}

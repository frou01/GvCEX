package handmadevehicle.entity.prefab;

import net.minecraft.entity.Entity;

import javax.vecmath.Vector3d;

public class Prefab_Vehicle_Plane extends Prefab_Vehicle_Base {
	public float yawspeed = 0.05f;
	public float rollspeed = 0.12f;
	public float pitchspeed = 0.15f;
	public float yawspeed_taxing = 0.3f;
	public float forced_rudder_effect = 0;
	public float slipresist = 0.1f;
	public float slipresist_onground = 0.9f;
	public float gravity = 0.49f;
	public float stability = 500;
	public float stability2 = 0.7f;
	public double rotmotion_reduceSpeed = 0.2d;
	public double rotmotion_reduceSpeedRoll = 0.01d;
	public float forced_rotmotion_reduceSpeed = 0;
	public float throttle_gearDown;
	public float throttle_AF = 9;
	public float speedfactor = 0.03f;
	public float speedfactor_af = 0.01f;
	public float liftfactor = 0.04f;
	public float flapliftfactor = 0.0008f;
	public boolean autoflap = false;
	public float dragfactor = 0.00011f;
	public float flapdragfactor = 0.000001f;
	public float geardragfactor = 0.000005f;
	public float brakedragfactor = 0.05f;
	public float brakedragfactor_ground = 0.3f;
	public float onground_pitch;
	public Vector3d unitThrottle = new Vector3d(0,0,1);
	public double[] camerapos = new double[]{0,2.5-0.21,-3.6};
	
	public Entity[] riddenByEntities = new Entity[1];
	public Prefab_Seat[] riddenByEntitiesInfo = {new Prefab_Seat()};
	public Prefab_Seat[] riddenByEntitiesInfo_zoom = {new Prefab_Seat()};
	
	public boolean displayModernHud = false;
	
	
	
	
	
	public float pitchsighwidthmax = 5;
	public float yawsightwidthmax = 10;
	public float pitchsighwidthmin = -5;
	public float yawsightwidthmin = -10;
	
	public float maxDive = 75;
	public float startDive = 40;
	public float maxClimb = -60;
	public float minALT=20;
	public float cruiseALT=40;
	public float maxbank = 45;
	
	public boolean throttledown_onDive = false;
	public boolean Dive_bombing = false;
	public boolean Torpedo_bomber = false;
	public boolean sholdUseMain_ToG = true;
	public boolean sholdUseMain_ToA = false;
	public boolean type_F_Plane_T_Heli = false;
	
	
	public boolean useMain_withSub = false;
	public int changeWeaponCycleSetting = 3000;
	
	public int outSightCntMax = 600;
	
}

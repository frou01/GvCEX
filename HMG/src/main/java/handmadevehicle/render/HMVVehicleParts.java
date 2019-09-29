package handmadevehicle.render;

import handmadeguns.client.render.HMGGunParts;
import handmadeguns.client.render.HMGGunParts_Motion;
import handmadeguns.client.render.HMGGunParts_Motion_PosAndRotation;
import handmadeguns.client.render.HMGGunParts_Motions;

public class HMVVehicleParts extends HMGGunParts {
	public int linkedTurretID;
	public boolean isTurretParts = false;
	public int trackPieceCount;
	private HMGGunParts_Motion_PosAndRotation peraPosAndRotation;
	private HMGGunParts_Motions trackPositions = new HMGGunParts_Motions();
	private HMGGunParts_Motion_PosAndRotation[] SomethingPosAndRotation = new HMGGunParts_Motion_PosAndRotation[8];//x,y,z,z2,yaw,pitch,roll,throttle
	private HMGGunParts_Motions[] SomethingMotionKey = new HMGGunParts_Motions[8];//TODO å„ì˙é¿ëïÅI
	public boolean isTrack;
	public boolean isPera;
	
	public void AddTrackPositions(int   startflame,
	                               float startoffsetX,
	                               float startoffsetY,
	                               float startoffsetZ,
	                               float startrotationX,
	                               float startrotationY,
	                               float startrotationZ,
	                               int   endflame,
	                               float endoffsetX,
	                               float endoffsetY,
	                               float endoffsetZ,
	                               float endrotationX,
	                               float endrotationY,
	                               float endrotationZ){
		HMGGunParts_Motion motion = new HMGGunParts_Motion();
		motion.startflame =         startflame;
		motion.startposX =          startoffsetX;
		motion.startposY =          startoffsetY;
		motion.startposZ =          startoffsetZ;
		motion.startrotationX =     startrotationX;
		motion.startrotationY =     startrotationY;
		motion.startrotationZ =     startrotationZ;
		motion.endflame =         endflame;
		motion.endposX =          endoffsetX;
		motion.endposY =          endoffsetY;
		motion.endposZ =          endoffsetZ;
		motion.endrotationX =     endrotationX;
		motion.endrotationY =     endrotationY;
		motion.endrotationZ =     endrotationZ;
		motion.setup();
		trackPositions.addmotion(motion);
		isbelt = true;
	}
	
	public void setIsTrack(boolean isTrack,int trackPieceCount){
		this.isTrack = isTrack;
		this.trackPieceCount = trackPieceCount;
	}
	public void setIsTrack_Cloning(boolean isTrack,int trackPieceCount){
		this.isTrack = isTrack;
		this.trackPieceCount = trackPieceCount;
		this.isavatar = true;
	}
	public HMGGunParts_Motion_PosAndRotation getTrackPositions(float flame){
		return trackPositions.getpartsMotion(flame);
	}
	
	public void AddRenderinfTrack(float offsetX, float offsetY, float offsetZ, float rotationX, float rotationY, float rotationZ){
		peraPosAndRotation = new HMGGunParts_Motion_PosAndRotation(offsetX,offsetY,offsetZ,rotationX,rotationY,rotationZ);
		isPera = true;
	}
	public HMGGunParts_Motion_PosAndRotation getRenderinfOfPeraPosAndRotation(){
		return peraPosAndRotation;
	}
	
	public HMVVehicleParts(String string) {
		super(string);
	}
	
	public HMVVehicleParts(String string, int motherID, HMGGunParts mother) {
		super(string,motherID,mother);
	}
}

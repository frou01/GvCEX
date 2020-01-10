package handmadevehicle.render;

import handmadeguns.client.render.HMGGunParts;
import handmadeguns.client.render.HMGGunParts_Motion;
import handmadeguns.client.render.HMGGunParts_Motion_PosAndRotation;
import handmadeguns.client.render.HMGGunParts_Motions;

import static handmadeguns.HMGGunMaker.readerCnt;
import static java.lang.Integer.parseInt;

public class HMVVehicleParts extends HMGGunParts {
	public int linkedTurretID;
	public boolean isTurretParts = false;
	public boolean isTurret_linkedGunMount = false;
	public int trackPieceCount;
	private HMGGunParts_Motion_PosAndRotation peraPosAndRotation;
	private HMGGunParts_Motions trackPositions = new HMGGunParts_Motions();
	private HMGGunParts_Motion_PosAndRotation[] somethingPosAndRotation = new HMGGunParts_Motion_PosAndRotation[12];//x,y,z,z2,yaw,pitch,roll,gear,flap,brake,speed
	private HMGGunParts_Motions[] somethingMotionKey = new HMGGunParts_Motions[12];
	public boolean isTrack;
	public boolean isPera;
	
	public void AddSomethingMotionKey(String[] type){
		HMGGunParts_Motion motion = new HMGGunParts_Motion(type);
		int id = parseInt(type[readerCnt++]);
		if(somethingMotionKey[id] == null) somethingMotionKey[id] = new HMGGunParts_Motions();
		somethingMotionKey[id].addmotion(motion);
		isbelt = true;
	}
	public void AddTrackPositions(String[] type){
		HMGGunParts_Motion motion = new HMGGunParts_Motion(type);
		trackPositions.addmotion(motion);
		isbelt = true;
	}
	
	public void setIsTrack(boolean isPera,int trackPieceCount){
		this.isPera = isPera;
		this.trackPieceCount = trackPieceCount;
	}
	public void setIsPera(boolean isPera){
		this.isPera = isPera;
	}
	public void setIsTrack_Cloning(boolean isTrack,int trackPieceCount){
		this.isTrack = isTrack;
		this.trackPieceCount = trackPieceCount;
		this.isavatar = true;
	}
	
	public void AddRenderinfTrack(float offsetX, float offsetY, float offsetZ, float rotationX, float rotationY, float rotationZ){
		peraPosAndRotation = new HMGGunParts_Motion_PosAndRotation(offsetX,offsetY,offsetZ,rotationX,rotationY,rotationZ);
	}

	public void AddRenderinfSomething(float offsetX, float offsetY, float offsetZ, float rotationX, float rotationY, float rotationZ,int id){
		somethingPosAndRotation[id] = new HMGGunParts_Motion_PosAndRotation(offsetX,offsetY,offsetZ,rotationX,rotationY,rotationZ);
	}
	public HMGGunParts_Motion_PosAndRotation getRenderinfOfPeraPosAndRotation(){
		return peraPosAndRotation;
	}
	public HMGGunParts_Motion_PosAndRotation getRenderinfOfSomethingPosAndRotation(int id){
		return somethingPosAndRotation[id];
	}
	public HMGGunParts_Motion_PosAndRotation getTrackPositions(float flame){
		return trackPositions.getpartsMotion(flame);
	}
	public HMGGunParts_Motion_PosAndRotation getSomethingPositions(float flame,int id){
		if(somethingMotionKey[id] == null)return null;
		return somethingMotionKey[id].getpartsMotion(flame);
	}
	
	public HMVVehicleParts(String string) {
		super(string);
	}
	
	public HMVVehicleParts(String string, int motherID, HMGGunParts mother) {
		super(string,motherID,mother);
	}
}

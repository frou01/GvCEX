package handmadevehicle.entity.parts;

import javax.vecmath.Quat4d;
import javax.vecmath.Vector3d;

public class OBBInfo {
	public Vector3d pos;
	public Vector3d size;
	public float armor_Top = 0;
	public float armor_Bottom = 0;
	public float armor_Front = 0;
	public float armor_Back = 0;
	public float armor_SideLeft = 0;
	public float armor_SideRight = 0;
	public int linkedTurret=-1;

	public Vector3d boxRotCenter = new Vector3d(0,0,0);
	public Quat4d boxRotation = new Quat4d(0,0,0,1);

	public OBBInfo(Vector3d pos,Vector3d directs){
		this.pos = pos;
		this.size = directs;
	}
	public OBBInfo(Vector3d pos, Vector3d boxRotCenter, Vector3d directs, Quat4d boxRotation,
			   float armor_Top,
			   float armor_Bottom,
			   float armor_Front,
			   float armor_Back,
			   float armor_SideLeft,
			   float armor_SideRight,
			   int linkedTurret){
		this.pos = pos;
		this.boxRotCenter = boxRotCenter;
		this.size = directs;
		this.boxRotation = boxRotation;

		this.armor_Top = armor_Top;
		this.armor_Bottom = armor_Bottom;
		this.armor_Front = armor_Front;
		this.armor_Back = armor_Back;
		this.armor_SideLeft = armor_SideLeft;
		this.armor_SideRight = armor_SideRight;

		this.linkedTurret = linkedTurret;
	}
	public void set(Vector3d pos, Vector3d boxRotCenter, Vector3d directs, Quat4d boxRotation,
					float armor_Top,
					float armor_Bottom,
					float armor_Front,
					float armor_Back,
					float armor_SideLeft,
					float armor_SideRight,
					int linkedTurret){
		this.pos = pos;
		this.boxRotCenter = boxRotCenter;
		this.size = directs;
		this.boxRotation = boxRotation;
		this.armor_Top = armor_Top;
		this.armor_Bottom = armor_Bottom;
		this.armor_Front = armor_Front;
		this.armor_Back = armor_Back;
		this.armor_SideLeft = armor_SideLeft;
		this.armor_SideRight = armor_SideRight;
		this.linkedTurret = linkedTurret;
	}
}

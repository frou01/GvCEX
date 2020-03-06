package handmadevehicle.entity.prefab;

import handmadevehicle.entity.parts.OBB;
import handmadevehicle.entity.parts.OBBInfo;

import javax.vecmath.Vector3d;

public class Prefab_AdditionalBoundingBox extends OBB {
	public Prefab_AdditionalBoundingBox(Vector3d pos,Vector3d directs){
		info = new OBBInfo(pos,directs);
		maxvertex = new Vector3d();
		maxvertex.add(info.pos,info.size);
		minvertex = new Vector3d();
		minvertex.sub(info.pos,info.size);
	}
}

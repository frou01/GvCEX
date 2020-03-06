package handmadevehicle.entity.parts;

import javax.vecmath.Vector3d;

public class HiterFace {

	public Vector3d[] vertices = new Vector3d[3];
	public Vector3d faceNormal;

	
	public boolean hitCheck(Vector3d start,Vector3d end)
	{
		Vector3d relativeStart = new Vector3d();
		Vector3d relativeEnd = new Vector3d();
		relativeStart.sub(start,vertices[0]);
		relativeEnd.sub(end,vertices[0]);

		double startToHitVecLength = faceNormal.dot(relativeStart);

		Vector3d startToHitVec = new Vector3d();
		startToHitVec.sub(relativeEnd,relativeStart);
		startToHitVec.normalize();
		startToHitVec.scale(startToHitVecLength);

		Vector3d hitVec = startToHitVec;
		hitVec.add(relativeStart);

		Vector3d relativeA = new Vector3d();
		relativeA.sub(vertices[1],vertices[0]);
		Vector3d relativeB = new Vector3d();
		relativeB.sub(vertices[2],vertices[0]);
		double xChecker = (hitVec.x - relativeA.x)/(relativeB.x - relativeA.x);
		boolean xCeck = xChecker<1 && xChecker>0;
		double yChecker = (hitVec.y - relativeA.y)/(relativeB.y - relativeA.y);
		boolean yCeck = yChecker<1 && yChecker>0;
		double zChecker = (hitVec.z - relativeA.z)/(relativeB.z - relativeA.z);
		boolean zCeck = zChecker<1 && zChecker>0;


		return xCeck && yCeck && zCeck;
	}

	public Vector3d calculateFaceNormal()
	{
		Vector3d v1 = new Vector3d(vertices[1].x - vertices[0].x, vertices[1].y - vertices[0].y, vertices[1].z - vertices[0].z);
		Vector3d v2 = new Vector3d(vertices[2].x - vertices[0].x, vertices[2].y - vertices[0].y, vertices[2].z - vertices[0].z);
		Vector3d normalVector = new Vector3d();

		normalVector.cross(v1,v2);
		normalVector.normalize();

		return faceNormal = new Vector3d( normalVector.x,  normalVector.y,  normalVector.z);
	}
}

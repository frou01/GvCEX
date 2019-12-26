package handmadevehicle.entity;

import javax.vecmath.Vector3d;

public class EngineVapourTrailSetting {
	Vector3d centerPoint;
	Vector3d vapourPoint;
	Vector3d rotorAxis;
	float vapourOffset;//角度オフセット
	int vapourDivide;//1tick中の分割数
}

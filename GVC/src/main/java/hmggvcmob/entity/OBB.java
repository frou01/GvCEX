package hmggvcmob.entity;

import javax.vecmath.Vector3d;

public class OBB {
    Vector3d m_Pos;              // 位置
    Vector3d[] m_NormaDirect = new Vector3d[3];   // 方向ベクトル
    double[] m_fLength = new double[3];             // 各軸方向の長さ

    public OBB(Vector3d pos,Vector3d[] directs){
        m_Pos = pos;
        m_NormaDirect = directs;
        for(int i = 0;i<m_NormaDirect.length ; i++)m_fLength[i] = m_NormaDirect[i].length();
        for(Vector3d adire :directs)adire.normalize();
    }

    public Vector3d GetDirect( int elem ){
        return m_NormaDirect[elem];
    }
    public double GetLen_W( int elem ){
        return m_fLength[elem];
    }
    public Vector3d GetPos_W(){
        return m_Pos;
    }
}

package handmadevehicle;

import handmadevehicle.entity.EntityDummy_rider;
import handmadevehicle.entity.parts.turrets.TurretObj;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import javax.vecmath.*;

import java.io.*;
import java.util.ArrayList;

import static java.lang.Double.NaN;
import static java.lang.Double.isNaN;
import static java.lang.Math.*;

public class Utils {
    public static Vector3d unitX = new Vector3d(1,0,0);
    public static Vector3d unitY = new Vector3d(0,1,0);
    public static Vector3d unitZ = new Vector3d(0,0,1);
    public static double[] CalculateGunElevationAngle(Entity shooter, Entity target, float m_gravity, float energy){
        //http://www.sousakuba.com/Programming/algo_dandoukeisan.htmlにて配布されているプログラムより転用(え？作れって？ﾁｶﾚﾀ…)
        m_gravity *=-1;
        double dist_y = ((target.posY + target.getEyeHeight())-(shooter.posY + shooter.getEyeHeight()));
        double dist_x = shooter.getDistance(target.posX,shooter.posY,target.posZ);
        double a = (m_gravity * dist_x * dist_x) /  ( 2.0 * energy * energy );
        double b = dist_x / a;
        double c = ( a - dist_y ) / a;
        double root = sqrt( -c + ( b * b ) / 4.0);
        double ts = (b*b/4) - c;
        double agl[] = new double[3];
        if ( ts < 0.0 ) {
            agl[2]	= -1;
        }else {
            agl[0] = atan((-b / 2.0) - root) * 180 / PI;
            agl[1] = atan((-b / 2.0) + root) * 180 / PI;
        }
        return agl;
    }
    public static void RotateVectorAroundY(Vector3d invec,double factor){
        factor = toRadians(factor);
        double f1 = cos(factor);
        double f2 = sin(factor);
        double d0 = invec.x * (double)f1 + invec.z * (double)f2;
        double d1 = invec.y;
        double d2 = invec.z * (double)f1 - invec.x * (double)f2;
        invec.set(d0, d1, d2);
    }
    public static void RotateVectorAroundX(Vector3d invec,double factor){
        factor = toRadians(factor);
        double f1 = cos(factor);
        double f2 = sin(factor);
        double x = invec.x;
        double y = invec.y * (double)f1 + invec.z * (double)f2;
        double z = invec.z * (double)f1 - invec.y * (double)f2;
        invec.set(x,y,z);
    }
    public static void RotateVectorAroundZ(Vector3d invec,double factor)
    {
        factor = toRadians(factor);
        double f1 = cos(factor);
        double f2 = sin(factor);
        double x = invec.x * (double)f1 - invec.y * (double)f2;
        double y = invec.y * (double)f1 + invec.x * (double)f2;
        double z = invec.z;
        invec.set(x,y,z);
    }
    public static double[] CalculateGunElevationAngle(EntityLivingBase shooter, Entity target, float m_gravity, float energy ,float yoffset){
        //http://www.sousakuba.com/Programming/algo_dandoukeisan.htmlにて配布されているプログラムより転用(え？作れって？ﾁｶﾚﾀ…)
        m_gravity *=-1;
        double dist_y = ((target.posY + target.getEyeHeight())-(shooter.posY + shooter.getEyeHeight() + yoffset));
        double dist_x = shooter.getDistance(target.posX,shooter.posY,target.posZ);
        double a = (m_gravity * dist_x * dist_x) /  ( 2.0 * energy * energy );
        double b = dist_x / a;
        double c = ( a - dist_y ) / a;
        double root = sqrt( -c + ( b * b ) / 4.0);
        double ts = (b*b/4) - c;
        double agl[] = new double[3];
        if ( ts < 0.0 ) {
            agl[2]	= -1;
        }else {
            agl[0] = atan((-b / 2.0) - root) * 180 / PI;
            agl[1] = atan((-b / 2.0) + root) * 180 / PI;
        }
        return agl;
    }
    public static double[] CalculateGunElevationAngle(double posX,double posY,double posZ, Entity target, float m_gravity, float energy){
        //http://www.sousakuba.com/Programming/algo_dandoukeisan.htmlにて配布されているプログラムより転用(え？作れって？ﾁｶﾚﾀ…)
        m_gravity *=-1;
        double dist_y = ((target.posY + target.getEyeHeight())-(posY));
        double dist_x = target.getDistance(posX,target.posY,posZ);
        double a = (m_gravity * dist_x * dist_x) /  ( 2.0 * energy * energy );
        double b = dist_x / a;
        double c = ( a - dist_y ) / a;
        double root = sqrt( -c + ( b * b ) / 4.0);
        double ts = (b*b/4) - c;
        double agl[] = new double[3];
        if ( ts < 0.0 ) {
            agl[2]	= -1;
        }else {
            agl[0] = atan((-b / 2.0) - root) * 180 / PI;
            agl[1] = atan((-b / 2.0) + root) * 180 / PI;
        }
        return agl;
    }
    public static double[] CalculateGunElevationAngle(double posX,double posY,double posZ, double targetX,double targetY,double targetZ, float m_gravity, float energy){
        //http://www.sousakuba.com/Programming/algo_dandoukeisan.htmlにて配布されているプログラムより転用(え？作れって？ﾁｶﾚﾀ…)
        m_gravity *=-1;
        double dist_y = ((targetY)-(posY));
        double dist_x = sqrt((posX - targetX) * (posX - targetX) + (posZ - targetZ) * (posZ - targetZ));
        double a = (m_gravity * dist_x * dist_x) /  ( 2.0 * energy * energy );
        double b = dist_x / a;
        double c = ( a - dist_y ) / a;
        double root = sqrt( -c + ( b * b ) / 4.0);
        double ts = (b*b/4) - c;
        double agl[] = new double[3];
        if ( ts < 0.0 ) {
            agl[2]	= -1;
        }else {
            agl[0] = atan((-b / 2.0) - root) * 180 / PI;
            agl[1] = atan((-b / 2.0) + root) * 180 / PI;
        }
        return agl;
    }
    public static double[] CalculateGunElevationAngle(EntityLivingBase shooter, Entity target, float m_gravity, float energy ,float xoffset , float yoffset){
        //http://www.sousakuba.com/Programming/algo_dandoukeisan.htmlにて配布されているプログラムより転用(え？作れって？ﾁｶﾚﾀ…)
        m_gravity *=-1;
        double dist_y = ((target.posY + target.getEyeHeight())-(shooter.posY + shooter.getEyeHeight() + yoffset));
        double dist_x = shooter.getDistance(target.posX,shooter.posY,target.posZ) + xoffset;
        double a = (m_gravity * dist_x * dist_x) /  ( 2.0 * energy * energy );
        double b = dist_x / a;
        double c = ( a - dist_y ) / a;
        double root = sqrt( -c + ( b * b ) / 4.0);
        double ts = (b*b/4) - c;
        double agl[] = new double[3];
        if ( ts < 0.0 ) {
            agl[2]	= -1;
        }else {
            agl[0] = atan((-b / 2.0) - root) * 180 / PI;
            agl[1] = atan((-b / 2.0) + root) * 180 / PI;
        }
        return agl;
    }
    public static void rotateaxisbymatrix4(Matrix4d src, Vector3d axis, float angle){
        //任意の軸で回転する。
        double c = Math.cos(angle);
        double s = Math.sin(angle);
        double a = 1.0f - c;
        double xy = axis.x*axis.y;
        double yz = axis.y*axis.z;
        double xz = axis.x*axis.z;
        double xs = axis.x*s;
        double ys = axis.y*s;
        double zs = axis.z*s;

        double f00 = axis.x*axis.x*a+c;
        double f01 = xy*a+zs;
        double f02 = xz*a-ys;
        double f10 = xy*a-zs;
        double f11 = axis.y*axis.y*a+c;
        double f12 = yz*a+xs;
        double f20 = xz*a+ys;
        double f21 = yz*a-xs;
        double f22 = axis.z*axis.z*a+c;

        double t00 = src.m00 * f00 + src.m10 * f01 + src.m20 * f02;
        double t01 = src.m01 * f00 + src.m11 * f01 + src.m21 * f02;
        double t02 = src.m02 * f00 + src.m12 * f01 + src.m22 * f02;
        double t03 = src.m03 * f00 + src.m13 * f01 + src.m23 * f02;
        double t10 = src.m00 * f10 + src.m10 * f11 + src.m20 * f12;
        double t11 = src.m01 * f10 + src.m11 * f11 + src.m21 * f12;
        double t12 = src.m02 * f10 + src.m12 * f11 + src.m22 * f12;
        double t13 = src.m03 * f10 + src.m13 * f11 + src.m23 * f12;
        src.m20 = src.m00 * f20 + src.m10 * f21 + src.m20 * f22;
        src.m21 = src.m01 * f20 + src.m11 * f21 + src.m21 * f22;
        src.m22 = src.m02 * f20 + src.m12 * f21 + src.m22 * f22;
        src.m23 = src.m03 * f20 + src.m13 * f21 + src.m23 * f22;
        src.m00 = t00;
        src.m01 = t01;
        src.m02 = t02;
        src.m03 = t03;
        src.m10 = t10;
        src.m11 = t11;
        src.m12 = t12;
        src.m13 = t13;
    }
    public static Vec3 getLook(float p_70676_1_, float rotationYawin, float rotationPitchin)
    {
        float f1;
        float f2;
        float f3;
        float f4;

        if (p_70676_1_ == 1.0F)
        {
            f1 = MathHelper.cos(-rotationYawin * 0.017453292F - (float)Math.PI);
            f2 = MathHelper.sin(-rotationYawin * 0.017453292F - (float)Math.PI);
            f3 = -MathHelper.cos(-rotationPitchin * 0.017453292F);
            f4 = MathHelper.sin(-rotationPitchin * 0.017453292F);
            return Vec3.createVectorHelper((double)(f2 * f3), (double)f4, (double)(f1 * f3));
        }
        else
        {
            f1 = MathHelper.cos(-rotationYawin * 0.017453292F - (float)Math.PI);
            f2 = MathHelper.sin(-rotationYawin * 0.017453292F - (float)Math.PI);
            f3 = -MathHelper.cos(-rotationPitchin * 0.017453292F);
            f4 = MathHelper.sin(-rotationPitchin * 0.017453292F);
            return Vec3.createVectorHelper((double)(f2 * f3)*p_70676_1_, (double)f4*p_70676_1_, (double)(f1 * f3)*p_70676_1_);
        }
    }
    public static Vec3 rotationVector_byAxisVector(Vec3 axis,Vec3 tovec, float angle)
    {
        double axisVectorX = axis.xCoord;
        double axisVectorY = axis.yCoord;
        double axisVectorZ = axis.zCoord;
        double toVectorX = tovec.xCoord;
        double toVectorY = tovec.yCoord;
        double toVectorZ = tovec.zCoord;
        double angleRad = (double)angle / 180.0D * Math.PI;
        double sintheta = Math.sin(angleRad);
        double costheta = Math.cos(angleRad);
        double returnVectorX = (axisVectorX * axisVectorX * (1 - costheta) + costheta)               * toVectorX + (axisVectorX * axisVectorY * (1 - costheta) - axisVectorZ * sintheta) * toVectorY + (axisVectorZ * axisVectorX * (1 - costheta) + axisVectorY * sintheta) * toVectorZ;
        double returnVectorY = (axisVectorX * axisVectorY * (1 - costheta) + axisVectorZ * sintheta) * toVectorX + (axisVectorY * axisVectorY * (1 - costheta) + costheta)               * toVectorY + (axisVectorY * axisVectorZ * (1 - costheta) - axisVectorX * sintheta) * toVectorZ;
        double returnVectorZ = (axisVectorZ * axisVectorX * (1 - costheta) - axisVectorY * sintheta) * toVectorX + (axisVectorY * axisVectorZ * (1 - costheta) + axisVectorX * sintheta) * toVectorY + (axisVectorZ * axisVectorZ * (1 - costheta) + costheta)               * toVectorZ;

        return Vec3.createVectorHelper(returnVectorX, returnVectorY, returnVectorZ);
    }
    public static Quat4d quatRotateAxis(Quat4d quat4d , AxisAngle4d angle4d){
        Quat4d temp = new Quat4d();
        quatsetFromVec4(temp,angle4d);
        temp.mul(quat4d);
        return temp;
    }
    public static void quatsetFromVec4(Quat4d quat4d, AxisAngle4d angle4d){
        double tx = angle4d.x;
        double ty = angle4d.y;
        double tz = angle4d.z;

        double s, c;
        s = sin(angle4d.angle);
        c = cos(angle4d.angle);
        quat4d.x = s * tx;
        quat4d.y = s * ty;
        quat4d.z = s * tz;
        quat4d.w = c;
    }

    public static void getVector_local_inRotatedObj(Vector3d in,Vector3d out,Quat4d rot){
        Quat4d invertRot = new Quat4d();
        invertRot.inverse(rot);
        NaNCheck(invertRot);
        transformVecforMinecraft(in);
        out.set(transformVecByQuat(in, invertRot));
        if(in != out){
            transformVecforMinecraft(in);
        }
        NaNCheck(in);
        NaNCheck(out);
    }

    public static Vector3d LinePrediction(Vector3d shotPosition, Vector3d targetPosition, Vector3d v3_Mv, double bulletSpeed)
    {
        //from https://qiita.com/A_rosuko/items/4a0612e4ed91f71813d6
        //Unityの物理はm/sなのでm/flameにする
        //目標の1フレームの移動速度 = targetMotion
        //射撃する位置から見た目標位置
        Vector3d v3_Pos = new Vector3d();v3_Pos.sub(targetPosition , shotPosition);;

        double A = v3_Mv.lengthSquared() - bulletSpeed * bulletSpeed;
        double B = v3_Pos.dot(v3_Mv);
        double C = v3_Pos.lengthSquared();

        //0割禁止
        if (A == 0 && B == 0)return targetPosition;
        if (A == 0 ){
            v3_Mv.scale(-C / B / 2);
            targetPosition.add(v3_Mv);
            return targetPosition;
        }

        //虚数解はどうせ当たらないので絶対値で無視した
        float D = (float) sqrt(abs(B * B - A * C));
        v3_Mv.scale(PlusMin((-B - D) / A, (-B + D) / A));
        targetPosition.add(v3_Mv);
        return targetPosition;
    }

    //プラスの最小値を返す(両方マイナスなら0)
    public static double PlusMin(double a, double b)
    {
        if (a < 0 && b < 0) return 0;
        if (a < 0) return b;
        if (b < 0) return a;
        return a < b ? a : b;
    }

    public static void transformVecforMinecraft(Vector3d vec){
        vec.z = -vec.z;
    }
    public static Vector3d transformVecByQuat(Vector3d vec, Quat4d qua)
    {
        if(vec.lengthSquared() == 0)return new Vector3d(vec);
        NaNCheck(qua);
        double x = qua.x + qua.x;
        double y = qua.y + qua.y;
        double z = qua.z + qua.z;
        double wx = qua.w * x;
        double wy = qua.w * y;
        double wz = qua.w * z;
        double xx = qua.x * x;
        double xy = qua.x * y;
        double xz = qua.x * z;
        double yy = qua.y * y;
        double yz = qua.y * z;
        double zz = qua.z * z;
        Vector3d temp = new Vector3d(
                ((vec.x * ((1.0f - yy) - zz)) + (vec.y * (xy - wz))) + (vec.z * (xz + wy)),
                ((vec.x * (xy + wz)) + (vec.y * ((1.0f - xx) - zz))) + (vec.z * (yz - wx)),
                ((vec.x * (xz - wy)) + (vec.y * (yz + wx))) + (vec.z * ((1.0f - xx) - yy)));
        NaNCheck(temp,vec);
        return temp;
    }
    public static void NaNCheck(Vector3d inVec){
        if (!Double.isNaN(inVec.x) && !Double.isNaN(inVec.y) && !Double.isNaN(inVec.z)) {
        }else {
            inVec.set(0,0,0);
        }

    }
    public static void NaNCheck(Vector3d inVec,Vector3d before){
        if (!Double.isNaN(inVec.x) && !Double.isNaN(inVec.y) && !Double.isNaN(inVec.z)) {
        }else {
            inVec.set(before);
        }

    }
    public static void NaNCheck(Quat4d inVec){
        if (!Double.isNaN(inVec.x) && !Double.isNaN(inVec.y) && !Double.isNaN(inVec.z) && !Double.isNaN(inVec.w)) {
        }else {
            inVec.set(0,0,0,1);
        }
    }
    public static Vec3 transformVecByQuat(Vec3 vec, Quat4d qua)
    {
        double x = qua.x + qua.x;
        double y = qua.y + qua.y;
        double z = qua.z + qua.z;
        double wx = qua.w * x;
        double wy = qua.w * y;
        double wz = qua.w * z;
        double xx = qua.x * x;
        double xy = qua.x * y;
        double xz = qua.x * z;
        double yy = qua.y * y;
        double yz = qua.y * z;
        double zz = qua.z * z;

        return Vec3.createVectorHelper(
                ((vec.xCoord * ((1.0f - yy) - zz)) + (vec.yCoord * (xy - wz))) - (vec.zCoord * (xz + wy)),
                ((vec.xCoord * (xy + wz)) + (vec.yCoord * ((1.0f - xx) - zz))) - (vec.zCoord * (yz - wx)),
                -((vec.xCoord * (xz - wy)) - (vec.yCoord * (yz + wx))) + (vec.zCoord * ((1.0f - xx) - yy)));
    }
    public static Matrix3d matrixfromQuat(Quat4d qua){
        double x = qua.x;
        double y = qua.y;
        double z = qua.z;
        double w = qua.w;

        double x2 = x * x;
        double y2 = y * y;
        double z2 = z * z;

        double xy = x * y;
        double xz = x * z;
        double yz = y * z;
        double wx = w * x;
        double wy = w * y;
        double wz = w * z;
        return new Matrix3d
                (       1-2*y2-2*z2 ,   2*xy+2*wz ,   2*xz-2*wy ,
                          2*xy-2*wz , 1-2*x2-2*z2 ,   2*yz+2*wx ,
                          2*xz+2*wy ,   2*yz-2*wx , 1-2*x2-2*y2);
    }
    public static double[] eulerfrommatrix(Matrix3d matrix3d){
        //0:P
        //1:Y
        //2:R
        double[] xyz = new double[3];
        if(matrix3d.m21>1)matrix3d.m21 = 1;
        if(matrix3d.m21<-1)matrix3d.m21 = -1;
        xyz[0] = asin(matrix3d.m21);
        if(Double.isNaN(xyz[0])){
            xyz[0] = 0;
            System.out.println("debug matrix " + matrix3d);
        }
        if(cos(xyz[0]) == 0){
            xyz[1] = 0;
            xyz[2] = atan2(matrix3d.m10, matrix3d.m00);
        }else {
            xyz[1] = atan2(matrix3d.m20, matrix3d.m22);
            xyz[2] = atan2(matrix3d.m01, matrix3d.m11);
        }
        if(Double.isNaN(xyz[1])){
            xyz[1] = 0;
            System.out.println("debug matrix " + matrix3d);
        }
        if(Double.isNaN(xyz[2])){
            xyz[2] = 0;
            System.out.println("debug matrix " + matrix3d);
        }
        return xyz;
    }

    public static void getCross(Vector3d tovec , Vector3d a,Vector3d b){
        tovec.cross(a,b);
    }
    public static double getDot(Vector3d a,Vector3d b){
        return a.dot(b);
    }
    public static double getLength(Vector3d a){
        return a.length();
    }

    public static double getMax(double[] data){
        double max = 0;
        for(int i = 0;i < data.length;i++){
            max = max<data[i] ? data[i]:max;
        }
//        System.out.println(" " + max);
        return max;
    }
    public static double getmin(double[] data){
        double min = 0;
        for(int i = 0;i < data.length;i++){
            min = min>data[i] ? data[i]:min;
        }
//        System.out.println(" " + max);
        return min;
    }
    public static double getMaxinsomeVectors(Vector3d[] data,int axis){
        double[] doubles = new double[data.length];
        int cnt = 0;
        for(Vector3d temp:data){
            switch (axis){
                case 0:
                    doubles[cnt] = temp.x;
                    break;
                case 1:
                    doubles[cnt] = temp.y;
                    break;
                case 2:
                    doubles[cnt] = temp.z;
                    break;
            }
            cnt ++ ;
        }
        double max = NaN;
        for(int i = 0;i < data.length;i++){
            max = (isNaN(max) || max<doubles[i]) ? doubles[i]:max;
        }
//        System.out.println(" " + max);
        return max;
    }
    public static double getmininsomeVectors(Vector3d[] data,int axis){
        double[] doubles = new double[data.length];
        int cnt = 0;
        for(Vector3d temp:data){
            switch (axis){
                case 0:
                    doubles[cnt] = temp.x;
                    break;
                case 1:
                    doubles[cnt] = temp.y;
                    break;
                case 2:
                    doubles[cnt] = temp.z;
                    break;
            }
            cnt ++ ;
        }
        double min = NaN;
        for(int i = 0;i < data.length;i++){
            min = (isNaN(min) || min>doubles[i]) ? doubles[i]:min;
        }
//        System.out.println(" " + min);
        return min;
    }
    public static double angle_cos(Vector3d var1,Vector3d var2) {
        double ret = var2.dot(var1) / (var2.length() * var1.length());
        if (ret < -1.0D) {
            ret = -1.0D;
        }

        if (ret > 1.0D) {
            ret = 1.0D;
        }
        if(Double.isNaN(ret)){
            ret = 1;
        }

        return ret;
    }
    public static Vector3d vector_interior_division(Vector3d var1,Vector3d var2,double coefficient) {
        Vector3d returnvec = new Vector3d();
        returnvec.x = var1.x * (1 - coefficient) + var2.x * coefficient;
        returnvec.y = var1.y * (1 - coefficient) + var2.y * coefficient;
        returnvec.z = var1.z * (1 - coefficient) + var2.z * coefficient;
        return returnvec;
    }

    public static Vector3d getIntermediateWithXValue(Vector3d start,Vector3d end, double target)
    {
        double d1 = end.x - start.x;//各軸ごとに距離を出す
        double d2 = end.y - start.y;//
        double d3 = end.z - start.z;//

        if (d1 * d1 < 1.0000000116860974E-7D)
        {
            return null;
        }
        else
        {
            double d4 = (target - start.x) / d1;//倍率算出
            return d4 >= 0.0D && d4 <= 1.0D ? new Vector3d(start.x + d1 * d4, start.y + d2 * d4, start.z + d3 * d4) : null;
        }
    }
    public static Vector3d getIntermediateWithYValue(Vector3d start,Vector3d end, double target)
    {
        double d1 = end.x - start.x;//各軸ごとに距離を出す
        double d2 = end.y - start.y;//
        double d3 = end.z - start.z;//

        if (d2 * d2 < 1.0000000116860974E-7D)
        {
            return null;
        }
        else
        {
            double d4 = (target - start.y) / d2;//倍率算出
            return d4 >= 0.0D && d4 <= 1.0D ? new Vector3d(start.x + d1 * d4, start.y + d2 * d4, start.z + d3 * d4) : null;
        }
    }
    public static Vector3d getIntermediateWithZValue(Vector3d start,Vector3d end, double target)
    {
        double d1 = end.x - start.x;//各軸ごとに距離を出す
        double d2 = end.y - start.y;//
        double d3 = end.z - start.z;//

        if (d3 * d3 < 1.0000000116860974E-7D)
        {
            return null;
        }
        else
        {
            double d4 = (target - start.z) / d3;//倍率算出
            return d4 >= 0.0D && d4 <= 1.0D ? new Vector3d(start.x + d1 * d4, start.y + d2 * d4, start.z + d3 * d4) : null;
        }
    }
    public static Vec3 getMinecraftVecObj(Vector3d invec){
        return invec == null ? null : Vec3.createVectorHelper(invec.x,invec.y,invec.z);
    }
    public static Vec3 javaxLikeVec3SUM(Vec3 invec1,Vec3 invec2){
        return invec1.addVector(invec2.xCoord,invec2.yCoord,invec2.zCoord);
    }
    public static Vector3d getjavaxVecObj(Vec3 invec){
        return invec == null ? null : new Vector3d(invec.xCoord,invec.yCoord,invec.zCoord);
    }
    public static Vector3d getJavaxVecFromIntArray(int[] in){
        return new Vector3d(in[0],in[1],in[2]);
    }
    public static int[] getIntPosesFromVector(Vector3d vector3d){
        return new int[]{MathHelper.floor_double(vector3d.x),
                MathHelper.floor_double(vector3d.y),
                MathHelper.floor_double(vector3d.z)};
    }
    public static Block getBlock(World world, Vector3d vector3d){
        int[] pos = getIntPosesFromVector(vector3d);
        return world.getBlock(pos[0],pos[1],pos[2]);
    }
    public static float getBlockHardness(World world, Vector3d vector3d){
        int[] pos = getIntPosesFromVector(vector3d);
        return world.getBlock(pos[0],pos[1],pos[2]).getBlockHardness(world,pos[0],pos[1],pos[2]);
    }
    public static void setBlock(World world, Vector3d vector3d,Block block,boolean replace){
        int[] pos = getIntPosesFromVector(vector3d);
        if(replace || !getBlock(world,vector3d).getMaterial().isSolid())world.setBlock(pos[0],pos[1],pos[2],block);
    }
    public static void playBlockDestroyEffect(World world, Vector3d vector3d){
        int[] pos = getIntPosesFromVector(vector3d);
        world.playAuxSFX(2001,pos[0],pos[1],pos[2], Block.getIdFromBlock(getBlock(world,vector3d)));
    }
    public static double getDistanceSq(Vector3d a,Vector3d b){
        a = new Vector3d(a);
        b = new Vector3d(b);
        a.sub(b);
        return a.lengthSquared();
    }
    
    public static byte[] fromObject(Object o) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutput out = new ObjectOutputStream(bos);
        out.writeObject(o);
        byte[] bytes = bos.toByteArray();
        out.close();
        bos.close();
        return bytes;
    }
    public static Object toObject(byte[] bytes) throws ClassNotFoundException, IOException{
        return new ObjectInputStream(new ByteArrayInputStream(bytes)).readObject();
    }
    public static void addAllTurret(ArrayList<TurretObj> turrets , TurretObj current){
        if(current !=null) {
            turrets.add(current);
            for (TurretObj a_child : current.getChilds()) {
                addAllTurret(turrets, a_child);
            }
            for (TurretObj a_brothers : current.getBrothers()) {
                addAllTurret(turrets, a_brothers);
            }
        }
    }
    public static boolean canMoveEntity(Entity entity){
        if(entity.ridingEntity instanceof EntityDummy_rider && ((EntityDummy_rider) entity.ridingEntity).linkedBaseLogic.ispilot(entity))return true;
        if(entity.ridingEntity == null)return true;
        return false;
    }
}

package handmadevehicle.entity.parts;

import handmadevehicle.Utils;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;

import javax.vecmath.Vector3d;

import static handmadevehicle.Utils.*;
import static handmadevehicle.Utils.getDistanceSq;

public class OBB {
    Vector3d pos;
    Vector3d size;
    public Vector3d maxvertex;
    public Vector3d minvertex;
    public double minXforColCheck;
    public double minYforColCheck;
    public double minZforColCheck;
    public double maxXforColCheck;
    public double maxYforColCheck;
    public double maxZforColCheck;
    
    

    public OBB(Vector3d pos,Vector3d directs){
        this.pos = pos;
        size = directs;
        size.scale(0.5);
    }
    public void updateColChecker(ModifiedBoundingBox modifiedBoundingBox){
        maxvertex = new Vector3d();
        maxvertex.add(pos,size);
        minvertex = new Vector3d();
        minvertex.sub(pos,size);
        Vector3d[] points = {
                new Vector3d(maxvertex.x,maxvertex.y,maxvertex.z),
                new Vector3d(maxvertex.x,maxvertex.y,minvertex.z),
                new Vector3d(maxvertex.x,minvertex.y,maxvertex.z),
                new Vector3d(maxvertex.x,minvertex.y,minvertex.z),
                new Vector3d(minvertex.x,maxvertex.y,maxvertex.z),
                new Vector3d(minvertex.x,maxvertex.y,minvertex.z),
                new Vector3d(minvertex.x,minvertex.y,maxvertex.z),
                new Vector3d(minvertex.x,minvertex.y,minvertex.z)};
        Vector3d rotcenterVec = new Vector3d(modifiedBoundingBox.centerRotX,modifiedBoundingBox.centerRotY,modifiedBoundingBox.centerRotZ);
        for(int i =0;i<points.length;i++){
            Vector3d temp = points[i];
            temp.sub(rotcenterVec);
            temp.set(Utils.transformVecByQuat(temp,modifiedBoundingBox.rot));
            transformVecforMinecraft(temp);
            temp.add(rotcenterVec);
        }
        
        minXforColCheck = modifiedBoundingBox.posX + (Utils.getmininsomeVectors(points,0));
        minYforColCheck = modifiedBoundingBox.posY + (Utils.getmininsomeVectors(points,1));
        minZforColCheck = modifiedBoundingBox.posZ + (Utils.getmininsomeVectors(points,2));
        maxXforColCheck = modifiedBoundingBox.posX + (Utils.getMaxinsomeVectors(points,0));
        maxYforColCheck = modifiedBoundingBox.posY + (Utils.getMaxinsomeVectors(points,1));
        maxZforColCheck = modifiedBoundingBox.posZ + (Utils.getMaxinsomeVectors(points,2));
    }
    
    public VectorAndHitSide getIntercept(ModifiedBoundingBox modifiedBoundingBox,Vector3d startVec,Vector3d endVec,Vector3d posVec,Vector3d rotcenterVec){
        Vector3d vec32 = getIntermediateWithXValue(startVec, endVec, minvertex.x);
        Vector3d vec33 = getIntermediateWithXValue(startVec, endVec, maxvertex.x);
    
        Vector3d vec34 = getIntermediateWithYValue(startVec, endVec, minvertex.y);
        Vector3d vec35 = getIntermediateWithYValue(startVec, endVec, maxvertex.y);
    
        Vector3d vec36 = getIntermediateWithZValue(startVec, endVec, minvertex.z);
        Vector3d vec37 = getIntermediateWithZValue(startVec, endVec, maxvertex.z);
    
    
        if (!this.isVecInYZ(getMinecraftVecObj(vec32))) {
            vec32 = null;
        }
    
        if (!this.isVecInYZ(getMinecraftVecObj(vec33))) {
            vec33 = null;
        }
    
        if (!this.isVecInXZ(getMinecraftVecObj(vec34))) {
            vec34 = null;
        }
    
        if (!this.isVecInXZ(getMinecraftVecObj(vec35))) {
            vec35 = null;
        }
    
        if (!this.isVecInXY(getMinecraftVecObj(vec36))) {
            vec36 = null;
        }
    
        if (!this.isVecInXY(getMinecraftVecObj(vec37))) {
            vec37 = null;
        }
    
        Vector3d vec38 = null;
    
        if (vec32 != null) {
            vec38 = vec32;
        }
    
        if (vec33 != null && (vec38 == null || getDistanceSq(startVec, vec33) < getDistanceSq(startVec, vec38))) {
            vec38 = vec33;
        }
    
        if (vec34 != null && (vec38 == null || getDistanceSq(startVec, vec34) < getDistanceSq(startVec, vec38))) {
            vec38 = vec34;
        }
    
        if (vec35 != null && (vec38 == null || getDistanceSq(startVec, vec35) < getDistanceSq(startVec, vec38))) {
            vec38 = vec35;
        }
    
        if (vec36 != null && (vec38 == null || getDistanceSq(startVec, vec36) < getDistanceSq(startVec, vec38))) {
            vec38 = vec36;
        }
    
        if (vec37 != null && (vec38 == null || getDistanceSq(startVec, vec37) < getDistanceSq(startVec, vec38))) {
            vec38 = vec37;
        }
    
        if (vec38 == null) {
                return new VectorAndHitSide(null,-1);
        } else {
            byte b0 = -1;
        
            if (vec38 == vec32) {
                b0 = 4;
            }
        
            if (vec38 == vec33) {
                b0 = 5;
            }
        
            if (vec38 == vec34) {
                b0 = 0;
            }
        
            if (vec38 == vec35) {
                b0 = 1;
            }
        
            if (vec38 == vec36) {
                b0 = 2;
            }
        
            if (vec38 == vec37) {
                b0 = 3;
            }
        
            vec38.sub(rotcenterVec);
            vec38 = Utils.transformVecByQuat(vec38, modifiedBoundingBox.rot);
            transformVecforMinecraft(vec38);
            vec38.add(rotcenterVec);
//            System.out.println("3"+vec38);
        
            vec38.add(posVec);
//                return new MovingObjectPosition(0, 0, 0, b0, getMinecraftVecObj(vec38));
            return new VectorAndHitSide(vec38,b0);
//            tempHitSide[cnt] = b0;
        }
    }
    
    private boolean isVecInYZ(Vec3 checkingVec)
    {
        return checkingVec != null && (checkingVec.yCoord >= minvertex.y && checkingVec.yCoord <= maxvertex.y && checkingVec.zCoord >= minvertex.z && checkingVec.zCoord <= maxvertex.z);
    }
    
    /**
     * Checks if the specified vector is within the XZ dimensions of the bounding box. Args: Vec3D
     */
    private boolean isVecInXZ(Vec3 checkingVec)
    {
        return checkingVec != null && (checkingVec.xCoord >= minvertex.x && checkingVec.xCoord <= maxvertex.x && checkingVec.zCoord >= minvertex.z && checkingVec.zCoord <= maxvertex.z);
    }
    
    /**
     * Checks if the specified vector is within the XY dimensions of the bounding box. Args: Vec3D
     */
    private boolean isVecInXY(Vec3 checkingVec)
    {
        return checkingVec != null && (checkingVec.xCoord >= minvertex.x && checkingVec.xCoord <= maxvertex.x && checkingVec.yCoord >= minvertex.y && checkingVec.yCoord <= maxvertex.y);
    }
    public Vector3d GetDirect(){
        return size;
    }
    public Vector3d GetPos_W(){
        return pos;
    }
    
    
    public boolean intersectsWith(ModifiedBoundingBox motherBox,AxisAlignedBB hittingbox){
        return
                hittingbox.maxX > minXforColCheck + motherBox.expandedminX
                        && hittingbox.minX < maxXforColCheck + motherBox.expandedmaxX
                        && hittingbox.maxY > minYforColCheck + motherBox.expandedminY
                        && hittingbox.minY < maxYforColCheck + motherBox.expandedmaxY
                        && hittingbox.maxZ > minZforColCheck + motherBox.expandedminZ
                        && hittingbox.minZ < maxZforColCheck + motherBox.expandedmaxZ
                ;
    }
    public boolean isVecInside(ModifiedBoundingBox motherBox,Vec3 p_72318_1_)
    {
        return
                p_72318_1_.xCoord > minXforColCheck + motherBox.expandedminX
                        && p_72318_1_.xCoord < maxXforColCheck + motherBox.expandedmaxX
                        && p_72318_1_.yCoord > minYforColCheck + motherBox.expandedminY
                        && p_72318_1_.yCoord < maxYforColCheck + motherBox.expandedmaxY
                        && p_72318_1_.zCoord > minZforColCheck + motherBox.expandedminZ
                        && p_72318_1_.zCoord < maxZforColCheck + motherBox.expandedmaxZ
                ;
    }
    
    
//
//    public double calculateXOffset(AxisAlignedBB p_72316_1_, double p_72316_2_)
//    {
//        if (p_72316_1_.maxY > this.minYforColCheck && p_72316_1_.minY < this.maxYforColCheck)
//        {
//            if (p_72316_1_.maxZ > this.minZforColCheck && p_72316_1_.minZ < this.maxZforColCheck)
//            {
//                double d1;
//
//                if (p_72316_2_ > 0.0D && p_72316_1_.maxX <= this.minXforColCheck)
//                {
//                    d1 = this.minXforColCheck - p_72316_1_.maxX;
//
//                    if (d1 < p_72316_2_)
//                    {
//                        p_72316_2_ = d1;
//                    }
//                }
//
//                if (p_72316_2_ < 0.0D && p_72316_1_.minX >= this.maxXforColCheck)
//                {
//                    d1 = this.maxXforColCheck - p_72316_1_.minX;
//
//                    if (d1 > p_72316_2_)
//                    {
//                        p_72316_2_ = d1;
//                    }
//                }
//
//                return p_72316_2_;
//            }
//            else
//            {
//                return p_72316_2_;
//            }
//        }
//        else
//        {
//            return p_72316_2_;
//        }
//    }
//
//    /**
//     * if instance and the argument bounding boxes overlap in the X and Z dimensions, calculate the offset between them
//     * in the Y dimension.  return var2 if the bounding boxes do not overlap or if var2 is closer to 0 then the
//     * calculated offset.  Otherwise return the calculated offset.
//     */
//    public double calculateYOffset(AxisAlignedBB p_72323_1_, double p_72323_2_)
//    {
//        if (p_72323_1_.maxX > this.minXforColCheck && p_72323_1_.minX < this.maxXforColCheck)
//        {
//            if (p_72323_1_.maxZ > this.minZforColCheck && p_72323_1_.minZ < this.maxZforColCheck)
//            {
//                double d1;
//
//                if (p_72323_2_ > 0.0D && p_72323_1_.maxY <= this.minYforColCheck)
//                {
//                    d1 = this.minYforColCheck - p_72323_1_.maxY;
//
//                    if (d1 < p_72323_2_)
//                    {
//                        p_72323_2_ = d1;
//                    }
//                }
//
//                if (p_72323_2_ < 0.0D && p_72323_1_.minY >= this.maxYforColCheck)
//                {
//                    d1 = this.maxYforColCheck - p_72323_1_.minY;
//
//                    if (d1 > p_72323_2_)
//                    {
//                        p_72323_2_ = d1;
//                    }
//                }
//
//                return p_72323_2_;
//            }
//            else
//            {
//                return p_72323_2_;
//            }
//        }
//        else
//        {
//            return p_72323_2_;
//        }
//    }
//
//    /**
//     * if instance and the argument bounding boxes overlap in the Y and X dimensions, calculate the offset between them
//     * in the Z dimension.  return var2 if the bounding boxes do not overlap or if var2 is closer to 0 then the
//     * calculated offset.  Otherwise return the calculated offset.
//     */
//    public double calculateZOffset(AxisAlignedBB p_72322_1_, double p_72322_2_)
//    {
//        if (p_72322_1_.maxX > this.minXforColCheck && p_72322_1_.minX < this.maxXforColCheck)
//        {
//            if (p_72322_1_.maxY > this.minYforColCheck && p_72322_1_.minY < this.maxYforColCheck)
//            {
//                double d1;
//
//                if (p_72322_2_ > 0.0D && p_72322_1_.maxZ <= this.minZforColCheck)
//                {
//                    d1 = this.minZforColCheck - p_72322_1_.maxZ;
//
//                    if (d1 < p_72322_2_)
//                    {
//                        p_72322_2_ = d1;
//                    }
//                }
//
//                if (p_72322_2_ < 0.0D && p_72322_1_.minZ >= this.maxZforColCheck)
//                {
//                    d1 = this.maxZforColCheck - p_72322_1_.minZ;
//
//                    if (d1 > p_72322_2_)
//                    {
//                        p_72322_2_ = d1;
//                    }
//                }
//
//                return p_72322_2_;
//            }
//            else
//            {
//                return p_72322_2_;
//            }
//        }
//        else
//        {
//            return p_72322_2_;
//        }
//    }
    
    public String toString(){
        return " [ " + pos.toString() + " , " + size.toString() +
                       "\n { minx" + minXforColCheck +
                       "\n , miny" + minYforColCheck +
                       "\n , minz" + minZforColCheck +
                       "\n , maxx" + maxXforColCheck +
                       "\n , maxy" + maxYforColCheck +
                       "\n , maxz" + maxZforColCheck +
                       " } ] ";
    }
}

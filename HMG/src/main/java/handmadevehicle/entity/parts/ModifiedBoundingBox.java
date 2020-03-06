package handmadevehicle.entity.parts;

import handmadevehicle.Utils;
import handmadevehicle.entity.parts.logics.BaseLogic;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import org.lwjgl.Sys;

import javax.vecmath.Quat4d;
import javax.vecmath.Vector3d;

import static handmadevehicle.Utils.*;
import static java.lang.Math.abs;

public class ModifiedBoundingBox extends AxisAlignedBB {
    public double centerRotX = 0;
    public double centerRotY = 0;
    public double centerRotZ = 0;
    
//    public double centerOffsetX = 0;
//    public double centerOffsetY = 0;
//    public double centerOffsetZ = 0;
//    public double realmaxX;
//    public double realmaxY;
//    public double realmaxZ;
//    public double minXforColCheck;
//    public double minYforColCheck;
//    public double minZforColCheck;
//    public double maxXforColCheck;
//    public double maxYforColCheck;
//    public double maxZforColCheck;
    
    public Vector3d maxvertex = new Vector3d();
    public Vector3d minvertex = new Vector3d();
    public OBB[] boxes = new OBB[1];

    public double posX;
    public double posY;
    public double posZ;
    
    double expandedminX = 0;
    double expandedminY = 0;
    double expandedminZ = 0;
    double expandedmaxX = 0;
    double expandedmaxY = 0;
    double expandedmaxZ = 0;

    public Quat4d rot = new Quat4d(0,0,0,1);

    public static ModifiedBoundingBox getBoundingBox(double newminX, double newminY, double newminZ, double newMaxX, double newMaxY, double newMaxZ,ModifiedBoundingBox origin)
    {
        ModifiedBoundingBox newbox = new ModifiedBoundingBox(newminX, newminY, newminZ, newMaxX, newMaxY, newMaxZ);
        newbox.expandedminX = newminX - origin.minX;
        newbox.expandedminY = newminY - origin.minY;
        newbox.expandedminZ = newminZ - origin.minZ;
        newbox.expandedmaxX = newMaxX - origin.maxX;
        newbox.expandedmaxY = newMaxY - origin.maxY;
        newbox.expandedmaxZ = newMaxZ - origin.maxZ;
        
        
        
        
        newbox.boxes = origin.boxes;
        newbox.maxvertex = origin.maxvertex;
        newbox.minvertex = origin.minvertex;
//        newbox.realmaxY = origin.realmaxY;
//        newbox.realmaxZ = origin.realmaxZ;
        newbox.posX = origin.posX;
        newbox.posY = origin.posY;
        newbox.posZ = origin.posZ;
        newbox.centerRotX = origin.centerRotX;
        newbox.centerRotY = origin.centerRotY;
        newbox.centerRotZ = origin.centerRotZ;
//        newbox.centerOffsetX = origin.centerOffsetX;
//        newbox.centerOffsetY = origin.centerOffsetY;
//        newbox.centerOffsetZ = origin.centerOffsetZ;
        newbox.rot = origin.rot;
        return newbox;
    }
    public ModifiedBoundingBox(double p_i2300_1_, double p_i2300_3_, double p_i2300_5_, double p_i2300_7_, double p_i2300_9_, double p_i2300_11_) {
        super(p_i2300_1_, p_i2300_3_, p_i2300_5_, p_i2300_7_, p_i2300_9_, p_i2300_11_);
    }
    public ModifiedBoundingBox(double p_i2300_1_, double p_i2300_3_, double p_i2300_5_, double p_i2300_7_, double p_i2300_9_, double p_i2300_11_,double cOffX,double cOffY,double cOffZ,double rMX,double rMY,double rMZ) {
        super(p_i2300_1_, p_i2300_3_, p_i2300_5_, p_i2300_7_, p_i2300_9_, p_i2300_11_);
//        centerOffsetX = cOffX;
//        centerOffsetY = cOffY;
//        centerOffsetZ = cOffZ;
//
//        realmaxX = rMX;
//        realmaxY = rMY;
//        realmaxZ = rMZ;
        boxes[0] = new OBB(new Vector3d(cOffX, cOffY, cOffZ),
                                  new Vector3d(rMX,
                                                      rMY,
                                                      rMZ));
        maxvertex.add(boxes[0].GetPos_W(),boxes[0].info.size);
        minvertex.sub(boxes[0].GetPos_W(),boxes[0].info.size);
        calculateMax_And_Min();
    }
    public void calculateMax_And_Min(){
        //X
        for(OBB abox:boxes){
            Vector3d tempMaxvertex = new Vector3d();
            tempMaxvertex.add(abox.info.pos,abox.info.size);
            Vector3d tempminvertex = new Vector3d();
            tempminvertex.sub(abox.info.pos,abox.info.size);
            if(maxvertex.x < tempMaxvertex.x)maxvertex.x = tempMaxvertex.x;
            if(maxvertex.y < tempMaxvertex.y)maxvertex.y = tempMaxvertex.y;
            if(maxvertex.z < tempMaxvertex.z)maxvertex.z = tempMaxvertex.z;
            
            if(minvertex.x > tempminvertex.x)minvertex.x = tempminvertex.x;
            if(minvertex.y > tempminvertex.y)minvertex.y = tempminvertex.y;
            if(minvertex.z > tempminvertex.z)minvertex.z = tempminvertex.z;
        }
    }
    public AxisAlignedBB setBounds(double p_72324_1_, double p_72324_3_, double p_72324_5_, double p_72324_7_, double p_72324_9_, double p_72324_11_)
    {
        this.minX = p_72324_1_;
        this.minY = p_72324_3_;
        this.minZ = p_72324_5_;
        this.maxX = p_72324_7_;
        this.maxY = p_72324_9_;
        this.maxZ = p_72324_11_;
        return this;
    }
    public AxisAlignedBB addCoord(double p_72321_1_, double p_72321_3_, double p_72321_5_)
    {
        double d3 = this.minX;
        double d4 = this.minY;
        double d5 = this.minZ;
        double d6 = this.maxX;
        double d7 = this.maxY;
        double d8 = this.maxZ;

        if (p_72321_1_ < 0.0D)
        {
            d3 += p_72321_1_;
        }

        if (p_72321_1_ > 0.0D)
        {
            d6 += p_72321_1_;
        }

        if (p_72321_3_ < 0.0D)
        {
            d4 += p_72321_3_;
        }

        if (p_72321_3_ > 0.0D)
        {
            d7 += p_72321_3_;
        }

        if (p_72321_5_ < 0.0D)
        {
            d5 += p_72321_5_;
        }

        if (p_72321_5_ > 0.0D)
        {
            d8 += p_72321_5_;
        }

        /**
         * Returns a bounding box with the specified bounds. Args: minX, minY, minZ, maxX, maxY, maxZ
         */
        return getBoundingBox(d3, d4, d5, d6, d7, d8,
                this);
    }

    /**
     * Returns a bounding box expanded by the specified vector (if negative numbers are given it will shrink). Args: x,
     * y, z
     */
    public AxisAlignedBB expand(double p_72314_1_, double p_72314_3_, double p_72314_5_)
    {
        double d3 = this.minX - p_72314_1_;
        double d4 = this.minY - p_72314_3_;
        double d5 = this.minZ - p_72314_5_;
        double d6 = this.maxX + p_72314_1_;
        double d7 = this.maxY + p_72314_3_;
        double d8 = this.maxZ + p_72314_5_;
        /**
         * Returns a bounding box with the specified bounds. Args: minX, minY, minZ, maxX, maxY, maxZ
         */
        return getBoundingBox(d3, d4, d5, d6, d7, d8, this);

    }

    public AxisAlignedBB func_111270_a(AxisAlignedBB p_111270_1_)
    {
        double d0 = Math.min(this.minX, p_111270_1_.minX);
        double d1 = Math.min(this.minY, p_111270_1_.minY);
        double d2 = Math.min(this.minZ, p_111270_1_.minZ);
        double d3 = Math.max(this.maxX, p_111270_1_.maxX);
        double d4 = Math.max(this.maxY, p_111270_1_.maxY);
        double d5 = Math.max(this.maxZ, p_111270_1_.maxZ);
        /**
         * Returns a bounding box with the specified bounds. Args: minX, minY, minZ, maxX, maxY, maxZ
         */
        return getBoundingBox(d0,
                d1,
                d2,
                d3,
                d4,
                d5,
                this);
    }

    /**
     * Returns a bounding box offseted by the specified vector (if negative numbers are given it will shrink). Args: x,
     * y, z
     */
    public AxisAlignedBB getOffsetBoundingBox(double p_72325_1_, double p_72325_3_, double p_72325_5_)
    {
        /**
         * Returns a bounding box with the specified bounds. Args: minX, minY, minZ, maxX, maxY, maxZ
         */
        return getBoundingBox(this.minX + p_72325_1_, this.minY + p_72325_3_, this.minZ + p_72325_5_, this.maxX + p_72325_1_, this.maxY + p_72325_3_, this.maxZ + p_72325_5_,
                this);
    }

//    /**
//     * if instance and the argument bounding boxes overlap in the Y and Z dimensions, calculate the offset between them
//     * in the X dimension.  return var2 if the bounding boxes do not overlap or if var2 is closer to 0 then the
//     * calculated offset.  Otherwise return the calculated offset.
//     */
//    public double calculateXOffset(AxisAlignedBB p_72316_1_, double p_72316_2_)
//    {
//        if (p_72316_1_.maxY > this.minY && p_72316_1_.minY < this.maxY)
//        {
//            if (p_72316_1_.maxZ > this.minZ && p_72316_1_.minZ < this.maxZ)
//            {
//                double d1;
//
//                if (p_72316_2_ > 0.0D && p_72316_1_.maxX <= this.minX)
//                {
//                    d1 = this.minX - p_72316_1_.maxX;
//
//                    if (d1 < p_72316_2_)
//                    {
//                        p_72316_2_ = d1;
//                    }
//                }
//
//                if (p_72316_2_ < 0.0D && p_72316_1_.minX >= this.maxX)
//                {
//                    d1 = this.maxX - p_72316_1_.minX;
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
//        Vector3d toX = new Vector3d(realmaxX,0,0);
//        Vector3d toY = new Vector3d(0,realmaxY,0);
//        Vector3d toZ = new Vector3d(0,0,realmaxZ);
//        Vector3d thiscenterVec = new Vector3d(centerX(),centerY(),centerZ());
//
//        if (p_72323_1_.maxX > this.minX && p_72323_1_.minX < this.maxX)
//        {
//            if (p_72323_1_.maxZ > this.minZ && p_72323_1_.minZ < this.maxZ)
//            {
//                double d1;
//
//                if (p_72323_2_ > 0.0D && p_72323_1_.maxY <= this.minY)
//                {
//                    d1 = this.minY - p_72323_1_.maxY;
//
//                    if (d1 < p_72323_2_)
//                    {
//                        p_72323_2_ = d1;
//                    }
//                }
//
//                if (p_72323_2_ < 0.0D && p_72323_1_.minY >= this.maxY)
//                {
//                    d1 = this.maxY - p_72323_1_.minY;
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
//        if (p_72322_1_.maxX > this.minX && p_72322_1_.minX < this.maxX)
//        {
//            if (p_72322_1_.maxY > this.minY && p_72322_1_.minY < this.maxY)
//            {
//                double d1;
//
//                if (p_72322_2_ > 0.0D && p_72322_1_.maxZ <= this.minZ)
//                {
//                    d1 = this.minZ - p_72322_1_.maxZ;
//
//                    if (d1 < p_72322_2_)
//                    {
//                        p_72322_2_ = d1;
//                    }
//                }
//
//                if (p_72322_2_ < 0.0D && p_72322_1_.minZ >= this.maxZ)
//                {
//                    d1 = this.maxZ - p_72322_1_.minZ;
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


    public void update(double posX, double posY, double posZ){
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
//        Vector3d[] points = {
//                new Vector3d(maxvertex.x,maxvertex.y,maxvertex.z),
//                new Vector3d(maxvertex.x,maxvertex.y,minvertex.z),
//                new Vector3d(maxvertex.x,minvertex.y,maxvertex.z),
//                new Vector3d(maxvertex.x,minvertex.y,minvertex.z),
//                new Vector3d(minvertex.x,maxvertex.y,maxvertex.z),
//                new Vector3d(minvertex.x,maxvertex.y,minvertex.z),
//                new Vector3d(minvertex.x,minvertex.y,maxvertex.z),
//                new Vector3d(minvertex.x,minvertex.y,minvertex.z)};
//        Vector3d rotcenterVec = new Vector3d(centerRotX,centerRotY,centerRotZ);
//        for(int i =0;i<points.length;i++){
//            Vector3d temp = points[i];
//            temp.sub(rotcenterVec);
//            temp.set(Utils.transformVecByQuat(temp,rot));
//            transformVecforMinecraft(temp);
//            temp.add(rotcenterVec);
//        }
//
//        minXforColCheck = this.posX + (Utils.getmininsomeVectors(points,0));
//        minYforColCheck = this.posY + (Utils.getmininsomeVectors(points,1));
//        minZforColCheck = this.posZ + (Utils.getmininsomeVectors(points,2));
//        maxXforColCheck = this.posX + (Utils.getMaxinsomeVectors(points,0));
//        maxYforColCheck = this.posY + (Utils.getMaxinsomeVectors(points,1));
//        maxZforColCheck = this.posZ + (Utils.getMaxinsomeVectors(points,2));
        for(OBB abox:boxes){
            abox.update(this);
//            System.out.println("debug" + abox);
        }
    }
    public void update(double posX, double posY, double posZ, BaseLogic baseLogic){
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;

        for(OBB abox:boxes){
            abox.update(this,baseLogic);
        }
    }
@Override
    public boolean intersectsWith(AxisAlignedBB hittingbox){
        for(OBB abox:boxes){
            if(abox.intersectsWith(this,hittingbox)) {
//                if(hittingbox.maxX - hittingbox.minX > 1) {
//                    System.out.println("debug" + hittingbox);
//                }
                return true;
            }
        }
        return false;
        
////        System.out.println("debug hiting max x " + (hittingbox.maxZ));
////        System.out.println("debug hiting min x " + (hittingbox.minZ));
////        System.out.println("debug max x " + (maxZ));
//        return
//                hittingbox.maxX > minXforColCheck  && hittingbox.minX < maxXforColCheck &&
//                hittingbox.maxY > minYforColCheck  && hittingbox.minY < maxYforColCheck &&
//                hittingbox.maxZ > minZforColCheck  && hittingbox.minZ < maxZforColCheck;
    }

    /**
     * Offsets the current bounding box by the specified coordinates. Args: x, y, z
     */
    public AxisAlignedBB offset(double p_72317_1_, double p_72317_3_, double p_72317_5_)
    {
        this.minX += p_72317_1_;
        this.minY += p_72317_3_;
        this.minZ += p_72317_5_;
        this.maxX += p_72317_1_;
        this.maxY += p_72317_3_;
        this.maxZ += p_72317_5_;
        
        this.posX += p_72317_1_;
        this.posY += p_72317_3_;
        this.posZ += p_72317_5_;
        return this;
    }

    /**
     * Returns if the supplied Vec3D is completely inside the bounding box
     */
    public boolean isVecInside(Vec3 p_72318_1_)
    {
//        System.out.println("isVecInside " + p_72318_1_);
//        return
//                p_72318_1_.xCoord > minXforColCheck  && p_72318_1_.xCoord < maxXforColCheck  &&
//                p_72318_1_.yCoord > minYforColCheck  && p_72318_1_.yCoord < maxYforColCheck  &&
//                p_72318_1_.zCoord > minZforColCheck  && p_72318_1_.zCoord < maxZforColCheck  ;

        for(OBB abox:boxes){
            if(abox.isVecInside(this,p_72318_1_)){
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the average length of the edges of the bounding box.
     */
    public double getAverageEdgeLength()
    {
        double d0 = this.maxX - this.minX;
        double d1 = this.maxY - this.minY;
        double d2 = this.maxZ - this.minZ;
        return (d0 + d1 + d2) / 3.0D;
    }

    /**
     * Returns a bounding box that is inset by the specified amounts
     */
    public AxisAlignedBB contract(double p_72331_1_, double p_72331_3_, double p_72331_5_)
    {
        double d3 = this.minX + p_72331_1_;
        double d4 = this.minY + p_72331_3_;
        double d5 = this.minZ + p_72331_5_;
        double d6 = this.maxX - p_72331_1_;
        double d7 = this.maxY - p_72331_3_;
        double d8 = this.maxZ - p_72331_5_;
        /**
         * Returns a bounding box with the specified bounds. Args: minX, minY, minZ, maxX, maxY, maxZ
         */
        return getBoundingBox(d3, d4, d5, d6, d7, d8,
                this);
    }

    public MovingObjectPosition calculateIntercept(Vec3 start, Vec3 end)
    {

        Vector3d startVec = new Vector3d(start.xCoord,start.yCoord,start.zCoord);
        Vector3d endVec = new Vector3d(end.xCoord,end.yCoord,end.zCoord);

        Vector3d posVec = new Vector3d(this.posX,this.posY,this.posZ);
        Vector3d rotcenterVec = new Vector3d(centerRotX,centerRotY,centerRotZ);


        startVec.sub(posVec);
        endVec.sub(posVec);

        Quat4d temp = new Quat4d(rot);
        inverse_safe(temp);

        transformVecforMinecraft(startVec);
        transformVecforMinecraft(endVec);

        startVec.sub(rotcenterVec);
        endVec.sub(rotcenterVec);

        startVec = Utils.transformVecByQuat(startVec,temp);
        endVec = Utils.transformVecByQuat(endVec,temp);

        startVec.add(rotcenterVec);
        endVec.add(rotcenterVec);
//        System.out.println("start" + startVec);
//        System.out.println("end" + endVec);
//
//        System.out.println("1"+startVec);
//        System.out.println("2"+endVec);
        VectorAndHitSide[] tempHitpoint = new VectorAndHitSide[boxes.length];
        for(int cnt = 0;cnt < boxes.length;cnt++) {
            VectorAndHitSide vectorAndHitSide = boxes[cnt].getIntercept(this,startVec,endVec,posVec,rotcenterVec);
            tempHitpoint[cnt] = vectorAndHitSide;
//            System.out.println(" " + cnt + "" + tempHitpoint[cnt]);
//            System.out.println("debug" + vectorAndHitSide.vector3d);
//            Vector3d tempMaxvertex = new Vector3d();
//            tempMaxvertex.add(boxes[cnt].pos,boxes[cnt].size);
//            Vector3d tempminvertex = new Vector3d();
//            tempminvertex.sub(boxes[cnt].pos,boxes[cnt].size);
//            Vector3d vec32 = getIntermediateWithXValue(startVec, endVec, tempminvertex.x);
//            Vector3d vec33 = getIntermediateWithXValue(startVec, endVec, tempMaxvertex.x);
//
//            Vector3d vec34 = getIntermediateWithYValue(startVec, endVec, tempminvertex.y);
//            Vector3d vec35 = getIntermediateWithYValue(startVec, endVec, tempMaxvertex.y);
//
//            Vector3d vec36 = getIntermediateWithZValue(startVec, endVec, tempminvertex.z);
//            Vector3d vec37 = getIntermediateWithZValue(startVec, endVec, tempMaxvertex.z);
//
//
//            if (!this.isVecInYZ(getMinecraftVecObj(vec32))) {
//                vec32 = null;
//            }
//
//            if (!this.isVecInYZ(getMinecraftVecObj(vec33))) {
//                vec33 = null;
//            }
//
//            if (!this.isVecInXZ(getMinecraftVecObj(vec34))) {
//                vec34 = null;
//            }
//
//            if (!this.isVecInXZ(getMinecraftVecObj(vec35))) {
//                vec35 = null;
//            }
//
//            if (!this.isVecInXY(getMinecraftVecObj(vec36))) {
//                vec36 = null;
//            }
//
//            if (!this.isVecInXY(getMinecraftVecObj(vec37))) {
//                vec37 = null;
//            }
//
//            Vector3d vec38 = null;
//
//            if (vec32 != null) {
//                vec38 = vec32;
//            }
//
//            if (vec33 != null && (vec38 == null || getDistanceSq(startVec, vec33) < getDistanceSq(startVec, vec38))) {
//                vec38 = vec33;
//            }
//
//            if (vec34 != null && (vec38 == null || getDistanceSq(startVec, vec34) < getDistanceSq(startVec, vec38))) {
//                vec38 = vec34;
//            }
//
//            if (vec35 != null && (vec38 == null || getDistanceSq(startVec, vec35) < getDistanceSq(startVec, vec38))) {
//                vec38 = vec35;
//            }
//
//            if (vec36 != null && (vec38 == null || getDistanceSq(startVec, vec36) < getDistanceSq(startVec, vec38))) {
//                vec38 = vec36;
//            }
//
//            if (vec37 != null && (vec38 == null || getDistanceSq(startVec, vec37) < getDistanceSq(startVec, vec38))) {
//                vec38 = vec37;
//            }
//
//            if (vec38 == null) {
////                return null;
//            } else {
//                byte b0 = -1;
//
//                if (vec38 == vec32) {
//                    b0 = 4;
//                }
//
//                if (vec38 == vec33) {
//                    b0 = 5;
//                }
//
//                if (vec38 == vec34) {
//                    b0 = 0;
//                }
//
//                if (vec38 == vec35) {
//                    b0 = 1;
//                }
//
//                if (vec38 == vec36) {
//                    b0 = 2;
//                }
//
//                if (vec38 == vec37) {
//                    b0 = 3;
//                }
//
//                vec38.sub(rotcenterVec);
//                vec38 = Utils.transformVecByQuat(vec38, rot);
//                transformVecforMinecraft(vec38);
//                vec38.add(rotcenterVec);
////            System.out.println("3"+vec38);
//
//                vec38.add(posVec);
////                return new MovingObjectPosition(0, 0, 0, b0, getMinecraftVecObj(vec38));
//                tempHitpoint[cnt] = vec38;
//                tempHitSide[cnt] = b0;
//            }
        }
        VectorAndHitSide temp_return = null;
        double dist = -1;
        for(int cnt = 0;cnt < tempHitpoint.length;cnt++){
            VectorAndHitSide a_Hitpoint = tempHitpoint[cnt];
            if(a_Hitpoint == null)continue;
            double tempdist = start.squareDistanceTo(a_Hitpoint.vector3d.x,a_Hitpoint.vector3d.y,a_Hitpoint.vector3d.z);
            if(tempdist < dist || dist == -1){
                dist = tempdist;
                temp_return = a_Hitpoint;
            }
        }
//        System.out.println("debug "  + hitside);
        if(temp_return != null) {
            MovingObjectPosition temp_moving = new MovingObjectPosition(0, 0, 0, temp_return.hitside, getMinecraftVecObj(temp_return.vector3d));
            temp_moving.hitInfo = temp_return.vector3d_relative;
            return temp_moving;
        }
        return null;
    }

    /**
     * Checks if the specified vector is within the YZ dimensions of the bounding box. Args: Vec3D
     */
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

    /**
     * Sets the bounding box to the same bounds as the bounding box passed in. Args: axisAlignedBB
     */
    public void setBB(AxisAlignedBB p_72328_1_)
    {
        this.minX = p_72328_1_.minX;
        this.minY = p_72328_1_.minY;
        this.minZ = p_72328_1_.minZ;
        this.maxX = p_72328_1_.maxX;
        this.maxY = p_72328_1_.maxY;
        this.maxZ = p_72328_1_.maxZ;
    }

    public String toString()
    {
        return "box[" + this.minX + ", " + this.minY + ", " + this.minZ + " -> " + this.maxX + ", " + this.maxY + ", " + this.maxZ + "]";
    }
    public ModifiedBoundingBox copy()
    {
        /**
         * Returns a bounding box with the specified bounds. Args: minX, minY, minZ, maxX, maxY, maxZ
         */
        return getBoundingBox(this.minX, this.minY, this.minZ, this.maxX, this.maxY, this.maxZ,
                this);
    }
    public AxisAlignedBB noMod_copy()
    {
        /**
         * Returns a bounding box with the specified bounds. Args: minX, minY, minZ, maxX, maxY, maxZ
         */
        return AxisAlignedBB.getBoundingBox(this.minX, this.minY, this.minZ, this.maxX, this.maxY, this.maxZ);
    }
}

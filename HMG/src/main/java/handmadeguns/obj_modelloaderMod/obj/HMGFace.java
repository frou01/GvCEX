package handmadeguns.obj_modelloaderMod.obj;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import handmadeguns.HandmadeGunsCore;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.Vec3;

public class HMGFace
{
    public HMGVertex[] vertices;
    public HMGVertex[] HMGVertexNormals;
    public HMGVertex faceNormal;
    public HMGTextureCoordinate[] HMGTextureCoordinates;

    @SideOnly(Side.CLIENT)
    public void addFaceForRender(Tessellator tessellator)
    {
        addFaceForRender(tessellator, 0.0005F);
    }

    @SideOnly(Side.CLIENT)
    public void addFaceForRender(Tessellator tessellator, float textureOffset)
    {
        if (faceNormal == null)
        {
            faceNormal = this.calculateFaceNormal();
        }

        tessellator.setNormal(faceNormal.x, faceNormal.y, faceNormal.z);

        float averageU = 0F;
        float averageV = 0F;

        if ((HMGTextureCoordinates != null) && (HMGTextureCoordinates.length > 0))
        {
            for (int i = 0; i < HMGTextureCoordinates.length; ++i)
            {
                averageU += HMGTextureCoordinates[i].u;
                averageV += HMGTextureCoordinates[i].v;
            }

            averageU = averageU / HMGTextureCoordinates.length;
            averageV = averageV / HMGTextureCoordinates.length;
        }

        float offsetU, offsetV;

        for (int i = 0; i < vertices.length; ++i)
        {

            if ((HMGTextureCoordinates != null) && (HMGTextureCoordinates.length > 0))
            {
                offsetU = textureOffset;
                offsetV = textureOffset;

                if (HMGTextureCoordinates[i].u > averageU)
                {
                    offsetU = -offsetU;
                }
                if (HMGTextureCoordinates[i].v > averageV)
                {
                    offsetV = -offsetV;
                }

                if(this.HMGVertexNormals !=null && i<this.HMGVertexNormals.length)
                {
                    tessellator.setNormal(this.HMGVertexNormals[i].x, this.HMGVertexNormals[i].y, this.HMGVertexNormals[i].z);
                }

                tessellator.addVertexWithUV(vertices[i].x, vertices[i].y, vertices[i].z, HMGTextureCoordinates[i].u + offsetU + HandmadeGunsCore.textureOffsetU, HMGTextureCoordinates[i].v + offsetV + HandmadeGunsCore.textureOffsetV);
            }
            else
            {
                tessellator.addVertex(vertices[i].x, vertices[i].y, vertices[i].z);
            }
        }
    }

    public HMGVertex calculateFaceNormal()
    {
        Vec3 v1 = Vec3.createVectorHelper(vertices[1].x - vertices[0].x, vertices[1].y - vertices[0].y, vertices[1].z - vertices[0].z);
        Vec3 v2 = Vec3.createVectorHelper(vertices[2].x - vertices[0].x, vertices[2].y - vertices[0].y, vertices[2].z - vertices[0].z);
        Vec3 normalVector = null;

        normalVector = v1.crossProduct(v2).normalize();

        return new HMGVertex((float) normalVector.xCoord, (float) normalVector.yCoord, (float) normalVector.zCoord);
    }
}
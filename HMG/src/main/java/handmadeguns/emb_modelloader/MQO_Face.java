package handmadeguns.emb_modelloader;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.Vec3;

@SideOnly(Side.CLIENT)
public class MQO_Face
{
	public int[] verticesID;
	public MQO_Vertex[] vertices;
	public MQO_Vertex[] vertexNormals;
	public MQO_Vertex faceNormal;
	public MQO_TextureCoordinate[] textureCoordinates;

	public MQO_Face copy()
	{
		MQO_Face f = new MQO_Face();

		return f;
	}

	public void addFaceForRender(Tessellator tessellator)
	{
		addFaceForRender(tessellator, 0.000F);
	}

	public void addFaceForRender(Tessellator tessellator, float textureOffset)
	{
		if (faceNormal == null)
		{
			faceNormal = this.calculateFaceNormal();
		}

		tessellator.setNormal(faceNormal.x, faceNormal.y, faceNormal.z);

		float averageU = 0F;
		float averageV = 0F;

		if ((textureCoordinates != null) && (textureCoordinates.length > 0))
		{
			for (int i = 0; i < textureCoordinates.length; ++i)
			{
				averageU += textureCoordinates[i].u;
				averageV += textureCoordinates[i].v;
			}

			averageU = averageU / textureCoordinates.length;
			averageV = averageV / textureCoordinates.length;
		}

		float offsetU, offsetV;

		for (int i = 0; i < vertices.length; ++i)
		{

			if ((textureCoordinates != null) && (textureCoordinates.length > 0))
			{
				offsetU = textureOffset;
				offsetV = textureOffset;

				if (textureCoordinates[i].u > averageU)
				{
					offsetU = -offsetU;
				}
				if (textureCoordinates[i].v > averageV)
				{
					offsetV = -offsetV;
				}

				if(this.vertexNormals!=null && i<this.vertexNormals.length)
				{
					tessellator.setNormal(this.vertexNormals[i].x, this.vertexNormals[i].y, this.vertexNormals[i].z);
				}

				tessellator.addVertexWithUV(vertices[i].x, vertices[i].y, vertices[i].z, textureCoordinates[i].u + offsetU, textureCoordinates[i].v + offsetV);
			}
			else
			{
				tessellator.addVertex(vertices[i].x, vertices[i].y, vertices[i].z);
			}
		}
	}

	public MQO_Vertex calculateFaceNormal()
	{
		Vec3 v1 = Vec3.createVectorHelper(vertices[1].x - vertices[0].x, vertices[1].y - vertices[0].y, vertices[1].z - vertices[0].z);
		Vec3 v2 = Vec3.createVectorHelper(vertices[2].x - vertices[0].x, vertices[2].y - vertices[0].y, vertices[2].z - vertices[0].z);
		Vec3 normalVector = null;

		normalVector = v1.crossProduct(v2).normalize();

		return new MQO_Vertex((float) normalVector.xCoord, (float) normalVector.yCoord, (float) normalVector.zCoord);
	}
}

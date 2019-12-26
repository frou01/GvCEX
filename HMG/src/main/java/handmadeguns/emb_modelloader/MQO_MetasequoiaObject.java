package handmadeguns.emb_modelloader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.Arrays;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.resources.IResource;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModelCustom;
import net.minecraftforge.client.model.ModelFormatException;

import static java.lang.Float.parseFloat;
import static java.lang.Integer.parseInt;

@SideOnly(Side.CLIENT)
public class MQO_MetasequoiaObject implements IModelCustom
{
	public ArrayList<MQO_Vertex>		vertices		= new ArrayList<MQO_Vertex>();
	public ArrayList<MQO_GroupObject>	groupObjects	= new ArrayList<MQO_GroupObject>();
	private MQO_GroupObject				currentGroupObject	= null;
	public MQO_Material[] materials = null;
	private String						fileName;
	private int							vertexNum = 0;
	private int							faceNum = 0;

	public float	min  =  1000000;
	public float	minX =  1000000;
	public float	minY =  1000000;
	public float	minZ =  1000000;

	public float	max  = -1000000;
	public float	maxX = -1000000;
	public float	maxY = -1000000;
	public float	maxZ = -1000000;

	public float	size  = 0;
	public float	sizeX = 0;
	public float	sizeY = 0;
	public float	sizeZ = 0;

	public MQO_MetasequoiaObject(ResourceLocation resource) throws ModelFormatException
	{
		this.fileName = resource.toString();
		try
		{
			IResource res = Minecraft.getMinecraft().getResourceManager().getResource(resource);
			loadObjModel(res.getInputStream());
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public String getType()
	{
		return "mqo";
	}

	public int getVertexNum()
	{
		return this.vertexNum;
	}

	public int getFaceNum()
	{
		return this.faceNum;
	}

	public String getFileName()
	{
		return this.fileName;
	}

	public void checkMinMax(MQO_Vertex v)
	{
		if(v.x < this.minX) this.minX = v.x;
		if(v.y < this.minY) this.minY = v.y;
		if(v.z < this.minZ) this.minZ = v.z;
		if(v.x > this.maxX) this.maxX = v.x;
		if(v.y > this.maxY) this.maxY = v.y;
		if(v.z > this.maxZ) this.maxZ = v.z;
	}

	public boolean containsPart(String partName)
	{
		for(MQO_GroupObject groupObject : groupObjects)
		{
			if (partName.equals(groupObject.name))
			{
				return true;
			}
		}
		return false;
	}

	@Override
	public void renderAll()
	{
		for (MQO_GroupObject groupObject : groupObjects)
		{
			groupObject.render();
		}
	}

	@Override
	public void renderOnly(String... groupNames)
	{
		for (MQO_GroupObject groupObject : groupObjects)
		{
			for (String groupName : groupNames)
			{
				if (groupName.equals(groupObject.name))
				{
					groupObject.render();
				}
			}
		}
	}

	@Override
	public void renderPart(String partName)
	{
		for (MQO_GroupObject groupObject : groupObjects)
		{
			if (partName.equals(groupObject.name))
			{
				groupObject.render();
			}
		}
	}

	@Override
	public void renderAllExcept(String... excludedGroupNames)
	{
		for (MQO_GroupObject groupObject : groupObjects)
		{
			boolean skipPart=false;
			for (String excludedGroupName : excludedGroupNames)
			{
				if (excludedGroupName.equals(groupObject.name))
				{
					skipPart=true;
				}
			}
			if(!skipPart)
			{
				groupObject.render();
			}
		}
	}


//	public void renderAllLine(int startLine, int maxLine)
//	{
//		Tessellator tessellator = Tessellator.instance;
//
//		tessellator.startDrawing(1);
//
//		renderAllLine(tessellator, startLine, maxLine);
//
//		tessellator.draw();
//	}
//
//	public void renderAllLine(Tessellator tessellator, int startLine, int maxLine)
//	{
//		int lineCnt = 0;
//		for (MQO_GroupObject groupObject : groupObjects)
//		{
//			if (groupObject.faces.size() > 0)
//			{
//				for (MQO_Face face : groupObject.faces)
//				{
//					for (int i = 0; i < face.vertices.length/3; ++i)
//					{
//						MQO_Vertex v1 = face.vertices[i*3 + 0];
//						MQO_Vertex v2 = face.vertices[i*3 + 1];
//						MQO_Vertex v3 = face.vertices[i*3 + 2];
//
//						lineCnt++;
//						if(lineCnt > maxLine) return;
//						tessellator.addVertex(v1.x, v1.y, v1.z);
//						tessellator.addVertex(v2.x, v2.y, v2.z);
//
//						lineCnt++;
//						if(lineCnt > maxLine) return;
//						tessellator.addVertex(v2.x, v2.y, v2.z);
//						tessellator.addVertex(v3.x, v3.y, v3.z);
//
//						lineCnt++;
//						if(lineCnt > maxLine) return;
//						tessellator.addVertex(v3.x, v3.y, v3.z);
//						tessellator.addVertex(v1.x, v1.y, v1.z);
//					}
//				}
//			}
//		}
//	}

//	public void renderAll(int startFace, int maxFace)
//	{
//		if(startFace < 0) startFace = 0;
//
//		Tessellator tessellator = Tessellator.instance;
//
//		tessellator.startDrawing(GL11.GL_TRIANGLES);
//
//		renderAll(tessellator, startFace, maxFace);
//
//		tessellator.draw();
//	}
//
//	public void renderAll(Tessellator tessellator, int startFace, int maxLine)
//	{
//		int faceCnt = 0;
//		for (MQO_GroupObject groupObject : groupObjects)
//		{
//			if (groupObject.faces.size() > 0)
//			{
//				for (MQO_Face face : groupObject.faces)
//				{
//					faceCnt++;
//					if(faceCnt < startFace) continue;
//					if(faceCnt > maxLine) return;
//					face.addFaceForRender(tessellator);
//				}
//			}
//		}
//	}




	private void loadObjModel(InputStream inputStream) throws ModelFormatException
	{
		BufferedReader reader = null;

		String currentLine = null;
		int lineCount = 0;

		try
		{
			reader = new BufferedReader(new InputStreamReader(inputStream));

			while ((currentLine = reader.readLine()) != null)
			{
				lineCount++;
				currentLine = currentLine.replaceAll("\\s+", " ").trim();

				// オブジェクトを探す
				if(isValidGroupObjectLine(currentLine))
				{
					MQO_GroupObject group = parseGroupObject(currentLine, lineCount);
					if(group == null)
					{
						continue;
					}
					group.faces = new ArrayList[materials == null?1:materials.length];
					Arrays.fill(group.faces, new ArrayList<MQO_Face>());

					for(int id = 0;id < group.faces.length;id++){
						group.faces[id] = new ArrayList<MQO_Face>();
					}

					group.glDrawingMode = GL11.GL_TRIANGLES;

					this.vertices.clear();
					int vertexNum = 0;

					boolean mirror = false;

					double  facet   = Math.cos(45 * 3.1415926535 / 180.0);
					boolean shading = false;

					// シェーディングの設定と頂点数読み込み
					while ((currentLine = reader.readLine()) != null)
					{
						lineCount++;
						currentLine = currentLine.replaceAll("\\s+", " ").trim();

						if(currentLine.equals("mirror 1"))
						{
							mirror = true;
						}
						if(currentLine.equals("shading 1"))
						{
							shading = true;
						}

						String s[] = currentLine.split(" ");
						if(s.length==2 && s[0].equals("facet"))
						{
							facet   = Math.cos(Double.parseDouble(s[1]) * 3.1415926535 / 180.0);
						}

						if(isValidVertexLine(currentLine))
						{
							vertexNum = Integer.valueOf(currentLine.split(" ")[1]);
							break;
						}
					}

					// 頂点読み込み
					if(vertexNum > 0)
					{
						while ((currentLine = reader.readLine()) != null)
						{
							lineCount++;
							currentLine = currentLine.replaceAll("\\s+", " ").trim();

							String s[] = currentLine.split(" ");
							if(s.length == 3)
							{
								MQO_Vertex v = new MQO_Vertex(
										Float.valueOf(s[0]) / 100,
										Float.valueOf(s[1]) / 100,
										Float.valueOf(s[2]) / 100);

								if(v.x < this.minX) this.minX = v.x;
								if(v.y < this.minY) this.minY = v.y;
								if(v.z < this.minZ) this.minZ = v.z;
								if(v.x > this.maxX) this.maxX = v.x;
								if(v.y > this.maxY) this.maxY = v.y;
								if(v.z > this.maxZ) this.maxZ = v.z;

								this.vertices.add(v);

								vertexNum--;

								if(vertexNum <= 0)
								{
									break;
								}
							}
							else if(s.length > 0)
							{
								throw new ModelFormatException("format error : "+this.fileName+" : line="+lineCount);
							}
						}

						int faceNum = 0;
						// 面数読み込み
						while ((currentLine = reader.readLine()) != null)
						{
							lineCount++;
							currentLine = currentLine.replaceAll("\\s+", " ").trim();

							if(isValidFaceLine(currentLine))
							{
								faceNum = Integer.valueOf(currentLine.split(" ")[1]);
								break;
							}
						}

						if(faceNum > 0)
						{
							while ((currentLine = reader.readLine()) != null)
							{
								lineCount++;
								currentLine = currentLine.replaceAll("\\s+", " ").trim();

								String s[] = currentLine.split(" ");
								if(s.length > 2)
								{
									if(Integer.valueOf(s[0]) >= 3)
									{
										MQO_Face faces[] = parseFace(currentLine, lineCount, mirror);
										for(MQO_Face face : faces)
										{
											group.faces[face.faceMat].add(face);
										}
									}
									faceNum--;
									if(faceNum <= 0)
									{
										break;
									}
								}
								else if(s.length > 2 && Integer.valueOf(s[0])!=3)
								{
									throw new ModelFormatException("found face is not triangle : "+this.fileName+" : line="+lineCount);
								}
							}

							calcVerticesNormal(group, shading, facet);
						}
					}
					this.vertexNum += this.vertices.size();
					for(int id = 0;id < group.faces.length;id++){
						this.faceNum   += group.faces[id].size();
					}
					this.vertices.clear();

					groupObjects.add(group);
				}else if(isValidMaterialLine(currentLine)){
					materials = parseMaterial(currentLine,reader);
				}
			}
		}
		catch (IOException e)
		{
			throw new ModelFormatException("IO Exception reading model format : "+this.fileName, e);
		}
		finally
		{
			if(this.minX < this.min)	this.min = this.minX;
			if(this.minY < this.min)	this.min = this.minY;
			if(this.minZ < this.min)	this.min = this.minZ;
			if(this.maxX > this.max)	this.max = this.maxX;
			if(this.maxY > this.max)	this.max = this.maxY;
			if(this.maxZ > this.max)	this.max = this.maxZ;
			this.sizeX = this.maxX - this.minX;
			this.sizeY = this.maxY - this.minY;
			this.sizeZ = this.maxZ - this.minZ;
			this.size  = this.max  - this.min;

			this.vertices = null;
			try
			{
				reader.close();
			}
			catch (IOException e)
			{
				// hush
			}

			try
			{
				inputStream.close();
			}
			catch (IOException e)
			{
				// hush
			}
		}
	}


	private void calcVerticesNormal(MQO_GroupObject group, boolean shading, double facet)
	{
		for(int id = 0;id < group.faces.length;id++){
			for(MQO_Face f : (ArrayList<MQO_Face>)group.faces[id])
			{
				f.vertexNormals = new MQO_Vertex[f.verticesID.length];
				for(int i=0; i<f.verticesID.length; i++)
				{
					MQO_Vertex vn = getVerticesNormalFromFace(f.faceNormal, f.verticesID[i], group, (float)facet);
					vn.normalize();



					if(shading)
					{
						if(f.faceNormal.x * vn.x + f.faceNormal.y * vn.y + f.faceNormal.z * vn.z >= facet)
						{
							f.vertexNormals[i] = vn;
						}
						else
						{
							f.vertexNormals[i] = f.faceNormal;
						}
					}
					else
					{
						f.vertexNormals[i] = f.faceNormal;
					}
				}
			}
		}
	}

	private MQO_Vertex getVerticesNormalFromFace(MQO_Vertex faceNormal, int verticesID, MQO_GroupObject group, float facet)
	{
		MQO_Vertex v = new MQO_Vertex(0,0,0);

		for(int i = 0;i < group.faces.length;i++) {
			for(MQO_Face f : (ArrayList<MQO_Face>)group.faces[i]) {
				for (int id : f.verticesID) {
					if (id == verticesID) {
						if (f.faceNormal.x * faceNormal.x + f.faceNormal.y * faceNormal.y + f.faceNormal.z * faceNormal.z >= facet) {
							v.add(f.faceNormal);
						}
						break;
					}
				}
			}
		}

		v.normalize();

		return v;
	}

	private MQO_Face[] parseFace(String line, int lineCount, boolean mirror)
	{
		String s[] = line.split("[ VU)(M]+");
		// Format
		// 3 V(0 2 1) M(0) UV(0.30158 0.75859 0.32219 0.75859 0.28098 0.75859)
		// ↓
		// 3	0 2 1	0	0.30158 0.75859	0.32219 0.75859	0.28098 0.75859

		int vnum = Integer.valueOf(s[0]);
		if(vnum!=3 && vnum!=4)
		{
			return new MQO_Face[]{};
		}

		if(vnum == 3)
		{
			MQO_Face face = new MQO_Face();
			face.verticesID = new int[]
				{
					Integer.valueOf(s[3]),
					Integer.valueOf(s[2]),
					Integer.valueOf(s[1]),
				};

			face.vertices = new MQO_Vertex[]{
					this.vertices.get(face.verticesID[0]),
					this.vertices.get(face.verticesID[1]),
					this.vertices.get(face.verticesID[2]),
			};
			if(s.length>=11)
			{
				face.faceMat = parseInt(s[4]);
				face.textureCoordinates = new MQO_TextureCoordinate[]{
					new MQO_TextureCoordinate(Float.valueOf(s[9]), Float.valueOf(s[10])),
					new MQO_TextureCoordinate(Float.valueOf(s[7]), Float.valueOf(s[8])),
					new MQO_TextureCoordinate(Float.valueOf(s[5]), Float.valueOf(s[6])),
				};
			}
			else
			{
				face.textureCoordinates = new MQO_TextureCoordinate[]{
					new MQO_TextureCoordinate(0,0),
					new MQO_TextureCoordinate(0,0),
					new MQO_TextureCoordinate(0,0),
				};
			}
			face.faceNormal = face.calculateFaceNormal();

			return new MQO_Face[]{ face };
		}
		else
		{
			MQO_Face face1 = new MQO_Face();
			face1.verticesID = new int[]
					{
						Integer.valueOf(s[3]),
						Integer.valueOf(s[2]),
						Integer.valueOf(s[1]),
					};

			face1.vertices = new MQO_Vertex[]{
					this.vertices.get(face1.verticesID[0]),
					this.vertices.get(face1.verticesID[1]),
					this.vertices.get(face1.verticesID[2]),
			};

			if(s.length>=12)
			{
				if(s.length>=14)face1.faceMat = parseInt(s[5]);
				face1.textureCoordinates = new MQO_TextureCoordinate[]{
					new MQO_TextureCoordinate(Float.valueOf(s[10]), Float.valueOf(s[11])),
					new MQO_TextureCoordinate(Float.valueOf(s[ 8]), Float.valueOf(s[ 9])),
					new MQO_TextureCoordinate(Float.valueOf(s[ 6]), Float.valueOf(s[ 7])),
				};
			}
			else
			{
				face1.textureCoordinates = new MQO_TextureCoordinate[]{
					new MQO_TextureCoordinate(0,0),
					new MQO_TextureCoordinate(0,0),
					new MQO_TextureCoordinate(0,0),
				};
			}

			face1.faceNormal = face1.calculateFaceNormal();


			MQO_Face face2 = new MQO_Face();
			face2.verticesID = new int[]
					{
						Integer.valueOf(s[4]),
						Integer.valueOf(s[3]),
						Integer.valueOf(s[1]),
					};

			face2.vertices = new MQO_Vertex[]{
					this.vertices.get(face2.verticesID[0]),
					this.vertices.get(face2.verticesID[1]),
					this.vertices.get(face2.verticesID[2]),
			};

			if(s.length>=14)
			{
				face2.faceMat = parseInt(s[5]);
				face2.textureCoordinates = new MQO_TextureCoordinate[]{
					new MQO_TextureCoordinate(Float.valueOf(s[12]), Float.valueOf(s[13])),
					new MQO_TextureCoordinate(Float.valueOf(s[10]), Float.valueOf(s[11])),
					new MQO_TextureCoordinate(Float.valueOf(s[ 6]), Float.valueOf(s[ 7])),
				};
			}
			else
			{
				face2.textureCoordinates = new MQO_TextureCoordinate[]{
					new MQO_TextureCoordinate(0,0),
					new MQO_TextureCoordinate(0,0),
					new MQO_TextureCoordinate(0,0),
				};
			}
			face2.faceNormal = face2.calculateFaceNormal();

			return new MQO_Face[]{ face1, face2 };
		}
	}

	// オブジェクトの開始行かどうか判別
	private static boolean isValidGroupObjectLine(String line)
	{
		// Object "obj4" {
		String[] s = line.split(" ");

		if(s.length < 2 || !s[0].equals("Object"))
		{
			return false;
		}

		if(s[1].length()<4 || s[1].charAt(0)!='"')
		{
			return false;
		}

		return true;
	}
	private static boolean isValidMaterialLine(String line)
	{
		// Object "obj4" {
		String[] s = line.split(" ");

		if(s.length < 2 || !s[0].equals("Material"))
		{
			return false;
		}

		return true;
	}
	private MQO_Material[] parseMaterial(String line, BufferedReader reader){
		try {
			String s[] = line.split(" ");
			int matNum = parseInt(s[1]);
			MQO_Material[] materials = new MQO_Material[matNum];
			for(int id = 0;id < matNum;id++){
				String matLine = null;
				MQO_Material currentMat = new MQO_Material();
				try {
					matLine = reader.readLine();
					matLine = matLine.replaceAll("\\s+", " ").trim();
					String[] matInfo = matLine.split(" ");
					for (String ainfo : matInfo) {
						ainfo = ainfo.replace('(',',');
						ainfo = ainfo.replace(')',' ');
						String[] matValue = ainfo.split(",");
						if (matValue.length > 1) {
							String infoType = matValue[0];
							String infoValue = matValue[1];
							switch (infoType) {
								case "dif":
									currentMat.dif = parseFloat(infoValue);
									break;
								case "amb":
									currentMat.amb = parseFloat(infoValue);
									break;
								case "emi":
									currentMat.emi = parseFloat(infoValue);
									break;
								case "spc":
									currentMat.spc = parseFloat(infoValue);
									break;
								case "power":
									currentMat.power = parseFloat(infoValue)*(128f/100f);
									break;
							}
						}
					}
					currentMat.setUp();
					materials[id] = currentMat;
				}catch (IOException e) {
					e.printStackTrace();
					System.out.println("WTF at" + matLine);
				}
			}
			return materials;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	private MQO_GroupObject parseGroupObject(String line, int lineCount) throws ModelFormatException
	{
		MQO_GroupObject group = null;

		if (isValidGroupObjectLine(line))
		{
			String s[] = line.split(" ");
			String trimmedLine = s[1].substring(1, s[1].length()-1);

			if (trimmedLine.length() > 0)
			{
				group = new MQO_GroupObject(trimmedLine,this);
			}
		}
		else
		{
			throw new ModelFormatException("Error parsing entry ('" + line + "'" + ", line " + lineCount + ") in file '" + fileName + "' - Incorrect format");
		}

		return group;
	}

	// 頂点の開始行かどうか判別
	private static boolean isValidVertexLine(String line)
	{
		String s[] = line.split(" ");

		if(!s[0].equals("vertex")) return false;

		return true;
	}

	/***
	 * Verifies that the given line from the model file is a valid face of any of the possible face formats
	 * @param line the line being validated
	 * @return true if the line is a valid face that matches any of the valid face formats, false otherwise
	 */
	private static boolean isValidFaceLine(String line)
	{
		String s[] = line.split(" ");

		if(!s[0].equals("face")) return false;

		return true;
	}
}

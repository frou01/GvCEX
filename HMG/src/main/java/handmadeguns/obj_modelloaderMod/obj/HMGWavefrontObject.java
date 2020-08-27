package handmadeguns.obj_modelloaderMod.obj;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import handmadeguns.client.render.IModelCustom_HMG;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.resources.IResource;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelFormatException;
import net.minecraftforge.client.model.obj.WavefrontObject;
import org.lwjgl.opengl.GL11;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static handmadeguns.HandmadeGunsCore.HMG_proxy;

/**
 *  Wavefront Object importer
 *  Based heavily off of the specifications found at http://en.wikipedia.org/wiki/Wavefront_.obj_file
 */
public class HMGWavefrontObject extends WavefrontObject implements IModelCustom_HMG
{
    private static Pattern vertexPattern = Pattern.compile("(v( (\\-){0,1}\\d+(\\.\\d+)?){3,4} *\\n)|(v( (\\-){0,1}\\d+(\\.\\d+)?){3,4} *$)");
    private static Pattern vertexNormalPattern = Pattern.compile("(vn( (\\-){0,1}\\d+(\\.\\d+)?){3,4} *\\n)|(vn( (\\-){0,1}\\d+(\\.\\d+)?){3,4} *$)");
    private static Pattern textureCoordinatePattern = Pattern.compile("(vt( (\\-){0,1}\\d+\\.\\d+){2,3} *\\n)|(vt( (\\-){0,1}\\d+(\\.\\d+)?){2,3} *$)");
    private static Pattern face_V_VT_VN_Pattern = Pattern.compile("(f( \\d+/\\d+/\\d+){3,4} *\\n)|(f( \\d+/\\d+/\\d+){3,4} *$)");
    private static Pattern face_V_VT_Pattern = Pattern.compile("(f( \\d+/\\d+){3,4} *\\n)|(f( \\d+/\\d+){3,4} *$)");
    private static Pattern face_V_VN_Pattern = Pattern.compile("(f( \\d+//\\d+){3,4} *\\n)|(f( \\d+//\\d+){3,4} *$)");
    private static Pattern face_V_Pattern = Pattern.compile("(f( \\d+){3,4} *\\n)|(f( \\d+){3,4} *$)");
    private static Pattern groupObjectPattern = Pattern.compile("([go]( [\\w\\d\\.]+) *\\n)|([go]( [\\w\\d\\.]+) *$)");

    private Matcher vertexMatcher, vertexNormalMatcher, textureCoordinateMatcher;
    private Matcher face_V_VT_VN_Matcher, face_V_VT_Matcher, face_V_VN_Matcher, face_V_Matcher;
    private Matcher groupObjectMatcher;

    public ArrayList<HMGVertex> vertices = new ArrayList<HMGVertex>();
    public ArrayList<HMGVertex> HMGVertexNormals = new ArrayList<HMGVertex>();
    public ArrayList<HMGTextureCoordinate> HMGTextureCoordinates = new ArrayList<HMGTextureCoordinate>();
    public ArrayList<HMGGroupObject> HMGGroupObjects = new ArrayList<HMGGroupObject>();
    private HMGGroupObject currentHMGGroupObject;
    private String fileName;

    public boolean endLoad = false;

    ExecutorService es;
    public HMGWavefrontObject(ResourceLocation resource) throws ModelFormatException
    {
        super(resource);
        HMG_proxy.AddModel(this);
        this.fileName = resource.toString();

        es = Executors.newCachedThreadPool();
        es.execute(() -> {
            try
            {
                IResource res = Minecraft.getMinecraft().getResourceManager().getResource(resource);
                loadObjModel(res.getInputStream());
            }
            catch (Throwable e)
            {
                es.shutdown();
                e.printStackTrace();
            }
            endLoad = true;
            es.shutdown();
        });
    }

    @Override
    public ExecutorService getLoadThread() {
        return es;
    }

    public HMGWavefrontObject(String filename, InputStream inputStream) throws ModelFormatException
    {
        super(filename,inputStream);
        this.fileName = filename;
        loadObjModel(inputStream);
    }

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

                if (currentLine.startsWith("#") || currentLine.length() == 0)
                {
                    continue;
                }
                else if (currentLine.startsWith("v "))
                {
                    HMGVertex HMGVertex = parseVertex(currentLine, lineCount);
                    if (HMGVertex != null)
                    {
                        vertices.add(HMGVertex);
                    }
                }
                else if (currentLine.startsWith("vn "))
                {
                    HMGVertex HMGVertex = parseVertexNormal(currentLine, lineCount);
                    if (HMGVertex != null)
                    {
                        HMGVertexNormals.add(HMGVertex);
                    }
                }
                else if (currentLine.startsWith("vt "))
                {
                    HMGTextureCoordinate HMGTextureCoordinate = parseTextureCoordinate(currentLine, lineCount);
                    if (HMGTextureCoordinate != null)
                    {
                        HMGTextureCoordinates.add(HMGTextureCoordinate);
                    }
                }
                else if (currentLine.startsWith("f "))
                {

                    if (currentHMGGroupObject == null)
                    {
                        currentHMGGroupObject = new HMGGroupObject("Default");
                    }

                    HMGFace HMGFace = parseFace(currentLine, lineCount);

                    if (HMGFace != null)
                    {
                        currentHMGGroupObject.HMGFaces.add(HMGFace);
                    }
                }
                else if (currentLine.startsWith("g ") | currentLine.startsWith("o "))
                {
                    HMGGroupObject group = parseGroupObject(currentLine, lineCount);

                    if (group != null)
                    {
                        if (currentHMGGroupObject != null)
                        {
                            HMGGroupObjects.add(currentHMGGroupObject);
                        }
                    }

                    currentHMGGroupObject = group;
                }
            }

            HMGGroupObjects.add(currentHMGGroupObject);
        }
        catch (IOException e)
        {
            throw new ModelFormatException("IO Exception reading model format", e);
        }
        finally
        {
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

    @Override
    @SideOnly(Side.CLIENT)
    public void renderAll()
    {
        tessellateAll();
    }

    @SideOnly(Side.CLIENT)
    public void tessellateAll()
    {
        for (HMGGroupObject HMGGroupObject : HMGGroupObjects)
        {
            HMGGroupObject.render();
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void renderOnly(String... groupNames)
    {
        for (HMGGroupObject HMGGroupObject : HMGGroupObjects)
        {
            for (String groupName : groupNames)
            {
                if (groupName.equalsIgnoreCase(HMGGroupObject.name))
                {
                    HMGGroupObject.render();
                }
            }
        }
    }

    @SideOnly(Side.CLIENT)
    public void tessellateOnly(Tessellator tessellator, String... groupNames) {
        for (HMGGroupObject HMGGroupObject : HMGGroupObjects)
        {
            for (String groupName : groupNames)
            {
                if (groupName.equalsIgnoreCase(HMGGroupObject.name))
                {
                    HMGGroupObject.render();
                }
            }
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void renderPart(String partName)
    {
        current = null;
        for (HMGGroupObject HMGGroupObject : HMGGroupObjects)
        {
            if (partName.equalsIgnoreCase(HMGGroupObject.name))
            {
                HMGGroupObject.render();
                current = HMGGroupObject;
            }
        }
    }

    @SideOnly(Side.CLIENT)
    public void tessellatePart(Tessellator tessellator, String partName) {
        for (HMGGroupObject HMGGroupObject : HMGGroupObjects)
        {
            if (partName.equalsIgnoreCase(HMGGroupObject.name))
            {
                HMGGroupObject.render();
            }
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void renderAllExcept(String... excludedGroupNames)
    {
        for (HMGGroupObject HMGGroupObject : HMGGroupObjects)
        {
            boolean skipPart=false;
            for (String excludedGroupName : excludedGroupNames)
            {
                if (excludedGroupName.equalsIgnoreCase(HMGGroupObject.name))
                {
                    skipPart=true;
                }
            }
            if(!skipPart)
            {
                HMGGroupObject.render();
            }
        }
    }

    @SideOnly(Side.CLIENT)
    public void tessellateAllExcept(Tessellator tessellator, String... excludedGroupNames)
    {
        boolean exclude;
        for (HMGGroupObject HMGGroupObject : HMGGroupObjects)
        {
            exclude=false;
            for (String excludedGroupName : excludedGroupNames)
            {
                if (excludedGroupName.equalsIgnoreCase(HMGGroupObject.name))
                {
                    exclude=true;
                }
            }
            if(!exclude)
            {
                HMGGroupObject.render();
            }
        }
    }

    private HMGVertex parseVertex(String line, int lineCount) throws ModelFormatException
    {
        HMGVertex HMGVertex = null;

        if (isValidVertexLine(line))
        {
            line = line.substring(line.indexOf(" ") + 1);
            String[] tokens = line.split(" ");

            try
            {
                if (tokens.length == 2)
                {
                    return new HMGVertex(Float.parseFloat(tokens[0]), Float.parseFloat(tokens[1]));
                }
                else if (tokens.length == 3)
                {
                    return new HMGVertex(Float.parseFloat(tokens[0]), Float.parseFloat(tokens[1]), Float.parseFloat(tokens[2]));
                }
            }
            catch (NumberFormatException e)
            {
                throw new ModelFormatException(String.format("Number formatting error at line %d",lineCount), e);
            }
        }
        else
        {
            throw new ModelFormatException("Error parsing entry ('" + line + "'" + ", line " + lineCount + ") in file '" + fileName + "' - Incorrect format");
        }

        return HMGVertex;
    }

    private HMGVertex parseVertexNormal(String line, int lineCount) throws ModelFormatException
    {
        HMGVertex HMGVertexNormal = null;

        if (isValidVertexNormalLine(line))
        {
            line = line.substring(line.indexOf(" ") + 1);
            String[] tokens = line.split(" ");

            try
            {
                if (tokens.length == 3)
                    return new HMGVertex(Float.parseFloat(tokens[0]), Float.parseFloat(tokens[1]), Float.parseFloat(tokens[2]));
            }
            catch (NumberFormatException e)
            {
                throw new ModelFormatException(String.format("Number formatting error at line %d",lineCount), e);
            }
        }
        else
        {
            throw new ModelFormatException("Error parsing entry ('" + line + "'" + ", line " + lineCount + ") in file '" + fileName + "' - Incorrect format");
        }

        return HMGVertexNormal;
    }

    private HMGTextureCoordinate parseTextureCoordinate(String line, int lineCount) throws ModelFormatException
    {
        HMGTextureCoordinate HMGTextureCoordinate = null;

        if (isValidTextureCoordinateLine(line))
        {
            line = line.substring(line.indexOf(" ") + 1);
            String[] tokens = line.split(" ");

            try
            {
                if (tokens.length == 2)
                    return new HMGTextureCoordinate(Float.parseFloat(tokens[0]), 1 - Float.parseFloat(tokens[1]));
                else if (tokens.length == 3)
                    return new HMGTextureCoordinate(Float.parseFloat(tokens[0]), 1 - Float.parseFloat(tokens[1]), Float.parseFloat(tokens[2]));
            }
            catch (NumberFormatException e)
            {
                throw new ModelFormatException(String.format("Number formatting error at line %d",lineCount), e);
            }
        }
        else
        {
            throw new ModelFormatException("Error parsing entry ('" + line + "'" + ", line " + lineCount + ") in file '" + fileName + "' - Incorrect format");
        }

        return HMGTextureCoordinate;
    }

    private HMGFace parseFace(String line, int lineCount) throws ModelFormatException
    {
        HMGFace HMGFace = null;

        if (isValidFaceLine(line))
        {
            HMGFace = new HMGFace();

            String trimmedLine = line.substring(line.indexOf(" ") + 1);
            String[] tokens = trimmedLine.split(" ");
            String[] subTokens = null;

            if (tokens.length == 3)
            {
                if (currentHMGGroupObject.glDrawingMode == -1)
                {
                    currentHMGGroupObject.glDrawingMode = GL11.GL_TRIANGLES;
                }
                else if (currentHMGGroupObject.glDrawingMode != GL11.GL_TRIANGLES)
                {
                    throw new ModelFormatException("Error parsing entry ('" + line + "'" + ", line " + lineCount + ") in file '" + fileName + "' - Invalid number of points for face (expected 4, found " + tokens.length + ")");
                }
            }
            else if (tokens.length == 4)
            {
                if (currentHMGGroupObject.glDrawingMode == -1)
                {
                    currentHMGGroupObject.glDrawingMode = GL11.GL_QUADS;
                }
                else if (currentHMGGroupObject.glDrawingMode != GL11.GL_QUADS)
                {
                    throw new ModelFormatException("Error parsing entry ('" + line + "'" + ", line " + lineCount + ") in file '" + fileName + "' - Invalid number of points for face (expected 3, found " + tokens.length + ")");
                }
            }

            // f v1/vt1/vn1 v2/vt2/vn2 v3/vt3/vn3 ...
            if (isValidFace_V_VT_VN_Line(line))
            {
                HMGFace.vertices = new HMGVertex[tokens.length];
                HMGFace.HMGTextureCoordinates = new HMGTextureCoordinate[tokens.length];
                HMGFace.HMGVertexNormals = new HMGVertex[tokens.length];

                for (int i = 0; i < tokens.length; ++i)
                {
                    subTokens = tokens[i].split("/");

                    HMGFace.vertices[i] = vertices.get(Integer.parseInt(subTokens[0]) - 1);
                    HMGFace.HMGTextureCoordinates[i] = HMGTextureCoordinates.get(Integer.parseInt(subTokens[1]) - 1);
                    HMGFace.HMGVertexNormals[i] = HMGVertexNormals.get(Integer.parseInt(subTokens[2]) - 1);
                }

                HMGFace.faceNormal = HMGFace.calculateFaceNormal();
            }
            // f v1/vt1 v2/vt2 v3/vt3 ...
            else if (isValidFace_V_VT_Line(line))
            {
                HMGFace.vertices = new HMGVertex[tokens.length];
                HMGFace.HMGTextureCoordinates = new HMGTextureCoordinate[tokens.length];

                for (int i = 0; i < tokens.length; ++i)
                {
                    subTokens = tokens[i].split("/");

                    HMGFace.vertices[i] = vertices.get(Integer.parseInt(subTokens[0]) - 1);
                    HMGFace.HMGTextureCoordinates[i] = HMGTextureCoordinates.get(Integer.parseInt(subTokens[1]) - 1);
                }

                HMGFace.faceNormal = HMGFace.calculateFaceNormal();
            }
            // f v1//vn1 v2//vn2 v3//vn3 ...
            else if (isValidFace_V_VN_Line(line))
            {
                HMGFace.vertices = new HMGVertex[tokens.length];
                HMGFace.HMGVertexNormals = new HMGVertex[tokens.length];

                for (int i = 0; i < tokens.length; ++i)
                {
                    subTokens = tokens[i].split("//");

                    HMGFace.vertices[i] = vertices.get(Integer.parseInt(subTokens[0]) - 1);
                    HMGFace.HMGVertexNormals[i] = HMGVertexNormals.get(Integer.parseInt(subTokens[1]) - 1);
                }

                HMGFace.faceNormal = HMGFace.calculateFaceNormal();
            }
            // f v1 v2 v3 ...
            else if (isValidFace_V_Line(line))
            {
                HMGFace.vertices = new HMGVertex[tokens.length];

                for (int i = 0; i < tokens.length; ++i)
                {
                    HMGFace.vertices[i] = vertices.get(Integer.parseInt(tokens[i]) - 1);
                }

                HMGFace.faceNormal = HMGFace.calculateFaceNormal();
            }
            else
            {
                throw new ModelFormatException("Error parsing entry ('" + line + "'" + ", line " + lineCount + ") in file '" + fileName + "' - Incorrect format");
            }
        }
        else
        {
            throw new ModelFormatException("Error parsing entry ('" + line + "'" + ", line " + lineCount + ") in file '" + fileName + "' - Incorrect format");
        }

        return HMGFace;
    }

    private HMGGroupObject parseGroupObject(String line, int lineCount) throws ModelFormatException
    {
        HMGGroupObject group = null;

        if (isValidGroupObjectLine(line))
        {
            String trimmedLine = line.substring(line.indexOf(" ") + 1);

            if (trimmedLine.length() > 0)
            {
                group = new HMGGroupObject(trimmedLine);
            }
        }
        else
        {
            throw new ModelFormatException("Error parsing entry ('" + line + "'" + ", line " + lineCount + ") in file '" + fileName + "' - Incorrect format");
        }

        return group;
    }

    /***
     * Verifies that the given line from the model file is a valid vertex
     * @param line the line being validated
     * @return true if the line is a valid vertex, false otherwise
     */
    private boolean isValidVertexLine(String line)
    {
        if (vertexMatcher != null)
        {
            vertexMatcher.reset();
        }

        vertexMatcher = vertexPattern.matcher(line);
        return vertexMatcher.matches();
    }

    /***
     * Verifies that the given line from the model file is a valid vertex normal
     * @param line the line being validated
     * @return true if the line is a valid vertex normal, false otherwise
     */
    private boolean isValidVertexNormalLine(String line)
    {
        if (vertexNormalMatcher != null)
        {
            vertexNormalMatcher.reset();
        }

        vertexNormalMatcher = vertexNormalPattern.matcher(line);
        return vertexNormalMatcher.matches();
    }

    /***
     * Verifies that the given line from the model file is a valid texture coordinate
     * @param line the line being validated
     * @return true if the line is a valid texture coordinate, false otherwise
     */
    private boolean isValidTextureCoordinateLine(String line)
    {
        if (textureCoordinateMatcher != null)
        {
            textureCoordinateMatcher.reset();
        }

        textureCoordinateMatcher = textureCoordinatePattern.matcher(line);
        return textureCoordinateMatcher.matches();
    }

    /***
     * Verifies that the given line from the model file is a valid face that is described by vertices, texture coordinates, and vertex normals
     * @param line the line being validated
     * @return true if the line is a valid face that matches the format "f v1/vt1/vn1 ..." (with a minimum of 3 points in the face, and a maximum of 4), false otherwise
     */
    private boolean isValidFace_V_VT_VN_Line(String line)
    {
        if (face_V_VT_VN_Matcher != null)
        {
            face_V_VT_VN_Matcher.reset();
        }

        face_V_VT_VN_Matcher = face_V_VT_VN_Pattern.matcher(line);
        return face_V_VT_VN_Matcher.matches();
    }

    /***
     * Verifies that the given line from the model file is a valid face that is described by vertices and texture coordinates
     * @param line the line being validated
     * @return true if the line is a valid face that matches the format "f v1/vt1 ..." (with a minimum of 3 points in the face, and a maximum of 4), false otherwise
     */
    private boolean isValidFace_V_VT_Line(String line)
    {
        if (face_V_VT_Matcher != null)
        {
            face_V_VT_Matcher.reset();
        }

        face_V_VT_Matcher = face_V_VT_Pattern.matcher(line);
        return face_V_VT_Matcher.matches();
    }

    /***
     * Verifies that the given line from the model file is a valid face that is described by vertices and vertex normals
     * @param line the line being validated
     * @return true if the line is a valid face that matches the format "f v1//vn1 ..." (with a minimum of 3 points in the face, and a maximum of 4), false otherwise
     */
    private boolean isValidFace_V_VN_Line(String line)
    {
        if (face_V_VN_Matcher != null)
        {
            face_V_VN_Matcher.reset();
        }

        face_V_VN_Matcher = face_V_VN_Pattern.matcher(line);
        return face_V_VN_Matcher.matches();
    }

    /***
     * Verifies that the given line from the model file is a valid face that is described by only vertices
     * @param line the line being validated
     * @return true if the line is a valid face that matches the format "f v1 ..." (with a minimum of 3 points in the face, and a maximum of 4), false otherwise
     */
    private boolean isValidFace_V_Line(String line)
    {
        if (face_V_Matcher != null)
        {
            face_V_Matcher.reset();
        }

        face_V_Matcher = face_V_Pattern.matcher(line);
        return face_V_Matcher.matches();
    }

    /***
     * Verifies that the given line from the model file is a valid face of any of the possible face formats
     * @param line the line being validated
     * @return true if the line is a valid face that matches any of the valid face formats, false otherwise
     */
    private boolean isValidFaceLine(String line)
    {
        return isValidFace_V_VT_VN_Line(line) || isValidFace_V_VT_Line(line) || isValidFace_V_VN_Line(line) || isValidFace_V_Line(line);
    }

    /***
     * Verifies that the given line from the model file is a valid group (or object)
     * @param line the line being validated
     * @return true if the line is a valid group (or object), false otherwise
     */
    private boolean isValidGroupObjectLine(String line)
    {
        if (groupObjectMatcher != null)
        {
            groupObjectMatcher.reset();
        }

        groupObjectMatcher = groupObjectPattern.matcher(line);
        return groupObjectMatcher.matches();
    }

    @Override
    public String getType()
    {
        return "obj";
    }
    HMGGroupObject current;
    @Override
    public HMGGroupObject renderPart_getInstance() {
        return current;
    }

    @Override
    public boolean isReady() {
        return endLoad;
    }

    public String toString(){
        return fileName;
    }
}
package handmadeguns.obj_modelloaderMod.obj;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.Tessellator;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;

public class HMGGroupObject
{
    public String name;
    public ArrayList<HMGFace> HMGFaces = new ArrayList<HMGFace>();
    public int glDrawingMode;
    private int displayList = -1;

    public HMGGroupObject()
    {
        this("");
    }

    public HMGGroupObject(String name)
    {
        this(name, -1);
    }

    public HMGGroupObject(String name, int glDrawingMode)
    {
        this.name = name;
        this.glDrawingMode = glDrawingMode;
    }

    public void initDisplay(){
        this.displayList = GLAllocation.generateDisplayLists(1);
        GL11.glNewList(this.displayList, GL11.GL_COMPILE);
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawing(glDrawingMode);
        render(tessellator);
        tessellator.draw();

        GL11.glEndList();
    }

    @SideOnly(Side.CLIENT)
    public void render()
    {
        if(displayList == -1)initDisplay();
        else if(displayList != 0) GL11.glCallList(this.displayList);
        else initDisplay();
    }

    @SideOnly(Side.CLIENT)
    private void render(Tessellator tessellator)
    {
        if (HMGFaces.size() > 0)
        {
            for (HMGFace HMGFace : HMGFaces)
            {
                HMGFace.addFaceForRender(tessellator);
            }
        }
    }
}
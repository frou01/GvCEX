package handmadeguns.emb_modelloader;

import java.util.ArrayList;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.Tessellator;

@SideOnly(Side.CLIENT)
public class MQO_GroupObject
{
	public String name;
	public ArrayList<MQO_Face> faces = new ArrayList<MQO_Face>();
	public int glDrawingMode;

	public MQO_GroupObject()
	{
		this("");
	}

	public MQO_GroupObject(String name)
	{
		this(name, -1);
	}

	public MQO_GroupObject(String name, int glDrawingMode)
	{
		this.name = name;
		this.glDrawingMode = glDrawingMode;
	}

	public void render()
	{
		if (faces.size() > 0)
		{
			Tessellator tessellator = Tessellator.instance;
			tessellator.startDrawing(glDrawingMode);
			render(tessellator);
			tessellator.draw();
		}
	}

	public void render(Tessellator tessellator)
	{
		if (faces.size() > 0)
		{
			for (MQO_Face face : faces)
			{
				face.addFaceForRender(tessellator);
			}
		}
	}
}

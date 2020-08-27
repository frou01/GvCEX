package handmadeguns.emb_modelloader;

import java.util.ArrayList;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import handmadeguns.obj_modelloaderMod.obj.HMGGroupObject;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.Vec3;
import org.lwjgl.opengl.GL11;

import static handmadeguns.emb_modelloader.MQO_Material.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.*;

@SideOnly(Side.CLIENT)
public class MQO_GroupObject extends HMGGroupObject
{
	public String name;
	public ArrayList[] faces;
	public int glDrawingMode;

	public MQO_Material currentMaterial;
	public MQO_MetasequoiaObject mqo_metasequoiaObject;
	private int displayList = -1;

	public MQO_GroupObject()
	{
		this("",null);
	}

	public MQO_GroupObject(String name,MQO_MetasequoiaObject mqo_metasequoiaObject)
	{
		this(name, -1);
		this.mqo_metasequoiaObject = mqo_metasequoiaObject;
	}

	public MQO_GroupObject(String name, int glDrawingMode)
	{
		this.name = name;
		this.glDrawingMode = glDrawingMode;
	}


	public void initDisplay(){
		this.displayList = GLAllocation.generateDisplayLists(1);
		GL11.glNewList(this.displayList, GL11.GL_COMPILE);
		render_init();
		GL11.glEndList();
	}

	public void render()
	{
		if(displayList == -1)initDisplay();
		else if(displayList != 0) GL11.glCallList(this.displayList);
		else initDisplay();
	}

	public void render_init()
	{
		for(int i = 0;i < faces.length;i++) {
			if (faces[i].size() > 0) {
				currentMaterial = null;
				if(mqo_metasequoiaObject.materials != null)currentMaterial = mqo_metasequoiaObject.materials[i];
				Tessellator tessellator = Tessellator.instance;
				tessellator.startDrawing(glDrawingMode);
				for (MQO_Face face : (ArrayList<MQO_Face>)faces[i]) {
					face.addFaceForRender(tessellator);
				}

				if (currentMaterial != null) {
					GL11.glLight(GL11.GL_LIGHT1, GL11.GL_SPECULAR, setColorBuffer(1, 1, 1, 1));
					GL11.glLight(GL11.GL_LIGHT0, GL11.GL_SPECULAR, setColorBuffer(0, 0, 0, 0));
					GL11.glLight(GL11.GL_LIGHT1, GL11.GL_DIFFUSE, setColorBuffer(0.4F, 0.4F, 0.4F, 1.0F));
					GL11.glLight(GL11.GL_LIGHT0, GL11.GL_DIFFUSE, setColorBuffer(0.6F, 0.6F, 0.6F, 1.0F));

					GL11.glLightModel(GL11.GL_LIGHT_MODEL_LOCAL_VIEWER, setColorBuffer(0.4f, 0.4f, 0.4f, 1.0F));
					GL11.glLightModel(GL11.GL_LIGHT_MODEL_AMBIENT, setColorBuffer(0.4f, 0.4f, 0.4f, 1.0F));
					glLightModeli(GL_LIGHT_MODEL_COLOR_CONTROL, GL_SEPARATE_SPECULAR_COLOR);

					GL11.glMaterial(GL_FRONT_AND_BACK, GL11.GL_DIFFUSE, currentMaterial.dif_Buf);
					GL11.glMaterial(GL_FRONT_AND_BACK, GL11.GL_AMBIENT, currentMaterial.amb_Buf);
					GL11.glMaterial(GL_FRONT_AND_BACK, GL11.GL_SPECULAR, currentMaterial.spc_Buf);
					GL11.glMaterial(GL_FRONT_AND_BACK, GL11.GL_EMISSION, currentMaterial.emi_Buf);
					GL11.glMaterialf(GL_FRONT_AND_BACK, GL11.GL_SHININESS, currentMaterial.power);
					GL11.glEnable(GL11.GL_COLOR_MATERIAL);
				}
				tessellator.draw();
				{
					GL11.glLight(GL11.GL_LIGHT1, GL11.GL_SPECULAR, setColorBuffer(0, 0, 0, 1));
					GL11.glLight(GL11.GL_LIGHT0, GL11.GL_SPECULAR, setColorBuffer(0, 0, 0, 1));

					GL11.glLightModel(GL11.GL_LIGHT_MODEL_LOCAL_VIEWER, setColorBuffer(0, 0, 0, 1.0F));
					GL11.glLightModel(GL11.GL_LIGHT_MODEL_AMBIENT, setColorBuffer(0.4f, 0.4f, 0.4f, 1.0F));
					glLightModeli(GL_LIGHT_MODEL_COLOR_CONTROL, GL_SINGLE_COLOR);

					GL11.glMaterial(GL_FRONT_AND_BACK, GL11.GL_DIFFUSE, setColorBuffer(0.2, 0.2, 0.2, 1f));
					GL11.glMaterial(GL_FRONT_AND_BACK, GL11.GL_AMBIENT, setColorBuffer(0.8, 0.8, 0.8, 1f));
					GL11.glMaterial(GL_FRONT_AND_BACK, GL11.GL_SPECULAR, setColorBuffer(0, 0, 0, 1f));
					GL11.glMaterial(GL_FRONT_AND_BACK, GL11.GL_EMISSION, setColorBuffer(0, 0, 0, 1f));
					GL11.glMaterialf(GL_FRONT_AND_BACK, GL11.GL_SHININESS, 0);
				}
			}
		}
	}
}

package handmadeguns.tcn_modelloaderMod;

import handmadeguns.obj_modelloaderMod.obj.HMGGroupObject;
import net.minecraft.client.model.ModelRenderer;
import org.lwjgl.opengl.GL11;

import java.util.List;

import static org.lwjgl.opengl.GL11.GL_CULL_FACE;

public class TechneGroupObject extends HMGGroupObject {
	public TechneGroupObject(List<ModelRenderer> part){
		this.part = part;
	}
	public List<ModelRenderer> part;

	public void render()
	{
		GL11.glPushMatrix();
		GL11.glRotatef(180,0,1,0);
		GL11.glRotatef(180,0,0,1);
		GL11.glDisable(GL_CULL_FACE);
		if (part != null)
		{
			for (ModelRenderer onepart : part)
			{
				onepart.renderWithRotation(1.0F);
			}
		}
		GL11.glEnable(GL_CULL_FACE);
		GL11.glPopMatrix();
	}
}

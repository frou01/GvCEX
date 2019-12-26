package handmadeguns.client.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.model.IModelCustom;
import org.lwjgl.opengl.GL11;

import static java.lang.Math.abs;
import static org.lwjgl.opengl.GL11.*;

public class HMGRenderItemCustom extends RenderItem implements IItemRenderer {
	private IModelCustom modeling;
	private ResourceLocation texture;
	public static float smoothing;
	public NBTTagCompound nbt;

	public HMGRenderItemCustom(IModelCustom modelgun, ResourceLocation texture) {
		modeling = modelgun;
		this.texture = texture;
	}

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {

		switch (type) {
			// case 1: //entity third person
			case INVENTORY:
			case FIRST_PERSON_MAP:
				return false;
			case EQUIPPED_FIRST_PERSON:
			case ENTITY:
			case EQUIPPED:
				return true;
		}
		return false;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
		switch (type) {
			// case 1:
			case INVENTORY:
			case FIRST_PERSON_MAP:
				return false;
			case EQUIPPED_FIRST_PERSON:
			case ENTITY:
			case EQUIPPED:
				return true;
		}
		return false;
	}
	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		GL11.glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		GL11.glColor4f(1, 1, 1, 1F);
		nbt = item.getTagCompound();
		switch (type) {
			case INVENTORY:
				break;
			case EQUIPPED_FIRST_PERSON://first
			{
//					boolean cocking = nbt.getBoolean("Cocking");
				GL11.glPushMatrix();
				GL11.glRotatef(180F, 1.0F, 0.0F, 0.0F);
				GL11.glRotatef(50F, 0.0F, 1.0F, 0.0F);
				GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
				Minecraft.getMinecraft().renderEngine.bindTexture(texture);
				GL11.glScalef(1, 1, 1);
				modeling.renderAll();
				GL11.glPopMatrix();//glend2
				break;
			}
			case EQUIPPED://thrid
				GL11.glPushMatrix();
				GL11.glRotatef(180F, 1.0F, 0.0F, 0.0F);
				GL11.glRotatef(50F, 0.0F, 1.0F, 0.0F);
				GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
				Minecraft.getMinecraft().renderEngine.bindTexture(texture);
				modeling.renderAll();
				GL11.glPopMatrix();//glend1
				break;

			case FIRST_PERSON_MAP:
				break;
		}

		GL11.glDepthMask(true);
		GL11.glDisable(GL_BLEND);
	}

	public void renderaspart() {
		renderaspart(0);
		renderaspart(1);
	}
	public void renderaspart(int pass) {
		glEnable(GL_BLEND);
		if(pass == 1) {
			glEnable(GL_BLEND);
			glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
			GL11.glDepthMask(false);
			glAlphaFunc(GL_LESS, 1);
		}else {
			GL11.glDepthMask(true);
			glAlphaFunc(GL_EQUAL, 1);
		}
		GL11.glColor4f(1, 1, 1, 1F);
		Minecraft.getMinecraft().renderEngine.bindTexture(texture);
		GL11.glScalef(1, 1, 1);
		modeling.renderAllExcept("light");


		RenderHelper.disableStandardItemLighting();
		float lastBrightnessX = OpenGlHelper.lastBrightnessX;
		float lastBrightnessY = OpenGlHelper.lastBrightnessY;
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240, 240);
		modeling.renderPart("light");
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)lastBrightnessX, (float)lastBrightnessY);
		RenderHelper.enableStandardItemLighting();

		{
			glClearStencil(0);
			glClear(GL_STENCIL_BUFFER_BIT);
			glEnable(GL_STENCIL_TEST);
			glStencilMask(1);

			glStencilFunc(
					GL_ALWAYS,   // GLenum func
					1,          // GLint ref
					~0);// GLuint mask
			glStencilOp(
					GL_KEEP,
					GL_KEEP,
					GL_REPLACE);
			if (pass != 1) {
				GL11.glDepthMask(false);
				glAlphaFunc(GL_ALWAYS, 1);
				glColorMask(
						false,   // GLboolean red
						false,   // GLboolean green
						false,   // GLboolean blue
						false);
			}
			modeling.renderPart("reticlePlate");
			if (pass != 1) {
				GL11.glDepthMask(true);
				glAlphaFunc(GL_EQUAL, 1);
				glColorMask(
						true,   // GLboolean red
						true,   // GLboolean green
						true,   // GLboolean blue
						true);
			}

			GL11.glDisable(GL11.GL_LIGHTING);
			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240, 240);
			glDisable(GL_DEPTH_TEST);


			glStencilFunc(
					GL_EQUAL,   // GLenum func
					1,          // GLint ref
					~0);// GLuint mask

			glStencilOp(GL_KEEP, GL_KEEP, GL_KEEP);
			glAlphaFunc(GL_ALWAYS, 1);
			GL11.glDepthMask(false);

			GL11.glDepthFunc(GL11.GL_ALWAYS);//ã≠êßï`âÊ
			modeling.renderPart("reticle");
			GL11.glDepthFunc(GL11.GL_LEQUAL);
			GL11.glDepthMask(true);
			glDisable(GL_STENCIL_TEST);
			glEnable(GL_DEPTH_TEST);
			GL11.glEnable(GL11.GL_LIGHTING);
			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float) lastBrightnessX, (float) lastBrightnessY);
		}
		if(pass == 1) {
			glEnable(GL_BLEND);
			glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
			GL11.glDepthMask(false);
			glAlphaFunc(GL_LESS, 1);
		}else {
			GL11.glDepthMask(true);
			glAlphaFunc(GL_EQUAL, 1);
		}

		GL11.glDepthMask(true);
	}
}

package handmadeguns.client.render;


import cpw.mods.fml.client.FMLClientHandler;
import handmadeguns.HandmadeGunsCore;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.renderer.texture.SimpleTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModelCustom;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL11.*;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

import static handmadeguns.HandmadeGunsCore.cfgRender_useStencil;
import static org.lwjgl.opengl.ARBFramebufferObject.*;
import static org.lwjgl.opengl.EXTFramebufferObject.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.GL_REFLECTION_MAP;

public abstract class PartsRender {
	public IModelCustom model;
	public ResourceLocation texture;
	public float gunPartsScale = 1;
	public int pass;

	public abstract void partSidentification(Object... data);

	public static final FrameBuffer FBO = FrameBuffer.create();

	public void part_Render(HMGGunParts parts, GunState state, float flame, int remainbullets, HMGGunParts_Motion_PosAndRotation OffsetAndRotation){
		HMGGunParts_Motion_PosAndRotation rotationCenterAndRotation = parts.getRenderinfCenter();
		if(OffsetAndRotation != null && !OffsetAndRotation.renderOnOff)return;
		GL11.glPushMatrix();
		transformParts(rotationCenterAndRotation,parts.getRenderinfDefault_offset(),parts);
		if(OffsetAndRotation != null)transformParts(rotationCenterAndRotation,OffsetAndRotation,parts);
		partModel_render(parts, state, flame, remainbullets, OffsetAndRotation);
		partSidentification(parts.childs,state,flame,remainbullets);
		GL11.glPopMatrix();
	}

	public void transformParts(HMGGunParts_Motion_PosAndRotation rotationCenterAndRotation,
	                           HMGGunParts_Motion_PosAndRotation posAndRotation,
	                           HMGGunParts parts){
		if (posAndRotation != null) {
			GL11.glTranslatef(posAndRotation.posX * gunPartsScale, posAndRotation.posY * gunPartsScale, posAndRotation.posZ * gunPartsScale);
			if(parts.rotateTypeIsVector){
				GL11.glTranslatef(rotationCenterAndRotation.posX * gunPartsScale, rotationCenterAndRotation.posY * gunPartsScale, rotationCenterAndRotation.posZ * gunPartsScale);
				GL11.glRotatef(posAndRotation.rotationX, rotationCenterAndRotation.rotateVec.x, rotationCenterAndRotation.rotateVec.y, rotationCenterAndRotation.rotateVec.z);
				GL11.glTranslatef(-rotationCenterAndRotation.posX * gunPartsScale, -rotationCenterAndRotation.posY * gunPartsScale, -rotationCenterAndRotation.posZ * gunPartsScale);
			}else {
				GL11.glTranslatef(rotationCenterAndRotation.posX * gunPartsScale, rotationCenterAndRotation.posY * gunPartsScale, rotationCenterAndRotation.posZ * gunPartsScale);
				GL11.glRotatef(posAndRotation.rotationY, 0, 1, 0);
				GL11.glRotatef(posAndRotation.rotationX, 1, 0, 0);
				GL11.glRotatef(posAndRotation.rotationZ, 0, 0, 1);
				GL11.glTranslatef(-rotationCenterAndRotation.posX * gunPartsScale, -rotationCenterAndRotation.posY * gunPartsScale, -rotationCenterAndRotation.posZ * gunPartsScale);
			}
		}
	}

	public void partModel_render(HMGGunParts parts, GunState state, float flame, int remainbullets, HMGGunParts_Motion_PosAndRotation OffsetAndRotation){
		HMGGunParts_Motion_PosAndRotation rotationCenterAndRotation = parts.getRenderinfCenter();
		if (parts.isbullet) {
			if (state == GunState.Recoil) {
				renderParts_Bullet(parts, flame / 10.0F, remainbullets, rotationCenterAndRotation);
			} else {
				renderParts_Bullet(parts, flame, remainbullets, rotationCenterAndRotation);
			}
		} else {
			if (this.pass == 1) {
				GL11.glEnable(3042);
				GL11.glBlendFunc(770, 771);
				GL11.glDepthMask(false);
				GL11.glAlphaFunc(513, 1.0F);
			} else {
				GL11.glDepthMask(true);
				GL11.glAlphaFunc(514, 1.0F);
			}
			this.model.renderPart(parts.partsname);
			float lastBrightnessX = OpenGlHelper.lastBrightnessX;
			float lastBrightnessY = OpenGlHelper.lastBrightnessY;
			GL11.glDisable(2896);
			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0F, 240.0F);
			this.model.renderPart(parts.partsname_light);
			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, lastBrightnessX, lastBrightnessY);
			GL11.glEnable(2896);
			if (parts.reticleAndPlate && this.pass == 1) {
				FBO.start();
				GL11.glPushMatrix();
				FMLClientHandler.instance().getClient().getTextureManager().bindTexture(this.texture);
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
				GL11.glClear(1024);
				GL11.glEnable(2960);
				GL11.glStencilMask(1);
				GL11.glStencilFunc(519, 1, -1);
				GL11.glStencilOp(7680, 7680, 7681);
				GL11.glDepthMask(false);
				GL11.glAlphaFunc(519, 1.0F);
				GL11.glColorMask(false, false, false, false);
				GL11.glEnable(3042);
				GL11.glBlendFunc(770, 771);
				GL11.glDepthMask(false);
				GL11.glAlphaFunc(516, 0.0F);
				this.model.renderPart(parts.partsname_reticlePlate);
				GL11.glDepthMask(true);
				GL11.glAlphaFunc(514, 1.0F);
				GL11.glColorMask(true, true, true, true);
				GL11.glDisable(2929);
				GL11.glStencilFunc(514, 1, -1);
				GL11.glStencilOp(7680, 7680, 7680);
				GL11.glAlphaFunc(516, 0.0F);
				GL11.glDepthMask(false);
				GL11.glDepthFunc(519);
				GL11.glDisable(2896);
				OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0F, 240.0F);
				this.model.renderPart(parts.partsname_reticle);
				if (parts.reticleChild != null)
					partSidentification(new Object[]{parts.reticleChild, state, Float.valueOf(flame), Integer.valueOf(remainbullets)});
				GL11.glDisable(2896);
				GL11.glDepthFunc(515);
				GL11.glDepthMask(true);
				GL11.glDisable(2960);
				GL11.glEnable(2929);
				GL11.glPopMatrix();
				int tex = FBO.end();
				OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0F, 240.0F);
				GL11.glPushMatrix();
				GL11.glDisable(2929);
				GL11.glMatrixMode(5889);
				FloatBuffer projectionMatrix = BufferUtils.createFloatBuffer(16);
				GL11.glGetFloat(2983, projectionMatrix);
				GL11.glLoadIdentity();
				GL11.glMatrixMode(5888);
				FloatBuffer modelViewMatrix = BufferUtils.createFloatBuffer(16);
				GL11.glGetFloat(2983, modelViewMatrix);
				GL11.glLoadIdentity();
				GL11.glOrtho(0.0D, 1.0D, 1.0D, 0.0D, -1.0D, 1.0D);
				GL11.glDisable(2884);
				GL11.glBindTexture(3553, tex);
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
				GL11.glBegin(7);
				GL11.glTexCoord2d(0.0D, 1.0D);
				GL11.glVertex2d(0.0D, 0.0D);
				GL11.glTexCoord2d(0.0D, 0.0D);
				GL11.glVertex2d(0.0D, 1.0D);
				GL11.glTexCoord2d(1.0D, 0.0D);
				GL11.glVertex2d(1.0D, 1.0D);
				GL11.glTexCoord2d(1.0D, 1.0D);
				GL11.glVertex2d(1.0D, 0.0D);
				GL11.glEnd();
				GL11.glEnable(2884);
				GL11.glMatrixMode(5889);
				GL11.glLoadMatrix(projectionMatrix);
				GL11.glMatrixMode(5888);
				GL11.glLoadMatrix(modelViewMatrix);
				GL11.glPopMatrix();
				FMLClientHandler.instance().getClient().getTextureManager().bindTexture(this.texture);
				GL11.glDepthFunc(515);
				GL11.glEnable(2929);
				OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, lastBrightnessX, lastBrightnessY);
				GL11.glEnable(2896);
			}
		}
	}

	public void renderParts_Bullet(HMGGunParts parts, float flame, int remainbullets, HMGGunParts_Motion_PosAndRotation rotationCenterAndRotation) {
		if (parts.isavatar) {
			if (parts.isbelt) {
				for (int i = 0; i < parts.Maximum_number_of_bullets && i < remainbullets; i++) {
					HMGGunParts_Motion_PosAndRotation bulletoffset = parts.getBulletposition(i - flame);
					if (bulletoffset != null && bulletoffset.renderOnOff) {
						GL11.glPushMatrix();
						transformParts(rotationCenterAndRotation, bulletoffset, parts);
						this.model.renderPart(parts.partsname);
						float lastBrightnessX = OpenGlHelper.lastBrightnessX;
						float lastBrightnessY = OpenGlHelper.lastBrightnessY;
						OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0F, 240.0F);
						this.model.renderPart(parts.partsname_light);
						OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, lastBrightnessX, lastBrightnessY);
						GL11.glPopMatrix();
					}
				}
			} else {
				HMGGunParts_Motion_PosAndRotation bulletoffset = parts.getRenderinfOfBullet();
				if (bulletoffset != null && bulletoffset.renderOnOff) {
					GL11.glTranslatef(bulletoffset.posX * -flame * this.gunPartsScale, bulletoffset.posY * -flame * this.gunPartsScale, bulletoffset.posZ * -flame * this.gunPartsScale);
					GL11.glTranslatef(rotationCenterAndRotation.posX * this.gunPartsScale, rotationCenterAndRotation.posY * this.gunPartsScale, rotationCenterAndRotation.posZ * this.gunPartsScale);
					GL11.glRotatef(bulletoffset.rotationY * -flame, 0.0F, 1.0F, 0.0F);
					GL11.glRotatef(bulletoffset.rotationX * -flame, 1.0F, 0.0F, 0.0F);
					GL11.glRotatef(bulletoffset.rotationZ * -flame, 0.0F, 0.0F, 1.0F);
					GL11.glTranslatef(-rotationCenterAndRotation.posX * this.gunPartsScale, -rotationCenterAndRotation.posY * this.gunPartsScale, -rotationCenterAndRotation.posZ * this.gunPartsScale);
					for (int i = 0; i < parts.Maximum_number_of_bullets && i < remainbullets; i++) {
						transformParts(rotationCenterAndRotation, bulletoffset, parts);
						this.model.renderPart(parts.partsname);
						float lastBrightnessX = OpenGlHelper.lastBrightnessX;
						float lastBrightnessY = OpenGlHelper.lastBrightnessY;
						OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0F, 240.0F);
						this.model.renderPart(parts.partsname_light);
						OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, lastBrightnessX, lastBrightnessY);
					}
				}
			}
		} else if (parts.isbelt) {
			HMGGunParts_Motion_PosAndRotation bulletoffset = parts.getBulletposition(remainbullets + flame);
			if (bulletoffset != null && bulletoffset.renderOnOff) {
				GL11.glPushMatrix();
				transformParts(rotationCenterAndRotation, bulletoffset, parts);
				this.model.renderPart(parts.partsname);
				float lastBrightnessX = OpenGlHelper.lastBrightnessX;
				float lastBrightnessY = OpenGlHelper.lastBrightnessY;
				OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0F, 240.0F);
				this.model.renderPart(parts.partsname_light);
				OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, lastBrightnessX, lastBrightnessY);
				GL11.glPopMatrix();
			}
		} else {
			HMGGunParts_Motion_PosAndRotation bulletoffset = parts.getRenderinfOfBullet();
			if (bulletoffset != null && bulletoffset.renderOnOff) {
				GL11.glTranslatef(bulletoffset.posX * flame * this.gunPartsScale, bulletoffset.posY * flame * this.gunPartsScale, bulletoffset.posZ * flame * this.gunPartsScale);
				GL11.glTranslatef(rotationCenterAndRotation.posX * this.gunPartsScale, rotationCenterAndRotation.posY * this.gunPartsScale, rotationCenterAndRotation.posZ * this.gunPartsScale);
				GL11.glRotatef(bulletoffset.rotationY * flame, 0.0F, 1.0F, 0.0F);
				GL11.glRotatef(bulletoffset.rotationX * flame, 1.0F, 0.0F, 0.0F);
				GL11.glRotatef(bulletoffset.rotationZ * flame, 0.0F, 0.0F, 1.0F);
				GL11.glTranslatef(-rotationCenterAndRotation.posX * this.gunPartsScale, -rotationCenterAndRotation.posY * this.gunPartsScale, -rotationCenterAndRotation.posZ * this.gunPartsScale);
				for (int i = 0; i < parts.Maximum_number_of_bullets && i < remainbullets; i++)
					transformParts(rotationCenterAndRotation, bulletoffset, parts);
				this.model.renderPart(parts.partsname);
				float lastBrightnessX = OpenGlHelper.lastBrightnessX;
				float lastBrightnessY = OpenGlHelper.lastBrightnessY;
				OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0F, 240.0F);
				this.model.renderPart(parts.partsname_light);
				OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, lastBrightnessX, lastBrightnessY);
			}
		}
	}
}
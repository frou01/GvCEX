package handmadeguns.client.render;

import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModelCustom;
import org.lwjgl.opengl.GL11;

import static org.lwjgl.opengl.GL11.*;

public abstract class PartsRender {
	public IModelCustom model;
	public ResourceLocation texture;
	public int pass;
	
	public abstract void partSidentification(Object... data);
	
	public void part_Render(HMGGunParts parts, GunState state, float flame, int remainbullets, HMGGunParts_Motion_PosAndRotation OffsetAndRotation){
		HMGGunParts_Motion_PosAndRotation rotationCenterAndRotation = parts.getRenderinfCenter();
		GL11.glPushMatrix();
		if(OffsetAndRotation != null) {
			GL11.glTranslatef(OffsetAndRotation.posX, OffsetAndRotation.posY, OffsetAndRotation.posZ);
			GL11.glTranslatef(rotationCenterAndRotation.posX, rotationCenterAndRotation.posY, rotationCenterAndRotation.posZ);
			GL11.glRotatef(OffsetAndRotation.rotationY, 0, 1, 0);
			GL11.glRotatef(OffsetAndRotation.rotationX, 1, 0, 0);
			GL11.glRotatef(OffsetAndRotation.rotationZ, 0, 0, 1);
			GL11.glTranslatef(-rotationCenterAndRotation.posX, -rotationCenterAndRotation.posY, -rotationCenterAndRotation.posZ);
		}else {
			GL11.glTranslatef(rotationCenterAndRotation.posX,rotationCenterAndRotation.posY,rotationCenterAndRotation.posZ);
			GL11.glRotatef(rotationCenterAndRotation.rotationY,0,1,0);
			GL11.glRotatef(rotationCenterAndRotation.rotationX,1,0,0);
			GL11.glRotatef(rotationCenterAndRotation.rotationZ,0,0,1);
			GL11.glTranslatef(-rotationCenterAndRotation.posX,-rotationCenterAndRotation.posY,-rotationCenterAndRotation.posZ);
		}
		partModel_render(parts, state, flame, remainbullets, OffsetAndRotation);
		partSidentification(parts.childs,new GunState[]{state},flame,remainbullets);
		GL11.glPopMatrix();
	}
	public void partModel_render(HMGGunParts parts, GunState state, float flame, int remainbullets, HMGGunParts_Motion_PosAndRotation OffsetAndRotation){
		HMGGunParts_Motion_PosAndRotation rotationCenterAndRotation = parts.getRenderinfCenter();
		
		
		if (parts.isbullet){
			if(state == GunState.Recoil)
				renderParts_Bullet(parts,flame/10,remainbullets,rotationCenterAndRotation);
			else
				renderParts_Bullet(parts,flame,remainbullets,rotationCenterAndRotation);
		}else {
			if(parts.reticleAndPlate){
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
				model.renderPart(parts.partsname + "reticlePlate");
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
				float lastBrightnessX = OpenGlHelper.lastBrightnessX;
				float lastBrightnessY = OpenGlHelper.lastBrightnessY;
				OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240, 240);
				glDisable(GL_DEPTH_TEST);
				
				
				glStencilFunc(
						GL_EQUAL,   // GLenum func
						1,          // GLint ref
						~0);// GLuint mask
				
				glStencilOp(GL_KEEP, GL_KEEP, GL_KEEP);
				glAlphaFunc(GL_ALWAYS, 1);
				GL11.glDepthMask(false);
				
				GL11.glDepthFunc(GL11.GL_ALWAYS);//強制描画
				model.renderPart(parts.partsname + "reticle");
				GL11.glDepthFunc(GL11.GL_LEQUAL);
				GL11.glDepthMask(true);
				glDisable(GL_STENCIL_TEST);
				glEnable(GL_DEPTH_TEST);
				GL11.glEnable(GL11.GL_LIGHTING);
				OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)lastBrightnessX, (float)lastBrightnessY);
				
				if(pass == 1) {
					glEnable(GL_BLEND);
					glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
					GL11.glDepthMask(false);
					glAlphaFunc(GL_LESS, 1);
				}else {
					GL11.glDepthMask(true);
					glAlphaFunc(GL_EQUAL, 1);
				}
			}
			model.renderPart(parts.partsname);
			float lastBrightnessX = OpenGlHelper.lastBrightnessX;
			float lastBrightnessY = OpenGlHelper.lastBrightnessY;
			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240, 240);
			model.renderPart(parts.partsname + "light");
			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)lastBrightnessX, (float)lastBrightnessY);
		}
	}
	
	public void renderParts_Bullet(HMGGunParts parts,float flame,int remainbullets,HMGGunParts_Motion_PosAndRotation rotationCenterAndRotation){
		if(parts.isavatar){
			if(parts.isbelt){
				//�c�e������e��`�悷��ʒu���擾����B
				for (int i = 0; (i < parts.Maximum_number_of_bullets && i < remainbullets); i++) {
					HMGGunParts_Motion_PosAndRotation bulletoffset = parts.getBulletposition(i - flame);
					if(bulletoffset != null) {
						GL11.glPushMatrix();
						//�����͐�������������߂��̂Ńv�b�V��
						GL11.glTranslatef(bulletoffset.posX, bulletoffset.posY, bulletoffset.posZ);
						GL11.glTranslatef(rotationCenterAndRotation.posX, rotationCenterAndRotation.posY, rotationCenterAndRotation.posZ);
						GL11.glRotatef(bulletoffset.rotationY, 0, 1, 0);
						GL11.glRotatef(bulletoffset.rotationX, 1, 0, 0);
						GL11.glRotatef(bulletoffset.rotationZ, 0, 0, 1);
						GL11.glTranslatef(-rotationCenterAndRotation.posX, -rotationCenterAndRotation.posY, -rotationCenterAndRotation.posZ);
						model.renderPart(parts.partsname);
						float lastBrightnessX = OpenGlHelper.lastBrightnessX;
						float lastBrightnessY = OpenGlHelper.lastBrightnessY;
						OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240, 240);
						model.renderPart(parts.partsname + "light");
						OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)lastBrightnessX, (float)lastBrightnessY);
						GL11.glPopMatrix();
					}
				}
			}else {
				//�e�e�Ƃ��ĕ`��A�e�����J��Ԃ�
				//�e�͒e�����I�t�Z�b�g������
				HMGGunParts_Motion_PosAndRotation bulletoffset = parts.getRenderinfOfBullet();
				if(bulletoffset != null) {
					GL11.glTranslatef(bulletoffset.posX * -flame, bulletoffset.posY * -flame, bulletoffset.posZ * -flame);
					GL11.glTranslatef(rotationCenterAndRotation.posX, rotationCenterAndRotation.posY, rotationCenterAndRotation.posZ);
					GL11.glRotatef(bulletoffset.rotationY * -flame, 0, 1, 0);
					GL11.glRotatef(bulletoffset.rotationX * -flame, 1, 0, 0);
					GL11.glRotatef(bulletoffset.rotationZ * -flame, 0, 0, 1);
					GL11.glTranslatef(-rotationCenterAndRotation.posX, -rotationCenterAndRotation.posY, -rotationCenterAndRotation.posZ);
					for (int i = 0; (i < parts.Maximum_number_of_bullets && i < remainbullets); i++) {
						GL11.glTranslatef(bulletoffset.posX, bulletoffset.posY, bulletoffset.posZ);
						GL11.glTranslatef(rotationCenterAndRotation.posX, rotationCenterAndRotation.posY, rotationCenterAndRotation.posZ);
						GL11.glRotatef(bulletoffset.rotationY, 0, 1, 0);
						GL11.glRotatef(bulletoffset.rotationX, 1, 0, 0);
						GL11.glRotatef(bulletoffset.rotationZ, 0, 0, 1);
						GL11.glTranslatef(-rotationCenterAndRotation.posX, -rotationCenterAndRotation.posY, -rotationCenterAndRotation.posZ);
						model.renderPart(parts.partsname);
						float lastBrightnessX = OpenGlHelper.lastBrightnessX;
						float lastBrightnessY = OpenGlHelper.lastBrightnessY;
						OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240, 240);
						model.renderPart(parts.partsname + "light");
						OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)lastBrightnessX, (float)lastBrightnessY);
					}
				}
			}
		}else {
			if(parts.isbelt){
				//�c�e������e��`�悷��ʒu���擾����B
				HMGGunParts_Motion_PosAndRotation bulletoffset = parts.getBulletposition(remainbullets + flame);
				if(bulletoffset != null) {
					GL11.glPushMatrix();
					GL11.glTranslatef(bulletoffset.posX, bulletoffset.posY, bulletoffset.posZ);
					GL11.glTranslatef(rotationCenterAndRotation.posX, rotationCenterAndRotation.posY, rotationCenterAndRotation.posZ);
					GL11.glRotatef(bulletoffset.rotationY, 0, 1, 0);
					GL11.glRotatef(bulletoffset.rotationX, 1, 0, 0);
					GL11.glRotatef(bulletoffset.rotationZ, 0, 0, 1);
					GL11.glTranslatef(-bulletoffset.posX, -bulletoffset.posY, -bulletoffset.posZ);
					model.renderPart(parts.partsname);
					float lastBrightnessX = OpenGlHelper.lastBrightnessX;
					float lastBrightnessY = OpenGlHelper.lastBrightnessY;
					OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240, 240);
					model.renderPart(parts.partsname + "light");
					OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)lastBrightnessX, (float)lastBrightnessY);
					GL11.glPopMatrix();
				}
			}else {
				//�e�����I�t�Z�b�g������
				HMGGunParts_Motion_PosAndRotation bulletoffset = parts.getRenderinfOfBullet();
				if(bulletoffset != null) {
					GL11.glTranslatef(bulletoffset.posX * flame, bulletoffset.posY * flame, bulletoffset.posZ * flame);
					GL11.glTranslatef(rotationCenterAndRotation.posX, rotationCenterAndRotation.posY, rotationCenterAndRotation.posZ);
					GL11.glRotatef(bulletoffset.rotationY * flame, 0, 1, 0);
					GL11.glRotatef(bulletoffset.rotationX * flame, 1, 0, 0);
					GL11.glRotatef(bulletoffset.rotationZ * flame, 0, 0, 1);
					GL11.glTranslatef(-rotationCenterAndRotation.posX, -rotationCenterAndRotation.posY, -rotationCenterAndRotation.posZ);
					for (int i = 0; (i < parts.Maximum_number_of_bullets && i < remainbullets); i++) {
						GL11.glTranslatef(bulletoffset.posX, bulletoffset.posY, bulletoffset.posZ);
						GL11.glTranslatef(rotationCenterAndRotation.posX, rotationCenterAndRotation.posY, rotationCenterAndRotation.posZ);
						GL11.glRotatef(bulletoffset.rotationY, 0, 1, 0);
						GL11.glRotatef(bulletoffset.rotationX, 1, 0, 0);
						GL11.glRotatef(bulletoffset.rotationZ, 0, 0, 1);
						GL11.glTranslatef(-rotationCenterAndRotation.posX, -rotationCenterAndRotation.posY, -rotationCenterAndRotation.posZ);
					}
					model.renderPart(parts.partsname);
					float lastBrightnessX = OpenGlHelper.lastBrightnessX;
					float lastBrightnessY = OpenGlHelper.lastBrightnessY;
					OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240, 240);
					model.renderPart(parts.partsname + "light");
					OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)lastBrightnessX, (float)lastBrightnessY);
				}
			}
		}
	}
}

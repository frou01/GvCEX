package handmadeguns.client.render;


import cpw.mods.fml.client.FMLClientHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.OpenGlHelper;
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


public abstract class PartsRender {
	public IModelCustom model;
	public ResourceLocation texture;
	public float gunPartsScale = 1;
	public int pass;

	public abstract void partSidentification(Object... data);

	private static final FrameBuffer FBO = FrameBuffer.create();

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


		if (parts.isbullet){
			if(state == GunState.Recoil)
				renderParts_Bullet(parts,flame/10,remainbullets,rotationCenterAndRotation);
			else
				renderParts_Bullet(parts,flame,remainbullets,rotationCenterAndRotation);
		}else {
			if(parts.reticleAndPlate && cfgRender_useStencil && pass==1){
				GL11.glDepthMask(true);
				glAlphaFunc(GL_EQUAL, 1);
				//INSERT : フレームバッファに描画開始
				//       : 保険でMatrixを一層深く
				//       : 銃のテクスチャを再bind
				FBO.start();
				GL11.glPushMatrix();
				FMLClientHandler.instance().getClient().getTextureManager().bindTexture(this.texture);
				glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
				//INSERT-END

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
				model.renderPart(parts.partsname_reticlePlate);
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
				model.renderPart(parts.partsname_reticle);
				if(parts.reticleChild != null)partSidentification(parts.reticleChild,state,flame,remainbullets);
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


				//INSET : Matrixを上層へ復元
				//      : FBOからテクスチャIDを取得
				//      : 画面に出力できるようにMatrixを保存し初期化
				//      : テクスチャをViwerPortに出力
				//      : 保存したMatrixを呼び戻す
				GL11.glPopMatrix();
				int tex = FBO.end();

				glPushMatrix();

				glMatrixMode(GL_PROJECTION);
				FloatBuffer projectionMatrix = BufferUtils.createFloatBuffer(16);
				glGetFloat(GL_PROJECTION_MATRIX, projectionMatrix);
				glLoadIdentity();

				glMatrixMode(GL_MODELVIEW);
				FloatBuffer modelViewMatrix = BufferUtils.createFloatBuffer(16);
				glGetFloat(GL_PROJECTION_MATRIX, modelViewMatrix);
				glLoadIdentity();

				glOrtho(0,1,1,0,-1,1);
				glDisable(GL_CULL_FACE);
				glBindTexture(GL_TEXTURE_2D, tex);
				glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
				glBegin(GL_QUADS);
				glTexCoord2d(0.0D, 1.0D);glVertex2d(0.0D, 0.0D);
				glTexCoord2d(0.0D, 0.0D);glVertex2d(0.0D, 1.0D);
				glTexCoord2d(1.0D, 0.0D);glVertex2d(1.0D, 1.0D);
				glTexCoord2d(1.0D, 1.0D);glVertex2d(1.0D, 0.0D);
				glEnd();
				glEnable(GL_CULL_FACE);

				glMatrixMode(GL_PROJECTION);
				glLoadMatrix(projectionMatrix);
				glMatrixMode(GL_MODELVIEW);
				glLoadMatrix(modelViewMatrix);
				glPopMatrix();
				//INSERT-END
				FMLClientHandler.instance().getClient().getTextureManager().bindTexture(this.texture);
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
			model.renderPart(parts.partsname_light);
			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)lastBrightnessX, (float)lastBrightnessY);
		}
	}

	public void renderParts_Bullet(HMGGunParts parts,float flame,int remainbullets,HMGGunParts_Motion_PosAndRotation rotationCenterAndRotation){
		if(parts.isavatar){
			if(parts.isbelt){
				for (int i = 0; (i < parts.Maximum_number_of_bullets && i < remainbullets); i++) {
					HMGGunParts_Motion_PosAndRotation bulletoffset = parts.getBulletposition(i - flame);
					if(bulletoffset != null && bulletoffset.renderOnOff) {
						GL11.glPushMatrix();
						transformParts(rotationCenterAndRotation,bulletoffset,parts);
						model.renderPart(parts.partsname);
						float lastBrightnessX = OpenGlHelper.lastBrightnessX;
						float lastBrightnessY = OpenGlHelper.lastBrightnessY;
						OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240, 240);
						model.renderPart(parts.partsname_light);
						OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)lastBrightnessX, (float)lastBrightnessY);
						GL11.glPopMatrix();
					}
				}
			}else {
				HMGGunParts_Motion_PosAndRotation bulletoffset = parts.getRenderinfOfBullet();
				if(bulletoffset != null && bulletoffset.renderOnOff) {
					GL11.glTranslatef(bulletoffset.posX * -flame * gunPartsScale, bulletoffset.posY * -flame * gunPartsScale, bulletoffset.posZ * -flame * gunPartsScale);
					GL11.glTranslatef(rotationCenterAndRotation.posX * gunPartsScale, rotationCenterAndRotation.posY * gunPartsScale, rotationCenterAndRotation.posZ * gunPartsScale);
					GL11.glRotatef(bulletoffset.rotationY * -flame, 0, 1, 0);
					GL11.glRotatef(bulletoffset.rotationX * -flame, 1, 0, 0);
					GL11.glRotatef(bulletoffset.rotationZ * -flame, 0, 0, 1);
					GL11.glTranslatef(-rotationCenterAndRotation.posX * gunPartsScale, -rotationCenterAndRotation.posY * gunPartsScale, -rotationCenterAndRotation.posZ * gunPartsScale);
					for (int i = 0; (i < parts.Maximum_number_of_bullets && i < remainbullets); i++) {
						transformParts(rotationCenterAndRotation,bulletoffset,parts);
						model.renderPart(parts.partsname);
						float lastBrightnessX = OpenGlHelper.lastBrightnessX;
						float lastBrightnessY = OpenGlHelper.lastBrightnessY;
						OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240, 240);
						model.renderPart(parts.partsname_light);
						OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)lastBrightnessX, (float)lastBrightnessY);
					}
				}
			}
		}else {
			if(parts.isbelt){
				HMGGunParts_Motion_PosAndRotation bulletoffset = parts.getBulletposition(remainbullets + flame);
				if(bulletoffset != null && bulletoffset.renderOnOff) {
					GL11.glPushMatrix();
					transformParts(rotationCenterAndRotation,bulletoffset,parts);
					model.renderPart(parts.partsname);
					float lastBrightnessX = OpenGlHelper.lastBrightnessX;
					float lastBrightnessY = OpenGlHelper.lastBrightnessY;
					OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240, 240);
					model.renderPart(parts.partsname_light);
					OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)lastBrightnessX, (float)lastBrightnessY);
					GL11.glPopMatrix();
				}
			}else {
				HMGGunParts_Motion_PosAndRotation bulletoffset = parts.getRenderinfOfBullet();
				if(bulletoffset != null && bulletoffset.renderOnOff) {
					GL11.glTranslatef(bulletoffset.posX * flame * gunPartsScale, bulletoffset.posY * flame * gunPartsScale, bulletoffset.posZ * flame * gunPartsScale);
					GL11.glTranslatef(rotationCenterAndRotation.posX * gunPartsScale, rotationCenterAndRotation.posY * gunPartsScale, rotationCenterAndRotation.posZ * gunPartsScale);
					GL11.glRotatef(bulletoffset.rotationY * flame, 0, 1, 0);
					GL11.glRotatef(bulletoffset.rotationX * flame, 1, 0, 0);
					GL11.glRotatef(bulletoffset.rotationZ * flame, 0, 0, 1);
					GL11.glTranslatef(-rotationCenterAndRotation.posX * gunPartsScale, -rotationCenterAndRotation.posY * gunPartsScale, -rotationCenterAndRotation.posZ * gunPartsScale);
					for (int i = 0; (i < parts.Maximum_number_of_bullets && i < remainbullets); i++) {
						transformParts(rotationCenterAndRotation,bulletoffset,parts);
					}
					model.renderPart(parts.partsname);
					float lastBrightnessX = OpenGlHelper.lastBrightnessX;
					float lastBrightnessY = OpenGlHelper.lastBrightnessY;
					OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240, 240);
					model.renderPart(parts.partsname_light);
					OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)lastBrightnessX, (float)lastBrightnessY);
				}
			}
		}
	}

	//INSERT INNER CLASS
	//フレームバッファ用のクラス
	//ueev_guns_packに内包されているueev.utils.RenderHelper.FrameBufferのコピー
	public static class FrameBuffer{
		private int defaultFBOID = 0;
		private int listIndex = -1;
		private List<Integer> fboList = new ArrayList<Integer>();
		private List<Integer> texList = new ArrayList<Integer>();
		private List<Integer> stencilList = new ArrayList<Integer>();//depth兼ねてるのでこの命名はおかしい

		public static FrameBuffer create(){
			return new FrameBuffer();
		}

		private int add(){
			int fboID;
			this.fboList.add(fboID = glGenFramebuffersEXT());
			this.texList.add(glGenTextures());
			this.stencilList.add(glGenRenderbuffersEXT());

			return fboID;
		}

		public void start(){
			int fboID;
			int texID;
			//int depthID;
			int stencilID;

			int width = Display.getWidth();
			int height = Display.getHeight();

			//標準のfboIDを取得
			if(this.listIndex == -1)this.defaultFBOID = glGetInteger(GL_FRAMEBUFFER_BINDING_EXT);//defaultFBOID = FMLClientHandler.instance().getClient().func_147110_a().field_147616_f;

			//FBOおよびアタッチするTextureとBufferを取得
			if(++this.listIndex >= this.fboList.size()){
				this.add();
			}

			//すでに作られたFBOなどを使いまわす処理
			fboID     = this.fboList.get(this.listIndex);
			texID     = this.texList.get(this.listIndex);
			//depthID   = this.depthList.get(this.listIndex);
			stencilID = this.stencilList.get(this.listIndex);

			//レンダリング先をFBOに設定
			glBindFramebufferEXT(GL_FRAMEBUFFER_EXT, fboID);

			//カラー用のテクスチャをアタッチ
			glBindTexture(GL_TEXTURE_2D, texID);
			glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
			glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, width, height, 0, GL_RGBA, GL_INT, (java.nio.ByteBuffer)null);
			glFramebufferTexture2DEXT(GL_FRAMEBUFFER_EXT, GL_COLOR_ATTACHMENT0_EXT, GL_TEXTURE_2D, texID, 0);

			//StencilBufferとDepthをアタッチ
			glBindRenderbufferEXT(GL_RENDERBUFFER_EXT, stencilID);
			glRenderbufferStorageEXT(GL_RENDERBUFFER_EXT, GL_DEPTH24_STENCIL8, width, height);
			glFramebufferRenderbufferEXT(GL_FRAMEBUFFER_EXT, GL_DEPTH_STENCIL_ATTACHMENT, GL_RENDERBUFFER_EXT, stencilID);

			glBindRenderbufferEXT(GL_RENDERBUFFER_EXT, 0);
			glClearColor(0.0F, 0.0F, 0.0F, 0.0F);
			glClear(GL_COLOR_BUFFER_BIT|GL_DEPTH_BUFFER_BIT);
			glBindFramebuffer(GL_READ_FRAMEBUFFER, this.defaultFBOID);
			glBindFramebuffer(GL_DRAW_FRAMEBUFFER, fboID);
			glBlitFramebuffer(0, 0, width, height, 0, 0, width, height,
					GL_DEPTH_BUFFER_BIT, GL_NEAREST);

		}

		public int end(){
			int fboID = -1;
			int texID = -1;

			if(this.listIndex >= 0){
				texID = this.texList.get(this.listIndex);
			}

			if(--this.listIndex >= 0){
				fboID = this.fboList.get(this.listIndex);
			}else{
				fboID = this.defaultFBOID;//FMLClientHandler.instance().getClient().func_147110_a().field_147616_f;
				this.listIndex = -1;
			}

			glBindFramebufferEXT(GL_FRAMEBUFFER_EXT, fboID);
			return texID;
		}
	}
}

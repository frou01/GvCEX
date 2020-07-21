package handmadeguns.client.render;

import org.lwjgl.opengl.Display;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.ARBFramebufferObject.*;
import static org.lwjgl.opengl.EXTFramebufferObject.*;
import static org.lwjgl.opengl.GL11.*;

public class FrameBuffer{
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
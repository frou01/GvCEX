/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.Properties;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TextureAnimation
/*     */ {
/*  20 */   private String srcTex = null;
/*  21 */   private String dstTex = null;
/*  22 */   bqx dstTexLoc = null;
/*  23 */   private int dstTextId = -1;
/*  24 */   private int dstX = 0;
/*  25 */   private int dstY = 0;
/*  26 */   private int frameWidth = 0;
/*  27 */   private int frameHeight = 0;
/*  28 */   private TextureAnimationFrame[] frames = null;
/*  29 */   private int activeFrame = 0;
/*     */   
/*  31 */   byte[] srcData = null;
/*     */   
/*  33 */   private ByteBuffer imageData = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TextureAnimation(String texFrom, byte[] srcData, String texTo, bqx locTexTo, int dstX, int dstY, int frameWidth, int frameHeight, Properties props, int durDef) {
/*  41 */     this.srcTex = texFrom;
/*  42 */     this.dstTex = texTo;
/*  43 */     this.dstTexLoc = locTexTo;
/*  44 */     this.dstX = dstX;
/*  45 */     this.dstY = dstY;
/*  46 */     this.frameWidth = frameWidth;
/*  47 */     this.frameHeight = frameHeight;
/*     */     
/*  49 */     int frameLen = frameWidth * frameHeight * 4;
/*     */     
/*  51 */     if (srcData.length % frameLen != 0) {
/*  52 */       Config.warn("Invalid animated texture length: " + srcData.length + ", frameWidth: " + frameWidth + ", frameHeight: " + frameHeight);
/*     */     }
/*  54 */     this.srcData = srcData;
/*     */     
/*  56 */     int numFrames = srcData.length / frameLen;
/*  57 */     if (props.get("tile.0") != null)
/*     */     {
/*  59 */       for (int j = 0; props.get("tile." + j) != null; j++)
/*     */       {
/*  61 */         numFrames = j + 1;
/*     */       }
/*     */     }
/*     */     
/*  65 */     String durationDefStr = (String)props.get("duration");
/*  66 */     int durationDef = Config.parseInt(durationDefStr, durDef);
/*     */     
/*  68 */     this.frames = new TextureAnimationFrame[numFrames];
/*  69 */     for (int i = 0; i < this.frames.length; i++) {
/*     */       
/*  71 */       String indexStr = (String)props.get("tile." + i);
/*  72 */       int index = Config.parseInt(indexStr, i);
/*  73 */       String durationStr = (String)props.get("duration." + i);
/*  74 */       int duration = Config.parseInt(durationStr, durationDef);
/*  75 */       TextureAnimationFrame frm = new TextureAnimationFrame(index, duration);
/*  76 */       this.frames[i] = frm;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean nextFrame() {
/*  85 */     if (this.frames.length <= 0) {
/*  86 */       return false;
/*     */     }
/*  88 */     if (this.activeFrame >= this.frames.length) {
/*  89 */       this.activeFrame = 0;
/*     */     }
/*  91 */     TextureAnimationFrame frame = this.frames[this.activeFrame];
/*     */     
/*  93 */     frame.counter++;
/*     */     
/*  95 */     if (frame.counter < frame.duration) {
/*  96 */       return false;
/*     */     }
/*  98 */     frame.counter = 0;
/*     */     
/* 100 */     this.activeFrame++;
/*     */     
/* 102 */     if (this.activeFrame >= this.frames.length) {
/* 103 */       this.activeFrame = 0;
/*     */     }
/* 105 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getActiveFrameIndex() {
/* 113 */     if (this.frames.length <= 0) {
/* 114 */       return 0;
/*     */     }
/* 116 */     if (this.activeFrame >= this.frames.length) {
/* 117 */       this.activeFrame = 0;
/*     */     }
/* 119 */     TextureAnimationFrame frame = this.frames[this.activeFrame];
/*     */     
/* 121 */     return frame.index;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getFrameCount() {
/* 128 */     return this.frames.length;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean updateTexture() {
/* 136 */     if (this.dstTextId < 0) {
/*     */       
/* 138 */       bqh tex = TextureUtils.getTexture(this.dstTexLoc);
/* 139 */       if (tex == null) {
/* 140 */         return false;
/*     */       }
/* 142 */       this.dstTextId = tex.b();
/*     */     } 
/*     */     
/* 145 */     if (this.imageData == null) {
/*     */ 
/*     */       
/* 148 */       this.imageData = ban.c(this.srcData.length);
/* 149 */       this.imageData.put(this.srcData);
/*     */       
/* 151 */       this.srcData = null;
/*     */     } 
/*     */     
/* 154 */     if (!nextFrame())
/*     */     {
/*     */       
/* 157 */       return false;
/*     */     }
/*     */     
/* 160 */     int frameLen = this.frameWidth * this.frameHeight * 4;
/*     */     
/* 162 */     int imgNum = getActiveFrameIndex();
/* 163 */     int offset = frameLen * imgNum;
/*     */     
/* 165 */     if (offset + frameLen > this.imageData.capacity()) {
/* 166 */       return false;
/*     */     }
/* 168 */     this.imageData.position(offset);
/*     */     
/* 170 */     GlStateManager.bindTexture(this.dstTextId);
/* 171 */     GL11.glTexSubImage2D(3553, 0, this.dstX, this.dstY, this.frameWidth, this.frameHeight, 6408, 5121, this.imageData);
/*     */     
/* 173 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getSrcTex() {
/* 180 */     return this.srcTex;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDstTex() {
/* 187 */     return this.dstTex;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public bqx getDstTexLoc() {
/* 194 */     return this.dstTexLoc;
/*     */   }
/*     */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\TextureAnimation.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
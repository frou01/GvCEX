/*     */ import java.nio.IntBuffer;
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
/*     */ public class GlStateManager
/*     */ {
/*  15 */   public static int activeTextureUnit = 0;
/*     */   
/*  17 */   public static int GL_FRAMEBUFFER = buu.e;
/*  18 */   public static int GL_RENDERBUFFER = buu.f;
/*  19 */   public static int GL_COLOR_ATTACHMENT0 = buu.g;
/*  20 */   public static int GL_DEPTH_ATTACHMENT = buu.h;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getActiveTextureUnit() {
/*  27 */     return activeTextureUnit;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void bindTexture(int tex) {
/*  33 */     GL11.glBindTexture(3553, tex);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void deleteTexture(int tex) {
/*  40 */     if (tex == 0) {
/*     */       return;
/*     */     }
/*  43 */     GL11.glDeleteTextures(tex);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void deleteTextures(IntBuffer buf) {
/*  50 */     buf.rewind();
/*  51 */     while (buf.position() < buf.limit()) {
/*     */       
/*  53 */       int texId = buf.get();
/*  54 */       deleteTexture(texId);
/*     */     } 
/*  56 */     buf.rewind();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setActiveTexture(int tex) {
/*  62 */     buu.j(tex);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void colorMask(boolean red, boolean green, boolean blue, boolean alpha) {
/*  68 */     GL11.glColorMask(red, green, blue, alpha);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void enableCull() {
/*  74 */     GL11.glEnable(2884);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void disableCull() {
/*  80 */     GL11.glDisable(2884);
/*     */   }
/*     */   
/*     */   public static void enableDepth() {
/*  84 */     GL11.glEnable(2929);
/*     */   }
/*     */   
/*     */   public static void disableDepth() {
/*  88 */     GL11.glDisable(2929);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void enableTexture2D() {
/*  93 */     GL11.glEnable(3553);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void disableTexture2D() {
/*  98 */     GL11.glDisable(3553);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void enableAlpha() {
/* 103 */     GL11.glEnable(3008);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void disableAlpha() {
/* 108 */     GL11.glDisable(3008);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void enableBlend() {
/* 113 */     GL11.glEnable(3042);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void disableBlend() {
/* 118 */     GL11.glDisable(3042);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void depthFunc(int func) {
/* 123 */     GL11.glDepthFunc(func);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void depthMask(boolean mask) {
/* 128 */     GL11.glDepthMask(mask);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void enableLighting() {
/* 133 */     GL11.glEnable(2896);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void disableLighting() {
/* 138 */     GL11.glDisable(2896);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void color(float p_179131_0_, float p_179131_1_, float p_179131_2_, float p_179131_3_) {
/* 143 */     GL11.glColor4f(p_179131_0_, p_179131_1_, p_179131_2_, p_179131_3_);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void color(float p_179124_0_, float p_179124_1_, float p_179124_2_) {
/* 148 */     color(p_179124_0_, p_179124_1_, p_179124_2_, 1.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void clearColor(float red, float green, float blue, float alpha) {
/* 153 */     GL11.glClearColor(red, green, blue, alpha);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void alphaFunc(int func, float ref) {
/* 158 */     GL11.glAlphaFunc(func, ref);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void blendFunc(int sfactor, int dfactor) {
/* 163 */     GL11.glBlendFunc(sfactor, dfactor);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void setFog(int p_179093_0_) {
/* 168 */     GL11.glFogi(2917, p_179093_0_);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void setFogDensity(float p_179095_0_) {
/* 173 */     GL11.glFogf(2914, p_179095_0_);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void setFogStart(float p_179102_0_) {
/* 178 */     GL11.glFogf(2915, p_179102_0_);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void setFogEnd(float p_179153_0_) {
/* 183 */     GL11.glFogf(2916, p_179153_0_);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void shadeModel(int p_179103_0_) {
/* 188 */     GL11.glShadeModel(p_179103_0_);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void clear(int p_179086_0_) {
/* 193 */     GL11.glClear(p_179086_0_);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void matrixMode(int p_179128_0_) {
/* 198 */     GL11.glMatrixMode(p_179128_0_);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void loadIdentity() {
/* 203 */     GL11.glLoadIdentity();
/*     */   }
/*     */ 
/*     */   
/*     */   public static void pushMatrix() {
/* 208 */     GL11.glPushMatrix();
/*     */   }
/*     */ 
/*     */   
/*     */   public static void popMatrix() {
/* 213 */     GL11.glPopMatrix();
/*     */   }
/*     */ 
/*     */   
/*     */   public static void tryBlendFuncSeparate(int p_179120_0_, int p_179120_1_, int p_179120_2_, int p_179120_3_) {
/* 218 */     buu.c(p_179120_0_, p_179120_1_, p_179120_2_, p_179120_3_);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void rotate(float p_179114_0_, float p_179114_1_, float p_179114_2_, float p_179114_3_) {
/* 223 */     GL11.glRotatef(p_179114_0_, p_179114_1_, p_179114_2_, p_179114_3_);
/*     */   }
/*     */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\GlStateManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
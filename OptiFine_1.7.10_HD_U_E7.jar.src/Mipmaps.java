/*     */ import java.awt.Dimension;
/*     */ import java.nio.IntBuffer;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Mipmaps
/*     */ {
/*     */   private final String iconName;
/*     */   private final int width;
/*     */   private final int height;
/*     */   private final int[] data;
/*     */   private final boolean direct;
/*     */   private int[][] mipmapDatas;
/*     */   private IntBuffer[] mipmapBuffers;
/*     */   private Dimension[] mipmapDimensions;
/*     */   
/*     */   public Mipmaps(String iconName, int width, int height, int[] data, boolean direct) {
/*  35 */     this.iconName = iconName;
/*  36 */     this.width = width;
/*  37 */     this.height = height;
/*  38 */     this.data = data;
/*  39 */     this.direct = direct;
/*     */     
/*  41 */     this.mipmapDimensions = makeMipmapDimensions(width, height, iconName);
/*     */     
/*  43 */     this.mipmapDatas = generateMipMapData(data, width, height, this.mipmapDimensions);
/*     */     
/*  45 */     if (direct) {
/*  46 */       this.mipmapBuffers = makeMipmapBuffers(this.mipmapDimensions, this.mipmapDatas);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static Dimension[] makeMipmapDimensions(int width, int height, String iconName) {
/*  52 */     int texWidth = TextureUtils.ceilPowerOfTwo(width);
/*  53 */     int texHeight = TextureUtils.ceilPowerOfTwo(height);
/*     */     
/*  55 */     if (texWidth != width || texHeight != height) {
/*     */       
/*  57 */       Config.warn("Mipmaps not possible (power of 2 dimensions needed), texture: " + iconName + ", dim: " + width + "x" + height);
/*  58 */       return new Dimension[0];
/*     */     } 
/*     */     
/*  61 */     List<Dimension> listDims = new ArrayList();
/*  62 */     int mipWidth = texWidth;
/*  63 */     int mipHeight = texHeight;
/*     */ 
/*     */     
/*     */     while (true) {
/*  67 */       mipWidth /= 2;
/*  68 */       mipHeight /= 2;
/*     */       
/*  70 */       if (mipWidth <= 0 && mipHeight <= 0)
/*     */         break; 
/*  72 */       if (mipWidth <= 0)
/*  73 */         mipWidth = 1; 
/*  74 */       if (mipHeight <= 0) {
/*  75 */         mipHeight = 1;
/*     */       }
/*  77 */       int mipLen = mipWidth * mipHeight * 4;
/*     */       
/*  79 */       Dimension dim = new Dimension(mipWidth, mipHeight);
/*  80 */       listDims.add(dim);
/*     */     } 
/*     */     
/*  83 */     Dimension[] mipmapDimensions = listDims.<Dimension>toArray(new Dimension[listDims.size()]);
/*  84 */     return mipmapDimensions;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int[][] generateMipMapData(int[] data, int width, int height, Dimension[] mipmapDimensions) {
/*  94 */     int[] parMipData = data;
/*  95 */     int parWidth = width;
/*  96 */     boolean scale = true;
/*     */     
/*  98 */     int[][] mipmapDatas = new int[mipmapDimensions.length][];
/*     */     
/* 100 */     for (int i = 0; i < mipmapDimensions.length; i++) {
/*     */       
/* 102 */       Dimension dim = mipmapDimensions[i];
/* 103 */       int mipWidth = dim.width;
/* 104 */       int mipHeight = dim.height;
/*     */       
/* 106 */       int[] mipData = new int[mipWidth * mipHeight];
/* 107 */       mipmapDatas[i] = mipData;
/*     */       
/* 109 */       int level = i + 1;
/*     */       
/* 111 */       if (scale)
/*     */       {
/* 113 */         for (int mipX = 0; mipX < mipWidth; mipX++) {
/*     */           
/* 115 */           for (int mipY = 0; mipY < mipHeight; mipY++) {
/*     */             
/* 117 */             int p1 = parMipData[mipX * 2 + 0 + (mipY * 2 + 0) * parWidth];
/* 118 */             int p2 = parMipData[mipX * 2 + 1 + (mipY * 2 + 0) * parWidth];
/* 119 */             int p3 = parMipData[mipX * 2 + 1 + (mipY * 2 + 1) * parWidth];
/* 120 */             int p4 = parMipData[mipX * 2 + 0 + (mipY * 2 + 1) * parWidth];
/* 121 */             int pixel = alphaBlend(p1, p2, p3, p4);
/* 122 */             mipData[mipX + mipY * mipWidth] = pixel;
/*     */           } 
/*     */         } 
/*     */       }
/*     */ 
/*     */       
/* 128 */       parMipData = mipData;
/* 129 */       parWidth = mipWidth;
/*     */       
/* 131 */       if (mipWidth <= 1 || mipHeight <= 1) {
/* 132 */         scale = false;
/*     */       }
/*     */     } 
/* 135 */     return mipmapDatas;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int alphaBlend(int c1, int c2, int c3, int c4) {
/* 142 */     int cx1 = alphaBlend(c1, c2);
/* 143 */     int cx2 = alphaBlend(c3, c4);
/* 144 */     int cx = alphaBlend(cx1, cx2);
/* 145 */     return cx;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int alphaBlend(int c1, int c2) {
/* 152 */     int a1 = (c1 & 0xFF000000) >> 24 & 0xFF;
/* 153 */     int a2 = (c2 & 0xFF000000) >> 24 & 0xFF;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 159 */     int ax = (a1 + a2) / 2;
/*     */     
/* 161 */     if (a1 == 0 && a2 == 0) {
/*     */       
/* 163 */       a1 = 1;
/* 164 */       a2 = 1;
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 169 */       if (a1 == 0) {
/*     */         
/* 171 */         c1 = c2;
/* 172 */         ax /= 2;
/*     */       } 
/*     */       
/* 175 */       if (a2 == 0) {
/*     */         
/* 177 */         c2 = c1;
/* 178 */         ax /= 2;
/*     */       } 
/*     */     } 
/*     */     
/* 182 */     int r1 = (c1 >> 16 & 0xFF) * a1;
/* 183 */     int g1 = (c1 >> 8 & 0xFF) * a1;
/* 184 */     int b1 = (c1 & 0xFF) * a1;
/*     */     
/* 186 */     int r2 = (c2 >> 16 & 0xFF) * a2;
/* 187 */     int g2 = (c2 >> 8 & 0xFF) * a2;
/* 188 */     int b2 = (c2 & 0xFF) * a2;
/*     */     
/* 190 */     int rx = (r1 + r2) / (a1 + a2);
/* 191 */     int gx = (g1 + g2) / (a1 + a2);
/* 192 */     int bx = (b1 + b2) / (a1 + a2);
/*     */     
/* 194 */     return ax << 24 | rx << 16 | gx << 8 | bx;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int averageColor(int i, int j) {
/* 202 */     int k = (i & 0xFF000000) >> 24 & 0xFF;
/* 203 */     int l = (j & 0xFF000000) >> 24 & 0xFF;
/* 204 */     return (k + l >> 1 << 24) + ((i & 0xFEFEFE) + (j & 0xFEFEFE) >> 1);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static IntBuffer[] makeMipmapBuffers(Dimension[] mipmapDimensions, int[][] mipmapDatas) {
/* 210 */     if (mipmapDimensions == null) {
/* 211 */       return null;
/*     */     }
/* 213 */     IntBuffer[] mipmapBuffers = new IntBuffer[mipmapDimensions.length];
/*     */     
/* 215 */     for (int i = 0; i < mipmapDimensions.length; i++) {
/*     */       
/* 217 */       Dimension dim = mipmapDimensions[i];
/* 218 */       int bufLen = dim.width * dim.height;
/* 219 */       IntBuffer buf = ban.f(bufLen);
/*     */       
/* 221 */       int[] data = mipmapDatas[i];
/* 222 */       buf.clear();
/* 223 */       buf.put(data);
/* 224 */       buf.clear();
/*     */       
/* 226 */       mipmapBuffers[i] = buf;
/*     */     } 
/*     */     
/* 229 */     return mipmapBuffers;
/*     */   }
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
/*     */ 
/*     */   
/*     */   public static void allocateMipmapTextures(int width, int height, String name) {
/* 276 */     Dimension[] dims = makeMipmapDimensions(width, height, name);
/* 277 */     for (int i = 0; i < dims.length; i++) {
/*     */       
/* 279 */       Dimension dim = dims[i];
/* 280 */       int mipWidth = dim.width;
/* 281 */       int mipHeight = dim.height;
/* 282 */       int level = i + 1;
/*     */       
/* 284 */       GL11.glTexImage2D(3553, level, 6408, mipWidth, mipHeight, 0, 32993, 33639, (IntBuffer)null);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\Mipmaps.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
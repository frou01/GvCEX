/*     */ package shadersmod.client;
/*     */ 
/*     */ import Config;
/*     */ import GlStateManager;
/*     */ import bao;
/*     */ import bpp;
/*     */ import bpq;
/*     */ import bpt;
/*     */ import bpv;
/*     */ import bpz;
/*     */ import bqd;
/*     */ import bqf;
/*     */ import bqh;
/*     */ import bqi;
/*     */ import bqw;
/*     */ import bqx;
/*     */ import bqy;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.IntBuffer;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.imageio.ImageIO;
/*     */ import org.lwjgl.BufferUtils;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ import shadersmod.common.SMCLog;
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
/*     */ public class ShadersTex
/*     */ {
/*     */   public static final int initialBufferSize = 1048576;
/*  51 */   public static ByteBuffer byteBuffer = BufferUtils.createByteBuffer(4194304);
/*  52 */   public static IntBuffer intBuffer = byteBuffer.asIntBuffer();
/*  53 */   public static int[] intArray = new int[1048576];
/*     */   public static final int defBaseTexColor = 0;
/*     */   public static final int defNormTexColor = -8421377;
/*     */   public static final int defSpecTexColor = 0;
/*  57 */   public static Map<Integer, MultiTexID> multiTexMap = new HashMap<Integer, MultiTexID>();
/*  58 */   public static bpz updatingTextureMap = null;
/*  59 */   public static bqd updatingSprite = null;
/*  60 */   public static MultiTexID updatingTex = null;
/*  61 */   public static MultiTexID boundTex = null;
/*  62 */   public static int updatingPage = 0;
/*  63 */   public static String iconName = null;
/*     */ 
/*     */   
/*     */   public static IntBuffer getIntBuffer(int size) {
/*  67 */     if (intBuffer.capacity() < size) {
/*     */       
/*  69 */       int bufferSize = roundUpPOT(size);
/*  70 */       byteBuffer = BufferUtils.createByteBuffer(bufferSize * 4);
/*  71 */       intBuffer = byteBuffer.asIntBuffer();
/*     */     } 
/*  73 */     return intBuffer;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static int[] getIntArray(int size) {
/*  79 */     if (intArray == null) {
/*  80 */       intArray = new int[1048576];
/*     */     }
/*  82 */     if (intArray.length < size) {
/*  83 */       intArray = new int[roundUpPOT(size)];
/*     */     }
/*  85 */     return intArray;
/*     */   }
/*     */ 
/*     */   
/*     */   public static int roundUpPOT(int x) {
/*  90 */     int i = x - 1;
/*  91 */     i |= i >> 1;
/*  92 */     i |= i >> 2;
/*  93 */     i |= i >> 4;
/*  94 */     i |= i >> 8;
/*  95 */     i |= i >> 16;
/*  96 */     return i + 1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static int log2(int x) {
/* 102 */     int log = 0;
/* 103 */     if ((x & 0xFFFF0000) != 0) {
/*     */       
/* 105 */       log += 16;
/* 106 */       x >>= 16;
/*     */     } 
/* 108 */     if ((x & 0xFF00) != 0) {
/*     */       
/* 110 */       log += 8;
/* 111 */       x >>= 8;
/*     */     } 
/* 113 */     if ((x & 0xF0) != 0) {
/*     */       
/* 115 */       log += 4;
/* 116 */       x >>= 4;
/*     */     } 
/* 118 */     if ((x & 0x6) != 0) {
/*     */       
/* 120 */       log += 2;
/* 121 */       x >>= 2;
/*     */     } 
/* 123 */     if ((x & 0x2) != 0)
/*     */     {
/* 125 */       log++;
/*     */     }
/* 127 */     return log;
/*     */   }
/*     */ 
/*     */   
/*     */   public static IntBuffer fillIntBuffer(int size, int value) {
/* 132 */     int[] aint = getIntArray(size);
/* 133 */     IntBuffer intBuf = getIntBuffer(size);
/* 134 */     Arrays.fill(intArray, 0, size, value);
/* 135 */     intBuffer.put(intArray, 0, size);
/* 136 */     return intBuffer;
/*     */   }
/*     */ 
/*     */   
/*     */   public static int[] createAIntImage(int size) {
/* 141 */     int[] aint = new int[size * 3];
/* 142 */     Arrays.fill(aint, 0, size, 0);
/* 143 */     Arrays.fill(aint, size, size * 2, -8421377);
/* 144 */     Arrays.fill(aint, size * 2, size * 3, 0);
/* 145 */     return aint;
/*     */   }
/*     */ 
/*     */   
/*     */   public static int[] createAIntImage(int size, int color) {
/* 150 */     int[] aint = new int[size * 3];
/* 151 */     Arrays.fill(aint, 0, size, color);
/* 152 */     Arrays.fill(aint, size, size * 2, -8421377);
/* 153 */     Arrays.fill(aint, size * 2, size * 3, 0);
/* 154 */     return aint;
/*     */   }
/*     */ 
/*     */   
/*     */   public static MultiTexID getMultiTexID(bpp tex) {
/* 159 */     MultiTexID multiTex = tex.multiTex;
/* 160 */     if (multiTex == null) {
/*     */       
/* 162 */       int baseTex = tex.b();
/* 163 */       multiTex = multiTexMap.get(Integer.valueOf(baseTex));
/* 164 */       if (multiTex == null) {
/*     */         
/* 166 */         multiTex = new MultiTexID(baseTex, GL11.glGenTextures(), GL11.glGenTextures());
/* 167 */         multiTexMap.put(Integer.valueOf(baseTex), multiTex);
/*     */       } 
/* 169 */       tex.multiTex = multiTex;
/*     */     } 
/* 171 */     return multiTex;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void deleteTextures(bpp atex, int texid) {
/* 176 */     MultiTexID multiTex = atex.multiTex;
/* 177 */     if (multiTex != null) {
/*     */       
/* 179 */       atex.multiTex = null;
/* 180 */       multiTexMap.remove(Integer.valueOf(multiTex.base));
/* 181 */       GlStateManager.deleteTexture(multiTex.norm);
/* 182 */       GlStateManager.deleteTexture(multiTex.spec);
/* 183 */       if (multiTex.base != texid) {
/*     */         
/* 185 */         SMCLog.warning("Error : MultiTexID.base mismatch: " + multiTex.base + ", texid: " + texid);
/* 186 */         GlStateManager.deleteTexture(multiTex.base);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void bindNSTextures(int normTex, int specTex) {
/* 194 */     if (Shaders.isRenderingWorld && GlStateManager.getActiveTextureUnit() == 33984) {
/*     */       
/* 196 */       GlStateManager.setActiveTexture(33986);
/* 197 */       GlStateManager.bindTexture(normTex);
/* 198 */       GlStateManager.setActiveTexture(33987);
/* 199 */       GlStateManager.bindTexture(specTex);
/* 200 */       GlStateManager.setActiveTexture(33984);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void bindNSTextures(MultiTexID multiTex) {
/* 207 */     bindNSTextures(multiTex.norm, multiTex.spec);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void bindTextures(int baseTex, int normTex, int specTex) {
/* 213 */     if (Shaders.isRenderingWorld && GlStateManager.getActiveTextureUnit() == 33984) {
/*     */       
/* 215 */       GlStateManager.setActiveTexture(33986);
/* 216 */       GlStateManager.bindTexture(normTex);
/* 217 */       GlStateManager.setActiveTexture(33987);
/* 218 */       GlStateManager.bindTexture(specTex);
/* 219 */       GlStateManager.setActiveTexture(33984);
/*     */     } 
/*     */     
/* 222 */     GlStateManager.bindTexture(baseTex);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void bindTextures(MultiTexID multiTex) {
/* 228 */     boundTex = multiTex;
/* 229 */     if (Shaders.isRenderingWorld && GlStateManager.getActiveTextureUnit() == 33984) {
/*     */       
/* 231 */       if (Shaders.configNormalMap) {
/*     */         
/* 233 */         GlStateManager.setActiveTexture(33986);
/* 234 */         GlStateManager.bindTexture(multiTex.norm);
/*     */       } 
/* 236 */       if (Shaders.configSpecularMap) {
/*     */         
/* 238 */         GlStateManager.setActiveTexture(33987);
/* 239 */         GlStateManager.bindTexture(multiTex.spec);
/*     */       } 
/* 241 */       GlStateManager.setActiveTexture(33984);
/*     */     } 
/*     */     
/* 244 */     GlStateManager.bindTexture(multiTex.base);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void bindTexture(bqh tex) {
/* 250 */     int texId = tex.b();
/*     */     
/* 252 */     if (tex instanceof bpz) {
/*     */       
/* 254 */       Shaders.atlasSizeX = ((bpz)tex).atlasWidth;
/* 255 */       Shaders.atlasSizeY = ((bpz)tex).atlasHeight;
/* 256 */       bindTextures(tex.getMultiTexID());
/*     */     }
/*     */     else {
/*     */       
/* 260 */       Shaders.atlasSizeX = 0;
/* 261 */       Shaders.atlasSizeY = 0;
/* 262 */       bindTextures(tex.getMultiTexID());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void bindTextureMapForUpdateAndRender(bqf tm, bqx resLoc) {
/* 268 */     bpz tex = (bpz)tm.b(resLoc);
/* 269 */     Shaders.atlasSizeX = tex.atlasWidth;
/* 270 */     Shaders.atlasSizeY = tex.atlasHeight;
/* 271 */     bindTextures(updatingTex = tex.getMultiTexID());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void bindTextures(int baseTex) {
/* 277 */     MultiTexID multiTex = multiTexMap.get(Integer.valueOf(baseTex));
/* 278 */     bindTextures(multiTex);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void initDynamicTexture(int texID, int width, int height, bpq tex) {
/* 284 */     MultiTexID multiTex = tex.getMultiTexID();
/*     */     
/* 286 */     int[] aint = tex.d();
/* 287 */     int size = width * height;
/* 288 */     Arrays.fill(aint, size, size * 2, -8421377);
/* 289 */     Arrays.fill(aint, size * 2, size * 3, 0);
/*     */ 
/*     */     
/* 292 */     bqi.a(multiTex.base, width, height);
/* 293 */     bqi.a(false, false);
/* 294 */     bqi.a(false);
/*     */     
/* 296 */     bqi.a(multiTex.norm, width, height);
/* 297 */     bqi.a(false, false);
/* 298 */     bqi.a(false);
/*     */     
/* 300 */     bqi.a(multiTex.spec, width, height);
/* 301 */     bqi.a(false, false);
/* 302 */     bqi.a(false);
/*     */ 
/*     */     
/* 305 */     GlStateManager.bindTexture(multiTex.base);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void updateDynamicTexture(int texID, int[] src, int width, int height, bpq tex) {
/* 310 */     MultiTexID multiTex = tex.getMultiTexID();
/*     */ 
/*     */     
/* 313 */     GlStateManager.bindTexture(multiTex.base);
/* 314 */     updateDynTexSubImage1(src, width, height, 0, 0, 0);
/*     */     
/* 316 */     GlStateManager.bindTexture(multiTex.norm);
/* 317 */     updateDynTexSubImage1(src, width, height, 0, 0, 1);
/*     */     
/* 319 */     GlStateManager.bindTexture(multiTex.spec);
/* 320 */     updateDynTexSubImage1(src, width, height, 0, 0, 2);
/*     */     
/* 322 */     GlStateManager.bindTexture(multiTex.base);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void updateDynTexSubImage1(int[] src, int width, int height, int posX, int posY, int page) {
/* 328 */     int size = width * height;
/*     */     
/* 330 */     IntBuffer intBuf = getIntBuffer(size);
/* 331 */     intBuf.clear();
/*     */     
/* 333 */     int offset = page * size;
/*     */     
/* 335 */     if (src.length < offset + size) {
/*     */       return;
/*     */     }
/* 338 */     intBuf.put(src, offset, size).position(0).limit(size);
/*     */     
/* 340 */     GL11.glTexSubImage2D(3553, 0, posX, posY, width, height, 32993, 33639, intBuf);
/* 341 */     intBuf.clear();
/*     */   }
/*     */ 
/*     */   
/*     */   public static bqh createDefaultTexture() {
/* 346 */     bpq tex = new bpq(1, 1);
/* 347 */     tex.d()[0] = -1;
/* 348 */     tex.a();
/* 349 */     return (bqh)tex;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void allocateTextureMap(int texID, int mipmapLevels, int width, int height, bpv stitcher, bpz tex) {
/* 355 */     SMCLog.info("allocateTextureMap " + mipmapLevels + " " + width + " " + height + " ");
/* 356 */     updatingTextureMap = tex;
/* 357 */     tex.atlasWidth = width;
/* 358 */     tex.atlasHeight = height;
/* 359 */     MultiTexID multiTex = getMultiTexID((bpp)tex);
/* 360 */     updatingTex = multiTex;
/* 361 */     bqi.allocateTextureImpl(multiTex.base, mipmapLevels, width, height);
/* 362 */     if (Shaders.configNormalMap)
/* 363 */       bqi.allocateTextureImpl(multiTex.norm, mipmapLevels, width, height); 
/* 364 */     if (Shaders.configSpecularMap)
/* 365 */       bqi.allocateTextureImpl(multiTex.spec, mipmapLevels, width, height); 
/* 366 */     GlStateManager.bindTexture(texID);
/*     */   }
/*     */ 
/*     */   
/*     */   public static bqd setSprite(bqd tas) {
/* 371 */     return updatingSprite = tas;
/*     */   }
/*     */ 
/*     */   
/*     */   public static String setIconName(String name) {
/* 376 */     return iconName = name;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void uploadTexSubForLoadAtlas(int[][] data, int width, int height, int xoffset, int yoffset, boolean linear, boolean clamp) {
/* 381 */     bqi.a(data, width, height, xoffset, yoffset, linear, clamp);
/* 382 */     boolean border = false;
/*     */ 
/*     */     
/* 385 */     if (Shaders.configNormalMap) {
/*     */       
/* 387 */       int[][] aaint = readImageAndMipmaps(iconName + "_n", width, height, data.length, border, -8421377);
/* 388 */       GlStateManager.bindTexture(updatingTex.norm);
/* 389 */       bqi.a(aaint, width, height, xoffset, yoffset, linear, clamp);
/*     */     } 
/*     */     
/* 392 */     if (Shaders.configSpecularMap) {
/*     */       
/* 394 */       int[][] aaint = readImageAndMipmaps(iconName + "_s", width, height, data.length, border, 0);
/* 395 */       GlStateManager.bindTexture(updatingTex.spec);
/* 396 */       bqi.a(aaint, width, height, xoffset, yoffset, linear, clamp);
/*     */     } 
/*     */     
/* 399 */     GlStateManager.bindTexture(updatingTex.base);
/*     */   }
/*     */ 
/*     */   
/*     */   public static int[][] readImageAndMipmaps(String name, int width, int height, int numLevels, boolean border, int defColor) {
/* 404 */     int[][] aaint = new int[numLevels][];
/*     */     
/* 406 */     int[] aint = new int[width * height];
/* 407 */     boolean goodImage = false;
/* 408 */     BufferedImage image = readImage(updatingTextureMap.a(new bqx(name), 0));
/* 409 */     if (image != null) {
/*     */       
/* 411 */       int imageWidth = image.getWidth();
/* 412 */       int imageHeight = image.getHeight();
/* 413 */       if (imageWidth + (border ? 16 : 0) == width) {
/*     */         
/* 415 */         goodImage = true;
/* 416 */         image.getRGB(0, 0, imageWidth, imageWidth, aint, 0, imageWidth);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 421 */     if (!goodImage)
/*     */     {
/* 423 */       Arrays.fill(aint, defColor);
/*     */     }
/* 425 */     GlStateManager.bindTexture(updatingTex.spec);
/* 426 */     aaint = genMipmapsSimple(aaint.length - 1, width, aaint);
/* 427 */     return aaint;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static BufferedImage readImage(bqx resLoc) {
/*     */     try {
/* 435 */       if (!Config.hasResource(resLoc)) {
/* 436 */         return null;
/*     */       }
/* 438 */       InputStream istr = Config.getResourceStream(resLoc);
/* 439 */       if (istr == null) {
/* 440 */         return null;
/*     */       }
/* 442 */       BufferedImage image = ImageIO.read(istr);
/*     */       
/* 444 */       istr.close();
/*     */       
/* 446 */       return image;
/*     */     }
/* 448 */     catch (IOException e) {
/*     */ 
/*     */       
/* 451 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static int[][] genMipmapsSimple(int maxLevel, int width, int[][] data) {
/* 458 */     for (int level = 1; level <= maxLevel; level++) {
/*     */       
/* 460 */       if (data[level] == null) {
/*     */         
/* 462 */         int cw = width >> level;
/* 463 */         int pw = cw * 2;
/* 464 */         int[] aintp = data[level - 1];
/* 465 */         int[] aintc = data[level] = new int[cw * cw];
/*     */         
/* 467 */         for (int y = 0; y < cw; y++) {
/*     */           
/* 469 */           for (int x = 0; x < cw; x++) {
/*     */             
/* 471 */             int ppos = y * 2 * pw + x * 2;
/* 472 */             aintc[y * cw + x] = blend4Simple(aintp[ppos], aintp[ppos + 1], aintp[ppos + pw], aintp[ppos + pw + 1]);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/* 477 */     return data;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void uploadTexSub(int[][] data, int width, int height, int xoffset, int yoffset, boolean linear, boolean clamp) {
/* 482 */     bqi.a(data, width, height, xoffset, yoffset, linear, clamp);
/*     */     
/* 484 */     if (Shaders.configNormalMap || Shaders.configSpecularMap) {
/*     */ 
/*     */       
/* 487 */       if (Shaders.configNormalMap) {
/*     */         
/* 489 */         GlStateManager.bindTexture(updatingTex.norm);
/* 490 */         uploadTexSub1(data, width, height, xoffset, yoffset, 1);
/*     */       } 
/*     */       
/* 493 */       if (Shaders.configSpecularMap) {
/*     */         
/* 495 */         GlStateManager.bindTexture(updatingTex.spec);
/* 496 */         uploadTexSub1(data, width, height, xoffset, yoffset, 2);
/*     */       } 
/*     */       
/* 499 */       GlStateManager.bindTexture(updatingTex.base);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void uploadTexSub1(int[][] src, int width, int height, int posX, int posY, int page) {
/* 506 */     int size = width * height;
/* 507 */     IntBuffer intBuf = getIntBuffer(size);
/* 508 */     int numLevel = src.length;
/* 509 */     int level = 0, lw = width, lh = height, px = posX, py = posY;
/* 510 */     for (; lw > 0 && lh > 0 && level < numLevel; 
/* 511 */       level++) {
/*     */       
/* 513 */       int lsize = lw * lh;
/* 514 */       int[] aint = src[level];
/* 515 */       intBuf.clear();
/* 516 */       if (aint.length >= lsize * (page + 1)) {
/*     */         
/* 518 */         intBuf.put(aint, lsize * page, lsize).position(0).limit(lsize);
/* 519 */         GL11.glTexSubImage2D(3553, level, px, py, lw, lh, 32993, 33639, intBuf);
/*     */       } 
/* 521 */       lw >>= 1;
/* 522 */       lh >>= 1;
/* 523 */       px >>= 1;
/* 524 */       py >>= 1;
/*     */     } 
/* 526 */     intBuf.clear();
/*     */   }
/*     */ 
/*     */   
/*     */   public static int blend4Alpha(int c0, int c1, int c2, int c3) {
/* 531 */     int dv, a0 = c0 >>> 24 & 0xFF;
/* 532 */     int a1 = c1 >>> 24 & 0xFF;
/* 533 */     int a2 = c2 >>> 24 & 0xFF;
/* 534 */     int a3 = c3 >>> 24 & 0xFF;
/* 535 */     int as = a0 + a1 + a2 + a3;
/* 536 */     int an = (as + 2) / 4;
/*     */ 
/*     */     
/* 539 */     if (as != 0) {
/*     */       
/* 541 */       dv = as;
/*     */     }
/*     */     else {
/*     */       
/* 545 */       dv = 4;
/* 546 */       a3 = a2 = a1 = a0 = 1;
/*     */     } 
/* 548 */     int frac = (dv + 1) / 2;
/*     */     
/* 550 */     int color = an << 24 | ((c0 >>> 16 & 0xFF) * a0 + (c1 >>> 16 & 0xFF) * a1 + (c2 >>> 16 & 0xFF) * a2 + (c3 >>> 16 & 0xFF) * a3 + frac) / dv << 16 | ((c0 >>> 8 & 0xFF) * a0 + (c1 >>> 8 & 0xFF) * a1 + (c2 >>> 8 & 0xFF) * a2 + (c3 >>> 8 & 0xFF) * a3 + frac) / dv << 8 | ((c0 >>> 0 & 0xFF) * a0 + (c1 >>> 0 & 0xFF) * a1 + (c2 >>> 0 & 0xFF) * a2 + (c3 >>> 0 & 0xFF) * a3 + frac) / dv << 0;
/*     */ 
/*     */ 
/*     */     
/* 554 */     return color;
/*     */   }
/*     */ 
/*     */   
/*     */   public static int blend4Simple(int c0, int c1, int c2, int c3) {
/* 559 */     int color = ((c0 >>> 24 & 0xFF) + (c1 >>> 24 & 0xFF) + (c2 >>> 24 & 0xFF) + (c3 >>> 24 & 0xFF) + 2) / 4 << 24 | ((c0 >>> 16 & 0xFF) + (c1 >>> 16 & 0xFF) + (c2 >>> 16 & 0xFF) + (c3 >>> 16 & 0xFF) + 2) / 4 << 16 | ((c0 >>> 8 & 0xFF) + (c1 >>> 8 & 0xFF) + (c2 >>> 8 & 0xFF) + (c3 >>> 8 & 0xFF) + 2) / 4 << 8 | ((c0 >>> 0 & 0xFF) + (c1 >>> 0 & 0xFF) + (c2 >>> 0 & 0xFF) + (c3 >>> 0 & 0xFF) + 2) / 4 << 0;
/*     */ 
/*     */ 
/*     */     
/* 563 */     return color;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void genMipmapAlpha(int[] aint, int offset, int width, int height) {
/* 568 */     int minwh = Math.min(width, height);
/*     */ 
/*     */     
/* 571 */     int w2 = width, w1 = w2;
/* 572 */     int h2 = height, h1 = h2;
/* 573 */     int o2 = offset, o1 = o2;
/*     */     
/* 575 */     o2 = offset;
/* 576 */     w2 = width;
/* 577 */     h2 = height;
/* 578 */     o1 = 0;
/* 579 */     w1 = 0;
/* 580 */     h1 = 0; int level;
/* 581 */     for (level = 0; w2 > 1 && h2 > 1; level++, w2 = w1, h2 = h1, o2 = o1) {
/*     */       
/* 583 */       o1 = o2 + w2 * h2;
/* 584 */       w1 = w2 / 2;
/* 585 */       h1 = h2 / 2;
/* 586 */       for (int y = 0; y < h1; y++) {
/*     */         
/* 588 */         int p1 = o1 + y * w1;
/* 589 */         int p2 = o2 + y * 2 * w2;
/* 590 */         for (int x = 0; x < w1; x++)
/*     */         {
/* 592 */           aint[p1 + x] = blend4Alpha(aint[p2 + x * 2], aint[p2 + x * 2 + 1], aint[p2 + w2 + x * 2], aint[p2 + w2 + x * 2 + 1]);
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 600 */     while (level > 0) {
/*     */       
/* 602 */       level--;
/* 603 */       w2 = width >> level;
/* 604 */       h2 = height >> level;
/* 605 */       o2 = o1 - w2 * h2;
/* 606 */       int p2 = o2;
/* 607 */       for (int y = 0; y < h2; y++) {
/*     */         
/* 609 */         for (int x = 0; x < w2; x++) {
/*     */ 
/*     */           
/* 612 */           if (aint[p2] == 0)
/*     */           {
/* 614 */             aint[p2] = aint[o1 + y / 2 * w1 + x / 2] & 0xFFFFFF;
/*     */           }
/* 616 */           p2++;
/*     */         } 
/*     */       } 
/* 619 */       o1 = o2;
/* 620 */       w1 = w2;
/* 621 */       h1 = h2;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void genMipmapSimple(int[] aint, int offset, int width, int height) {
/* 627 */     int minwh = Math.min(width, height);
/*     */ 
/*     */     
/* 630 */     int w2 = width, w1 = w2;
/* 631 */     int h2 = height, h1 = h2;
/* 632 */     int o2 = offset, o1 = o2;
/*     */     
/* 634 */     o2 = offset;
/* 635 */     w2 = width;
/* 636 */     h2 = height;
/* 637 */     o1 = 0;
/* 638 */     w1 = 0;
/* 639 */     h1 = 0; int level;
/* 640 */     for (level = 0; w2 > 1 && h2 > 1; level++, w2 = w1, h2 = h1, o2 = o1) {
/*     */       
/* 642 */       o1 = o2 + w2 * h2;
/* 643 */       w1 = w2 / 2;
/* 644 */       h1 = h2 / 2;
/* 645 */       for (int y = 0; y < h1; y++) {
/*     */         
/* 647 */         int p1 = o1 + y * w1;
/* 648 */         int p2 = o2 + y * 2 * w2;
/* 649 */         for (int x = 0; x < w1; x++)
/*     */         {
/* 651 */           aint[p1 + x] = blend4Simple(aint[p2 + x * 2], aint[p2 + x * 2 + 1], aint[p2 + w2 + x * 2], aint[p2 + w2 + x * 2 + 1]);
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 659 */     while (level > 0) {
/*     */       
/* 661 */       level--;
/* 662 */       w2 = width >> level;
/* 663 */       h2 = height >> level;
/* 664 */       o2 = o1 - w2 * h2;
/* 665 */       int p2 = o2;
/* 666 */       for (int y = 0; y < h2; y++) {
/*     */         
/* 668 */         for (int x = 0; x < w2; x++) {
/*     */ 
/*     */           
/* 671 */           if (aint[p2] == 0)
/*     */           {
/* 673 */             aint[p2] = aint[o1 + y / 2 * w1 + x / 2] & 0xFFFFFF;
/*     */           }
/* 675 */           p2++;
/*     */         } 
/*     */       } 
/* 678 */       o1 = o2;
/* 679 */       w1 = w2;
/* 680 */       h1 = h2;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isSemiTransparent(int[] aint, int width, int height) {
/* 686 */     int size = width * height;
/*     */     
/* 688 */     if (aint[0] >>> 24 == 255 && aint[size - 1] == 0)
/* 689 */       return true; 
/* 690 */     for (int i = 0; i < size; i++) {
/*     */       
/* 692 */       int alpha = aint[i] >>> 24;
/* 693 */       if (alpha != 0 && alpha != 255)
/* 694 */         return true; 
/*     */     } 
/* 696 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void updateSubTex1(int[] src, int width, int height, int posX, int posY) {
/* 706 */     int level = 0, cw = width, ch = height, cx = posX, cy = posY;
/* 707 */     for (; cw > 0 && ch > 0; 
/* 708 */       level++, cw /= 2, ch /= 2, cx /= 2, cy /= 2)
/*     */     {
/* 710 */       GL11.glCopyTexSubImage2D(3553, level, cx, cy, 0, 0, cw, ch);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static void setupTexture(MultiTexID multiTex, int[] src, int width, int height, boolean linear, boolean clamp) {
/* 716 */     int mmfilter = linear ? 9729 : 9728;
/* 717 */     int wraptype = clamp ? 10496 : 10497;
/* 718 */     int size = width * height;
/* 719 */     IntBuffer intBuf = getIntBuffer(size);
/*     */     
/* 721 */     intBuf.clear();
/* 722 */     intBuf.put(src, 0, size).position(0).limit(size);
/* 723 */     GlStateManager.bindTexture(multiTex.base);
/* 724 */     GL11.glTexImage2D(3553, 0, 6408, width, height, 0, 32993, 33639, intBuf);
/* 725 */     GL11.glTexParameteri(3553, 10241, mmfilter);
/* 726 */     GL11.glTexParameteri(3553, 10240, mmfilter);
/* 727 */     GL11.glTexParameteri(3553, 10242, wraptype);
/* 728 */     GL11.glTexParameteri(3553, 10243, wraptype);
/*     */     
/* 730 */     intBuf.put(src, size, size).position(0).limit(size);
/* 731 */     GlStateManager.bindTexture(multiTex.norm);
/* 732 */     GL11.glTexImage2D(3553, 0, 6408, width, height, 0, 32993, 33639, intBuf);
/* 733 */     GL11.glTexParameteri(3553, 10241, mmfilter);
/* 734 */     GL11.glTexParameteri(3553, 10240, mmfilter);
/* 735 */     GL11.glTexParameteri(3553, 10242, wraptype);
/* 736 */     GL11.glTexParameteri(3553, 10243, wraptype);
/*     */     
/* 738 */     intBuf.put(src, size * 2, size).position(0).limit(size);
/* 739 */     GlStateManager.bindTexture(multiTex.spec);
/* 740 */     GL11.glTexImage2D(3553, 0, 6408, width, height, 0, 32993, 33639, intBuf);
/* 741 */     GL11.glTexParameteri(3553, 10241, mmfilter);
/* 742 */     GL11.glTexParameteri(3553, 10240, mmfilter);
/* 743 */     GL11.glTexParameteri(3553, 10242, wraptype);
/* 744 */     GL11.glTexParameteri(3553, 10243, wraptype);
/*     */     
/* 746 */     GlStateManager.bindTexture(multiTex.base);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void updateSubImage(MultiTexID multiTex, int[] src, int width, int height, int posX, int posY, boolean linear, boolean clamp) {
/* 752 */     int size = width * height;
/* 753 */     IntBuffer intBuf = getIntBuffer(size);
/*     */     
/* 755 */     intBuf.clear();
/* 756 */     intBuf.put(src, 0, size);
/* 757 */     intBuf.position(0).limit(size);
/* 758 */     GlStateManager.bindTexture(multiTex.base);
/* 759 */     GL11.glTexParameteri(3553, 10241, 9728);
/* 760 */     GL11.glTexParameteri(3553, 10240, 9728);
/* 761 */     GL11.glTexParameteri(3553, 10242, 10497);
/* 762 */     GL11.glTexParameteri(3553, 10243, 10497);
/* 763 */     GL11.glTexSubImage2D(3553, 0, posX, posY, width, height, 32993, 33639, intBuf);
/* 764 */     if (src.length == size * 3) {
/*     */       
/* 766 */       intBuf.clear();
/* 767 */       intBuf.put(src, size, size).position(0);
/* 768 */       intBuf.position(0).limit(size);
/*     */     } 
/* 770 */     GlStateManager.bindTexture(multiTex.norm);
/* 771 */     GL11.glTexParameteri(3553, 10241, 9728);
/* 772 */     GL11.glTexParameteri(3553, 10240, 9728);
/* 773 */     GL11.glTexParameteri(3553, 10242, 10497);
/* 774 */     GL11.glTexParameteri(3553, 10243, 10497);
/* 775 */     GL11.glTexSubImage2D(3553, 0, posX, posY, width, height, 32993, 33639, intBuf);
/* 776 */     if (src.length == size * 3) {
/*     */       
/* 778 */       intBuf.clear();
/* 779 */       intBuf.put(src, size * 2, size);
/* 780 */       intBuf.position(0).limit(size);
/*     */     } 
/* 782 */     GlStateManager.bindTexture(multiTex.spec);
/* 783 */     GL11.glTexParameteri(3553, 10241, 9728);
/* 784 */     GL11.glTexParameteri(3553, 10240, 9728);
/* 785 */     GL11.glTexParameteri(3553, 10242, 10497);
/* 786 */     GL11.glTexParameteri(3553, 10243, 10497);
/* 787 */     GL11.glTexSubImage2D(3553, 0, posX, posY, width, height, 32993, 33639, intBuf);
/* 788 */     GlStateManager.setActiveTexture(33984);
/*     */   }
/*     */ 
/*     */   
/*     */   public static bqx getNSMapLocation(bqx location, String mapName) {
/* 793 */     String basename = location.a();
/* 794 */     String[] basenameParts = basename.split(".png");
/* 795 */     String basenameNoFileType = basenameParts[0];
/* 796 */     return new bqx(location.b(), basenameNoFileType + "_" + mapName + ".png");
/*     */   }
/*     */ 
/*     */   
/*     */   public static void loadNSMap(bqy manager, bqx location, int width, int height, int[] aint) {
/* 801 */     if (Shaders.configNormalMap)
/* 802 */       loadNSMap1(manager, getNSMapLocation(location, "n"), width, height, aint, width * height, -8421377); 
/* 803 */     if (Shaders.configSpecularMap) {
/* 804 */       loadNSMap1(manager, getNSMapLocation(location, "s"), width, height, aint, width * height * 2, 0);
/*     */     }
/*     */   }
/*     */   
/*     */   public static void loadNSMap1(bqy manager, bqx location, int width, int height, int[] aint, int offset, int defaultColor) {
/* 809 */     boolean good = false;
/*     */     
/*     */     try {
/* 812 */       bqw res = manager.a(location);
/* 813 */       BufferedImage bufferedimage = ImageIO.read(res.b());
/* 814 */       if (bufferedimage != null && bufferedimage.getWidth() == width && bufferedimage.getHeight() == height)
/*     */       {
/* 816 */         bufferedimage.getRGB(0, 0, width, height, aint, offset, width);
/* 817 */         good = true;
/*     */       }
/*     */     
/* 820 */     } catch (IOException ex) {}
/*     */ 
/*     */     
/* 823 */     if (!good)
/*     */     {
/* 825 */       Arrays.fill(aint, offset, offset + width * height, defaultColor);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int loadSimpleTexture(int textureID, BufferedImage bufferedimage, boolean linear, boolean clamp, bqy resourceManager, bqx location, MultiTexID multiTex) {
/* 835 */     int width = bufferedimage.getWidth();
/* 836 */     int height = bufferedimage.getHeight();
/* 837 */     int size = width * height;
/* 838 */     int[] aint = getIntArray(size * 3);
/* 839 */     bufferedimage.getRGB(0, 0, width, height, aint, 0, width);
/* 840 */     loadNSMap(resourceManager, location, width, height, aint);
/* 841 */     setupTexture(multiTex, aint, width, height, linear, clamp);
/* 842 */     return textureID;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void mergeImage(int[] aint, int dstoff, int srcoff, int size) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public static int blendColor(int color1, int color2, int factor1) {
/* 852 */     int factor2 = 255 - factor1;
/* 853 */     return ((color1 >>> 24 & 0xFF) * factor1 + (color2 >>> 24 & 0xFF) * factor2) / 255 << 24 | ((color1 >>> 16 & 0xFF) * factor1 + (color2 >>> 16 & 0xFF) * factor2) / 255 << 16 | ((color1 >>> 8 & 0xFF) * factor1 + (color2 >>> 8 & 0xFF) * factor2) / 255 << 8 | ((color1 >>> 0 & 0xFF) * factor1 + (color2 >>> 0 & 0xFF) * factor2) / 255 << 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void loadLayeredTexture(bpt tex, bqy manager, List<String> list) {
/* 861 */     int width = 0;
/* 862 */     int height = 0;
/* 863 */     int size = 0;
/* 864 */     int[] image = null;
/*     */     
/* 866 */     for (Iterator<String> iterator = list.iterator(); iterator.hasNext(); ) {
/*     */       
/* 868 */       String s = iterator.next();
/* 869 */       if (s != null) {
/*     */         
/*     */         try {
/*     */           
/* 873 */           bqx location = new bqx(s);
/* 874 */           InputStream inputstream = manager.a(location).b();
/* 875 */           BufferedImage bufimg = ImageIO.read(inputstream);
/*     */           
/* 877 */           if (size == 0) {
/*     */             
/* 879 */             width = bufimg.getWidth();
/* 880 */             height = bufimg.getHeight();
/* 881 */             size = width * height;
/* 882 */             image = createAIntImage(size, 0);
/*     */           } 
/* 884 */           int[] aint = getIntArray(size * 3);
/* 885 */           bufimg.getRGB(0, 0, width, height, aint, 0, width);
/* 886 */           loadNSMap(manager, location, width, height, aint);
/*     */           
/* 888 */           for (int i = 0; i < size; i++)
/*     */           {
/* 890 */             int alpha = aint[i] >>> 24 & 0xFF;
/* 891 */             image[size * 0 + i] = blendColor(aint[size * 0 + i], image[size * 0 + i], alpha);
/* 892 */             image[size * 1 + i] = blendColor(aint[size * 1 + i], image[size * 1 + i], alpha);
/* 893 */             image[size * 2 + i] = blendColor(aint[size * 2 + i], image[size * 2 + i], alpha);
/*     */           }
/*     */         
/* 896 */         } catch (IOException ex) {
/*     */           
/* 898 */           ex.printStackTrace();
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/* 903 */     setupTexture(tex.getMultiTexID(), image, width, height, false, false);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static void updateTextureMinMagFilter() {
/* 909 */     bqf texman = bao.B().P();
/* 910 */     bqh texObj = texman.b(bpz.b);
/* 911 */     if (texObj != null) {
/*     */       
/* 913 */       MultiTexID multiTex = texObj.getMultiTexID();
/*     */       
/* 915 */       GlStateManager.bindTexture(multiTex.base);
/* 916 */       GL11.glTexParameteri(3553, 10241, Shaders.texMinFilValue[Shaders.configTexMinFilB]);
/* 917 */       GL11.glTexParameteri(3553, 10240, Shaders.texMagFilValue[Shaders.configTexMagFilB]);
/*     */       
/* 919 */       GlStateManager.bindTexture(multiTex.norm);
/* 920 */       GL11.glTexParameteri(3553, 10241, Shaders.texMinFilValue[Shaders.configTexMinFilN]);
/* 921 */       GL11.glTexParameteri(3553, 10240, Shaders.texMagFilValue[Shaders.configTexMagFilN]);
/*     */ 
/*     */       
/* 924 */       GlStateManager.bindTexture(multiTex.spec);
/* 925 */       GL11.glTexParameteri(3553, 10241, Shaders.texMinFilValue[Shaders.configTexMinFilS]);
/* 926 */       GL11.glTexParameteri(3553, 10240, Shaders.texMagFilValue[Shaders.configTexMagFilS]);
/*     */       
/* 928 */       GlStateManager.bindTexture(0);
/*     */     } 
/*     */   }
/*     */   
/* 932 */   public static bqy resManager = null;
/* 933 */   static bqx resLocation = null;
/* 934 */   static int imageSize = 0;
/*     */ 
/*     */   
/*     */   public static bqw loadResource(bqy manager, bqx location) throws IOException {
/* 938 */     resManager = manager;
/* 939 */     resLocation = location;
/* 940 */     return manager.a(location);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static int[] loadAtlasSprite(BufferedImage bufferedimage, int startX, int startY, int w, int h, int[] aint, int offset, int scansize) {
/* 946 */     imageSize = w * h;
/* 947 */     bufferedimage.getRGB(startX, startY, w, h, aint, offset, scansize);
/* 948 */     loadNSMap(resManager, resLocation, w, h, aint);
/* 949 */     return aint;
/*     */   }
/*     */ 
/*     */   
/*     */   public static int[][] getFrameTexData(int[][] src, int width, int height, int frameIndex) {
/* 954 */     int numLevel = src.length;
/* 955 */     int[][] dst = new int[numLevel][];
/* 956 */     for (int level = 0; level < numLevel; level++) {
/*     */       
/* 958 */       int[] sr1 = src[level];
/* 959 */       if (sr1 != null) {
/*     */         
/* 961 */         int frameSize = (width >> level) * (height >> level);
/* 962 */         int[] ds1 = new int[frameSize * 3];
/* 963 */         dst[level] = ds1;
/* 964 */         int srcSize = sr1.length / 3;
/* 965 */         int srcPos = frameSize * frameIndex;
/* 966 */         int dstPos = 0;
/* 967 */         System.arraycopy(sr1, srcPos, ds1, dstPos, frameSize);
/* 968 */         srcPos += srcSize;
/* 969 */         dstPos += frameSize;
/* 970 */         System.arraycopy(sr1, srcPos, ds1, dstPos, frameSize);
/* 971 */         srcPos += srcSize;
/* 972 */         dstPos += frameSize;
/* 973 */         System.arraycopy(sr1, srcPos, ds1, dstPos, frameSize);
/*     */       } 
/*     */     } 
/* 976 */     return dst;
/*     */   }
/*     */ 
/*     */   
/*     */   public static int[][] prepareAF(bqd tas, int[][] src, int width, int height) {
/* 981 */     boolean skip = true;
/*     */ 
/*     */     
/* 984 */     return src;
/*     */   }
/*     */   
/*     */   public static void fixTransparentColor(bqd tas, int[] aint) {}
/*     */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\shadersmod\client\ShadersTex.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
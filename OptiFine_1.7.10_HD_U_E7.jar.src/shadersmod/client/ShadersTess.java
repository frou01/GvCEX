/*     */ package shadersmod.client;
/*     */ 
/*     */ import bmh;
/*     */ import buu;
/*     */ import java.nio.FloatBuffer;
/*     */ import java.nio.ShortBuffer;
/*     */ import java.util.Arrays;
/*     */ import org.lwjgl.opengl.ARBVertexShader;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ import org.lwjgl.opengl.GL13;
/*     */ import shadersmod.common.SMCLog;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ShadersTess
/*     */ {
/*     */   public static final int vertexStride = 18;
/*     */   public float midTextureU;
/*     */   public float midTextureV;
/*     */   public float normalX;
/*     */   public float normalY;
/*     */   public float normalZ;
/*     */   public float v0x;
/*     */   public float v0y;
/*     */   public float v0z;
/*     */   public float v0u;
/*     */   public float v0v;
/*     */   public float v1x;
/*     */   public float v1y;
/*     */   public float v1z;
/*     */   public float v1u;
/*     */   public float v1v;
/*     */   public float v2x;
/*     */   public float v2y;
/*     */   public float v2z;
/*     */   public float v2u;
/*     */   public float v2v;
/*     */   public float v3x;
/*     */   public float v3y;
/*     */   public float v3z;
/*     */   public float v3u;
/*     */   public float v3v;
/*     */   
/*     */   public static int draw(bmh tess) {
/*  47 */     if (!tess.x)
/*     */     {
/*  49 */       throw new IllegalStateException("Not tesselating!");
/*     */     }
/*     */ 
/*     */     
/*  53 */     tess.x = false;
/*  54 */     if (tess.s == 7 && tess.g % 4 != 0)
/*     */     {
/*  56 */       SMCLog.warning("%s", new Object[] { "bad vertexCount" });
/*     */     }
/*     */     
/*  59 */     int voffset = 0;
/*  60 */     int realDrawMode = tess.s;
/*     */     
/*  62 */     while (voffset < tess.g) {
/*     */       
/*  64 */       int vcount = Math.min(tess.g - voffset, tess.b.capacity() / 72);
/*  65 */       if (realDrawMode == 7)
/*     */       {
/*  67 */         vcount = vcount / 4 * 4;
/*     */       }
/*     */       
/*  70 */       tess.d.clear();
/*  71 */       tess.e.clear();
/*  72 */       tess.c.clear();
/*  73 */       tess.c.put(tess.f, voffset * 18, vcount * 18);
/*  74 */       tess.b.position(0);
/*  75 */       tess.b.limit(vcount * 72);
/*  76 */       voffset += vcount;
/*  77 */       if (tess.m) {
/*     */         
/*  79 */         tess.d.position(3);
/*  80 */         GL11.glTexCoordPointer(2, 72, tess.d);
/*  81 */         GL11.glEnableClientState(32888);
/*     */       } 
/*     */       
/*  84 */       if (tess.n) {
/*     */         
/*  86 */         buu.k(buu.c);
/*  87 */         tess.e.position(12);
/*  88 */         GL11.glTexCoordPointer(2, 72, tess.e);
/*  89 */         GL11.glEnableClientState(32888);
/*  90 */         buu.k(buu.b);
/*     */       } 
/*     */       
/*  93 */       if (tess.l) {
/*     */         
/*  95 */         tess.b.position(20);
/*  96 */         GL11.glColorPointer(4, true, 72, tess.b);
/*  97 */         GL11.glEnableClientState(32886);
/*     */       } 
/*     */       
/* 100 */       if (tess.o) {
/*     */         
/* 102 */         tess.d.position(9);
/* 103 */         GL11.glNormalPointer(72, tess.d);
/* 104 */         GL11.glEnableClientState(32885);
/*     */       } 
/*     */       
/* 107 */       tess.d.position(0);
/* 108 */       GL11.glVertexPointer(3, 72, tess.d);
/* 109 */       preDrawArray(tess);
/* 110 */       GL11.glEnableClientState(32884);
/* 111 */       GL11.glDrawArrays(realDrawMode, 0, vcount);
/*     */     } 
/*     */     
/* 114 */     GL11.glDisableClientState(32884);
/* 115 */     postDrawArray(tess);
/* 116 */     if (tess.m)
/*     */     {
/* 118 */       GL11.glDisableClientState(32888);
/*     */     }
/*     */     
/* 121 */     if (tess.n) {
/*     */       
/* 123 */       buu.k(buu.c);
/* 124 */       GL11.glDisableClientState(32888);
/* 125 */       buu.k(buu.b);
/*     */     } 
/*     */     
/* 128 */     if (tess.l)
/*     */     {
/* 130 */       GL11.glDisableClientState(32886);
/*     */     }
/*     */     
/* 133 */     if (tess.o)
/*     */     {
/* 135 */       GL11.glDisableClientState(32885);
/*     */     }
/*     */     
/* 138 */     int n = tess.p * 4;
/* 139 */     tess.d();
/* 140 */     return n;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void preDrawArray(bmh tess) {
/* 146 */     if (Shaders.useMultiTexCoord3Attrib && tess.m) {
/*     */       
/* 148 */       GL13.glClientActiveTexture(33987);
/* 149 */       GL11.glTexCoordPointer(2, 72, (FloatBuffer)tess.d.position(16));
/* 150 */       GL11.glEnableClientState(32888);
/* 151 */       GL13.glClientActiveTexture(33984);
/*     */     } 
/*     */     
/* 154 */     if (Shaders.useMidTexCoordAttrib && tess.m) {
/*     */       
/* 156 */       ARBVertexShader.glVertexAttribPointerARB(Shaders.midTexCoordAttrib, 2, false, 72, (FloatBuffer)tess.d.position(16));
/* 157 */       ARBVertexShader.glEnableVertexAttribArrayARB(Shaders.midTexCoordAttrib);
/*     */     } 
/*     */     
/* 160 */     if (Shaders.useTangentAttrib && tess.m) {
/*     */       
/* 162 */       ARBVertexShader.glVertexAttribPointerARB(Shaders.tangentAttrib, 4, false, 72, (FloatBuffer)tess.d.position(12));
/* 163 */       ARBVertexShader.glEnableVertexAttribArrayARB(Shaders.tangentAttrib);
/*     */     } 
/*     */     
/* 166 */     if (Shaders.useEntityAttrib) {
/*     */       
/* 168 */       ARBVertexShader.glVertexAttribPointerARB(Shaders.entityAttrib, 3, false, false, 72, (ShortBuffer)tess.e.position(14));
/* 169 */       ARBVertexShader.glEnableVertexAttribArrayARB(Shaders.entityAttrib);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void preDrawArrayVBO(bmh tess) {
/* 176 */     if (Shaders.useMultiTexCoord3Attrib && tess.m) {
/*     */       
/* 178 */       GL13.glClientActiveTexture(33987);
/* 179 */       GL11.glTexCoordPointer(2, 5126, 72, 64L);
/* 180 */       GL11.glEnableClientState(32888);
/* 181 */       GL13.glClientActiveTexture(33984);
/*     */     } 
/*     */     
/* 184 */     if (Shaders.useMidTexCoordAttrib && tess.m) {
/*     */       
/* 186 */       ARBVertexShader.glVertexAttribPointerARB(Shaders.midTexCoordAttrib, 2, 5126, false, 72, 64L);
/* 187 */       ARBVertexShader.glEnableVertexAttribArrayARB(Shaders.midTexCoordAttrib);
/*     */     } 
/*     */     
/* 190 */     if (Shaders.useTangentAttrib && tess.m) {
/*     */       
/* 192 */       ARBVertexShader.glVertexAttribPointerARB(Shaders.tangentAttrib, 4, 5126, false, 72, 48L);
/* 193 */       ARBVertexShader.glEnableVertexAttribArrayARB(Shaders.tangentAttrib);
/*     */     } 
/*     */     
/* 196 */     if (Shaders.useEntityAttrib) {
/*     */       
/* 198 */       ARBVertexShader.glVertexAttribPointerARB(Shaders.entityAttrib, 3, 5122, false, 72, 28L);
/* 199 */       ARBVertexShader.glEnableVertexAttribArrayARB(Shaders.entityAttrib);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void postDrawArray(bmh tess) {
/* 206 */     if (Shaders.useEntityAttrib)
/*     */     {
/* 208 */       ARBVertexShader.glDisableVertexAttribArrayARB(Shaders.entityAttrib);
/*     */     }
/*     */     
/* 211 */     if (Shaders.useMidTexCoordAttrib && tess.m)
/*     */     {
/* 213 */       ARBVertexShader.glDisableVertexAttribArrayARB(Shaders.midTexCoordAttrib);
/*     */     }
/*     */     
/* 216 */     if (Shaders.useTangentAttrib && tess.m)
/*     */     {
/* 218 */       ARBVertexShader.glDisableVertexAttribArrayARB(Shaders.tangentAttrib);
/*     */     }
/*     */     
/* 221 */     if (Shaders.useMultiTexCoord3Attrib && tess.m) {
/*     */       
/* 223 */       GL13.glClientActiveTexture(33987);
/* 224 */       GL11.glDisableClientState(32888);
/* 225 */       GL13.glClientActiveTexture(33984);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void addVertex(bmh tess, double parx, double pary, double parz) {
/* 232 */     ShadersTess stess = tess.shadersTess;
/* 233 */     int[] rawBuffer = tess.f;
/* 234 */     int rbi = tess.p;
/* 235 */     float fx = (float)(parx + tess.t);
/* 236 */     float fy = (float)(pary + tess.u);
/* 237 */     float fz = (float)(parz + tess.v);
/* 238 */     if (rbi >= tess.y - 72)
/*     */     {
/* 240 */       if (tess.y >= 16777216) {
/*     */         
/* 242 */         if (tess.q % 4 == 0)
/*     */         {
/* 244 */           tess.a();
/* 245 */           tess.x = true;
/*     */         }
/*     */       
/* 248 */       } else if (tess.y > 0) {
/*     */         
/* 250 */         tess.y *= 2;
/* 251 */         tess.f = rawBuffer = Arrays.copyOf(tess.f, tess.y);
/* 252 */         SMCLog.info("Expand tesselator buffer %d", new Object[] { Integer.valueOf(tess.y) });
/*     */       }
/*     */       else {
/*     */         
/* 256 */         tess.y = 65536;
/* 257 */         tess.f = rawBuffer = new int[tess.y];
/*     */       } 
/*     */     }
/*     */     
/* 261 */     if (tess.s == 7) {
/*     */       float x1, y1, z1, x2, y2, z2, vnx, vny, vnz, lensq, mult, u1, v1, u2, v2, d, r, tan1x, tan1y, tan1z, tan2x, tan2y, tan2z, tan3x, tan3y, tan3z, tan1w;
/* 263 */       int i = tess.q % 4;
/* 264 */       switch (i) {
/*     */         
/*     */         case 0:
/* 267 */           stess.v0x = fx;
/* 268 */           stess.v0y = fy;
/* 269 */           stess.v0z = fz;
/* 270 */           stess.v0u = (float)tess.h;
/* 271 */           stess.v0v = (float)tess.i;
/*     */           break;
/*     */         case 1:
/* 274 */           stess.v1x = fx;
/* 275 */           stess.v1y = fy;
/* 276 */           stess.v1z = fz;
/* 277 */           stess.v1u = (float)tess.h;
/* 278 */           stess.v1v = (float)tess.i;
/*     */           break;
/*     */         case 2:
/* 281 */           stess.v2x = fx;
/* 282 */           stess.v2y = fy;
/* 283 */           stess.v2z = fz;
/* 284 */           stess.v2u = (float)tess.h;
/* 285 */           stess.v2v = (float)tess.i;
/*     */           break;
/*     */         case 3:
/* 288 */           stess.v3x = fx;
/* 289 */           stess.v3y = fy;
/* 290 */           stess.v3z = fz;
/* 291 */           stess.v3u = (float)tess.h;
/* 292 */           stess.v3v = (float)tess.i;
/* 293 */           x1 = stess.v2x - stess.v0x;
/* 294 */           y1 = stess.v2y - stess.v0y;
/* 295 */           z1 = stess.v2z - stess.v0z;
/* 296 */           x2 = stess.v3x - stess.v1x;
/* 297 */           y2 = stess.v3y - stess.v1y;
/* 298 */           z2 = stess.v3z - stess.v1z;
/* 299 */           vnx = y1 * z2 - y2 * z1;
/* 300 */           vny = z1 * x2 - z2 * x1;
/* 301 */           vnz = x1 * y2 - x2 * y1;
/* 302 */           lensq = vnx * vnx + vny * vny + vnz * vnz;
/* 303 */           mult = (lensq != 0.0D) ? (float)(1.0D / Math.sqrt(lensq)) : 1.0F;
/* 304 */           vnx *= mult;
/* 305 */           vny *= mult;
/* 306 */           vnz *= mult;
/* 307 */           tess.o = true;
/* 308 */           x1 = stess.v1x - stess.v0x;
/* 309 */           y1 = stess.v1y - stess.v0y;
/* 310 */           z1 = stess.v1z - stess.v0z;
/* 311 */           u1 = stess.v1u - stess.v0u;
/* 312 */           v1 = stess.v1v - stess.v0v;
/* 313 */           x2 = stess.v2x - stess.v0x;
/* 314 */           y2 = stess.v2y - stess.v0y;
/* 315 */           z2 = stess.v2z - stess.v0z;
/* 316 */           u2 = stess.v2u - stess.v0u;
/* 317 */           v2 = stess.v2v - stess.v0v;
/* 318 */           d = u1 * v2 - u2 * v1;
/* 319 */           r = (d != 0.0F) ? (1.0F / d) : 1.0F;
/* 320 */           tan1x = (v2 * x1 - v1 * x2) * r;
/* 321 */           tan1y = (v2 * y1 - v1 * y2) * r;
/* 322 */           tan1z = (v2 * z1 - v1 * z2) * r;
/* 323 */           tan2x = (u1 * x2 - u2 * x1) * r;
/* 324 */           tan2y = (u1 * y2 - u2 * y1) * r;
/* 325 */           tan2z = (u1 * z2 - u2 * z1) * r;
/* 326 */           lensq = tan1x * tan1x + tan1y * tan1y + tan1z * tan1z;
/* 327 */           mult = (lensq != 0.0D) ? (float)(1.0D / Math.sqrt(lensq)) : 1.0F;
/* 328 */           tan1x *= mult;
/* 329 */           tan1y *= mult;
/* 330 */           tan1z *= mult;
/* 331 */           lensq = tan2x * tan2x + tan2y * tan2y + tan2z * tan2z;
/* 332 */           mult = (lensq != 0.0D) ? (float)(1.0D / Math.sqrt(lensq)) : 1.0F;
/* 333 */           tan2x *= mult;
/* 334 */           tan2y *= mult;
/* 335 */           tan2z *= mult;
/* 336 */           tan3x = vnz * tan1y - vny * tan1z;
/* 337 */           tan3y = vnx * tan1z - vnz * tan1x;
/* 338 */           tan3z = vny * tan1x - vnx * tan1y;
/* 339 */           tan1w = (tan2x * tan3x + tan2y * tan3y + tan2z * tan3z < 0.0F) ? -1.0F : 1.0F;
/* 340 */           rawBuffer[rbi + 9] = Float.floatToRawIntBits(stess.normalX = vnx); rawBuffer[rbi + -9] = Float.floatToRawIntBits(stess.normalX = vnx); rawBuffer[rbi + -27] = Float.floatToRawIntBits(stess.normalX = vnx); rawBuffer[rbi + -45] = Float.floatToRawIntBits(stess.normalX = vnx);
/* 341 */           rawBuffer[rbi + 10] = Float.floatToRawIntBits(stess.normalY = vny); rawBuffer[rbi + -8] = Float.floatToRawIntBits(stess.normalY = vny); rawBuffer[rbi + -26] = Float.floatToRawIntBits(stess.normalY = vny); rawBuffer[rbi + -44] = Float.floatToRawIntBits(stess.normalY = vny);
/* 342 */           rawBuffer[rbi + 11] = Float.floatToRawIntBits(stess.normalZ = vnz); rawBuffer[rbi + -7] = Float.floatToRawIntBits(stess.normalZ = vnz); rawBuffer[rbi + -25] = Float.floatToRawIntBits(stess.normalZ = vnz); rawBuffer[rbi + -43] = Float.floatToRawIntBits(stess.normalZ = vnz);
/* 343 */           rawBuffer[rbi + 12] = Float.floatToRawIntBits(tan1x); rawBuffer[rbi + -6] = Float.floatToRawIntBits(tan1x); rawBuffer[rbi + -24] = Float.floatToRawIntBits(tan1x); rawBuffer[rbi + -42] = Float.floatToRawIntBits(tan1x);
/* 344 */           rawBuffer[rbi + 13] = Float.floatToRawIntBits(tan1y); rawBuffer[rbi + -5] = Float.floatToRawIntBits(tan1y); rawBuffer[rbi + -23] = Float.floatToRawIntBits(tan1y); rawBuffer[rbi + -41] = Float.floatToRawIntBits(tan1y);
/* 345 */           rawBuffer[rbi + 14] = Float.floatToRawIntBits(tan1z); rawBuffer[rbi + -4] = Float.floatToRawIntBits(tan1z); rawBuffer[rbi + -22] = Float.floatToRawIntBits(tan1z); rawBuffer[rbi + -40] = Float.floatToRawIntBits(tan1z);
/* 346 */           rawBuffer[rbi + 15] = Float.floatToRawIntBits(tan1w); rawBuffer[rbi + -3] = Float.floatToRawIntBits(tan1w); rawBuffer[rbi + -21] = Float.floatToRawIntBits(tan1w); rawBuffer[rbi + -39] = Float.floatToRawIntBits(tan1w);
/* 347 */           stess.midTextureU = (Float.intBitsToFloat(rawBuffer[rbi + -51]) + Float.intBitsToFloat(rawBuffer[rbi + -33]) + Float.intBitsToFloat(rawBuffer[rbi + -15]) + (float)tess.h) / 4.0F;
/*     */           
/* 349 */           stess.midTextureV = (Float.intBitsToFloat(rawBuffer[rbi + -50]) + Float.intBitsToFloat(rawBuffer[rbi + -32]) + Float.intBitsToFloat(rawBuffer[rbi + -14]) + (float)tess.i) / 4.0F;
/*     */           
/* 351 */           rawBuffer[rbi + 16] = Float.floatToRawIntBits(stess.midTextureU); rawBuffer[rbi + -2] = Float.floatToRawIntBits(stess.midTextureU); rawBuffer[rbi + -20] = Float.floatToRawIntBits(stess.midTextureU); rawBuffer[rbi + -38] = Float.floatToRawIntBits(stess.midTextureU);
/* 352 */           rawBuffer[rbi + 17] = Float.floatToRawIntBits(stess.midTextureV); rawBuffer[rbi + -1] = Float.floatToRawIntBits(stess.midTextureV); rawBuffer[rbi + -19] = Float.floatToRawIntBits(stess.midTextureV); rawBuffer[rbi + -37] = Float.floatToRawIntBits(stess.midTextureV);
/*     */           break;
/*     */       } 
/* 355 */     } else if (tess.s == 4) {
/*     */       float x1, y1, z1, x2, y2, z2, vnx, vny, vnz, lensq, mult, u1, v1, u2, v2, d, r, tan1x, tan1y, tan1z, tan2x, tan2y, tan2z, tan3x, tan3y, tan3z, tan1w;
/* 357 */       int i = tess.q % 3;
/* 358 */       switch (i) {
/*     */         
/*     */         case 0:
/* 361 */           stess.v0x = fx;
/* 362 */           stess.v0y = fy;
/* 363 */           stess.v0z = fz;
/* 364 */           stess.v0u = (float)tess.h;
/* 365 */           stess.v0v = (float)tess.i;
/*     */           break;
/*     */         case 1:
/* 368 */           stess.v1x = fx;
/* 369 */           stess.v1y = fy;
/* 370 */           stess.v1z = fz;
/* 371 */           stess.v1u = (float)tess.h;
/* 372 */           stess.v1v = (float)tess.i;
/*     */           break;
/*     */         case 2:
/* 375 */           stess.v2x = fx;
/* 376 */           stess.v2y = fy;
/* 377 */           stess.v2z = fz;
/* 378 */           stess.v2u = (float)tess.h;
/* 379 */           stess.v2v = (float)tess.i;
/* 380 */           x1 = stess.v1x - stess.v0x;
/* 381 */           y1 = stess.v1y - stess.v0y;
/* 382 */           z1 = stess.v1z - stess.v0z;
/* 383 */           x2 = stess.v2x - stess.v1x;
/* 384 */           y2 = stess.v2y - stess.v1y;
/* 385 */           z2 = stess.v2z - stess.v1z;
/* 386 */           vnx = y1 * z2 - y2 * z1;
/* 387 */           vny = z1 * x2 - z2 * x1;
/* 388 */           vnz = x1 * y2 - x2 * y1;
/* 389 */           lensq = vnx * vnx + vny * vny + vnz * vnz;
/* 390 */           mult = (lensq != 0.0D) ? (float)(1.0D / Math.sqrt(lensq)) : 1.0F;
/* 391 */           vnx *= mult;
/* 392 */           vny *= mult;
/* 393 */           vnz *= mult;
/* 394 */           tess.o = true;
/* 395 */           x1 = stess.v1x - stess.v0x;
/* 396 */           y1 = stess.v1y - stess.v0y;
/* 397 */           z1 = stess.v1z - stess.v0z;
/* 398 */           u1 = stess.v1u - stess.v0u;
/* 399 */           v1 = stess.v1v - stess.v0v;
/* 400 */           x2 = stess.v2x - stess.v0x;
/* 401 */           y2 = stess.v2y - stess.v0y;
/* 402 */           z2 = stess.v2z - stess.v0z;
/* 403 */           u2 = stess.v2u - stess.v0u;
/* 404 */           v2 = stess.v2v - stess.v0v;
/* 405 */           d = u1 * v2 - u2 * v1;
/* 406 */           r = (d != 0.0F) ? (1.0F / d) : 1.0F;
/* 407 */           tan1x = (v2 * x1 - v1 * x2) * r;
/* 408 */           tan1y = (v2 * y1 - v1 * y2) * r;
/* 409 */           tan1z = (v2 * z1 - v1 * z2) * r;
/* 410 */           tan2x = (u1 * x2 - u2 * x1) * r;
/* 411 */           tan2y = (u1 * y2 - u2 * y1) * r;
/* 412 */           tan2z = (u1 * z2 - u2 * z1) * r;
/* 413 */           lensq = tan1x * tan1x + tan1y * tan1y + tan1z * tan1z;
/* 414 */           mult = (lensq != 0.0D) ? (float)(1.0D / Math.sqrt(lensq)) : 1.0F;
/* 415 */           tan1x *= mult;
/* 416 */           tan1y *= mult;
/* 417 */           tan1z *= mult;
/* 418 */           lensq = tan2x * tan2x + tan2y * tan2y + tan2z * tan2z;
/* 419 */           mult = (lensq != 0.0D) ? (float)(1.0D / Math.sqrt(lensq)) : 1.0F;
/* 420 */           tan2x *= mult;
/* 421 */           tan2y *= mult;
/* 422 */           tan2z *= mult;
/* 423 */           tan3x = vnz * tan1y - vny * tan1z;
/* 424 */           tan3y = vnx * tan1z - vnz * tan1x;
/* 425 */           tan3z = vny * tan1x - vnx * tan1y;
/* 426 */           tan1w = (tan2x * tan3x + tan2y * tan3y + tan2z * tan3z < 0.0F) ? -1.0F : 1.0F;
/* 427 */           rawBuffer[rbi + 9] = Float.floatToRawIntBits(stess.normalX = vnx); rawBuffer[rbi + -9] = Float.floatToRawIntBits(stess.normalX = vnx); rawBuffer[rbi + -27] = Float.floatToRawIntBits(stess.normalX = vnx);
/* 428 */           rawBuffer[rbi + 10] = Float.floatToRawIntBits(stess.normalY = vny); rawBuffer[rbi + -8] = Float.floatToRawIntBits(stess.normalY = vny); rawBuffer[rbi + -26] = Float.floatToRawIntBits(stess.normalY = vny);
/* 429 */           rawBuffer[rbi + 11] = Float.floatToRawIntBits(stess.normalZ = vnz); rawBuffer[rbi + -7] = Float.floatToRawIntBits(stess.normalZ = vnz); rawBuffer[rbi + -25] = Float.floatToRawIntBits(stess.normalZ = vnz);
/* 430 */           rawBuffer[rbi + 12] = Float.floatToRawIntBits(tan1x); rawBuffer[rbi + -6] = Float.floatToRawIntBits(tan1x); rawBuffer[rbi + -24] = Float.floatToRawIntBits(tan1x);
/* 431 */           rawBuffer[rbi + 13] = Float.floatToRawIntBits(tan1y); rawBuffer[rbi + -5] = Float.floatToRawIntBits(tan1y); rawBuffer[rbi + -23] = Float.floatToRawIntBits(tan1y);
/* 432 */           rawBuffer[rbi + 14] = Float.floatToRawIntBits(tan1z); rawBuffer[rbi + -4] = Float.floatToRawIntBits(tan1z); rawBuffer[rbi + -22] = Float.floatToRawIntBits(tan1z);
/* 433 */           rawBuffer[rbi + 15] = Float.floatToRawIntBits(tan1w); rawBuffer[rbi + -3] = Float.floatToRawIntBits(tan1w); rawBuffer[rbi + -21] = Float.floatToRawIntBits(tan1w);
/* 434 */           stess.midTextureU = (Float.intBitsToFloat(rawBuffer[rbi + -33]) + Float.intBitsToFloat(rawBuffer[rbi + -15]) + (float)tess.h) / 3.0F;
/* 435 */           stess.midTextureV = (Float.intBitsToFloat(rawBuffer[rbi + -32]) + Float.intBitsToFloat(rawBuffer[rbi + -14]) + (float)tess.i) / 3.0F;
/* 436 */           rawBuffer[rbi + 16] = Float.floatToRawIntBits(stess.midTextureU); rawBuffer[rbi + -2] = Float.floatToRawIntBits(stess.midTextureU); rawBuffer[rbi + -20] = Float.floatToRawIntBits(stess.midTextureU);
/* 437 */           rawBuffer[rbi + 17] = Float.floatToRawIntBits(stess.midTextureV); rawBuffer[rbi + -1] = Float.floatToRawIntBits(stess.midTextureV); rawBuffer[rbi + -19] = Float.floatToRawIntBits(stess.midTextureV);
/*     */           break;
/*     */       } 
/*     */     } 
/* 441 */     tess.q++;
/* 442 */     rawBuffer[rbi + 0] = Float.floatToRawIntBits(fx);
/* 443 */     rawBuffer[rbi + 1] = Float.floatToRawIntBits(fy);
/* 444 */     rawBuffer[rbi + 2] = Float.floatToRawIntBits(fz);
/* 445 */     rawBuffer[rbi + 3] = Float.floatToRawIntBits((float)tess.h);
/* 446 */     rawBuffer[rbi + 4] = Float.floatToRawIntBits((float)tess.i);
/* 447 */     rawBuffer[rbi + 5] = tess.k;
/* 448 */     rawBuffer[rbi + 6] = tess.j;
/* 449 */     rawBuffer[rbi + 7] = Shaders.getEntityData();
/* 450 */     rawBuffer[rbi + 8] = Shaders.getEntityData2();
/* 451 */     rawBuffer[rbi + 9] = Float.floatToRawIntBits(stess.normalX);
/* 452 */     rawBuffer[rbi + 10] = Float.floatToRawIntBits(stess.normalY);
/* 453 */     rawBuffer[rbi + 11] = Float.floatToRawIntBits(stess.normalZ);
/* 454 */     rbi += 18;
/* 455 */     tess.p = rbi;
/* 456 */     tess.g++;
/*     */   }
/*     */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\shadersmod\client\ShadersTess.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
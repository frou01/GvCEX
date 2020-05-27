/*     */ package shadersmod.client;
/*     */ 
/*     */ import bmy;
/*     */ import qh;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ClippingHelperShadow
/*     */   extends bmy
/*     */ {
/*  12 */   private static ClippingHelperShadow instance = new ClippingHelperShadow();
/*  13 */   float[] frustumTest = new float[6];
/*  14 */   float[][] shadowClipPlanes = new float[10][4];
/*     */   int shadowClipPlaneCount;
/*  16 */   float[] matInvMP = new float[16];
/*  17 */   float[] vecIntersection = new float[4];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean b(double x1, double y1, double z1, double x2, double y2, double z2) {
/*  25 */     for (int index = 0; index < this.shadowClipPlaneCount; index++) {
/*     */       
/*  27 */       float[] plane = this.shadowClipPlanes[index];
/*  28 */       if (dot4(plane, x1, y1, z1) <= 0.0D && dot4(plane, x2, y1, z1) <= 0.0D && dot4(plane, x1, y2, z1) <= 0.0D && dot4(plane, x2, y2, z1) <= 0.0D && dot4(plane, x1, y1, z2) <= 0.0D && dot4(plane, x2, y1, z2) <= 0.0D && dot4(plane, x1, y2, z2) <= 0.0D && dot4(plane, x2, y2, z2) <= 0.0D)
/*     */       {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  34 */         return false;
/*     */       }
/*     */     } 
/*     */     
/*  38 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   private double dot4(float[] plane, double x, double y, double z) {
/*  43 */     return plane[0] * x + plane[1] * y + plane[2] * z + plane[3];
/*     */   }
/*     */ 
/*     */   
/*     */   private double dot3(float[] vecA, float[] vecB) {
/*  48 */     return vecA[0] * vecB[0] + vecA[1] * vecB[1] + vecA[2] * vecB[2];
/*     */   }
/*     */ 
/*     */   
/*     */   public static bmy getInstance() {
/*  53 */     instance.init();
/*  54 */     return instance;
/*     */   }
/*     */ 
/*     */   
/*     */   private void normalizePlane(float[] plane) {
/*  59 */     float length = qh.c(plane[0] * plane[0] + plane[1] * plane[1] + plane[2] * plane[2]);
/*  60 */     plane[0] = plane[0] / length;
/*  61 */     plane[1] = plane[1] / length;
/*  62 */     plane[2] = plane[2] / length;
/*  63 */     plane[3] = plane[3] / length;
/*     */   }
/*     */ 
/*     */   
/*     */   private void normalize3(float[] plane) {
/*  68 */     float length = qh.c(plane[0] * plane[0] + plane[1] * plane[1] + plane[2] * plane[2]);
/*  69 */     if (length == 0.0F)
/*  70 */       length = 1.0F; 
/*  71 */     plane[0] = plane[0] / length;
/*  72 */     plane[1] = plane[1] / length;
/*  73 */     plane[2] = plane[2] / length;
/*     */   }
/*     */ 
/*     */   
/*     */   private void assignPlane(float[] plane, float a, float b, float c, float d) {
/*  78 */     float length = (float)Math.sqrt((a * a + b * b + c * c));
/*  79 */     plane[0] = a / length;
/*  80 */     plane[1] = b / length;
/*  81 */     plane[2] = c / length;
/*  82 */     plane[3] = d / length;
/*     */   }
/*     */ 
/*     */   
/*     */   private void copyPlane(float[] dst, float[] src) {
/*  87 */     dst[0] = src[0];
/*  88 */     dst[1] = src[1];
/*  89 */     dst[2] = src[2];
/*  90 */     dst[3] = src[3];
/*     */   }
/*     */ 
/*     */   
/*     */   private void cross3(float[] out, float[] a, float[] b) {
/*  95 */     out[0] = a[1] * b[2] - a[2] * b[1];
/*  96 */     out[1] = a[2] * b[0] - a[0] * b[2];
/*  97 */     out[2] = a[0] * b[1] - a[1] * b[0];
/*     */   }
/*     */ 
/*     */   
/*     */   private void addShadowClipPlane(float[] plane) {
/* 102 */     copyPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], plane);
/*     */   }
/*     */ 
/*     */   
/*     */   private float length(float x, float y, float z) {
/* 107 */     return (float)Math.sqrt((x * x + y * y + z * z));
/*     */   }
/*     */   
/*     */   private float distance(float x1, float y1, float z1, float x2, float y2, float z2) {
/* 111 */     return length(x1 - x2, y1 - y2, z1 - z2);
/*     */   }
/*     */ 
/*     */   
/*     */   private void makeShadowPlane(float[] shadowPlane, float[] positivePlane, float[] negativePlane, float[] vecSun) {
/* 116 */     cross3(this.vecIntersection, positivePlane, negativePlane);
/* 117 */     cross3(shadowPlane, this.vecIntersection, vecSun);
/* 118 */     normalize3(shadowPlane);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 123 */     float dotPN = (float)dot3(positivePlane, negativePlane);
/* 124 */     float dotNP = dotPN;
/* 125 */     float dotSN = (float)dot3(shadowPlane, negativePlane);
/* 126 */     float disSN = distance(shadowPlane[0], shadowPlane[1], shadowPlane[2], negativePlane[0] * dotSN, negativePlane[1] * dotSN, negativePlane[2] * dotSN);
/* 127 */     float disPN = distance(positivePlane[0], positivePlane[1], positivePlane[2], negativePlane[0] * dotPN, negativePlane[1] * dotPN, negativePlane[2] * dotPN);
/* 128 */     float k1 = disSN / disPN;
/* 129 */     float dotSP = (float)dot3(shadowPlane, positivePlane);
/* 130 */     float disSP = distance(shadowPlane[0], shadowPlane[1], shadowPlane[2], positivePlane[0] * dotSP, positivePlane[1] * dotSP, positivePlane[2] * dotSP);
/* 131 */     float disNP = distance(negativePlane[0], negativePlane[1], negativePlane[2], positivePlane[0] * dotNP, positivePlane[1] * dotNP, positivePlane[2] * dotNP);
/* 132 */     float k2 = disSP / disNP;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 141 */     shadowPlane[3] = positivePlane[3] * k1 + negativePlane[3] * k2;
/*     */   }
/*     */ 
/*     */   
/*     */   public void init() {
/* 146 */     float[] matPrj = this.b;
/* 147 */     float[] matMdv = this.c;
/* 148 */     float[] matMP = this.d;
/* 149 */     System.arraycopy(Shaders.faProjection, 0, matPrj, 0, 16);
/* 150 */     System.arraycopy(Shaders.faModelView, 0, matMdv, 0, 16);
/*     */ 
/*     */     
/* 153 */     SMath.multiplyMat4xMat4(matMP, matMdv, matPrj);
/*     */ 
/*     */     
/* 156 */     assignPlane(this.a[0], matMP[3] - matMP[0], matMP[7] - matMP[4], matMP[11] - matMP[8], matMP[15] - matMP[12]);
/*     */     
/* 158 */     assignPlane(this.a[1], matMP[3] + matMP[0], matMP[7] + matMP[4], matMP[11] + matMP[8], matMP[15] + matMP[12]);
/*     */     
/* 160 */     assignPlane(this.a[2], matMP[3] + matMP[1], matMP[7] + matMP[5], matMP[11] + matMP[9], matMP[15] + matMP[13]);
/*     */     
/* 162 */     assignPlane(this.a[3], matMP[3] - matMP[1], matMP[7] - matMP[5], matMP[11] - matMP[9], matMP[15] - matMP[13]);
/*     */     
/* 164 */     assignPlane(this.a[4], matMP[3] - matMP[2], matMP[7] - matMP[6], matMP[11] - matMP[10], matMP[15] - matMP[14]);
/*     */     
/* 166 */     assignPlane(this.a[5], matMP[3] + matMP[2], matMP[7] + matMP[6], matMP[11] + matMP[10], matMP[15] + matMP[14]);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 171 */     float[] vecSun = Shaders.shadowLightPositionVector;
/* 172 */     float test0 = (float)dot3(this.a[0], vecSun);
/* 173 */     float test1 = (float)dot3(this.a[1], vecSun);
/* 174 */     float test2 = (float)dot3(this.a[2], vecSun);
/* 175 */     float test3 = (float)dot3(this.a[3], vecSun);
/* 176 */     float test4 = (float)dot3(this.a[4], vecSun);
/* 177 */     float test5 = (float)dot3(this.a[5], vecSun);
/*     */     
/* 179 */     this.shadowClipPlaneCount = 0;
/*     */ 
/*     */     
/* 182 */     if (test0 >= 0.0F) {
/*     */       
/* 184 */       copyPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.a[0]);
/* 185 */       if (test0 > 0.0F) {
/*     */         
/* 187 */         if (test2 < 0.0F)
/*     */         {
/* 189 */           makeShadowPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.a[0], this.a[2], vecSun);
/*     */         }
/* 191 */         if (test3 < 0.0F)
/*     */         {
/* 193 */           makeShadowPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.a[0], this.a[3], vecSun);
/*     */         }
/* 195 */         if (test4 < 0.0F)
/*     */         {
/* 197 */           makeShadowPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.a[0], this.a[4], vecSun);
/*     */         }
/* 199 */         if (test5 < 0.0F)
/*     */         {
/* 201 */           makeShadowPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.a[0], this.a[5], vecSun);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 206 */     if (test1 >= 0.0F) {
/*     */       
/* 208 */       copyPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.a[1]);
/* 209 */       if (test1 > 0.0F) {
/*     */         
/* 211 */         if (test2 < 0.0F)
/*     */         {
/* 213 */           makeShadowPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.a[1], this.a[2], vecSun);
/*     */         }
/* 215 */         if (test3 < 0.0F)
/*     */         {
/* 217 */           makeShadowPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.a[1], this.a[3], vecSun);
/*     */         }
/* 219 */         if (test4 < 0.0F)
/*     */         {
/* 221 */           makeShadowPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.a[1], this.a[4], vecSun);
/*     */         }
/* 223 */         if (test5 < 0.0F)
/*     */         {
/* 225 */           makeShadowPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.a[1], this.a[5], vecSun);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 230 */     if (test2 >= 0.0F) {
/*     */       
/* 232 */       copyPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.a[2]);
/* 233 */       if (test2 > 0.0F) {
/*     */         
/* 235 */         if (test0 < 0.0F)
/*     */         {
/* 237 */           makeShadowPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.a[2], this.a[0], vecSun);
/*     */         }
/* 239 */         if (test1 < 0.0F)
/*     */         {
/* 241 */           makeShadowPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.a[2], this.a[1], vecSun);
/*     */         }
/* 243 */         if (test4 < 0.0F)
/*     */         {
/* 245 */           makeShadowPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.a[2], this.a[4], vecSun);
/*     */         }
/* 247 */         if (test5 < 0.0F)
/*     */         {
/* 249 */           makeShadowPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.a[2], this.a[5], vecSun);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 254 */     if (test3 >= 0.0F) {
/*     */       
/* 256 */       copyPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.a[3]);
/* 257 */       if (test3 > 0.0F) {
/*     */         
/* 259 */         if (test0 < 0.0F)
/*     */         {
/* 261 */           makeShadowPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.a[3], this.a[0], vecSun);
/*     */         }
/* 263 */         if (test1 < 0.0F)
/*     */         {
/* 265 */           makeShadowPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.a[3], this.a[1], vecSun);
/*     */         }
/* 267 */         if (test4 < 0.0F)
/*     */         {
/* 269 */           makeShadowPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.a[3], this.a[4], vecSun);
/*     */         }
/* 271 */         if (test5 < 0.0F)
/*     */         {
/* 273 */           makeShadowPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.a[3], this.a[5], vecSun);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 278 */     if (test4 >= 0.0F) {
/*     */       
/* 280 */       copyPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.a[4]);
/* 281 */       if (test4 > 0.0F) {
/*     */         
/* 283 */         if (test0 < 0.0F)
/*     */         {
/* 285 */           makeShadowPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.a[4], this.a[0], vecSun);
/*     */         }
/* 287 */         if (test1 < 0.0F)
/*     */         {
/* 289 */           makeShadowPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.a[4], this.a[1], vecSun);
/*     */         }
/* 291 */         if (test2 < 0.0F)
/*     */         {
/* 293 */           makeShadowPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.a[4], this.a[2], vecSun);
/*     */         }
/* 295 */         if (test3 < 0.0F)
/*     */         {
/* 297 */           makeShadowPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.a[4], this.a[3], vecSun);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 302 */     if (test5 >= 0.0F) {
/*     */       
/* 304 */       copyPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.a[5]);
/* 305 */       if (test5 > 0.0F) {
/*     */         
/* 307 */         if (test0 < 0.0F)
/*     */         {
/* 309 */           makeShadowPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.a[5], this.a[0], vecSun);
/*     */         }
/* 311 */         if (test1 < 0.0F)
/*     */         {
/* 313 */           makeShadowPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.a[5], this.a[1], vecSun);
/*     */         }
/* 315 */         if (test2 < 0.0F)
/*     */         {
/* 317 */           makeShadowPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.a[5], this.a[2], vecSun);
/*     */         }
/* 319 */         if (test3 < 0.0F)
/*     */         {
/* 321 */           makeShadowPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.a[5], this.a[3], vecSun);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\shadersmod\client\ClippingHelperShadow.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
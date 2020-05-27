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
/*     */ public class ModelSprite
/*     */ {
/*  16 */   private bix modelRenderer = null;
/*  17 */   private int textureOffsetX = 0;
/*  18 */   private int textureOffsetY = 0;
/*  19 */   private float posX = 0.0F;
/*  20 */   private float posY = 0.0F;
/*  21 */   private float posZ = 0.0F;
/*  22 */   private int sizeX = 0;
/*  23 */   private int sizeY = 0;
/*  24 */   private int sizeZ = 0;
/*  25 */   private float sizeAdd = 0.0F;
/*     */   
/*  27 */   private float minU = 0.0F;
/*  28 */   private float minV = 0.0F;
/*  29 */   private float maxU = 0.0F;
/*  30 */   private float maxV = 0.0F;
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
/*     */   public ModelSprite(bix modelRenderer, int textureOffsetX, int textureOffsetY, float posX, float posY, float posZ, int sizeX, int sizeY, int sizeZ, float sizeAdd) {
/*  47 */     this.modelRenderer = modelRenderer;
/*  48 */     this.textureOffsetX = textureOffsetX;
/*  49 */     this.textureOffsetY = textureOffsetY;
/*  50 */     this.posX = posX;
/*  51 */     this.posY = posY;
/*  52 */     this.posZ = posZ;
/*  53 */     this.sizeX = sizeX;
/*  54 */     this.sizeY = sizeY;
/*  55 */     this.sizeZ = sizeZ;
/*  56 */     this.sizeAdd = sizeAdd;
/*     */     
/*  58 */     this.minU = textureOffsetX / modelRenderer.a;
/*  59 */     this.minV = textureOffsetY / modelRenderer.b;
/*  60 */     this.maxU = (textureOffsetX + sizeX) / modelRenderer.a;
/*  61 */     this.maxV = (textureOffsetY + sizeY) / modelRenderer.b;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void render(bmh tessellator, float scale) {
/*  70 */     GL11.glTranslatef(this.posX * scale, this.posY * scale, this.posZ * scale);
/*     */     
/*  72 */     float rMinU = this.minU;
/*  73 */     float rMaxU = this.maxU;
/*  74 */     float rMinV = this.minV;
/*  75 */     float rMaxV = this.maxV;
/*     */     
/*  77 */     if (this.modelRenderer.i) {
/*     */       
/*  79 */       rMinU = this.maxU;
/*  80 */       rMaxU = this.minU;
/*     */     } 
/*     */     
/*  83 */     if (this.modelRenderer.mirrorV) {
/*     */       
/*  85 */       rMinV = this.maxV;
/*  86 */       rMaxV = this.minV;
/*     */     } 
/*     */     
/*  89 */     renderItemIn2D(tessellator, rMinU, rMinV, rMaxU, rMaxV, this.sizeX, this.sizeY, scale * this.sizeZ, this.modelRenderer.a, this.modelRenderer.b);
/*     */     
/*  91 */     GL11.glTranslatef(-this.posX * scale, -this.posY * scale, -this.posZ * scale);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void renderItemIn2D(bmh tessellator, float minU, float minV, float maxU, float maxV, int sizeX, int sizeY, float width, float texWidth, float texHeight) {
/* 101 */     if (width < 6.25E-4F) {
/* 102 */       width = 6.25E-4F;
/*     */     }
/* 104 */     float dU = maxU - minU;
/* 105 */     float dV = maxV - minV;
/*     */     
/* 107 */     double dimX = (qh.e(dU) * texWidth / 16.0F);
/* 108 */     double dimY = (qh.e(dV) * texHeight / 16.0F);
/*     */     
/* 110 */     tessellator.b();
/* 111 */     tessellator.c(0.0F, 0.0F, -1.0F);
/* 112 */     tessellator.a(0.0D, dimY, 0.0D, minU, maxV);
/* 113 */     tessellator.a(dimX, dimY, 0.0D, maxU, maxV);
/* 114 */     tessellator.a(dimX, 0.0D, 0.0D, maxU, minV);
/* 115 */     tessellator.a(0.0D, 0.0D, 0.0D, minU, minV);
/* 116 */     tessellator.a();
/* 117 */     tessellator.b();
/* 118 */     tessellator.c(0.0F, 0.0F, 1.0F);
/* 119 */     tessellator.a(0.0D, 0.0D, width, minU, minV);
/* 120 */     tessellator.a(dimX, 0.0D, width, maxU, minV);
/* 121 */     tessellator.a(dimX, dimY, width, maxU, maxV);
/* 122 */     tessellator.a(0.0D, dimY, width, minU, maxV);
/* 123 */     tessellator.a();
/* 124 */     float var8 = 0.5F * dU / sizeX;
/* 125 */     float var9 = 0.5F * dV / sizeY;
/* 126 */     tessellator.b();
/* 127 */     tessellator.c(-1.0F, 0.0F, 0.0F);
/*     */ 
/*     */     
/*     */     int var10;
/*     */     
/* 132 */     for (var10 = 0; var10 < sizeX; var10++) {
/*     */       
/* 134 */       float var11 = var10 / sizeX;
/* 135 */       float var12 = minU + dU * var11 + var8;
/* 136 */       tessellator.a(var11 * dimX, dimY, width, var12, maxV);
/* 137 */       tessellator.a(var11 * dimX, dimY, 0.0D, var12, maxV);
/* 138 */       tessellator.a(var11 * dimX, 0.0D, 0.0D, var12, minV);
/* 139 */       tessellator.a(var11 * dimX, 0.0D, width, var12, minV);
/*     */     } 
/*     */     
/* 142 */     tessellator.a();
/* 143 */     tessellator.b();
/* 144 */     tessellator.c(1.0F, 0.0F, 0.0F);
/*     */ 
/*     */     
/* 147 */     for (var10 = 0; var10 < sizeX; var10++) {
/*     */       
/* 149 */       float var11 = var10 / sizeX;
/* 150 */       float var12 = minU + dU * var11 + var8;
/* 151 */       float var13 = var11 + 1.0F / sizeX;
/* 152 */       tessellator.a(var13 * dimX, 0.0D, width, var12, minV);
/* 153 */       tessellator.a(var13 * dimX, 0.0D, 0.0D, var12, minV);
/* 154 */       tessellator.a(var13 * dimX, dimY, 0.0D, var12, maxV);
/* 155 */       tessellator.a(var13 * dimX, dimY, width, var12, maxV);
/*     */     } 
/*     */     
/* 158 */     tessellator.a();
/* 159 */     tessellator.b();
/* 160 */     tessellator.c(0.0F, 1.0F, 0.0F);
/*     */     
/* 162 */     for (var10 = 0; var10 < sizeY; var10++) {
/*     */       
/* 164 */       float var11 = var10 / sizeY;
/* 165 */       float var12 = minV + dV * var11 + var9;
/* 166 */       float var13 = var11 + 1.0F / sizeY;
/* 167 */       tessellator.a(0.0D, var13 * dimY, width, minU, var12);
/* 168 */       tessellator.a(dimX, var13 * dimY, width, maxU, var12);
/* 169 */       tessellator.a(dimX, var13 * dimY, 0.0D, maxU, var12);
/* 170 */       tessellator.a(0.0D, var13 * dimY, 0.0D, minU, var12);
/*     */     } 
/*     */     
/* 173 */     tessellator.a();
/* 174 */     tessellator.b();
/* 175 */     tessellator.c(0.0F, -1.0F, 0.0F);
/*     */     
/* 177 */     for (var10 = 0; var10 < sizeY; var10++) {
/*     */       
/* 179 */       float var11 = var10 / sizeY;
/* 180 */       float var12 = minV + dV * var11 + var9;
/* 181 */       tessellator.a(dimX, var11 * dimY, width, maxU, var12);
/* 182 */       tessellator.a(0.0D, var11 * dimY, width, minU, var12);
/* 183 */       tessellator.a(0.0D, var11 * dimY, 0.0D, minU, var12);
/* 184 */       tessellator.a(dimX, var11 * dimY, 0.0D, maxU, var12);
/*     */     } 
/*     */     
/* 187 */     tessellator.a();
/*     */   }
/*     */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\ModelSprite.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
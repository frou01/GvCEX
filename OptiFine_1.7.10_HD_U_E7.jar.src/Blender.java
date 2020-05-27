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
/*     */ public class Blender
/*     */ {
/*     */   public static final int BLEND_ALPHA = 0;
/*     */   public static final int BLEND_ADD = 1;
/*     */   public static final int BLEND_SUBSTRACT = 2;
/*     */   public static final int BLEND_MULTIPLY = 3;
/*     */   public static final int BLEND_DODGE = 4;
/*     */   public static final int BLEND_BURN = 5;
/*     */   public static final int BLEND_SCREEN = 6;
/*     */   public static final int BLEND_OVERLAY = 7;
/*     */   public static final int BLEND_REPLACE = 8;
/*     */   public static final int BLEND_DEFAULT = 1;
/*     */   
/*     */   public static int parseBlend(String str) {
/*  32 */     if (str == null) {
/*  33 */       return 1;
/*     */     }
/*  35 */     str = str.toLowerCase().trim();
/*     */     
/*  37 */     if (str.equals("alpha"))
/*  38 */       return 0; 
/*  39 */     if (str.equals("add"))
/*  40 */       return 1; 
/*  41 */     if (str.equals("subtract"))
/*  42 */       return 2; 
/*  43 */     if (str.equals("multiply"))
/*  44 */       return 3; 
/*  45 */     if (str.equals("dodge"))
/*  46 */       return 4; 
/*  47 */     if (str.equals("burn"))
/*  48 */       return 5; 
/*  49 */     if (str.equals("screen"))
/*  50 */       return 6; 
/*  51 */     if (str.equals("overlay"))
/*  52 */       return 7; 
/*  53 */     if (str.equals("replace")) {
/*  54 */       return 8;
/*     */     }
/*  56 */     Config.warn("Unknown blend: " + str);
/*     */     
/*  58 */     return 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setupBlend(int blend, float brightness) {
/*  66 */     switch (blend) {
/*     */       
/*     */       case 0:
/*  69 */         GL11.glDisable(3008);
/*  70 */         GL11.glEnable(3042);
/*  71 */         GL11.glBlendFunc(770, 771);
/*  72 */         GL11.glColor4f(1.0F, 1.0F, 1.0F, brightness);
/*     */         break;
/*     */       case 1:
/*  75 */         GL11.glDisable(3008);
/*  76 */         GL11.glEnable(3042);
/*  77 */         GL11.glBlendFunc(770, 1);
/*  78 */         GL11.glColor4f(1.0F, 1.0F, 1.0F, brightness);
/*     */         break;
/*     */       case 2:
/*  81 */         GL11.glDisable(3008);
/*  82 */         GL11.glEnable(3042);
/*  83 */         GL11.glBlendFunc(775, 0);
/*  84 */         GL11.glColor4f(brightness, brightness, brightness, 1.0F);
/*     */         break;
/*     */       case 3:
/*  87 */         GL11.glDisable(3008);
/*  88 */         GL11.glEnable(3042);
/*  89 */         GL11.glBlendFunc(774, 771);
/*  90 */         GL11.glColor4f(brightness, brightness, brightness, brightness);
/*     */         break;
/*     */       case 4:
/*  93 */         GL11.glDisable(3008);
/*  94 */         GL11.glEnable(3042);
/*  95 */         GL11.glBlendFunc(1, 1);
/*  96 */         GL11.glColor4f(brightness, brightness, brightness, 1.0F);
/*     */         break;
/*     */       case 5:
/*  99 */         GL11.glDisable(3008);
/* 100 */         GL11.glEnable(3042);
/* 101 */         GL11.glBlendFunc(0, 769);
/* 102 */         GL11.glColor4f(brightness, brightness, brightness, 1.0F);
/*     */         break;
/*     */       case 6:
/* 105 */         GL11.glDisable(3008);
/* 106 */         GL11.glEnable(3042);
/* 107 */         GL11.glBlendFunc(1, 769);
/* 108 */         GL11.glColor4f(brightness, brightness, brightness, 1.0F);
/*     */         break;
/*     */       case 7:
/* 111 */         GL11.glDisable(3008);
/* 112 */         GL11.glEnable(3042);
/* 113 */         GL11.glBlendFunc(774, 768);
/* 114 */         GL11.glColor4f(brightness, brightness, brightness, 1.0F);
/*     */         break;
/*     */       case 8:
/* 117 */         GL11.glEnable(3008);
/* 118 */         GL11.glDisable(3042);
/* 119 */         GL11.glColor4f(1.0F, 1.0F, 1.0F, brightness);
/*     */         break;
/*     */     } 
/* 122 */     GL11.glEnable(3553);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void clearBlend(float rainBrightness) {
/* 129 */     GL11.glDisable(3008);
/* 130 */     GL11.glEnable(3042);
/* 131 */     GL11.glBlendFunc(770, 1);
/* 132 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, rainBrightness);
/*     */   }
/*     */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\Blender.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
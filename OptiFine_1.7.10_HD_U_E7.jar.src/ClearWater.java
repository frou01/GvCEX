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
/*     */ public class ClearWater
/*     */ {
/*     */   public static void updateWaterOpacity(bbj settings, ahb world) {
/*  25 */     if (settings != null) {
/*     */ 
/*     */       
/*  28 */       int opacity = 3;
/*  29 */       if (settings.ofClearWater) {
/*  30 */         opacity = 1;
/*     */       }
/*  32 */       BlockUtils.setLightOpacity(ajn.j, opacity);
/*  33 */       BlockUtils.setLightOpacity((aji)ajn.i, opacity);
/*     */     } 
/*     */     
/*  36 */     if (world == null) {
/*     */       return;
/*     */     }
/*  39 */     apu cp = world.L();
/*  40 */     if (cp == null) {
/*     */       return;
/*     */     }
/*  43 */     sv sv = (Config.getMinecraft()).i;
/*  44 */     if (sv == null) {
/*     */       return;
/*     */     }
/*  47 */     int cViewX = (int)((sa)sv).s / 16;
/*  48 */     int cViewZ = (int)((sa)sv).u / 16;
/*     */     
/*  50 */     int cXMin = cViewX - 512;
/*  51 */     int cXMax = cViewX + 512;
/*  52 */     int cZMin = cViewZ - 512;
/*  53 */     int cZMax = cViewZ + 512;
/*     */     
/*  55 */     int countUpdated = 0;
/*  56 */     for (int cx = cXMin; cx < cXMax; cx++) {
/*     */       
/*  58 */       for (int cz = cZMin; cz < cZMax; cz++) {
/*     */         
/*  60 */         if (cp.a(cx, cz)) {
/*     */ 
/*     */           
/*  63 */           apx c = cp.d(cx, cz);
/*  64 */           if (c != null && !(c instanceof apw)) {
/*     */ 
/*     */             
/*  67 */             int x0 = cx << 4;
/*  68 */             int z0 = cz << 4;
/*  69 */             int x1 = x0 + 16;
/*  70 */             int z1 = z0 + 16;
/*  71 */             for (int x = x0; x < x1; x++) {
/*     */               
/*  73 */               for (int z = z0; z < z1; z++) {
/*     */ 
/*     */                 
/*  76 */                 int posH = world.h(x, z);
/*     */                 
/*  78 */                 for (int y = 0; y < posH; y++) {
/*     */                   
/*  80 */                   aji block = world.a(x, y, z);
/*     */                   
/*  82 */                   if (block.o() == awt.h) {
/*     */ 
/*     */                     
/*  85 */                     world.b(x, z, y, posH);
/*  86 */                     countUpdated++; break;
/*     */                   } 
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*  95 */     if (countUpdated > 0) {
/*     */       
/*  97 */       String threadName = "server";
/*  98 */       if (Config.isMinecraftThread()) {
/*  99 */         threadName = "client";
/*     */       }
/* 101 */       Config.dbg("ClearWater (" + threadName + ") relighted " + countUpdated + " chunks");
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\ClearWater.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
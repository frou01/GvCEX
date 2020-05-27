/*     */ import java.util.List;
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
/*     */ public class WrUpdates
/*     */ {
/*  17 */   private static IWrUpdater wrUpdater = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setWrUpdater(IWrUpdater updater) {
/*  24 */     if (wrUpdater != null) {
/*  25 */       wrUpdater.terminate();
/*     */     }
/*  27 */     wrUpdater = updater;
/*     */     
/*  29 */     if (wrUpdater != null) {
/*     */       
/*     */       try {
/*     */         
/*  33 */         wrUpdater.initialize();
/*     */       }
/*  35 */       catch (Exception e) {
/*     */         
/*  37 */         wrUpdater = null;
/*  38 */         e.printStackTrace();
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean hasWrUpdater() {
/*  48 */     return (wrUpdater != null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static IWrUpdater getWrUpdater() {
/*  55 */     return wrUpdater;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static blo makeWorldRenderer(ahb worldObj, List tileEntities, int x, int y, int z, int glRenderListBase) {
/*  61 */     if (wrUpdater == null) {
/*  62 */       return new blo(worldObj, tileEntities, x, y, z, glRenderListBase);
/*     */     }
/*  64 */     return wrUpdater.makeWorldRenderer(worldObj, tileEntities, x, y, z, glRenderListBase);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean updateRenderers(bma rg, sv entityliving, boolean flag) {
/*     */     try {
/*  71 */       return wrUpdater.updateRenderers(rg, entityliving, flag);
/*     */     }
/*  73 */     catch (Exception e) {
/*     */ 
/*     */       
/*  76 */       e.printStackTrace();
/*     */       
/*  78 */       setWrUpdater(null);
/*     */       
/*  80 */       return false;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void resumeBackgroundUpdates() {
/*  86 */     if (wrUpdater != null) {
/*  87 */       wrUpdater.resumeBackgroundUpdates();
/*     */     }
/*     */   }
/*     */   
/*     */   public static void pauseBackgroundUpdates() {
/*  92 */     if (wrUpdater != null) {
/*  93 */       wrUpdater.pauseBackgroundUpdates();
/*     */     }
/*     */   }
/*     */   
/*     */   public static void finishCurrentUpdate() {
/*  98 */     if (wrUpdater != null) {
/*  99 */       wrUpdater.finishCurrentUpdate();
/*     */     }
/*     */   }
/*     */   
/*     */   public static void preRender(bma rg, sv player) {
/* 104 */     if (wrUpdater != null) {
/* 105 */       wrUpdater.preRender(rg, player);
/*     */     }
/*     */   }
/*     */   
/*     */   public static void postRender() {
/* 110 */     if (wrUpdater != null) {
/* 111 */       wrUpdater.postRender();
/*     */     }
/*     */   }
/*     */   
/*     */   public static void clearAllUpdates() {
/* 116 */     if (wrUpdater != null)
/* 117 */       wrUpdater.clearAllUpdates(); 
/*     */   }
/*     */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\WrUpdates.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
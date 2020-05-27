/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WrUpdaterSmooth
/*     */   implements IWrUpdater
/*     */ {
/*  13 */   private long lastUpdateStartTimeNs = 0L;
/*  14 */   private long updateStartTimeNs = 0L;
/*  15 */   private long updateTimeNs = 10000000L;
/*  16 */   private WorldRendererSmooth currentUpdateRenderer = null;
/*  17 */   private int renderersUpdated = 0;
/*  18 */   private int renderersFound = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initialize() {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void terminate() {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public blo makeWorldRenderer(ahb worldObj, List tileEntities, int x, int y, int z, int glRenderListBase) {
/*  35 */     return new WorldRendererSmooth(worldObj, tileEntities, x, y, z, glRenderListBase);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean updateRenderers(bma rg, sv entityliving, boolean flag) {
/*  44 */     this.lastUpdateStartTimeNs = this.updateStartTimeNs;
/*  45 */     this.updateStartTimeNs = System.nanoTime();
/*  46 */     long finishTimeNs = this.updateStartTimeNs + this.updateTimeNs;
/*     */     
/*  48 */     int maxNum = Config.getUpdatesPerFrame();
/*  49 */     if (Config.isDynamicUpdates() && !rg.isMoving(entityliving)) {
/*  50 */       maxNum *= 3;
/*     */     }
/*  52 */     this.renderersUpdated = 0;
/*     */ 
/*     */     
/*     */     do {
/*  56 */       this.renderersFound = 0;
/*  57 */       updateRenderersImpl(rg, entityliving, flag);
/*     */     }
/*  59 */     while (this.renderersFound > 0 && System.nanoTime() - finishTimeNs < 0L);
/*     */     
/*  61 */     if (this.renderersFound > 0) {
/*     */       
/*  63 */       maxNum = Math.min(maxNum, this.renderersFound);
/*  64 */       long diff = 400000L;
/*  65 */       if (this.renderersUpdated > maxNum)
/*  66 */         this.updateTimeNs -= 2L * diff; 
/*  67 */       if (this.renderersUpdated < maxNum) {
/*  68 */         this.updateTimeNs += diff;
/*     */       }
/*     */     } else {
/*     */       
/*  72 */       this.updateTimeNs = 0L;
/*  73 */       this.updateTimeNs -= 200000L;
/*     */     } 
/*  75 */     if (this.updateTimeNs < 0L) {
/*  76 */       this.updateTimeNs = 0L;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*  81 */     return (this.renderersUpdated > 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void updateRenderersImpl(bma rg, sv entityliving, boolean flag) {
/*  92 */     this.renderersFound = 0;
/*     */     
/*  94 */     boolean currentUpdateFinished = true;
/*  95 */     if (this.currentUpdateRenderer != null) {
/*     */       
/*  97 */       this.renderersFound++;
/*  98 */       currentUpdateFinished = updateRenderer(this.currentUpdateRenderer);
/*  99 */       if (currentUpdateFinished) {
/* 100 */         this.renderersUpdated++;
/*     */       }
/*     */     } 
/* 103 */     if (rg.t.size() <= 0) {
/*     */       return;
/*     */     }
/* 106 */     int NOT_IN_FRUSTRUM_MUL = 4;
/*     */     
/* 108 */     WorldRendererSmooth wrBest = null;
/* 109 */     float distSqBest = Float.MAX_VALUE;
/* 110 */     int indexBest = -1;
/*     */ 
/*     */     
/* 113 */     for (int i = 0; i < rg.t.size(); i++) {
/*     */       
/* 115 */       WorldRendererSmooth wr = (WorldRendererSmooth)rg.t.get(i);
/*     */       
/* 117 */       if (wr == null) {
/*     */         continue;
/*     */       }
/* 120 */       this.renderersFound++;
/*     */       
/* 122 */       if (!wr.q) {
/*     */ 
/*     */         
/* 125 */         rg.t.set(i, null);
/*     */         
/*     */         continue;
/*     */       } 
/* 129 */       float distSq = wr.a((sa)entityliving);
/*     */       
/* 131 */       if (distSq <= 256.0F)
/*     */       {
/*     */         
/* 134 */         if (rg.isActingNow()) {
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 139 */           wr.updateRenderer();
/* 140 */           wr.q = false;
/* 141 */           rg.t.set(i, null);
/* 142 */           this.renderersUpdated++;
/*     */ 
/*     */           
/*     */           continue;
/*     */         } 
/*     */       }
/*     */ 
/*     */       
/* 150 */       if (!wr.l) {
/* 151 */         distSq *= NOT_IN_FRUSTRUM_MUL;
/*     */       }
/* 153 */       if (wrBest == null) {
/*     */         
/* 155 */         wrBest = wr;
/* 156 */         distSqBest = distSq;
/* 157 */         indexBest = i;
/*     */ 
/*     */       
/*     */       }
/* 161 */       else if (distSq < distSqBest) {
/*     */ 
/*     */         
/* 164 */         wrBest = wr;
/* 165 */         distSqBest = distSq;
/* 166 */         indexBest = i;
/*     */       } 
/*     */       continue;
/*     */     } 
/* 170 */     if (this.currentUpdateRenderer != null)
/*     */     {
/* 172 */       if (!currentUpdateFinished) {
/*     */         return;
/*     */       }
/*     */     }
/* 176 */     if (wrBest != null) {
/*     */ 
/*     */ 
/*     */       
/* 180 */       rg.t.set(indexBest, null);
/* 181 */       if (!updateRenderer(wrBest))
/*     */         return; 
/* 183 */       this.renderersUpdated++;
/*     */       
/* 185 */       if (System.nanoTime() > this.updateStartTimeNs + this.updateTimeNs) {
/*     */         return;
/*     */       }
/* 188 */       float maxDiffDistSq = distSqBest / 5.0F;
/*     */       
/* 190 */       for (int j = 0; j < rg.t.size(); j++) {
/*     */ 
/*     */ 
/*     */         
/* 194 */         WorldRendererSmooth wr = (WorldRendererSmooth)rg.t.get(j);
/*     */         
/* 196 */         if (wr != null) {
/*     */ 
/*     */           
/* 199 */           float distSq = wr.a((sa)entityliving);
/*     */           
/* 201 */           if (!wr.l)
/* 202 */             distSq *= NOT_IN_FRUSTRUM_MUL; 
/* 203 */           float diffDistSq = Math.abs(distSq - distSqBest);
/* 204 */           if (diffDistSq < maxDiffDistSq) {
/*     */ 
/*     */             
/* 207 */             rg.t.set(j, null);
/* 208 */             if (!updateRenderer(wr))
/*     */               return; 
/* 210 */             this.renderersUpdated++;
/*     */             
/* 212 */             if (System.nanoTime() > this.updateStartTimeNs + this.updateTimeNs)
/*     */               break; 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/* 218 */     if (this.renderersFound == 0) {
/* 219 */       rg.t.clear();
/*     */     }
/* 221 */     if (rg.t.size() > 100 && this.renderersFound < rg.t.size() * 4 / 5) {
/*     */ 
/*     */ 
/*     */       
/* 225 */       int dstIndex = 0;
/* 226 */       for (int srcIndex = 0; srcIndex < rg.t.size(); srcIndex++) {
/*     */         
/* 228 */         Object wr = rg.t.get(srcIndex);
/* 229 */         if (wr != null) {
/*     */ 
/*     */           
/* 232 */           if (srcIndex != dstIndex) {
/* 233 */             rg.t.set(dstIndex, wr);
/*     */           }
/* 235 */           dstIndex++;
/*     */         } 
/*     */       } 
/* 238 */       for (int j = rg.t.size() - 1; j >= dstIndex; j--)
/*     */       {
/* 240 */         rg.t.remove(j);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean updateRenderer(WorldRendererSmooth wr) {
/* 249 */     long finishTime = this.updateStartTimeNs + this.updateTimeNs;
/*     */     
/* 251 */     wr.q = false;
/* 252 */     boolean ready = wr.updateRenderer(finishTime);
/* 253 */     if (!ready) {
/*     */       
/* 255 */       this.currentUpdateRenderer = wr;
/* 256 */       return false;
/*     */     } 
/*     */     
/* 259 */     wr.finishUpdate();
/* 260 */     this.currentUpdateRenderer = null;
/* 261 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void finishCurrentUpdate() {
/* 266 */     if (this.currentUpdateRenderer == null) {
/*     */       return;
/*     */     }
/* 269 */     this.currentUpdateRenderer.updateRenderer();
/*     */     
/* 271 */     this.currentUpdateRenderer = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void resumeBackgroundUpdates() {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void pauseBackgroundUpdates() {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void preRender(bma rg, sv player) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void postRender() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void clearAllUpdates() {
/* 296 */     finishCurrentUpdate();
/*     */   }
/*     */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\WrUpdaterSmooth.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
/*     */ import java.util.List;
/*     */ import org.lwjgl.opengl.Display;
/*     */ import org.lwjgl.opengl.Drawable;
/*     */ import org.lwjgl.opengl.Pbuffer;
/*     */ import org.lwjgl.opengl.PixelFormat;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WrUpdaterThreaded
/*     */   implements IWrUpdater
/*     */ {
/*  18 */   private WrUpdateThread updateThread = null;
/*     */   
/*  20 */   private float timePerUpdateMs = 10.0F;
/*  21 */   private long updateStartTimeNs = 0L;
/*     */   private boolean firstUpdate = true;
/*  23 */   private int updateTargetNum = 0;
/*     */ 
/*     */   
/*     */   public void terminate() {
/*  27 */     if (this.updateThread == null) {
/*     */       return;
/*     */     }
/*  30 */     this.updateThread.terminate();
/*     */     
/*  32 */     this.updateThread.unpauseToEndOfUpdate();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void initialize() {}
/*     */ 
/*     */ 
/*     */   
/*     */   private void delayedInit() {
/*  42 */     if (this.updateThread != null) {
/*     */       return;
/*     */     }
/*  45 */     createUpdateThread(Display.getDrawable());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public blo makeWorldRenderer(ahb worldObj, List tileEntities, int x, int y, int z, int glRenderListBase) {
/*  51 */     return new WorldRendererThreaded(worldObj, tileEntities, x, y, z, glRenderListBase);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WrUpdateThread createUpdateThread(Drawable displayDrawable) {
/*  59 */     if (this.updateThread != null) {
/*  60 */       throw new IllegalStateException("UpdateThread is already existing");
/*     */     }
/*     */     try {
/*  63 */       Pbuffer pbuffer = new Pbuffer(1, 1, new PixelFormat(), displayDrawable);
/*  64 */       this.updateThread = new WrUpdateThread(pbuffer);
/*  65 */       this.updateThread.setPriority(1);
/*  66 */       this.updateThread.start();
/*     */       
/*  68 */       this.updateThread.pause();
/*     */       
/*  70 */       return this.updateThread;
/*     */     }
/*  72 */     catch (Exception e) {
/*     */       
/*  74 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isUpdateThread() {
/*  84 */     return (Thread.currentThread() == this.updateThread);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isBackgroundChunkLoading() {
/*  92 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void preRender(bma rg, sv player) {
/*  98 */     this.updateTargetNum = 0;
/*  99 */     if (this.updateThread != null) {
/*     */       
/* 101 */       if (this.updateStartTimeNs == 0L) {
/* 102 */         this.updateStartTimeNs = System.nanoTime();
/*     */       }
/* 104 */       if (this.updateThread.hasWorkToDo()) {
/*     */         
/* 106 */         this.updateTargetNum = Config.getUpdatesPerFrame();
/* 107 */         if (Config.isDynamicUpdates() && !rg.isMoving(player)) {
/* 108 */           this.updateTargetNum *= 3;
/*     */         }
/* 110 */         this.updateTargetNum = Math.min(this.updateTargetNum, this.updateThread.getPendingUpdatesCount());
/*     */         
/* 112 */         if (this.updateTargetNum > 0) {
/* 113 */           this.updateThread.unpause();
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void postRender() {
/* 123 */     if (this.updateThread != null) {
/*     */       
/* 125 */       float sleepTimeMs = 0.0F;
/* 126 */       if (this.updateTargetNum > 0) {
/*     */ 
/*     */         
/* 129 */         long renderTimeNs = System.nanoTime() - this.updateStartTimeNs;
/*     */         
/* 131 */         float targetRunTime = this.timePerUpdateMs * (1.0F + (this.updateTargetNum - 1) / 2.0F);
/*     */         
/* 133 */         sleepTimeMs = targetRunTime;
/* 134 */         if (sleepTimeMs > 0.0F) {
/*     */ 
/*     */           
/* 137 */           int sleepTimeMsInt = (int)sleepTimeMs;
/* 138 */           Config.sleep(sleepTimeMsInt);
/*     */         } 
/*     */         
/* 141 */         this.updateThread.pause();
/*     */       } 
/*     */       
/* 144 */       float deltaTime = 0.2F;
/* 145 */       if (this.updateTargetNum > 0) {
/*     */         
/* 147 */         int updateCount = this.updateThread.resetUpdateCount();
/* 148 */         if (updateCount < this.updateTargetNum)
/* 149 */           this.timePerUpdateMs += deltaTime; 
/* 150 */         if (updateCount > this.updateTargetNum)
/* 151 */           this.timePerUpdateMs -= deltaTime; 
/* 152 */         if (updateCount == this.updateTargetNum) {
/* 153 */           this.timePerUpdateMs -= deltaTime;
/*     */ 
/*     */         
/*     */         }
/*     */ 
/*     */       
/*     */       }
/*     */       else {
/*     */ 
/*     */         
/* 163 */         this.timePerUpdateMs -= deltaTime / 5.0F;
/*     */       } 
/*     */       
/* 166 */       if (this.timePerUpdateMs < 0.0F) {
/* 167 */         this.timePerUpdateMs = 0.0F;
/*     */       }
/* 169 */       this.updateStartTimeNs = System.nanoTime();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean updateRenderers(bma rg, sv entityliving, boolean flag) {
/* 176 */     delayedInit();
/*     */     
/* 178 */     if (rg.t.size() <= 0)
/*     */     {
/*     */       
/* 181 */       return true;
/*     */     }
/*     */     
/* 184 */     int num = 0;
/*     */ 
/*     */     
/* 187 */     int NOT_IN_FRUSTRUM_MUL = 4;
/* 188 */     int numValid = 0;
/*     */     
/* 190 */     blo wrBest = null;
/* 191 */     float distSqBest = Float.MAX_VALUE;
/* 192 */     int indexBest = -1;
/*     */ 
/*     */ 
/*     */     
/* 196 */     for (int i = 0; i < rg.t.size(); i++) {
/*     */       
/* 198 */       blo wr = (blo)rg.t.get(i);
/*     */       
/* 200 */       if (wr == null) {
/*     */         continue;
/*     */       }
/* 203 */       numValid++;
/*     */       
/* 205 */       if (wr.isUpdating) {
/*     */         continue;
/*     */       }
/* 208 */       if (!wr.q) {
/*     */ 
/*     */         
/* 211 */         rg.t.set(i, null);
/*     */         
/*     */         continue;
/*     */       } 
/* 215 */       float distSq = wr.a((sa)entityliving);
/*     */       
/* 217 */       if (distSq < 512.0F) {
/*     */ 
/*     */ 
/*     */         
/* 221 */         if ((distSq < 256.0F && rg.isActingNow() && wr.l) || this.firstUpdate) {
/*     */ 
/*     */ 
/*     */           
/* 225 */           if (this.updateThread != null)
/*     */           {
/*     */ 
/*     */             
/* 229 */             this.updateThread.unpauseToEndOfUpdate();
/*     */           }
/*     */           
/* 232 */           wr.a(entityliving);
/* 233 */           wr.q = false;
/* 234 */           rg.t.set(i, null);
/* 235 */           num++;
/*     */ 
/*     */           
/*     */           continue;
/*     */         } 
/*     */ 
/*     */         
/* 242 */         if (this.updateThread != null) {
/*     */ 
/*     */ 
/*     */           
/* 246 */           this.updateThread.addRendererToUpdate(wr, true);
/* 247 */           wr.q = false;
/* 248 */           rg.t.set(i, null);
/* 249 */           num++;
/*     */ 
/*     */           
/*     */           continue;
/*     */         } 
/*     */       } 
/*     */       
/* 256 */       if (!wr.l) {
/* 257 */         distSq *= NOT_IN_FRUSTRUM_MUL;
/*     */       }
/* 259 */       if (wrBest == null) {
/*     */         
/* 261 */         wrBest = wr;
/* 262 */         distSqBest = distSq;
/* 263 */         indexBest = i;
/*     */ 
/*     */       
/*     */       }
/* 267 */       else if (distSq < distSqBest) {
/*     */ 
/*     */         
/* 270 */         wrBest = wr;
/* 271 */         distSqBest = distSq;
/* 272 */         indexBest = i;
/*     */       } 
/*     */       continue;
/*     */     } 
/* 276 */     int maxUpdateNum = Config.getUpdatesPerFrame();
/* 277 */     boolean turboMode = false;
/*     */     
/* 279 */     if (Config.isDynamicUpdates())
/*     */     {
/* 281 */       if (!rg.isMoving(entityliving)) {
/*     */         
/* 283 */         maxUpdateNum *= 3;
/* 284 */         turboMode = true;
/*     */       } 
/*     */     }
/*     */     
/* 288 */     if (this.updateThread != null) {
/*     */ 
/*     */       
/* 291 */       maxUpdateNum = this.updateThread.getUpdateCapacity();
/* 292 */       if (maxUpdateNum <= 0)
/*     */       {
/*     */ 
/*     */ 
/*     */         
/* 297 */         return true;
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 303 */     if (wrBest != null) {
/*     */ 
/*     */ 
/*     */       
/* 307 */       updateRenderer(wrBest, entityliving);
/* 308 */       rg.t.set(indexBest, null);
/* 309 */       num++;
/*     */       
/* 311 */       float maxDiffDistSq = distSqBest / 5.0F;
/*     */       
/* 313 */       for (int j = 0; j < rg.t.size(); j++) {
/*     */         
/* 315 */         if (num >= maxUpdateNum)
/*     */           break; 
/* 317 */         blo wr = (blo)rg.t.get(j);
/*     */         
/* 319 */         if (wr != null)
/*     */         {
/*     */           
/* 322 */           if (!wr.isUpdating) {
/*     */ 
/*     */             
/* 325 */             float distSq = wr.a((sa)entityliving);
/*     */             
/* 327 */             if (!wr.l)
/* 328 */               distSq *= NOT_IN_FRUSTRUM_MUL; 
/* 329 */             float diffDistSq = Math.abs(distSq - distSqBest);
/* 330 */             if (diffDistSq < maxDiffDistSq) {
/*     */ 
/*     */               
/* 333 */               updateRenderer(wr, entityliving);
/* 334 */               rg.t.set(j, null);
/* 335 */               num++;
/*     */             } 
/*     */           }  } 
/*     */       } 
/*     */     } 
/* 340 */     if (numValid == 0) {
/* 341 */       rg.t.clear();
/*     */     }
/* 343 */     if (rg.t.size() > 100 && numValid < rg.t.size() * 4 / 5) {
/*     */ 
/*     */ 
/*     */       
/* 347 */       int dstIndex = 0;
/* 348 */       for (int srcIndex = 0; srcIndex < rg.t.size(); srcIndex++) {
/*     */         
/* 350 */         Object wr = rg.t.get(srcIndex);
/* 351 */         if (wr != null) {
/*     */ 
/*     */           
/* 354 */           if (srcIndex != dstIndex) {
/* 355 */             rg.t.set(dstIndex, wr);
/*     */           }
/* 357 */           dstIndex++;
/*     */         } 
/*     */       } 
/* 360 */       for (int j = rg.t.size() - 1; j >= dstIndex; j--)
/*     */       {
/*     */         
/* 363 */         rg.t.remove(j);
/*     */       }
/*     */     } 
/*     */     
/* 367 */     this.firstUpdate = false;
/*     */     
/* 369 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void updateRenderer(blo wr, sv entityLiving) {
/* 379 */     WrUpdateThread ut = this.updateThread;
/* 380 */     if (ut != null) {
/*     */ 
/*     */ 
/*     */       
/* 384 */       ut.addRendererToUpdate(wr, false);
/*     */       
/* 386 */       wr.q = false;
/*     */       
/*     */       return;
/*     */     } 
/* 390 */     wr.a(entityLiving);
/* 391 */     wr.q = false;
/* 392 */     wr.isUpdating = false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void finishCurrentUpdate() {
/* 397 */     if (this.updateThread != null) {
/* 398 */       this.updateThread.unpauseToEndOfUpdate();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void resumeBackgroundUpdates() {
/* 404 */     if (this.updateThread != null) {
/* 405 */       this.updateThread.unpause();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void pauseBackgroundUpdates() {
/* 411 */     if (this.updateThread != null) {
/* 412 */       this.updateThread.pause();
/*     */     }
/*     */   }
/*     */   
/*     */   public void clearAllUpdates() {
/* 417 */     if (this.updateThread != null) {
/* 418 */       this.updateThread.clearAllUpdates();
/*     */     }
/* 420 */     this.firstUpdate = true;
/*     */   }
/*     */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\WrUpdaterThreaded.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */